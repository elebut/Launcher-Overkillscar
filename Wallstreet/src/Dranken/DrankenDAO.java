/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dranken;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Ruben
 */
public class DrankenDAO implements Serializable{
    //Set die alle huidige dranken en prijzen bijhoudt
    private TreeSet<Drank> dranken;
    //Set die alle vorige prijzen bijhoudt
    private HashMap<LocalTime,TreeSet<Drank>> history;
    private TreeSet<Drank> vorige;
    //set die alle volgende prijzen bijhoud
    private TreeSet<Drank> volgende;
    public DrankenDAO(TreeSet<Drank> dranken) {
        this.dranken = dranken;
        history = new HashMap<>();
        vorige = new TreeSet<>();
        volgende = new TreeSet<>();
        
    }

    public DrankenDAO() {
        dranken = new TreeSet<>();
        dranken.add(new Drank("Cola", 1.7,1.2));
        dranken.add(new Drank("Jupiler", 1.4, 0));
        history = new HashMap<>();
        vorige = new TreeSet<>();
        volgende = deepCopy(dranken);
    }
    
    public void addDrank(Drank d){
        dranken.add(d);
    }

    public TreeSet<Drank> getDranken(){
        return dranken;
    }
    
    public ObservableList<Drank> getList(){
        ObservableList<Drank> lijst = FXCollections.observableArrayList();
        for(Drank d:dranken){
            lijst.add(d);
        }
        return lijst;
    }
    public void pushList(){
        history.put(LocalTime.now(),dranken);
    }
    
    public TreeSet<Drank> getSet(ObservableList<Drank> lijst){
        TreeSet<Drank> temp= new TreeSet<>();
        for(Drank d:lijst){
            temp.add(d);
        }
        return temp;
    }
    
    public void updateDranken(ObservableList<Drank> lijst){
        pushList();
        dranken = new TreeSet<>(getSet(lijst));
    }
    public TreeSet<Drank> getVorige(){
        return vorige;
    }
    public ObservableList<Drank> getVorigeList(){
        ObservableList<Drank> lijst = FXCollections.observableArrayList();
        for(Drank d:vorige){
            lijst.add(d);
        }
        return lijst;
    }
    public TreeSet<Drank> getVolgende(){
        return volgende;
    }
    public ObservableList<Drank> getVolgendeList(){
        ObservableList<Drank> lijst = FXCollections.observableArrayList();
        for(Drank d:volgende){
            lijst.add(d);
        }
        return lijst;
    }
    
    
    public void print(TreeSet<Drank> set){
        for(Drank d: set){
            System.out.println(d);
        }
    }
    public void pushVolgendeNaarHuidige(){
        vorige = deepCopy(dranken);
        dranken = deepCopy(volgende);
        volgende = deepCopy(dranken);
        print(dranken);
        print(vorige);
        print(volgende);
    }
    public void pushHuidigeNaarVolgende(){
        volgende = new TreeSet<>(dranken);
    }
    
    public TreeSet<Drank> deepCopy(TreeSet<Drank> source){
        TreeSet<Drank> temp = new TreeSet<>();
        for(Drank d: source){
            temp.add(d.clone());
        }
        return temp;
    }
    
    public void voegListToeAanVolgende(ObservableList<Drank> list){
        int aantal = 0;
        for(Drank d:list){
            if(!d.getNaam().isEmpty()){
                volgende.add(d);
                aantal++;
            }
        }
        System.out.println(aantal + " Dranken toegevoegd!");
    }
}
