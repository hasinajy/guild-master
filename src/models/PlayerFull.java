package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Postgres;

public class PlayerFull {
    private int playerID;
    private String username;
    private String characterName;
    private int level;
    private String description;
    private String imgPath;

    private int genderID;
    private String genderName;

    private int factionID;
    private String factionName;

    public static final int MIN_LVL = 1;
    public static final int MAX_LVL = 100;

    // Constructors
    public PlayerFull() {
    }

    public PlayerFull(int playerID, String username, String characterName, int level, String description,
            String imgPath, int genderID, String genderName, int factionID, String factionName) {
        this.setPlayerID(playerID);
        this.setUsername(username);
        this.setCharacterName(characterName);
        this.setLevel(level);
        this.setDescription(description);
        this.setImgPath(imgPath);
        this.setGenderID(genderID);
        this.setGenderName(genderName);
        this.setFactionID(factionID);
        this.setFactionName(factionName);
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

    public int getGenderID() {
        return genderID;
    }

    public void setGenderID(int genderID) {
        this.genderID = genderID;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public int getFactionID() {
        return factionID;
    }

    public void setFactionID(int factionID) {
        this.factionID = factionID;
    }

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public static int getMinLvl() {
        return MIN_LVL;
    }

    public static int getMaxLvl() {
        return MAX_LVL;
    }

    // User methods
    public static PlayerFull getByID(int playerID) throws ClassNotFoundException, SQLException {
        PlayerFull player = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT\r\n" + //
                    "    player.player_id AS player_id,\r\n" + //
                    "    player.username AS username,\r\n" + //
                    "    player.character_name AS character_name,\r\n" + //
                    "    player.level AS level,\r\n" + //
                    "    player.description AS description,\r\n" + //
                    "    player.img_path AS img_path,\r\n" + //
                    "    gender.gender_id AS gender_id,\r\n" + //
                    "    gender.name AS gender_name,\r\n" + //
                    "    faction.faction_id AS faction_id,\r\n" + //
                    "    faction.name AS faction_name\r\n" + //
                    "FROM\r\n" + //
                    "    player\r\n" + //
                    "    JOIN gender ON player.gender_id = gender.gender_id\r\n" + //
                    "    LEFT JOIN faction ON player.faction_id = faction.faction_id\r\n" + //
                    "WHERE\r\n" + //
                    "    player.player_id = ?;";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, playerID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String characterName = rs.getString("character_name");
                int level = rs.getInt("level");
                String description = rs.getString("description");
                String imgPath = rs.getString("img_path");
                int genderID = rs.getInt("gender_id");
                String genderName = rs.getString("gender_name");
                int factionID = rs.getInt("faction_id");
                String factionName = rs.getString("faction_name");

                player = new PlayerFull(playerID, username, characterName, level, description, imgPath, genderID,
                        genderName, factionID, factionName);
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

    public static ArrayList<PlayerFull> getAll() throws ClassNotFoundException, SQLException {
        ArrayList<PlayerFull> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT\r\n" + //
                    "    player.player_id AS player_id,\r\n" + //
                    "    player.username AS username,\r\n" + //
                    "    player.character_name AS character_name,\r\n" + //
                    "    player.level AS level,\r\n" + //
                    "    player.description AS description,\r\n" + //
                    "    player.img_path AS img_path,\r\n" + //
                    "    gender.gender_id AS gender_id,\r\n" + //
                    "    gender.name AS gender_name,\r\n" + //
                    "    faction.faction_id AS faction_id,\r\n" + //
                    "    faction.name AS faction_name\r\n" + //
                    "FROM\r\n" + //
                    "    player\r\n" + //
                    "    JOIN gender ON player.gender_id = gender.gender_id\r\n" + //
                    "    LEFT JOIN faction ON player.faction_id = faction.faction_id\r\n" + //
                    "WHERE\r\n" + //
                    "    player.is_deleted = false;";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int playerID = rs.getInt("player_id");
                String username = rs.getString("username");
                String characterName = rs.getString("character_name");
                int level = rs.getInt("level");
                String description = rs.getString("description");
                String imgPath = rs.getString("img_path");
                int genderID = rs.getInt("gender_id");
                String genderName = rs.getString("gender_name");
                int factionID = rs.getInt("faction_id");
                String factionName = rs.getString("faction_name");

                data.add(
                        new PlayerFull(playerID, username, characterName, level, description, imgPath, genderID,
                                genderName, factionID, factionName));
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

    public static ArrayList<PlayerFull> search(String usrName, String charName, int minLvl, int maxLvl,
            int uFactionID) throws ClassNotFoundException, SQLException {
        ArrayList<PlayerFull> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = buildQuery(usrName, charName, minLvl, maxLvl, uFactionID);

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String characterName = rs.getString("character_name");
                int level = rs.getInt("level");
                String description = rs.getString("description");
                String imgPath = rs.getString("img_path");
                int genderID = rs.getInt("gender_id");
                String genderName = rs.getString("gender_name");
                int factionID = rs.getInt("faction_id");
                String factionName = rs.getString("faction_name");

                data.add(
                        new PlayerFull(maxLvl, username, characterName, level, description, imgPath, genderID,
                                genderName, factionID, factionName));
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

    private static String buildQuery(String usrName, String charName, int minLvl, int maxLvl, int factionID) {
        String query = "SELECT\r\n" + //
                "    player_id,\r\n" + //
                "    username,\r\n" + //
                "    character_name,\r\n" + //
                "    gender.name AS gender_name,\r\n" + //
                "    level,\r\n" + //
                "    faction.name AS faction_name,\r\n" + //
                "    description,\r\n" + //
                "    img_path\r\n" + //
                "FROM\r\n" + //
                "    player\r\n" + //
                "    JOIN gender ON player.gender_id = gender.gender_id\r\n" + //
                "    JOIN faction ON player.faction_id = faction.faction_id\r\n" + //
                "WHERE\r\n" + //
                "    1 = 1";

        StringBuilder queryBuilder = new StringBuilder(query);

        if (usrName != null) {
            queryBuilder.append(" AND username LIKE '%").append(usrName).append("%'");
        }

        if (charName != null) {
            queryBuilder.append(" AND character_name LIKE '%").append(charName).append("%'");
        }

        if (minLvl != -1 && maxLvl != -1) {
            queryBuilder.append(" AND level BETWEEN ").append(minLvl).append(" AND ").append(maxLvl);
        } else if (minLvl != -1) {
            queryBuilder.append(" AND level >= ").append(minLvl);
        } else if (maxLvl != -1) {
            queryBuilder.append(" AND level <= ").append(maxLvl);
        }

        if (factionID != -1) {
            queryBuilder.append(" AND faction.faction_id = ").append(factionID);
        }

        return queryBuilder.toString();
    }
}
