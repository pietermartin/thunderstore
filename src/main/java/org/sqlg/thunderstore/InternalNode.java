package org.sqlg.thunderstore;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class InternalNode extends Node {

    final List<Node> children;

    public InternalNode(BPlusTree bPlusTree) {
        super(bPlusTree);
        this.children = new ArrayList<>(bPlusTree.getMinimumChildrenPerNode());
    }

    @Override
    protected int getMinimumKeysPerNode() {
        return bPlusTree.getMinimumKeysPerNode();
    }

    @Override
    public LeafNode searchLeafNodeToAddTo(int key) {
        for (int i = 0; i < keys.size(); i++) {
            if (key <= keys.get(i)) {
                return children.get(i).searchLeafNodeToAddTo(key);
            }
        }
        if (!children.isEmpty()) {
            Node last = children.getLast();
            if (last instanceof LeafNode leafNode) {
                return leafNode;
            } else {
                return last.searchLeafNodeToAddTo(key);
            }
        }
        return null;
    }

    @Override
    public LeafNode searchForLeafNode(int key) {
        for (int i = 0; i < keys.size(); i++) {
            if (key < keys.get(i)) {
                return children.get(i).searchForLeafNode(key);
            }
        }
        if (!children.isEmpty()) {
            return children.getLast().searchForLeafNode(key);
        }
        return null;
    }

    @Override
    public InternalNode searchForInternalNode(int key) {
        if (this.keys.contains(key)) {
            return this;
        } else {
            for (Node child : this.children) {
                InternalNode internalNode = child.searchForInternalNode(key);
                if (internalNode != null) {
                    return internalNode;
                }
            }
        }
        return null;
    }

    @Override
    public void split(int key) {
        //first add the key, then find the key to move
        keys.add(key);
        keys.sort(Integer::compareTo);
        int indexToMoveUp = Double.valueOf(Math.ceil((double) keys.size() / 2)).intValue();
        if ((keys.size() % 2) != 0) {
            indexToMoveUp = indexToMoveUp - 1;
        }
        Integer keyToMoveUp = keys.get(indexToMoveUp);
        InternalNode internalNode = new InternalNode(bPlusTree);
        for (int i = keys.size() - 1; i >= indexToMoveUp; i--) {
            int keyToRemove = keys.remove(i);
            //do not repeat keys on internal nodes
            if (keyToRemove != keyToMoveUp) {
                internalNode.keys.addFirst(keyToRemove);
            }
        }
        for (int i = children.size() - 1; i > indexToMoveUp; i--) {
            Node childToMove = children.remove(i);
            internalNode.children.addFirst(childToMove);
            childToMove.parent = internalNode;
        }

        if (parent == null) {

            //create a new parent internal node
            parent = new InternalNode(bPlusTree);
            bPlusTree.setRoot(parent);
            parent.getChildren().add(this);
            internalNode.parent = parent;

            parent.keys.add(keyToMoveUp);
            parent.getChildren().add(internalNode);

        } else {

            internalNode.parent = parent;
            int indexOf = parent.getChildren().indexOf(this);
            parent.getChildren().add(indexOf + 1, internalNode);
            parent.insert(keyToMoveUp);

        }
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    protected void delete(int key) {
        if (parent == null || keys.size() > minimumKeysPerNode) {

            int indexOfKeyToReplace = keys.indexOf(key);
            if (indexOfKeyToReplace != -1) {
                //replace the key being deleted with the smallest key in the child to the right
                int keyToSet = children.get(indexOfKeyToReplace + 1).keys.getFirst();
                int replaced = keys.set(indexOfKeyToReplace, keyToSet);

                assert key == replaced;
                assert parent == null || keys.size() >= minimumKeysPerNode;

                //check parent
                if (parent != null) {
                    parent.delete(key);
                }
            }

        } else {

            //merge
            int indexOfKeyToRemove = keys.indexOf(key);
            if (indexOfKeyToRemove != -1) {
                merge(key, parent.keys.get(indexOfKeyToRemove), indexOfKeyToRemove);
            }

        }
    }

    void merge(int keyToRemove, int keyToReplace, int indexOfKeyToRemove) {
        assert keys.size() == minimumKeysPerNode : "merge can only be called on a node with keys == minimumKeysPerNode";
        assert parent != null : "parent must be null for merge on an InternalNode";

        int indexOfChild = parent.children.indexOf(this);
        int indexOfParentKey = indexOfChild == 0 || indexOfChild == 1 ? 0 : indexOfChild - 1;

        //leftAllowed && rightAllowed indicate that there are siblings to the left or right that has the same parent.
        boolean leftAllowed = indexOfChild > 0;
        boolean rightAllowed = indexOfChild < parent.children.size() - 1;
        assert leftAllowed || rightAllowed : "there must always be a node to the left or right";

        boolean merged = false;
        if (leftAllowed) {
            InternalNode left = previousNode();
            assert left != null : "expected an InternalNode to the left to have been found";

            if (left.keys.size() == minimumKeysPerNode) {
                //all keys can be merged into the node to the left

                int removed = keys.remove(indexOfKeyToRemove);

                //move the remaining keys and children into left
                int keyBeforeMergeSize = left.keys.size();
                left.keys.addAll(keys);
                left.children.addAll(children);
                for (Node child : children) {
                    child.parent = left;
                }
                parent.children.remove(indexOfChild);
                keys.clear();
                children.clear();

                //bring the parent down a level
                int parentKey = parent.keys.get(indexOfParentKey);
                left.keys.add(keyBeforeMergeSize, parentKey);

                if (parent.isRoot() && parent.keys.size() == 1) {
                    bPlusTree.setRoot(left);
                    parent = null;
                }

                merged = true;
            }
            if (!merged && left.keys.size() > minimumKeysPerNode) {
                //perform a triangle movement
                //        a1
                //  b1,b2    b3,b4
                //
                //        b2
                //     b1    a1,b4
                //
                //move left biggest key up a level and the parent down a level to the right
                int removed = keys.remove(indexOfKeyToRemove);

                int lastKey = left.keys.removeLast();
                Node lastChild = left.children.removeLast();
                lastChild.parent = this;
                int parentKey = parent.keys.remove(indexOfParentKey);
                parent.keys.add(indexOfParentKey, lastKey);

                keys.addFirst(parentKey);
                children.addFirst(lastChild);

                merged = true;
            }
        }
        if (!merged && rightAllowed) {
            InternalNode right = nextNode();
            assert right != null : "expected an InternalNode to the right to have been found";
            if (right.keys.size() == minimumKeysPerNode) {
                //all keys can be merged into the node to the right

                int removed = keys.remove(indexOfKeyToRemove);

                //move the remaining keys and children into right
                int keyBeforeMergeSize = right.keys.size();
                right.keys.addAll(keys);
                right.keys.sort(Integer::compareTo);
                for (int i = children.size() - 1; i >= 0; i--) {
                    Node child = children.get(i);
                    right.children.addFirst(child);
                    child.parent = right;
                }
                parent.children.remove(indexOfChild);
                keys.clear();
                children.clear();

                //bring the parent down a level
                int parentKey = parent.keys.get(indexOfParentKey);
                right.keys.add(keyBeforeMergeSize - 1, parentKey);

                if (parent.isRoot() && parent.keys.size() == 1) {
                    bPlusTree.setRoot(right);
                    parent = null;
                }
                merged = true;
            }
            if (!merged && right.keys.size() > minimumKeysPerNode) {
                //perform a triangle movement
                //        a1
                //  b1,b2    b3,b4
                //
                //        b3
                //  b1,a1    b4
                //
                //move left biggest key up a level and the parent down a level to the right
                int removed = keys.remove(indexOfKeyToRemove);

                int firstKey = right.keys.removeFirst();
                Node firstChild = right.children.removeFirst();
                firstChild.parent = this;
                int parentKey = parent.keys.remove(indexOfParentKey);
                parent.keys.add(indexOfParentKey, firstKey);

                assert parentKey != keyToRemove : "key to remove should not exist in the tree";

                keys.addLast(parentKey);
                children.addLast(firstChild);

                merged = true;
            }
        }

        assert merged : "failed to merge internal node " + this;

        if (parent != null && keys.isEmpty()) {
            if (!parent.isRoot() && parent.keys.size() == parent.minimumKeysPerNode) {
                parent.merge(keyToRemove, keyToReplace, indexOfParentKey);
            } else {
                parent.keys.remove(indexOfParentKey);
            }
        }

    }

    @Override
    void print(int indent, StringBuilder sb) {
        for (int i = children.size() - 1; i >= 0; i--) {
            Node child = children.get(i);
            child.print(indent + 1, sb);

            if (i != 0) {
                Integer key = keys.get(i - 1);
                sb.append("    ".repeat(indent));
                sb.append("InternalNode\n");
                sb.append("    ".repeat(indent));
                sb.append(key);
                sb.append("\n");
            }
        }
    }

    @Override
    public String toString() {
        return "InternalNode{" + super.toString() + "}";
    }

    @Override
    public boolean isValid() {
        boolean valid = super.isValid();
        if (children.size() > maximumChildrenPerNode) {
            valid = false;
            LOGGER.log(Level.SEVERE, "{0} fails maximumChildrenPerNode constraint, expected {1}, got {2}", new Object[]{children.toString(), maximumChildrenPerNode, keys.size()});
        }
        if (parent == null && children.size() < 2) {
            valid = false;
            LOGGER.log(Level.SEVERE, "{0} fails root must have >= 2 child nodes constraint, expected {1}, got {2}", new Object[]{children.toString(), "> 2", children.size()});
        }
        if (parent != null && children.size() < minimumChildrenPerNode) {
            valid = false;
            LOGGER.log(Level.SEVERE, "{0} fails minimumChildrenPerNode constraint, expected {1}, got {2}", new Object[]{children.toString(), minimumChildrenPerNode, keys.size()});
        }
        if (valid) {
            int index = 0;
            for (Node child : children) {
                valid = child.isValid();
                if (!valid) {
                    break;
                }
                if (index < children.size() - 1) {
                    valid = child.keys.getLast() < this.children.get(index + 1).keys.getFirst();
                    if (!valid) {
                        LOGGER.log(Level.SEVERE, "{0} fails constraint, {1} is bigger than {2}", new Object[]{children.toString(), child.keys.getLast(), this.children.get(index + 1).keys.getFirst()});
                        break;
                    }
                }

                index++;
            }
        }
        return valid;
    }

    @Override
    public InternalNode nextNode() {
        return nextInternalNode();
    }

    @Override
    public InternalNode previousNode() {
        return previousInternalNode();
    }

    public InternalNode previousInternalNode() {
        if (this.parent == null) {
            //this is the root node so there is no next InternalNode
            return null;
        } else {
            int indexOf = this.parent.getChildren().indexOf(this);
            if (indexOf > 0) {
                return (InternalNode) this.parent.getChildren().get(indexOf - 1);
            } else {
                InternalNode internalNode = findFirstInternalNodeToTheLeft();
                if (internalNode == null) {
                    return null;
                } else {
                    return internalNode.findBiggestInternalNodeAtDepth(depth());
                }
            }
        }
    }

    public InternalNode nextInternalNode() {
        if (this.parent == null) {
            //this is the root node so there is no next InternalNode
            return null;
        } else {
            int indexOf = this.parent.getChildren().indexOf(this);
            if (indexOf + 1 < this.parent.getChildren().size()) {
                return (InternalNode) this.parent.getChildren().get(indexOf + 1);
            } else {
                InternalNode internalNode = findFirstInternalNodeToTheRight();
                if (internalNode == null) {
                    return null;
                } else {
                    //findSmallestInternalNodeAtDepth will always return an InternalNode at least one level higher (closer to root) than the current
                    assert internalNode.depth() < depth();
                    return (InternalNode) internalNode.findSmallestInternalNodeAtDepth(depth());
                }
            }
        }
    }

    private InternalNode findBiggestInternalNodeAtDepth(int depth) {
        Node child = getChildren().getLast();
        assert child instanceof InternalNode : "Always expect a InternalNode here. Method should return before LeafNodes are reached.";
        InternalNode internalNode = (InternalNode) child;
        if (internalNode.depth() == depth) {
            return internalNode;
        } else {
            return internalNode.findBiggestInternalNodeAtDepth(depth);
        }
    }

    private InternalNode findFirstInternalNodeToTheRight() {
        if (parent == null) {
            return null;
        } else {
            int indexOf = this.parent.getChildren().indexOf(this);
            if (indexOf + 1 < this.parent.getChildren().size()) {
                return (InternalNode) this.parent.getChildren().get(indexOf + 1);
            } else {
                return parent.findFirstInternalNodeToTheRight();
            }
        }
    }

    private InternalNode findFirstInternalNodeToTheLeft() {
        if (parent == null) {
            return null;
        } else {
            int indexOf = this.parent.getChildren().indexOf(this);
            if (indexOf > 0) {
                return (InternalNode) this.parent.getChildren().get(indexOf - 1);
            } else {
                return parent.findFirstInternalNodeToTheLeft();
            }
        }
    }

    public LeafNode findSmallestLeafNode() {
        Node node = this.children.getFirst();
        if (node instanceof LeafNode) {
            return (LeafNode) node;
        } else {
            InternalNode internalNode = (InternalNode) node;
            return internalNode.findSmallestLeafNode();
        }
    }

    public LeafNode findBiggestLeafNode() {
        Node node = this.children.getLast();
        if (node instanceof LeafNode) {
            return (LeafNode) node;
        } else {
            InternalNode internalNode = (InternalNode) node;
            return internalNode.findBiggestLeafNode();
        }
    }

}
