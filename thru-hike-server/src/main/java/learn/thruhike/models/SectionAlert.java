package learn.thruhike.models;

public class SectionAlert {
    private int alertId;
    private int appUserId;
    private int alertCategoryId;
    private AlertCategory alertCategory;
    private String alertContent;
    private int trailSectionId;

    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getAlertCategoryId() {
        return alertCategoryId;
    }

    public void setAlertCategoryId(int alertCategoryId) {
        this.alertCategoryId = alertCategoryId;
    }

    public AlertCategory getAlertCategory() {
        return alertCategory;
    }

    public void setAlertCategory(AlertCategory alertCategory) {
        this.alertCategory = alertCategory;
    }

    public String getAlertContent() {
        return alertContent;
    }

    public void setAlertContent(String alertContent) {
        this.alertContent = alertContent;
    }

    public int getTrailSectionId() {
        return trailSectionId;
    }

    public void setTrailSectionId(int trailSectionId) {
        this.trailSectionId = trailSectionId;
    }

}
