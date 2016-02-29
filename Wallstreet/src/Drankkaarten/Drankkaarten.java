
import Drankkaarten.DataBeheer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import Drankkaarten.Grafiek;
import Drankkaarten.Vereniging;
import java.util.HashMap;
import java.util.HashSet;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class Drankkaarten extends Application {
DataBeheer b;
    @Override
    public void start(Stage stage) {
        b = new DataBeheer();
        FlowPane hb = new FlowPane();
        TextField naamVereniging = new TextField();
        naamVereniging.setText("Naam Vereniging");
        TextField aantalDrankkaarten = new TextField();
        aantalDrankkaarten.setText("Aantal Drankkaarten");
        ChoiceBox soortenVereniging = new ChoiceBox();
        soortenVereniging.getItems().addAll(b.getTypes());
        Button addV = new Button();
        addV.setText("Voeg Vereniging Toe");
        Button showG = new Button();
        showG.setText("Toon Geselecteerde Grafiek");
        ChoiceBox graf = new ChoiceBox();
        graf.getItems().addAll("Taart", "Histogram");
          
        TextArea data = new TextArea();
        data.setText(b.getLijstString());
        showG.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               if(graf.getSelectionModel().getSelectedIndex() == 0){
                    startTaart();
                }
                else
                startGrafiek();
            }
        });        
        addV.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                b.voegToe(new Vereniging(naamVereniging.getText(),b.getTypes()[soortenVereniging.getSelectionModel().getSelectedIndex()],Integer.parseInt(aantalDrankkaarten.getText())));
                data.setText(b.getLijstString());}
        });
      
        
        hb.getChildren().addAll(naamVereniging, aantalDrankkaarten, soortenVereniging,graf, addV, showG,data);
        Scene scene = new Scene(hb, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void startGrafiek() {
        Grafiek graf = new Grafiek(b);
        Stage stage = new Stage();
        Scene scene = new Scene(graf.getGrafiek(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    public void startTaart(){
        Stage stage = new Stage();
        Scene scene = new Scene(createChart(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    
    protected PieChart createChart() {
        PieChart pc = new PieChart();
        HashMap<String,HashSet<Vereniging>> v = b.getVerenigingen();
        for(String s: v.keySet()){
            for(Vereniging ver:v.get(s)){
                pc.getData().add(new PieChart.Data(ver.getType() + ver.getNaam(),ver.getDrankkaarten()));
            }
        }
        
        // setup chart
        pc.setId("Drankkaarten");
        pc.setTitle("Drankkaarten");
        return pc;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
