import java.io.*;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class metrics2 {
    public LinkedHashMap<String, Integer>numJobByPartitionList = new LinkedHashMap<>();
    public LinkedHashMap<String, String>PartitionList = new LinkedHashMap<>();
    //constructor
    //when a object from this class is called, it will invoke the readFile() method
    public metrics2(){
        generatePartitionList();
    }

    public void generatePartitionList(){
        try{
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            while((dummy = inputStream.readLine())!= null){
                String type;
                if(dummy.contains("Partition")){
                    String jobId = dummy.split(" ")[3].substring(6);
                    type = dummy.split(" ")[dummy.split(" ").length-1].substring(10);
                    this.PartitionList.put(jobId, type);

                }
            }
        }
        catch(FileNotFoundException e){

        }
        catch(IOException e){

        }
    }

    public void findJobIDByPartition(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the partition, Enter '1', '2', '3', '4', '5', '6' to select partition ");
        System.out.println("List Of Partition: ");
        System.out.println("Partition 1. CPU-EPYC");
        System.out.println("Partition 2: CPU-Opteron");
        System.out.println("Partition 3: GPU-V100s");
        System.out.println("Partition 4: GPU-K40c");
        System.out.println("Partition 5: GPU-Titan");
        System.out.println("Partition 6: GPU-K10");
        String choose = sc.nextLine();

        while(!choose.equals("-1")){
            String translate= "";
            if(choose.equals("1")){
                translate = "cpu-epyc";
            }else if(choose.equals("2")){
                translate = "cpu-opteron";
            }else if(choose.equals("3")){
                translate = "gpu-v100s";
            }else if(choose.equals("4")){
                translate = "gpu-k40c";
            }else if(choose.equals("5")){
                translate = "gpu-titan";
            }else if(choose.equals("6")){
                translate = "gpu-k10";
            }else{
                System.out.println("Wrong input");
            }

            System.out.println("Partition type: " + translate);

            for(String code: getPartitionList().keySet()){
                if(getPartitionList().get(code).equals(translate)){
                    System.out.printf("%s\t\t%s\n",code, getPartitionList().get(code));
                }
            }





        }
        System.exit(0);

    }
    //enter jobId, output its partition
    public void findPartitionByJobID(){
        generatePartitionList();
        System.out.println("Enter the jobID, -1 to exit to exit to main menu");
        Scanner sc = new Scanner(System.in);
        String jobID = sc.nextLine();

        while(!jobID.equals("-1")){
            if(getPartitionList().containsKey(jobID)){
                System.out.println("Job ID\t\tPartition Type");
                System.out.printf("%s\t\t%s\n", jobID, getPartitionList().get(jobID));
            }
            else{
                System.out.println("No Such Job ID");
            }
            System.out.println("Enter the jobID: -1 to exit to main menu");
            jobID = sc.nextLine();
        }

    }
    //this method will generate the number of jobs by partition
    public void generateNumJobByPartition(){
        try{
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            String partition;

            while((dummy =inputStream.readLine())!= null){
                if (dummy.contains("Partition")){

                    partition = dummy.split(" ")[dummy.split(" ").length-1].substring(10);
                    String p = partition;
                    if (this.numJobByPartitionList.containsKey(p)){
                        this.numJobByPartitionList.put(p, this.numJobByPartitionList.get(p) + 1);
                    } else {
                        this.numJobByPartitionList.put(p, 1);
                    }
                }
            }


            System.out.printf("Partition Type\t\tNumber of Jobs\n");
            for(String code: getNumJobByPartitionList().keySet()){
                System.out.printf("%-14s\t\t%s\n", code, this.numJobByPartitionList.get(code));
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
    public LinkedHashMap<String, Integer>getNumJobByPartitionList(){
        return this.numJobByPartitionList;
    }

    public LinkedHashMap<String, String>getPartitionList(){
        return this.PartitionList;
    }
}


