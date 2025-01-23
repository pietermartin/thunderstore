package org.sqlg.thunderstore;

import java.util.List;

public final class LeafNode extends Node {

    public LeafNode(BPlusTree bPlusTree) {
        super(bPlusTree);
    }

    @Override
    protected int getMinimumKeysPerNode() {
        return bPlusTree.getMinimumKeysPerNode();
    }

    @Override
    public List<Node> getChildren() {
        return List.of();
    }


    @Override
    public LeafNode searchLeafNodeToAddTo(int key) {
        if (keys.contains(key)) {
            throw new IllegalStateException("key " + key + " already exists");
        }
        return this;
    }

    @Override
    public LeafNode searchForLeafNode(int key) {
        if (this.keys.contains(key)) {
            return this;
        } else {
            return null;
        }
    }

    /**
     * This will always return null. If the search reaches a leaf node then an InternalNode with key does not exist.
     *
     * @param key The key to search for.
     * @return An InternalNode with `key` in keys
     */
    @Override
    public InternalNode searchForInternalNode(int key) {
        return null;
    }

    @Override
    protected void delete(int key) {
        //is the root node or has more than minimumKeysPerNode? i.e. it is the trivial operation to just remove the key.
        if (parent == null || keys.size() > minimumKeysPerNode) {


            int indexOfKeyToRemove = keys.indexOf(key);
            int keyToReplaceOnParents = nextKey(key);

            Integer removed = keys.remove(indexOfKeyToRemove);
            assert removed != null : "failed to remove key " + indexOfKeyToRemove + ", not found";
            assert parent == null || keys.size() >= minimumKeysPerNode;

            if (parent != null) {
                if (keyToReplaceOnParents != -1) {
                    bPlusTree.getRoot().walkReplaceKeyToRemove(key, keyToReplaceOnParents);
                }
            }

        } else {
            //merge the node to the left or right
            int indexOfKeyToRemove = keys.indexOf(key);
            int keyToReplaceOnParents = nextKey(key);
            merge(key);
            bPlusTree.getRoot().walkReplaceKeyToRemove(key, keyToReplaceOnParents);
        }
    }

    private void merge(int keyToRemove) {
        assert keys.size() == minimumKeysPerNode : "merge can only be called on a node with keys = minimumKeysPerNode";

        int indexOfChild = parent.children.indexOf(this);
        int indexOfParentKey = indexOfChild == 0 || indexOfChild == 1 ? 0 : indexOfChild - 1;

        //leftAllowed && rightAllowed indicate that there are siblings to the left or right that has the same parent.
        boolean leftAllowed = indexOfChild > 0;
        boolean rightAllowed = indexOfChild < parent.children.size() - 1;
        assert leftAllowed || rightAllowed : "there must always be a node to the left or right";

        int indexOfKeyToRemove = keys.indexOf(keyToRemove);
        int removed = keys.remove(indexOfKeyToRemove);

        boolean merged = false;
        if (leftAllowed) {
            LeafNode left = previousNode();
            if (left != null && left.keys.size() == minimumKeysPerNode) {
                //move the remaining keys into previous
                left.keys.addAll(keys);
                keys.clear();
                //shift over the parent node by one, i.e. delete parent key and the corresponding child
                parent.children.remove(indexOfChild);
                merged = true;
                
                if (parent.isRoot() && parent.keys.size() == 1) {
                    bPlusTree.setRoot(left);
                    parent = null;
                }
            }
            if (!merged && left != null && left.keys.size() > minimumKeysPerNode) {
                //from left, remove the biggest key, make it the parent and add to keys
                int last = left.keys.removeLast();
                parent.keys.set(indexOfParentKey, last);
                keys.addFirst(last);
                merged = true;
            }
        }

        if (!merged && rightAllowed) {
            LeafNode right = nextNode();
            if (right != null && right.keys.size() == minimumKeysPerNode) {
                //move the remaining keys into next
                right.keys.addAll(keys);
                right.keys.sort(Integer::compareTo);
                keys.clear();
                //shift over the parent node by one, i.e. delete parent key and the corresponding child
                parent.children.remove(indexOfChild);
                merged = true;

                if (parent.isRoot() && parent.keys.size() == 1) {
                    bPlusTree.setRoot(right);
                    parent = null;
                }
            }
            if (!merged && right != null && right.keys.size() > minimumKeysPerNode) {
                //triangle logic
                //         9
                //    x,7    9,10
                //
                //         10
                //    x,9     10
                //from right, remove the smallest key, make it the parent and add to keys
                int first = right.keys.removeFirst();
                int nextToMakeParent = right.keys.getFirst();
                parent.keys.set(indexOfChild, nextToMakeParent);
                keys.addLast(first);
                merged = true;
            }
        }

        assert merged : "failed to merge leaf node " + this;

        if (parent != null && keys.isEmpty()) {
            if (!parent.isRoot() && parent.keys.size() == parent.minimumKeysPerNode) {
                parent.merge(keyToRemove, parent.keys.get(indexOfParentKey), indexOfParentKey);
            } else {
                parent.keys.remove(indexOfParentKey);
            }
        }


    }

