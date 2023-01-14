import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class metrics1 {
    protected LinkedHashMap<String, Integer> submitJob;
    protected LinkedHashMap<String, Integer> completeJob;
    protected LinkedHashMap<String, Integer> uncompleteJob;
    protected int numSubmit; protected int numComplete; protected int numUncomplete;
    protected String startDate; protected String endDate;

//    constructor
    public metrics1(){
        LinkedHashMap <String, Integer> submitJobList = new LinkedHashMap<>();
        LinkedHashMap <String, Integer> completeJobList = new LinkedHashMap<>();
        LinkedHashMap <String, Integer> uncompleteJobList = new LinkedHashMap<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter start date time:");
        this.startDate = process(sc.nextLine());
        System.out.println("Enter end date time:");
        this.endDate = process(sc.nextLine());
        this.submitJob = setSubmitJob(submitJobList);
        this.completeJob = setCompleteJob(completeJobList);
        this.uncompleteJob = setUncompleteJob(uncompleteJobList);
        this.numSubmit = submitJob.size();
        this.numComplete = completeJob.size();
        this.numUncomplete = uncompleteJob.size();

    }
    public static void main(String[] args) {

        metrics1 obj = new metrics1();
        obj.checkJobStatus();
        obj.displayStatus();


        System.out.println("number of submitted job: " + obj.submitJob.size());
        System.out.println("number of completed job: " + obj.completeJob.size());
        System.out.println("number of uncompleted job: " + obj.uncompleteJob.size());


    }

    public LinkedHashMap<String, Integer> setSubmitJob(LinkedHashMap<String, Integer> a){

        try{
            String dummy;
            String [] submit;
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            while((dummy = inputStream.readLine())!=null) {
                String[] stringElem = dummy.split(" ");
                String time = stringElem[0].substring(1, stringElem[0].length() - 1);
                LocalDateTime targetDate = testing(process(time));

                //check if the time is within the time range
                if (targetDate.isAfter(testing(this.startDate)) && targetDate.isBefore(testing(this.endDate))) {

                    //add submitted job into submitJob
                    if (dummy.contains("submit")) {
                        submit = dummy.split(" ");
                        for (int i = 0; i < submit.length; i++) {
                            if (submit[i].contains("JobId")) {
                                String jobID = submit[i].substring(6);
                                if (a.containsKey(jobID)) {
                                    a.put(jobID, a.get(jobID) + 1);
                                } else {
                                    a.put(jobID, 1);
                                }
                            }
                        }
                    }

                    if ((dummy.contains("done"))) {
                        submit = dummy.split(" ");
                        for (int i = 0; i < submit.length; i++) {
                            if (submit[i].contains("JobId")) {
                                String jobID = submit[i].substring(6);
                                if (a.containsKey(jobID)) {
                                    a.put(jobID, a.get(jobID) + 1);
                                } else {
                                    a.put(jobID, 1);
                                }

                            }
                        }
                    }



                }
            }


        }
        catch (FileNotFoundException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println(e);
        }

        return a;
    }
    public LinkedHashMap<String, Integer> setCompleteJob(LinkedHashMap<String, Integer> a){
        try{
            String dummy;
            String [] submit;
            LinkedHashMap<String, Integer> completeJob = new LinkedHashMap<>();
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            while((dummy = inputStream.readLine())!=null) {
                String[] stringElem = dummy.split(" ");
                String time = stringElem[0].substring(1, stringElem[0].length() - 1);
                LocalDateTime targetDate = testing(process(time));

                //check if the time is within the time range
                if (targetDate.isAfter(testing(this.startDate)) && targetDate.isBefore(testing(this.endDate))) {

                    //add completed job into completeJob between time range
                    if ((dummy.contains("done"))) {
                        submit = dummy.split(" ");
                        for (int i = 0; i < submit.length; i++) {
                            if (submit[i].contains("JobId")) {
                                String jobID = submit[i].substring(6);
                                if (a.containsKey(jobID)) {
                                    a.put(jobID, a.get(jobID) + 1);
                                    a.put(jobID, a.get(jobID) + 1);
                                } else {
                                    a.put(jobID, 1);
                                }

                            }
                        }
                    }


                }
            }


        }
        catch (FileNotFoundException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println(e);
        }

        return a;
    }
    public LinkedHashMap<String, Integer> setUncompleteJob(LinkedHashMap<String, Integer> a){

        for(String jobID: this.submitJob.keySet()){
            if(this.submitJob.get(jobID) != 2){
                a.put(jobID, submitJob.get(jobID));
            }
        }
        return a;
    }

    public void checkJobStatus(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter jobID to check job completion status: ");
        String jobId = sc.nextLine();
        String status;

        while(!jobId.equals("-1")){

            while(submitJob.containsKey(jobId)){
                if(jobId.equals("-1")){
                    System.exit(0);
                }
                if(uncompleteJob.containsKey(jobId)){
                    status = "Not Completed";
                }
                else{
                    status = "Completed";
                }

                System.out.printf("JobID\t\tStatus\n");
                System.out.printf("%s\t\t%s\n", jobId, status);
                System.out.println("Enter jobID to check job completion status: ");
                jobId = sc.nextLine();
            }
            System.out.println("No such jobID");
            System.out.println("Enter jobID to check job completion status: ");
            jobId = sc.nextLine();

        }
        System.exit(0);


    }

    public void displayStatus(){
        String status;
        System.out.printf("JobID\t\tStatus\n");
        System.out.println("-------------------------");
        for(String code: this.submitJob.keySet()){

            if(this.submitJob.get(code) != 2){
                status = "Not Completed";
            }
            else{
                status = "completed";
            }

            System.out.printf("%s\t\t%s\n", code, status);

        }


    }


//    local method
    private static String process(String s){
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
//    local method
    private static LocalDateTime testing(String s){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
//        System.out.println(dateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");



        LocalDateTime ldt = LocalDateTime.parse(s, formatter);
        return ldt;
    }
}
