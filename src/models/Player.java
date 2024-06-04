package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import database.Postgres;

public class Player {
    private int playerID;
    private String username;
    private String characterName;
    private int genderID;
    private int level;
    private int factionID;
    private String description;
    private String imgPath;

    // Constructors
    public Player() {
    }

    public Player(int playerID) {
        this.playerID = playerID;
    }

    public Player(int playerID, String username, String characterName, int genderID, int level, int factionID,
            String description, String imgPath) {
        this.playerID = playerID;
        this.username = username;
        this.characterName = characterName;
        this.genderID = genderID;
        this.level = level;
        this.setFactionID(factionID);
        this.setDescription(description);
        this.setImgPath(imgPath);
    }

    // Getters & Setters
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
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

    public int getGenderID() {
        return genderID;
    }

    public void setGenderID(int genderID) {
        this.genderID = genderID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFactionID() {
        return factionID;
    }

    public void setFactionID(int factionID) {
        this.factionID = factionID;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    // User methods
    public static Player getByID(int playerID) throws ClassNotFoundException, SQLException {
        Player player = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM player WHERE player_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, playerID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String characterName = rs.getString("character_name");
                int genderID = rs.getInt("gender_id");
                int level = rs.getInt("level");
                int factionID = rs.getInt("faction_id");
                String description = rs.getString("description");
                String imgPath = rs.getString("img_path");

                player = new Player(playerID, username, characterName, genderID, level, factionID, description,
                        imgPath);
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

        return player;
    }

    public void delete() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE\r\n" + //
                    "    player\r\n" + //
                    "SET\r\n" + //
                    "    is_deleted = true\r\n" + //
                    "WHERE\r\n" + //
                    "    player_id = ?;";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.getPlayerID());
            stmt.executeUpdate();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();

            if (conn != null)
                conn.close();
        }
    }

    public void create() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO player(username, character_name, gender_id, level, faction_id, description, img_path)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, this.username);
            stmt.setString(2, this.characterName);
            stmt.setInt(3, this.genderID);
            stmt.setInt(4, this.level);
            stmt.setInt(5, this.factionID);
            stmt.setString(6, this.description);
            stmt.setString(7, this.getImgPath());
            stmt.executeUpdate();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();

            if (conn != null)
                conn.close();
        }
    }

    public void update() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Postgres.getInstance().getConnection();

            if (this.getImgPath().equals("player/")) {
                String query = "UPDATE player SET"
                        + " username = ?, character_name = ?, gender_id = ?, level = ?, faction_id = ?, description = ?"
                        + " WHERE player_id = ?";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, this.getUsername());
                stmt.setString(2, this.getCharacterName());
                stmt.setInt(3, this.getGenderID());
                stmt.setInt(4, this.getLevel());

                if (this.getFactionID() == 0) {
                    stmt.setNull(5, Types.INTEGER);
                } else {
                    stmt.setInt(5, this.getFactionID());
                }

                stmt.setString(6, this.getDescription());
                stmt.setInt(7, this.playerID);
            } else {
                String query = "UPDATE player SET"
                        + " username = ?, character_name = ?, gender_id = ?, level = ?, faction_id = ?, description = ?, img_path = ?"
                        + " WHERE player_id = ?";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, this.getUsername());
                stmt.setString(2, this.getCharacterName());
                stmt.setInt(3, this.getGenderID());
                stmt.setInt(4, this.getLevel());

                if (this.getFactionID() == 0) {
                    stmt.setNull(5, Types.INTEGER);
                } else {
                    stmt.setInt(5, this.getFactionID());
                }

                stmt.setString(6, this.getDescription());
                stmt.setString(7, this.getImgPath());
                stmt.setInt(8, this.playerID);
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();

            if (conn != null)
                conn.close();
        }
    }
}
