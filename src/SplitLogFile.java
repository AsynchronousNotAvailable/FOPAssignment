import java.io.IOException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;


public class SplitLogFile {
    public static void main(String[] args) {
        String [] submit = {};
        LinkedHashMap <String, Integer> submitJob = new LinkedHashMap<>();



        try{
            String sentence;
            BufferedReader inputStream = new BufferedReader(new FileReader("./src/extracted_log.txt"));
            while((sentence = inputStream.readLine()) != null){
                if(sentence.contains("submit")){

                    submit = sentence.split(" ");
                    for(int i = 0; i < submit.length; i++){
                        if(submit[i].contains("JobId")){
//                            System.out.println(submit[i]);
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

        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        System.out.println(submitJob);
    }


}