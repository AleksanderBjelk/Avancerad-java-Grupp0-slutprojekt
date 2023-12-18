import javax.swing.*;
import java.awt.*;
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
        leftTopPanel.setLayout(new GridLayout(0, 3));
        left.add(leftTopPanel, BorderLayout.NORTH);


        JPanel info = new JPanel();
        info.setBackground(bColor);
        info.setLayout(new GridLayout(4, 0));
        left.add(info);


        searchField = new JTextField();
        searchField.setBackground(tColor);
        searchField.setFont(new Font("Serif", Font.BOLD, 18));
        searchField.addActionListener(e -> {
            String searchTerm = searchField.getText();
            DataManager.getRequests(searchTerm, this);
        });
        leftTopPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(tColor);
        searchButton.setForeground(bColor);
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(true);
        //searchButton.setBorder(BorderFactory.createRaisedBevelBorder());
        searchButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        searchButton.setFont(new Font("Serif", Font.BOLD, 18));
        searchButton.addActionListener(ActionListener -> {
            String searchTerm = searchField.getText();
            DataManager.getRequests(searchTerm, this);
        });
        leftTopPanel.add(searchButton);

        JButton randomButton = new JButton("Random movie");
        randomButton.setBackground(tColor);
        randomButton.setForeground(bColor);
        randomButton.setOpaque(true);
        randomButton.setBorderPainted(true);
        //randomButton.setBorder(BorderFactory.createRaisedBevelBorder());
        randomButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        randomButton.setFont(new Font("Serif", Font.BOLD, 18));
        randomButton.addActionListener(e -> {
            String str = DataManager.fetchRandomMovieFromURL("https://raw.githubusercontent.com/jberkel/imdb-movie-links/master/top250.txt");
            DataManager.getRequests(str, this);

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
        genre.setFont(new Font("Serif", Font.BOLD, 25));
        info.add(genre);

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

    public void updateFields(String title, String actors, String release, String genre, String plot, String posterURL, String rating) throws MalformedURLException {
        titleLabel.setText("<HTML>" + title + "</HTML>");
        actorsLabel.setText("<HTML>" + actors + "</HTML>");
        releaseLabel.setText("<HTML>" + release + "</HTML>");
        genreLabel.setText("<HTML>" + genre + "</HTML>");
        plotLabel.setText("<HTML>" + plot + "</HTML>");
        ratingLabel.setText("User ratings: " + rating);
        ImageIcon posterIcon = new ImageIcon(new URL(posterURL));
        posterLabel.setIcon(posterIcon);
    }
}
