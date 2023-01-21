import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


//find jobId, if the time is in that month, find avg exectime
public class averageExecforEachMonth extends metrics1 {
    public long avgJune;
    public long avgJuly;
    public long avgAug;
    public long avgSep;
    public long avgOct;
    public long avgNov;
    public long avgDec;
    public averageExecforEachMonth(){

    }

    public void findExecTime(){
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader("./data/extracted_log.txt"));
            String dummy;
            while ((dummy = inputStream.readLine()) != null) {
                LocalDateTime[] array = new LocalDateTime[2];
                if (dummy.contains("sched: Allocate")) {
                    this.startOnly++;
                    String unprocessedTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length() - 1);
                    LocalDateTime processedTime = convertToLDT(formatDateTime(unprocessedTime));
                        array[0] = processedTime;
                        this.startTimeList.add(processedTime); //testing for visualization
                        String jobID = dummy.split(" ")[3].substring(6);
                        this.startLdtWithJobId.put(jobID, array);


                }

                if (dummy.contains("WEXITSTATUS")) {
                    this.endOnly++;
                    String unprocessedTime = dummy.split(" ")[0].substring(1, dummy.split(" ")[0].length() - 1);
                    LocalDateTime processedTime = convertToLDT(formatDateTime(unprocessedTime));
                        array[1] = processedTime;
                        this.endTimeList.add(processedTime); //testing for visualization
                        String jobID = dummy.split(" ")[2].substring(6);
                        if (this.startLdtWithJobId.containsKey(jobID)) {
                            this.startLdtWithJobId.get(jobID)[1] = processedTime; //if jobId exist, store in same array [startldt, endldt]
                            this.endTimeList.add(processedTime);
                        } else {
                            this.startLdtWithJobId.put(jobID, array); //if not exist, store in a new set of array [null, endldt]
                        }

                }
            }


//            System.out.println("Number of Time Stamp of sched: Allocate with job ID: "+this.startLdtWithJobId.size());
//            System.out.println("Number of Time Stamp of WEXITSTATUS with job ID: "+this.endLdtWithJobId.size());
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        long totalTimeJune = 0; int countJobsJune=0;
        long totalTimeJuly = 0; int countJobsJuly=0;
        long totalTimeAug = 0; int countJobsAug=0;
        long totalTimeSep = 0; int countJobsSep=0;
        long totalTimeOct = 0; int countJobsOct=0;
        long totalTimeNov = 0; int countJobsNov=0;
        long totalTimeDec = 0; int countJobsDec=0;


        for(String code: getStartLdtWithJobId().keySet()){
            if(getStartLdtWithJobId().get(code)[0] == null || getStartLdtWithJobId().get(code)[1] == null){
                continue;
            }
            if(getStartLdtWithJobId().get(code)[0].getMonthValue() == 6 &&getStartLdtWithJobId().get(code)[1].getMonthValue() == 6 ){
                totalTimeJune = Duration.between(getStartLdtWithJobId().get(code)[0], getStartLdtWithJobId().get(code)[1]).toMillis();
                countJobsJune++;
            }
            if(getStartLdtWithJobId().get(code)[0].getMonthValue() == 7 &&getStartLdtWithJobId().get(code)[1].getMonthValue() == 7 ){
                totalTimeJuly = Duration.between(getStartLdtWithJobId().get(code)[0], getStartLdtWithJobId().get(code)[1]).toMillis();
                countJobsJuly++;
            }
            if(getStartLdtWithJobId().get(code)[0].getMonthValue() == 8 &&getStartLdtWithJobId().get(code)[1].getMonthValue() == 8 ){
                totalTimeAug = Duration.between(getStartLdtWithJobId().get(code)[0], getStartLdtWithJobId().get(code)[1]).toMillis();
                countJobsAug++;
            }
            if(getStartLdtWithJobId().get(code)[0].getMonthValue() == 9 &&getStartLdtWithJobId().get(code)[1].getMonthValue() == 9 ){
                totalTimeSep = Duration.between(getStartLdtWithJobId().get(code)[0], getStartLdtWithJobId().get(code)[1]).toMillis();
                countJobsSep++;
            }
            if(getStartLdtWithJobId().get(code)[0].getMonthValue() == 10 &&getStartLdtWithJobId().get(code)[1].getMonthValue() == 10 ){
                totalTimeOct = Duration.between(getStartLdtWithJobId().get(code)[0], getStartLdtWithJobId().get(code)[1]).toMillis();
                countJobsOct++;
            }
            if(getStartLdtWithJobId().get(code)[0].getMonthValue() == 11 &&getStartLdtWithJobId().get(code)[1].getMonthValue() ==11 ){
                totalTimeNov = Duration.between(getStartLdtWithJobId().get(code)[0], getStartLdtWithJobId().get(code)[1]).toMillis();
                countJobsNov++;
            }
            if(getStartLdtWithJobId().get(code)[0].getMonthValue() == 12 &&getStartLdtWithJobId().get(code)[1].getMonthValue() == 12 ){
                totalTimeDec = Duration.between(getStartLdtWithJobId().get(code)[0], getStartLdtWithJobId().get(code)[1]).toMillis();
                countJobsDec++;
            }

        }
        this.avgJune = totalTimeJune/countJobsJune;
        this.avgJuly = totalTimeJuly/countJobsJuly;
        this.avgAug = totalTimeAug/countJobsAug;
        this.avgSep = totalTimeSep/countJobsSep;
        this.avgOct = totalTimeOct/countJobsOct;
        this.avgNov = totalTimeNov/countJobsNov;
        this.avgDec = totalTimeDec/countJobsDec;



//        for(String jobId: getStartLdtWithJobId().keySet()){
//
//            if(getStartLdtWithJobId().get(jobId)[0].getMonthValue() == 6 && getStartLdtWithJobId().get(jobId)[1].getMonthValue() == 6){
//                totalTimeJune += Duration.between(getStartLdtWithJobId().get(jobId)[0], getStartLdtWithJobId().get(jobId)[1]).toMillis();
//                countJobsJune++;
//            }
//
//            if(getStartLdtWithJobId().get(jobId)[0].getMonthValue() == 7 && getStartLdtWithJobId().get(jobId)[1].getMonthValue() == 7){
//                totalTimeJuly += Duration.between(getStartLdtWithJobId().get(jobId)[0], getStartLdtWithJobId().get(jobId)[1]).toMillis();
//                countJobsJuly++;
//            }
//        }



    }

    public long getAvgJune(){
        return this.avgJune;
    }


}


