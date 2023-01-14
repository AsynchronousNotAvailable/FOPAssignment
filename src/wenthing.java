import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//(b)	Number of jobs by partitions, i.e. EPYC, Opteron and GPU.

public class wenthing {

    public static void metrics2(){
        String [] partitionLine = {};
        ArrayList<String> partitionList = new ArrayList<>();
        ArrayList<String> jobIDList = new ArrayList<>();
        String sentence;
        String typePartition="", jobID="";

        try {
            //read file
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));

            //extract jobID and partition
            while ((sentence = inputStream.readLine()) != null) {
                if (sentence.contains("Partition")) {
                    partitionLine = sentence.split(" ");
                    for (int i=0; i<partitionLine.length; i++){
                        if (partitionLine[i].contains("Partition=")){
                            typePartition = partitionLine[i].substring(10);
                            partitionList.add(typePartition);
                        }
                        if (partitionLine[i].contains("JobId=")){
                            jobID = partitionLine[i].substring(6);
                            jobIDList.add(jobID);
                        }
                    }
                }
            }

            ArrayList<String> GPUv100s = new ArrayList<>();
            ArrayList<String> GPUk40c = new ArrayList<>();
            ArrayList<String> GPUtitan = new ArrayList<>();
            ArrayList<String> GPUk10 = new ArrayList<>();
            ArrayList<String> CPUopteron = new ArrayList<>();
            ArrayList<String> CPUepyc = new ArrayList<>();

            //categorise according partition
            for (int i=0; i<partitionList.size(); i++){
                if (partitionList.get(i).contains("gpu-v100s"))
                    GPUv100s.add(jobIDList.get(i));
                else if (partitionList.get(i).contains("gpu-k40c"))
                    GPUk40c.add(jobIDList.get(i));
                else if (partitionList.get(i).contains("gpu-titan"))
                    GPUtitan.add(jobIDList.get(i));
                else if (partitionList.get(i).contains("gpu-k10"))
                    GPUk10.add(jobIDList.get(i));
                else if (partitionList.get(i).contains("cpu-opteron"))
                    CPUopteron.add(jobIDList.get(i));
                else if (partitionList.get(i).contains("cpu-epyc"))
                    CPUepyc.add(jobIDList.get(i));
            }

            System.out.println("Total number of jobs: "+partitionList.size());

            System.out.println("\nNumber of jobs by partition");
            System.out.printf("%-15s%5d\n","gpu-k10:",GPUk10.size());
            System.out.printf("%-15s%5d\n","gpu-v100s:",GPUv100s.size());
            System.out.printf("%-15s%5d\n","gpu-k40c: ",GPUk40c.size());
            System.out.printf("%-15s%5d\n","gpu-titan: ",GPUtitan.size());
            System.out.printf("%-15s%5d\n","cpu-opteron: ",CPUopteron.size());
            System.out.printf("%-15s%5d\n","cpu-epyc: ",CPUepyc.size());

        }

        catch (FileNotFoundException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

//    public static void displayJobID(ArrayList<String> jobID){
//        for (int i=0; i< jobID.size(); i++){
//            System.out.print(jobID.get(i)+" ");
//        }
//        System.out.println();
//    }

    public static void main(String[] args) {
        metrics2();
    }
}