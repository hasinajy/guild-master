package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.PostgresResources;

public class TransactionType {
    private int transactionTypeId;
    private char transactionTypeCode;
    private String name;

    // Queries
    private static final String READ_QUERY = "SELECT\r\n" + //
            "    transaction_type.transaction_type_id AS \"transaction_type.transaction_type_id\",\r\n" + //
            "    transaction_type.transaction_type_code AS \"transaction_type.transaction_type_code\",\r\n" + //
            "    transaction_type.name AS \"transaction_type.name\"\r\n" + //
            "FROM\r\n" + //
            "    transaction_type";

    /* ------------------------------ Constructors ------------------------------ */
    public TransactionType() {
    }

    public TransactionType(int transactionTypeId, char transactionTypeCode, String name) {
        this.setTransactionTypeId(transactionTypeId);
        this.setTransactionTypeCode(transactionTypeCode);
        this.setName(name);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public char getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(char transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* ---------------------------- Database methods ---------------------------- */
    public static List<TransactionType> getAll() throws ClassNotFoundException, SQLException {
        List<TransactionType> data = new ArrayList<>();
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(TransactionType.getReadQuery());
            pg.executeQuery(false);

            data = TransactionType.getTableInstance(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return data;
    }

    /* ----------------------------- Utility methods ---------------------------- */
    // Instantiation methods
    private static TransactionType createTransactionTypeFromResultSet(PostgresResources pg) throws SQLException {
        TransactionType transactionType = new TransactionType();

        transactionType.setTransactionTypeId(pg.getInt("transaction_type.transaction_type_id"));
        transactionType.setTransactionTypeCode(pg.getString("transaction_type.transaction_type_code").charAt(0));
        transactionType.setName(pg.getString("transaction_type.name"));

        return transactionType;
    }

    private static List<TransactionType> getTableInstance(PostgresResources pg) throws SQLException {
        List<TransactionType> transactionTypeList = new ArrayList<>();

        while (pg.next()) {
            TransactionType transactionType = TransactionType.createTransactionTypeFromResultSet(pg);
            transactionTypeList.add(transactionType);
        }

        return transactionTypeList;
    }

    // Read
    private static String getReadQuery() {
        return TransactionType.READ_QUERY;
    }
}
