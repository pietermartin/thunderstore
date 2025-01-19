package org.sqlg.thunderstore;

public class AllInternalNodeIterator implements BPlusIterator<InternalNode> {

    private final InternalNode start;
    private InternalNode current;
    private InternalNode currentLeftMost;
    private InternalNode currentRightMost;

    public AllInternalNodeIterator(InternalNode start) {
        if (start == null) {
            throw new IllegalArgumentException("start cannot be null");
        }
        if (!start.isRoot()) {
            throw new IllegalArgumentException("start must be a root node");
        }
        this.start = start;
        this.currentLeftMost = start;
        this.currentRightMost = start;
    }

    @Override
    public boolean hasPrevious() {
        InternalNode previous = current.previousInternalNode();
        if (previous == null) {
            //find the next level down's left most InternalNode
            return currentRightMost.parent != null;
        } else {
            return true;
        }
    }

    @Override
    public InternalNode previous() {
        InternalNode previous = current.previousInternalNode();
        if (previous == null) {
            //find the previous level up right most InternalNode
            InternalNode rightMost = currentRightMost.parent;
            current = rightMost;
            currentRightMost = current;
            return rightMost;
        } else {
            current = previous;
            return previous;
        }
    }

    @Override
    public InternalNode last() {
        return null;
    }

    @Override
    public boolean hasNext() {
        if (current == null) {
            return true;
        } else {
            InternalNode next = current.nextInternalNode();
            if (next == null) {
                //find the next level down's left most InternalNode
                currentRightMost = current;
                Node leftMost = currentLeftMost.children.getFirst();
                return leftMost instanceof InternalNode;
            } else {
                return true;
            }
        }
    }

    @Override
    public InternalNode next() {
        if (current == null) {
            current = start;
            return current;
        } else {
            InternalNode next = current.nextInternalNode();
            if (next == null) {
                //find the next level down's left most InternalNode
                Node leftMost = currentLeftMost.children.getFirst();
                assert leftMost instanceof InternalNode;
                current = (InternalNode) leftMost;
                currentLeftMost = current;
                return (InternalNode) leftMost;
            } else {
                current = next;
                return next;
            }
        }
    }
}
