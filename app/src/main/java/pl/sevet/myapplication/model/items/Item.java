package pl.sevet.myapplication.model.items;

import pl.sevet.myapplication.model.BaseModel;

import java.util.Date;

public class Item implements BaseModel {
    private Long id;
    private String description;
    private float quantity;
    private Long typeId;
    private Long locationId;
    private Date expDate;
    private Date creationDate;

    public Item() {
    }

    public Item(String description, float quantity, Long typeId, Long locationId, Date expDate, Date creationDate) {
        this.description = description;
        this.quantity = quantity;
        this.typeId = typeId;
        this.locationId = locationId;
        this.expDate = expDate;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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

}
