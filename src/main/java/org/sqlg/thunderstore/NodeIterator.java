package org.sqlg.thunderstore;

public class NodeIterator implements BPlusIterator<Node> {

    private final Node start;
    private Node current = null;

    public NodeIterator(Node start) {
        assert start != null : "start must not be null";
        this.start = start;
    }

    @Override
    public boolean hasNext() {
        if (current == null) {
            return true;
        } else {
            switch (current) {
                case LeafNode leafNode -> {
                    return leafNode.nextLeafNode() != null;
                }
                case InternalNode internalNode -> {
                    InternalNode nextInternalNode = internalNode.nextInternalNode();
                    if (nextInternalNode != null) {
                        return true;
                    } else {
                        Node node = current.bPlusTree.getRoot().findSmallestInternalNodeAtDepth(current.depth() + 1);
                        return node != null;
                    }
                }
            }
        }
    }

    @Override
    public boolean hasPrevious() {
        if (current == null) {
            return true;
        } else {
            switch (current) {
                case LeafNode leafNode -> {
                    return leafNode.previousLeafNode() != null;
                }
                case InternalNode internalNode -> {
                    return internalNode.previousInternalNode() != null;
                }
            }
        }
    }

    @Override
    public Node next() {
        if (current == null) {
            switch (start) {
                case LeafNode _ -> current = start;
                case InternalNode internalNode-> current = start;
            }
        } else {
            switch (current) {
                case LeafNode leafNode -> {
                    current = leafNode.nextLeafNode();
                }
                case InternalNode internalNode-> {
                    InternalNode nextInternalNode =  internalNode.nextInternalNode();
                    if (nextInternalNode != null) {
                        current = nextInternalNode;
                    } else {
                        nextInternalNode = internalNode.nextInternalNode();
                        if (nextInternalNode != null) {
                            current = nextInternalNode;
                        } else {
                            current = current.bPlusTree.getRoot().findSmallestInternalNodeAtDepth(current.depth() + 1);
                        }
                    }
                }
            }
        }
        return current;
    }


    @Override
    public Node previous() {
        if (current == null) {
            switch (start) {
                case LeafNode _ -> current = start;
                case InternalNode internalNode-> current = internalNode.findBiggestLeafNode();
            }
        } else {
            switch (start) {
                case LeafNode leafNode -> current = leafNode.previousLeafNode();
                case InternalNode internalNode-> current = internalNode.previousInternalNode();
            }
        }
        return current;
    }

    @Override
    public Node last() {
        Node last = null;
        Node next = next();
        if (next != null) {
            while (next != null) {
                last = next;
                switch (next) {
                    case LeafNode leafNode -> next = leafNode.nextLeafNode();
                    case InternalNode internalNode-> next = internalNode.nextInternalNode();
                }
            }
        } else {
            last = current;
        }
        return last;
    }

}
