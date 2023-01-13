import java.io.*;
import java.util.LinkedHashMap;

public class metrics2 {
    public metrics2(){

    }

    public void readFile(){
        try{
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            String partition;
            LinkedHashMap<String, Integer> partitionList = new LinkedHashMap<>();
            while((dummy =inputStream.readLine())!= null){
                if (dummy.contains("Partition")){

                    partition = dummy.split(" ")[dummy.split(" ").length-1].substring(10);
                    String p = partition;
                    if (partitionList.containsKey(p)){
                        partitionList.put(p, partitionList.get(p) + 1);
                    } else {
                        partitionList.put(p, 1);
                    }




                }
            }

            System.out.println(partitionList);
        }
        catch (FileNotFoundException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
}

class Tester{
    public static void main(String[] args) {
        metrics2 obj= new metrics2();
        obj.readFile();
    }
}
