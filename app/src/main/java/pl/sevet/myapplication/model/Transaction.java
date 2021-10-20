package pl.sevet.myapplication.model;

import java.util.Date;

public class Transaction implements BaseModel{

    private TransactionType transactionType;
    private Long itemId;
    private Long itemTypeId;
    private Long beforeLocationId;
    private Long afterLocationId;
    private float beforeQuantity;
    private float afterQuantity;
    private Long userId;
    private Date transactionDate;
    private Date expDate;

    public Transaction() {
    }


    public Transaction(TransactionType transactionType, Long itemId, Long itemTypeId, Long beforeLocationId, Long afterLocationId, float beforeQuantity, float afterQuantity, Long userId, Date transactionDate, Date expDate) {
        this.transactionType = transactionType;
        this.itemId = itemId;
        this.itemTypeId = itemTypeId;
        this.beforeLocationId = beforeLocationId;
        this.afterLocationId = afterLocationId;
        this.beforeQuantity = beforeQuantity;
        this.afterQuantity = afterQuantity;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.expDate = expDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getBeforeLocationId() {
        return beforeLocationId;
    }

    public void setBeforeLocationId(Long beforeLocationId) {
        this.beforeLocationId = beforeLocationId;
    }

    public Long getAfterLocationId() {
        return afterLocationId;
    }

    public void setAfterLocationId(Long afterLocationId) {
        this.afterLocationId = afterLocationId;
    }

    public float getBeforeQuantity() {
        return beforeQuantity;
    }

    public void setBeforeQuantity(float beforeQuantity) {
        this.beforeQuantity = beforeQuantity;
    }

    public float getAfterQuantity() {
        return afterQuantity;
    }

    public void setAfterQuantity(float afterQuantity) {
        this.afterQuantity = afterQuantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }
}
