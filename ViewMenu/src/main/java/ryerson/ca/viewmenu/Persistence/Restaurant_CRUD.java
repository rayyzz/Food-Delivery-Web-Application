/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.viewmenu.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import ryerson.ca.viewmenu.Helper.FoodItem;
import ryerson.ca.viewmenu.Helper.Restaurant;

/**
 *
 * @author student
 */
public class Restaurant_CRUD {
    
    private static Connection getCon(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("TRYING");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Menu_LBS?autoReconnect=true&useSSL=false", "root", "student");
            System.out.println("Connection established.");
        }catch(Exception e){System.out.println(e);}
        return con;
    }
    
    public static Restaurant read(String username, String password){
        Restaurant bean = null;
        try{
            Connection con = getCon();
            //READ THIS: i dont think theyre demoing but their shit just isnt working
            System.out.println(username + " " + password);
            
            String q = "select * from Restaurant where username LIKE \"" + username + "\"";
            
            //String q = "SELECT * FROM Restaurant WHERE username = " + username;
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                //bean = new UserInfo();
                String user = rs.getString("username");
                String pass = rs.getString("password");
                String category = rs.getString("category");
                int restID = rs.getInt("RestaurantID");
                
                System.out.println(user + pass + category +  restID);
                
                if(pass.equals(password)){
                    bean = new Restaurant(username, category, restID, pass);
                    // given a restaurant ID, you can find all the food items belonging to that restaurant
                    String findMenuSQL = "SELECT DISTINCT FoodItem.nameFood, FoodItem.price, FoodItem.calories, FoodItem.FoodID" +
                        "\nFROM FoodItem, Restaurant, FoodItem_Restaurant_ContainedBy" +
                        "\nWHERE Restaurant.RestaurantID = " + String.valueOf(restID) + 
                        " \nAND Restaurant.RestaurantID = FoodItem_Restaurant_ContainedBy.RestaurantID" +
                        " \nAND FoodItem.FoodID = FoodItem_Restaurant_ContainedBy.FoodID;";
                    System.out.println(findMenuSQL);
                    FoodItem_CRUD.createMenu(bean, findMenuSQL);
                }
            }
            con.close();
        }catch(Exception e){System.out.println(e);}
        return bean;
    }
    
    public static Restaurant searchForRestaurant(String query) {
        Restaurant rest = null;
        System.out.println("searchForRestaurant:");
        try{
            Connection con = getCon();
            String q = "select * from Restaurant WHERE username = \"" + query + "\"";// where username LIKE \"" + username + "\"";
            System.out.println("here:" + query);
            System.out.println(q);
            //if (!query.equals("")) q += " WHERE username LIKE \"" + query + "\""; 
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String user = rs.getString("username");
                String pass = rs.getString("password");
                String category = rs.getString("category");
                int restID = rs.getInt("RestaurantID");
                
                rest = new Restaurant(user, category, restID);
                System.out.println("searchForRestaurant RESULTS: " + rest.getName());
                
                String findMenuSQL = "SELECT DISTINCT FoodItem.nameFood, FoodItem.price, FoodItem.calories, FoodItem.FoodID" +
                    "\nFROM FoodItem, Restaurant, FoodItem_Restaurant_ContainedBy" +
                    "\nWHERE Restaurant.RestaurantID = " + String.valueOf(restID) + 
                    " \nAND Restaurant.RestaurantID = FoodItem_Restaurant_ContainedBy.RestaurantID" +
                    " \nAND FoodItem.FoodID = FoodItem_Restaurant_ContainedBy.FoodID;";
                System.out.println(findMenuSQL);
                rest = FoodItem_CRUD.createMenu(rest, findMenuSQL);
            }
            con.close();
        }catch(Exception e){System.out.println(e);}
    return rest;
    } 
    
    public static Restaurant readUser(String username){
        Restaurant bean = null;
        try{
            Connection con = getCon();
            //READ THIS: i dont think theyre demoing but their shit just isnt working
            System.out.println(username);
            
            String q = "select * from Restaurant where username =\"" + username + "\"";
            
            //String q = "SELECT * FROM Restaurant WHERE username = " + username;
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                //bean = new UserInfo();
                String user = rs.getString("username");
                String pass = rs.getString("password");
                String category = rs.getString("category");
                int restID = rs.getInt("RestaurantID");
                
                System.out.println(user + pass + category +  restID);
                bean = new Restaurant(username, category, restID);
                
                // given a restaurant ID, you can find all the food items belonging to that restaurant
                String findMenuSQL = "SELECT DISTINCT FoodItem.nameFood, FoodItem.price, FoodItem.calories, FoodItem.FoodID" +
                    "\nFROM FoodItem, Restaurant, FoodItem_Restaurant_ContainedBy" +
                    "\nWHERE Restaurant.RestaurantID = " + String.valueOf(restID) + 
                    " \nAND Restaurant.RestaurantID = FoodItem_Restaurant_ContainedBy.RestaurantID" +
                    " \nAND FoodItem.FoodID = FoodItem_Restaurant_ContainedBy.FoodID;";
                System.out.println(findMenuSQL);
                FoodItem_CRUD.createMenu(bean, findMenuSQL);
                
            }
            con.close();
        }catch(Exception e){System.out.println(e);}
        return bean;
    }
    
    
}
