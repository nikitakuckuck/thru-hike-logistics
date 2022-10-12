package learn.thruhike.models;

import java.math.BigDecimal;

public class FoodIdea {
    private int foodId;
    private int appUserId;
    private String foodName;
    private int calories;
    private BigDecimal price;
    private double weight;
    private boolean requiresStove;
    private String foodNotes;

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isRequiresStove() {
        return requiresStove;
    }

    public void setRequiresStove(boolean requiresStove) {
        this.requiresStove = requiresStove;
    }

    public String getFoodNotes() {
        return foodNotes;
    }

    public void setFoodNotes(String foodNotes) {
        this.foodNotes = foodNotes;
    }
}
