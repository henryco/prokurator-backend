package dev.tindersamurai.prokurator.backend.mvc.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
public class Guild {

	private @Id @Column(
			name = "id"
	) String id;

	private @Column(
			name = "name",
			nullable = false
	) String name;

}
