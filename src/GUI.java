import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    public GUI(){
        setSize(700,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0,2));


        JPanel left = new JPanel();
        left.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        left.setLayout(new GridLayout(4,0));
        add(left);

        JLabel title = new JLabel("TITLE");
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

        setVisible(true);

    }
}
