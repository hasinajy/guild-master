package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Postgres;

public class TransactionFull {
    private int transactionID;
    private Date date;
    private String transactionType;
    private String itemName;
    private String playerCharacterName;
    private String staffCharacterName;
    private String note;

    // Constructors
    public TransactionFull(int transactionID, Date date, String transactionType, String itemName,
            String playerCharacterName,
            String staffCharacterName, String note) {
        this.transactionID = transactionID;
        this.date = date;
        this.transactionType = transactionType;
        this.itemName = itemName;
        this.playerCharacterName = playerCharacterName;
        this.staffCharacterName = staffCharacterName;
        this.note = note;
    }

    // Getters & Setters
    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPlayerCharacterName() {
        return playerCharacterName;
    }

    public void setPlayerCharacterName(String playerCharacterName) {
        this.playerCharacterName = playerCharacterName;
    }

    public String getStaffCharacterName() {
        return staffCharacterName;
    }

    public void setStaffCharacterName(String staffCharacterName) {
        this.staffCharacterName = staffCharacterName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // User methods
    public static TransactionFull getByID(int transactionID) throws ClassNotFoundException, SQLException {
        TransactionFull transaction = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT\r\n" + //
                    "    transaction_id,\r\n" + //
                    "    date,\r\n" + //
                    "    transaction_type.name AS transaction_type,\r\n" + //
                    "    item.name AS item_name,\r\n" + //
                    "    player.character_name AS player_character_name,\r\n" + //
                    "    staff.username AS staff_character_name,\r\n" + //
                    "    note\r\n" + //
                    "FROM\r\n" + //
                    "    transaction\r\n" + //
                    "    JOIN transaction_type ON transaction.transaction_type_id = transaction_type.transaction_type_id\r\n"
                    + //
                    "    JOIN item ON transaction.item_id = item.item_id\r\n" + //
                    "    JOIN player ON transaction.player_id = player.player_id\r\n" + //
                    "    JOIN staff ON transaction.staff_id = staff.staff_id\r\n" + //
                    "WHERE\r\n" + //
                    "    transaction_id = ?;";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, transactionID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Date date = rs.getDate("date");
                String transactionType = rs.getString("transaction_type");
                String itemName = rs.getString("item_name");
                String playerCharacterName = rs.getString("player_character_name");
                String staffCharacterName = rs.getString("staff_character_name");
                String note = rs.getString("note");

                transaction = new TransactionFull(transactionID, date, transactionType, itemName, playerCharacterName,
                        staffCharacterName, note);
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

        return transaction;
    }

    public static ArrayList<TransactionFull> getAll() throws ClassNotFoundException, SQLException {
        ArrayList<TransactionFull> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT\r\n" + //
                    "    transaction_id,\r\n" + //
                    "    date,\r\n" + //
                    "    transaction_type.name AS transaction_type,\r\n" + //
                    "    item.name AS item_name,\r\n" + //
                    "    player.character_name AS player_character_name,\r\n" + //
                    "    staff.username AS staff_character_name,\r\n" + //
                    "    note\r\n" + //
                    "FROM\r\n" + //
                    "    transaction\r\n" + //
                    "    JOIN transaction_type ON transaction.transaction_type_id = transaction_type.transaction_type_id\r\n"
                    + //
                    "    JOIN item ON transaction.item_id = item.item_id\r\n" + //
                    "    JOIN player ON transaction.player_id = player.player_id\r\n" + //
                    "    JOIN staff ON transaction.staff_id = staff.staff_id;";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int transactionID = rs.getInt("transaction_id");
                Date date = rs.getDate("date");
                String transactionType = rs.getString("transaction_type");
                String itemName = rs.getString("item_name");
                String playerCharacterName = rs.getString("player_character_name");
                String staffCharacterName = rs.getString("staff_character_name");
                String note = rs.getString("note");

                data.add(
                        new TransactionFull(transactionID, date, transactionType, itemName, playerCharacterName,
                                staffCharacterName, note));
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

    public static ArrayList<TransactionFull> search(int sTransactionID, Date startDate, Date endDate, int sItemID,
            String sCharName) throws ClassNotFoundException, SQLException {
        ArrayList<TransactionFull> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = buildQuery(sTransactionID, startDate, endDate, sItemID, sCharName);

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int transactionID = rs.getInt("transaction_id");
                Date date = rs.getDate("date");
                String transactionType = rs.getString("transaction_type");
                String itemName = rs.getString("item_name");
                String playerCharacterName = rs.getString("player_character_name");
                String staffCharacterName = rs.getString("staff_character_name");
                String note = rs.getString("note");

                data.add(
                        new TransactionFull(transactionID, date, transactionType, itemName, playerCharacterName,
                                staffCharacterName, note));
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

    private static String buildQuery(int sTransactionID, Date startDate, Date endDate, int sItemID,
            String sCharName) {
        String query = "SELECT\r\n" + //
                "    transaction_id,\r\n" + //
                "    date,\r\n" + //
                "    transaction_type.name AS transaction_type,\r\n" + //
                "    item.name AS item_name,\r\n" + //
                "    player.character_name AS player_character_name,\r\n" + //
                "    staff.username AS staff_character_name,\r\n" + //
                "    note\r\n" + //
                "FROM\r\n" + //
                "    transaction\r\n" + //
                "    JOIN transaction_type ON transaction.transaction_type_id = transaction_type.transaction_type_id\r\n"
                + //
                "    JOIN item ON transaction.item_id = item.item_id\r\n" + //
                "    JOIN player ON transaction.player_id = player.player_id\r\n" + //
                "    JOIN staff ON transaction.staff_id = staff.staff_id\r\n" + //
                "WHERE\r\n" + //
                "    1 = 1";
        StringBuilder queryBuilder = new StringBuilder(query);

        if (sCharName != null) {
            queryBuilder.append(" AND player_character_name LIKE '%").append(sCharName).append("%'");
        }

        if (sTransactionID != -1) {
            queryBuilder.append(" AND transaction.transaction_id = ").append(sTransactionID);
        }

        if (startDate != null) {
            queryBuilder.append(" AND date >= ").append("'")
                    .append(new Date(startDate.getTime()))
                    .append("'");
        }

        if (endDate != null) {
            queryBuilder.append(" AND date <= ").append("'").append(new Date(endDate.getTime()))
                    .append("'");
        }

        if (sItemID != -1) {
            queryBuilder.append(" AND item.item_id = ").append(sItemID);
        }

        return queryBuilder.toString();
    }
}
