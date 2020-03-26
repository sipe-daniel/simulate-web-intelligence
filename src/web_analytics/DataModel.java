/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web_analytics;

/**
 *
 * @author jordan
 */
public class DataModel {
    private String index;
    private String nameWebSite;
    private String date;
    private String namePage;
    private String category;
    
    public DataModel(){
    }
    
    public DataModel( int pIndex, String pNameWebSite,String pNamePage,String pDate,String pCategory){
        this.index = String.format("%s", pIndex);
        this.nameWebSite = pNameWebSite;
        this.namePage = pNamePage;
        this.category = pCategory;
        this.date = pDate; 
    }
    public String getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = String.format("%s",index);
    }

    public String getNameWebSite() {
        return nameWebSite;
    }

    public void setNameWebSite(String nameWebSite) {
        this.nameWebSite = nameWebSite;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNamePage() {
        return namePage;
    }

    public void setNamePage(String namePage) {
        this.namePage = namePage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
  
}


