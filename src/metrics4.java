import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

public class metrics4 {
    //instance variables
    protected LinkedHashMap<String, String> startExecTime = new LinkedHashMap<>();
    protected LinkedHashMap<String, String> endExecTime = new LinkedHashMap<>();
    protected LinkedHashMap<String, String> noEndExecTime = new LinkedHashMap<>();

    //constructor
    public metrics4(){
        generateTime();
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

    //startTime minus endTime
    public void findExecTime(){
        LinkedHashMap<String, String> startExecTime = getStartExecTime();
        LinkedHashMap<String, String> endExecTime = getEndExecTime();
        LinkedHashMap<String, Long> execTime = new LinkedHashMap<>();

        for(String code: startExecTime.keySet()){
            if(endExecTime.containsKey(code)) {
                LocalDateTime startTime = processTime(startExecTime.get(code).replace('T', ' '));
                LocalDateTime endTime = processTime(endExecTime.get(code).replace('T', ' '));

                long milliseconds = Duration.between(startTime, endTime).toMillis();

                execTime.put(code, milliseconds);

            }
        }
        long totalTime = 0;
        for (String code: execTime.keySet()){
            totalTime += execTime.get(code);
        }
        totalTime /= execTime.size();
        long hours = TimeUnit.MILLISECONDS.toHours(totalTime);

        System.out.printf("JobID\t\tExecution Time (milliseconds)\n");
        for(String code: execTime.keySet()){
            System.out.printf("%s\t\t%s\n", code, execTime.get(code));
        }
        System.out.println("Average execution time: "+ hours);
        System.out.println("Number of jobs with execution start time:"+getStartExecTime().size());
        System.out.println("Number of jobs with execution end time: "+getEndExecTime().size());
        System.out.println("Number of jobs that do not have execution end time: "+getNoEndExecTime().size());
        System.out.println("Number of jobs that has execution time: "+execTime.size());
    }

    //convert string to LocalDateTime
    public LocalDateTime processTime(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime ldt = LocalDateTime.parse(s, formatter);
        return ldt;
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

}
