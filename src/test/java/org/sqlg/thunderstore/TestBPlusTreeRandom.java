package org.sqlg.thunderstore;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestBPlusTreeRandom extends BaseTest {

    private final static Logger LOGGER = Logger.getLogger(TestBPlusTreeRandom.class.getName());

    @Test
    public void testBPlusTreeOrder3() {
        for (int j = 0; j < 1000; j++) {

            int KEYS_TO_ADD = 1000;
            BPlusTree bPlusTree = new BPlusTree(3);
            Random rand = new Random();
            List<Integer> inserted = new ArrayList<>();
            for (int i = 0; i < KEYS_TO_ADD; i++) {
                int key = rand.nextInt(1000);
                LeafNode leafNode = bPlusTree.searchForLeafNode(key);
                if (leafNode == null) {
                    inserted.add(key);
                    bPlusTree.insert(key);
                }
            }
//            LOGGER.log(Level.INFO, "inserted keys: {0}", inserted.stream().map(i -> ".insert(" + i + ")").reduce((a, b) -> a + b).orElse(""));
            assertTreeGeneral(bPlusTree);
//            LOGGER.info(bPlusTree.print().toString());

            List<Integer> deleted = new ArrayList<>();
            for (int i = 0; i < KEYS_TO_ADD; i++) {
                int key = rand.nextInt(1000);
                LeafNode leafNode = bPlusTree.searchForLeafNode(key);
                if (leafNode != null) {
                    deleted.add(key);
                    bPlusTree.delete(key);
                }
            }
//            LOGGER.log(Level.INFO, "deleted keys: {0}", deleted.stream().map(i -> ".delete(" + i + ")").reduce((a, b) -> a + b).orElse(""));
//            LOGGER.info(bPlusTree.print().toString());
            assertTreeGeneral(bPlusTree);

        }
    }


    @Test
    public void testBPlusTreeOrder4() {
        for (int j = 0; j < 1000; j++) {

            int KEYS_TO_ADD = 1000;
            BPlusTree bPlusTree = new BPlusTree(4);
            Random rand = new Random();
            List<Integer> inserted = new ArrayList<>();
            for (int i = 0; i < KEYS_TO_ADD; i++) {
                int key = rand.nextInt(1000);
                LeafNode leafNode = bPlusTree.searchForLeafNode(key);
                if (leafNode == null) {
                    inserted.add(key);
                    bPlusTree.insert(key);
                }
            }
//            LOGGER.log(Level.INFO, "inserted keys: {0}", inserted.stream().map(i -> ".insert(" + i + ")").reduce((a, b) -> a + b).orElse(""));
            assertTreeGeneral(bPlusTree);
//            LOGGER.info(bPlusTree.print().toString());

            List<Integer> deleted = new ArrayList<>();
            for (int i = 0; i < KEYS_TO_ADD; i++) {
                int key = rand.nextInt(1000);
                LeafNode leafNode = bPlusTree.searchForLeafNode(key);
                if (leafNode != null) {
                    deleted.add(key);
                    bPlusTree.delete(key);
                }
            }
//            LOGGER.info(bPlusTree.print().toString());
            assertTreeGeneral(bPlusTree);

        }
    }

    @Test
    public void testBPlusTreeOrder5() {
        for (int j = 0; j < 1000; j++) {

            int KEYS_TO_ADD = 10_00;
            BPlusTree bPlusTree = new BPlusTree(5);
            Random rand = new Random();
            List<Integer> inserted = new ArrayList<>();
            for (int i = 0; i < KEYS_TO_ADD; i++) {
                int key = rand.nextInt(KEYS_TO_ADD);
                LeafNode leafNode = bPlusTree.searchForLeafNode(key);
                if (leafNode == null) {
                    inserted.add(key);
                    bPlusTree.insert(key);
                }
            }
//            LOGGER.log(Level.INFO, "inserted keys: {0}", inserted.stream().map(i -> ".insert(" + i + ")").reduce((a, b) -> a + b).orElse(""));
            assertTreeGeneral(bPlusTree);
//            LOGGER.info(bPlusTree.print().toString());

            List<Integer> deleted = new ArrayList<>();
            try {
                for (int i = 0; i < KEYS_TO_ADD; i++) {
                    int key = rand.nextInt(1000);
                    LeafNode leafNode = bPlusTree.searchForLeafNode(key);
                    if (leafNode != null) {
                        deleted.add(key);
                        bPlusTree.delete(key);
                    }
                }
                assertTreeGeneral(bPlusTree);
            } finally {
//                LOGGER.log(Level.INFO, "deleted keys: {0}", deleted.stream().map(i -> ".delete(" + i + ")").reduce((a, b) -> a + b).orElse(""));
            }

        }
    }

    @Test
    public void testBPlusTreeOrderX() {
        for (int j = 0; j < 10; j++) {
            int KEYS_TO_ADD = 1_000_000;
            Random randOrder = new Random();
            BPlusTree bPlusTree = new BPlusTree(randOrder.nextInt(3, 10_000));
            Random rand = new Random();
            for (int i = 0; i < KEYS_TO_ADD; i++) {
                int key = rand.nextInt(KEYS_TO_ADD);
                LeafNode leafNode = bPlusTree.searchForLeafNode(key);
                if (leafNode == null) {
                    bPlusTree.insert(key);
                }
            }
            assertTreeGeneral(bPlusTree);
            for (int i = 0; i < KEYS_TO_ADD; i++) {
                int key = rand.nextInt(KEYS_TO_ADD);
                LeafNode leafNode = bPlusTree.searchForLeafNode(key);
                if (leafNode != null) {
                    bPlusTree.delete(key);
                }
            }
            assertTreeGeneral(bPlusTree);
            LOGGER.log(Level.INFO, "completed {0}", Integer.toString(j));
        }
    }

    @Test
    public void testBPlusTreeOrderLarge() {
        BPlusTree bPlusTree = new BPlusTree(100);
        int KEYS_TO_ADD = 10_000_000;
        Random rand = new Random();
        for (int i = 0; i < KEYS_TO_ADD; i++) {
            int key = rand.nextInt(KEYS_TO_ADD);
            LeafNode leafNode = bPlusTree.searchForLeafNode(key);
            if (leafNode == null) {
                bPlusTree.insert(key);
            }
            if (i % 1_000_000 == 0) {
                LOGGER.log(Level.INFO, "insert {0}, depth {1}", new Object[]{Integer.toString(i), Integer.toString(bPlusTree.depth())});
            }
        }
        assertTreeGeneral(bPlusTree);
        for (int i = 0; i < KEYS_TO_ADD; i++) {
            int key = rand.nextInt(KEYS_TO_ADD);
            LeafNode leafNode = bPlusTree.searchForLeafNode(key);
            if (leafNode != null) {
                bPlusTree.delete(key);
            }
            if (i % 100_000 == 0) {
                LOGGER.log(Level.INFO, "delete {0}, depth {1}", new Object[]{Integer.toString(i), Integer.toString(bPlusTree.depth())});
            }
        }
        assertTreeGeneral(bPlusTree);
    }
}
