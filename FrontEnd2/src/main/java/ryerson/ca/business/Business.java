/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.business;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.IOUtils;
import ryerson.ca.frontend.FrontEnd;
import ryerson.ca.helper.FoodItem;
import ryerson.ca.helper.FoodItemsXML;
import ryerson.ca.helper.RestaurantsXML;
import ryerson.ca.persistence.Restaurant_CRUD;

/**
 *
 * @author student
 */
public class Business {

    public static boolean isAuthenticated(String username, String passwrod) {
        if(Restaurant_CRUD.exists(username, passwrod)){
            return true;
        }
        return false;
    }

    public static FoodItemsXML getServicesFoodItem(String foodName, String token) throws IOException {

        Client viewmenuclient = ClientBuilder.newClient();
        String q = "http://localhost:8080/ViewFoodItem/webresources/ViewFoodItem";
        WebTarget viewmenuwebTarget
                = viewmenuclient.target(q);
        InputStream is
                = viewmenuwebTarget.path(foodName).request(MediaType.APPLICATION_XML).get(InputStream.class);
        String xml = IOUtils.toString(is, "utf-8");
        FoodItemsXML f = fooditemxmltoObjects(xml);

        return (f);
    }
    
    public static RestaurantsXML getServicesRestaurant(String username, String token) throws IOException {

        Client viewmenuclient = ClientBuilder.newClient();
        String q = "http://localhost:8080/ViewMenu/webresources/ViewMenu";
        WebTarget viewmenuwebTarget
                = viewmenuclient.target(q);
        InputStream is
                = viewmenuwebTarget.path(username).request(MediaType.APPLICATION_XML).get(InputStream.class);
        String xml = IOUtils.toString(is, "utf-8");
        RestaurantsXML f = restaurantxmltoObjects(xml);

        return (f);
    }

    private static FoodItemsXML fooditemxmltoObjects(String xml) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(FoodItemsXML.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            FoodItemsXML fooditems = (FoodItemsXML) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return fooditems;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static RestaurantsXML restaurantxmltoObjects(String xml) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(RestaurantsXML.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            RestaurantsXML restaurants = (RestaurantsXML) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return restaurants;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

}
