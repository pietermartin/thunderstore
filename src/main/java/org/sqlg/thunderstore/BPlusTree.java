package org.sqlg.thunderstore;

import org.apache.commons.collections4.IteratorUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BPlusTree {

    private final static Logger LOGGER = Logger.getLogger(BPlusTree.class.getName());
    private final int order;
    private final int maximumKeysPerNode; //order - 1
    private final int minimumKeysPerNode; //|order / 2| - 1
    private final int maximumChildrenPerNode; //order
    private final int minimumChildrenPerNode; //|order / 2|
    private Node root;

    public BPlusTree(int order) {
        if (order < 3) {
            throw new IllegalArgumentException("order must be at least 3");
        }
        this.order = order;
        this.maximumKeysPerNode = order - 1;
        this.minimumKeysPerNode = Double.valueOf(Math.ceil((double) order / 2) - 1).intValue();
        this.maximumChildrenPerNode = order;
        this.minimumChildrenPerNode = Double.valueOf(Math.ceil((double) order / 2)).intValue();
        this.root = new LeafNode(this);
    }

    public void setRoot(Node root) {
        this.root = root;
        this.root.parent = null;
    }

    public BPlusTree delete(int key) {
        Node node = searchForLeafNode(key);
        node.delete(key);
        return this;
    }

    public BPlusTree insert(int key) {
        Node node = searchLeafNodeToActOn(key);
        node.insert(key);
        return this;
    }

    public LeafNode searchLeafNodeToActOn(int key) {
        if (root == null) {
            return null;
        } else {
            return root.searchLeafNodeToAddTo(key);
        }
    }

    public LeafNode searchForLeafNode(int key) {
        if (root == null) {
            return null;
        } else {
            return root.searchForLeafNode(key);
        }
    }

    public InternalNode searchInternalNode(int key) {
        if (root == null) {
            return null;
        } else {
            return root.searchForInternalNode(key);
        }
    }

    public int getMaximumKeysPerNode() {
        return maximumKeysPerNode;
    }

    public int getMinimumKeysPerNode() {
        return minimumKeysPerNode;
    }

    public int getMaximumChildrenPerNode() {
        return maximumChildrenPerNode;
    }

    public int getMinimumChildrenPerNode() {
        return minimumChildrenPerNode;
    }

    public Node getRoot() {
        return root;
    }

    public StringBuilder print() {
        StringBuilder sb = new StringBuilder(this.toString());
        sb.append("\n");
        if (root != null) {
            print(root, 0, sb);
        }
        return sb;
    }

    private void print(Node node, int indent, StringBuilder sb) {
        if (node instanceof LeafNode leafNode) {
            leafNode.print(indent, sb);
        } else if (node instanceof InternalNode internalNode) {
            internalNode.print(indent, sb);
        }
    }

    @Override
    public String toString() {
        return "BPlusTree [order=" + order + ", maximumKeysPerNode=" + maximumKeysPerNode + ", minimumKeysPerNode=" + minimumKeysPerNode + ", maximumChildrenPerNode=" + maximumChildrenPerNode + ", minimumChildrenPerNode=" + minimumChildrenPerNode + "]";
    }

    public boolean isValid() {
        List<LeafNode> leafNodes = IteratorUtils.toList(leafNodeIterator());
        //assert all parents to the left are smaller or equal to leaf node
        boolean valid = true;
        if (!(leafNodes.size() == 1 && leafNodes.getFirst() == root)) {
            Node __previous;
            for (LeafNode leafNode : leafNodes) {
                __previous = leafNode;
                InternalNode parent = leafNode.parent;
                Integer first = leafNode.keys.getFirst();
                while (parent != null) {
                    int indexOfLeaf = parent.children.indexOf(__previous);
                    int _indexOfLeaf = indexOfLeaf == 0 || indexOfLeaf == 1 ? 0 : indexOfLeaf - 1;
                    int parentKey = parent.keys.get(_indexOfLeaf);
                    if (indexOfLeaf == 0) {
                        if (first >= parentKey) {
                            LOGGER.log(Level.SEVERE, "leafNode {0} must be < to parent keys, instead found {1}", new Object[]{first, parentKey});
                            valid = false;
                        }
                    } else {
                        if (first < parentKey) {
                            LOGGER.log(Level.SEVERE, "leafNode {0} must be >= to parent keys, instead found {1}", new Object[]{first, parentKey});
                            valid = false;
                        }
                    }
                    if (!valid) {
                        break;
                    }
                    __previous = parent;
                    parent = parent.parent;
                }
            }
        }

        //assert that all internal nodes are also leaf nodes
        Set<Integer> keys = new HashSet<>();
        for (LeafNode leafNode : leafNodes) {
            keys.addAll(leafNode.keys);
        }
        Set<Integer> _internalNodeKeys = new HashSet<>();
        List<InternalNode> internalNodes = IteratorUtils.toList(internalNodeIterator());
        for (InternalNode internalNode : internalNodes) {
            _internalNodeKeys.addAll(internalNode.keys);
        }
        if (!keys.containsAll(_internalNodeKeys)) {
            _internalNodeKeys.removeAll(keys);
            LOGGER.log(Level.SEVERE, "found internal nodes that are not leaf nodes, {0}", new Object[]{_internalNodeKeys.toString()});
            valid = false;
        }


        List<Node> nodes = IteratorUtils.toList(nodeIterator(getRoot()));
        for (Node node : nodes) {
            if (node.keys.size() > maximumKeysPerNode) {
                LOGGER.log(Level.SEVERE, "keys {0} exceed maximumKeysPerNode {1}", new Object[]{node.keys.toString(), maximumKeysPerNode});
                valid = false;
            }
            if (!node.isRoot() && node.keys.size() < minimumKeysPerNode) {
                LOGGER.log(Level.SEVERE, "keys {0} exceed minimumKeysPerNode {1}", new Object[]{node.keys.toString(), minimumKeysPerNode});
                valid = false;
            }
            if (node.getChildren().size() > maximumChildrenPerNode) {
                LOGGER.log(Level.SEVERE, "children {0} exceed maximumChildrenPerNode {1}", new Object[]{node.getChildren().toString(), maximumChildrenPerNode});
                valid = false;
            }
            if (!node.isRoot() && node instanceof InternalNode && node.getChildren().size() < minimumChildrenPerNode) {
                LOGGER.log(Level.SEVERE, "children {0} exceed minimumChildrenPerNode {1}", new Object[]{node.getChildren().toString(), minimumChildrenPerNode});
                valid = false;
            }

            List<Node> children = node.getChildren();
            if (!children.isEmpty()) {
                for (int i = 0; i < node.keys.size(); i++) {
                    Integer key = node.keys.get(i);
                    Node child = children.get(i);
                    if (i == 0 && key < child.keys.getLast()) {
                        LOGGER.log(Level.SEVERE, "found child key to the left {0} that is >= to the parent key {1}", new Object[]{child.keys.getLast(), key});
                        valid = false;
                    }
                    if (!valid) {
                        break;
                    }
                }
            }
            if (!valid) {
                break;
            }
        }

        valid = valid && this.root.isValid();
        return valid;
    }

    public BPlusIterator<InternalNode> internalNodeIterator(InternalNode start) {
        if (root == null || root instanceof LeafNode) {
            return BPlusIterator.emptyListIterator();
        } else {
            return new InternalNodeIterator(start);
        }
    }

    public BPlusIterator<LeafNode> leafNodeIterator() {
        return leafNodeIterator(getRoot());
    }

    public BPlusIterator<InternalNode> internalNodeIterator() {
        if (root == null) {
            return BPlusIterator.emptyListIterator();
        } else {
            if (root instanceof InternalNode internalNode) {
                return new AllInternalNodeIterator(internalNode);
            } else {
                return BPlusIterator.emptyListIterator();
            }
        }
    }

    public BPlusIterator<LeafNode> leafNodeIterator(Node start) {
        if (root == null) {
            return BPlusIterator.emptyListIterator();
        } else {
            return new LeafNodeIterator(start);
        }
    }

    public BPlusIterator<Node> nodeIterator(Node start) {
        if (root == null) {
            return BPlusIterator.emptyListIterator();
        } else {
            return new NodeIterator(start);
        }
    }

    public int depth() {
        if (root == null) {
            return 0;
        } else {
            if (root instanceof InternalNode internalNode) {
                LeafNode leafNode = internalNode.findSmallestLeafNode();
                return leafNode.depth();
            } else {
                return root.depth();
            }
        }
    }
}
