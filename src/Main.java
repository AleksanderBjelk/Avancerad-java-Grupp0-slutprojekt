import javax.swing.SwingUtilities;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { //används för att den ska köras på rätt kod och gör GUI men responsiv
            new GUI();
        });
    }
}