/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Dranken.Drank;
import Dranken.DrankenDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Ruben
 */
public class GUI extends Application {
    DrankenDAO dranken;        
    DrankenTabel tabel;
    DrankenTabel vorige;
    DrankenTabel volgende;
    TableView<Drank> aanpasTabel;
    
    @Override
    public void start(Stage primaryStage) {
       // primaryStage.setResizable(false);
       try {
           File data = new File("data.dat");
           if(data.exists()){
           ObjectInputStream Oin = new ObjectInputStream(new FileInputStream(data));
           dranken = (DrankenDAO) Oin.readObject();
           }
           else{
               dranken = new DrankenDAO();
           }
           
       } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
                  
                  
       
       
        dranken =new DrankenDAO();        
        tabel = new DrankenTabel(dranken.getList());
        vorige = new DrankenTabel(dranken.getVorigeList());
        volgende = new DrankenTabel(dranken.getVolgendeList());
        aanpasTabel = new TableView<>();      
        
        //Aanmaken van de layout
        BorderPane root = new BorderPane();
        
        
        
        //Aanmaken menubalk
        MenuBar bar = new MenuBar();
        Menu fileMenu = new Menu("_File");
        Menu editMenu = new Menu("_Edit");
        Menu historyMenu = new Menu("_History");
        MenuItem fileMenuaddItem = MenuItemBuilder.create().text("_Voeg item toe..").build();
        fileMenuaddItem.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            voegDrankToe(tabel);
        }
        });
        MenuItem fileMenuQuit = MenuItemBuilder.create().text("_Exit").build();
        fileMenu.getItems().addAll(fileMenuaddItem,fileMenuQuit);
        fileMenuQuit.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            ObjectOutputStream out = null;
            try {
                out = new ObjectOutputStream(new FileOutputStream(new File("data.dat")));
                out.writeObject(dranken);
                
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0);
            }
        }
        });
        
        bar.getMenus().addAll(fileMenu,editMenu);
        
         //Aanmaken Knoppen voor pagina's
        HBox bot = new HBox();
        root.setBottom(bot);
        Button overviewKnop = new Button("Overzicht");
        Button manualKnop = new Button("Zet Prijzen Manueel");
        bot.getChildren().addAll(overviewKnop,manualKnop);        
        
        //Aanmaken body
        Label huidigLabel = new Label("Huidige Prijzen:");
        Label vorigLabel = new Label("Vorige Prijzen:");
        Label volgendLabel = new Label("Volgende Prijzen:");
        VBox middenBox = new VBox();
        VBox linksBox = new VBox();
        VBox rechtsBox = new VBox();
        middenBox.getChildren().addAll(huidigLabel,tabel.getTabel());
        linksBox.getChildren().addAll(vorigLabel,vorige.getTabel());
        rechtsBox.getChildren().addAll(volgendLabel,volgende.getTabel());
        root.setTop(bar);
        root.setCenter(middenBox);
        root.setLeft(linksBox);
        root.setRight(rechtsBox);
        
        //Editeerbare tabel voor aanpassingen
        VBox editeerbareTabel = new VBox();
        Label nieuwePrijs = new Label("Nieuwe Prijzen:");
        aanpasTabel.setEditable(true);
        Button confirmNieuwePrijsKnop = new Button("_Bevestig Prijzen");
        Button resetNaarHuidigePrijzen = new Button("_Reset Naar Huidige Prijzen");
        Button moveToTabel = new Button("_<==");
        HBox controlsTabel = new HBox();
        controlsTabel.getChildren().addAll(confirmNieuwePrijsKnop,resetNaarHuidigePrijzen,moveToTabel);
        
        editeerbareTabel.getChildren().addAll(nieuwePrijs,aanpasTabel,controlsTabel);
        
        TableColumn<Drank, String> naamKolom = 
            new TableColumn<>("Naam");
        naamKolom.setMinWidth(100);
        naamKolom.setCellValueFactory(
            new PropertyValueFactory<>("Naam"));        
        naamKolom.setCellFactory(TextFieldTableCell.<Drank>forTableColumn());
        naamKolom.setOnEditCommit(
            (TableColumn.CellEditEvent<Drank, String> t) -> {
                ((Drank) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setNaam(t.getNewValue());
        }); 
        TableColumn<Drank, String> prijsKolom = 
            new TableColumn<>("Prijs");
        prijsKolom.setMinWidth(100);
        prijsKolom.setCellValueFactory(
            new PropertyValueFactory<>("prijsStr"));
       prijsKolom.setCellFactory(TextFieldTableCell.<Drank>forTableColumn());
       prijsKolom.setOnEditCommit(
            (TableColumn.CellEditEvent<Drank, String> t) -> {
                ((Drank) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setPrijs(Double.parseDouble(t.getNewValue()));
        });       
       TableColumn<Drank, String> borgKolom = 
            new TableColumn<>("Borg");
        borgKolom.setMinWidth(100);
        borgKolom.setCellValueFactory(
            new PropertyValueFactory<>("BorgStr"));
       borgKolom.setCellFactory(TextFieldTableCell.<Drank>forTableColumn());
       borgKolom.setOnEditCommit(
            (TableColumn.CellEditEvent<Drank, String> t) -> {
                ((Drank) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setBorg(Double.parseDouble(t.getNewValue()));
                dranken.print(dranken.getVolgende());
                
        });
        aanpasTabel.setItems(dranken.getVolgendeList());
        aanpasTabel.getColumns().addAll(naamKolom, prijsKolom, borgKolom);
        
        //Grid voor toevoegen dranken in manual tab
        /*GridPane grid = new GridPane();
        ColumnConstraints naamKolomGrid = new ColumnConstraints(20);
        ColumnConstraints prijsKolomGrid = new ColumnConstraints(20);
        ColumnConstraints borgKolomGrid = new ColumnConstraints(20);
        grid.getColumnConstraints().addAll(naamKolomGrid,prijsKolomGrid,borgKolomGrid);*/
        TableView<Drank> inputTabel = new TableView<>();
        inputTabel.setEditable(true);
        TableColumn<Drank, String> naamKolomInput = 
            new TableColumn<>("Naam");
        naamKolomInput.setMinWidth(100);
        naamKolomInput.setCellValueFactory(
            new PropertyValueFactory<>("Naam"));        
        naamKolomInput.setCellFactory(TextFieldTableCell.<Drank>forTableColumn());
        naamKolomInput.setOnEditCommit(
            (TableColumn.CellEditEvent<Drank, String> t) -> {
                ((Drank) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setNaam(t.getNewValue());
        }); 
        TableColumn<Drank, String> prijsInput = 
            new TableColumn<>("Prijs");
        prijsInput.setMinWidth(100);
        prijsInput.setCellValueFactory(
            new PropertyValueFactory<>("prijsStr"));
       prijsInput.setCellFactory(TextFieldTableCell.<Drank>forTableColumn());
       prijsInput.setOnEditCommit(
            (TableColumn.CellEditEvent<Drank, String> t) -> {
                ((Drank) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setPrijs(Double.parseDouble(t.getNewValue()));
        });       
       TableColumn<Drank, String> borgInput = 
            new TableColumn<>("Borg");
        borgInput.setMinWidth(100);
        borgInput.setCellValueFactory(
            new PropertyValueFactory<>("BorgStr"));
       borgInput.setCellFactory(TextFieldTableCell.<Drank>forTableColumn());
       borgInput.setOnEditCommit(
            (TableColumn.CellEditEvent<Drank, String> t) -> {
                ((Drank) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setBorg(Double.parseDouble(t.getNewValue()));
        });
        ObservableList<Drank> list = FXCollections.observableArrayList(
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0));
        inputTabel.setItems(list);
        inputTabel.getColumns().addAll(naamKolomInput,prijsInput,borgInput);
        VBox input = new VBox();
        Label inputLabel = new Label("Voeg Dranken Toe:");
        input.getChildren().addAll(inputLabel,inputTabel);
        
        
        
        Scene scene = new Scene(root, 1000, 500);
        
        primaryStage.setTitle("Wallstreet Mainframe");
        primaryStage.setScene(scene);
        primaryStage.show();
       
        
        //ActionEvents voor knoppen        
        overviewKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {   
                root.setLeft(linksBox);             
                root.setCenter(middenBox);
                root.setRight(rechtsBox);   
                updateTabellen();
                for(Drank d:dranken.getVolgende()){
                    System.out.println(d);
                }
            }
        });
        
        manualKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.setCenter(editeerbareTabel);
                root.setLeft(middenBox);
                root.setRight(input);
                updateTabellen();
                
            }
        });
        confirmNieuwePrijsKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dranken.pushVolgendeNaarHuidige();
                updateTabellen();
            }
        });
                
        resetNaarHuidigePrijzen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dranken.pushHuidigeNaarVolgende();
                updateTabellen();
                
            }
        });
        moveToTabel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dranken.voegListToeAanVolgende(inputTabel.getItems());
                ObservableList<Drank> list = FXCollections.observableArrayList(
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0),
                new Drank("",0,0));
                inputTabel.setItems(list);
                updateTabellen();
            }
        });        
        
    }
    
    private void updateTabellen(){
        tabel.update(dranken.getList());
        vorige.update(dranken.getVorigeList());     
        volgende.update(dranken.getVolgendeList());
        aanpasTabel.setItems(dranken.getVolgendeList());
        volgende.getTabel().setItems(dranken.getVolgendeList());
    }

    private void voegDrankToe(DrankenTabel tabel){
        Stage toev = new Stage();
        GridPane grid = new GridPane();
        Label naamLabel = new Label("Naam:");
        TextField naamText= new TextField("Lekker Drankje");
        naamLabel.setLabelFor(naamText);
        Label prijsLabel = new Label("Prijs:");
        TextField prijsText= new TextField("Niet te duur?");
        naamLabel.setLabelFor(naamText);
        Label borgLabel = new Label("Borg:");
        TextField borgText= new TextField("Is het een duur glas?");
        naamLabel.setLabelFor(naamText);
        Button toevKnop = new Button("_Voeg Toe");
        GridPane.setConstraints(naamLabel, 0, 0);
        GridPane.setConstraints(naamText, 1, 0);
        GridPane.setConstraints(prijsLabel, 0, 1);
        GridPane.setConstraints(prijsText, 1, 1);
        GridPane.setConstraints(borgLabel, 0, 2);
        GridPane.setConstraints(borgText, 1, 2);        
        GridPane.setConstraints(toevKnop, 0, 3,3,1,HPos.CENTER,VPos.CENTER);
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.getChildren().addAll(naamLabel,naamText,prijsLabel,prijsText,borgLabel,borgText,toevKnop);
        toev.setTitle("Drank Toevoegen");
        toevKnop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dranken.addDrank(new Drank(naamText.getText(),Double.parseDouble(prijsText.getText()),Double.parseDouble(borgText.getText())));
                toev.close();
                tabel.update(dranken.getList());                
            }
        });
        
        
        
        Scene scene = new Scene(grid,225,180);
        toev.setScene(scene);
        toev.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
