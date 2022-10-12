package learn.thruhike.models;

public class ResupplyItem {
    private int itemId;
    private int appUserId;
    private String itemName;
    private int itemQuantity;
    private TrailSection trailSection;
    private String storeName;
    private FoodIdea foodIdea;
    private boolean markAsRecurring;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public TrailSection getTrailSection() {
        return trailSection;
    }

    public void setTrailSection(TrailSection trailSection) {
        this.trailSection = trailSection;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public FoodIdea getFoodIdea() {
        return foodIdea;
    }

    public void setFoodIdea(FoodIdea foodIdea) {
        this.foodIdea = foodIdea;
    }

    public boolean isMarkAsRecurring() {
        return markAsRecurring;
    }

    public void setMarkAsRecurring(boolean markAsRecurring) {
        this.markAsRecurring = markAsRecurring;
    }
}
