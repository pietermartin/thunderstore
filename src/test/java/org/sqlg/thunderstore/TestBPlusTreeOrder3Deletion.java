package org.sqlg.thunderstore;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

public class TestBPlusTreeOrder3Deletion extends BaseTest {

    private final static Logger LOGGER = Logger.getLogger(TestBPlusTreeOrder3Deletion.class.getName());

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

    @Test
    public void testDeleteWithParent() {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(691).insert(707).insert(355).insert(817).insert(627).insert(106)
                .insert(171).insert(44).insert(42).insert(351).insert(392).insert(794)
                .insert(187).insert(766).insert(390).insert(750).insert(101).insert(248)
                .insert(443).insert(609).insert(63).insert(669).insert(306).insert(644)
                .insert(713).insert(33).insert(908).insert(96).insert(160).insert(341)
                .insert(515);
        LOGGER.info(bPlusTree.print().toString());
        assertTree(
                bPlusTree,
                5,
                21,
                List.of(List.of(33, 42), List.of(44), List.of(63), List.of(96, 101), List.of(106, 160), List.of(171), List.of(187), List.of(248), List.of(306), List.of(341, 351), List.of(355, 390),
                        List.of(392), List.of(443, 515), List.of(609), List.of(627), List.of(644, 669), List.of(691), List.of(707, 713), List.of(750, 766), List.of(794), List.of(817, 908)),
                List.of(List.of(355), List.of(171), List.of(691), List.of(63), List.of(248), List.of(443, 627), List.of(750), List.of(44), List.of(96,106), List.of(187), List.of(306, 341),
                        List.of(392), List.of(609), List.of(644), List.of(707), List.of(794, 817))
        );

        bPlusTree.delete(750);
        assertTree(
                bPlusTree,
                5,
                21,
                List.of(List.of(33, 42), List.of(44), List.of(63), List.of(96, 101), List.of(106, 160), List.of(171), List.of(187), List.of(248), List.of(306), List.of(341, 351), List.of(355, 390),
                        List.of(392), List.of(443, 515), List.of(609), List.of(627), List.of(644, 669), List.of(691), List.of(707, 713), List.of(766), List.of(794), List.of(817, 908)),
                List.of(List.of(355), List.of(171), List.of(691), List.of(63), List.of(248), List.of(443, 627), List.of(766), List.of(44), List.of(96,106), List.of(187), List.of(306, 341),
                        List.of(392), List.of(609), List.of(644), List.of(707), List.of(794, 817))
        );
    }

    @Test
    public void testDelete() {

        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(436).insert(848).insert(39).insert(632).insert(11).insert(662).insert(211).insert(496).insert(746).insert(319)
                .insert(959).insert(890).insert(547).insert(487).insert(584).insert(528).insert(693).insert(119).insert(193).insert(160)
                .insert(861).insert(225).insert(834).insert(933).insert(521).insert(388).insert(303).insert(941).insert(453).insert(915);
        assertTree(
                bPlusTree,
                4,
                19,
                List.of(List.of(11), List.of(39), List.of(119), List.of(160, 193), List.of(211), List.of(225, 303), List.of(319, 388), List.of(436), List.of(453, 487), List.of(496),
                        List.of(521, 528), List.of(547, 584), List.of(632), List.of(662, 693), List.of(746, 834), List.of(848, 861), List.of(890, 915), List.of(933), List.of(941, 959)),
                List.of(List.of(211, 632), List.of(119), List.of(319, 496), List.of(746, 890), List.of(39), List.of(160), List.of(225), List.of(436, 453), List.of(521, 547), List.of(662), List.of(848), List.of(933, 941))
        );


        bPlusTree.delete(39);
        assertTree(
                bPlusTree,
                4,
                18,
                List.of(List.of(11), List.of(119), List.of(160, 193), List.of(211), List.of(225, 303), List.of(319, 388), List.of(436), List.of(453, 487), List.of(496),
                        List.of(521, 528), List.of(547, 584), List.of(632), List.of(662, 693), List.of(746, 834), List.of(848, 861), List.of(890, 915), List.of(933), List.of(941, 959)),
                List.of(List.of(319, 632), List.of(211), List.of(496), List.of(746, 890), List.of(119, 160), List.of(225), List.of(436, 453), List.of(521, 547), List.of(662), List.of(848), List.of(933, 941))
        );
        LOGGER.info(bPlusTree.print().toString());

    }

    @Test
    public void testDeleteAgain() {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(206).insert(380).insert(160).insert(693).insert(121).insert(53)
                .insert(710).insert(146).insert(386).insert(786).insert(609).insert(588)
                .insert(129).insert(659).insert(783).insert(106).insert(418).insert(51)
                .insert(149).insert(679);

        assertTree(
                bPlusTree,
                4,
                14,
                List.of(List.of(51), List.of(53, 106), List.of(121, 129), List.of(146), List.of(149, 160), List.of(206), List.of(380), List.of(386, 418),
                        List.of(588), List.of(609), List.of(659, 679), List.of(693), List.of(710), List.of(783, 786)),
                List.of(List.of(386), List.of(121, 206), List.of(609, 693), List.of(53), List.of(146,149), List.of(380), List.of(588), List.of(659), List.of(710, 783))
        );

        bPlusTree.delete(679);
        assertTree(
                bPlusTree,
                4,
                14,
                List.of(List.of(51), List.of(53, 106), List.of(121, 129), List.of(146), List.of(149, 160), List.of(206), List.of(380), List.of(386, 418),
                        List.of(588), List.of(609), List.of(659), List.of(693), List.of(710), List.of(783, 786)),
                List.of(List.of(386), List.of(121, 206), List.of(609, 693), List.of(53), List.of(146,149), List.of(380), List.of(588), List.of(659), List.of(710, 783))
        );
//        LOGGER.info(bPlusTree.print().toString());

        bPlusTree.delete(206);
        LOGGER.info(bPlusTree.print().toString());
        assertTreeGeneral(bPlusTree);

        bPlusTree.delete(588);
        assertTreeGeneral(bPlusTree);

    }
}
