package Drankkaarten;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.HashSet;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;

public class Drankkaarten extends Application implements Serializable {
DataBeheer b;
    @Override
    public void start(Stage stage) throws ClassNotFoundException {   
        try{
            FileInputStream fin = new FileInputStream(new File("data.dat"));
            ObjectInputStream oin = new ObjectInputStream(fin);
            b = (DataBeheer) oin.readObject();
        }
        catch(IOException  e){
            b = new DataBeheer();
        }
        catch(ClassNotFoundException e){
            b = new DataBeheer();
        }
        
        
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
                data.setText(b.getLijstString());
                serializeData();
            }
                
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
    
    public void serializeData(){
        try{FileOutputStream fout = new FileOutputStream(new File("Data.dat")); 
        ObjectOutputStream oout = new ObjectOutputStream(fout);
        oout.writeObject(b);
        }
        catch(Exception e){
            System.out.println("FOUT!");
        }
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
