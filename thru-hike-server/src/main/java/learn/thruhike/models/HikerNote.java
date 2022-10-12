package learn.thruhike.models;

public class HikerNote {
    private int noteId;
    private int appUserId;
    private NoteCategory category;
    private String content;
    private TrailSection trailSection;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public NoteCategory getCategory() {
        return category;
    }

    public void setCategory(NoteCategory category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TrailSection getTrailSection() {
        return trailSection;
    }

    public void setTrailSection(TrailSection trailSection) {
        this.trailSection = trailSection;
    }
}
