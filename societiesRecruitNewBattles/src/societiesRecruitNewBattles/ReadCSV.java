package societiesRecruitNewBattles;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadCSV {

    public static ArrayList<String[]> readCSV(String path, int ignore_line, String code_form) {
        ArrayList<String[]> data = new ArrayList<>();
        try {
            InputStreamReader csv = new InputStreamReader(new FileInputStream(path), code_form);

            BufferedReader br = new BufferedReader(csv);

            // 读取直到最后一行
            // 忽略行数
            for (int i = 0; i < ignore_line; i++) {
                br.readLine();
            }

            String line = "";
            while ((line = br.readLine()) != null) {
                // 把一行数据分割成多个字段
                data.add(line.split(","));
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

}
