import javax.swing.*;
import Extract.OpenFile;
import org.jfree.ui.RefineryUtilities;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        selection();


//        do {
//
//            System.out.println("Start of System");
//            System.out.println("-------------------------------------");
//            System.out.println("Enter which metrices you would like to generate: ");
//            System.out.println("Press '1', '2', '3', or '4' to generate the following metrices");
//            System.out.println("1. Generate the following jobIDs, Start Time, End Time and Find number of jobs being created within the given time range.");
//            System.out.println("2. Find number of jobs by partition, i.e. EPYC, Opteron and GPU");
//            System.out.println("3. Number of jobs causing error the corresponding user.");
//            System.out.println("4. Average execution time of jobs");
//            System.out.println("-1. to exit");
//
//            String num = sc.next();
//            if (num.equals("1")) {
//                metrics1 obj = new metrics1();
//                System.out.println("Enter which option you would like proceed: ");
//                System.out.println("Press '1', '2' to proceed: ");
//                System.out.println("1. Generate number of jobs created and ended within given time range: ");
//                System.out.println("2. Enter jobId, check it's time stamp submission and completion status");
//                char choose = option.nextLine().charAt(0);
//                if(choose == '1'){
//                    obj.option1();
//                }
//                else if(choose == '2'){
//                    obj.option2();
//                }
//                else{
//                    System.out.println("Wrong code entered");
//                    break;
//                }
//
//            } else if (num.equals("2")) {
//                metrics2 obj = new metrics2();
//                System.out.println("Enter which option you would like proceed: ");
//                System.out.println("Press '1', '2' to proceed: ");
//                System.out.println("1. Generate number of jobs by partition: ");
//                System.out.println("2. Enter jobId, find its respective partition");
//                System.out.println("3. Enter partition, generate jobID that uses that partition");
//                char choose = option.nextLine().charAt(0);
//                switch (choose){
//                    case '1': obj.generateNumJobByPartition();break;
//                    case '2': obj.findPartitionByJobID();break;
//                    case '3': obj.findJobIDByPartition();break;
//                }
//
//            } else if (num.equals("3")) {
//                metrics3 obj = new metrics3();
//                metrics5 obj2 = new metrics5();
//                System.out.println("Enter which option you would like proceed: ");
//                System.out.println("Press '1', '2' to proceed: ");
//                System.out.println("1. Generate number of jobs causing error and its corresponding user: ");
//                System.out.println("2. Generate all types of error and it's quantity");
//                char choose = option.nextLine().charAt(0);
//                switch (choose){
//                    case '1': obj.findError();
//                    case '2': obj2.findErrorType();
//                }
//
//            } else if (num.equals("4")) {
//                metrics4 obj = new metrics4();
//                System.out.println("Enter which option you would like proceed: ");
//                System.out.println("Press '1', '2' to proceed: ");
//                System.out.println("1. Display jobID and their respective execution time: ");
//                System.out.println("2. Enter jobID, find the execution time of that job Id");
//                System.out.println("3. Calculate average execution time for certain time range");
//                char choose = option.nextLine().charAt(0);
//                switch (choose){
//                    case '1': obj.displayAvgExecTime();break;
//                    case '2': obj.findExecTimeByJobID();break;
//                    case '3': obj.AvgExecTimeWithTime();break;
//                }
//
//
//            } else if(num.equals("-1")){
//               System.exit(0);
//            }
//        }while(true);




    }



    public static void selection(){
        Scanner sc = new Scanner(System.in);
        Scanner option = new Scanner(System.in);
        OpenFile openFile = new OpenFile();



        String[] options = {"1", "2", "3", "4", "Exit"};
        var selection = JOptionPane.showOptionDialog(null, "1. Generate the following jobIDs, Start Time, End Time and Find number of jobs being created within the given time range.\n2. Find number of jobs by partition, i.e. EPYC, Opteron and GPU\n3. Number of jobs causing error the corresponding user.\n4. Average execution time of jobs", "slurm",
                0, 3, null, options, options[0]);
        do {

            switch (selection) {
                case 0:
                    while(selection != 2) {

                        String[] pick1List = {"1", "2", "Back"};
                        var pick = JOptionPane.showOptionDialog(null, "1. Generate number of jobs created and ended within given time range: \n2. Enter jobId, check it's time stamp submission and completion status", "Slurm",
                                0, 3, null, pick1List, pick1List[0]);

                        switch (pick) {

                            case 0:
                                metrics1 obj1 = new metrics1();
                                obj1.option1();
                                String[] generate1 = {"Visualize", "Back"};
                                var show = JOptionPane.showOptionDialog(null, "Generate Scatter plot for number of jobs created and ended within time range", "Slurm",
                                        0, 3, null, generate1, generate1[0]);
                                switch (show){
                                    case 0:
                                        metrics1ScatterPlot example = new metrics1ScatterPlot("Scatter Plot");
                                        example.pack();
                                        RefineryUtilities.centerFrameOnScreen(example);
                                        example.setVisible(true);
                                        break;

                                    case 1:
                                        selection();
                                        break;
                                }

                            break;
                            case 1:
                                metrics1 obj2 = new metrics1();
                                obj2.option2();
                                break;
                            case 2:
                                selection();
                                break;
                        }

                    }
                    break;

                case 1:
                    metrics2 obj2 = new metrics2();
                    String[] pick2List = {"1", "2", "3", "Back"};
                    var pick2 = JOptionPane.showOptionDialog(null, "1. Generate number of jobs by partition: \n2. Enter jobId, find its respective partition\n3. Enter JobID, show its partition", "slurm",
                            0, 3, null, pick2List, pick2List[0]);
                    switch (pick2) {
                        case 0:
                            obj2.generateNumJobByPartition();
                            String[] generate1 = {"Visualize", "Back"};
                            var show = JOptionPane.showOptionDialog(null, "Generate Pie Chart to show number of jobs based on partition", "Slurm",
                                    0, 3, null, generate1, generate1[0]);
                            switch (show){
                                case 0:
                                    metrics2_pieChart chart = new metrics2_pieChart("Pie Chart", "Number of jobs by partitions");
                                    chart.pack();
                                    chart.setVisible(true);
                                    break;

                                case 1:
                                    selection();
                                    break;
                            }
                            break;
                        case 1:
                            obj2.findPartitionByJobID();
                            break;
                        case 2:
                            obj2.findJobIDByPartition();
                            break;
                        case 3:
                            selection();
                    }
                    break;


                case 2:
                    metrics3 obj3 = new metrics3();
                    metrics5 obj5 = new metrics5();
                    String[] pick3List = {"1", "2", "Back"};
                    var pick3 = JOptionPane.showOptionDialog(null, "1. Generate number of jobs causing error and its corresponding user: \n2. Generate all types of error and it's quantity", "slurm",
                            0, 3, null, pick3List, pick3List[0]);
                    switch (pick3) {
                        case 0:
                            obj3.findError();
                            obj3.displayError();
                            String[] generate1 = {"Visualize", "Back"};
                            var show = JOptionPane.showOptionDialog(null, "Generate Bar Chart to show number of job errors and its corresponding user", "Slurm",
                                    0, 3, null, generate1, generate1[0]);
                            switch (show){
                                case 0:
                                    metrics3BarChart chart = new metrics3BarChart("Job Errors Statistics",
                                            "Number of Job Errors By its User");
                                    chart.pack( );
                                    RefineryUtilities.centerFrameOnScreen( chart );
                                    chart.setVisible( true );
                                    break;

                                case 1:
                                    selection();
                                    break;
                            }
                            break;
                        case 1:
                            obj5.findErrorType();
                            break;
                        case 2:
                            selection();

                    }
                    break;

                case 3:
                    metrics4 obj4 = new metrics4();
                    String[] pick4List = {"1", "2", "3", "Back"};
                    var pick4 = JOptionPane.showOptionDialog(null, "1. Display jobID and their respective execution time: \n2. Enter jobID, find the execution time of that job Id\n3. Calculate average execution time for certain time range", "slurm",
                            0, 3, null, pick4List, pick4List[0]);
                    switch (pick4) {
                        case 0:
                            obj4.displayAvgExecTime();
                            String[] generate1 = {"Visualize", "Back"};
                            var show = JOptionPane.showOptionDialog(null, "Generate Line Graph to show average execution time from June to December", "Slurm",
                                    0, 3, null, generate1, generate1[0]);
                            switch (show){
                                case 0:
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            new metrics4LineGraph().setVisible(true);
                                        }
                                    });
                                    break;

                                case 1:
                                    selection();
                                    break;
                            }
                            break;
                        case 1:
                            obj4.findExecTimeByJobID();
                            break;
                        case 2:
                            obj4.AvgExecTimeWithTime();
                            break;
                        case 3:
                            selection();

                    }
                    break;

                case 4:
                    System.exit(0);
            }
        }while (selection!=4);

    }

}
