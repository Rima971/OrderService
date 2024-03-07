package com.swiggy.orderManager.adapters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.function.Function;

public class CatalogueServiceAdapter {

//    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String RESTAURANTS_RESOURCE_URL = "https://localhost:8090/api/restaurants/";
    private static final Function<Integer, String> MENU_ITEMS_RESOURCE_URL = (Integer restaurantId) -> "https://localhost:8090/api/restaurants/"+restaurantId+"/menu-items/";

    private static void fetchRestaurant(int restaurantId) throws IOException {
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

    }
}
