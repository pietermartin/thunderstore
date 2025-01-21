package org.sqlg.thunderstore;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

public class TestBPlusTreeTestIterators extends BaseTest {

    private final static Logger LOGGER = Logger.getLogger(TestBPlusTreeTestIterators.class.getName());

    @Test
    public void testNodeIterator() {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(1).insert(2).insert(3).insert(4);
        bPlusTree.insert(5).insert(6).insert(7).insert(8);
        LOGGER.info(bPlusTree.print().toString());

        BPlusIterator<InternalNode> internalNodeBPlusIterator = bPlusTree.internalNodeIterator();
        List<InternalNode> internalNodes = IteratorUtils.toList(internalNodeBPlusIterator);
        Assertions.assertEquals(4, internalNodes.size());

        BPlusIterator<Node> iterator = bPlusTree.nodeIterator(bPlusTree.getRoot());
        List<Node> nodes = IteratorUtils.toList(iterator);
        Assertions.assertEquals(11, nodes.size());
    }
}
