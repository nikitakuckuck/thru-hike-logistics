package learn.thruhike.models;

public class SectionAlert {
    private int alertId;
    private int appUserId;
    private AlertCategory alertCategory;
    private String alertContent;
    private int trailSectionId;
    private boolean futureSections;

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

    public boolean isFutureSections() {
        return futureSections;
    }

    public void setFutureSections(boolean futureSections) {
        this.futureSections = futureSections;
    }
}
