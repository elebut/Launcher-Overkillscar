/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Dranken.Drank;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author Ruben
 */
public class DrankenTabel  {
    private TableView tabel;
    
    public DrankenTabel(ObservableList<Drank> d){
        tabel = new TableView();
        TableColumn naamDrank = new TableColumn("Drank");
        naamDrank.setMinWidth(60);
        naamDrank.setCellValueFactory(new PropertyValueFactory<Drank,String>("naam"));
        naamDrank.setCellFactory(TextFieldTableCell.forTableColumn());
        naamDrank.setOnEditCommit(
        new EventHandler<CellEditEvent<Drank, String>>() {
        @Override
        public void handle(CellEditEvent<Drank, String> t) {
            ((Drank) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setNaam(t.getNewValue());
        }
    }
);
        TableColumn prijsDrank = new TableColumn("Prijs");
        prijsDrank.setMinWidth(20);
        prijsDrank.setCellValueFactory(new PropertyValueFactory<Drank,Double>("prijs"));
        TableColumn borgDrank = new TableColumn("Borg");
        borgDrank.setMinWidth(20);
        borgDrank.setCellValueFactory(new PropertyValueFactory<Drank,Double>("borg"));
        tabel.setItems(d);
        tabel.getColumns().addAll(naamDrank,prijsDrank,borgDrank);        
    }
    
    public TableView getTabel(){
        return tabel;
    }
    public void update(ObservableList<Drank> d){
        tabel.setItems(d);
    }

    
    
}
