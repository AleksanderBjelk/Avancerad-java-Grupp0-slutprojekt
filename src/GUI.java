import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GUI extends JFrame {
    private static JTextField searchField;
    private JLabel titleLabel;
    private JLabel actorsLabel;
    private JLabel releaseLabel;
    private JLabel plotLabel;
    private JLabel posterLabel;
    private JLabel ratingLabel;
    private JLabel genreLabel;


    public GUI() {

        ImageIcon appIcon = new ImageIcon("src/Loggaimdb.png"); // Ladda din logga från en fil
        setIconImage(appIcon.getImage());
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));
        setTitle("EN BÄTTRE IMDB");

        Color bColor = new Color(0, 0, 0);
        Color tColor = new Color(248, 199, 3);

        JPanel left = new JPanel();
        left.setLayout(new BorderLayout());
        add(left);

        JPanel leftTopPanel = new JPanel();
        leftTopPanel.setBackground(bColor);
        leftTopPanel.setLayout(new GridLayout(0,3));
        left.add(leftTopPanel, BorderLayout.NORTH);


        JPanel info = new JPanel();
        info.setBackground(bColor);
        info.setLayout(new GridLayout(4,0));
        left.add(info);


        searchField = new JTextField();
        searchField.setBackground(tColor);
        searchField.addActionListener(e -> {
            String searchTerm = searchField.getText();
            getRequests(searchTerm, this);
        });
        leftTopPanel.add(searchField);

        JButton search = new JButton("Search");
        search.setBackground(tColor);
        search.setForeground(bColor);
        search.addActionListener(ActionListener -> {
            String searchTerm = searchField.getText();
            getRequests(searchTerm, this);
        });
        leftTopPanel.add(search);

        JButton randomButton = new JButton("Random movie");
        randomButton.setBackground(tColor);
        randomButton.setForeground(bColor);
        randomButton.addActionListener(e -> {
            //fetchRandomMovieFromURL("https://raw.githubusercontent.com/jberkel/imdb-movie-links/master/top250.txt");
        });
        leftTopPanel.add(randomButton);


        JLabel title = new JLabel("TITLE:");
        title.setForeground(tColor);
        info.add(title);
        title.setFont(new Font("Serif", Font.BOLD, 40));

        titleLabel = new JLabel();
        titleLabel.setForeground(tColor);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        info.add(titleLabel);


        JLabel actors = new JLabel("ACTORS:");
        actors.setForeground(tColor);
        info.add(actors);
        actors.setFont(new Font("Serif", Font.BOLD, 25));

        actorsLabel = new JLabel();
        actorsLabel.setForeground(tColor);
        actorsLabel.setFont(new Font("Serif", Font.BOLD, 20));
        info.add(actorsLabel);


        JLabel release = new JLabel("RELEASE DATE:");
        release.setForeground(tColor);
        info.add(release);
        release.setFont(new Font("Serif", Font.BOLD, 25));

        releaseLabel = new JLabel();
        releaseLabel.setForeground(tColor);
        releaseLabel.setFont(new Font("Serif", Font.BOLD, 20));
        info.add(releaseLabel);

        JLabel genre = new JLabel("GENRE: ");
        genre.setForeground(tColor);
        info.add(genre);
        genre.setFont(new Font("Serif", Font.BOLD, 25));

        genreLabel = new JLabel();
        genreLabel.setForeground(tColor);
        genreLabel.setFont(new Font("Serif", Font.BOLD, 20));
        info.add(genreLabel);


        JPanel right = new JPanel();
        right.setBackground(bColor);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        add(right);

        right.add(Box.createVerticalGlue());

        posterLabel = new JLabel();
        posterLabel.setForeground(tColor);
        right.add(posterLabel); //Centrerad posterLabel
        posterLabel.setAlignmentX(0.5F);

        ratingLabel = new JLabel();
        ratingLabel.setForeground(tColor);
        ratingLabel.setAlignmentX(0.5F);
        ratingLabel.setFont(new Font("Serif", Font.BOLD, 40));
        right.add(ratingLabel);

        right.add(Box.createVerticalGlue());

        plotLabel = new JLabel();
        plotLabel.setForeground(tColor);
        plotLabel.setAlignmentX(0.5F);
        plotLabel.setFont(new Font("Serif", Font.BOLD, 20));
        right.add(plotLabel);

        right.add(Box.createVerticalGlue());

        setVisible(true);
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

    public void updateFields(String title, String actors, String release, String genre, String plot, String posterURL, String rating) throws MalformedURLException {
        titleLabel.setText("<HTML>"+title+"</HTML>");
        actorsLabel.setText("<HTML>"+actors+"</HTML>");
        releaseLabel.setText("<HTML>"+release+"</HTML>");
        genreLabel.setText("<HTML>"+genre+"</HTML>");
        plotLabel.setText("<HTML>"+plot+"</HTML>");
        ratingLabel.setText("User ratings: " + rating);
            ImageIcon posterIcon = new ImageIcon(new URL(posterURL));
            posterLabel.setIcon(posterIcon);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);

    }
}
