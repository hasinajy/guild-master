package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.PostgresResources;

public class Gender {
    private int genderId;
    private String name;

    /* ------------------------------ Constructors ------------------------------ */
    public Gender() {
    }

    public Gender(int genderId) {
        this.genderId = genderId;
        this.name = "Default Gender";
    }

    public Gender(int genderId, String name) {
        this.genderId = genderId;
        this.name = name;
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* ---------------------------- Database methods ---------------------------- */
    // Utility methods
    private static Gender getRowInstance(PostgresResources pg) throws SQLException {
        Gender gender = new Gender();

        gender.setGenderId(pg.getInt("gender_id"));
        gender.setName(pg.getString("name"));

        return gender;
    }

    private static List<Gender> getTableInstance(PostgresResources pg) throws SQLException {
        List<Gender> genderList = new ArrayList<>();

        while (pg.next()) {
            genderList.add(Gender.getRowInstance(pg));
        }

        return genderList;
    }

    // Read
    public static List<Gender> getAll() throws ClassNotFoundException, SQLException {
        List<Gender> data = new ArrayList<>();
        PostgresResources pg = new PostgresResources();

        try {
            String query = "SELECT gender_id, name FROM gender";

            pg.initResources(query);
            pg.executeQuery(false);

           data = Gender.getTableInstance(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return data;
    }
}
