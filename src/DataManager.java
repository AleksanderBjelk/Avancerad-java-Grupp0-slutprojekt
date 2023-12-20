import com.google.gson.Gson;
import com.google.gson.JsonObject;  //GSON biblotek, maven gson:gson:2.10.1

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


public class DataManager {
    public static String fetchRandomMovieFromURL(String topUrl) {

        // ArrayList där vi sparar Strings med filmnamnet från IMDBs topp 250 lista
        ArrayList<String> Top250 = new ArrayList<>();

        try {

            // Create the URL for the HTTP GET request
            URL url = new URL(topUrl);


            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET"); //POST , PATCH , DELETE

            // Get the response code t.ex 400, 404, 200 är ok
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // ok är bra
                // Read the response from the InputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;


                while ((line = reader.readLine()) != null) {
                    Top250.add(line.substring(32, line.length() - 7) + "\n");
                }
                reader.close();

                return Top250.get(new Random().nextInt(250));


            } else { //404 403 402 etc error koder
                // Handle the error response
                System.out.println("Error response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            System.out.println("Det gick inte" + e);

        }
        return "";
    }


    public static void getRequests(String movie, GUI gui) {

        try {

            // Create the URL for the HTTP GET request
            URL url = new URL("https://www.omdbapi.com/?apikey=eee5649f&t=" + movie);


            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET"); //POST , PATCH , DELETE

            // Get the response code t.ex 400, 404, 200 är ok
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // ok är bra
                // Read the response from the InputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

                String title = jsonObject.get("Title").getAsString();
                String actors = jsonObject.get("Actors").getAsString();
                String release = jsonObject.get("Released").getAsString();
                String genre = jsonObject.get("Genre").getAsString();
                String plot = jsonObject.get("Plot").getAsString();
                String posterURL = jsonObject.get("Poster").getAsString();
                String rating = jsonObject.get("imdbRating").getAsString();
                gui.updateFields(title, actors, release, genre, plot, posterURL, rating);


            } else { //404 403 402 etc error koder
                // Handle the error response
                System.out.println("Error response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
