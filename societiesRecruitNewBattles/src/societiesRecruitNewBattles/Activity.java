package societiesRecruitNewBattles;

import java.util.ArrayList;

public class Activity implements Comparable {
    String activity_name;
    long set_up_time; // 时间戳
    String place_name;
    //    ArrayList<Community> communities;
    ArrayList<String> communities_name;  // 通过社团名检索到社团
    String instruction;  // 活动介绍
    String remarks;  // 备注
    String extra_data;  // 额外信息

    public Activity(String activity_name, long set_up_time, String place_name,
                    ArrayList<String> communities_name, String instruction, String remarks) {
        this.activity_name = activity_name;
        this.set_up_time = set_up_time;
        this.place_name = place_name;
        this.communities_name = new ArrayList<>(communities_name);
        this.instruction = instruction;
        this.remarks = remarks;
        this.extra_data = "";
    }

    public void setExtra_data(String extra_data) {
        this.extra_data = extra_data;
    }

    // 展示活动信息
    public void displayActivitiesData() {
        // 活动名称	开始时间	地点	主办社团	宣传标语	备注
        String community_name = "";
        for (int i = 0; i < communities_name.size() - 1; i++) {
            community_name += communities_name.get(i) + "、";
        }
        community_name += communities_name.get(communities_name.size() - 1);

        System.out.println("活动名称: " + activity_name +
                "\n开始时间: " + String.valueOf(set_up_time) +
                "\n地点: " + place_name +
                "\n主办社团: " + community_name +
                "\n宣传标语: " + instruction +
                "\n备注: " + remarks +
                "\n额外信息： " + extra_data);
    }

    public void addCommunityName(String community_name) {
        communities_name.add(community_name);
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public long getSet_up_time() {
        return set_up_time;
    }

    public String getPlace_name() {
        return place_name;
    }

//    public ArrayList<Community> getCommunities() {
//        return communities;
//    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getRemarks() {
        return remarks;
    }

    public int compareTo(Object o) {
        Activity other_publication = (Activity) o;
        if (set_up_time < other_publication.getSet_up_time())
            return -1;
        else if (set_up_time > other_publication.getSet_up_time())
            return 1;
        else
            return 0;
    }
}
