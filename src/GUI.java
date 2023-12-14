import org.w3c.dom.CDATASection;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GUI extends JFrame {

    private static JTextField searchField;
    private JLabel titleField;
    private JLabel actorsField;
    private JLabel releaseField;
    private JLabel plotField;
    private JLabel posterLabel;

    public GUI() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));


        JPanel left = new JPanel();
        left.setLayout(new GridLayout(5, 0));
        left.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        add(left);

        JPanel leftTopPanel = new JPanel();
        leftTopPanel.setLayout(new BorderLayout());
        leftTopPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        left.add(leftTopPanel);

        searchField = new JTextField();
        leftTopPanel.add(searchField, BorderLayout.NORTH);

        JButton button = new JButton("Search");
        left.add(button);

        JLabel title = new JLabel("TITLE:");
        left.add(title);
        titleField = new JLabel();
        left.add(titleField);

        JLabel actors = new JLabel("ACTORS:");
        left.add(actors);
        actorsField = new JLabel();
        left.add(actorsField);


        JLabel release = new JLabel("RELEASE DATE:");
        left.add(release);
        releaseField = new JLabel();
        left.add(releaseField);


        JLabel plot = new JLabel("PLOT:");
        left.add(plot);
        plotField = new JLabel();
        left.add(plotField);


        JPanel right = new JPanel();
        right.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        right.setLayout(new GridLayout(2, 0));
        add(right);

        JPanel topRight = new JPanel();
        right.add(topRight);

        JPanel lowRight = new JPanel();
        right.add(lowRight);

        JLabel rating = new JLabel("RATING");
        lowRight.add(rating);

        posterLabel = new JLabel();
        topRight.add(posterLabel);

        setVisible(true);

        searchField.addActionListener(e -> {
            String searchTerm = searchField.getText();
            getRequests(searchTerm, this);
        });

    }

    public void updateFields(String title, String actors, String release, String plot, String posterURL) {
        titleField.setText(title);
        actorsField.setText(actors);
        releaseField.setText(release);
        plotField.setText(plot);
        try {
        ImageIcon posterIcon = new ImageIcon(new URL(posterURL));
        posterLabel.setIcon(posterIcon);
    } catch(
    IOException e)

    {
        e.printStackTrace();
    }

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

                // Handle the response data
                System.out.println("OMDb API");
                System.out.println(response);

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

                String title = jsonObject.get("Title").getAsString();
                String actors = jsonObject.get("Actors").getAsString();
                String release = jsonObject.get("Released").getAsString();
                String plot = jsonObject.get("Plot").getAsString();
                String posterURL = jsonObject.get("Poster").getAsString();

                SwingUtilities.invokeLater(() -> gui.updateFields(title, actors, release, plot, posterURL));
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
