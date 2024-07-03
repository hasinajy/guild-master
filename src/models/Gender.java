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

            while (rs.next()) {
                int genderID = rs.getInt("gender_id");
                String name = rs.getString("name");

                data.add(new Gender(genderID, name));
            }
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (rs != null)
                rs.close();

            if (stmt != null)
                stmt.close();

            if (conn != null)
                conn.close();
        }

        return data;
    }
}
