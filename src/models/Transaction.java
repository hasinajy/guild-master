package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.Postgres;

public class Transaction {
    private int transactionID;
    private Date date;
    private int transactionTypeID;
    private int itemID;
    private int playerID;
    private int staffID;
    private String note;

    // Constructors
    public Transaction(int transactionID) {
        this.transactionID = transactionID;
    }

    public Transaction(int transactionID, Date date, int transactionTypeID, int itemID, int playerID, int staffID,
            String note) {
        this.transactionID = transactionID;
        this.date = date;
        this.transactionTypeID = transactionTypeID;
        this.itemID = itemID;
        this.playerID = playerID;
        this.staffID = staffID;
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

    public int getTransactionTypeID() {
        return transactionTypeID;
    }

    public void setTransactionTypeID(int transactionTypeID) {
        this.transactionTypeID = transactionTypeID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // Class methods
    public void withdraw(int inventoryID) throws ClassNotFoundException, SQLException {
        Inventory inventory = Inventory.getByID(inventoryID);

        if (inventory == null)
            return;

        if (inventory.getPlayerID() != 0)
            new Transaction(1, null, 1, inventory.getItemID(), inventory.getPlayerID(), 1, "").create();
    }

    public void deposit(int inventoryID) throws ClassNotFoundException, SQLException {
        Inventory inventory = Inventory.getByID(inventoryID);

        if (inventory == null)
            return;

        new Transaction(1, null, 2, inventory.getItemID(), inventory.getPlayerID(), 1, "").create();
    }

    public static Transaction getByID(int transactionID) throws ClassNotFoundException, SQLException {
        Transaction transaction = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM transaction WHERE transaction_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, transactionID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Date date = rs.getDate("date");
                int transactionTypeID = rs.getInt("transaction_type_id");
                int itemID = rs.getInt("item_id");
                int playerID = rs.getInt("player_id");
                int staffID = rs.getInt("staff_id");
                String note = rs.getString("note");

                transaction = new Transaction(transactionID, date, transactionTypeID, itemID, playerID, staffID, note);
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

    public void delete() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM transaction WHERE transaction_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.transactionID);
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
            String query = "";

            if (this.getDate() == null) {
                query = "INSERT INTO transaction(transaction_type_id, item_id, player_id, staff_id, note)"
                        + " VALUES (?, ?, ?, ?, ?)";
            } else {
                query = "INSERT INTO transaction(transaction_type_id, item_id, player_id, staff_id, note, date)"
                        + " VALUES (?, ?, ?, ?, ?, ?)";
            }

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.transactionTypeID);
            stmt.setInt(2, this.itemID);
            stmt.setInt(3, this.playerID);
            stmt.setInt(4, this.staffID);
            stmt.setString(5, this.note);

            if (this.getDate() != null)
                stmt.setDate(6, this.date);

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
            String query = "UPDATE transaction SET"
                    + " date = ?, transaction_type_id = ?, item_id = ?, player_id = ?, staff_id = ?, note = ?"
                    + " WHERE transaction_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, this.date.toString());
            stmt.setInt(2, this.transactionTypeID);
            stmt.setInt(3, this.itemID);
            stmt.setInt(4, this.playerID);
            stmt.setInt(5, this.staffID);
            stmt.setString(6, this.note);
            stmt.setInt(7, this.transactionID);
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
