package dev.tindersamurai.prokurator.backend.mvc.data.filter;

import dev.tindersamurai.prokurator.backend.commons.entity.MediaContent;
import dev.tindersamurai.prokurator.backend.commons.entity.MediaProbe;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component @Slf4j
public class MediaFilterSql implements IMediaFilter {

	@SuppressWarnings("SameParameterValue")
	private static final class Query {
		private final List<Integer> types;
		private final List<Object> args;
		private final StringBuilder builder;

		private Query() {
			builder = new StringBuilder();
			types = new ArrayList<>();
			args = new ArrayList<>();
		}

		private static Query create() {
			return new Query();
		}

		private Query addArgument(Object arg) {
			return addArgument(arg, Types.CHAR);
		}

		private Query addArgument(Object arg, int type) {
			types.add(type);
			args.add(arg);
			return this;
		}

		private Query in(String name, Object[] params) {
			if (params == null || params.length == 0) return this;

			builder.append("AND ");
			builder.append(name);
			builder.append(" IN (\n");

			for (int i = 0; i < params.length; i++) {
				if (i != 0) builder.append(", ");
				builder.append("?");
				addArgument(params[i]);
			}

			builder.append(")\n");

			return this;
		}

		private Query like(String name, Object[] params) {
			if (params == null || params.length == 0) return this;

			builder.append("AND (\n");
			for (int i = 0; i < params.length; i++) {
				if (i != 0) builder.append(" OR ");
				builder.append(name);
				builder.append(" LIKE ?");
				addArgument("%" + params[i] + "%");
			}
			builder.append(")\n");

			return this;
		}

		private Query any(Object param, String ... names) {
			if (param == null || param.toString().isEmpty())
				return this;

			val arg = "%" + param + "%";
			builder.append("AND (\n");
			for (int i = 0; i < names.length; i++) {
				if (i != 0) builder.append(" OR ");
				builder.append(names[i]);
				builder.append(" LIKE ?");
				addArgument(arg);
			}
			builder.append(")\n");

			return this;
		}


		private Query date(String name, String op, Long param) {
			if (param == null) return this;

			builder.append("AND ");
			builder.append(name);
			builder.append(" ");
			builder.append(op);
			builder.append(" ?\n");

			return addArgument(new Date(param), Types.TIMESTAMP);
		}

		@Override
		public String toString() {
			return builder.toString();
		}

		private Object[] getArgs() {
			return args.toArray(new Object[0]);
		}

		private int[] getTypes() {
			val its = new int[types.size()];
			for (int i = 0; i < types.size(); i++)
				its[i] = types.get(i);
			return its;
		}
	}

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public MediaFilterSql(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<MediaContent> filterMedia(MediaProbe probe, String guildId) {

		val query = probe.getQuery();
		val page = probe.getPage();

		val builder = new StringBuilder();
		builder.append("SELECT media.*, txc.category, txc.nsfw, txc.guild_id\n");
		builder.append("FROM media_post as media\n");
		builder.append("JOIN text_channel as txc\n");
		builder.append("on txc.id = media.channel_id\n");
		builder.append("WHERE\n");
		builder.append("txc.guild_id = ?\n");

		val criteria = Query.create()
				.addArgument(guildId)
				.in("media.channel_id", query.getChannel())
				.like("txc.category", query.getCategory())
				.in("media.removed", query.getDeleted())
				.in("media.author_id", query.getUser())
				.like("media.name", query.getFile())
				.in("txc.nsfw", query.getNsfw())
				.date("media.date", ">=", query.getAfter())
				.date("media.date", "<=", query.getBefore())
				.any(query.getRaw(),
						"media.name",
						"media.channel_id",
						"media.author_id",
						"media.removed",
						"txc.category",
						"txc.nsfw"
				);


		builder.append(criteria.toString());

		builder.append("\nORDER BY media.date DESC\n");
		builder.append(" LIMIT ").append(page.getSize());
		builder.append(" OFFSET ").append(page.getSize() * page.getPage());

		val string = builder.toString();
		val types = criteria.getTypes();
		val args = criteria.getArgs();

		log.debug("FILTER MEDIA: {}, {}, {}", args, types, string);

		return jdbcTemplate.query(string, args, types, (rs, column) ->
				MediaContent.builder()
						.id(rs.getString("id"))
						.date(rs.getDate("date").getTime())
						.size(rs.getLong("size"))
						.removed(rs.getBoolean("removed"))
						.image(rs.getBoolean("image"))
						.nsfw(rs.getBoolean("nsfw"))
						.author(rs.getString("author_id"))
						.channel(rs.getString("channel_id"))
						.file(rs.getString("fid"))
						.name(rs.getString("name"))
						.category(rs.getString("category"))
						.guild(rs.getString("guild_id"))
						.build()
		);
	}

}
