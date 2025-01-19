package org.sqlg.thunderstore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

public class TestBPlusTreeOrder4  extends BaseTest{

    private final static Logger LOGGER = Logger.getLogger(TestBPlusTreeOrder4.class.getName());

    @Test
    public void testBPlusTree28() {
        BPlusTree bPlusTree = new BPlusTree(4);
        bPlusTree.insert(1).insert(2).insert(3)
                .insert(4).insert(5).insert(6)
                .insert(7).insert(8).insert(9)
                .insert(10).insert(11).insert(12)
                .insert(13).insert(14).insert(15)
                .insert(16).insert(17).insert(18)
                .insert(19).insert(20).insert(21)
                .insert(22).insert(23).insert(24)
                .insert(25).insert(26).insert(27).insert(28)
        ;
        assertTree(
                bPlusTree,
                4,
                14,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6), List.of(7, 8), List.of(9, 10),
                        List.of(11, 12), List.of(13, 14), List.of(15, 16), List.of(17, 18), List.of(19, 20),
                        List.of(21, 22), List.of(23, 24), List.of(25, 26), List.of(27, 28)),
                List.of(List.of(19), List.of(7, 13), List.of(25), List.of(3, 5), List.of(9, 11),
                        List.of(15, 17), List.of(21, 23), List.of(27))
        );
        LOGGER.info(bPlusTree.print().toString());

        BPlusIterator<LeafNode> leafNodeBPlusIterator = bPlusTree.leafNodeIterator();
        Assertions.assertTrue(leafNodeBPlusIterator.hasNext());
        Assertions.assertTrue(leafNodeBPlusIterator.hasPrevious());
        LeafNode next = leafNodeBPlusIterator.next();
        Assertions.assertNotNull(next);
        Assertions.assertEquals(List.of(1, 2), next.keys);
        Assertions.assertFalse(leafNodeBPlusIterator.hasPrevious());
        next = leafNodeBPlusIterator.next();
        Assertions.assertNotNull(next);
        Assertions.assertEquals(List.of(3, 4), next.keys);
        Assertions.assertTrue(leafNodeBPlusIterator.hasPrevious());
        LeafNode prev = leafNodeBPlusIterator.previous();
        Assertions.assertNotNull(prev);
        Assertions.assertEquals(List.of(1, 2), prev.keys);

        LeafNode leafNode = bPlusTree.searchForLeafNode(28);
        Assertions.assertNotNull(leafNode, "Failed to find leaf node with key " + 28);

        bPlusTree.delete(28);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                4,
                14,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6), List.of(7, 8), List.of(9, 10),
                        List.of(11, 12), List.of(13, 14), List.of(15, 16), List.of(17, 18), List.of(19, 20),
                        List.of(21, 22), List.of(23, 24), List.of(25, 26), List.of(27)),
                List.of(List.of(19), List.of(7, 13), List.of(25), List.of(3, 5), List.of(9, 11),
                        List.of(15, 17), List.of(21, 23), List.of(27))
        );

    }

}
