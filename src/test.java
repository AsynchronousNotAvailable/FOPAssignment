import java.util.Arrays;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
        String s = "2022-3-14";
        String [] s_arr = s.split("-");
        System.out.println(Arrays.toString(s_arr));

        String l = "[2022-06-01T01:02:36.012] sched: Allocate JobId=42802 NodeList=gpu05 #CPUs=32 Partition=gpu-v100s";
        String[] l_arr = l.split(" ");
        l_arr[0] = l_arr[0].substring(1, l_arr[0].length()-1);
        System.out.println(l_arr[0]);
        System.out.println(Arrays.toString(l_arr));
        for(String elem: l_arr){
//            System.out.println(elem);
            if(elem.contains("Partition")){
                System.out.println(elem);
            }
        }
    }
}
