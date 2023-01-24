import Extract.OpenFile;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
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

        String choose = JOptionPane.showInputDialog("Enter the partition, Enter '1', '2', '3', '4', '5', '6' to select partition, " +
                "'-1' to exit to main menu\nList Of Partition: \nPartition 1. CPU-EPYC\nPartition 2: CPU-Opteron\nPartition 3: GPU-V100s\nPartition 4: GPU-K40c\nPartition 5: GPU-Titan\nPartition 6: GPU-K10\n");

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
                JOptionPane.showMessageDialog(null, "Wrong input");
            }


            try{
                OpenFile open = new OpenFile();
                PrintWriter outputStream = new PrintWriter(new FileWriter(String.format("./data/%s.txt", translate)));
                outputStream.write("Job ID\t\tPartition Type\n");
                for(String code: getPartitionList().keySet()){
                    if(getPartitionList().get(code).equals(translate)){
                        outputStream.write(String.format("%s\t\t%s\n",code, getPartitionList().get(code)));
                        outputStream.flush();
                    }
                }

                outputStream.close();
                open.showFile(String.format("./data/%s.txt", translate));
            }
            catch (IOException e){
                System.out.println(e);
            }





            choose = JOptionPane.showInputDialog("Enter the partition, Enter '1', '2', '3', '4', '5', '6' to select partition, " +
                    "'-1' to exit to main menu\nList Of Partition: \nPartition 1. CPU-EPYC\nPartition 2: CPU-Opteron\nPartition 3: GPU-V100s\nPartition 4: GPU-K40c\nPartition 5: GPU-Titan\nPartition 6: GPU-K10\n");






        }



    }
    //enter jobId, output its partition
    public void findPartitionByJobID(){
        generatePartitionList();
        String jobID = JOptionPane.showInputDialog("Enter the jobID, -1 to exit to exit to main menu");


        while(!jobID.equals("-1")){
            if(getPartitionList().containsKey(jobID)){
                String output = String.format("%s\t\t%s\n", jobID, getPartitionList().get(jobID));
                JOptionPane.showMessageDialog(null, output);
            }
            else{
                JOptionPane.showMessageDialog(null, "No Such Job ID");

            }

            jobID = JOptionPane.showInputDialog("Enter the jobID, -1 to exit to exit to main menu");
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
                System.out.printf("%-13s\t\t\t%s\n", code, getNumJobByPartitionList().get(code));

            }


        }
        catch (FileNotFoundException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println(e);
        }


        try{
            PrintWriter outputStream = new PrintWriter(new File("./data/PartitionList.txt"));
            outputStream.write("Partition Type\t\tNumber of Jobs\n");
            for(String code: getNumJobByPartitionList().keySet()){
                outputStream.write(String.format("%-13s\t\t\t%s\n", code, getNumJobByPartitionList().get(code)));

            }
            outputStream.flush();
            outputStream.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
        OpenFile file = new OpenFile();
        file.showFile("./data/PartitionList.txt");
    }


    //getter method
    public LinkedHashMap<String, Integer>getNumJobByPartitionList(){
        return this.numJobByPartitionList;
    }

    public LinkedHashMap<String, String>getPartitionList(){
        return this.PartitionList;
    }
}


