import Extract.OpenFile;

import javax.swing.*;
import java.awt.datatransfer.FlavorEvent;
import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class metrics4 extends metrics1{
    //instance variables
    protected LinkedHashMap<String, String> startExecTime = new LinkedHashMap<>();
    protected LinkedHashMap<String, String> endExecTime = new LinkedHashMap<>();
    protected LinkedHashMap<String, String> noEndExecTime = new LinkedHashMap<>();
    LinkedHashMap<String, Long> execTime = new LinkedHashMap<>();
    public ArrayList<String>jobIDList = new ArrayList<>();
    public String startDateTime;
    public String endDateTime;


    //constructor
    public metrics4(){
        generateTime();
        AvgExecTime();

    }
    //parse start time, end time, and noEndTimeExecution into LinkedHashMap
    public void generateTime(){
        try{
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;

            while((dummy = inputStream.readLine())!= null){
                if(dummy.contains("Allocate")){
//                    System.out.println(dummy);
                    String startTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length()-1);
                    String jobID = dummy.split(" ")[3].substring(6);
                    this.startExecTime.put(jobID, startTime);
                }

                if(dummy.contains("WEXITSTATUS")){
                    String endTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length()-1);
                    String jobID = dummy.split(" ")[2].substring(6);
                    this.endExecTime.put(jobID, endTime);
                }
            }


            for(String code: this.startExecTime.keySet()){
                if(!this.endExecTime.containsKey(code)){
                    this.noEndExecTime.put(code, this.startExecTime.get(code));
                }
            }


        }
        catch (FileNotFoundException e){

        }
        catch (IOException e){

        }
    }

    //enter time range, find average execution time of jobs within time range
    public void AvgExecTimeWithTime() {
        super.processTime();
        this.startDateTime = super.getStartDate();
        this.endDateTime = super.getEndDate();
        LinkedHashMap<String, String> localStart = new LinkedHashMap<>();
        LinkedHashMap<String, String> localEnd = new LinkedHashMap<>();


        try {
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            PrintWriter outputStream = new PrintWriter(new File("./data/averageExecutionTime.txt"));
            String dummy;

            while ((dummy = inputStream.readLine()) != null) {
                LocalDateTime targetTime = processTime(formatDateTime(dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length()-1)));
                if(targetTime.isAfter(processTime(this.startDateTime))&&targetTime.isBefore(processTime(this.endDateTime))){

                    if (dummy.contains("Allocate")) {
//                    System.out.println(dummy);
                        String startTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length() - 1);
                        String jobID = dummy.split(" ")[3].substring(6);

                        localStart.put(jobID, startTime);
                    }

                    if (dummy.contains("WEXITSTATUS")) {
                        String endTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length() - 1);
                        String jobID = dummy.split(" ")[2].substring(6);
                        localEnd.put(jobID, endTime);
                    }
                }

            }


            LinkedHashMap<String, Long>localExecTime = new LinkedHashMap<>();
            for(String code: localStart.keySet()){
                if(localEnd.containsKey(code)) {
                    LocalDateTime startTime = processTime(localStart.get(code).replace('T', ' '));
                    LocalDateTime endTime = processTime(localEnd.get(code).replace('T', ' '));

                    long milliseconds = Duration.between(startTime, endTime).toMillis();

                    localExecTime.put(code, milliseconds);

                }
            }

            long totalTime = 0;
            for (String code: localExecTime.keySet()){
                totalTime += localExecTime.get(code);
            }
            totalTime /= localExecTime.size();

            double seconds =  (totalTime / 1000) % 60 ;
            double minutes =  ((totalTime / (1000*60)) % 60);
            double hours   =  ((totalTime / (1000*60*60)) % 24);
//            long hours = TimeUnit.MILLISECONDS.toHours(totalTime);
            outputStream.write("JobID\t\tExecution Time (milliseconds)\n");
            for(String code: localExecTime.keySet()){
                outputStream.write(String.format("%s\t\t%s\n", code, localExecTime.get(code)));
            }
            outputStream.write(String.format("Average execution time: "+ hours + "\n"));
            outputStream.write(String.format("Number of jobs with execution start time from %s to %s is %s",startDateTime, endDateTime,localStart.size() + "\n"));
            outputStream.write(String.format("Number of jobs with execution end time: "+localEnd.size() +"\n"));
            outputStream.write(String.format("Number of jobs that do not have execution end time: "+getNoEndExecTime().size() + "\n"));
            outputStream.write(String.format("Number of jobs that has execution time: "+localExecTime.size() + "\n"));

            outputStream.flush();
            outputStream.close();
            OpenFile open = new OpenFile();
            open.showFile("./data/averageExecutionTime.txt");
        }
        catch (FileNotFoundException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println(e);
        }


    }
    //startTime minus endTime
    public void AvgExecTime(){
        LinkedHashMap<String, String> startExecTime = getStartExecTime();
        LinkedHashMap<String, String> endExecTime = getEndExecTime();


        for(String code: startExecTime.keySet()){
            if(endExecTime.containsKey(code)) {
                LocalDateTime startTime = processTime(startExecTime.get(code).replace('T', ' '));
                LocalDateTime endTime = processTime(endExecTime.get(code).replace('T', ' '));

                long milliseconds = Duration.between(startTime, endTime).toMillis();

                this.execTime.put(code, milliseconds);

            }
        }




    }

    //display Average execution time
    public void displayAvgExecTime(){
        long totalTime = 0;
        for (String code: getExecTime().keySet()){
            totalTime += getExecTime().get(code);
        }
        totalTime /= getExecTime().size();
        long hours = TimeUnit.MILLISECONDS.toHours(totalTime);
        System.out.printf("JobID\t\tExecution Time (milliseconds)\n");
        for(String code: getExecTime().keySet()){
            System.out.printf("%s\t\t%s\n", code, getExecTime().get(code));
        }
        System.out.println("Average execution time: "+ hours);
        System.out.println("Number of jobs with execution start time:"+getStartExecTime().size());
        System.out.println("Number of jobs with execution end time: "+getEndExecTime().size());
        System.out.println("Number of jobs that do not have execution end time: "+getNoEndExecTime().size());
        System.out.println("Number of jobs that has execution time: "+getExecTime().size());
    }

    //Enter jobID, find execution time for that jobID
    public void findExecTimeByJobID(){


        String jobID = JOptionPane.showInputDialog("Enter the jobID, -1 to exit to main menu");
        while(!jobID.equals("-1")){
            if(getExecTime().containsKey(jobID)){
                JOptionPane.showMessageDialog(null, String.format("JobID\t\tExecution Time(Milliseconds)\n%s\t\t%s\n", jobID, getExecTime().get(jobID)));

            }
            else{
                JOptionPane.showMessageDialog(null,"the jobID is not invalid or does not have execution time");

            }

            jobID = JOptionPane.showInputDialog("Enter the jobID, -1 to exit to main menu");
        }


    }
    //convert string to LocalDateTime
    public LocalDateTime processTime(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime ldt = LocalDateTime.parse(s, formatter);
        return ldt;
    }

    public String formatDateTime(String s){
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


    //getter method
    public LinkedHashMap<String, String> getStartExecTime(){
       return this.startExecTime;
    }
    //getter method
    public LinkedHashMap<String, String> getEndExecTime(){
        return this.endExecTime;
    }
    //getter method
    public LinkedHashMap<String, String> getNoEndExecTime(){
        return this.noEndExecTime;
    }

    public LinkedHashMap<String, Long> getExecTime(){
        return this.execTime;
    }

}
