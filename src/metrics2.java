import java.io.*;
import java.util.LinkedHashMap;

public class metrics2 {
    public LinkedHashMap<String, Integer>partitionList = new LinkedHashMap<>();
    //constructor
    //when a object from this class is called, it will invoke the readFile() method
    public metrics2(){
        generatePartitionList();
    }

    //this method will generate the number of jobs by partition
    public void generatePartitionList(){
        try{
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            String partition;

            while((dummy =inputStream.readLine())!= null){
                if (dummy.contains("Partition")){

                    partition = dummy.split(" ")[dummy.split(" ").length-1].substring(10);
                    String p = partition;
                    if (this.partitionList.containsKey(p)){
                        this.partitionList.put(p, this.partitionList.get(p) + 1);
                    } else {
                        this.partitionList.put(p, 1);
                    }
                }
            }


            System.out.printf("Partition Type\t\tNumber of Jobs\n");
            for(String code: getPartitionList().keySet()){
                System.out.printf("%-14s\t\t%s\n", code, this.partitionList.get(code));
            }
        }
        catch (FileNotFoundException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println(e);
        }
    }


    //getter method
    public LinkedHashMap<String, Integer>getPartitionList(){
        return this.partitionList;
    }
}


