import javax.swing.*;
import java.awt.*;

public class AppUI <T extends Comparable<T>> extends JPanel {

    private RedBlackTree<T> RBTree = new RedBlackTree<>();
    private Drawer drawer = new Drawer(RBTree);

    public AppUI() {
        super.setLayout(new BorderLayout());
        drawer.setBackground(new Color(60, 120, 65));
        setScrollPane();
        setTopPanel();
    }

    private void setScrollPane() {
        drawer.setPreferredSize(new Dimension(4486, 2048));
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(drawer);
        scroll.setPreferredSize(new Dimension(750, 500));
        scroll.getViewport().setViewPosition(new Point(1715, 0));
        add(scroll, BorderLayout.CENTER);
    }

    private void setTopPanel() {
        int topBot = 10, leftRight = 15;
        final JTextField textBox = new JTextField(15);
        textBox.setBorder(BorderFactory.createEmptyBorder(topBot,leftRight,topBot,leftRight));
        final JButton b_insert = new JButton("INSERT");
        final JButton b_delete = new JButton("DELETE");
        final JButton b_clear = new JButton("CLEAR TREE");
        b_insert.setFocusPainted(false);
        b_insert.setBackground(new Color(125,215,125));
        b_insert.setBorder(BorderFactory.createEmptyBorder(topBot,leftRight,topBot,leftRight));
        b_delete.setFocusPainted(false);
        b_delete.setBackground(new Color(225,115,125));
        b_delete.setBorder(BorderFactory.createEmptyBorder(topBot,leftRight,topBot,leftRight));
        b_clear.setFocusPainted(false);
        b_clear.setBackground(new Color(235, 215, 30));
        b_clear.setBorder(BorderFactory.createEmptyBorder(topBot,leftRight,topBot,leftRight));
        JPanel panel = new JPanel();
        panel.add(b_insert);
        panel.add(textBox);
        panel.add(b_delete);
        panel.add(b_clear);
        panel.setBackground(new Color(55, 55, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(10,300,10,300));
        add(panel, BorderLayout.NORTH);
        b_insert.addActionListener(actionEvent -> {
            if (textBox.getText().equals(""))
                return;
            Integer toInsert;
            try {
                toInsert = Integer.parseInt(textBox.getText());
            }
            catch(Exception w)
            {
                JOptionPane.showMessageDialog(null, "Please enter a valid input");
                return;
            }
            if (RBTree.hasValue((T) toInsert)) {
                JOptionPane.showMessageDialog(null, "Element is already present in the tree");
            } else {
                RBTree.insert((T) toInsert);
                drawer.repaint();
                textBox.requestFocus();
            }
            textBox.setText("");
        });
        b_delete.addActionListener(actionEvent -> {
            if (textBox.getText().equals(""))
                return;
            Integer toDelete;
            try {
                toDelete = Integer.parseInt(textBox.getText());
            }
            catch(Exception w)
            {
                JOptionPane.showMessageDialog(null, "Please enter a valid input");
                return;
            }
            if (!RBTree.hasValue((T)toDelete)) {
                JOptionPane.showMessageDialog(null, "Element is not present in the tree");
            } else {
                RBTree._delete((T) toDelete);
                drawer.repaint();
                textBox.requestFocus();
            }
            textBox.setText("");
        });
        b_clear.addActionListener(actionEvent -> {
            if (RBTree.getRoot() == null)
                JOptionPane.showMessageDialog(null, "Tree is already empty");
            else {
                int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the tree?",
                        "WARNING", JOptionPane.YES_NO_OPTION);
                if(n<=0)RBTree.clear();
            }
            drawer.setSearch(null);
            drawer.repaint();
        });
    }
}
