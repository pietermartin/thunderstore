package org.sqlg.thunderstore;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

public class TestBPlusTreeOrder4Deletion extends BaseTest {

    protected final static Logger LOGGER = Logger.getLogger(TestBPlusTreeOrder4Deletion.class.getName());

    @Test
    public void testBPlusTreeOrder4DeletionFromRight10() {
        BPlusTree bPlusTree = new BPlusTree(4);
        bPlusTree.insert(1).insert(2).insert(3)
                .insert(4).insert(5).insert(6)
                .insert(7).insert(8).insert(9).insert(10)
        ;
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7), List.of(3, 5), List.of(9))
        );

        bPlusTree.delete(10);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6), List.of(7, 8), List.of(9)),
                List.of(List.of(7), List.of(3, 5), List.of(9))
        );

        bPlusTree.delete(9);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6), List.of(7), List.of(8)),
                List.of(List.of(7), List.of(3, 5), List.of(8))
        );

        bPlusTree.delete(8);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                4,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6), List.of(7)),
                List.of(List.of(5), List.of(3), List.of(7))
        );

        bPlusTree.delete(7);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                4,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5), List.of(6)),
                List.of(List.of(5), List.of(3), List.of(6))
        );

        bPlusTree.delete(6);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5)),
                List.of(List.of(3, 5))
        );

        bPlusTree.delete(5);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(1, 2), List.of(3), List.of(4)),
                List.of(List.of(3, 4))
        );

        bPlusTree.delete(4);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(1, 2), List.of(3)),
                List.of(List.of(3))
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
    public void testBPlusTreeOrder4DeletionFromLeft10() {
        BPlusTree bPlusTree = new BPlusTree(4);
        bPlusTree.insert(1).insert(2).insert(3)
                .insert(4).insert(5).insert(6)
                .insert(7).insert(8).insert(9).insert(10)
        ;
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7), List.of(3, 5), List.of(9))
        );

        bPlusTree.delete(1);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(2), List.of(3, 4), List.of(5, 6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7), List.of(3, 5), List.of(9))
        );

        bPlusTree.delete(2);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(3), List.of(4), List.of(5, 6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7), List.of(4, 5), List.of(9))
        );

        bPlusTree.delete(3);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                4,
                List.of(List.of(4), List.of(5, 6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7), List.of(5), List.of(9))
        );

        bPlusTree.delete(4);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                4,
                List.of(List.of(5), List.of(6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7), List.of(6), List.of(9))
        );

        bPlusTree.delete(5);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7, 9))
        );

        bPlusTree.delete(6);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(7), List.of(8), List.of(9, 10)),
                List.of(List.of(8, 9))
        );

        bPlusTree.delete(7);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(8), List.of(9, 10)),
                List.of(List.of(9))
        );

        bPlusTree.delete(8);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(9), List.of(10)),
                List.of(List.of(10))
        );

        bPlusTree.delete(9);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                1,
                1,
                List.of(List.of(10)),
                List.of()
        );
    }

    @Test
    public void testBPlusTreeOrder4DeletionFromMiddleLeft10() {
        BPlusTree bPlusTree = new BPlusTree(4);
        bPlusTree.insert(1).insert(2).insert(3)
                .insert(4).insert(5).insert(6)
                .insert(7).insert(8).insert(9).insert(10)
        ;
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7), List.of(3, 5), List.of(9))
        );

        bPlusTree.delete(5);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1, 2), List.of(3, 4), List.of(6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7), List.of(3, 6), List.of(9))
        );

        bPlusTree.delete(3);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1, 2), List.of(4), List.of(6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7), List.of(4, 6), List.of(9))
        );

        bPlusTree.delete(4);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1), List.of(2), List.of(6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7), List.of(2, 6), List.of(9))
        );

        bPlusTree.delete(2);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                4,
                List.of(List.of(1), List.of(6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7), List.of(6), List.of(9))
        );

        bPlusTree.delete(6);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(1), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7, 9))
        );

        bPlusTree.delete(7);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(1), List.of(8), List.of(9, 10)),
                List.of(List.of(8, 9))
        );

        bPlusTree.delete(8);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(1), List.of(9, 10)),
                List.of(List.of(9))
        );

        bPlusTree.delete(9);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(1), List.of(10)),
                List.of(List.of(10))
        );
    }

    @Test
    public void testBPlusTreeOrder4DeletionFromMiddleRight10() {
        BPlusTree bPlusTree = new BPlusTree(4);
        bPlusTree.insert(1).insert(2).insert(3)
                .insert(4).insert(5).insert(6)
                .insert(7).insert(8).insert(9).insert(10)
        ;
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6), List.of(7, 8), List.of(9, 10)),
                List.of(List.of(7), List.of(3, 5), List.of(9))
        );

        bPlusTree.delete(8);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6), List.of(7), List.of(9, 10)),
                List.of(List.of(7), List.of(3, 5), List.of(9))
        );

        bPlusTree.delete(7);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6), List.of(9), List.of(10)),
                List.of(List.of(9), List.of(3, 5), List.of(10))
        );

        bPlusTree.delete(5);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1, 2), List.of(3, 4), List.of(6), List.of(9), List.of(10)),
                List.of(List.of(9), List.of(3, 6), List.of(10))
        );

        bPlusTree.delete(6);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1, 2), List.of(3), List.of(4), List.of(9), List.of(10)),
                List.of(List.of(9), List.of(3, 4), List.of(10))
        );

        bPlusTree.delete(3);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                5,
                List.of(List.of(1), List.of(2), List.of(4), List.of(9), List.of(10)),
                List.of(List.of(9), List.of(2, 4), List.of(10))
        );

        bPlusTree.delete(2);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                3,
                4,
                List.of(List.of(1), List.of(4), List.of(9), List.of(10)),
                List.of(List.of(9), List.of(4), List.of(10))
        );

        bPlusTree.delete(4);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                3,
                List.of(List.of(1), List.of(9), List.of(10)),
                List.of(List.of(9, 10))
        );

        bPlusTree.delete(9);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                2,
                2,
                List.of(List.of(1), List.of(10)),
                List.of(List.of(10))
        );

        bPlusTree.delete(10);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                1,
                1,
                List.of(List.of(1)),
                List.of()
        );
    }
}
