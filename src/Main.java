import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Start of System");
        System.out.println("-------------------------------------");
        System.out.println("Enter which metrices you would like to generate: ");
        System.out.println("Press '1', '2', '3', or '4' to generate the following metrices");
        System.out.println("1. Generate the following jobIDs, Start Time, End Time and Find number of jobs being created within the given time range.");
        System.out.println("2. Find number of jobs by partition, i.e. EPYC, Opteron and GPU");
        System.out.println("3. Number of jobs causing error the corresponding user.");
        System.out.println("4. Average execution time of jobs");

        char num = sc.next().charAt(0);
        if(num == '1'){
            optimised1 obj = new optimised1();
        }
        else if(num == '2'){
            metrics2 obj = new metrics2();
        }
        else if(num == '3'){
            metrics3 obj = new metrics3();
        }
        else if(num == '4'){
            metrics4 obj= new metrics4();
        }


//        metrics2 findPartition= new metrics2();
//        findPartition.readFile();

//        metrics3 findJobError = new metrics3();
//        findJobError.findError();

//        metrics4 excecutionTime = new metrics4();
//        excecutionTime.findExecTime();


//        System.out.println(excecutionTime.startExecTime.size());
//        System.out.println(excecutionTime.endExecTime.size());
//        System.out.println(excecutionTime.noEndExecTime.size());
//        metrics4a obj = new metrics4a();
//        obj.findExecTime();


//        metrics1a obj = new metrics1a();
//        obj.findings();
//        System.out.println(obj.getStartTimeOnlyList().size());
//        System.out.println(obj.getEndTimeOnlyList().size());


//        System.out.println(obj.getStartLdtWithJobId());
//        System.out.println(obj.getEndLdtWithJobId());




    }
}
