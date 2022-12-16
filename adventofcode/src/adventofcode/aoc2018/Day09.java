package adventofcode.aoc2018;

import adventofcode.util.DoubleLinkedListNode;

public class Day09
{

    public static void main(String[] args)
    {
        part1(9, 25);

        part1(10, 1618);
        part1(13, 7999);
        part1(17, 1104);
        part1(21, 6111);
        part1(30, 5807);

        part1(411, 71170);
        part1(411, 7117000);
    }

    private static void part1(int players, int lastMarble)
    {
        long[] score = new long[players];

        DoubleLinkedListNode<Integer> node = new DoubleLinkedListNode<Integer>(0);

        int currentPlayer = 0;
        for (int marble = 1; marble <= lastMarble; marble++)
        {
            if (marble % 23 == 0)
            {
                score[currentPlayer] += marble;
                node = node.jumpBack(6);
                int marble2 = node.previous.value;
                score[currentPlayer] += marble2;
                node.previous.remove();
            }
            else
            {
                // place marble
                node = node.next;
                node = node.insertAfter(new DoubleLinkedListNode<>(marble));
            }
            currentPlayer = (currentPlayer + 1) % players;
        }
        long highscore = 0;
        for (long s : score)
        {
            highscore = Math.max(highscore, s);
        }
        System.out.println("part1: " + highscore);
    }

}
