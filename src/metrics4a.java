import java.io.*;
import java.util.LinkedHashMap;

public class metrics4a extends metrics4{
    private  LinkedHashMap<String, String> startExecTime = new LinkedHashMap<>();
    private LinkedHashMap<String, String> endExecTime = new LinkedHashMap<>();
    private LinkedHashMap<String, String> noEndExecTime = new LinkedHashMap<>();
    public metrics4a(){
        super();
        this.startExecTime = super.getStartExecTime();
        this.endExecTime = super.getEndExecTime();
        this.noEndExecTime = super.getNoEndExecTime();
    }

    public static void main(String[] args) {
        metrics4a obj = new metrics4a();
        obj.findMissingEndExec();
    }
    public void findMissingEndExec(){
        try{
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            while((dummy = inputStream.readLine())!= null){
                for(String code: this.noEndExecTime.keySet()){
                    if (dummy.contains(code)){
                        System.out.println(dummy);
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
    }


    private static String process(String s){
        if(s.contains("T")){
            s = s.replace('T', ' ');

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
}
