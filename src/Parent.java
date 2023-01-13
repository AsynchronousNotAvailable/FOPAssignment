import java.util.ArrayList;

public class Parent {
    public ArrayList<String>dataset;
    public Parent(ArrayList<String> d){
        this.dataset = d;
    }

    public static void main(String[] args) {
        metrics_1 c = new metrics_1();
        System.out.println(c.numSubmit);
    }
}
