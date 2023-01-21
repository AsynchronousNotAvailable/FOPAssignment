import Extract.OpenFile;

import java.io.*;
import java.util.LinkedHashMap;

public class metrics3 {
    public LinkedHashMap<String, Integer>userList = new LinkedHashMap<>();

    public metrics3(){
    }
    public void findError(){
        try{
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            String [] errorArr;
            int totalJobs = 0;
            while((dummy = inputStream.readLine())!= null){
                if(dummy.contains("error")){
//                    if(dummy.contains("user=")){
//                        System.out.println(dummy);
//                    }
                    errorArr = dummy.split(" ");
                    for(int i = 0; i<errorArr.length; i++){
                        if(errorArr[i].contains("user=")){

                            String user = errorArr[i].substring(6, errorArr[i].length()-2);
                            if(this.userList.containsKey(user)){
                                this.userList.put(user, this.userList.get(user)+1);
                            }
                            else{
                                this.userList.put(user, 1);
                            }
                        }
                    }


                }

            }



        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public void displayError(){
        try{
            PrintWriter outputStream = new PrintWriter(new FileWriter("./data/userError.txt"));
            int totalJobs = 0;
            for(String code: this.userList.keySet()){
                totalJobs += this.userList.get(code);
                outputStream.write(String.format("%-20s\t\t%s\n", code, this.userList.get(code)));
            }

            outputStream.write(String.format("The total jobs that has error is: %s\n", totalJobs));

            outputStream.flush();
            outputStream.close();
        }
        catch(IOException e){
            System.out.println(e);
        }

        OpenFile open = new OpenFile();
        open.showFile("./data/userError.txt");

    }

    public LinkedHashMap<String, Integer>getUserList(){
        return this.userList;
    }
}


