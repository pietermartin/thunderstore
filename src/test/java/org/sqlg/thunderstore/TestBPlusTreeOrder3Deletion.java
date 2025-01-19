package org.sqlg.thunderstore;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

public class TestBPlusTreeOrder3Deletion extends BaseTest {

    protected final static Logger LOGGER = Logger.getLogger(TestBPlusTreeOrder3Deletion.class.getName());

    @Test
    public void testBPlusTreeOrder3DeletionFromRight5() {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(1).insert(2).insert(3)
                .insert(4).insert(5)
        ;
        LOGGER.info(bPlusTree.print().toString());
        bPlusTree.delete(5);
        LOGGER.info(bPlusTree.print().toString());
        bPlusTree.delete(4);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(1), List.of(2), List.of(3)),
                List.of(List.of(2, 3))
        );

        bPlusTree.delete(3);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(1), List.of(2)),
                List.of(List.of(2))
        );

        bPlusTree.delete(2);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                1,
                1,
                List.of(List.of(1)),
                List.of()
        );
    }

    @Test
    public void testBPlusTreeOrder3DeletionFromLeft5() {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(1).insert(2).insert(3)
                .insert(4).insert(5)
        ;
        LOGGER.info(bPlusTree.print().toString());
        bPlusTree.delete(1);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(2), List.of(3), List.of(4, 5)),
                List.of(List.of(3, 4))
        );
        LOGGER.info(bPlusTree.print().toString());
        bPlusTree.delete(2);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(3), List.of(4, 5)),
                List.of(List.of(4))
        );

        bPlusTree.delete(3);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(4), List.of(5)),
                List.of(List.of(5))
        );

        bPlusTree.delete(4);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                1,
                1,
                List.of(List.of(5)),
                List.of()
        );
    }

    @Test
    public void testBPlusTreeOrder3DeletionFromRight9() {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(1).insert(2).insert(3)
                .insert(4).insert(5).insert(6)
                .insert(7).insert(8).insert(9)
        ;
        LOGGER.info(bPlusTree.print().toString());
        bPlusTree.delete(9);
        LOGGER.info(bPlusTree.print().toString());
        bPlusTree.delete(8);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                7,
                List.of(List.of(1), List.of(2), List.of(3), List.of(4), List.of(5), List.of(6), List.of(7)),
                List.of(List.of(3, 5), List.of(2), List.of(4), List.of(6, 7))
        );
        bPlusTree.delete(7);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                6,
                List.of(List.of(1), List.of(2), List.of(3), List.of(4), List.of(5), List.of(6)),
                List.of(List.of(3, 5), List.of(2), List.of(4), List.of(6))
        );

        bPlusTree.delete(6);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1), List.of(2), List.of(3), List.of(4), List.of(5)),
                List.of(List.of(3), List.of(2), List.of(4, 5))
        );

        bPlusTree.delete(5);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                4,
                List.of(List.of(1), List.of(2), List.of(3), List.of(4)),
                List.of(List.of(3), List.of(2), List.of(4))
        );

        bPlusTree.delete(4);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(1), List.of(2), List.of(3)),
                List.of(List.of(2, 3))
        );

        bPlusTree.delete(3);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(1), List.of(2)),
                List.of(List.of(2))
        );

        bPlusTree.delete(2);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                1,
                1,
                List.of(List.of(1)),
                List.of()
        );
    }

    @Test
    public void testBPlusTreeOrder3DeletionFromLeft9() {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(1).insert(2).insert(3)
                .insert(4).insert(5).insert(6)
                .insert(7).insert(8).insert(9)
        ;
        LOGGER.info(bPlusTree.print().toString());
        bPlusTree.delete(1);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                7,
                List.of(List.of(2), List.of(3), List.of(4), List.of(5), List.of(6), List.of(7), List.of(8, 9)),
                List.of(List.of(5, 7), List.of(3, 4), List.of(6), List.of(8))
        );

        bPlusTree.delete(2);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                6,
                List.of(List.of(3), List.of(4), List.of(5), List.of(6), List.of(7), List.of(8, 9)),
                List.of(List.of(5, 7), List.of(4), List.of(6), List.of(8))
        );

        bPlusTree.delete(3);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(4), List.of(5), List.of(6), List.of(7), List.of(8, 9)),
                List.of(List.of(7), List.of(5, 6), List.of(8))
        );

        bPlusTree.delete(4);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                4,
                List.of(List.of(5), List.of(6), List.of(7), List.of(8, 9)),
                List.of(List.of(7), List.of(6), List.of(8))
        );

        bPlusTree.delete(5);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(6), List.of(7), List.of(8, 9)),
                List.of(List.of(7, 8))
        );

        bPlusTree.delete(6);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(7), List.of(8, 9)),
                List.of(List.of(8))
        );

        bPlusTree.delete(7);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(8), List.of(9)),
                List.of(List.of(9))
        );

        bPlusTree.delete(8);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                1,
                1,
                List.of(List.of(9)),
                List.of()
        );
    }

    @Test
    public void testBPlusTreeOrder3DeletionFromMiddleLeft9() {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(1).insert(2).insert(3)
                .insert(4).insert(5).insert(6)
                .insert(7).insert(8).insert(9)
        ;
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                4,
                8,
                List.of(List.of(1), List.of(2), List.of(3), List.of(4), List.of(5), List.of(6), List.of(7), List.of(8, 9)),
                List.of(List.of(5), List.of(3), List.of(7), List.of(2), List.of(4), List.of(6), List.of(8))
        );

        bPlusTree.delete(3);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                7,
                List.of(List.of(1), List.of(2), List.of(4), List.of(5), List.of(6), List.of(7), List.of(8, 9)),
                List.of(List.of(5, 7), List.of(2, 4), List.of(6), List.of(8))
        );

        bPlusTree.delete(5);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                6,
                List.of(List.of(1), List.of(2), List.of(4), List.of(6), List.of(7), List.of(8, 9)),
                List.of(List.of(4, 7), List.of(2), List.of(6), List.of(8))
        );

        bPlusTree.delete(4);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1), List.of(2), List.of(6), List.of(7), List.of(8, 9)),
                List.of(List.of(7), List.of(2, 6), List.of(8))
        );

        bPlusTree.delete(2);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                4,
                List.of(List.of(1), List.of(6), List.of(7), List.of(8, 9)),
                List.of(List.of(7), List.of(6), List.of(8))
        );

        bPlusTree.delete(6);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(1), List.of(7), List.of(8, 9)),
                List.of(List.of(7, 8))
        );

        bPlusTree.delete(7);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(1), List.of(8, 9)),
                List.of(List.of(8))
        );

        bPlusTree.delete(1);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(8), List.of(9)),
                List.of(List.of(9))
        );

        bPlusTree.delete(8);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                1,
                1,
                List.of(List.of(9)),
                List.of()
        );
    }

    @Test
    public void testBPlusTreeOrder3DeletionEntryAlsoInternalNode9() {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(1).insert(2).insert(3)
                .insert(4).insert(5).insert(6)
                .insert(7).insert(8).insert(9)
        ;
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                4,
                8,
                List.of(List.of(1), List.of(2), List.of(3), List.of(4), List.of(5), List.of(6), List.of(7), List.of(8, 9)),
                List.of(List.of(5), List.of(3), List.of(7), List.of(2), List.of(4), List.of(6), List.of(8))
        );

        bPlusTree.delete(5);
        LOGGER.info(bPlusTree.print().toString());
        assertTreeGeneral(bPlusTree);

    }

    @Test
    public void testBPlusTreeOrder3DeletionFromMiddleRight9() {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(1).insert(2).insert(3)
                .insert(4).insert(5).insert(6)
                .insert(7).insert(8).insert(9)
        ;
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                4,
                8,
                List.of(List.of(1), List.of(2), List.of(3), List.of(4), List.of(5), List.of(6), List.of(7), List.of(8, 9)),
                List.of(List.of(5), List.of(3), List.of(7), List.of(2), List.of(4), List.of(6), List.of(8))
        );

        bPlusTree.delete(6);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                7,
                List.of(List.of(1), List.of(2), List.of(3), List.of(4), List.of(5), List.of(7), List.of(8, 9)),
                List.of(List.of(3, 5), List.of(2), List.of(4), List.of(7, 8))
        );

        bPlusTree.delete(4);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                6,
                List.of(List.of(1), List.of(2), List.of(3), List.of(5), List.of(7), List.of(8, 9)),
                List.of(List.of(5), List.of(2, 3), List.of(7, 8))
        );
    }

    @Test
    public void testBPlusTreeOrder3DeletionFromMiddleRight5s() {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(5).insert(10).insert(15)
                .insert(20).insert(25).insert(30)
                .insert(35)
        ;
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                6,
                List.of(List.of(5), List.of(10), List.of(15), List.of(20), List.of(25), List.of(30, 35)),
                List.of(List.of(15, 25), List.of(10), List.of(20), List.of(30))
        );
        LOGGER.info(bPlusTree.print().toString());

        bPlusTree.insert(11).insert(12);
        bPlusTree.insert(16).insert(17);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                8,
                List.of(List.of(5), List.of(10), List.of(11, 12), List.of(15), List.of(16, 17), List.of(20), List.of(25), List.of(30, 35)),
                List.of(List.of(15, 25), List.of(10, 11), List.of(16, 20), List.of(30))
        );
        bPlusTree.insert(13);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                4,
                9,
                List.of(List.of(5), List.of(10), List.of(11), List.of(12, 13), List.of(15), List.of(16, 17), List.of(20), List.of(25), List.of(30, 35)),
                List.of(List.of(15), List.of(11), List.of(25), List.of(10), List.of(12), List.of(16, 20), List.of(30))
        );

        bPlusTree.delete(16);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                4,
                9,
                List.of(List.of(5), List.of(10), List.of(11), List.of(12, 13), List.of(15), List.of(17), List.of(20), List.of(25), List.of(30, 35)),
                List.of(List.of(15), List.of(11), List.of(25), List.of(10), List.of(12), List.of(17, 20), List.of(30))
        );

        bPlusTree.delete(17);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                4,
                8,
                List.of(List.of(5), List.of(10), List.of(11), List.of(12, 13), List.of(15), List.of(20), List.of(25), List.of(30, 35)),
                List.of(List.of(15), List.of(11), List.of(25), List.of(10), List.of(12), List.of(20), List.of(30))
        );

        bPlusTree.delete(13);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                4,
                8,
                List.of(List.of(5), List.of(10), List.of(11), List.of(12), List.of(15), List.of(20), List.of(25), List.of(30, 35)),
                List.of(List.of(15), List.of(11), List.of(25), List.of(10), List.of(12), List.of(20), List.of(30))
        );
    }

    @Test
    public void testBPlusTreeOrder3DeletionFromMiddleRight5s_again() {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(5).insert(10).insert(15)
                .insert(20).insert(25).insert(30)
        ;
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(5), List.of(10), List.of(15), List.of(20), List.of(25, 30)),
                List.of(List.of(15), List.of(10), List.of(20, 25))
        );

        bPlusTree.insert(14).insert(13);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                6,
                List.of(List.of(5), List.of(10), List.of(13, 14), List.of(15), List.of(20), List.of(25, 30)),
                List.of(List.of(15), List.of(10, 13), List.of(20, 25))
        );

        bPlusTree.delete(13);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                6,
                List.of(List.of(5), List.of(10), List.of(14), List.of(15), List.of(20), List.of(25, 30)),
                List.of(List.of(15), List.of(10, 14), List.of(20, 25))
        );

        bPlusTree.delete(14);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(5), List.of(10), List.of(15), List.of(20), List.of(25, 30)),
                List.of(List.of(15), List.of(10), List.of(20, 25))
        );

        bPlusTree.delete(10);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                4,
                List.of(List.of(5), List.of(15), List.of(20), List.of(25, 30)),
                List.of(List.of(20), List.of(15), List.of(25))
        );

        bPlusTree.delete(15);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(5), List.of(20), List.of(25, 30)),
                List.of(List.of(20, 25))
        );
    }
}
