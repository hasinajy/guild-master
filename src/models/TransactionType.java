package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Postgres;

public class TransactionType {
    private int transactionTypeID;
    private char transactionTypeCode;
    private String name;

    // Constructors
    public TransactionType(int transactionTypeID, char transactionTypeCode, String name) {
        this.setTransactionTypeID(transactionTypeID);
        this.setTransactionTypeCode(transactionTypeCode);
        this.setName(name);
    }

    // Getters & Setters
    public int getTransactionTypeID() {
        return transactionTypeID;
    }

    public void setTransactionTypeID(int transactionTypeID) {
        this.transactionTypeID = transactionTypeID;
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

    // CRUD methods
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
                int transactionTypeID = rs.getInt("transaction_type_id");
                char transactionTypeCode = rs.getString("transaction_type_code").charAt(0);
                String name = rs.getString("name");

                data.add(new TransactionType(transactionTypeID, transactionTypeCode, name));
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
