package pl.sevet.myapplication.model.items;

public class ItemGroup {
    private Long id;
    private String name;
    private Measurement measurement;
    private boolean expDate;

    public ItemGroup() {
    }

    public ItemGroup(String name, Measurement measurement, boolean expDate) {
        this.name = name;
        this.measurement = measurement;
        this.expDate = expDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public boolean isExpDate() {
        return expDate;
    }

    public void setExpDate(boolean expDate) {
        this.expDate = expDate;
    }
}
