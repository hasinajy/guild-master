package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.PostgresResources;

public class Staff {
    private int staffId;
    private String username;
    private String characterName;

    /* ------------------------------ Constructors ------------------------------ */
    public Staff() {
    }

    public Staff(int staffId, String username, String characterName) {
        this.setStaffId(staffId);
        this.setUsername(username);
        this.setCharacterName(characterName);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    /* ---------------------------- Database methods ---------------------------- */
    // Create
    public void create() throws SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            // TODO: Implement the create method of the Staff model
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    // Read
    public List<Faction> getAll() throws SQLException {
        List<Faction> data = new ArrayList<>();
        PostgresResources pg = new PostgresResources();

        try {
            // TODO: Implement the getAll method of the Staff model
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return data;
    }

    // Update
    public void update() throws SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            // TODO: Implement the udpate method of the Staff model
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    // Delete
    public void delete() throws SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            // TODO: Implement the delete method of the Staff model
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }
}
