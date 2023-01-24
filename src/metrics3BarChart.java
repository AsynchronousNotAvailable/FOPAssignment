import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;


public class metrics3BarChart extends ApplicationFrame {

    public metrics3BarChart(String applicationTitle , String chartTitle ) {
        super( applicationTitle );
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Category",
                "Score",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel( barChart );
        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    private CategoryDataset createDataset( ) {
        metrics3 obj  = new metrics3();
        obj.findError();
        obj.getUserList();

        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset( );
        for(String user: obj.getUserList().keySet()){
            dataset.addValue(obj.getUserList().get(user), user, "User");
        }


        return dataset;
    }


}