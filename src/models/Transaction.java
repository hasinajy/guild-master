package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Postgres;

public class Transaction {
    private int transactionId;
    private Date date;
    private int transactionTypeId;
    private int itemId;
    private int playerId;
    private int staffId;
    private String note;

    private static final String BASE_QUERY = "SELECT * FROM transaction WHERE 1=1";

    /* ------------------------------ Constructors ------------------------------ */
    public Transaction() {
    }

    public Transaction(int transactionId) {
        this.transactionId = transactionId;
    }

    public Transaction(int transactionId, Date date, int transactionTypeId, int itemId, int playerId, int staffId,
            String note) {
        this.transactionId = transactionId;
        this.date = date;
        this.transactionTypeId = transactionTypeId;
        this.itemId = itemId;
        this.playerId = playerId;
        this.staffId = staffId;
        this.note = note;
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /* ---------------------------- Service methods ---------------------------- */
    public static void withdraw(int inventoryID) throws ClassNotFoundException, SQLException {
        // TODO: Fix the conditionals for withdrawal

        Inventory inventory = Inventory.getById(inventoryID);

        if (inventory == null) {
            return;
        }

        if (inventory.getPlayerId() != 0) {
            Transaction transaction = new Transaction(0, null, 1, inventory.getItemId(), inventory.getPlayerId(), 1,
                    "");
            transaction.create();
        }
    }

    public void deposit(int inventoryID) throws ClassNotFoundException, SQLException {
        // TODO: Fix the conditionals for deposit

        Inventory inventory = Inventory.getById(inventoryID);

        if (inventory == null)
            return;

        new Transaction(1, null, 2, inventory.getItemId(), inventory.getPlayerId(), 1, "").create();
    }


    /* ---------------------------- Database methods ---------------------------- */
    public static Transaction getById(int transactionId) throws ClassNotFoundException, SQLException {
        Transaction transaction = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT date, transaction_type_id, item_id, player_id, staff_id, note FROM transaction WHERE transaction_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, transactionId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Date date = rs.getDate("date");
                int transactionTypeId = rs.getInt("transaction_type_id");
                int itemId = rs.getInt("item_id");
                int playerId = rs.getInt("player_id");
                int staffId = rs.getInt("staff_id");
                String note = rs.getString("note");

                transaction = new Transaction(transactionId, date, transactionTypeId, itemId, playerId, staffId, note);
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
            stmt.setInt(1, this.getTransactionId());
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

    public static void delete(int transactionId) throws ClassNotFoundException, SQLException {
        new Transaction(transactionId).delete();
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
            stmt.setInt(1, this.getTransactionTypeId());
            stmt.setInt(2, this.getItemId());
            stmt.setInt(3, this.getPlayerId());
            stmt.setInt(4, this.getStaffId());
            stmt.setString(5, this.getNote());

            if (this.getDate() != null)
                stmt.setDate(6, this.getDate());

            stmt.executeUpdate();

            if (this.getTransactionTypeId() == 2) {
                new Inventory(0, itemId, playerId, 10, 1, 0, 0).create();
            } else {
                // TODO: Remove inventory row after withdrawal
            }
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
            stmt.setDate(1, this.getDate());
            stmt.setInt(2, this.getTransactionTypeId());
            stmt.setInt(3, this.getItemId());
            stmt.setInt(4, this.getPlayerId());
            stmt.setInt(5, this.getStaffId());
            stmt.setString(6, this.getNote());
            stmt.setInt(7, this.getTransactionId());
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

    public void update(int transactionId) throws ClassNotFoundException, SQLException {
        this.setTransactionId(transactionId);
        this.update();
    }

    public static List<Transaction> searchTransactions(TransactionSearchCriteria criteria)
            throws ClassNotFoundException, SQLException {
        List<Transaction> transactions = new ArrayList<>();
        StringBuilder query = new StringBuilder(BASE_QUERY);

        List<Object> parameters = new ArrayList<>();
        if (criteria.getTransactionTypeId() != null) {
            query.append(" AND transaction_type_id = ?");
            parameters.add(criteria.getTransactionTypeId());
        }
        if (criteria.getStartDate() != null) {
            query.append(" AND date >= ?");
            parameters.add(criteria.getStartDate());
        }
        if (criteria.getEndDate() != null) {
            query.append(" AND date <= ?");
            parameters.add(criteria.getEndDate());
        }
        if (criteria.getItemId() != null) {
            query.append(" AND item_id = ?");
            parameters.add(criteria.getItemId());
        }
        if (criteria.getPlayerId() != null) {
            query.append(" AND player_id = ?");
            parameters.add(criteria.getPlayerId());
        }

        query.append(" ORDER BY transaction_type_id, date");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query.toString());

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setDate(rs.getDate("date"));
                transaction.setTransactionTypeId(rs.getInt("transaction_type_id"));
                transaction.setItemId(rs.getInt("item_id"));
                transaction.setPlayerId(rs.getInt("player_id"));
                transaction.setStaffId(rs.getInt("staff_id"));
                transaction.setNote(rs.getString("note"));
                transactions.add(transaction);
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

        return transactions;
    }
}
