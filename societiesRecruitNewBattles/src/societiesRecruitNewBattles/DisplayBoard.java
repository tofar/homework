package societiesRecruitNewBattles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayBoard extends Thread {
    ArrayList<Community> communities;
    ArrayList<Activity> activities;
    String password;

    MyObject myObject;

    public DisplayBoard(ArrayList<Community> communities, ArrayList<Activity> activities, String password, MyObject myObject) {
        this.communities = new ArrayList<>(communities);
        this.activities = new ArrayList<>(activities);
        this.password = password;
        this.myObject = myObject;
    }


    // 展示
    @Override
    public void run() {
        boolean is_break = false;

        while (true) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("规则：显示所有社团列表请输入 1, 显示所有活动的活动列表请输入 2, 进入管理系统请输入 m, 进入搜索系统请输入s, 退出系统请输入 q ");
            System.out.print("请输入: ");

            myObject.setCurrentTime();
            myObject.setIfcalulate(true);
            String first = scanner.nextLine();
            myObject.setIfcalulate(false);

            // 社团列表
            if (first.equals("1")) {
                int i = 1;
                for (Community community : communities) {
                    System.out.println(String.valueOf(i) + ". " + community.getCommunity_name());
                    i++;
                }
                System.out.print("\n规则：查看社团详情请输入社团代号, 返回上一级请输入 b, 退出系统请输入 q");
                while (true) {

                    myObject.setCurrentTime();
                    myObject.setIfcalulate(true);
                    System.out.print("请输入: ");
                    String second = scanner.nextLine();
                    myObject.setIfcalulate(false);

                    Pattern pattern = Pattern.compile("[0-9]*");
                    Matcher isNum = pattern.matcher(second);
                    if (isNum.matches()) {
                        if (Integer.parseInt(second) <= communities.size() && Integer.parseInt(second) >= 1) {
                            communities.get(Integer.parseInt(second) - 1).displayCommunityData();
                        } else {
                            System.out.println("请输入正确指令");
                        }
                    } else {
                        if (second.equals("b")) {
                            break;
                        } else {
                            if (second.equals("q")) {
                                is_break = true;
                                break;
                            } else {
                                System.out.println("请输入正确指令");
                            }
                        }
                    }
                }
                if (is_break) {
                    break;
                }
            } else {
                // 活动列表
                if (first.equals("2")) {
                    int i = 1;
                    for (Activity activity : activities) {
                        System.out.println(String.valueOf(i) + ". " + activity.getActivity_name());
                        i++;
                    }
                    System.out.print("\n规则：查看活动详情请输入活动代号, 返回上一级请输入 b, 退出系统请输入 q");
                    while (true) {

                        myObject.setCurrentTime();
                        myObject.setIfcalulate(true);
                        System.out.print("请输入: ");
                        String second = scanner.nextLine();
                        myObject.setIfcalulate(false);

                        Pattern pattern = Pattern.compile("[0-9]*");
                        Matcher isNum = pattern.matcher(second);
                        if (isNum.matches()) {
                            if (Integer.parseInt(second) <= activities.size() && Integer.parseInt(second) >= 1) {
                                activities.get(Integer.parseInt(second) - 1).displayActivitiesData();
                            } else {
                                System.out.println("请输入正确指令");
                            }
                        } else {
                            if (second.equals("b")) {
                                break;
                            } else {
                                if (second.equals("q")) {
                                    is_break = true;
                                    break;
                                } else {
                                    System.out.println("请输入正确指令");
                                }
                            }
                        }
                    }
                    if (is_break) {
                        break;
                    }
                } else {
                    // 管理系统
                    if (first.equals("m")) {
                        System.out.println("规则: 输入密码请先按 1, 退出请按 2");
                        while (true) {

                            myObject.setCurrentTime();
                            myObject.setIfcalulate(true);
                            String second = scanner.nextLine();
                            myObject.setIfcalulate(false);

                            if (second.equals("1")) {
                                System.out.print("请输入管理系统密码： ");

                                myObject.setCurrentTime();
                                myObject.setIfcalulate(true);
                                String pw = scanner.nextLine();
                                myObject.setIfcalulate(false);

                                getIntoManageBord(pw);
                                break;
                            } else {
                                if (second.equals("2")) {
                                    break;
                                } else {
                                    System.out.println("请输入正确指令");
                                }
                            }
                        }
                    } else {
                        if (first.equals("q")) {
                            {
                                is_break = true;
                            }
                        } else {
                            if (first.equals("s")) {
                                while (true) {
                                    System.out.println("搜索社团请输入1, 搜索活动请输入2, 退出搜索系统请输入q ");

                                    myObject.setCurrentTime();
                                    myObject.setIfcalulate(true);
                                    System.out.print("请输入: ");
                                    String second = scanner.nextLine();
                                    myObject.setIfcalulate(false);

                                    if (second.equals("1")) {
                                        System.out.println("请输入社团名称: ");

                                        myObject.setCurrentTime();
                                        myObject.setIfcalulate(true);
                                        System.out.print("请输入: ");
                                        String third = scanner.nextLine();
                                        myObject.setIfcalulate(false);

//                                        Community community = getCommunityByName(third);
//
//                                        if (community != null) {
//                                            community.displayCommunityData();
//                                        } else {
//                                            System.out.println("没有找到和此名字相关的社团");
//                                        }

                                        ArrayList<Community> result = getCommunitiesByName(third);
                                        if (result.size() == 0) {
                                            System.out.println("没有找到和此名字相关的社团");
                                        } else {
                                            for (Community community : result) {
                                                community.displayCommunityData();
                                                System.out.println();
                                            }
                                        }


                                    } else {
                                        if (second.equals("2")) {
                                            System.out.println("请输入活动名称: ");

                                            myObject.setCurrentTime();
                                            myObject.setIfcalulate(true);
                                            System.out.print("请输入: ");
                                            String third = scanner.nextLine();
                                            myObject.setIfcalulate(false);

//                                            Activity activity = getActivityByName(third);
//                                            if (activity != null) {
//                                                activity.displayActivitiesData();
//                                            } else {
//                                                System.out.println("没有找到和此名字相关的活动");
//
//                                            }

                                            ArrayList<Community> result = getCommunitiesByName(third);
                                            if (result.size() == 0) {
                                                System.out.println("没有找到和此名字相关的活动");
                                            } else {
                                                for (Community community: result) {
                                                    community.displayCommunityData();
                                                    System.out.println();
                                                }
                                            }


                                        } else {
                                            if (second.equals("q")) {
                                                break;
                                            } else {
                                                System.out.println("请输入正确指令");
                                            }
                                        }
                                    }
                                }

                            } else {
                                System.out.println("请输入正确指令");
                            }
                        }
                    }
                }
            }

            // 判断是否退出系统
            if (is_break) {
                break;
            }
        }
        System.out.println("Bye");
    }

    public boolean changePassword(String origin_password, String new_password) {
        if (origin_password.equals(password)) {
            this.password = new_password;
            return true;
        } else {
            return false;
        }
    }

    // 进入管理系统
    public void getIntoManageBord(String pw) {
        if (pw.equals(password)) {
//            管理员可以
//            查看每个社团的所有成员列表,还可以按照不同条件(开展活动次数、活动规模
//                    (人数))对社团进行排序,管理员可以按照学校的建议设置排序规则。管理员
//                    还需要对所有的活动进行信息汇总,除了上面介绍的基本信息外,还需根据活动
//            的具体类型添加补充信息,比如讲座类活动需列出主讲人(姓名、单位),比赛
//            类(比如歌唱大赛)要列出获奖人名单,等等
            // 首先展示活动和社团
            System.out.println("社团信息展示: ");
            int i = 1;
            for (Community community : communities) {
                System.out.println(String.valueOf(i) + ". " + community.getCommunity_name());
                i++;
            }
            i = 1;
            System.out.println("\n\n活动信息展示: ");
            for (Activity activity : activities) {
                System.out.println(String.valueOf(i) + ". " + activity.getActivity_name());
                i++;
            }
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // 成员列表已经显示，活动信息已显示，所以不用写了
                System.out.println("\n\n规则：设置社团排序请输入1， 设置活动排序请输入2，添加活动额外信息请先输入3，" +
                        "退出管理系统请输入q");

                myObject.setCurrentTime();
                myObject.setIfcalulate(true);
                String third = scanner.nextLine();
                myObject.setIfcalulate(false);

                if (third.equals("1")) {
                    System.out.println("");
                    System.out.println("将社团按照建立时间排序请输入 0, 退出输入请输入q, 手动输入顺序请输入社团标号并以空格间隔： ");

                    myObject.setCurrentTime();
                    myObject.setIfcalulate(true);
                    String fourth = scanner.nextLine();
                    myObject.setIfcalulate(false);

                    if (fourth.equals("0")) {
                        Collections.sort(communities);
                    } else {
                        if (!fourth.equals("q")) {

                            String[] sorted_num = fourth.split(" ");
                            boolean is_match = true;
                            ArrayList<Community> temp_communities = new ArrayList<>();
                            for (String num : sorted_num) {
                                // 正则匹配
                                Pattern pattern = Pattern.compile("[0-9]*");
                                Matcher isNum = pattern.matcher(num);
                                if (isNum.matches()) {
                                    if (Integer.parseInt(num) <= communities.size() && Integer.parseInt(num) >= 1) {
                                        temp_communities.add(communities.get(Integer.parseInt(num) - 1));
                                    } else {
                                        System.out.println("输入标号存在问题");
                                        is_match = false;
                                        break;
                                    }
                                } else {
                                    System.out.println("输入标号存在问题");
                                    is_match = false;
                                    break;
                                }
                            }
                            if (is_match) {
                                communities = temp_communities;
                            } else {
                                temp_communities.clear();
                            }

                        }
                    }
                } else {
                    // 设置活动排序请输入2
                    if (third.equals("2")) {
                        System.out.println("将活动按照建立时间排序请输入 0,退出输入请输入q ,手动输入顺序请输入活动标号并以空格间隔： ");

                        myObject.setCurrentTime();
                        myObject.setIfcalulate(true);
                        String fourth = scanner.nextLine();
                        myObject.setIfcalulate(false);

                        if (fourth.equals("0")) {
                            Collections.sort(activities);
                        } else {
                            if (!fourth.equals("q")) {
                                String[] sorted_num = fourth.split(" ");

                                boolean is_match = true;
                                ArrayList<Activity> temp_activities = new ArrayList<>();
                                for (String num : sorted_num) {
                                    // 正则匹配
                                    Pattern pattern = Pattern.compile("[0-9]*");
                                    Matcher isNum = pattern.matcher(num);
                                    if (isNum.matches()) {
                                        int n = Integer.parseInt(num);
                                        if (n <= activities.size() && n >= 1) {
                                            temp_activities.add(activities.get(n - 1));
                                        } else {
                                            if (n == 0) {
                                            } else {
                                                System.out.println("输入标号存在问题");
                                                is_match = false;
                                                break;
                                            }
                                        }
                                    } else {
                                        System.out.println("输入标号存在问题");
                                        is_match = false;
                                        break;
                                    }
                                }
                                if (is_match) {
                                    activities = temp_activities;
                                } else {
                                    temp_activities.clear();
                                }
                            }

                        }
                    } else {
                        // 添加活动额外信息请先输入3
                        if (third.equals("3")) {
                            System.out.println("规则：修改活动额外信息请先输入活动标号，退出输入请输入 q");
                            while (true) {
                                System.out.println("请输入指令： ");

                                myObject.setCurrentTime();
                                myObject.setIfcalulate(true);
                                String order = scanner.nextLine();
                                myObject.setIfcalulate(false);

                                if (order.equals("q")) {
                                    break;
                                } else {
                                    String num = order;
                                    // 正则匹配
                                    Pattern pattern = Pattern.compile("[0-9]*");
                                    Matcher isNum = pattern.matcher(num);
                                    if (isNum.matches()) {
                                        int n = Integer.parseInt(num);
                                        if (n <= activities.size() && n >= 1) {
                                            System.out.print("请输入活动额外信息： ");

                                            myObject.setCurrentTime();
                                            myObject.setIfcalulate(true);
                                            String extra_data = scanner.nextLine();
                                            myObject.setIfcalulate(false);

                                            if (extra_data != null) {
                                                activities.get(n - 1).setExtra_data(extra_data);
                                                System.out.println();
                                                activities.get(n - 1).displayActivitiesData();
                                            }
                                        } else {
                                            System.out.println("输入标号存在问题");
                                        }
                                    } else {
                                        System.out.println("输入标号存在问题");
                                    }
                                }
                            }

                        } else {
                            if (third.equals("q")) {
                                break;
                            } else {
                                System.out.println("请输入正确指令");
                            }
                        }
                    }
                }

            }
        } else {
            System.out.println("密码错误");
        }
    }


    public Activity getActivityByName(String name) {
        for (Activity activity : activities) {
            if (name.equals(activity.getActivity_name())) {
                return activity;
            }
        }

        return null;
    }

    public Community getCommunityByName(String name) {
        for (Community community : communities) {
            if (name.equals(community.getCommunity_name())) {
                return community;
            }
        }
        return null;
    }

    public ArrayList<Activity> getActivitiesByName(String name) {
        ArrayList<Activity> result = new ArrayList<>();
        for (Activity activity : activities) {
            Pattern pattern = Pattern.compile(".*" + name + ".*");
            Matcher isMatch = pattern.matcher(activity.getActivity_name());
            if (isMatch.matches()) {
                result.add(activity);
            }

        }

        return result;
    }

    public ArrayList<Community> getCommunitiesByName(String name) {
        ArrayList<Community> result = new ArrayList<>();
        for (Community community : communities) {
            Pattern pattern = Pattern.compile(".*" + name + ".*");
            Matcher isMatch = pattern.matcher(community.getCommunity_name());
            if (isMatch.matches()) {
                result.add(community);
            }

        }

        return result;
    }

}
