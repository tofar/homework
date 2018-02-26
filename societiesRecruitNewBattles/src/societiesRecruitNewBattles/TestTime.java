package societiesRecruitNewBattles;

public class TestTime extends Thread {
    private MyObject myObject;
    public  TestTime(MyObject myObject) {
        this.myObject = myObject;
    }

    public void run() {
        // 时间触发阀值
        long timeThreshold = 15000;
        while (true) {

            // 鉴于演示效果，60秒太长，所以将60秒缩短为15秒
            if (myObject.getIfcalulate() && (System.currentTimeMillis()-myObject.getCurrentTime() > timeThreshold)) {
                // 10个社团
                try {
                    System.out.println();
                    for (int i = 0; i < 10; i++) {
                        System.out.println("社团展示： " + i);
                        Thread.sleep(1000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
