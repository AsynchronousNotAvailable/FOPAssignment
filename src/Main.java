import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner option = new Scanner(System.in);

        do {
            System.out.println("Start of System");
            System.out.println("-------------------------------------");
            System.out.println("Enter which metrices you would like to generate: ");
            System.out.println("Press '1', '2', '3', or '4' to generate the following metrices");
            System.out.println("1. Generate the following jobIDs, Start Time, End Time and Find number of jobs being created within the given time range.");
            System.out.println("2. Find number of jobs by partition, i.e. EPYC, Opteron and GPU");
            System.out.println("3. Number of jobs causing error the corresponding user.");
            System.out.println("4. Average execution time of jobs");
            System.out.println("-1. to exit");
            String num = sc.next();
            if (num.equals("1")) {
                metrics1 obj = new metrics1();
                System.out.println("Enter which option you would like proceed: ");
                System.out.println("Press '1', '2' to proceed: ");
                System.out.println("1. Generate number of jobs created and ended within given time range: ");
                System.out.println("2. Enter jobId, check it's time stamp submission and completion status");
                char choose = option.nextLine().charAt(0);
                if(choose == '1'){
                    obj.option1();
                }
                else if(choose == '2'){
                    obj.option2();
                }
                else{
                    System.out.println("Wrong code entered");
                    break;
                }

            } else if (num.equals("2")) {
                metrics2 obj = new metrics2();
                System.out.println("Enter which option you would like proceed: ");
                System.out.println("Press '1', '2' to proceed: ");
                System.out.println("1. Generate number of jobs by partition: ");
                System.out.println("2. Enter jobId, find its respective partition");
                System.out.println("3. Enter partition, generate jobID that uses that partition");
                char choose = option.nextLine().charAt(0);
                switch (choose){
                    case '1': obj.generateNumJobByPartition();
                    case '2': obj.findPartitionByJobID();
                    case '3': obj.findJobIDByPartition();
                }

            } else if (num.equals("3")) {
                metrics3 obj = new metrics3();
                metrics5 obj2 = new metrics5();
                System.out.println("Enter which option you would like proceed: ");
                System.out.println("Press '1', '2' to proceed: ");
                System.out.println("1. Generate number of jobs causing error and its corresponding user: ");
                System.out.println("2. Generate all types of error and it's quantity");
                char choose = option.nextLine().charAt(0);
                switch (choose){
                    case '1': obj.findError();
                    case '2': obj2.findErrorType();
                }

            } else if (num.equals("4")) {
                metrics4 obj = new metrics4();
                System.out.println("Enter which option you would like proceed: ");
                System.out.println("Press '1', '2' to proceed: ");
                System.out.println("1. Display jobID and their respective execution time: ");
                System.out.println("2. Enter jobID, find the execution time of that job Id");
                System.out.println("3. Calculate average execution time for certain time range");
                char choose = option.nextLine().charAt(0);
                switch (choose){
                    case '1': obj.displayAvgExecTime();
                    case '2': obj.findExecTimeByJobID();
                }


            } else if(num.equals("-1")){
               System.exit(0);
            }
        }while(true);




    }
}