    private int nextKey(int currentKey) {
        int indexOfCurrent = keys.indexOf(currentKey);
        assert indexOfCurrent != -1 : "currentKey must exist";
        if (indexOfCurrent != keys.size() - 1) {
            return keys.get(indexOfCurrent + 1);
        } else {
            LeafNode next = nextLeafNode();
            if (next != null) {
                return next.keys.getFirst();
            } else {
                return -1;
            }
        }
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
        LeafNode leafNode = new LeafNode(bPlusTree);
        for (int i = keys.size() - 1; i >= indexToMoveUp; i--) {
            leafNode.keys.addFirst(keys.remove(i));
        }

        if (parent == null) {

            //create a new parent internal node
            parent = new InternalNode(bPlusTree);
            bPlusTree.setRoot(parent);
            parent.getChildren().add(this);
            leafNode.parent = parent;

            parent.keys.add(keyToMoveUp);
            parent.getChildren().add(leafNode);

        } else {

            leafNode.parent = parent;
            int indexOfCurrent = parent.getChildren().indexOf(this);
            parent.getChildren().add(indexOfCurrent + 1, leafNode);
            parent.insert(keyToMoveUp);

        }
    }

    @Override
    void print(int indent, StringBuilder sb) {
        sb.append("    ".repeat(indent));
        sb.append("LeafNode\n");
        for (Integer key : keys.reversed()) {
            sb.append("    ".repeat(indent));
            sb.append(key);
            sb.append("\n");
        }
    }

    @Override
    public LeafNode nextNode() {
        return nextLeafNode();
    }

    @Override
    public LeafNode previousNode() {
        return previousLeafNode();
    }

    public LeafNode nextLeafNode() {
        if (parent == null) {
            return null;
        } else {
            int indexOf = this.parent.getChildren().indexOf(this);
            if (indexOf + 1 < this.parent.getChildren().size()) {
                return (LeafNode) this.parent.getChildren().get(indexOf + 1);
            } else {
                InternalNode next = this.parent.nextInternalNode();
                if (next != null) {
                    assert next.depth() == bPlusTree.depth() - 1;
                    return (LeafNode) next.getChildren().getFirst();
                } else {
                    return null;
                }
            }
        }
    }

    public LeafNode previousLeafNode() {
        if (this.parent == null) {
            return null;
        } else {
            int indexOf = this.parent.getChildren().indexOf(this);
            if (indexOf > 0) {
                return (LeafNode) this.parent.getChildren().get(indexOf - 1);
            } else {
                InternalNode previous = this.parent.previousInternalNode();
                if (previous != null) {
                    assert previous.depth() == bPlusTree.depth() - 1;
                    return (LeafNode) previous.getChildren().getLast();
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "LeafNode{" + super.toString() + "}";
    }

}
