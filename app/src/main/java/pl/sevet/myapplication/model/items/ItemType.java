package pl.sevet.myapplication.model.items;

import pl.sevet.myapplication.model.BaseModel;

public class ItemType implements BaseModel {

    private Long id;
    private Long groupId;
    private String name;
    private String description;

    public ItemType() {
    }

    public ItemType(Long groupId, String name, String description) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
