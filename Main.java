import javax.swing.*;

public class Main {

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setTitle("Red Black Tree Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AppUI app = new AppUI();
        frame.add(app);
        frame.pack();
        frame.setVisible(true);
    }
}