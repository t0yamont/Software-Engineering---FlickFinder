package com.flickfinder.controller;

import java.sql.SQLException;
import com.flickfinder.dao.PersonDAO;
import com.flickfinder.model.Person;
import io.javalin.http.Context;

public class PersonController {

    private final PersonDAO personDAO;

    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    // M.3: Retrieve a list of all people
    public void getAllPeople(Context ctx) {
        String limitParam = ctx.queryParam("limit");
        try {
            ctx.json(personDAO.getAllPeople(limitParam));
        } catch (SQLException e) {
            ctx.status(500);
            ctx.result("Database error");
            e.printStackTrace();
        }
    }

    // M.4: Retrieve a person by their ID
    public void getPersonById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            Person person = personDAO.getPersonById(id);
            if (person == null) {
                ctx.status(404);
                ctx.result("Person not found");
            } else {
                ctx.json(person);
            }
        } catch (SQLException e) {
            ctx.status(500);
            ctx.result("Database error");
            e.printStackTrace();
        }
    }

    public void getMoviesStarringPerson(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            ctx.json(personDAO.getMoviesStarringPerson(id));
        } catch (SQLException e) {
            ctx.status(500);
            ctx.result("Database error");
            e.printStackTrace();
        }
    }
}
