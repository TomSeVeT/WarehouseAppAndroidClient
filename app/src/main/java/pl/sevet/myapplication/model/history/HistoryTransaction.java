package pl.sevet.myapplication.model.history;

import pl.sevet.myapplication.model.TransactionType;

import java.util.Date;

public class HistoryTransaction {
    private TransactionType transactionType;
    private Long userId;
    private float quantityBefore;
    private float quantityAfter;
    private Long locIdBefore;
    private Long locIdAfter;
    private Date transactionDate;

    public HistoryTransaction() {
    }

    public HistoryTransaction(TransactionType transactionType, Long userId, float quantityBefore, float quantityAfter, Long locIdBefore, Long locIdAfter, Date transactionDate) {
        this.transactionType = transactionType;
        this.userId = userId;
        this.quantityBefore = quantityBefore;
        this.quantityAfter = quantityAfter;
        this.locIdBefore = locIdBefore;
        this.locIdAfter = locIdAfter;
        this.transactionDate = transactionDate;
    }


    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public float getQuantityBefore() {
        return quantityBefore;
    }

    public void setQuantityBefore(float quantityBefore) {
        this.quantityBefore = quantityBefore;
    }

    public float getQuantityAfter() {
        return quantityAfter;
    }

    public void setQuantityAfter(float quantityAfter) {
        this.quantityAfter = quantityAfter;
    }

    public Long getLocIdBefore() {
        return locIdBefore;
    }

    public void setLocIdBefore(Long locIdBefore) {
        this.locIdBefore = locIdBefore;
    }

    public Long getLocIdAfter() {
        return locIdAfter;
    }

    public void setLocIdAfter(Long locIdAfter) {
        this.locIdAfter = locIdAfter;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
