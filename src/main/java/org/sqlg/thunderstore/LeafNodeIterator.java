package org.sqlg.thunderstore;

public class LeafNodeIterator implements BPlusIterator<LeafNode> {

    private final Node start;
    private LeafNode current = null;

    public LeafNodeIterator(Node start) {
        assert start != null : "start must not be null";
        this.start = start;
    }

    @Override
    public boolean hasNext() {
        if (current == null) {
            return true;
        } else {
            return current.nextLeafNode() != null;
        }
    }

    @Override
    public boolean hasPrevious() {
        if (current == null) {
            return true;
        } else {
            return current.previousLeafNode() != null;
        }
    }

    @Override
    public LeafNode next() {
        if (current == null) {
            if (start instanceof LeafNode) {
                current = (LeafNode) start;
            } else {
                InternalNode internalNode = (InternalNode) start;
                current = internalNode.findSmallestLeafNode();
            }
        } else {
            current = current.nextLeafNode();
        }
        return current;
    }


    @Override
    public LeafNode previous() {
        if (current == null) {
            if (start instanceof LeafNode) {
                current = (LeafNode) start;
            } else {
                InternalNode internalNode = (InternalNode) start;
                current = internalNode.findBiggestLeafNode();
            }
        } else {
            current = current.previousLeafNode();
        }
        return current;
    }

    @Override
    public LeafNode last() {
        LeafNode last = null;
        LeafNode next = next();
        if (next != null) {
            while (next != null) {
                last = next;
                next = next.nextLeafNode();
            }

        } else {
            last = current;
        }
        return last;
    }

}
