package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Postgres;

public class TransactionType {
    private int transactionTypeId;
    private char transactionTypeCode;
    private String name;

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
    public static ArrayList<TransactionType> getAll() throws ClassNotFoundException, SQLException {
        ArrayList<TransactionType> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM transaction_type";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int transactionTypeId = rs.getInt("transaction_type_id");
                char transactionTypeCode = rs.getString("transaction_type_code").charAt(0);
                String name = rs.getString("name");

                data.add(new TransactionType(transactionTypeId, transactionTypeCode, name));
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
}
