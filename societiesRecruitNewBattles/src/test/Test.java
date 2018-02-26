package test;

import societiesRecruitNewBattles.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        ArrayList<String[]> activity_data =
                new ArrayList<>(ReadCSV.readCSV("src/社团及活动信息表/活动表.csv", 2, "gb2312"));

        // 没有用户就算了
        //    HashMap<Integer, String[]> user_data =
        //  new HashMap<>(ReadCSV.readCSV("src/社团及活动信息表/用户表.csv", 2, "gb2312"));

        ArrayList<String[]> community_data =
                new ArrayList<>(ReadCSV.readCSV("src/社团及活动信息表/社团表.csv", 2, "gb2312"));
        String password = "123456789";

        // 初始化活动信息和社团信息
        ArrayList<Community> communities = init_Communities(community_data);
        ArrayList<Activity> activities = init_Activities(activity_data);

        activity_data.clear();
        community_data.clear();

        MyObject myObject = new MyObject();
        // 初始化展板系统
        DisplayBoard displayBoard = new DisplayBoard(communities, activities, password, myObject);
        TestTime testTime = new TestTime(myObject);
        // 展示
        displayBoard.start();
        testTime.start();

    }

    // 得到时间戳    原格式 如： 2015.1.1
    public static long getTime(String user_time) {
        String re_time = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();

            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.parseLong(re_time);
    }

    // 初始化活动
    public static ArrayList<Activity> init_Activities(ArrayList<String[]> activities_data) {
        ArrayList<Activity> activities = new ArrayList<>();
        // 活动名称	开始时间	地点	主办社团	宣传标语	备注
        for (String[] data : activities_data) {
            // String activity_name, int set_up_time, String place_name,
            // ArrayList<Community> communities  String instruction, String remarks
            if (data.length >= 5) {
                ArrayList<String> communities_name = new ArrayList<>();

                String[] community_name = data[3].split("、");
                for (String st : community_name) {
                    communities_name.add(st);
                }

                if (data.length > 5) {
                    activities.add(new Activity(data[0], getTime(data[1]), data[2], communities_name, data[4], data[5]));

                } else {

                    activities.add(new Activity(data[0], getTime(data[1]), data[2], communities_name, data[4], ""));

                }
            }
        }
        return activities;
    }

    // 初始化社团
    public static ArrayList<Community> init_Communities(ArrayList<String[]> community_data) {
        // 社团名	创建时间	负责人	联系电话	邮箱	社团成员	举办的活动	简介
        ArrayList<Community> communities = new ArrayList<>();
        int i = 0;
        for (String[] data : community_data) {
            // String community_name, Member leader, String tell_num, String email,
            // String instruction, int set_up_time
            String[] activities_name = data[6].split("、");
            ArrayList<String> activities_name2 = new ArrayList<>();
            for (String st : activities_name) {
                activities_name2.add(st);
            }

            communities.add(new Community(data[0], new Member(data[2]), data[3], data[4], activities_name2, data[7], getTime(data[1])));
            String[] members_name = data[5].split("、");
            for (String st : members_name) {
                if (!st.equals(data[2])) {
                    communities.get(i).addMember(new Member(st));
                }
            }
            i++;
        }

        return communities;
    }

}
