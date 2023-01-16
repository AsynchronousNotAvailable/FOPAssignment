import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

public class metrics3 {

    public metrics3(){
        findError();
    }
    public void findError(){
        try{
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            String [] errorArr;
            int totalJobs = 0;
            LinkedHashMap<String, Integer> userList = new LinkedHashMap<>();
            while((dummy = inputStream.readLine())!= null){
                if(dummy.contains("error")){
//                    if(dummy.contains("user=")){
//                        System.out.println(dummy);
//                    }
                    errorArr = dummy.split(" ");
                    for(int i = 0; i<errorArr.length; i++){
                        if(errorArr[i].contains("user=")){

                            String user = errorArr[i].substring(6, errorArr[i].length()-2);
                            if(userList.containsKey(user)){
                                userList.put(user, userList.get(user)+1);
                            }
                            else{
                                userList.put(user, 1);
                            }
                        }
                    }


                }

            }


            for(String code: userList.keySet()){
                totalJobs += userList.get(code);
                System.out.printf("%-20s\t\t%s\n", code, userList.get(code));
            }

            System.out.printf("The total jobs that has error is: %s", totalJobs);
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

}


