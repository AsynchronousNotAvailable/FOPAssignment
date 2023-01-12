import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {

        String date = "2022-03-02T11:30"; //sample 1
        String s = "2022-03-14 12"; // sample 2
        String afterDate = process(date);
        String afterS = process(s);
        System.out.println(afterDate);
        System.out.println(afterS);




        if(testing(afterDate).isBefore(testing(afterS))){
            System.out.printf("%s is before %s", testing(afterDate), testing(afterS));
        }else{
            System.out.printf("%s is before %s", testing(afterS), testing(afterDate));
        }


        String [] s_arr = s.split("-");
//        System.out.println(Arrays.toString(s_arr));

        LinkedHashMap <String, Integer> partitionList = new LinkedHashMap<>();
        String l = "[2022-06-01T01:02:36.012] sched: Allocate JobId=42802 NodeList=gpu05 #CPUs=32 Partition=gpu-v100s";
        String[] l_arr = l.split(" ");

        l_arr[0] = l_arr[0].substring(1, l_arr[0].length()-1);
//        System.out.println(l_arr[0]);
//        System.out.println(Arrays.toString(l_arr));


        for(String elem: l_arr){
            if(elem.contains("Partition")){
                String [] temp = elem.split("=");
                System.out.println(Arrays.toString(temp));
                partitionList.put(temp[1], 1);
                System.out.println(elem);
            }
        }
        System.out.println(partitionList);
    }

    static String process(String s){
        if(s.contains("T")){
            s = s.replace('T', ' ');
            //2022-12-16 00
        }
        if(!s.contains(" ")){
            s += " 00:00:00.000";
        }
        else if(s.length() == 13){
            s += ":00:00.000";
        }
        else if(s.length() == 16){
            s += ":00.000";
        }
        else if(s.length() == 19){
            s += ".000";
        }
        return s;
    }

    static LocalDateTime testing(String s){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
//        System.out.println(dateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");



        LocalDateTime ldt = LocalDateTime.parse(s, formatter);
        return ldt;
    }
}
