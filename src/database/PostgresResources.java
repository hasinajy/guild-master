package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
        setStmtValues(classList, values, null);
    }

    public void setStmtValues(Class<?>[] classList, Object[] values, int[] indexList)
            throws IllegalArgumentException, SQLException {
        if (classList.length != values.length) {
            throw new IllegalArgumentException("Class list and values should have the same length");
        }

        int stmtIndex;

        for (int i = 0; i < classList.length; i++) {
            stmtIndex = (indexList == null) ? i + 1 : indexList[i];

            this.setStmtValue(classList[i], stmtIndex, values[i]);
        }
    }

        }
    }

    public void setStmtValues(Class<?> clazz, Object[] values) throws IllegalArgumentException, SQLException {
        List<Class<?>> classList = new ArrayList<>();

        for (int i = 0; i < values.length; i++) {
            classList.add(clazz);
        }

        this.setStmtValues(classList.toArray(new Class<?>[0]), values);
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
