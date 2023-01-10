
import org.w3c.dom.CDATASection;

import java.io.IOException;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Scanner;


public class SplitLogFile {

    public static void main(String[] args) {
        metrics1();
        Scanner sc = new Scanner(System.in);


    }

    static ArrayList<Integer> getTime(String [] arr){
        ArrayList<Integer> dateElem = new ArrayList<>();
        for(String elem: arr){
            dateElem.add(Integer.parseInt(elem));
        }
        return dateElem;
    }
    //find jobs submitted and completed at given time range
    static void metrics1(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the start time when the job is submitted: ");
        ArrayList <Integer> dateElem = getTime(sc.next().split("-"));
        Date startTime = new Date(dateElem.get(0), dateElem.get(1), dateElem.get(2));
        System.out.println("Enter the end time when the job is completed: ");
        dateElem = getTime(sc.next().split("-"));
        Date endTime = new Date(dateElem.get(0), dateElem.get(1), dateElem.get(2));
        LocalDateTime minThreshold = LocalDateTime.ofInstant(startTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime maxThreshold = LocalDateTime.ofInstant(endTime.toInstant(), ZoneId.systemDefault());
        System.out.println(minThreshold);
        System.out.println(maxThreshold);



        String [] submit = {};
        LinkedHashMap<String, Integer> submitJob = new LinkedHashMap<>();

        ArrayList<String> filter = new ArrayList<>();
        try{
            String sentence;
            BufferedReader inputStream = new BufferedReader(new FileReader("./src/extracted_log.txt"));
            while((sentence = inputStream.readLine()) != null){
                if(sentence.contains("submit")){
                    submit = sentence.split(" ");
                    for(int i = 0; i < submit.length; i++){
                        if(submit[i].contains("JobId")){
                            String jobID = submit[i].substring(6);
                            if(submitJob.containsKey(jobID)){
                                submitJob.put(jobID, submitJob.get(jobID)+1);
                            }
                            else{
                                submitJob.put(jobID, 1);
                            }

                        }
                    }
                }

                if((sentence.contains("done"))){
                    submit = sentence.split(" ");
                    for(int i = 0; i < submit.length; i++){
                        if(submit[i].contains("JobId")){
                            String jobID = submit[i].substring(6);
                            if(submitJob.containsKey(jobID)){
                                submitJob.put(jobID, submitJob.get(jobID)+1);
                            }
                            else{
                                submitJob.put(jobID, 1);
                            }

                        }
                    }
                }
            }
            for(String jobID: submitJob.keySet()){
                if(submitJob.get(jobID) == 2){
                    filter.add(jobID);

                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        System.out.println(filter);
        System.out.println(filter.size());
        System.out.printf("The amount of job submitted and ended at given time range is:", filter.size());
    }





}

