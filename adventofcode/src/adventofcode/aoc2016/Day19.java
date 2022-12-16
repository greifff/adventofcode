package adventofcode.aoc2016;

import adventofcode.util.DoubleLinkedListNode;

public class Day19
{

    public static void main(String[] args)
    {
        part1(5);
        part1(3004953);
        part2(5);
        part2(9);
        part2(3004953);
    }

    private static void part1(int count)
    {
        DoubleLinkedListNode<Integer> elf0 = new DoubleLinkedListNode<Integer>(1);
        DoubleLinkedListNode<Integer> elf1 = elf0;
        for (int i = 2; i <= count; i++)
        {
            DoubleLinkedListNode<Integer> elf2 = new DoubleLinkedListNode<Integer>(i);
            elf1.insertAfter(elf2);
            elf1 = elf1.next;
        }

        // System.out.println("# " + elf0.toString(new StringBuilder(), elf0.previous));
        // play
        DoubleLinkedListNode<Integer> elf = elf0;
        while (elf != elf.next)
        {
            elf.next.remove();
            elf = elf.next;
        }

        System.out.println("part1: " + elf.value);
    }

    private static void part2(int count)
    {
        DoubleLinkedListNode<Integer> elf0 = new DoubleLinkedListNode<Integer>(1);
        DoubleLinkedListNode<Integer> elf1 = elf0;
        for (int i = 2; i <= count; i++)
        {
            DoubleLinkedListNode<Integer> elf2 = new DoubleLinkedListNode<Integer>(i);
            elf1.insertAfter(elf2);
            elf1 = elf1.next;
        }

        // System.out.println("# " + elf0.toString(new StringBuilder(), elf0.previous));
        // play
        DoubleLinkedListNode<Integer> elfa = elf0;
        DoubleLinkedListNode<Integer> elfb = elf0.jumpAhead(count / 2);
        // int count1 = count;
        while (elfa != elfa.next)
        {
            elfb.remove();
            elfb = elfb.next;
            count--;
            if ((count & 1) == 0)
            {
                elfb = elfb.next;
            }
            elfa = elfa.next;
        }

        System.out.println("part2: " + elfa.value);
    }
}
