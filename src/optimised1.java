import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class optimised1 {
    //instance variables
    public LinkedHashMap<String, LocalDateTime[]>startLdtWithJobId = new LinkedHashMap<>();
    public LinkedHashMap<String, LocalDateTime[]>endLdtWithJobId = new LinkedHashMap<>();


    //constructor
    //once this object of this class is called, it will invoke the processTime() method.
    public optimised1(){
        processTime();
    }

    //this method allows user to input start time and end time, generating jobIDs being created and ended within the time range given
    public void processTime(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Start Date Time: ");
        String startDateTime = formatDateTime(sc.nextLine());
        System.out.println("Enter End Date Time: ");
        String endDateTime = formatDateTime(sc.nextLine());

        try{
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            while((dummy = inputStream.readLine())!=null){
                LocalDateTime [] array = new LocalDateTime[2];
                if(dummy.contains("sched: Allocate")){
                    String unprocessedTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length()-1);
                    LocalDateTime processedTime = convertToLDT(formatDateTime(unprocessedTime));
                    if(processedTime.isAfter(convertToLDT(startDateTime))&&processedTime.isBefore(convertToLDT(endDateTime))){
                        array[0] = processedTime;
                        String jobID = dummy.split(" ")[3].substring(6);
                        this.startLdtWithJobId.put(jobID, array);
                    }

                }

                if(dummy.contains("WEXITSTATUS")){
                    String unprocessedTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length()-1);
                    LocalDateTime processedTime = convertToLDT(formatDateTime(unprocessedTime));
                    if(processedTime.isAfter(convertToLDT(startDateTime))&&processedTime.isBefore(convertToLDT(endDateTime))) {
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


            System.out.println("JobID\t\tStart Time Stamp\t\tEnd Time Stamp");
            for(String code: getStartLdtWithJobId().keySet()){
                System.out.printf("%s\t\t%s\n", code, Arrays.toString(getStartLdtWithJobId().get(code)));
//                System.out.printf("%s\t\t%-20s\t\t%s\n", code, this.startLdtWithJobId.get(code)[0], this.startLdtWithJobId.get(code)[1] );
            }



//            System.out.println("Number of Time Stamp of sched: Allocate with job ID: "+this.startLdtWithJobId.size());
//            System.out.println("Number of Time Stamp of WEXITSTATUS with job ID: "+this.endLdtWithJobId.size());
        }


        catch(FileNotFoundException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println(e);
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
}
