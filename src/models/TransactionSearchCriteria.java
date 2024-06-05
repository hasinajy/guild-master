package models;

import java.sql.Date;

public class TransactionSearchCriteria {
    private Integer transactionTypeId;
    private Date startDate;
    private Date endDate;
    private Integer itemId;
    private Integer playerId;

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

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }
}
