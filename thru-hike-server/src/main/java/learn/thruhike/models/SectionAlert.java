package learn.thruhike.models;

public class SectionAlert {
    private int alertId;
    private int appUserId;
    private AlertCategory alertCategory;
    private String alertContent;
    private TrailSection trailSection;
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

    public TrailSection getTrailSection() {
        return trailSection;
    }

    public void setTrailSection(TrailSection trailSection) {
        this.trailSection = trailSection;
    }

    public boolean isFutureSections() {
        return futureSections;
    }

    public void setFutureSections(boolean futureSections) {
        this.futureSections = futureSections;
    }
}
