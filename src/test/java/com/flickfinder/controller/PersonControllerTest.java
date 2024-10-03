package com.flickfinder.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.dao.PersonDAO;

import io.javalin.http.Context;


class PersonControllerTest {


	private Context ctx;


	private PersonDAO personDAO;


	private PersonController personController;

	@BeforeEach
	void setUp() {

		personDAO = mock(PersonDAO.class);

		ctx = mock(Context.class);

		personController = new PersonController(personDAO);
	}


	@Test
	void testGetAllPersons() {
		personController.getAllPeople(ctx);
		try {
			verify(personDAO).getAllPeople(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Test
	void testThrows500ExceptionWhenGetAllDatabaseError() throws SQLException {
		when(personDAO.getAllPeople(null)).thenThrow(new SQLException());
		personController.getAllPeople(ctx);
		verify(ctx).status(500);
	}


	@Test
	void testGetPersonById() {
		when(ctx.pathParam("id")).thenReturn("1");
		personController.getPersonById(ctx);
		try {
			verify(personDAO).getPersonById(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Test
	void testThrows500ExceptionWhenGetByIdDatabaseError() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("3");
		when(personDAO.getPersonById(3)).thenThrow(new SQLException());
		personController.getPersonById(ctx);
		verify(ctx).status(500);
	}


	@Test
	void testThrows404ExceptionWhenNoPersonFound() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("3");
		when(personDAO.getPersonById(3)).thenReturn(null);
		personController.getPersonById(ctx);
		verify(ctx).status(404);
	}
}
