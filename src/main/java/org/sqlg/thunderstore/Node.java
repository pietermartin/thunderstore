package org.sqlg.thunderstore;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public sealed abstract class Node permits LeafNode, InternalNode {

    protected final static Logger LOGGER = Logger.getLogger(Node.class.getName());
    protected final BPlusTree bPlusTree;
    protected final int maximumKeysPerNode;
    protected final int minimumKeysPerNode;
    protected final int maximumChildrenPerNode;
    protected final int minimumChildrenPerNode;
    protected final List<Integer> keys;
    protected InternalNode parent = null;

    public Node(BPlusTree bPlusTree) {
        this.bPlusTree = bPlusTree;
        this.maximumKeysPerNode = bPlusTree.getMaximumKeysPerNode();
        this.minimumKeysPerNode = getMinimumKeysPerNode();
        this.maximumChildrenPerNode = bPlusTree.getMaximumChildrenPerNode();
        this.minimumChildrenPerNode = bPlusTree.getMinimumChildrenPerNode();
        this.keys = new ArrayList<>(bPlusTree.getMaximumKeysPerNode());
    }

    protected abstract int getMinimumKeysPerNode();

    public void insert(int key) {
        if (keys.size() < maximumKeysPerNode) {
            keys.add(key);
            keys.sort(Integer::compareTo);
        } else {
            //split the node taking from the middle key up
            split(key);
        }
        assert keys.size() <= maximumKeysPerNode;
        assert parent == null || keys.size() >= minimumKeysPerNode;
    }

    protected abstract void delete(int key);

    protected abstract void split(int key);

    protected void walkParentReplaceKeyToRemove(int keyToRemove, int keyToReplace) {
        if (parent != null) {
            int indexOf = parent.keys.indexOf(keyToRemove);
            if (indexOf != -1) {
                parent.keys.set(indexOf, keyToReplace);
            }
            parent.walkParentReplaceKeyToRemove(keyToRemove, keyToReplace);
        }
    }

    public abstract List<Node> getChildren();

    public abstract Node nextNode();

    public abstract Node previousNode();

    public abstract LeafNode searchLeafNodeToAddTo(int key);

    public abstract LeafNode searchForLeafNode(int key);

    public abstract InternalNode searchForInternalNode(int key);

    public int depth() {
        return internalDepth(1);
    }

    protected int internalDepth(int depth) {
        if (parent == null) {
            return depth;
        } else {
            return parent.internalDepth(depth + 1);
        }
    }

    abstract void print(int indent, StringBuilder sb);

    @Override
    public String toString() {
        return this.keys.stream().map(Object::toString).reduce((a, b) -> a + "," + b).orElse("");
    }

    protected boolean isRoot() {
        return parent == null;
    }

    public boolean isValid() {
        boolean valid = true;
        if (parent == null && depth() != 1) {
            LOGGER.log(Level.SEVERE, "{0} fails parent node must have depth of 1, got {2}", new Object[]{keys.toString(), depth()});
            valid = false;
        }
        if (parent != null && depth() < 2) {
            LOGGER.log(Level.SEVERE, "{0} fails non root node must have depth of > 1, got {2}", new Object[]{keys.toString(), depth()});
            valid = false;
        }
        if (keys.size() > maximumKeysPerNode) {
            LOGGER.log(Level.SEVERE, "{0} fails maximumKeysPerNode constraint, expected {1}, got {2}", new Object[]{keys.toString(), maximumKeysPerNode, keys.size()});
            valid = false;
        }
        if (parent != null && keys.size() < minimumKeysPerNode) {
            LOGGER.log(Level.SEVERE, "{0} fails minimumKeysPerNode constraint, expected {1}, got {2}", new Object[]{keys.toString(), minimumKeysPerNode, keys.size()});
            valid = false;
        }
//        LOGGER.log(Level.INFO, "valid: {0}", keys.toString());
        return valid;
    }
}
