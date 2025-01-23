package org.sqlg.thunderstore;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

public class TestBPlusTreeOrder5 extends BaseTest {

    private final static Logger LOGGER = Logger.getLogger(TestBPlusTreeOrder5.class.getName());

    @Test
    public void failure1() {
        BPlusTree bPlusTree = new BPlusTree(5);
        bPlusTree.insert(991).insert(951).insert(423).insert(216).insert(788).insert(171).insert(109).insert(608).insert(522).insert(553);
        assertTree(
                bPlusTree,
                2,
                4,
                List.of(List.of(109, 171), List.of(216, 423), List.of(522, 553, 608), List.of(788, 951, 991)),
                List.of(List.of(216, 522, 788))
        );
        LOGGER.info(bPlusTree.print().toString());

        bPlusTree.delete(216);
        LOGGER.info(bPlusTree.print().toString());
        assertTreeGeneral(bPlusTree);
    }

    @Test
    public void failure2() {
        BPlusTree bPlusTree = new BPlusTree(5);
        bPlusTree.insert(452).insert(277).insert(212).insert(611).insert(444).insert(722).insert(90).insert(26).insert(221).insert(257).insert(630).insert(262).insert(672).insert(718);

        assertTree(
                bPlusTree,
                3,
                6,
                List.of(List.of(26, 90), List.of(212, 221), List.of(257, 262, 277), List.of(444, 452), List.of(611, 630), List.of(672, 718, 722)),
                List.of(List.of(444), List.of(212, 257), List.of(611, 672))
        );
        LOGGER.info(bPlusTree.print().toString());

        bPlusTree.delete(221);
        LOGGER.info(bPlusTree.print().toString());
        assertTreeGeneral(bPlusTree);
    }

    @Test
    public void failure3() {
        BPlusTree bPlusTree = new BPlusTree(5);
        bPlusTree.insert(457).insert(492).insert(707).insert(201).insert(558).insert(259)
                .insert(83).insert(302).insert(809).insert(505).insert(531).insert(386)
                .insert(716).insert(272).insert(388).insert(345).insert(733).insert(696);

        assertTree(
                bPlusTree,
                3,
                7,
                List.of(List.of(83,201), List.of(259,272), List.of(302,345), List.of(386,388,457), List.of(492,505,531), List.of(558,696,707), List.of(716,733,809)),
                List.of(List.of(386), List.of(259,302), List.of(492,558,716))
        );
        LOGGER.info(bPlusTree.print().toString());

        bPlusTree.delete(272);
        LOGGER.info(bPlusTree.print().toString());
        assertTreeGeneral(bPlusTree);
    }

    @Test
    public void failure4() {
        BPlusTree bPlusTree = new BPlusTree(5);
        bPlusTree.insert(497).insert(432).insert(701).insert(882).insert(746).insert(8)
                .insert(68).insert(717).insert(581).insert(536).insert(679).insert(965)
                .insert(830).insert(972).insert(335).insert(230).insert(96).insert(148)
                .insert(133).insert(275).insert(611).insert(264);

        assertTree(
                bPlusTree,
                3,
                9,
                List.of(List.of(8, 68), List.of(96, 133), List.of(148, 230), List.of(264, 275, 335), List.of(432, 497), List.of(536, 581, 611, 679), List.of(701, 717), List.of(746, 830), List.of(882, 965, 972)),
                List.of(List.of(264, 701), List.of(96, 148), List.of(432, 536), List.of(746, 882))
        );
        LOGGER.info(bPlusTree.print().toString());

        bPlusTree.delete(830);
        LOGGER.info(bPlusTree.print().toString());
        assertTreeGeneral(bPlusTree);
    }

    @Test
    public void failure5() {
        BPlusTree bPlusTree = new BPlusTree(5);
        assertTreeGeneral(bPlusTree);
        bPlusTree.insert(497).insert(432).insert(701).insert(882).insert(746).insert(8)
                .insert(68).insert(717).insert(581).insert(536).insert(679).insert(965)
                .insert(830).insert(972).insert(335).insert(230).insert(96).insert(148)
                .insert(133).insert(275).insert(611).insert(264);

        assertTree(
                bPlusTree,
                3,
                9,
                List.of(List.of(8, 68), List.of(96, 133), List.of(148, 230), List.of(264, 275, 335), List.of(432, 497), List.of(536, 581, 611, 679), List.of(701, 717), List.of(746, 830), List.of(882, 965, 972)),
                List.of(List.of(264, 701), List.of(96, 148), List.of(432, 536), List.of(746, 882))
        );
        LOGGER.info(bPlusTree.print().toString());

        bPlusTree.delete(497).delete(432).delete(701).delete(882).delete(746).delete(8)
                .delete(68).delete(717).delete(581).delete(536).delete(679).delete(965)
                .delete(830).delete(972).delete(335).delete(230).delete(96).delete(148)
                .delete(133).delete(275).delete(611);
        bPlusTree.delete(264);
        LOGGER.info(bPlusTree.print().toString());
        assertTreeGeneral(bPlusTree);
    }
}
