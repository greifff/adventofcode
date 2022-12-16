package adventofcode.aoc2017;

import adventofcode.util.DoubleLinkedListNode;

public class Day17
{

    public static void main(String[] args)
    {
        int test = 3;
        int data = 314;

        part1(test);
        part1(data);
        part2(data);
    }

    private static void part1(int steps)
    {
        DoubleLinkedListNode<Integer> buffer = new DoubleLinkedListNode<>(0);

        for (int i = 1; i <= 2017; i++)
        {
            buffer = buffer.jumpAhead(steps % i);
            buffer.insertAfter(new DoubleLinkedListNode<Integer>(i));
            buffer = buffer.next;
        }

        System.out.println("part1: " + buffer.next.value);
    }

    private static void part2(int steps)
    {
        DoubleLinkedListNode<Integer> buffer = new DoubleLinkedListNode<>(0);

        for (int i = 1; i <= 314; i++)
        {
            buffer = buffer.jumpAhead(steps % i);
            buffer.insertAfter(new DoubleLinkedListNode<Integer>(i));
            buffer = buffer.next;
        }
        for (int i = 315; i <= 50_000_000; i++)
        {
            buffer = buffer.jumpAhead(steps);
            buffer.insertAfter(new DoubleLinkedListNode<Integer>(i));
            buffer = buffer.next;
        }

        // find 0
        while (buffer.value != 0)
        {
            buffer = buffer.next;
        }

        System.out.println("part2: " + buffer.next.value);
    }
}
