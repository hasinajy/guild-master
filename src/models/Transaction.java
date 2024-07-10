package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Postgres;
import database.PostgresResources;
import utils.DateUtils;

public class Transaction {
    private int transactionId;
    private TransactionType transactionType;
    private Date date;
    private Item item;
    private Player player;
    private Staff staff;
    private String note;

    // Queries
    private static final String CREATE_QUERY = "INSERT INTO transaction(transaction_type_id, date, item_id, player_id, staff_id, note) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String READ_QUERY = "SELECT * FROM transaction";
    private static final String UPDATE_QUERY = "UPDATE transaction SET date = ?, transaction_type_id = ?, item_id = ?, player_id = ?, staff_id = ?, note = ? WHERE transaction_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM transaction WHERE transaction_id = ?";
    private static final String BASE_QUERY = "SELECT * FROM transaction WHERE 1=1";

    /* ------------------------------ Constructors ------------------------------ */
    public Transaction() {
    }

    public Transaction(int transactionId, TransactionType transactionType, Date date, Item item, Player player,
            Staff staff, String note) {
        this.setTransactionId(transactionId);
        this.setTransactionType(transactionType);
        this.setDate(date);
        this.setItem(item);
        this.setPlayer(player);
        this.setStaff(staff);
        this.setNote(note);
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /* ---------------------------- Service methods ---------------------------- */
    public void deposit(int inventoryId) throws ClassNotFoundException, SQLException {
        // TODO: Fix the conditionals for deposit

        Inventory inventory = Inventory.getById(inventoryId);

        if (inventory == null) {
            return;
        }

        Transaction transaction = new Transaction(1, null, 2, inventory.getItemId(), inventory.getPlayerId(), 1, "");
        transaction.create();
    }

    public static void withdraw(int inventoryId) throws ClassNotFoundException, SQLException {
        // TODO: Fix the conditionals for withdrawal

        Inventory inventory = Inventory.getById(inventoryId);

        if (inventory == null) {
            return;
        }

        // Transaction can only occur if the player is still active
        if (inventory.getPlayerId() != 0) {
            Transaction transaction = new Transaction();

            TransactionType transactionType = new TransactionType();
            transactionType.setTransactionTypeId(1);

            Item item = new Item();
            item.setItemId(inventory.getItemId());

            Player player = new Player();
            player.setPlayerID(inventory.getPlayerId());

            Staff staff = new Staff();
            staff.setStaffID(1);

            transaction.setTransactionType(transactionType);
            transaction.setDate(DateUtils.getCurrentDate());
            transaction.setItem(item);
            transaction.setPlayer(player);
            
            transaction.create();
        }
    }

    /* ---------------------------- Database methods ---------------------------- */
    // Create
    public void create() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            // TODO: Check if this.getDate() can be null and if it can be, always add a
            // default date from the controller

            pg.initResources(Transaction.getCreateQuery());
            pg.setStmtValues(Transaction.getCreateClassList(), this.getCreateValues());
            pg.executeQuery(true);

            if (this.getTransactionType().getTransactionTypeId() == 2) {
                // TODO: Add inventory row after deposit
            } else {
                // TODO: Remove inventory row after withdrawal
            }
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    // Read
    public static Transaction getById(int transactionId) throws ClassNotFoundException, SQLException {
        Transaction transaction = null;
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Transaction.getReadQuery(true));
            pg.setStmtValues(int.class, new Object[] { transactionId });
            pg.executeQuery(false);

            transaction = Transaction.getRowInstance(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return transaction;
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

    // Update
    public void update() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Transaction.getUpdateQuery());
            pg.setStmtValues(Transaction.getUpdateClassList(), this.getUpdateValues());
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    public void update(int transactionId) throws ClassNotFoundException, SQLException {
        this.setTransactionId(transactionId);
        this.update();
    }

    // Delete
    public void delete() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Transaction.getDeleteQuery());
            pg.setStmtValues(int.class, new Object[] { this.getTransactionId() });
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    public static void delete(int transactionId) throws ClassNotFoundException, SQLException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.delete();
    }

    /* ----------------------------- Utility methods ---------------------------- */
    // Instantiation methods
    private static Transaction createTransactionFromResultSet(PostgresResources pg) throws SQLException {
        Transaction transaction = new Transaction();

        TransactionType transactionType = new TransactionType();
        transactionType.setTransactionTypeId(pg.getInt("transaction.transaction_type_id"));

        Item item = new Item();
        item.setItemId(pg.getInt("transaction.item_id"));

        Player player = new Player();
        player.setPlayerID(pg.getInt("transaction.player_id"));

        Staff staff = new Staff();
        staff.setStaffID(pg.getInt("transaction.staff_id"));

        transaction.setTransactionType(transactionType);
        transaction.setDate(pg.getDate("transaction.date"));
        transaction.setItem(item);
        transaction.setPlayer(player);
        transaction.setStaff(staff);
        transaction.setNote(pg.getString("transaction.note"));

        return transaction;
    }

    private static Transaction getRowInstance(PostgresResources pg) throws SQLException {
        Transaction transaction = null;

        if (pg.next()) {
            transaction = Transaction.createTransactionFromResultSet(pg);
        }

        return transaction;
    }

    // Create
    private static String getCreateQuery() {
        return Transaction.CREATE_QUERY;
    }

    private static Class<?>[] getCreateClassList() {
        return new Class[] {
                int.class,
                Date.class,
                int.class,
                int.class,
                int.class,
                String.class
        };
    }

    private Object[] getCreateValues() {
        return new Object[] {
                this.getTransactionType().getTransactionTypeId(),
                this.getDate(),
                this.getItem().getItemId(),
                this.getPlayer().getPlayerID(),
                this.getStaff().getStaffID(),
                this.getNote()
        };
    }

    // Read
    private static String getReadQuery(boolean hasWhere) {
        StringBuilder sb = new StringBuilder(Transaction.READ_QUERY);

        if (hasWhere) {
            sb.append(" WHERE transaction_id = ?");
        }

        return sb.toString();
    }

    // Update
    private static String getUpdateQuery() {
        return Transaction.UPDATE_QUERY;
    }

    private static Class<?>[] getUpdateClassList() {
        return new Class[] {
                Date.class,
                int.class,
                int.class,
                int.class,
                int.class,
                String.class,
                int.class
        };
    }

    private Object[] getUpdateValues() {
        return new Object[] {
                this.getDate(),
                this.getTransactionType().getTransactionTypeId(),
                this.getItem().getItemId(),
                this.getPlayer().getPlayerID(),
                this.getStaff().getStaffID(),
                this.getNote(),
                this.getTransactionType()
        };
    }

    // Delete
    private static String getDeleteQuery() {
        return Transaction.DELETE_QUERY;
    }
}
