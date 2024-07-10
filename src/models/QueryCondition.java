package models;

import java.util.ArrayList;
import java.util.List;

public class QueryCondition {
    private StringBuilder query;
    private List<Class<?>> classList;
    private List<Object> parameters;

    /* ------------------------------ Constructors ------------------------------ */
    public QueryCondition(String baseQuery) {
        this.query = new StringBuilder(baseQuery);
        this.classList = new ArrayList<>();
        this.parameters = new ArrayList<>();
    }

    /* --------------------------------- Getters -------------------------------- */
    public String getQuery() {
        return query.toString();
    }

    public Class<?>[] getClassList() {
        return classList.toArray(new Class<?>[0]);
    }

    public Object[] getParameters() {
        return parameters.toArray();
    }

    /* ------------------------------ Class methods ----------------------------- */
    public void addCondition(String condition, Class<?> type, Object value) {
        if (value != null) {
            query.append(condition);
            classList.add(type);
            parameters.add(value);
        }
    }

}
