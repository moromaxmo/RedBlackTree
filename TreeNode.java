
public class TreeNode<T extends Comparable<T>> {
    public static boolean red = false;
    public static boolean black = true;
    private boolean color = red;
    private TreeNode<T> left;
    private TreeNode<T> right;
    private TreeNode<T> parent;
    private T data;

    public TreeNode(T _date) {
        this.data = _date;
    }

    public void unLinkParent() {
        if (getParent() == null)
            return;
        if (parent.getLeft() == this)
            parent.setLeft(null);
        else if (parent.getRight() == this)
            parent.setRight(null);
        this.parent = null;
    }

    public boolean hasLeft() {
        return left != null;
    }
    public boolean hasRight() {
        return right != null;
    }

    public static boolean isRed(TreeNode<?> _node) {
        return getColor(_node) == red;
    }
    public static boolean isBlack(TreeNode<?> _node) {
        return !isRed(_node);
    }

    public void setData(T _date) {
        this.data = _date;
    }
    public T getData() {
        return data;
    }

    public void setLeft(TreeNode<T> _newLeft) {
        if (getLeft() != null)
            getLeft().setParent(null);
        if (_newLeft != null) {
            _newLeft.unLinkParent();
            _newLeft.setParent(this);
        }
        this.left = _newLeft;
    }
    public TreeNode<T> getLeft() {
        return left;
    }
    public static TreeNode<?> getLeft(TreeNode<?> _node) {
        return _node == null ? null : _node.getLeft();
    }

    public void setRight(TreeNode<T> _newRight) {
        if (getRight() != null) {
            getRight().setParent(null);
        }
        if (_newRight != null) {
            _newRight.unLinkParent();
            _newRight.setParent(this);
        }
        this.right = _newRight;
    }
    public TreeNode<T> getRight() {
        return right;
    }
    public static TreeNode<?> getRight(TreeNode<?> _node) {
        return _node == null ? null : _node.getRight();
    }

    public void setColor(boolean _color) {
        this.color = _color;
    }
    public static void setColor(TreeNode<?> _node, boolean color) {
        if (_node == null)
            return;
        _node.setColor(color);
    }

    public boolean getColor() {
        return color;
    }
    public static boolean getColor(TreeNode<?> _node) {
        return _node == null ? black : _node.getColor();
    }

    public void setParent(TreeNode<T> _parent) {
        this.parent = _parent;
    }
    public TreeNode<T> getParent() {
        return parent;
    }

    public TreeNode<T> getGrandparent() {
        TreeNode<T> _parent = getParent();
        return (_parent == null) ? null : _parent.getParent();
    }
    public TreeNode<T> getSibling() {
        TreeNode<T> _parent = getParent();
        if (_parent != null) { // No sibling of root _node
            if (this == _parent.getRight())
                return _parent.getLeft();
            else
                return _parent.getRight();
        }
        return null;
    }
    public TreeNode<T> getUncle() {
        TreeNode<T> _parent = getParent();
        if (_parent != null) { // No uncle of root
            return _parent.getSibling();
        }
        return null;
    }

}