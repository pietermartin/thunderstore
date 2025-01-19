package org.sqlg.thunderstore;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Assertions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseTest {

    protected void assertTreeGeneral(BPlusTree bPlusTree) {
        Assertions.assertTrue(bPlusTree.isValid(), "Tree is not valid");
    }

    protected void assertTree(BPlusTree bPlusTree, int depth, int numberOfLeafNodes, List<List<Integer>> leafNodeKeys, List<List<Integer>> internalNodeKeys) {
        assertTreeGeneral(bPlusTree);
        Assertions.assertTrue(bPlusTree.isValid(), "Tree is not valid");
        Assertions.assertEquals(depth, bPlusTree.depth());
        Assertions.assertEquals(numberOfLeafNodes, IteratorUtils.size(bPlusTree.leafNodeIterator()));
        Assertions.assertEquals(numberOfLeafNodes, leafNodeKeys.size());
        int count = 0;
        Set<Integer> keys = new HashSet<>();
        List<LeafNode> leafNodes = IteratorUtils.toList(bPlusTree.leafNodeIterator());
        for (LeafNode leafNode : leafNodes) {
            Assertions.assertEquals(leafNodeKeys.get(count++), leafNode.keys);
            for (Integer key : leafNode.keys) {
                Assertions.assertTrue(keys.add(key));
                LeafNode _leafNode = bPlusTree.searchForLeafNode(key);
                Assertions.assertEquals(_leafNode.keys, leafNode.keys);
            }
        }
        for (List<Integer> internalNodeKey : internalNodeKeys) {
            for (Integer i : internalNodeKey) {
                InternalNode internalNode = bPlusTree.searchInternalNode(i);
                Assertions.assertNotNull(internalNode, "Failed to find internal node with key " + i);
                Assertions.assertEquals(internalNodeKey, internalNode.keys);
            }
        }
        LeafNode last = bPlusTree.leafNodeIterator().last();
        Assertions.assertNotNull(last);
        Assertions.assertEquals(leafNodeKeys.getLast(), last.keys);
        LeafNode previous = last;
        LeafNode _previous = previous;
        while (previous != null) {
            _previous = previous;
            previous = previous.previousLeafNode();
        }
        Assertions.assertEquals(leafNodeKeys.getFirst(), _previous.keys);

        BPlusIterator<LeafNode> iterator = bPlusTree.leafNodeIterator(bPlusTree.searchForLeafNode(leafNodeKeys.getLast().getLast()));
        count = 1;
        while (iterator.hasPrevious()) {
            LeafNode leafNode = iterator.previous();
            Assertions.assertEquals(leafNodeKeys.get(leafNodeKeys.size() - count++), leafNode.keys);
            for (Integer key : leafNode.keys) {
                LeafNode _leafNode = bPlusTree.searchForLeafNode(key);
                Assertions.assertEquals(_leafNode.keys, leafNode.keys);
            }
        }

        //assert InternalNodes
        Set<Integer> _internalNodeKeys = new HashSet<>();
        List<InternalNode> internalNodes = IteratorUtils.toList(bPlusTree.internalNodeIterator());
        for (InternalNode internalNode : internalNodes) {
            _internalNodeKeys.addAll(internalNode.keys);
        }
        //assert that all internal nodes are also leaf nodes
        Assertions.assertTrue(new HashSet<>(keys).containsAll(_internalNodeKeys), "found internal nodes that are not leaf nodes, " + (_internalNodeKeys.removeAll(keys) ? _internalNodeKeys.toString() : ""));
    }
}
