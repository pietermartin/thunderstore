package org.sqlg.thunderstore;

import java.util.Iterator;

public interface BPlusIterator<E> extends Iterator<E> {
    boolean hasPrevious();
    E previous();
    static <E> BPlusIterator<E> emptyListIterator() {
        return new BPlusIterator<>() {

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public E next() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public E previous() {
                return null;
            }

            @Override
            public E last() {
                return null;
            }
        };
    }
    E last();
}
