/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web_analytics;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DateFormat.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author jordan
 */
public class WebsiteviewController implements Initializable {
    @FXML private TableView<DataModel> tableView;
    @FXML private TableColumn<DataModel,String> tableColumnIndexTest;
    @FXML private TableColumn<DataModel,String> tableColumnNameWebsite;
    @FXML private TableColumn<DataModel,String> tableColumnNameCategory;
    @FXML private TableColumn<DataModel,String> ColumnNamePage;
    @FXML private TableColumn<DataModel,String> Columndate;
    
    
    private List<DataModel> sendList = new ArrayList<>();
    private ObservableList<DataModel> observableList;
    
    @FXML  TextField nameWebSite;
    @FXML  TextField nameCategory;
    @FXML  TextField namePage;
    private ArrayList<ArrayList<String>> list= new ArrayList<>();
    private String[][] list1 ;
    
    private SqlConnector rs;    
    private List<String> allNameSite = new ArrayList();
    private List<String> allNamePage = new ArrayList();
    
    private void load(String namefield,String nameTable,String nameColumn) throws SQLException{
       ResultSet result = rs.sqlSelect("SELECT * FROM "+nameTable+"");
       
       if(namefield.equals("allNameSite")){
            while(result.next()){
            this.allNameSite.add(result.getString(nameColumn));
            }   
       }else{
              while(result.next()){
                this.allNamePage.add(result.getString(nameColumn));
                System.out.println(allNamePage.get(0));
            }   
       }
    }
   

    /**
     * pour verifier si l'information entrer se repete avec celle deja recuperé
     * @param paramPrincipal
     * @param paramVerified
     * @return 
     */
    private boolean verifiedOccurence(List<String> paramPrincipal,String paramVerified){
        for(String str : paramPrincipal){
            if(str.equals(paramVerified))
                return true;
        }
        return false; 
    }
    

     /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.createTableView();
    } 

    private void createTableView(){
        tableColumnIndexTest.setCellValueFactory(new PropertyValueFactory<>("index"));
        tableColumnNameWebsite.setCellValueFactory(new PropertyValueFactory<>("nameWebSite"));
        tableColumnNameCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        ColumnNamePage.setCellValueFactory(new PropertyValueFactory<>("namePage"));
        Columndate.setCellValueFactory(new PropertyValueFactory<>("date"));

        observableList = FXCollections.observableArrayList(sendList);
        tableView.setItems(observableList);
    }
    
    /**
     * pour charger les valeurs dans un tableau a parti duquel on va charger le tableview
     * @throws SQLException 
     */
    private void loadList() throws SQLException{
         ResultSet result = rs.sqlSelect("SELECT *  FROM siteweb,page  WHERE siteweb.nomsite = page.nom_site");
         
         for(int i = 0; i < 4;i++)
             list.add(i,new ArrayList<>());
       
         while(result.next()){
            this.list.get(0).add(result.getString("nomsite"));
            this.list.get(1).add(result.getString("nompage"));
            this.list.get(2).add(result.getString("date"));
            this.list.get(3).add(result.getString("nom_category"));
       }    
    }
    
    /**
     * addItemTableView : pour ajouter les valeurs  ou donnée dans le tableview  
     * @throws SQLException 
     */
    @FXML
    public void addItemTableView() throws SQLException{
        this.connectionBD();
        this.loadList();
        for(int j=0,nbTest=list.get(0).size();j < nbTest;j++){
                                                // index 0:nameWebSite   1:namepage  2:date     3:category
            DataModel c1 = new DataModel(j+1,list.get(0).get(j) ,list.get(1).get(j),list.get(2).get(j),list.get(3).get(j));
            sendList.add(c1);
        }
        
       observableList = FXCollections.observableArrayList(sendList);
       tableView.setItems(observableList);
       sendList.clear();
    }
    
   /**
    * connection a la base de données
    */
    public void connectionBD(){
        try {
            rs = SqlConnector.getInstance();
            System.out.println("connection reussi");

          } catch (SQLException ex) {
            System.out.println("connection echoue");   
         }   
    }
    public boolean verifiedfieldEmpty(String field1,String field2, String field3){
        if(field1.isEmpty()||field2.isEmpty()||field3.isEmpty()){
            return true;
        }
        return false;
    }
    
    /**
     * core : function coeur de l'application 
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException 
     */
    @FXML
    public void core() throws SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{    
              this.connectionBD();

                
              this.load("allNameSite","siteweb", "nomsite");
              this.load("allNamePage","page", "nompage");
              this.loadList();
              this.addItemTableView();
         
         Calendar calendar = Calendar.getInstance();
         java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
         if(!this.verifiedfieldEmpty(nameWebSite.getText(), nameCategory.getText(), namePage.getText()) && !this.verifiedOccurence(this.allNameSite,nameWebSite.getText())&& !this.verifiedfieldEmpty(nameWebSite.getText(), nameCategory.getText(), namePage.getText())){
            rs.createdWebsite(nameWebSite.getText(), nameCategory.getText(), ourJavaDateObject);
            rs.createdCategory(nameCategory.getText());
            rs.createdPage(this.nameWebSite.getText()+"/"+this.namePage.getText(), this.nameWebSite.getText());
         }else
            if(!this.verifiedfieldEmpty(nameWebSite.getText(), nameCategory.getText(), namePage.getText()) && !this.verifiedOccurence(this.allNamePage, this.nameWebSite.getText()+"/"+this.namePage.getText()) )
             rs.createdPage(this.nameWebSite.getText()+"/"+this.namePage.getText(), this.nameWebSite.getText());
            else if( this.verifiedOccurence(this.allNamePage, this.nameWebSite.getText()+"/"+this.namePage.getText())){
                System.out.println("page deja presente");
            }else
                System.out.println("l'un des champs entré est vide");         
    }
}
