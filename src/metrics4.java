import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

public class metrics4 {
    protected LinkedHashMap<String, String> startExecTime = new LinkedHashMap<>();
    protected LinkedHashMap<String, String> endExecTime = new LinkedHashMap<>();
    protected LinkedHashMap<String, String> noEndExecTime = new LinkedHashMap<>();


    public metrics4(){
        executionTime();

    }
    public void executionTime(){
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

    public LinkedHashMap<String, String> getStartExecTime(){
       return this.startExecTime;
    }
    public LinkedHashMap<String, String> getEndExecTime(){
        return this.endExecTime;
    }
    public LinkedHashMap<String, String> getNoEndExecTime(){
        return this.noEndExecTime;
    }

}
