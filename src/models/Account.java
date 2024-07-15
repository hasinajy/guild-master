package models;

import java.sql.SQLException;

import database.PostgresResources;

public class Account {
    private String username;
    private String password;

    /* ------------------------------ Constructors ------------------------------ */
    public Account() {
    }

    public Account(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /* ---------------------------- Database methods ---------------------------- */
    public boolean exists() throws ClassNotFoundException, SQLException {
        boolean accountExists = false;
        PostgresResources pg = new PostgresResources();

        try {
            String query = "SELECT * FROM account WHERE username = ? AND password = encode(digest(?, 'sha1'), 'hex')";
            pg.initResources(query);
            pg.setStmtValues(String.class, new Object[] { this.getUsername(), this.getPassword() });
            pg.executeQuery(false);

            if (pg.next()) {
                accountExists = true;
            }
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return accountExists;
    }
}
