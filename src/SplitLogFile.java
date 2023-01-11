
import org.w3c.dom.CDATASection;

import java.io.IOException;

import java.io.*;

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

    //process date time input from console
    //this method will accept String type array as argument
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
        ArrayList <Integer> dateElem = getTime(sc.next().split("-")); //.split() means you split the string into an array of substring
        Date startTime = new Date(dateElem.get(0), dateElem.get(1), dateElem.get(2));

        System.out.println("Enter the end time when the job is completed: ");
        dateElem = getTime(sc.next().split("-"));
        Date endTime = new Date(dateElem.get(0), dateElem.get(1), dateElem.get(2));
        System.out.println(startTime);

        LocalDateTime minThreshold = LocalDateTime.ofInstant(startTime.toInstant(), ZoneId.systemDefault()); //convert the date time to ISO format
        LocalDateTime maxThreshold = LocalDateTime.ofInstant(endTime.toInstant(), ZoneId.systemDefault());

        System.out.println("start time: "+ minThreshold);
        System.out.println("end time: " + maxThreshold);


        try{
            String dummy;
            String time;
            String [] stringElem = {};
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            while((dummy = inputStream.readLine()) != null){
                stringElem = dummy.split(" ");
                time = stringElem[0].substring(1, stringElem[0].length()-1);
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println(e);
        }


        //to find jobID submitted and completed between the given time range
        //using LinkedHashMap
        //because jobID is unique, we add jobID into LinkedHashMap as the KEY
        //the number of occurrence of jobID between the time range will be counted and added into LinkedHashMap as VALUE
        String [] submit = {};
        LinkedHashMap<String, Integer> submitJob = new LinkedHashMap<>();
        ArrayList<String> filter = new ArrayList<>();


        try{
            String sentence;
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
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
//        System.out.println(filter.size());
        System.out.printf("The amount of job submitted and ended at given time range is: %s", filter.size());
    }





}

