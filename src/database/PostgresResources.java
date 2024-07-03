package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresResources {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    /* ------------------------------ Constructors ------------------------------ */
    public PostgresResources() {
        this.conn = null;
        this.stmt = null;
        this.rs = null;
    }

    /* --------------------------- Getters and setters -------------------------- */
    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public PreparedStatement getStmt() {
        return stmt;
    }

    public void setStmt(PreparedStatement stmt) {
        this.stmt = stmt;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    /* ------------------------------ Class methods ----------------------------- */
    public void initResources(String query) throws ClassNotFoundException, SQLException {
        this.setConn(Postgres.getInstance().getConnection());
        this.setStmt(conn.prepareStatement(query));
    }

    public void setStmtValues(Class<?>[] classList, Object[] values) throws IllegalArgumentException, SQLException {
        if (classList.length != values.length) {
            throw new IllegalArgumentException("Class list and values should have the same length");
        }

        for (int i = 0; i < classList.length; i++) {
            Class<?> clazz = classList[i];
            Object value = values[i];
            int stmtIndex = i + 1;

            if (clazz == int.class || clazz == Integer.class) {
                this.getStmt().setInt(stmtIndex, (int) value);
            } else if (clazz == float.class || clazz == Float.class) {
                this.getStmt().setFloat(stmtIndex, (float) value);
            } else if (clazz == double.class || clazz == Double.class) {
                this.getStmt().setDouble(stmtIndex, (double) value);
            } else if (clazz == String.class) {
                this.getStmt().setString(stmtIndex, (String) value);
            } else if (clazz == Date.class) {
                this.getStmt().setDate(stmtIndex, (Date) value);
            }
        }
    }

    public void executeQuery(boolean isUpdate) throws UnsupportedOperationException, SQLException {
        if (this.getStmt() == null) {
            throw new UnsupportedOperationException("No query found to be executed");
        }

        this.getConn().setAutoCommit(false);

        if (isUpdate) {
            this.getStmt().executeUpdate();
        } else {
            this.setRs(this.getStmt().executeQuery());
        }

        this.getConn().commit();
    }

    public void rollback() throws SQLException {
        if (this.getConn() != null) {
            this.getConn().rollback();
        }
    }

    public void closeResources() throws SQLException {
        if (this.getRs() != null) {
            this.getRs().close();
        }

        if (this.getStmt() != null) {
            this.getStmt().close();
        }

        if (this.getConn() != null) {
            this.getConn().close();
        }
    }
}
