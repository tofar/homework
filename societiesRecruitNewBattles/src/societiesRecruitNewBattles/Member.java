package societiesRecruitNewBattles;

public class Member {
    String name;
    String college;  // 学院
    String class_name;

    public Member(String name) {
        this.name = name;
        college = "";
        class_name = "";
    }

    public Member(String name, String college, String class_name) {
        this.name = name;
        this.class_name = class_name;
        this.college = college;
    }

    public String getName() {
        return this.name;
    }

    public String getClass_name() {
        return class_name;
    }

    public String getCollege() {
        return college;
    }
}
