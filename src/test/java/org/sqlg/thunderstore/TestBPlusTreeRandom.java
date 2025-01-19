package org.sqlg.thunderstore;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.logging.Logger;

public class TestBPlusTreeRandom extends BaseTest {

    private final static Logger LOGGER = Logger.getLogger(TestBPlusTreeRandom.class.getName());

    @Test
    public void testBPlusTreeOrder3() {
        BPlusTree bPlusTree = new BPlusTree(3);
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int key = rand.nextInt(1000);
            LeafNode leafNode = bPlusTree.searchForLeafNode(key);
            if (leafNode == null) {
                bPlusTree.insert(key);
            }
        }
        assertTreeGeneral(bPlusTree);
        LOGGER.info(bPlusTree.print().toString());
    }
}
