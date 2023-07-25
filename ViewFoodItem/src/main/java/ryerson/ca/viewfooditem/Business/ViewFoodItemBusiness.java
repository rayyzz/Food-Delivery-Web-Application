/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.viewfooditem.Business;

import ryerson.ca.viewfooditem.Helper.FoodItemsXML;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import ryerson.ca.viewfooditem.Helper.FoodItem;
import ryerson.ca.viewfooditem.Persistence.FoodItem_CRUD;

/**
 *
 * @author student
 */
public class ViewFoodItemBusiness {
    
    public FoodItemsXML getFoodItemsByQuery(String query){
       Set<FoodItem> fooditems = FoodItem_CRUD.searchForFoodItems(query);
       Map<Integer ,FoodItem> foodList= new HashMap ();
           System.out.println("&&&&&&&&&&&&&&&&&&&&&&"+ fooditems.size());
        for(FoodItem f : fooditems){
            foodList.put(f.getID(), f);
            
        }
        System.out.println("**********************"+ foodList.size());
        FoodItemsXML bs;
        bs = new FoodItemsXML();
        bs.setFoodItem(new ArrayList(foodList.values()));
        return (bs);
    }
    
      
}
