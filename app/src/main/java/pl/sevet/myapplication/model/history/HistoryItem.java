package pl.sevet.myapplication.model.history;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class HistoryItem {
    private Long itemId;
    private Long itemTypeId;
    private Date expDate;
    private Date creationDate;
    private ArrayList<HistoryTransaction> transactions;

    public HistoryItem() {
    }

    public HistoryItem(Long itemId, Long itemTypeId, Date expDate, Date creationDate, ArrayList<HistoryTransaction> transactions) {
        this.itemId = itemId;
        this.itemTypeId = itemTypeId;
        this.expDate = expDate;
        this.creationDate = creationDate;
        this.transactions = transactions;
    }



    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public ArrayList<HistoryTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<HistoryTransaction> transactions) {
        this.transactions = transactions;
    }
}
