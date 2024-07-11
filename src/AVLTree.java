/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {

    private IAVLNode root;
    private IAVLNode min;
    private IAVLNode max;

    // Constructor
    public AVLTree() {
        this.root = null;
        this.min = null;
        this.max = null;
    }
    public AVLTree(IAVLNode root) {
        if (root == null) {
            this.root = root;
            this.min = null;
            this.max = null;
        }
        else {
            this.root = root;
            this.min = root;
            this.max = root;
            if (this.root.getLeft() == null) {
                IAVLNode leftVirt = new AVLNode(-1, null, true);
                root.setLeft(leftVirt);
                leftVirt.setParent(root);
                leftVirt.setHeight(-1);
            }
            if (this.root.getRight() == null) {
                IAVLNode rightVirt = new AVLNode(-1, null, true);
                root.setRight(rightVirt);
                rightVirt.setParent(root);
                rightVirt.setHeight(-1);
            }
        }
        // Complexity = O(1)
    }

    /**
     * public boolean empty()
     *
     * Returns true if and only if the tree is empty.
     *
     */
    public boolean empty() {
        if (this.root == null) {
            return true;
        }
        return false;
        // Complexity = O(1)
    }

    /**
     * public String search(int k)
     *
     * Returns the info of an item with key k if it exists in the tree.
     * otherwise, returns null.
     */
    // Find the node in which the value is k
    public String search(int k){
        if (this.root == null) {
            return null;
        }
        IAVLNode node = this.root;
        // If it has 2 sons, see which way to go depending on the key
        while (node.getLeft().getValue() != null && node.getRight().getValue() != null) {
            if (k == node.getKey()) {
                return node.getValue();
            }
            else if (k < node.getKey()) {
                node = node.getLeft();
            }
            else {
                node = node.getRight();
            }
        }
        // If it has 1 son, go in it's direction until there are no more nodes
        // If there are no more nodes, return null
		/* Note: the following while loops will run only once since
		it's an AVL tree, a balanced tree, thus there can be only one
		intersection in which the node has 1 son only */
        while (node.getLeft().getValue() != null) {
            if (k == node.getKey()) {
                return node.getValue();
            }
            else {
                node = node.getLeft();
            }
        }
        while (node.getRight().getValue() != null) {
            if (k == node.getKey()) {
                return node.getValue();
            }
            else {
                node = node.getRight();
            }
        }
        if (node.getKey() == k) {
            return node.getValue();
        }
        return null;
        //Complexity = O(log(n))
    }

    public void rotate(IAVLNode cur, IAVLNode prev, String instruction){
        if (instruction.equals("right")) {
            IAVLNode z = prev;
            IAVLNode x = cur;
            IAVLNode b = x.getRight();
            // fix fathers and sons
            x.setRight(z);
            x.setParent(z.getParent());
            if(z.getParent() != null) {
                if (z.getParent().getLeft() == z) {
                    z.getParent().setLeft(x);
                }
                else {
                    z.getParent().setRight(x);
                }
            }
            z.setParent(x);
            b.setParent(z);
            z.setLeft(b);
            z.setHeight(z.getHeight() - 1);
            // fix sizes
            if(b.isRealNode()) {
                b.setSize(b.getLeft().getSize() + b.getRight().getSize() + 1);
            }
            z.setSize(z.getLeft().getSize() + z.getRight().getSize() + 1);
            x.setSize(x.getLeft().getSize() + x.getRight().getSize() + 1);
            if (x.getParent() == null) {
                this.root = x;
            }
        }
        else if (instruction.equals("left")) {
            IAVLNode z = prev;
            IAVLNode x = cur;
            IAVLNode a = x.getLeft();
            // fix fathers and sons
            x.setLeft(z);
            x.setParent(z.getParent());
            if(z.getParent() != null) {
                if (z.getParent().getLeft() == z) {
                    z.getParent().setLeft(x);
                }
                else {
                    z.getParent().setRight(x);
                }
            }
            z.setParent(x);
            a.setParent(z);
            z.setRight(a);
            z.setHeight(z.getHeight() - 1);
            // fix sizes
            if(a.isRealNode()) {
                a.setSize(a.getLeft().getSize() + a.getRight().getSize() + 1);
            }
            z.setSize(z.getLeft().getSize() + z.getRight().getSize() + 1);
            x.setSize(x.getLeft().getSize() + x.getRight().getSize() + 1);
            if (x.getParent() == null) {
                this.root = x;
            }
        }
        else if(instruction.equals("right left")) {
            IAVLNode z = prev;
            IAVLNode x = cur;
            IAVLNode b = x.getLeft();
            IAVLNode c = b.getRight();
            IAVLNode d = b.getLeft();
            // fix fathers and sons
            b.setParent(z.getParent());
            x.setParent(b);
            if(z.getParent() != null) {
                if (z.getParent().getLeft() == z) {
                    z.getParent().setLeft(b);
                }
                else {
                    z.getParent().setRight(b);
                }
            }
            z.setParent(b);
            c.setParent(x);
            d.setParent(z);
            b.setLeft(z);
            b.setRight(x);
            x.setLeft(c);
            z.setRight(d);
            // promote and demote
            x.setHeight(x.getHeight() - 1);
            z.setHeight(z.getHeight() - 1);
            b.setHeight(b.getHeight() + 1);
            // fix sizes
            b.setSize(b.getLeft().getSize() + b.getRight().getSize() + 1);
            z.setSize(z.getLeft().getSize() + z.getRight().getSize() + 1);
            x.setSize(x.getLeft().getSize() + x.getRight().getSize() + 1);
            if (c.isRealNode()){
                c.setSize(c.getLeft().getSize() + c.getRight().getSize() + 1);
            }
            else {
                c.setSize(0);
            }
            if (d.isRealNode()) {
                d.setSize(d.getLeft().getSize() + d.getRight().getSize() + 1);
            }
            else {
                d.setSize(0);
            }
            if (b.getParent() == null) {
                this.root = b;
            }
        }
        else if(instruction.equals("left right")) {
            IAVLNode z = prev;
            IAVLNode x = cur;
            IAVLNode b = x.getRight();
            IAVLNode c = b.getLeft();
            IAVLNode d = b.getRight();
            // fix fathers and sons
            b.setParent(z.getParent());
            x.setParent(b);
            if(z.getParent() != null) {
                if (z.getParent().getLeft() == z) {
                    z.getParent().setLeft(b);
                }
                else {
                    z.getParent().setRight(b);
                }
            }
            z.setParent(b);
            c.setParent(x);
            d.setParent(z);
            b.setLeft(x);
            b.setRight(z);
            x.setRight(c);
            z.setLeft(d);
            // promote and demote
            x.setHeight(x.getHeight() - 1);
            z.setHeight(z.getHeight() - 1);
            b.setHeight(b.getHeight() + 1);
            // fix sizes
            b.setSize(b.getLeft().getSize() + b.getRight().getSize() + 1);
            z.setSize(z.getLeft().getSize() + z.getRight().getSize() + 1);
            x.setSize(x.getLeft().getSize() + x.getRight().getSize() + 1);
            if (c.isRealNode()){
                c.setSize(c.getLeft().getSize() + c.getRight().getSize() + 1);
            }
            else {
                c.setSize(0);
            }
            if (d.isRealNode()) {
                d.setSize(d.getLeft().getSize() + d.getRight().getSize() + 1);
            }
            else {
                d.setSize(0);
            }
            if (b.getParent() == null) {
                this.root = b;
            }
        }
        //Complexity = O(1)
    }
    /**
     * public int insert(int k, String i)
     *
     * Inserts an item with key k and info i to the AVL tree.
     * The tree must remain valid, i.e. keep its invariants.
     * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
     * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
     * Returns -1 if an item with key k already exists in the tree.
     */
    public int insert(int k, String i) {
        int actionCount = 0;
        // If k is in the tree, return -1
        if (this.search(k) != null) {
            return -1;
        }
        // If the tree was empty
        if (this.root == null) {
            IAVLNode newRoot = new AVLNode(k,i,false);
            this.root = newRoot;
            newRoot.setSize(1);
            newRoot.setHeight(0);
            IAVLNode leftVirt = new AVLNode(-1, null, true);
            IAVLNode rightVirt = new AVLNode(-1, null, true);
            this.root.setLeft(leftVirt);
            leftVirt.setParent(this.root);
            leftVirt.setHeight(-1);
            this.root.setRight(rightVirt);
            rightVirt.setParent(this.root);
            rightVirt.setHeight(-1);
            this.min = this.root;
            this.max = this.root;
            return 0;
        }
        // add Virtual nodes
        IAVLNode leftVirt = new AVLNode(-1, null, true);
        IAVLNode rightVirt = new AVLNode(-1, null, true);
        boolean inserted = false;
        char caseType = 'a';
        // To begin with, we need to understand where to insert the node
        IAVLNode node = this.root;
        IAVLNode nodeInsert = new AVLNode(k, i, false);
        // We go down the tree while we have both right and left sub trees
        while (node.getLeft().isRealNode() && node.getRight().isRealNode()) {
            if (k < node.getKey()) {
                node = node.getLeft();
            }
            else if (k > node.getKey()){
                node = node.getRight();
            }
        }
        // If we don't have right sub tree, go down the left sub tree until k > node.getKey, and insert the node to the right
        while (node.getLeft().isRealNode()) {
            if (k > node.getKey()) {
                node.setRight(nodeInsert);
                nodeInsert.setParent(node);
                inserted = true;
                caseType = 'B';
                break;
            }
            else {
                node = node.getLeft();
            }
        }
        // If we don't have left sub tree, go down the right sub tree until k < node.getKey, and insert the node to the left
        while (!inserted && node.getRight().isRealNode()) {
            if (k < node.getKey()) {
                node.setLeft(nodeInsert);
                nodeInsert.setParent(node);
                inserted = true;
                caseType = 'B';
                break;
            }
            else {
                node = node.getRight();
            }
        }
        // If we've reached a leaf and haven't inserted the node yet, we check to which side we should insert it, and do it
        if (!inserted) {
            if (k > node.getKey()) {
                node.setRight(nodeInsert);
                nodeInsert.setParent(node);
            }
            else {
                node.setLeft(nodeInsert);
                nodeInsert.setParent(node);
            }
            nodeInsert.setParent(node);
            caseType = 'A';
        }
        // set Height, parent (done above), right virtual son, left virtual son, their heights and their parents
        nodeInsert.setHeight(0);
        nodeInsert.setLeft(leftVirt);
        nodeInsert.setRight(rightVirt);
        rightVirt.setHeight(-1);
        leftVirt.setHeight(-1);
        leftVirt.setParent(nodeInsert);
        rightVirt.setParent(nodeInsert);
        // update min and max
        if (k > this.max.getKey()) {
            this.max = nodeInsert;
        }
        if (k < this.min.getKey()) {
            this.min = nodeInsert;
        }
        // Now, we fix the tree to AVL, if it's Case A
        IAVLNode cur = nodeInsert.getParent();
        cur.setSize(cur.getLeft().getSize() + cur.getRight().getSize() + 1);
        if (caseType == 'A') {
            // In any case, we increase the height of the parent
            cur.setHeight(cur.getHeight() + 1);
            actionCount += 1;
            // update the size of the parent
            IAVLNode prev = cur.getParent();
            if (prev == null) {
                return actionCount;
            }
            // update the size of the grandparent
            prev.setSize(prev.getLeft().getSize() + prev.getRight().getSize() + 1);
            // Handle cases 1,2,3 until the tree is correct
            while (cur != this.root) {
                // if we have 1,1 or 1,2 or 2,1, break
                if ((prev.getHeight() - prev.getRight().getHeight() == 1 && prev.getHeight() - prev.getLeft().getHeight() == 1) ||
                        (prev.getHeight() - prev.getRight().getHeight() == 1 && prev.getHeight() - prev.getLeft().getHeight() == 2) ||
                        (prev.getHeight() - prev.getRight().getHeight() == 2 && prev.getHeight() - prev.getLeft().getHeight() == 1))  {
                    break;
                }
                // Case 1: if we have 0,1 or 1,0
                if ((prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getRight().getHeight() == 1) ||
                        (prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getLeft().getHeight() == 1)){
                    prev.setHeight(prev.getHeight() + 1);
                    cur.setSize(cur.getLeft().getSize() + cur.getRight().getSize() + 1);
                    actionCount += 1;
                }
                // Case 2 and 3: if we have 0,2 and then 1,2 or 2,1
                else if ((prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getRight().getHeight() == 2) ||
                        (prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getLeft().getHeight() == 2)) {
                    // Case 2: if we have 1,2
                    if (cur.getHeight() - cur.getLeft().getHeight() == 1 && cur.getHeight() - cur.getRight().getHeight() == 2) {
                        // we have 0,2
                        if (prev.getHeight() - prev.getLeft().getHeight() == 0) {
                            this.rotate(cur, prev, "right");
                            actionCount += (1 + 1);
                        }
                        // we have 2,0
                        else {
                            this.rotate(cur, prev, "right left");
                            actionCount += (3 + 2);
                        }
                    }
                    // Case 3: if we have 2,1
                    else if (cur.getHeight() - cur.getLeft().getHeight() == 2 && cur.getHeight() - cur.getRight().getHeight() == 1) {
                        // we have 0,2
                        if (prev.getHeight() - prev.getLeft().getHeight() == 0) {
                            this.rotate(cur, prev, "left right");
                            actionCount += (3 + 2);
                        }
                        // we have 2,0
                        else {
                            this.rotate(cur, prev, "left");
                            actionCount += (1 + 1);
                        }
                    }
                    break;
                }
                // Go up the tree
                cur.setSize(cur.getLeft().getSize() + cur.getRight().getSize() + 1);
                prev.setSize(prev.getLeft().getSize() + prev.getRight().getSize() + 1);
                cur = cur.getParent();
                prev = prev.getParent();
                if (cur == null || prev == null) {
                    break;
                }
                cur.setSize(cur.getLeft().getSize() + cur.getRight().getSize() + 1);
                prev.setSize(prev.getLeft().getSize() + prev.getRight().getSize() + 1);
            }
        }
        // in Case B, we don't need to fix the tree
        while(true) {
            cur.setSize(cur.getLeft().getSize() + cur.getRight().getSize() + 1);
            if (cur == this.root) {
                break;
            }
            cur = cur.getParent();
        }

        return actionCount;
        // Complexity = O(log(n))
    }


    /**
     * public int delete(int k)
     *
     * Deletes an item with key k from the binary tree, if it is there.
     * The tree must remain valid, i.e. keep its invariants.
     * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
     * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
     * Returns -1 if an item with key k was not found in the tree.
     */
    public int delete(int k){
        // If k is not in tree, return -1
        int actionCount = 0;
        if (search(k) == null) {
            return -1;
        }
        // Find the node
        IAVLNode node = this.root;
        while (node.getLeft().getValue() != null && node.getRight().getValue() != null) {
            if (k == node.getKey()) {
                break;
            }
            else if (k < node.getKey()) {
                node = node.getLeft();
            }
            else {
                node = node.getRight();
            }
        }
        while (node.getLeft().getValue() != null) {
            if (k == node.getKey()) {
                break;
            }
            else {
                node = node.getLeft();
            }
        }
        while (node.getRight().getValue() != null) {
            if (k == node.getKey()) {
                break;
            }
            else {
                node = node.getRight();
            }
        }
        // if the node is the only node in the tree
        if (!node.getLeft().isRealNode() && !node.getRight().isRealNode() && node.getParent() == null) {
            this.root = null;
            // update min max
            this.max = null;
            this.min = null;
            return 0;
        }
        // Find the successor
        IAVLNode successor = this.getSuccessor(node);
        // if it's the max value, get the predecessor
        if (successor == null) {
            if (node.getLeft().isRealNode()) {
                successor = node.getLeft();
                while (successor.getRight().isRealNode()) {
                    successor = successor.getRight();
                }
            }
            else {
                successor = node.getParent();
            }
        }
        IAVLNode parent = node.getParent();
        IAVLNode virtual = new AVLNode(-1, null, true);
        // Identify if successor is needed, if the node is not a leaf
        if (node.getLeft().getValue() != null && node.getRight().getValue() != null) {
            // Create a new node with the attributes of the successor, and put it instead of the node
            // get the pointers
            IAVLNode temp = new AVLNode(successor.getKey() ,successor.getValue() ,false);
            IAVLNode nodeParent = node.getParent();
            IAVLNode nodeLeft = node.getLeft();
            IAVLNode nodeRight = node.getRight();
            IAVLNode oldSuccessorParent = successor.getParent();
            String leftOrRight = null;
            // check if the successor was a right or left son
            if (oldSuccessorParent.getLeft() == successor) {
                leftOrRight = "left";
            }
            else if (oldSuccessorParent.getRight() == successor) {
                leftOrRight = "right";
            }
            IAVLNode oldSuccessorRight = successor.getRight();
            IAVLNode oldSuccessorLeft = successor.getLeft();
            // set the successor's new relations
            successor.setParent(nodeParent);
            if (successor.equals(nodeLeft)) {
                nodeLeft = temp;
            }
            successor.setLeft(nodeLeft);
            if (successor.equals(nodeRight)) {
                nodeRight = temp;
            }
            successor.setRight(nodeRight);
            if(nodeParent != null) {
                if (nodeParent.getLeft() == node) {
                    nodeParent.setLeft(successor);
                }
                else {
                    nodeParent.setRight(successor);
                }
            }
            nodeLeft.setParent(successor);
            nodeRight.setParent(successor);
            successor.setHeight(node.getHeight());
            successor.setSize(node.getSize());
            // set the temp node (to be deleted) new relations
            if (oldSuccessorParent.equals(node)) {
                oldSuccessorParent = successor;
            }
            temp.setParent(oldSuccessorParent);
            temp.setRight(oldSuccessorRight);
            temp.setLeft(oldSuccessorLeft);
            if (leftOrRight.equals("left")) {
                oldSuccessorParent.setLeft(temp);
            }
            else if (leftOrRight.equals("right")) {
                oldSuccessorParent.setRight(temp);
            }
            oldSuccessorRight.setParent(temp);
            oldSuccessorLeft.setParent(temp);
            if (!temp.getLeft().isRealNode() && !temp.getRight().isRealNode()) {
                temp.setHeight(0);
            }
            else {
                temp.setHeight(1);
            }
            temp.setSize(temp.getLeft().getSize() + temp.getRight().getSize() + 1);
            // now, temp is in the successor's place, and successor is in node's place
            node = temp;
            parent = node.getParent();
        }
        // if we try to delete a unary node which is the root
        if (this.root == node && (!node.getLeft().isRealNode() || !node.getRight().isRealNode())) {
            if (node.getLeft().isRealNode()) {
                this.root = node.getLeft();
                this.root.setParent(null);
            }
            else {
                this.root = node.getRight();
                this.root.setParent(null);
            }
            //update min max
            this.max = this.root;
            this.min = this.root;
            return 0;
        }
        // If we delete a leaf, we have 3 cases
        if (node.getLeft().getValue() == null && node.getRight().getValue() == null) {
            // 1, 1
            if (parent.getHeight() - parent.getLeft().getHeight() == 1 && parent.getHeight() - parent.getRight().getHeight() == 1) {
                if (parent.getLeft() == node) {
                    parent.setLeft(virtual);
                    virtual.setParent(parent);
                }
                else {
                    parent.setRight(virtual);
                    virtual.setParent(parent);
                }
            }
            // 1, 2 or 2, 1
            else {
                // Node is right son
                if (parent.getRight() == node) {
                    // 2, 1 and node is 1
                    if (parent.getHeight() - node.getHeight() == 1) {
                        parent.setRight(virtual);
                        virtual.setParent(parent);
                        parent.setHeight(parent.getHeight() - 1);
                        actionCount += 1;
                    }
                    // 1, 2 and node is 2
                    else if (parent.getHeight() - node.getHeight() == 2){
                        parent.setRight(virtual);
                        virtual.setParent(parent);
                    }
                }
                // Node is left son
                else {
                    // 1, 2 and node is 1
                    if (parent.getHeight() - node.getHeight() == 1) {
                        parent.setLeft(virtual);
                        virtual.setParent(parent);
                        parent.setHeight(parent.getHeight() - 1);
                        actionCount += 1;
                    }
                    // 2, 1 and node is 2
                    else if (parent.getHeight() - node.getHeight() == 2){
                        parent.setLeft(virtual);
                        virtual.setParent(parent);
                    }
                }
            }
        }
        // If we delete a unary node, we have 3 cases
        else if ((node.getRight().getValue() == null && node.getLeft().getValue() != null) ||
                (node.getRight().getValue() != null && node.getLeft().getValue() == null)) {
            // 1, 1
            if (parent.getHeight() - parent.getLeft().getHeight() == 1 && parent.getHeight() - parent.getRight().getHeight() == 1) {
                // node is left son
                if (parent.getLeft() == node) {
                    // node has a left son
                    if (node.getLeft().getValue() != null) {
                        parent.setLeft(node.getLeft());
                        node.getLeft().setParent(parent);
                    }
                    // node has a right son
                    else {
                        parent.setLeft(node.getRight());
                        node.getRight().setParent(parent);
                    }
                }
                // node is right son
                else {
                    // node has a left son
                    if (node.getLeft().getValue() != null) {
                        parent.setRight(node.getLeft());
                        node.getLeft().setParent(parent);
                    }
                    // node has a right son
                    else {
                        parent.setRight(node.getRight());
                        node.getRight().setParent(parent);
                    }
                }
            }
            // 1, 2 or 2, 1
            else {
                // node is right son
                if (parent.getRight() == node) {
                    // 2, 1 and son is 1
                    if (parent.getHeight() - node.getHeight() == 1) {
                        // find the real leaf
                        if (node.getHeight() - node.getLeft().getHeight() == 1) {
                            parent.setRight(node.getLeft());
                            node.getLeft().setParent(parent);
                        }
                        else {
                            parent.setRight(node.getRight());
                            node.getRight().setParent(parent);
                        }
                        // demote parent
                        parent.setHeight(parent.getHeight() - 1);
                        actionCount += 1;
                    }
                    // 1, 2 and son is 2
                    else {
                        // find the real leaf
                        if (node.getHeight() - node.getLeft().getHeight() == 1) {
                            parent.setRight(node.getLeft());
                            node.getLeft().setParent(parent);
                        }
                        else {
                            parent.setRight(node.getRight());
                            node.getRight().setParent(parent);
                        }
                    }
                }
                // node is left son
                else {
                    // 1, 2 and son is 1
                    if (parent.getHeight() - node.getHeight() == 1) {
                        // find the real leaf
                        if (node.getHeight() - node.getLeft().getHeight() == 1) {
                            parent.setLeft(node.getLeft());
                            node.getLeft().setParent(parent);
                        }
                        else {
                            parent.setLeft(node.getRight());
                            node.getRight().setParent(parent);
                        }
                        // demote parent
                        parent.setHeight(parent.getHeight() - 1);
                        actionCount += 1;
                    }
                    // 2, 1 and son is 2
                    else {
                        // find the real leaf
                        if (node.getHeight() - node.getLeft().getHeight() == 1) {
                            parent.setLeft(node.getLeft());
                            node.getLeft().setParent(parent);
                        }
                        else {
                            parent.setLeft(node.getRight());
                            node.getRight().setParent(parent);
                        }
                    }
                }
            }
        }
        // now, fix the size of the parent z
        parent.setSize(parent.getLeft().getSize() + parent.getRight().getSize() + 1);
        // Now, fix the tree, our only remaining pointer is "parent" (z in the slideshow)
        while (parent != null) {
            // case 1: 2,2
            if (parent.getHeight() - parent.getLeft().getHeight() == 2 &&
                    parent.getHeight() - parent.getRight().getHeight() == 2) {
                parent.setHeight(parent.getHeight() - 1);
                parent.setSize(parent.getLeft().getSize() + parent.getRight().getSize() + 1);
                actionCount += 1;
            }
            // case 2: 3,1
            else if (parent.getHeight() - parent.getLeft().getHeight() == 3 && parent.getHeight() - parent.getRight().getHeight() == 1) {
                // now we split into cases relating the son of the parent
                IAVLNode y = parent.getRight();
                IAVLNode b = y.getRight();
                IAVLNode a = y.getLeft();
                // now we look at the sons
                // 1, 1
                if (y.getHeight() - a.getHeight() == 1 && y.getHeight() - b.getHeight() == 1) {
                    // demote parent (in rotate func), promote y and rotate left
                    y.setHeight(y.getHeight() + 1);
                    this.rotate(y, parent, "left");
                    actionCount += (1 + 1 + 1);
                    //update sizes
                    parent.setSize(parent.getLeft().getSize() + parent.getRight().getSize() + 1);
                    y.setSize(y.getLeft().getSize() + y.getRight().getSize() + 1);
                    break;
                }
                // 2,1
                else if(y.getHeight() - a.getHeight() == 2 && y.getHeight() - b.getHeight() == 1) {
                    // demote parent twice (one time in rotate), rotate left
                    parent.setHeight(parent.getHeight() - 1);
                    this.rotate(y, parent, "left");
                    actionCount += (2 + 1);
                    // update sizes
                    parent.setSize(parent.getLeft().getSize() + parent.getRight().getSize() + 1);
                    y.setSize(y.getLeft().getSize() + y.getRight().getSize() + 1);
                    // set parent to be y, the current parent, and afterwards go up again
                    parent = y;
                }
                // 1, 2
                else if(y.getHeight() - a.getHeight() == 1 && y.getHeight() - b.getHeight() == 2) {
                    // demote parent twice, demote y, promote a, and rotate right left (the rotation already does the promote
                    // and demote, appart from one demote to parent
                    parent.setHeight(parent.getHeight() - 1);
                    this.rotate(y, parent, "right left");
                    actionCount += (4 + 2);
                    // update sizes
                    parent.setSize(parent.getLeft().getSize() + parent.getRight().getSize() + 1);
                    y.setSize(y.getLeft().getSize() + y.getRight().getSize() + 1);
                    a.setSize(a.getLeft().getSize() + a.getRight().getSize() + 1);
                    parent = a;
                }
            }
            // case 3: 1,3
            else if (parent.getHeight() - parent.getLeft().getHeight() == 1 && parent.getHeight() - parent.getRight().getHeight() == 3) {
                IAVLNode y = parent.getLeft();
                IAVLNode b = y.getLeft();
                IAVLNode a = y.getRight();
                // 1,1
                if (y.getHeight() - a.getHeight() == 1 && y.getHeight() - b.getHeight() == 1) {
                    // demote parent (in rotate func), promote y and rotate right
                    y.setHeight(y.getHeight() + 1);
                    this.rotate(y, parent, "right");
                    actionCount += (1 + 1 + 1);
                    //update sizes
                    parent.setSize(parent.getLeft().getSize() + parent.getRight().getSize() + 1);
                    y.setSize(y.getLeft().getSize() + y.getRight().getSize() + 1);
                    break;
                }
                // 1, 2
                else if(y.getHeight() - a.getHeight() == 2 && y.getHeight() - b.getHeight() == 1) {
                    // demote parent twice (one time in rotate), rotate right
                    parent.setHeight(parent.getHeight() - 1);
                    this.rotate(y, parent, "right");
                    actionCount += (2 + 1);
                    // update sizes
                    parent.setSize(parent.getLeft().getSize() + parent.getRight().getSize() + 1);
                    y.setSize(y.getLeft().getSize() + y.getRight().getSize() + 1);
                    // set parent to be y, the current parent, and afterwards go up again
                    parent = y;
                }
                // 2, 1
                else if(y.getHeight() - a.getHeight() == 1 && y.getHeight() - b.getHeight() == 2) {
                    // demote parent twice, demote y, promote a, and rotate right left (the rotation already does the promote
                    // and demote, appart from one demote to parent)
                    parent.setHeight(parent.getHeight() - 1);
                    this.rotate(y, parent, "left right");
                    actionCount += (4 + 2);
                    // update sizes
                    parent.setSize(parent.getLeft().getSize() + parent.getRight().getSize() + 1);
                    y.setSize(y.getLeft().getSize() + y.getRight().getSize() + 1);
                    a.setSize(a.getLeft().getSize() + a.getRight().getSize() + 1);
                    parent = a;
                }
            }
            // if we still need to go up, go up the tree
            if (parent.getParent() == null) {
                // if we reached the parent, update the pointer
                this.root = parent;
                this.root.setParent(null);
            }
            parent = parent.getParent();
            if(parent != null) {
                parent.setSize(parent.getLeft().getSize() + parent.getRight().getSize() + 1);
            }
            else if (parent == null) {
                break;
            }
        }
        while(parent != null) {
            parent.setSize(parent.getLeft().getSize() + parent.getRight().getSize() + 1);
            // update the root
            if (parent.getParent() == null) {
                this.root = parent;
                this.root.setParent(null);
            }
            parent = parent.getParent();
        }
        // update min and max if needed
        if (this.min.getKey() == k) {
            // While we can go left, we go left
            // When there are no more nodes in the left direction, return the value
            IAVLNode nodeMin = this.root;
            while (nodeMin.getLeft().getValue() != null) {
                nodeMin = nodeMin.getLeft();
            }
            this.min = nodeMin;
        }
        if (this.max.getKey() == k) {
            // While we can go right, we go right
            // When there are no more nodes in the right direction, return the value
            IAVLNode nodeMax = this.root;
            while (nodeMax.getRight().getValue() != null) {
                nodeMax = nodeMax.getRight();
            }
            this.max = nodeMax;
        }
        return actionCount;
        // Complexity = O(log(n))
    }

    /**
     * public String min()
     *
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty.
     */
    public String min(){
        if (this.min == null) {
            return null;
        }
        return this.min.getValue();
        // Complexity = O(1)
    }

    /**
     * public String max()
     *
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty.
     */
    public String max(){
        if (this.max == null) {
            return null;
        }
        return this.max.getValue();
        // Complexity = O(1)
    }

    /**
     * public int[] keysToArray()
     *
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     */
    public int[] keysToArray(){
        // Just as we did in HW2 question 3 b:
        // We go left while we can, and insert the nodes to an array
	  /* when we can't go left anymore, we insert the cur node to the res array
	  and go right*/
        // We repeat this process until the tree is done
        // We implement the logic of a stack, first in last out
        // if the tree is emtpy return an empty array
        if (this.empty()) {
            int[] res = new int[0];
            return res;
        }
        IAVLNode[] array = new IAVLNode[this.root.getSize()];
        int[] res = new int[this.root.getSize()];
        int idx = 0;
        int idxRes = 0;
        IAVLNode cur = this.root;
        while (cur.getValue() != null || res[idxRes - 1] != this.max.getKey()) {
            while (cur.getValue() != null) {
                array[idx] = cur;
                idx += 1;
                cur = cur.getLeft();
            }
            cur = array[idx - 1];
            array[idx - 1] = null;
            idx -= 1;
            res[idxRes] = cur.getKey();
            idxRes += 1;
            cur = cur.getRight();
        }
        return res;
        // Complexity = O(n)
    }

    /**
     * public String[] infoToArray()
     *
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     */
    public String[] infoToArray(){
//Just as we did in HW2 question 3 b:
        // We go left while we can, and insert the nodes to an array
	  /* when we can't go left anymore, we insert the cur node to the res array
	  and go right*/
        // We repeat this process until the tree is done
        // We implement the logic of a stack, first in last out
        // if the tree is empty
        if (this.empty()) {
            String[] res = new String[0];
            return res;
        }
        IAVLNode[] array = new IAVLNode[this.root.getSize()];
        String[] res = new String[this.root.getSize()];
        int idx = 0;
        int idxRes = 0;
        IAVLNode cur = this.root;
        while (cur.getValue() != null || res[this.root.getSize() - 1] == null) {
            while (cur.getValue() != null) {
                array[idx] = cur;
                idx += 1;
                cur = cur.getLeft();
            }
            cur = array[idx - 1];
            array[idx - 1] = null;
            idx -= 1;
            res[idxRes] = cur.getValue();
            idxRes += 1;
            cur = cur.getRight();
        }
        return res;
        // Complexity = O(n)
    }

    /**
     * public int size()
     *
     * Returns the number of nodes in the tree.
     */
    // We maintain a field called n which keeps count on how many nodes are in the tree
    public int size(){
        if (this.root == null) {
            return 0;
        }
        return this.root.getSize();
        // Complexity = O(1)
    }

    /**
     * public int getRoot()
     *
     * Returns the root AVL node, or null if the tree is empty
     */
    public IAVLNode getRoot(){
        return this.root;
        // Complexity = O(1)
    }

    /**
     * public AVLTree[] split(int x)
     *
     * splits the tree into 2 trees according to the key x.
     * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
     *
     * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
     * postcondition: none
     */
    public AVLTree[] split(int x)
    {
        // first of all, find x
        IAVLNode node = this.root;
        // If it has 2 sons, see which way to go depending on the key
        while (node.getLeft().getValue() != null && node.getRight().getValue() != null) {
            if (x == node.getKey()) {
                break;
            }
            else if (x < node.getKey()) {
                node = node.getLeft();
            }
            else {
                node = node.getRight();
            }
        }
        // If it has 1 son, go in it's direction until there are no more nodes
        // If there are no more nodes, return null
		/* Note: the following while loops will run only once since
		it's an AVL tree, a balanced tree, thus there can be only one
		intersection in which the node has 1 son only */
        while (node.getLeft().getValue() != null) {
            if (x == node.getKey()) {
                break;
            }
            else {
                node = node.getLeft();
            }
        }
        while (node.getRight().getValue() != null) {
            if (x == node.getKey()) {
                break;
            }
            else {
                node = node.getRight();
            }
        }
        // create two new trees
        AVLTree tGreen = new AVLTree(null);
        AVLTree tRed = new AVLTree(null);
        // split the trees
        // note: the min max will be updated via join
        IAVLNode oldNode = null;
        IAVLNode original = node;
        while(node != null) {
            IAVLNode parent = node.getParent();
            if (tGreen.root == null && tRed.root == null && !node.getLeft().isRealNode() && !node.getRight().isRealNode()) {
                tGreen.root = null;
                tRed.root = null;
            }
            else if (tGreen.root == null && tRed.root == null) {
                if (node.getLeft() == original) {
                    tRed.root = node;
                    IAVLNode tempVirt = new AVLNode(-1, null, true);
                    node.setLeft(tempVirt);
                    tempVirt.setParent(node);
                    tRed.max = tRed.root.getRight();
                    tRed.min = tRed.root;
                    tRed.root.setSize(2);
                }
                else if (node.getRight() == original) {
                    tGreen.root = node;
                    IAVLNode tempVirt = new AVLNode(-1, null, true);
                    node.setRight(tempVirt);
                    tempVirt.setParent(node);
                    tGreen.max = tGreen.root;
                    tGreen.min = tGreen.root.getLeft();
                    tGreen.root.setSize(2);
                }
                else {
                    tGreen.root = node.getLeft();
                    // find max
                    IAVLNode tempRoot = tGreen.getRoot();
                    if (tempRoot.getRight() != null) {
                        while (tempRoot.getRight().isRealNode()) {
                            tempRoot = tempRoot.getRight();
                        }
                    }
                    tGreen.max = tempRoot;
                    // find min
                    tempRoot = tGreen.root;
                    if (tempRoot.getLeft() != null) {
                        while (tempRoot.getLeft().isRealNode()) {
                            tempRoot = tempRoot.getLeft();
                        }
                    }
                    tGreen.min = tempRoot;
                    tRed.root = node.getRight();
                    // find max
                    tempRoot = tRed.getRoot();
                    if (tempRoot.getRight() != null) {
                        while (tempRoot.getRight().isRealNode()) {
                            tempRoot = tempRoot.getRight();
                        }
                    }
                    tRed.max = tempRoot;
                    // find min
                    tempRoot = tRed.root;
                    if (tempRoot.getLeft() != null) {
                        while (tempRoot.getLeft().isRealNode()) {
                            tempRoot = tempRoot.getLeft();
                        }
                    }
                    tRed.min = tempRoot;

                }
            }
            else if (node.getLeft() == oldNode) {
                AVLTree temp = new AVLTree(node.getRight());
                tRed.join(node, temp);
                tRed.getRoot().setSize(tRed.getRoot().getLeft().getSize() + tRed.getRoot().getRight().getSize() + 1);
                // find max
                IAVLNode tempRoot = tRed.getRoot();
                if (tempRoot.getRight() != null) {
                    while (tempRoot.getRight().isRealNode()) {
                        tempRoot = tempRoot.getRight();
                    }
                }
                tRed.max = tempRoot;
                // find min
                tempRoot = tRed.root;
                if (tempRoot.getLeft() != null) {
                    while (tempRoot.getLeft().isRealNode()) {
                        tempRoot = tempRoot.getLeft();
                    }
                }
                tRed.min = tempRoot;
            }
            else if(node.getRight() == oldNode) {
                AVLTree temp = new AVLTree(node.getLeft());
                tGreen.join(node, temp);
                tGreen.getRoot().setSize(tGreen.getRoot().getLeft().getSize() + tGreen.getRoot().getRight().getSize() + 1);
                // find max
                IAVLNode tempRoot = tGreen.getRoot();
                if (tempRoot.getRight() != null) {
                    while (tempRoot.getRight().isRealNode()) {
                        tempRoot = tempRoot.getRight();
                    }
                }
                tGreen.max = tempRoot;
                // find min
                tempRoot = tGreen.root;
                if (tempRoot.getLeft() != null) {
                    while (tempRoot.getLeft().isRealNode()) {
                        tempRoot = tempRoot.getLeft();
                    }
                }
                tGreen.min = tempRoot;
            }
            // get up the tree
            oldNode = node;
            node = parent;
        }
        // make sure the new roots don't point to other nodes
        if (tGreen.getRoot() != null) {
            tGreen.getRoot().setParent(null);
        }
        if (tRed.getRoot() != null) {
            tRed.getRoot().setParent(null);
        }
        // make sure the min and max are correct
        IAVLNode maxFinder = tGreen.getRoot();
        if (maxFinder != null && maxFinder.isRealNode()) {
            while(maxFinder.getRight().isRealNode()) {
                maxFinder = maxFinder.getRight();
            }
            tGreen.max = maxFinder;
        }
        else {
            tGreen.max = null;
        }
        maxFinder = tRed.getRoot();
        if (maxFinder != null && maxFinder.isRealNode()) {
            while(maxFinder.getRight().isRealNode()) {
                maxFinder = maxFinder.getRight();
            }
            tRed.max = maxFinder;
        }
        else {
            tRed.max = null;
        }
        IAVLNode minFinder = tGreen.getRoot();
        if (minFinder != null && minFinder.isRealNode()) {
            while(minFinder.getLeft().isRealNode()) {
                minFinder = minFinder.getLeft();
            }
            tGreen.min = minFinder;
        }
        else {
            tGreen.min = null;
        }
        minFinder = tRed.getRoot();
        if (minFinder != null && minFinder.isRealNode()) {
            while(minFinder.getLeft().isRealNode()) {
                minFinder = minFinder.getLeft();
            }
            tRed.min = minFinder;
        }
        else {
            tRed.min = null;
        }
        // create the array to be returned
        AVLTree[] AVLArray = new AVLTree[2];
        if (tGreen.getRoot() != null) {
            if (tGreen.getRoot().getKey() == -1) {
                tGreen = new AVLTree();
            }
            if (tGreen.getRoot() != null) {
                tGreen.getRoot().setSize(tGreen.getRoot().getLeft().getSize() + tGreen.getRoot().getRight().getSize() + 1);
            }
        }
        if (tRed.getRoot() != null) {
            if (tRed.getRoot().getKey() == -1) {
                tRed = new AVLTree();
            }
            if (tRed.getRoot() != null) {
                tRed.getRoot().setSize(tRed.getRoot().getLeft().getSize() + tRed.getRoot().getRight().getSize() + 1);
            }
        }
        AVLArray[0] = tGreen;
        AVLArray[1] = tRed;
        this.root = tGreen.getRoot();
        this.max = tGreen.max;
        this.min = tGreen.min;
        return AVLArray;
        // Complexity = O(log(n))
    }

    /**
     * public int join(IAVLNode x, AVLTree t)
     *
     * joins t and x with the tree.
     * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
     *
     * precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
     * postcondition: none
     */
    public int join(IAVLNode x, AVLTree t){
        // handle the cases in which one of the trees is empty, and update min max
        if (t.root == null && this.root == null) {
            this.root = x;
            t.root = x;
            IAVLNode leftVirt = new AVLNode(-1, null, true);
            IAVLNode rightVirt = new AVLNode(-1, null, true);
            x.setLeft(leftVirt);
            x.setRight(rightVirt);
            leftVirt.setParent(x);
            rightVirt.setParent(x);
            x.setParent(null);
            this.min = x;
            this.max = x;
            this.root.setSize(1);
            return 1;
        }
        // if one of the trees is empty, x is either bigger than all of it's keys or smallers
        else if (t.root == null) {
            int thisRank = this.root.getHeight();
            IAVLNode tempRoot = this.root;
            IAVLNode leftVirt = new AVLNode(-1, null, true);
            IAVLNode rightVirt = new AVLNode(-1, null, true);
            x.setLeft(leftVirt);
            leftVirt.setParent(x);
            x.setRight(rightVirt);
            rightVirt.setParent(x);
            // find max
            while (tempRoot.getRight().isRealNode()) {
                tempRoot = tempRoot.getRight();
            }
            this.max = tempRoot;
            // find min
            tempRoot = this.root;
            while (tempRoot.getLeft().isRealNode()) {
                tempRoot = tempRoot.getLeft();
            }
            this.min = tempRoot;
            if (x.getKey() > this.max.getKey()) {
                x.setSize(1);
                x.setHeight(0);
                this.max.setRight(x);
                x.setParent(this.max);
                this.max = x;

            }
            else {
                x.setSize(1);
                x.setHeight(0);
                this.min.setLeft(x);
                x.setParent(this.min);
                this.min = x;
            }
            t.root = this.root;
            // now fix the tree
            IAVLNode cur = x;
            IAVLNode prev = x.getParent();
            while (cur != this.root) {
                // if we have 1,1 or 1,2 or 2,1, break
                if ((prev.getHeight() - prev.getRight().getHeight() == 1 && prev.getHeight() - prev.getLeft().getHeight() == 1) ||
                        (prev.getHeight() - prev.getRight().getHeight() == 1 && prev.getHeight() - prev.getLeft().getHeight() == 2) ||
                        (prev.getHeight() - prev.getRight().getHeight() == 2 && prev.getHeight() - prev.getLeft().getHeight() == 1))  {
                    break;
                }
                // Case 1: if we have 0,1 or 1,0
                if ((prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getRight().getHeight() == 1) ||
                        (prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getLeft().getHeight() == 1)){
                    prev.setHeight(prev.getHeight() + 1);
                }
                // Case 2 and 3: if we have 0,2 and then 1,2 or 2,1
                else if ((prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getRight().getHeight() == 2) ||
                        (prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getLeft().getHeight() == 2)) {
                    // Case 2: if we have 1,2
                    if (cur.getHeight() - cur.getLeft().getHeight() == 1 && cur.getHeight() - cur.getRight().getHeight() == 2) {
                        // we have 0,2
                        if (prev.getHeight() - prev.getLeft().getHeight() == 0) {
                            this.rotate(cur, prev, "right");
                        }
                        // we have 2,0
                        else {
                            this.rotate(cur, prev, "left");
                        }
                    }
                    // Case 3: if we have 2,1
                    else if (cur.getHeight() - cur.getLeft().getHeight() == 2 && cur.getHeight() - cur.getRight().getHeight() == 1) {
                        // we have 0,2
                        if (prev.getHeight() - prev.getLeft().getHeight() == 0) {
                            this.rotate(cur, prev, "left right");
                        }
                        // we have 2,0
                        else {
                            this.rotate(cur, prev, "right left");
                        }
                    }
                    break;
                }
                // go up the tree
                cur = cur.getParent();
                prev = prev.getParent();
                if (cur == null || prev == null) {
                    break;
                }
                // update the sizes
                cur.setSize(cur.getLeft().getSize() + cur.getRight().getSize() + 1);
                if (cur != this.root) {
                    prev.setSize(prev.getLeft().getSize() + prev.getRight().getSize() + 1);
                }
            }
            while(true) {
                cur.setSize(cur.getLeft().getSize() + cur.getRight().getSize() + 1);
                if (cur == this.root) {
                    break;
                }
                cur = cur.getParent();
            }
            return (Math.abs(thisRank - (-1)) + 1);
        }
        else if (this.root == null) {
            int tRank = t.root.getHeight();
            IAVLNode tempRoot = t.root;
            IAVLNode leftVirt = new AVLNode(-1, null, true);
            IAVLNode rightVirt = new AVLNode(-1, null, true);
            x.setLeft(leftVirt);
            leftVirt.setParent(x);
            x.setRight(rightVirt);
            rightVirt.setParent(x);
            // find max
            while (tempRoot.getRight().isRealNode()) {
                tempRoot = tempRoot.getRight();
            }
            t.max = tempRoot;
            // find min
            tempRoot = t.root;
            while (tempRoot.getLeft().isRealNode()) {
                tempRoot = tempRoot.getLeft();
            }
            t.min = tempRoot;
            if (x.getKey() > t.max.getKey()) {
                x.setSize(1);
                x.setHeight(0);
                t.max.setRight(x);
                x.setParent(t.max);
                t.max = x;
            }
            else {
                x.setSize(1);
                x.setHeight(0);
                t.min.setLeft(x);
                x.setParent(t.min);
                t.min = x;
            }
            this.max = t.max;
            this.min = t.min;
            this.root = t.root;
            // now fix the tree
            IAVLNode cur = x;
            IAVLNode prev = x.getParent();
            while (cur != this.root) {
                // if we have 1,1 or 1,2 or 2,1, break
                if ((prev.getHeight() - prev.getRight().getHeight() == 1 && prev.getHeight() - prev.getLeft().getHeight() == 1) ||
                        (prev.getHeight() - prev.getRight().getHeight() == 1 && prev.getHeight() - prev.getLeft().getHeight() == 2) ||
                        (prev.getHeight() - prev.getRight().getHeight() == 2 && prev.getHeight() - prev.getLeft().getHeight() == 1))  {
                    break;
                }
                // Case 1: if we have 0,1 or 1,0
                if ((prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getRight().getHeight() == 1) ||
                        (prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getLeft().getHeight() == 1)){
                    prev.setHeight(prev.getHeight() + 1);
                }
                // Case 2 and 3: if we have 0,2 and then 1,2 or 2,1
                else if ((prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getRight().getHeight() == 2) ||
                        (prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getLeft().getHeight() == 2)) {
                    // Case 2: if we have 1,2
                    if (cur.getHeight() - cur.getLeft().getHeight() == 1 && cur.getHeight() - cur.getRight().getHeight() == 2) {
                        // we have 0,2
                        if (prev.getHeight() - prev.getLeft().getHeight() == 0) {
                            this.rotate(cur, prev, "right");
                        }
                        // we have 2,0
                        else {
                            this.rotate(cur, prev, "left");
                        }
                    }
                    // Case 3: if we have 2,1
                    else if (cur.getHeight() - cur.getLeft().getHeight() == 2 && cur.getHeight() - cur.getRight().getHeight() == 1) {
                        // we have 0,2
                        if (prev.getHeight() - prev.getLeft().getHeight() == 0) {
                            this.rotate(cur, prev, "left right");
                        }
                        // we have 2,0
                        else {
                            this.rotate(cur, prev, "right left");
                        }
                    }
                    break;
                }
                // go up the tree
                cur = cur.getParent();
                prev = prev.getParent();
                if (cur == null || prev == null) {
                    break;
                }
                // update the sizes
                cur.setSize(cur.getLeft().getSize() + cur.getRight().getSize() + 1);
                if (cur != this.root) {
                    prev.setSize(prev.getLeft().getSize() + prev.getRight().getSize() + 1);
                }
            }
            while(true) {
                cur.setSize(cur.getLeft().getSize() + cur.getRight().getSize() + 1);
                if (cur == this.root) {
                    break;
                }
                cur = cur.getParent();
            }
            return (Math.abs(tRank - (-1)) + 1);
        }
        // find out which tree is smaller in key
        IAVLNode rootBig;
        IAVLNode rootSmall;
        IAVLNode rootHigh;
        IAVLNode rootShort;
        String bigTree;
        String highTree;
        // check which tree has bigger keys
        if (this.root.getKey() > t.root.getKey()) {
            rootBig = this.root;
            rootSmall = t.root;
            bigTree = "this";
        }
        else {
            rootBig = t.root;
            rootSmall = this.root;
            bigTree = "t";
        }
        // check the heights of the trees
        if (this.root.getHeight() > t.root.getHeight()) {
            rootHigh = this.root;
            rootShort = t.root;
            highTree = "this";
        }
        else if (this.root.getHeight() < t.root.getHeight()) {
            rootHigh = t.root;
            rootShort = this.root;
            highTree = "t";
        }
        else {
            x.setRight(rootBig);
            x.setLeft(rootSmall);
            rootBig.setParent(x);
            rootSmall.setParent(x);
            this.root = x;
            t.root = x;
            int res = 1;
            return res;
        }
        // calculate the complexity - MAKE SURE THAT IT'S OK TO CALCULATE BEFORE THE JOIN ITSELF
        int thisRank = this.root.getHeight();
        int tRank = t.root.getHeight();
        int res = Math.abs(thisRank - tRank) + 1;
        // go left in the bigger tree until the heights difference between the nodes is either 1 or 2
        if (rootHigh.getKey() == rootBig.getKey()) {
            while (rootHigh.getHeight() - rootShort.getHeight() != 1 && rootHigh.getHeight() - rootShort.getHeight() != 2) {
                rootHigh = rootHigh.getLeft();
            }
            rootBig = rootHigh;
            // Set the kids of x: left son is the small root and right son is the left son of the node I've just reached
            // update the sizes and heights of x
            x.setLeft(rootSmall);
            x.setRight(rootBig.getLeft());
            rootSmall.setParent(x);
            rootBig.getLeft().setParent(x);
            rootBig.setLeft(x);
            x.setParent(rootBig);
            x.setHeight(rootSmall.getHeight() + 1);
            x.setSize(x.getLeft().getSize() + x.getRight().getSize() + 1);
            // connect the trees to "this", and update min max
            if (bigTree.equals("this")) {
                this.min = t.min;
            }
            else {
                this.max = t.max;
            }
            // update the roots
            if(highTree.equals("this")) {
                t.root = this.root;
            }
            else {
                this.root = t.root;
            }
            rootHigh = rootBig;
        }
        // go right in the smaller tree until the heights difference between the nodes is either 1 or 2
        else if (rootHigh.getKey() == rootSmall.getKey()) {
            while (rootHigh.getHeight() - rootShort.getHeight() != 1 && rootHigh.getHeight() - rootShort.getHeight() != 2) {
                rootHigh = rootHigh.getRight();
            }
            rootSmall = rootHigh;
            // Set the kids of x: left son is the small root and right son is the left son of the node I've just reached
            // update the sizes and heights of x
            x.setLeft(rootSmall.getRight());
            x.setRight(rootBig);
            rootSmall.getRight().setParent(x);
            rootBig.setParent(x);
            rootSmall.setRight(x);
            x.setParent(rootSmall);
            x.setHeight(rootBig.getHeight() + 1);
            x.setSize(x.getLeft().getSize() + x.getRight().getSize() + 1);
            // connect the trees to "this", and update min max
            if (bigTree.equals("this")) {
                this.min = t.min;
            }
            else {
                this.max = t.max;
            }
            // update the roots
            if(highTree.equals("this")) {
                t.root = this.root;
            }
            else {
                this.root = t.root;
            }
            rootHigh = rootSmall;
        }
        // now the trees are connected and we have one new tree ("this"), and we need to reballance the tree
        // if the height dif between rootHigh and x is 1, no need for reballance
        if (rootHigh.getHeight() - x.getHeight() == 0) {
            // 0,2 or 2,0
            if (rootHigh.getHeight() - rootHigh.getRight().getHeight() == 2 || rootHigh.getHeight() - rootHigh.getLeft().getHeight() == 2) {
                if (highTree.equals(bigTree)) {
                    this.rotate(x, rootHigh, "right");
                    rootHigh.setHeight(rootHigh.getHeight() + 1);
                    x.setHeight(x.getHeight() + 1);
                }
                else if (!highTree.equals(bigTree)) {
                    this.rotate(x, rootHigh, "left");
                    rootHigh.setHeight(rootHigh.getHeight() + 1);
                    x.setHeight(x.getHeight() + 1);
                }
            }
        }
        // 0,1 -> we need to do the enitre "insert" reballance
        IAVLNode cur = x;
        IAVLNode prev = rootHigh;
        while (cur != this.root) {
            // if we have 1,1 or 1,2 or 2,1, break
            if ((prev.getHeight() - prev.getRight().getHeight() == 1 && prev.getHeight() - prev.getLeft().getHeight() == 1) ||
                    (prev.getHeight() - prev.getRight().getHeight() == 1 && prev.getHeight() - prev.getLeft().getHeight() == 2) ||
                    (prev.getHeight() - prev.getRight().getHeight() == 2 && prev.getHeight() - prev.getLeft().getHeight() == 1))  {
                break;
            }
            // Case 1: if we have 0,1 or 1,0
            if ((prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getRight().getHeight() == 1) ||
                    (prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getLeft().getHeight() == 1)){
                prev.setHeight(prev.getHeight() + 1);
            }
            // Case 2 and 3: if we have 0,2 and then 1,2 or 2,1
            else if ((prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getRight().getHeight() == 2) ||
                    (prev.getHeight() - cur.getHeight() == 0 && prev.getHeight() - prev.getLeft().getHeight() == 2)) {
                // Case 2: if we have 1,2
                if (cur.getHeight() - cur.getLeft().getHeight() == 1 && cur.getHeight() - cur.getRight().getHeight() == 2) {
                    // we have 0,2
                    if (prev.getHeight() - prev.getLeft().getHeight() == 0) {
                        this.rotate(cur, prev, "right");
                    }
                    // we have 2,0
                    else {
                        this.rotate(cur, prev, "left");
                    }
                }
                // Case 3: if we have 2,1
                else if (cur.getHeight() - cur.getLeft().getHeight() == 2 && cur.getHeight() - cur.getRight().getHeight() == 1) {
                    // we have 0,2
                    if (prev.getHeight() - prev.getLeft().getHeight() == 0) {
                        this.rotate(cur, prev, "left right");
                    }
                    // we have 2,0
                    else {
                        this.rotate(cur, prev, "right left");
                    }
                }
                break;
            }
            // go up the tree
            cur = cur.getParent();
            prev = prev.getParent();
            if (cur == null || prev == null) {
                break;
            }
            // update the sizes
            cur.setSize(cur.getLeft().getSize() + cur.getRight().getSize() + 1);
            prev.setSize(prev.getLeft().getSize() + prev.getRight().getSize() + 1);
        }
        while(true) {
            cur.setSize(cur.getLeft().getSize() + cur.getRight().getSize() + 1);
            if (cur == this.root) {
                break;
            }
            cur = cur.getParent();
        }
        // After finishing the join itself, return the result calculated beforehand
        return res;
        // Complexity = O(log(n))
    }


    public IAVLNode getSuccessor(IAVLNode node) {
        // Since there is no successor to the max node, return null
        if(node == this.max) {
            return null;
        }
        // If there is a right son, go to it and then go left until there is no more left
        if (node.getRight().isRealNode()) {
            node = node.getRight();
            while (node.getLeft().isRealNode()) {
                node = node.getLeft();
            }
        }
        // If there is no right son, go up until node is the left son, and take the parent
        else {
            while (node.getParent().getLeft() != node) {
                node = node.getParent();
                if (node == this.root) {
                    break;
                }
            }
            if (node.getParent().isRealNode()) {
                node = node.getParent();
            }
        }
        return node;
        // O(log(n))
    }
    /**
     * public interface IAVLNode
     * ! Do not delete or modify this - otherwise all tests will fail !
     */
    public interface IAVLNode{
        public int getKey(); // Returns node's key (for virtual node return -1).
        public String getValue(); // Returns node's value [info], for virtual node returns null.
        public void setLeft(IAVLNode node); // Sets left child.
        public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
        public void setRight(IAVLNode node); // Sets right child.
        public IAVLNode getRight(); // Returns right child, if there is no right child return null.
        public void setParent(IAVLNode node); // Sets parent.
        public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
        public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
        public void setHeight(int height); // Sets the height of the node.
        public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
        public void setSize(int size); // sets the size of the node.
        public int getSize(); // Returns the size of the node (0 for virtual nodes).
    }

    /**
     * public class AVLNode
     *
     * If you wish to implement classes other than AVLTree
     * (for example AVLNode), do it in this file, not in another file.
     *
     * This class can and MUST be modified (It must implement IAVLNode).
     */
    public class AVLNode implements IAVLNode{

        private int key;
        private String info;
        private IAVLNode right;
        private IAVLNode left;
        private IAVLNode parent;
        private int height;
        private boolean virtual;
        private int size;

        // Create the constructor with 3 values:
        // key, info and virtual boolean
        public AVLNode (int key, String info, boolean virtual) {
            if (virtual == true) {
                this.key = -1;
                this.size = 0;
                this.height = -1;
                this.info = null;
            }
            else {
                this.key = key;
                this.size = 1;
                this.height = 0;
                this.info = info;
            }
            this.virtual = virtual;
            this.right = null;
            this.left = null;
            this.parent = null;
            // Complexity = O(1)
        }
        // Get the key of the node. If it's virtual, return -1
        public int getKey(){
            return this.key;
            // Complexity = O(1)
        }

        // Get the info field of the node (by default will be null)
        public String getValue(){
            return this.info;
            // Complexity = O(1)
        }

        public void setLeft(IAVLNode node){
            this.left = node;
            // Complexity = O(1)
        }

        public IAVLNode getLeft(){
            return this.left;
            // Complexity = O(1)
        }

        public void setRight(IAVLNode node){
            this.right = node;
            // Complexity = O(1)
        }

        public IAVLNode getRight(){
            return this.right;
            // Complexity = O(1)
        }

        public void setParent(IAVLNode node){
            this.parent = node;
            // Complexity = O(1)
        }

        public IAVLNode getParent(){
            return this.parent;
            // Complexity = O(1)
        }

        public boolean isRealNode(){
            return !(this.virtual);
            // Complexity = O(1)
        }

        public void setHeight(int height){
            this.height = height;
            // Complexity = O(1)
        }
        public int getHeight(){
            return this.height;
            // Complexity = O(1)
        }
        public void setSize(int size) {
            this.size = size;
            // Complexity = O(1)
        }
        public int getSize() {
            return this.size;
            // Complexity = O(1)
        }
    }
}

