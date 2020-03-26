/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web_analytics;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;

/**
 *
 * @author jordan
 */
public class SqlConnector {
   
    private static SqlConnector Instance;
    private static Connection conn = null;
    private String dbUser;
    private String dbHost;
    private String dbPassword;
    private String dbName;
    private String dbPort;

    //constructeur
    SqlConnector() throws SQLException{
        loadDbProperties();
        connect();
    }

    public void loadDbProperties() {
        dbHost = "3306";
        dbPassword = "jordan96";
        dbName = "web_Analytics";
        dbPort = "3306";
        dbUser = "root";
    }
    //fonction de connection

    private boolean connect() throws SQLException{
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_Analytics","root","jordan96");
        }

        catch (ClassNotFoundException e) 
        {
            System.err.println("Pilote non trouvé");
            return false;

        }
        catch (SQLException e) {
            System.err.println("Connection impossible");
            System.err.println(e);
            return false;
        }
        return true;
    }
    /**
     * SqlConnector : 
     * @return
     * @throws SQLException 
     */
    public static SqlConnector getInstance() throws SQLException
    {
        if (Instance == null)
            Instance = new SqlConnector();
        return Instance;
    }

    private int sqlInsert(String s)
    {
        if (conn != null) 
        {
            Statement query;
            try 
            {
                query = conn.createStatement();
  
                query.executeUpdate(s);
                return 0;
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                return -1;
            }
        }
    return -1;
    }

    public ResultSet sqlSelect(String s)
    {
        if (conn != null) 
        {
            Statement query;
            try 
            {
                query = conn.createStatement();
                System.out.println("marche");
                return query.executeQuery(s);
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public void createdWebsite(String nameWebSite,String nameCategory, Date ourJavaDateObject) {
      int resultOperation = this.sqlInsert("INSERT INTO siteweb(nomsite,nom_category,date) VALUES('"+nameWebSite+"','"+nameCategory+"','"+ourJavaDateObject+"')");
      if(resultOperation==0)
        System.out.println("operation sucess");
    }

    public void createdCategory(String nameCategory) { 
       this.sqlInsert("INSERT INTO websitecategory( nomcategory) VALUES('"+nameCategory+"')");
    }

    public void createdPage(String namePage ,String nameWebSite) { 
       this.sqlInsert("INSERT INTO page(nompage,nom_site) VALUES('"+namePage+"','"+nameWebSite+"')");
    }	
}
