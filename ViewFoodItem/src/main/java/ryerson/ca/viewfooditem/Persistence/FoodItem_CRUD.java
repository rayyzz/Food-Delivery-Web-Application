/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.viewfooditem.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import ryerson.ca.viewfooditem.Helper.FoodItem;
import ryerson.ca.viewfooditem.Helper.Restaurant;

/**
 *
 * @author student
 */
public class FoodItem_CRUD {
    
    private static Connection getCon(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FoodItem_LBS?autoReconnect=true&useSSL=false", "root", "student");
            System.out.println("Connection established.");
        }catch(Exception e){System.out.println(e);}
        return con;
    }
    
    public static void createMenu(Restaurant res, String q){
        try{
            Connection con = getCon();
            
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                //been = new UserInfo();
                String name = rs.getString("nameFood");
                float price = rs.getFloat("price");
                int calories = rs.getInt("calories");
                int FoodID = rs.getInt("FoodID");
                
                res.addToMenu(new FoodItem(name, calories, FoodID, price));
            }
            con.close();
        }catch(Exception e){System.out.println(e);}
    }

    public static Set<FoodItem> searchForFoodItems(String query) {
        Set<FoodItem> res = new HashSet<FoodItem>();
        try{
            Connection con = getCon();
            String q = "SELECT * FROM FoodItem";// LIKE " + query;
            System.out.println("here:" + query);
            if (!query.equals("")) q += " WHERE nameFood LIKE \"" + query + "\""; 
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                //been = new UserInfo();
                String name = rs.getString("nameFood");
                float price = rs.getFloat("price");
                int calories = rs.getInt("calories");
                int FoodID = rs.getInt("FoodID");
                
                res.add(new FoodItem(name, calories, FoodID, price));
            }
            con.close();
        }catch(Exception e){System.out.println(e);}
    return res;
    }   
}
