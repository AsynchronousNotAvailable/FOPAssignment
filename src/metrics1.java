import Extract.OpenFile;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class metrics1 {

    public static void main(String[] args) {

    }

    //instance variables
    public LinkedHashMap<String, LocalDateTime[]> startLdtWithJobId = new LinkedHashMap<>();
    public LinkedHashMap<String, LocalDateTime[]> endLdtWithJobId = new LinkedHashMap<>();
    ArrayList<LocalDateTime> startTimeList = new ArrayList<>();
    ArrayList<LocalDateTime> endTimeList = new ArrayList<>();
    public String startDate; public String endDate;
    public int startOnly;
    public int endOnly;
    //constructor
    //once this object of this class is called, it will invoke the processTime() method.
    public metrics1() {

    }



    //this method allows user to input start time and end time, generating jobIDs being created and ended within the time range given
    public void processTime() {

        String startDate = JOptionPane.showInputDialog("Enter Start Date Time: ");//dev mode
        this.startDate = formatDateTime(startDate);
        String endDate = JOptionPane.showInputDialog("Enter End Date Time: "); //dev mode
        this.endDate = formatDateTime(endDate);
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            while ((dummy = inputStream.readLine()) != null) {
                LocalDateTime[] array = new LocalDateTime[2];
                if (dummy.contains("sched: Allocate")) {
                    this.startOnly++;
                    String unprocessedTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length() - 1);
                    LocalDateTime processedTime = convertToLDT(formatDateTime(unprocessedTime));
                    if (processedTime.isAfter(convertToLDT(getStartDate())) && processedTime.isBefore(convertToLDT(getEndDate()))) {
                        array[0] = processedTime;
                        this.startTimeList.add(processedTime); //testing for visualization
                        String jobID = dummy.split(" ")[3].substring(6);
                        this.startLdtWithJobId.put(jobID, array);

                    }

                }

                if (dummy.contains("WEXITSTATUS")) {
                    this.endOnly++;
                    String unprocessedTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length() - 1);
                    LocalDateTime processedTime = convertToLDT(formatDateTime(unprocessedTime));
                    if (processedTime.isAfter(convertToLDT(this.startDate)) && processedTime.isBefore(convertToLDT(this.endDate))) {
                        array[1] = processedTime;
                        this.endTimeList.add(processedTime); //testing for visualization
                        String jobID = dummy.split(" ")[2].substring(6);
                        if (this.startLdtWithJobId.containsKey(jobID)) {
                            this.startLdtWithJobId.get(jobID)[1] = processedTime; //if jobId exist, store in same array [startldt, endldt]
                            this.endTimeList.add(processedTime);
                        } else {
                            this.startLdtWithJobId.put(jobID, array); //if not exist, store in a new set of array [null, endldt]
                        }

                    }
                }
            }


