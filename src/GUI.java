import org.w3c.dom.CDATASection;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GUI extends JFrame {


    public GUI(){
        setSize(700,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0,2));


        JPanel left = new JPanel();
        left.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        left.setLayout(new GridLayout(4,0));
        add(left);

        JLabel title = new JLabel("TITLE:");
        left.add(title);
        JLabel actors = new JLabel("ACTORS");
        left.add(actors);
        JLabel release = new JLabel("RELEASE");
        left.add(release);
        JLabel plot = new JLabel("plotplotplotplotplotplotplotplotplotplotplotplot");
        left.add(plot);


        JPanel right = new JPanel();
        right.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        right.setLayout(new GridLayout(2,0));
        add(right);

        JPanel topRight = new JPanel();
        right.add(topRight);

        JPanel lowRight = new JPanel();
        right.add(lowRight);

        JLabel pic = new JLabel("PICTURE");
        topRight.add(pic);
        JLabel rating = new JLabel("RATING");
        lowRight.add(rating);

        getRequests("star+wars");

        setVisible(true);

    }
    public static void getRequests(String movie) {

        try {

            // Create the URL for the HTTP GET request
            URL url = new URL("https://www.omdbapi.com/?apikey=eee5649f&t="+movie);


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

                // Handle the response data
                System.out.println("Response from Firebase Realtime Database:");
                System.out.println(response);
                TextArea ta = new TextArea(response.toString());
            } else { //404 403 402 etc error koder
                // Handle the error response
                System.out.println("Error response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
