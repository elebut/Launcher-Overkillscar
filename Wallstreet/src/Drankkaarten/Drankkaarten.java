import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import Drankkaarten.Grafiek;
 
public class Drankkaarten extends Application {

 
    @Override public void start(Stage stage) {
        stage.setTitle("Wallstreet Drankkaarten");
        Grafiek graf = new Grafiek();
        
        Scene scene  = new Scene(graf.getGrafiek(),800,600);
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}