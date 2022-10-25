package learn.thruhike.models;

public class ExitItem {
    private int exitItemId;
    private int appUserId;
    private String exitItemName;
    private boolean goodToGo;

    public int getExitItemId() {
        return exitItemId;
    }

    public void setExitItemId(int exitItemId) {
        this.exitItemId = exitItemId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getExitItemName() {
        return exitItemName;
    }

    public void setExitItemName(String exitItemName) {
        this.exitItemName = exitItemName;
    }

    public boolean isGoodToGo() {
        return goodToGo;
    }

    public void setGoodToGo(boolean goodToGo) {
        this.goodToGo = goodToGo;
    }
}
