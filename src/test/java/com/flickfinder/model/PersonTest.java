package com.flickfinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {

	private Person person;

	@BeforeEach
	public void setUp() {
		person = new Person(3, "Christopher Nolan", 1970);
	}

	@Test
	public void testPersonCreated() {
		assertEquals(3, person.getId());
		assertEquals("Christopher Nolan", person.getName());
		assertEquals(1970, person.getBirth());
	}

}
