import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
        System.out.println("try again");
        System.out.println("kk");
        System.out.println("hihi");
        System.out.println("Hello");
        String s = "2022-3-14";
        String [] s_arr = s.split("-");
        System.out.println(Arrays.toString(s_arr));
        LinkedHashMap <String, Integer> partitionList = new LinkedHashMap<>();
        String l = "[2022-06-01T01:02:36.012] sched: Allocate JobId=42802 NodeList=gpu05 #CPUs=32 Partition=gpu-v100s";
        String[] l_arr = l.split(" ");
        System.out.println(l_arr[0]);
        l_arr[0] = l_arr[0].substring(1, l_arr[0].length()-1);
        System.out.println(l_arr[0]);
        System.out.println(Arrays.toString(l_arr));
        for(String elem: l_arr){
//            System.out.println(elem);
            if(elem.contains("Partition")){
                String [] temp = elem.split("=");
                System.out.println(Arrays.toString(temp));
                partitionList.put(temp[1], 1);
                System.out.println(elem);
            }
        }
        System.out.println(partitionList);
    }

    static void testing(){
        System.out.println("this is a testing function");
    }
}
