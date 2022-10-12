package learn.thruhike.models;

public class Trail {
    private int trailId;
    private int appUserId;
    private String trailName;
    private String trailAbbreviation;

    public int getTrailId() {
        return trailId;
    }

    public void setTrailId(int trailId) {
        this.trailId = trailId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getTrailName() {
        return trailName;
    }

    public void setTrailName(String trailName) {
        this.trailName = trailName;
    }

    public String getTrailAbbreviation() {
        return trailAbbreviation;
    }

    public void setTrailAbbreviation(String trailAbbreviation) {
        this.trailAbbreviation = trailAbbreviation;
    }
}
