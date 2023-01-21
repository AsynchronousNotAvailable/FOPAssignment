import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class metrics4LineGraph extends JFrame { // the class extends the JFrame class

    public metrics4LineGraph() {   // the constructor will contain the panel of a certain size and the close operations
        super("Line Graph"); // calls the super class constructor

        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createChartPanel() { // this method will create the chart panel containin the graph
        String chartTitle = "Average Execution Time from June to December";
        String xAxisLabel = "Month";
        String yAxisLabel = "Average Execution Time";

        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
                xAxisLabel, yAxisLabel, dataset);

        customizeChart(chart);

        // saves the chart as an image files
        File imageFile = new File("XYLineChart.png");
        int width = 640;
        int height = 480;

        try {
            ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
        } catch (IOException ex) {
            System.err.println(ex);
        }

        return new ChartPanel(chart);
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


    public LinkedHashMap<LocalDateTime, Integer> invoker(LocalDateTime start, LocalDateTime end, ArrayList<LocalDateTime> jobTimeList){
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
        averageExecforEachMonth obj = new averageExecforEachMonth();
        obj.findExecTime();
        // this method creates the data as time seris
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("");


        series1.add(6, obj.avgJune);
        series1.add(7, obj.avgJuly);
        series1.add(8, obj.avgAug);
        series1.add(9, obj.avgSep);
        series1.add(10, obj.avgOct);
        series1.add(11, obj.avgNov);
        series1.add(12, obj.avgDec);


        dataset.addSeries(series1);


        return dataset;
    }

    private void customizeChart(JFreeChart chart) {   // here we make some customization
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // sets paint color for each series
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);

        // sets thickness for series (using strokes)
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));

        // sets paint color for plot outlines
        plot.setOutlinePaint(Color.BLUE);
        plot.setOutlineStroke(new BasicStroke(2.0f));

        // sets renderer for lines
        plot.setRenderer(renderer);

        // sets plot background
        plot.setBackgroundPaint(Color.WHITE);

        // sets paint color for the grid lines
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new metrics4LineGraph().setVisible(true);
            }
        });
    }
}