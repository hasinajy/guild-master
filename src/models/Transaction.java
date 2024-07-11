package models;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private static final String JOIN_QUERY = "SELECT * FROM v_transaction";

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
    public static void deposit(int inventoryId) throws ClassNotFoundException, SQLException {
        // TODO: Fix the conditionals for deposit

        Inventory inventory = Inventory.getById(inventoryId);

        if (inventory == null) {
            return;
        }

        if (inventory.getPlayer().getPlayerId() != 0) {
            Transaction transaction = new Transaction();

            TransactionType transactionType = new TransactionType();
            transactionType.setTransactionTypeId(2);

            Item item = new Item();
            item.setItemId(inventory.getItem().getItemId());

            Player player = new Player();
            player.setPlayerId(inventory.getPlayer().getPlayerId());

            Staff staff = new Staff();
            staff.setStaffId(1);

            transaction.setTransactionType(transactionType);
            transaction.setDate(DateUtils.getCurrentDate());
            transaction.setItem(item);
            transaction.setPlayer(player);

            transaction.create();
        }
    }

    public static void withdraw(int inventoryId) throws ClassNotFoundException, SQLException {
        // TODO: Fix the conditionals for withdrawal

        Inventory inventory = Inventory.getById(inventoryId);

        if (inventory == null) {
            return;
        }

        // Transaction can only occur if the player is still active
        if (inventory.getPlayer().getPlayerId() != 0) {
            Transaction transaction = new Transaction();

            TransactionType transactionType = new TransactionType();
            transactionType.setTransactionTypeId(1);

            Item item = new Item();
            item.setItemId(inventory.getItem().getItemId());

            Player player = new Player();
            player.setPlayerId(inventory.getPlayer().getPlayerId());

            Staff staff = new Staff();
            staff.setStaffId(1);

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

    public static List<Transaction> getAll() throws ClassNotFoundException, SQLException {
        List<Transaction> transactions = new ArrayList<>();
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Transaction.getJoinQuery(false));
            pg.executeQuery(false);

            transactions = Transaction.getJoinTableInstance(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return transactions;
    }

    public static List<Transaction> searchTransactions(TransactionSearchCriteria criteria)
            throws ClassNotFoundException, SQLException {
        List<Transaction> transactions = new ArrayList<>();
        PostgresResources pg = new PostgresResources();

        try {
            QueryCondition queryCondition = new QueryCondition(Transaction.getJoinQuery(true));

            queryCondition.addCondition(" AND transaction.transaction_type_id = ?",
                    int.class, criteria.getTransactionTypeId());
            queryCondition.addCondition(" AND transaction.date >= ?",
                    Date.class, criteria.getStartDate());
            queryCondition.addCondition(" AND transaction.date <= ?",
                    Date.class, criteria.getEndDate());
            queryCondition.addCondition(" AND transaction.item_id = ?",
                    int.class, criteria.getItemId());
            queryCondition.addCondition(" AND transaction.player_id = ?",
                    int.class, criteria.getPlayerId());

            pg.initResources(queryCondition.getQuery());
            pg.setStmtValues(queryCondition.getClassList(), queryCondition.getParameters());
            pg.executeQuery(false);

            transactions = Transaction.getJoinTableInstance(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
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
        player.setPlayerId(pg.getInt("transaction.player_id"));

        Staff staff = new Staff();
        staff.setStaffId(pg.getInt("transaction.staff_id"));

        transaction.setTransactionType(transactionType);
        transaction.setDate(pg.getDate("transaction.date"));
        transaction.setItem(item);
        transaction.setPlayer(player);
        transaction.setStaff(staff);
        transaction.setNote(pg.getString("transaction.note"));

        return transaction;
    }

    private static Transaction createTransactionFromJoin(PostgresResources pg) throws SQLException {
        Transaction transaction = new Transaction();

        TransactionType transactionType = new TransactionType();
        transactionType.setTransactionTypeId(pg.getInt("transaction.transaction_type_id"));
        transactionType.setName(pg.getString("transaction_type.name"));

        Item item = new Item();
        item.setItemId(pg.getInt("transaction.item_id"));
        item.setName(pg.getString("item.name"));

        Name name = new Name();
        name.setCharacterName(pg.getString("player.character_name"));

        Player player = new Player();
        player.setPlayerId(pg.getInt("transaction.player_id"));
        player.setName(name);

        Staff staff = new Staff();
        staff.setStaffId(pg.getInt("transaction.staff_id"));
        staff.setCharacterName(pg.getString("staff.character_name"));

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

    private static List<Transaction> getJoinTableInstance(PostgresResources pg) throws SQLException {
        List<Transaction> transactionList = new ArrayList<>();

        while (pg.next()) {
            Transaction transaction = Transaction.createTransactionFromJoin(pg);
            transactionList.add(transaction);
        }

        return transactionList;
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
                this.getPlayer().getPlayerId(),
                this.getStaff().getStaffId(),
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
                this.getPlayer().getPlayerId(),
                this.getStaff().getStaffId(),
                this.getNote(),
                this.getTransactionType()
        };
    }

    // Delete
    private static String getDeleteQuery() {
        return Transaction.DELETE_QUERY;
    }

    // Join
    private static String getJoinQuery(boolean hasWhere) {
        StringBuilder sb = new StringBuilder(Transaction.JOIN_QUERY);

        if (hasWhere) {
            sb.append(" WHERE 1 = 1");
        }

        return sb.toString();
    }
}
