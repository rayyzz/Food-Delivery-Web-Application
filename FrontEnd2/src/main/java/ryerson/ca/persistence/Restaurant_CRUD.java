/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import ryerson.ca.helper.FoodItem;
import ryerson.ca.helper.Restaurant;

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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_DB?autoReconnect=true&useSSL=false", "root", "student");
            System.out.println("Connection established.");
        }catch(Exception e){System.out.println(e);}
        return con;
    }
    
    public static boolean exists(String username, String password){
        boolean bean = false;
        try{
            Connection con = getCon();
            System.out.println(username + " " + password);
            
            String q = "select * from Restaurant where username LIKE \"" + username + "\"";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String user = rs.getString("username");
                String pass = rs.getString("password");
                String category = rs.getString("category");
                int restID = rs.getInt("RestaurantID");
                
                System.out.println(user + pass + category +  restID);
                
                if(pass.equals(password)){
                    bean = true;
                    break;
                }
            }
            con.close();
        }catch(Exception e){System.out.println(e);}
        return bean;
    }
    
}
