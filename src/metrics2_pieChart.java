import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import javax.swing.*;

public class metrics2_pieChart extends JFrame {

    public metrics2_pieChart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        // This will create the dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("GPU-V100s", 618);
        dataset.setValue("GPU-K10", 439);
        dataset.setValue("GPU-Titan ", 673);
        dataset.setValue("GPU-K40c ", 301);
        dataset.setValue("CPU-Opteron", 4920);
        dataset.setValue("CPU-EPYC", 3015);
        // based on the dataset we create the chart
        JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, dataset, true, true, false);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
    }

    public static void main(String[] args) {
        metrics2_pieChart chart = new metrics2_pieChart("Doughnut Chart", "Number of jobs by partitions");
        chart.pack();
        chart.setVisible(true);
    }
}