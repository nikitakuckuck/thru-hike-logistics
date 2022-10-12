package learn.thruhike.models;

public class SectionAlert {
    private int alertId;
    private int appUserId;
    private String alertCategory;
    private String alert_content;
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

    public String getAlertCategory() {
        return alertCategory;
    }

    public void setAlertCategory(String alertCategory) {
        this.alertCategory = alertCategory;
    }

    public String getAlert_content() {
        return alert_content;
    }

    public void setAlert_content(String alert_content) {
        this.alert_content = alert_content;
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
