
public class RedBlackTree<T extends Comparable<T>> {
    private TreeNode<T> root;

    /*...TREE USED FUNCTIONS... */
    public boolean isEmpty() {
        return this.root == null;
    }

    public boolean hasValue(T data) {
        return this.hasNode(this.root, data);
    }

    public void insert(T data) {
        if (this.isEmpty()) {
            this.root = new TreeNode<>(data);
        } else {
            this.bst_insert(this.root, data);
        }
        this.root.setColor(TreeNode.black);
    }

    public void _delete(T data) {
        if (!this.hasValue(data)) {
            return;
        }
        TreeNode<T> _node = this.Search(data);
        if (_node.getLeft() != null && _node.getRight() != null) {
            TreeNode<T> successor = this.getSuccessor(_node);
            _node.setData(successor.getData());
            _node = successor;
        }
        TreeNode<T> _newNode = _node.getLeft() == null ? _node.getRight() : _node.getLeft();
        if (_newNode != null) {
            if (_node == this.root) {
                _newNode.unLinkParent();
                this.root = _newNode;
            }
            else if (TreeNode.getLeft(_node.getParent()) == _node) {
                _node.getParent().setLeft(_newNode);
            }
            else {
                _node.getParent().setRight(_newNode);
            }
            if (TreeNode.isBlack(_node)) {
                this.RB_checkDelete(_newNode);
            }
        }
        else if (_node == this.root) {
            this.root = null;
        }
        else {
            if (TreeNode.isBlack(_node)) {
                this.RB_checkDelete(_node);
            }
            _node.unLinkParent();
        }
    }

    public void clear() {
        this.root = null;
    }

    public TreeNode<T> Search(T data) {
        return this.SearchNode(this.root, data);
    }

    public TreeNode<T> getRoot() {
        return this.root;
    }

    protected int getDepth(TreeNode<T> _node) {
        if (_node != null) {
            int right_depth;
            int left_depth = this.getDepth(_node.getLeft());
            return left_depth > (right_depth = this.getDepth(_node.getRight())) ? left_depth + 1 : right_depth + 1;
        }
        return 0;
    }

    /*...TREE HELP FUNCTIONS...*/
    private void bst_insert(TreeNode<T> _node, T data) {
        if (this.hasValue(data)) {
            return;
        }
        if (_node.getData().compareTo(data) > 0) {
            if (_node.hasLeft()) {
                this.bst_insert(_node.getLeft(), data);
            } else {
                TreeNode<T> _newNode = new TreeNode<>(data);
                _node.setLeft(_newNode);
                this.RB_check(_newNode);
            }
        } else if (_node.hasRight()) {
            this.bst_insert(_node.getRight(), data);
        } else {
            TreeNode<T> _newNode = new TreeNode<>(data);
            _node.setRight(_newNode);
            this.RB_check(_newNode);
        }
    }

    private TreeNode<T> SearchNode(TreeNode<T> root, T data) {
        if (root == null) {
            return null;
        }
        if (root.getData().compareTo(data) > 0) {
            return this.SearchNode(root.getLeft(), data);
        }
        if (root.getData().compareTo(data) < 0) {
            return this.SearchNode(root.getRight(), data);
        }
        return root;
    }

    private boolean hasNode(TreeNode<T> root, T data) {
        if (root == null) {
            return false;
        }
        if (root.getData().compareTo(data) > 0) {
            return this.hasNode(root.getLeft(), data);
        }
        if (root.getData().compareTo(data) < 0) {
            return this.hasNode(root.getRight(), data);
        }
        return true;
    }

    private TreeNode<T> getSuccessor(TreeNode<T> _root) {
        TreeNode<T> _node = _root.getLeft();
        if (_node != null) {
            while (_node.getRight() != null) {
                _node = _node.getRight();
            }
        }
        return _node;
    }

    private void RB_check(TreeNode<T> _node) {
        if (_node == null || _node == this.root || TreeNode.isBlack(_node.getParent())) {
            return; // root reached or parent is already black
        }
        if (TreeNode.isRed(_node.getUncle())) { // uncle is red -> recoloring case
            _node.getParent().setColor(!_node.getParent().getColor());
            _node.getUncle().setColor(!_node.getUncle().getColor());
            _node.getGrandparent().setColor(!_node.getGrandparent().getColor());
            this.RB_check(_node.getGrandparent());//check the rest of the tree (bottom-up)
        }
        /*uncle is black -> rotation cases*/
        else if (this.leftParent(_node)) { // parent is left child of grandparent
            if (this.isRight(_node)) { // node is right child of parent - > double rotation LR case
                _node = _node.getParent();
                this.leftRotate(_node);
            }
            //node here is left child of parent -> single right rotation
            TreeNode.setColor(_node.getParent(), TreeNode.black);
            TreeNode.setColor(_node.getGrandparent(), TreeNode.red);
            this.rightRotate(_node.getGrandparent());
        }
        else if (this.rightParent(_node)) { // parent is right child of grandparent
            if (this.isLeft(_node)) { // node is left child of parent ->  double rotation RL case
                _node = _node.getParent();
                this.rightRotate(_node);
            }
            //node here is right child of parent -> single left rotation
            TreeNode.setColor(_node.getParent(), TreeNode.black);
            TreeNode.setColor(_node.getGrandparent(), TreeNode.red);
            this.leftRotate(_node.getGrandparent());
        }
    }

