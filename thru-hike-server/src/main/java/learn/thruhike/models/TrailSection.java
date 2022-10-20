package learn.thruhike.models;

public class TrailSection {

    private int trailSectionId;
    private int appUserId;
    private int trailId;
    private Trail trail;
    private String sectionStart;
    private String sectionEnd;
    private double latitude;
    private double longitude;
    private int sectionLength;
    private int sectionDays;
    private boolean upcoming;

    public int getTrailSectionId() {
        return trailSectionId;
    }

    public void setTrailSectionId(int trailSectionId) {
        this.trailSectionId = trailSectionId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getTrailId() {
        return trailId;
    }

    public void setTrailId(int trailId) {
        this.trailId = trailId;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }

    public String getSectionStart() {
        return sectionStart;
    }

    public void setSectionStart(String sectionStart) {
        this.sectionStart = sectionStart;
    }

    public String getSectionEnd() {
        return sectionEnd;
    }

    public void setSectionEnd(String sectionEnd) {
        this.sectionEnd = sectionEnd;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getSectionLength() {
        return sectionLength;
    }

    public void setSectionLength(int sectionLength) {
        this.sectionLength = sectionLength;
    }

    public int getSectionDays() {
        return sectionDays;
    }

    public void setSectionDays(int sectionDays) {
        this.sectionDays = sectionDays;
    }

    public boolean isUpcoming() {
        return upcoming;
    }

    public void setUpcoming(boolean upcoming) {
        this.upcoming = upcoming;
    }
}
