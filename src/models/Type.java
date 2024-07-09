package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Postgres;
import database.PostgresResources;

public class Type {
    private int typeId;
    private String name;
    private String imgPath;

    // Queries
    private static final String CREATE_QUERY = "INSERT INTO type(name, img_path) VALUES (?, ?)";

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

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM type WHERE type_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, typeId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String imgPath = rs.getString("img_path");

                type = new Type(typeId, name, imgPath);
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

        return type;
    }

    public static List<Type> getAll() throws ClassNotFoundException, SQLException {
        List<Type> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM type";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int typeId = rs.getInt("type_id");
                String name = rs.getString("name");
                String imgPath = rs.getString("img_path");

                data.add(new Type(typeId, name, imgPath));
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

    // Update
    public void update() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Postgres.getInstance().getConnection();

            if (this.getImgPath().equals("type/")) {
                String query = "UPDATE type SET name = ? WHERE type_id = ?";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, this.getName());
                stmt.setInt(2, this.getTypeId());
            } else {
                String query = "UPDATE type SET name = ?, img_path = ? WHERE type_id = ?";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, this.getName());
                stmt.setString(2, this.getImgPath());
                stmt.setInt(3, this.getTypeId());
            }

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
    protected static Type getRowInstance(PostgresResources pg) throws SQLException {
        Type type = new Type();

        type.setTypeId(pg.getInt("type.type_id"));
        type.setName(pg.getString("type.name"));
        type.setImgPath(pg.getString("type.img_path"));

        return type;
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
}
