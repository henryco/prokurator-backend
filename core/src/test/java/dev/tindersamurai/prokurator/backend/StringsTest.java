package dev.tindersamurai.prokurator.backend;

import lombok.val;
import org.junit.Test;

import java.util.UUID;

public class StringsTest {

	@Test
	public void extensionTrimTest() {
		System.out.println(test(UUID.randomUUID().toString()));
		System.out.println(test("randomFIle.png"));
		System.out.println(test("some."));
		System.out.println(test(".hmhm"));
		System.out.println(test(".what.s"));
	}

	private static String test(String t) {
		val index = t.lastIndexOf(".");
		if (index <= 0) return t;
		return t.substring(0, index);
	}
}
