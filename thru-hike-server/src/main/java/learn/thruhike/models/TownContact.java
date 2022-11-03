package learn.thruhike.models;

public class TownContact {
    private int townContactId;
    private int appUserId;
    private String category;
    private String content;
    private String otherNotes;
    private int trailSectionId;

    public int getTownContactId() {
        return townContactId;
    }

    public void setTownContactId(int townContactId) {
        this.townContactId = townContactId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    public void setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
    }

    public int getTrailSectionId() {
        return trailSectionId;
    }

    public void setTrailSectionId(int trailSectionId) {
        this.trailSectionId = trailSectionId;
    }
}
