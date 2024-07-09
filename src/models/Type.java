package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Postgres;
import database.PostgresResources;
import utils.NameChecker;

public class Type {
    private int typeId;
    private String name;
    private String imgPath;

    // Queries
    private static final String CREATE_QUERY = "INSERT INTO type(name, img_path) VALUES (?, ?)";
    private static final String READ_QUERY = "SELECT * FROM type";

    /* ------------------------------ Constructors ------------------------------ */
    public Type() {
    }

    public Type(int typeId) {
        this.setTypeId(typeId);
        this.setName("Default Type");
    }

    public Type(int typeId, String name, String imgPath) {
        this.setTypeId(typeId);
        this.setName(name);
        this.setImgPath(imgPath);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /* ---------------------------- Database methods ---------------------------- */
    // Create
    public void create() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Type.getCreateQuery());
            pg.setStmtValues(Type.getCreateClassList(), this.getCreateValues());
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    // Read
    public static Type getById(int typeId) throws ClassNotFoundException, SQLException {
        Type type = null;
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Type.getReadQuery(true));
            pg.setStmtValues(int.class, new Object[] { typeId });
            pg.executeQuery(false);

            type = Type.createTypeFromResultSet(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return type;
    }

    public static List<Type> getAll() throws ClassNotFoundException, SQLException {
        List<Type> data = new ArrayList<>();
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Type.getReadQuery(false));
            pg.executeQuery(false);

            data = Type.getTableInstance(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return data;
    }

    // Update
    public void update() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            // TODO: Extract methods for the classList and values
            
            String query = null;
            Class<?>[] classList = null;
            Object[] values = null;

            if (NameChecker.isNewImgPath(this.getImgPath(), "type")) {
                query = "UPDATE type SET name = ?, img_path = ? WHERE type_id = ?";
                classList = new Class[] { String.class, String.class, int.class };
                values = new Object[] { this.getName(), this.getImgPath(), this.getTypeId() };
            } else {
                query = "UPDATE type SET name = ? WHERE type_id = ?";
                classList = new Class[] { String.class, int.class };
                values = new Object[] { this.getName(), this.getTypeId() };
            }

            pg.initResources(query);
            pg.setStmtValues(classList, values);
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    public void update(int typeId) throws ClassNotFoundException, SQLException {
        this.setTypeId(typeId);
        this.update();
    }

    // Delete
    public static void deleteById(int typeId) throws ClassNotFoundException, SQLException {
        new Type(typeId).delete();
    }

    public void delete() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM type WHERE type_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.typeId);
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

    public static void delete(int typeId) throws ClassNotFoundException, SQLException {
        new Type(typeId).delete();
    }

    /* ----------------------------- Utility methods ---------------------------- */
    // Instantiation methods
    protected static Type createTypeFromResultSet(PostgresResources pg) throws SQLException {
        Type type = new Type();

        type.setTypeId(pg.getInt("type.type_id"));
        type.setName(pg.getString("type.name"));
        type.setImgPath(pg.getString("type.img_path"));

        return type;
    }

    protected static Type getRowInstance(PostgresResources pg) throws SQLException {
        Type type = null;

        if (pg.next()) {
            type = Type.createTypeFromResultSet(pg);
        }

        return type;
    }

    protected static List<Type> getTableInstance(PostgresResources pg) throws SQLException {
        List<Type> typeList = new ArrayList<>();

        while (pg.next()) {
            Type type = Type.createTypeFromResultSet(pg);
            typeList.add(type);
        }

        return typeList;
    }

    // Create
    private static String getCreateQuery() {
        return Type.CREATE_QUERY;
    }

    private static Class<?>[] getCreateClassList() {
        return new Class[] {
                String.class,
                String.class
        };
    }

    private Object[] getCreateValues() {
        return new Object[] {
                this.getName(),
                this.getImgPath()
        };
    }

    // Update
    private static String getReadQuery(boolean hasWhere) {
        StringBuilder sb = new StringBuilder(Type.READ_QUERY);

        if (hasWhere) {
            sb.append(" WHERE type_id = ?");
        }

        return sb.toString();
    }
}
