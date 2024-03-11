package com.swiggy.orderManager.adapters;

import com.swiggy.orderManager.dtos.MenuItemDto;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.function.Function;
@Component
public class CatalogueServiceAdapter {

//    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String RESTAURANTS_RESOURCE_URL = "https://localhost:8090/api/restaurants/";
    private static final Function<Integer, String> MENU_ITEMS_RESOURCE_URL = (Integer restaurantId) -> "https://localhost:8090/api/restaurants/"+restaurantId+"/menu-items/";

    public Boolean restaurantExists(int restaurantId) throws IOException {
        URL obj = URI.create(RESTAURANTS_RESOURCE_URL+restaurantId).toURL();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
//        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request did not work.");
        }
        return true;
    }

    public MenuItemDto fetchMenuItem(int itemId, int restaurantId) throws IOException {
        URL obj = URI.create(MENU_ITEMS_RESOURCE_URL.apply(restaurantId)+itemId).toURL();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
//        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request did not work.");
        }
        return new MenuItemDto();
    }
}
