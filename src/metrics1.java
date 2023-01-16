import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class metrics1 {
    public static void main(String[] args) {

    }

    //instance variables
    public LinkedHashMap<String, LocalDateTime[]> startLdtWithJobId = new LinkedHashMap<>();
    public LinkedHashMap<String, LocalDateTime[]> endLdtWithJobId = new LinkedHashMap<>();


    //constructor
    //once this object of this class is called, it will invoke the processTime() method.
    public metrics1() {

    }

    //this method allows user to input start time and end time, generating jobIDs being created and ended within the time range given
    public void processTime() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Start Date Time: ");
        String startDate = sc.nextLine();
        String startDateTime = formatDateTime(startDate);
        System.out.println("Enter End Date Time: ");
        String endDate = sc.nextLine();
        String endDateTime = formatDateTime(endDate);
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            while ((dummy = inputStream.readLine()) != null) {
                LocalDateTime[] array = new LocalDateTime[2];
                if (dummy.contains("sched: Allocate")) {
                    String unprocessedTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length() - 1);
                    LocalDateTime processedTime = convertToLDT(formatDateTime(unprocessedTime));
                    if (processedTime.isAfter(convertToLDT(startDateTime)) && processedTime.isBefore(convertToLDT(endDateTime))) {
                        array[0] = processedTime;
                        String jobID = dummy.split(" ")[3].substring(6);
                        this.startLdtWithJobId.put(jobID, array);
                    }

                }

                if (dummy.contains("WEXITSTATUS")) {
                    String unprocessedTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length() - 1);
                    LocalDateTime processedTime = convertToLDT(formatDateTime(unprocessedTime));
                    if (processedTime.isAfter(convertToLDT(startDateTime)) && processedTime.isBefore(convertToLDT(endDateTime))) {
                        array[1] = processedTime;
                        String jobID = dummy.split(" ")[2].substring(6);
                        if (this.startLdtWithJobId.containsKey(jobID)) {
                            this.startLdtWithJobId.get(jobID)[1] = processedTime;
                        } else {
                            this.startLdtWithJobId.put(jobID, array);
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
        processTime();
        for (String code : getStartLdtWithJobId().keySet()) {
            System.out.printf("%s\t%30s\t\t%s\n", code, getStartLdtWithJobId().get(code)[0], getStartLdtWithJobId().get(code)[1]);
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
        System.out.println("Enter jobID to check job completion status: ");
        String jobId = sc.nextLine();


        while(!jobId.equals("-1")){

            while(getStartLdtWithJobId().containsKey(jobId)){
                String status = "";
                if(jobId.equals("-1")){
                    System.exit(0);
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

                System.out.printf("JobID\t\t\t\tStart Time\t\t\t\t\t\t\tEnd Time\t\t\t\t\tStatus\n");
                System.out.printf("%s\t\t\t\t\t%s\t\t\t%s\t\t\t\t\t%s\n", jobId,this.startLdtWithJobId.get(jobId)[0],this.startLdtWithJobId.get(jobId)[1],  status);
                System.out.println("Enter jobID to check job completion status: ");
                jobId = sc.nextLine();
                if(jobId.equals("-1")){
                    System.exit(0);
                }
            }
            System.out.println("No such jobID");
            System.out.println("Enter jobID to check job completion status: ");
            jobId = sc.nextLine();
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
