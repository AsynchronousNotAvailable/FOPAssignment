import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class metrics1a {
    public LinkedHashMap<LocalDateTime, Integer> ldtList = new LinkedHashMap<>();
    public LinkedHashMap<String, LocalDateTime>startLdtWithJobId = new LinkedHashMap<>();
    public LinkedHashMap<String, LocalDateTime>endLdtWithJobId = new LinkedHashMap<>();
    public ArrayList<String> startTimeOnlyList = new ArrayList<>();
    public ArrayList<String> endTimeOnlyList = new ArrayList<>();

    public metrics1a(){
        processTime();
    }
    public void processTime(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Start Date Time: ");
        String startDateTime = sc.nextLine();
        System.out.println("Enter End Date Time: ");
        String endDateTime = sc.nextLine();

        try{

            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            while((dummy = inputStream.readLine())!=null){
                if(dummy.contains("sched: Allocate")){
                    String unprocessedTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length()-1);
                    LocalDateTime processedTime = convertToLDT(formatDateTime(unprocessedTime));
                    String jobID = dummy.split(" ")[3].substring(6);
                    this.startLdtWithJobId.put(jobID, processedTime);
                }

                if(dummy.contains("WEXITSTATUS")){
                    String unprocessedTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length()-1);
                    LocalDateTime processedTime = convertToLDT(formatDateTime(unprocessedTime));
                    String jobID = dummy.split(" ")[2].substring(6);
                    this.endLdtWithJobId.put(jobID, processedTime);

                }



            }


            System.out.println("JobID\t\tTime Stamp");
            for(String key: this.startLdtWithJobId.keySet()){
                System.out.printf("%s\t\t%s\n", key, this.startLdtWithJobId.get(key));
            }


            System.out.println("Number of Time Stamp of sched: Allocate with job ID: "+this.startLdtWithJobId.size());
            System.out.println("Number of Time Stamp of WEXITSTATUS with job ID: "+this.endLdtWithJobId.size());
        }


        catch(FileNotFoundException e){
            System.out.println(e);
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    public LinkedHashMap<String, LocalDateTime>getStartLdtWithJobId(){
        return this.startLdtWithJobId;
    }

    public LinkedHashMap<String, LocalDateTime>getEndLdtWithJobId(){
        return this.endLdtWithJobId;
    }

    public ArrayList<String> getStartTimeOnlyList(){
        return this.startTimeOnlyList;
    }

    public ArrayList<String> getEndTimeOnlyList(){
        return this.endTimeOnlyList;
    }



    public void findings(){
        //find jobID with start time
        for(String code: getStartLdtWithJobId().keySet()){
            if(!getEndLdtWithJobId().containsKey(code)){
                this.startTimeOnlyList.add(code);
//                try{
//                    BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
//                    System.out.println(code);
//                    String parse;
//                    while((parse = inputStream.readLine())!= null){
//                        if(parse.contains(code)){
//
//                            System.out.println(parse);
//
//                        }
//
//
//                    }
//                }
//                catch(FileNotFoundException e){
//                    System.out.println(e);
//                }
//                catch (IOException e){
//                    System.out.println(e);
//                }
            }


        }


//        find jobId with end time only
        for(String code: getEndLdtWithJobId().keySet()){
            if(!getStartLdtWithJobId().containsKey(code)){
                this.endTimeOnlyList.add(code);
//                try{
//                    BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
//                    String parse;
//                    while((parse= inputStream.readLine())!= null){
//                        if(parse.contains(code)){
////                            System.out.println(parse);
//                        }
//                    }
//                }
//                catch(FileNotFoundException e){
//                    System.out.println(e);
//                }
//                catch (IOException e){
//                    System.out.println(e);
//                }
            }

//                System.out.printf("%s\t\t%s\n", code, getEndLdtWithJobId().get(code));
            }
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

    public LocalDateTime convertToLDT(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime ldt = LocalDateTime.parse(s, formatter);
        return ldt;
    }
}
