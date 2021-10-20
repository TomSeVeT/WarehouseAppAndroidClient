package pl.sevet.myapplication.model.users;

public class UserAccess {
    private Long userId;
    private Long locationId;

    public UserAccess() {
    }

    public UserAccess(Long userId, Long locationId) {
        this.userId = userId;
        this.locationId = locationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
