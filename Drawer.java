import javax.swing.*;
import java.awt.*;

public class Drawer <T extends Comparable<T>> extends JPanel {
    private RedBlackTree<T> tree;
    private int radius = 23;
    private int yOffset = 40;
    private Color textColor = new Color(230, 230, 230);

    private TreeNode<T> toSearch;

    public Drawer(RedBlackTree<T> tree) {
        this.tree = tree;
    }

    public void setSearch(TreeNode<T> c) {
        toSearch = c;
    }
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (tree.isEmpty())
            return;
        Graphics2D graphics2d = (Graphics2D) graphics;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawTree(graphics2d, tree.getRoot(), getWidth() / 2, 30, getGap());
    }
    private void drawTree(Graphics2D g, TreeNode<T> _root, int x, int y, int xOffset) {
        if (x < 0)
            setPreferredSize(new Dimension(2 * getWidth(), getHeight()));
        drawNode(g, _root, x, y);
        if (_root.getRight() != null)
        {
            join(g, x + xOffset, y + yOffset, x, y);
            drawTree(g, _root.getRight(), x + xOffset, y + yOffset, xOffset / 2);
        }
        if (_root.getLeft() != null) {
            join(g, x - xOffset, y + yOffset, x, y);
            drawTree(g, _root.getLeft(), x - xOffset, y + yOffset, xOffset / 2);
        }
    }

    private void drawNode(Graphics2D g, TreeNode<T> _node, int x, int y) {
        if(_node.getColor() == TreeNode.red )
            g.setColor(new Color(200,50,50));
        else
            g.setColor(new Color(40,40,40));
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        g.setColor(textColor);
        String text = _node.getData().toString();
        drawText(g, text, x, y);
        g.setColor(Color.DARK_GRAY);
    }

    private void drawText(Graphics2D g, String text, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        double t_width = fm.getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (x - t_width / 2), (y + fm.getMaxAscent() / 2));
    }

    private void join(Graphics2D g, int x1, int y1, int x2, int y2) {
        double hypot = Math.hypot(yOffset, x2 - x1);
        int xx = (int) (x1 + radius * (x2 - x1) / hypot);
        int yy = (int) (y1 - radius * yOffset / hypot);
        int xx2 = (int) (x2 - radius * (x2 - x1) / hypot);
        int yy2 = (int) (y2 + radius * yOffset / hypot);
        g.drawLine(xx, yy, xx2, yy2);
    }

    private int getGap() {
        int depth = tree.getDepth(tree.getRoot());
        int multiplier = 30;
        float exponent = (float) 1.4;
        if (depth > 6) {
            multiplier += depth * 3;
            exponent += .1;
        }
        return (int) Math.pow(depth, exponent) * multiplier;
    }

}
