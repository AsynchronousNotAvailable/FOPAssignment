import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class metrics1ScatterPlot extends ApplicationFrame {

    public metrics1ScatterPlot(String title) {
        super(title);

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    public long[] differenceInManyTime(LocalDateTime start, LocalDateTime end){
        long [] timeDiffArr = new long [5];
        timeDiffArr[0] = Duration.between(start, end).toMillis();
        timeDiffArr[1] = Duration.between(start, end).toSeconds();
        timeDiffArr[2] = Duration.between(start, end).toMinutes();
        timeDiffArr[3] = Duration.between(start, end).toHours();
        timeDiffArr[4] = Duration.between(start, end).toDays();

        return timeDiffArr;
    }


    public LinkedHashMap<LocalDateTime, Integer> invoker(LocalDateTime start, LocalDateTime end, ArrayList<LocalDateTime>jobTimeList){
        LinkedHashMap<LocalDateTime, Integer> sessionNumJob= new LinkedHashMap<>();
        long perSession = differenceInManyTime(start, end)[4]/10; String useWhat = "Days";
        if(differenceInManyTime(start, end)[4]/30 == 0){
            perSession = (differenceInManyTime(start, end)[3])/ 30;
            useWhat = "Hours";
        }
        if(differenceInManyTime(start, end)[3]/30 == 0){
            perSession = (differenceInManyTime(start, end)[2]) /30;
            useWhat = "Minutes";
        }
        if(differenceInManyTime(start, end)[2]/30 == 0){
            perSession = (differenceInManyTime(start, end)[1]) /30;
            useWhat = "Seconds";
        }
        if(differenceInManyTime(start, end)[1]/30 == 0){
            perSession = (differenceInManyTime(start, end)[0]) /30;
            useWhat = "Mills";
        }

        System.out.println(perSession);
        System.out.println(useWhat);


        int count = 0;

        switch (useWhat){
            case "Days":
                while(start.isBefore(end)){
                    LocalDateTime checkpoint = start.plusDays(perSession); //zhuyi
                    int numJobOfSession = 0;
                    for(int i = count; i < jobTimeList.size(); i++){
                        if(jobTimeList.get(i).isBefore(checkpoint)){
                            numJobOfSession++;
                            count++;
                        }
                        System.out.println(count);
                    }


                    sessionNumJob.put(checkpoint, numJobOfSession);
                    start = checkpoint;

                };
                break;
            case "Hours":
                while(start.isBefore(end)){
                    LocalDateTime checkpoint = start.plusHours(perSession); //zhuyi
                    int numJobOfSession = 0;
                    for(int i = count; i < jobTimeList.size(); i++){
                        if(jobTimeList.get(i).isBefore(checkpoint)){
                            numJobOfSession++;
                            count++;
                        }
                        System.out.println(count);
                    }


                    sessionNumJob.put(checkpoint, numJobOfSession);
                    start = checkpoint;

                };
                break;

            case "Minutes":
                while(start.isBefore(end)){
                    LocalDateTime checkpoint = start.plusMinutes(perSession); //zhuyi
                    int numJobOfSession = 0;
                    for(int i = count; i < jobTimeList.size(); i++){
                        if(jobTimeList.get(i).isBefore(checkpoint)){
                            numJobOfSession++;
                            count++;
                        }
                        System.out.println(count);
                    }


                    sessionNumJob.put(checkpoint, numJobOfSession);
                    start = checkpoint;

                };
                break;

            case "Seconds":
                while(start.isBefore(end)){
                    LocalDateTime checkpoint = start.plusSeconds(perSession); //zhuyi
                    int numJobOfSession = 0;
                    for(int i = count; i < jobTimeList.size(); i++){
                        if(jobTimeList.get(i).isBefore(checkpoint)){
                            numJobOfSession++;
                            count++;
                        }
                        System.out.println(count);
                    }


                    sessionNumJob.put(checkpoint, numJobOfSession);
                    start = checkpoint;

                };
                break;

            case "Mills":
                while(start.isBefore(end)){
                    LocalDateTime checkpoint = start.plusNanos(perSession); //zhuyi
                    int numJobOfSession = 0;
                    for(int i = count; i < jobTimeList.size(); i++){
                        if(jobTimeList.get(i).isBefore(checkpoint)){
                            numJobOfSession++;
                            count++;
                        }
                        System.out.println(count);
                    }


                    sessionNumJob.put(checkpoint, numJobOfSession);
                    start = checkpoint;

                };
                break;
        }
        return sessionNumJob;
    }


    private XYDataset createDataset() {
        metrics1 obj = new metrics1();
        obj.processTime();
        System.out.println(obj.getStartTimeList());
        System.out.println(obj.getEndTimeList());

        ArrayList<LocalDateTime> startList = obj.getStartTimeList();
        ArrayList<LocalDateTime>  endList = obj.getEndTimeList();
        LocalDateTime jobCreateStartTime = startList.get(0);
        LocalDateTime jobCreateEndTime = startList.get(startList.size() - 1);

        LocalDateTime jobEndStartTime = endList.get(0);
        LocalDateTime jobEndFinishTime = endList.get(endList.size()-1);


        LinkedHashMap<LocalDateTime, Integer> startSessionNumJob = new LinkedHashMap<>();
        LinkedHashMap<LocalDateTime, Integer> endSessionNumJob = new LinkedHashMap<>();

        startSessionNumJob = invoker(jobCreateStartTime, jobCreateEndTime, startList);
        endSessionNumJob = invoker(jobEndStartTime, jobEndFinishTime, endList);


        System.out.println(startList.size());
        System.out.printf("Checkpoint\t\tNumber of Jobs Started\n");
        for(LocalDateTime checkpoint: startSessionNumJob.keySet()){
            System.out.printf("%s\t\t%s\n", checkpoint, startSessionNumJob.get(checkpoint));
        }


        System.out.printf("Checkpoint\t\tNumber of Jobs ended\n");
        for(LocalDateTime checkpoint: endSessionNumJob.keySet()){
            System.out.printf("%s\t\t%s\n", checkpoint, endSessionNumJob.get(checkpoint));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Jobs created");
        XYSeries series2 = new XYSeries("Jobs ended");
        for(LocalDateTime checkpoint: startSessionNumJob.keySet()){
            series1.add(checkpoint.toEpochSecond(ZoneOffset.UTC), startSessionNumJob.get(checkpoint));
        }

        for(LocalDateTime checkpoint: endSessionNumJob.keySet()){
            series2.add(checkpoint.toEpochSecond(ZoneOffset.UTC), endSessionNumJob.get(checkpoint));
        }


        dataset.addSeries(series1);
        dataset.addSeries(series2);
        return dataset;
    }













    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Scatter Plot of Number of Jobs Created/Ended within given time range",
                "Time in seconds(after epoch)", "Number of jobs", dataset);
        return chart;
    }

    public static void main(String[] args) {
        metrics1ScatterPlot example = new metrics1ScatterPlot("Scatter Plot Example");
        example.pack();
        RefineryUtilities.centerFrameOnScreen(example);
        example.setVisible(true);
    }
}
