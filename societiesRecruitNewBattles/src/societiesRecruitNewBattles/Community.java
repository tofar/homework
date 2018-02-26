package societiesRecruitNewBattles;

import java.util.ArrayList;

public class Community implements Comparable {
    Member leader;
    ArrayList<Member> members;
    String community_name;
    long set_up_time; // 时间戳
    String tele_num;
    String email;
    ArrayList<String> activities_name;
    String instruction;

    public Community(String community_name, Member leader, String tele_num, String email,
                     ArrayList<String> activities_name, String instruction, long set_up_time) {
        this.leader = leader;
        this.community_name = community_name;
        this.tele_num = tele_num;
        this.email = email;
        this.instruction = instruction;
        this.set_up_time = set_up_time;
        this.activities_name = new ArrayList<>(activities_name);
        members = new ArrayList<>();
        members.add(leader);
    }

    // 展示社团信息
    public void displayCommunityData() {
        // 社团名	创建时间	负责人	联系电话	邮箱	社团成员	举办的活动	简介
        String member_name = "";
        for (int i = 0; i < members.size() - 1; i++) {
            member_name += members.get(i).getName() + "、";
        }
        member_name += members.get(members.size() - 1).getName();

        String activity_name = "";
        for (int i = 0; i < activities_name.size() - 1; i++) {
            activity_name += activities_name.get(i) + "、";
        }
        activity_name += activities_name.get(activities_name.size() - 1);

        System.out.println("社团名: " + community_name +
                "\n创建时间: " + String.valueOf(set_up_time) +
                "\n负责人: " + leader.getName() +
                "\n联系电话: " + tele_num +
                "\n邮箱: " + email +
                "\n社团成员: " + member_name +
                "\n举办的活动: " + activity_name +
                "\n简介: " + instruction);
    }

    public void addActivityName(Activity activity) {
        activities_name.add(activity.getActivity_name());
    }

    public void addActivityName(String activity_name) {
        activities_name.add(activity_name);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public String getCommunity_name() {
        return community_name;
    }

    public String getInstruction() {
        return instruction;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public String getEmail() {
        return email;
    }

    public String gettele_num() {
        return tele_num;
    }

    public long getSet_up_time() {
        return set_up_time;
    }

    public int compareTo(Object o) {
        Community other_publication = (Community) o;
        if (set_up_time < other_publication.getSet_up_time())
            return -1;
        else if (set_up_time > other_publication.getSet_up_time())
            return 1;
        else
            return 0;
    }


}