//            System.out.println("Number of Time Stamp of sched: Allocate with job ID: "+this.startLdtWithJobId.size());
//            System.out.println("Number of Time Stamp of WEXITSTATUS with job ID: "+this.endLdtWithJobId.size());
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    public void option1() {
        OpenFile open = new OpenFile();
        processTime();


        try{
            PrintWriter outputstream = new PrintWriter(new FileWriter("./data/numJobsGivenTime.txt"));
            System.out.println(convertToLDT(getStartDate()));
            System.out.println(convertToLDT(getEndDate()));
            outputstream.write(String.format("The number of jobs started from %s to %s is: %s\n", convertToLDT(getStartDate()), convertToLDT(getEndDate()), this.startOnly));
            outputstream.write(String.format("The number of jobs ended from %s to %s is: %s\n", convertToLDT(getStartDate()), convertToLDT(getEndDate()), this.endOnly));
            outputstream.println();
            outputstream.write(String.format("%s\t\t%-30s\t%s\n", "JobID", "Start Time", "End Time"));

            for (String code : getStartLdtWithJobId().keySet()) {

                if(getStartLdtWithJobId().get(code)[0] == null){
                    System.out.println("yes");
                    outputstream.write(String.format("%s\t\t%-30s\t%s\n", code, "N/A", getStartLdtWithJobId().get(code)[1] ));
                }
                else if(getStartLdtWithJobId().get(code)[1] == null){
                    outputstream.write(String.format("%s\t\t%-30s\t%s\n", code, getStartLdtWithJobId().get(code)[0], "N/A" ));
                }
                else {
                    outputstream.write(String.format("%s\t\t%-30s\t%s\n", code, getStartLdtWithJobId().get(code)[0], getStartLdtWithJobId().get(code)[1] ));
                }

            }
            outputstream.flush();
            outputstream.close();
            open.showFile("./data/numJobsGivenTime.txt");
        }
        catch(IOException e){
            System.out.println(e);
        }





    }


    //enter job id, check job status
    public void option2(){
        //take in all jobId from file
        try{
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            while((dummy = inputStream.readLine())!= null){
                LocalDateTime [] array = new LocalDateTime[2];
                if(dummy.contains("sched: Allocate")){
                    String unprocessedTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length()-1);
                    LocalDateTime processedTime = convertToLDT(formatDateTime(unprocessedTime));
                    array[0] = processedTime;
                    String jobID = dummy.split(" ")[3].substring(6);
                    this.startLdtWithJobId.put(jobID, array);
                }

                if(dummy.contains("WEXITSTATUS")){
                    String unprocessedTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length()-1);
                    LocalDateTime processedTime = convertToLDT(formatDateTime(unprocessedTime));
                    array[1] = processedTime;
                    String jobID = dummy.split(" ")[2].substring(6);
                    if(this.startLdtWithJobId.containsKey(jobID)){
                        this.startLdtWithJobId.get(jobID)[1] = processedTime;
                    }
                    else{
                        this.startLdtWithJobId.put(jobID, array);
                    }
                }


            }
        }
        catch (FileNotFoundException e){

        }
        catch (IOException e){

        }

        Scanner sc = new Scanner(System.in);

        String jobId = JOptionPane.showInputDialog("Enter the jobID to check job completion status, -1 to exit to exit to main menu");

        while(!jobId.equals("-1")){

            while(getStartLdtWithJobId().containsKey(jobId)){
                String status = "";
                if(jobId.equals("-1")){
                    break;
                }
                if(getStartLdtWithJobId().containsKey(jobId)){
                    if(getStartLdtWithJobId().get(jobId)[0] == null){
                        status = "No Start Time. Completed";
                    }
                    else if(getStartLdtWithJobId().get(jobId)[1] == null){
                        status = "No End Time. Not Completed";
                    }
                    else{
                        status = "Completed";
                }

                }
                var output = String.format("JobID\t\t\t\t\t\t\t\t\t\t\t\t\tStart Time\t\t\t\t\t\t\t\t\t\t\t\t\tEnd Time\t\t\t\t\t\t\t\t\t\t\t\t\t\tStatus\n%s\t\t\t\t\t%s\t\t\t%s\t\t\t\t\t%s\n", jobId,this.startLdtWithJobId.get(jobId)[0],this.startLdtWithJobId.get(jobId)[1],  status);
                JOptionPane.showMessageDialog(null, output);


                jobId = JOptionPane.showInputDialog("Enter the jobID to check job completion status, -1 to exit to exit to main menu");
                if(jobId.equals("-1")){
                    break;
                }
            }

            jobId = JOptionPane.showInputDialog("No such jobID\nEnter the jobID to check job completion status, -1 to exit to exit to main menu");
        }


    }

    //getter method
    public LinkedHashMap<String, LocalDateTime[]>getStartLdtWithJobId(){
        return this.startLdtWithJobId;
    }

    //getter method
    public LinkedHashMap<String, LocalDateTime[]>getEndLdtWithJobId(){
        return this.endLdtWithJobId;
    }




//    public void findings(){
//        //find jobID with start time
//        for(String code: getStartLdtWithJobId().keySet()){
//            if(!getEndLdtWithJobId().containsKey(code)){
//                this.startTimeOnlyList.add(code);
////                try{
////                    BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
////                    System.out.println(code);
////                    String parse;
////                    while((parse = inputStream.readLine())!= null){
////                        if(parse.contains(code)){
////
////                            System.out.println(parse);
////
////                        }
////
////
////                    }
////                }
////                catch(FileNotFoundException e){
////                    System.out.println(e);
////                }
////                catch (IOException e){
////                    System.out.println(e);
////                }
//            }
//
//
//        }
//
//
////        find jobId with end time only
//        for(String code: getEndLdtWithJobId().keySet()){
//            if(!getStartLdtWithJobId().containsKey(code)){
//                this.endTimeOnlyList.add(code);
////                try{
////                    BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
////                    String parse;
////                    while((parse= inputStream.readLine())!= null){
////                        if(parse.contains(code)){
//////                            System.out.println(parse);
////                        }
////                    }
////                }
////                catch(FileNotFoundException e){
////                    System.out.println(e);
////                }
////                catch (IOException e){
////                    System.out.println(e);
////                }
//            }
//
////                System.out.printf("%s\t\t%s\n", code, getEndLdtWithJobId().get(code));
//        }
//    }

    //local method to process date time by user input so can be parsed into ldt method
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

    //convert String date time to LocalDateTime
    public LocalDateTime convertToLDT(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime ldt = LocalDateTime.parse(s, formatter);
        return ldt;
    }

    public String getStartDate(){
        return this.startDate;
    }

    public String getEndDate(){
        return this.endDate;
    }

    public ArrayList<LocalDateTime> getStartTimeList(){
        return this.startTimeList;
    }

    public ArrayList<LocalDateTime> getEndTimeList(){
        return this.endTimeList;
    }
}
