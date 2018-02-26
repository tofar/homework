package societiesRecruitNewBattles;

public class MyObject {
    private long currentTime;
    private boolean ifcalulate;
    public MyObject() {
        currentTime = System.currentTimeMillis();
        ifcalulate = false;
    }

    synchronized public void setCurrentTime() {
        currentTime = System.currentTimeMillis();

    }

    synchronized public void setIfcalulate(boolean ifcalulate) {
        this.ifcalulate = ifcalulate;
    }

    synchronized public boolean getIfcalulate() {
        return ifcalulate;
    }

    synchronized public long getCurrentTime() {
        return currentTime;
    }

}