/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Drankkaarten;

import java.util.HashMap;
import java.util.HashSet;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Ruben
 */
public class Grafiek {
    final NumberAxis xAxis = new NumberAxis();
    final CategoryAxis yAxis = new CategoryAxis();
    final BarChart<Number,String> bc;
    private HashMap<Integer,String> types;
    
    public Grafiek(DataBeheer b){
        bc =  new BarChart<>(xAxis, yAxis);
        bc.setTitle("Drankkaarten Per Vereniging");
        xAxis.setLabel("Aantal");  
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Vereniging"); 
        types = new HashMap<>();
        types.put(0, "Chiro");
        types.put(1,"KSA");
 
       /* XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");       
        series1.getData().add(new XYChart.Data(25601.34, austria));
        series1.getData().add(new XYChart.Data(20148.82, brazil));
        series1.getData().add(new XYChart.Data(10000, france));
        series1.getData().add(new XYChart.Data(35407.15, italy));
        series1.getData().add(new XYChart.Data(12000, usa));    */ 
        b.voegToe(new Vereniging("Ruiselede","Chiro", 20));        
        b.voegToe(new Vereniging("Tielt", "Chiro", 4));
        b.voegToe(new Vereniging("Tielt","KSA",5));
        init(b.getVerenigingen());
       
    }
    
    public void addSeries(String naam, Vereniging ... v){
        XYChart.Series s = new XYChart.Series();
        s.setName(naam);
        for(Vereniging ve:v){
            s.getData().add(new XYChart.Data<>(ve.getDrankkaarten(), ve.getNaam()));
        }        
        bc.getData().add(s);
    } 
    
    public void init(HashMap<String,HashSet<Vereniging>> lijst){
        int grootte = lijst.size();
        for(String s: lijst.keySet()){
            // For lus om van HashSet naar Array te gaan
            Vereniging[] v = new Vereniging[lijst.get(s).size()];
            int j = 0;
            for(Vereniging ver:lijst.get(s)){
                v[j] = ver;
                j++;
            }
            addSeries(s,v);
        }
    }
    
    public BarChart<Number,String> getGrafiek(){
        return bc;
    }
    
}
