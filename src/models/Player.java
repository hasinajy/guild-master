package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import database.Postgres;

public class Player {
    private int playerId;
    private int level;
    private String description;
    private String imgPath;
    private Name name;
    private Gender gender;
    private Faction faction;

    /* ------------------------------ Constructors ------------------------------ */
    public Player() {
    }

    public Player(int playerId, int level, String description, String imgPath, Name name, Gender gender,
            Faction faction) {
        this.setPlayerId(playerId);
        this.setLevel(level);
        this.setDescription(description);
        this.setImgPath(imgPath);
        this.setName(name);
        this.setGender(gender);
        this.setFaction(faction);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    /* ---------------------------- Database methods ---------------------------- */
    public static Player getById(int playerId) throws ClassNotFoundException, SQLException {
        Player player = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM player WHERE player_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, playerId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String characterName = rs.getString("character_name");
                int genderId = rs.getInt("gender_id");
                int level = rs.getInt("level");
                int factionId = rs.getInt("faction_id");
                String description = rs.getString("description");
                String imgPath = rs.getString("img_path");

                player = new Player(playerId, username, characterName, genderId, level, factionId, description,
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
            stmt.setInt(1, this.getPlayerId());
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

    public static void delete(int playerId) throws ClassNotFoundException, SQLException {
        new Player(playerId).delete();
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
            stmt.setInt(3, this.genderId);
            stmt.setInt(4, this.level);
            stmt.setInt(5, this.factionId);
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
                stmt.setInt(3, this.getGenderId());
                stmt.setInt(4, this.getLevel());

                if (this.getFactionId() == 0) {
                    stmt.setNull(5, Types.INTEGER);
                } else {
                    stmt.setInt(5, this.getFactionId());
                }

                stmt.setString(6, this.getDescription());
                stmt.setInt(7, this.playerId);
            } else {
                String query = "UPDATE player SET"
                        + " username = ?, character_name = ?, gender_id = ?, level = ?, faction_id = ?, description = ?, img_path = ?"
                        + " WHERE player_id = ?";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, this.getUsername());
                stmt.setString(2, this.getCharacterName());
                stmt.setInt(3, this.getGenderId());
                stmt.setInt(4, this.getLevel());

                if (this.getFactionId() == 0) {
                    stmt.setNull(5, Types.INTEGER);
                } else {
                    stmt.setInt(5, this.getFactionId());
                }

                stmt.setString(6, this.getDescription());
                stmt.setString(7, this.getImgPath());
                stmt.setInt(8, this.playerId);
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

    public void update(int playerId) throws ClassNotFoundException, SQLException {
        this.setPlayerId(playerId);
        this.update();
    }
}
