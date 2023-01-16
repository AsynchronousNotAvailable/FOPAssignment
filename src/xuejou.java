import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
public class xuejou {
    public static void main(String[] args) {
        int cntAccessQos=0,cntIvdQos=0,cntSV=0,cntLookupFailure=0,cntIvdNode=0,cntRqJobId=0,cntNodeUMHPC=0,cntNode15=0,cntNode13=0,cntNode12=0,cntSTimeout=0,cntLaunchF=0,cntSetupF=0,cntIvdJobId=0,cntZeroBytes=0,cntKillTaskF=0,cntTresRunSecs=0,cntCPUCnt=0,cntNotExecveJob=0,cntAbortingJobId=0,cntSocketTimedOut=0,cntGresUdf=0,cntUserNotFound=0,cntXConfigureGrpTraining=0,cntJobAdy=0;
        System.out.println("Error: does not have access to qos");
        System.out.println("Username       Number of Error");
        int CntJobError=0,indexJobIDStart,indexJobIDStop;
        String ErrorJobName="";
        try{
            Scanner inputStream=new Scanner(new FileInputStream("./data/extracted_log.txt"));
            while(inputStream.hasNextLine()){
                String input=inputStream.nextLine();
                if(input.contains("error: This association")){
                    indexJobIDStart=input.indexOf("user=\'")+6;
                    indexJobIDStop=input.indexOf(", partition=")-1;
                    ErrorJobName+=input.substring(indexJobIDStart,indexJobIDStop)+" ";
                    cntAccessQos+=1;
                }
                if(input.contains("error: Invalid qos")){
                    cntIvdQos++;
                }
                if(input.contains("Security violation")||input.contains("SECURITY VIOLATION")){
                    cntSV++;
                }
                if(input.contains("lookup failure for node")){
                    cntLookupFailure++;
                }
                if(input.contains("error: node_name2bitmap")){
                    cntIvdNode++;
                }
                if(input.contains("Requeue of JobId")){
                    cntRqJobId++;
                }
                if(input.contains("Nodes umhpc not responding")){
                    cntNodeUMHPC++;
                }
                if(input.contains("Nodes cpu15 not responding")){
                    cntNode15++;
                }
                if(input.contains("Nodes cpu13 not responding")){
                    cntNode13++;
                }
                if(input.contains("Nodes cpu12 not responding")){
                    cntNode12++;
                }
                if(input.contains("Socket timed out")){
                    cntSTimeout++;
                }
                if(input.contains("Prolog launch failure")){
                    cntLaunchF++;
                }
                if(input.contains("Prolog or job env setup failure")){
                    cntSetupF++;
                }
                if(input.contains("Invalid job id specified")){
                    cntIvdJobId++;
                }
                if(input.contains("Zero Bytes were transmitted or received")){
                    cntZeroBytes++;
                }
                if(input.contains("Kill task failed")){
                    cntKillTaskF++;
                }
                if(input.contains("used_tres_run_secs underflow")){
                    cntTresRunSecs++;
                }
                if(input.contains("Configured cpu count change on cpu")){
                    cntCPUCnt++;
                }
                if(input.contains("Slurmd could not execve job")){
                    cntNotExecveJob++;
                }
                if(input.contains("Aborting JobId")){
                    cntAbortingJobId++;
                }
                if(input.contains("Socket timed out on send/recv operation")){
                    cntSocketTimedOut++;
                }
                if(input.contains("error: gres/gpu")){
                    cntGresUdf++;
                }
                if(input.contains("User 548300548 not found")){
                    cntUserNotFound++;
                }
                if(input.contains("Could not find configured group training")){
                    cntXConfigureGrpTraining++;
                }
                if(input.contains("Job/step already completing or completed")){
                    cntJobAdy++;
                }
            }

            String[] arrayName=ErrorJobName.split(" ");
            Map<String, Long>duplicateCount =Arrays.stream(arrayName).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
            duplicateCount.forEach((key,value) -> System.out.printf("%-15s%15s\n",key,value));
            System.out.println("Total number of errors caused by \"does not have access to qos:\" "+cntAccessQos);
            System.out.println("------------------------------------------------------------------");
            System.out.printf("%-45s%-10s\n","Types of error","Numbers of error");
            System.out.printf("%-45s%-10d\n","Invalid qos",cntIvdQos);
            System.out.printf("%-45s%-10d\n","Security violation",cntSV);
            System.out.printf("%-45s%-10d\n","Lookup failure for node",cntLookupFailure);
            System.out.printf("%-45s%-10d\n","Invalid node specified",cntIvdNode);
            System.out.printf("%-45s%-10d\n","Requeue of JobId",cntRqJobId);
            System.out.printf("%-45s%-10d\n","Nodes UMHPC not responding",cntNodeUMHPC);
            System.out.printf("%-45s%-10d\n","Nodes CPU15 not responding",cntNode15);
            System.out.printf("%-45s%-10d\n","Nodes CPU13 not responding",cntNode13);
            System.out.printf("%-45s%-10d\n","Nodes CPU12 not responding",cntNode12);
            System.out.printf("%-45s%-10d\n","Socket timed out",cntSTimeout);
            System.out.printf("%-45s%-10d\n","Profog launch failure",cntLaunchF);
            System.out.printf("%-45s%-10d\n","Prolog or job env setup failure",cntSetupF);
            System.out.printf("%-45s%-10d\n","Invalid jobId specified",cntIvdJobId);
            System.out.printf("%-45s%-10d\n","Zero bytes are transmitted or received",cntZeroBytes);
            System.out.printf("%-45s%-10d\n","Kill tasked failed",cntKillTaskF);
            System.out.printf("%-45s%-10d\n","Used tres run secs underflow",cntTresRunSecs);
            System.out.printf("%-45s%-10d\n","Configured cpu count change",cntCPUCnt);
            System.out.printf("%-45s%-10d\n","Slurmd could not execve job",cntNotExecveJob);
            System.out.printf("%-45s%-10d\n","Aborting jobId",cntAbortingJobId);
            System.out.printf("%-45s%-10d\n","Socket timed out on send/recv operation",cntSocketTimedOut);
            System.out.printf("%-45s%-10d\n","Gres count underflow",cntGresUdf);
            System.out.printf("%-45s%-10d\n","User 548300548 not found",cntUserNotFound);
            System.out.printf("%-45s%-10d\n","Could not find configured group training",cntXConfigureGrpTraining);
            System.out.printf("%-45s%-10d\n","Job/step already completing or completed",cntJobAdy);




        }catch(FileNotFoundException e){
            System.out.println("File was not found.");
        }
    }

}