package models;

import java.sql.Date;

import jakarta.servlet.http.HttpServletRequest;

public class TransactionSearchCriteria {
    private Integer transactionTypeId;
    private Date startDate;
    private Date endDate;
    private Integer itemId;
    private String characterName;

    // Constructors
    public TransactionSearchCriteria() {
    }

    public TransactionSearchCriteria(Integer transactionTypeId) {
        this();
        this.setTransactionTypeId(transactionTypeId);
    }

    // Getters & Setters
    public Integer getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Integer transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    /* ------------------------------ Class methods ----------------------------- */
    public static TransactionSearchCriteria getCriteriaFromRequest(HttpServletRequest req) {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();

        String transactionTypeIdParam = req.getParameter("transaction-type-id");
        String startDateParam = req.getParameter("start-date");
        String endDateParam = req.getParameter("end-date");
        String itemIdParam = req.getParameter("item-id");
        String characterNameParam = req.getParameter("character-name");

        if (transactionTypeIdParam != null && !transactionTypeIdParam.isEmpty()) {
            try {
                criteria.setTransactionTypeId(Integer.parseInt(transactionTypeIdParam));
            } catch (NumberFormatException e) {
                criteria.setTransactionTypeId(null);
            }
        }

        if (startDateParam != null && !startDateParam.isEmpty()) {
            try {
                criteria.setStartDate(Date.valueOf(startDateParam));
            } catch (IllegalArgumentException e) {
                criteria.setStartDate(null);
            }
        }

        if (endDateParam != null && !endDateParam.isEmpty()) {
            try {
                criteria.setEndDate(Date.valueOf(endDateParam));
            } catch (IllegalArgumentException e) {
                criteria.setEndDate(null);
            }
        }

        if (itemIdParam != null && !itemIdParam.isEmpty()) {
            try {
                criteria.setItemId(Integer.parseInt(itemIdParam));
            } catch (NumberFormatException e) {
                criteria.setItemId(null);
            }
        }

        if (characterNameParam != null && !characterNameParam.isEmpty()) {
            criteria.setCharacterName(characterNameParam.trim());
        }

        return criteria;
    }
}
