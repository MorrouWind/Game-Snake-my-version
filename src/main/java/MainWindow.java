import javax.swing.*;

public class MainWindow extends JFrame {

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        mw.setTitle("Snake");
        mw.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mw.setSize(400, 415);
        mw.setLocation(400, 400);
        mw.add(new GameField());
        mw.setVisible(true);
    }

}