    private void RB_checkDelete(TreeNode<T> _node) {
        while (_node != this.root && TreeNode.isBlack(_node)) { // will be replaced by black node
            TreeNode<T> _sibling = _node.getSibling();
            if (_node == TreeNode.getLeft(_node.getParent())) { // node is left child
                if (TreeNode.isRed(_sibling)) { // case sibling is red -> recolor and change sibling
                    TreeNode.setColor(_sibling, TreeNode.black);
                    TreeNode.setColor(_node.getParent(), TreeNode.red);
                    this.leftRotate(_node.getParent());
                    _sibling = (TreeNode<T>) TreeNode.getRight(_node.getParent());
                }
                if (TreeNode.isBlack(TreeNode.getLeft(_sibling)) && TreeNode.isBlack(TreeNode.getRight(_sibling))) {//case nephews are black
                    TreeNode.setColor(_sibling, TreeNode.red);                                                      // -> recolor
                    _node = _node.getParent();
                    continue;
                }
                if (TreeNode.isBlack(TreeNode.getRight(_sibling))) { // case far nephew (right) is black -> R rotation
                    TreeNode.setColor(TreeNode.getLeft(_sibling), TreeNode.black);
                    TreeNode.setColor(_sibling, TreeNode.red);
                    this.rightRotate(_sibling);
                    _sibling = (TreeNode<T>) TreeNode.getRight(_node.getParent());
                }
                // far nephew here is red  -> L rotation
                TreeNode.setColor(_sibling, TreeNode.getColor(_node.getParent()));
                TreeNode.setColor(_node.getParent(), TreeNode.black);
                TreeNode.setColor(TreeNode.getRight(_sibling), TreeNode.black);
                this.leftRotate(_node.getParent());
                _node = this.root;
                continue;
            }
            // node is right child
            if (TreeNode.isRed(_sibling)) { // case sibling is red -> recolor and change sibling
                TreeNode.setColor(_sibling, TreeNode.black);
                TreeNode.setColor(_node.getParent(), TreeNode.red);
                this.rightRotate(_node.getParent());
                _sibling = (TreeNode<T>) TreeNode.getLeft(_node.getParent());
            }
            if (TreeNode.isBlack(TreeNode.getLeft(_sibling)) && TreeNode.isBlack(TreeNode.getRight(_sibling))) {//case nephews are black
                TreeNode.setColor(_sibling, TreeNode.red);                                                      // -> recolor
                _node = _node.getParent();
                continue;
            }
            if (TreeNode.isBlack(TreeNode.getLeft(_sibling))) { // case far nephew (left) is black -> L rotation
                TreeNode.setColor(TreeNode.getRight(_sibling), TreeNode.black);
                TreeNode.setColor(_sibling, TreeNode.red);
                this.leftRotate(_sibling);
                _sibling = (TreeNode<T>) TreeNode.getLeft(_node.getParent());
            }
            //far nephew here is red -> R rotation
            TreeNode.setColor(_sibling, TreeNode.getColor(_node.getParent()));
            TreeNode.setColor(_node.getParent(), TreeNode.black);
            TreeNode.setColor(TreeNode.getLeft(_sibling), TreeNode.black);
            this.rightRotate(_node.getParent());
            _node = this.root;
        }
        TreeNode.setColor(_node, TreeNode.black);
    }

    private void rightRotate(TreeNode<T> _node) {
        if (_node.getLeft() == null) {
            return;
        }
        TreeNode<T> leftTree = _node.getLeft();
        _node.setLeft(leftTree.getRight());
        if (_node.getParent() == null) {
            this.root = leftTree;
        } else if (_node.getParent().getLeft() == _node) {
            _node.getParent().setLeft(leftTree);
        } else {
            _node.getParent().setRight(leftTree);
        }
        leftTree.setRight(_node);
    }

    private void leftRotate(TreeNode<T> _node) {
        if (_node.getRight() == null) {
            return;
        }
        TreeNode<T> rightTree = _node.getRight();
        _node.setRight(rightTree.getLeft());
        if (_node.getParent() == null) {
            this.root = rightTree;
        } else if (_node.getParent().getLeft() == _node) {
            _node.getParent().setLeft(rightTree);
        } else {
            _node.getParent().setRight(rightTree);
        }
        rightTree.setLeft(_node);
    }

    private boolean rightParent(TreeNode<T> _node) {
        return TreeNode.getRight(_node.getGrandparent()) == _node.getParent();
    }

    private boolean leftParent(TreeNode<T> _node) {
        return TreeNode.getLeft(_node.getGrandparent()) == _node.getParent();
    }

    private boolean isRight(TreeNode<T> _node) {
        return TreeNode.getRight(_node.getParent()) == _node;
    }

    private boolean isLeft(TreeNode<T> _node) {
        return TreeNode.getLeft(_node.getParent()) == _node;
    }
}