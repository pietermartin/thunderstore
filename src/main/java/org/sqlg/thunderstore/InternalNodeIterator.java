package org.sqlg.thunderstore;

public class InternalNodeIterator implements BPlusIterator<InternalNode> {

    private InternalNode current;

    public InternalNodeIterator(InternalNode start) {
        assert start != null : "start must not be null";
        this.current = start;
    }

    @Override
    public boolean hasNext() {
        return this.current.nextInternalNode() != null;
    }

    @Override
    public InternalNode next() {
        this.current =  this.current.nextInternalNode();
        return this.current;
    }

    @Override
    public boolean hasPrevious() {
        return this.current.previousInternalNode() != null;
    }

    @Override
    public InternalNode previous() {
        this.current = this.current.previousInternalNode();
        return this.current;
    }

    @Override
    public InternalNode last() {
        InternalNode next = next();
        while (next != null) {
            next = next.nextInternalNode();
        }
        return next;
    }
}
