package adventofcode.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.DoubleLinkedListNode;

public class day23
{

    public static void main(String[] args)
    {

        String labels = "284573961";
        // "389125467"; // test data

        List<Integer> cups = labels.chars().map(c -> Integer.parseInt("" + (char) c)).boxed()
                .collect(Collectors.toList());

        part1(cups);
        part2(cups);
    }

    private static void part2(List<Integer> cups)
    {

        Map<Integer, DoubleLinkedListNode<Integer>> nodeLUT = new HashMap<>();
        DoubleLinkedListNode<Integer> currentNode = new DoubleLinkedListNode<Integer>(cups.get(0));
        nodeLUT.put(cups.get(0), currentNode);
        for (int i = 1; i < cups.size(); i++)
        {
            int value = cups.get(i);
            DoubleLinkedListNode<Integer> node = new DoubleLinkedListNode<Integer>(value);
            currentNode.insertBefore(node);
            nodeLUT.put(value, node);
        }

        for (int i = 10; i <= 1_000_000; i++)
        {
            DoubleLinkedListNode<Integer> node = new DoubleLinkedListNode<Integer>(i);
            currentNode.insertBefore(node);
            nodeLUT.put(i, node);
        }
        System.out.println("## " + nodeLUT.size());
        cupsGame(currentNode, nodeLUT, 10_000_000, 1_000_000);

        DoubleLinkedListNode<Integer> cups2 = nodeLUT.get(1);

        long c1 = cups2.next.value;
        long c2 = cups2.next.next.value;
        System.out.println("part2: " + c1 + " * " + c2 + " = " + (c1 * c2));
    }

    private static void part1(List<Integer> cups)
    {
        Map<Integer, DoubleLinkedListNode<Integer>> nodeLUT = new HashMap<>();
        DoubleLinkedListNode<Integer> currentNode = new DoubleLinkedListNode<Integer>(cups.get(0));
        nodeLUT.put(cups.get(0), currentNode);
        for (int i = 1; i < cups.size(); i++)
        {
            int value = cups.get(i);
            DoubleLinkedListNode<Integer> node = new DoubleLinkedListNode<Integer>(value);
            currentNode.insertBefore(node);
            nodeLUT.put(value, node);
        }

        cupsGame(currentNode, nodeLUT, 100, 9);

        DoubleLinkedListNode<Integer> cups2 = nodeLUT.get(1);

        System.out.print("part1: ");
        for (int i = 0; i < 9; i++)
        {
            System.out.print("" + cups2.value);
            cups2 = cups2.next;
        }
        System.out.println();
    }

    private static DoubleLinkedListNode<Integer> cupsGame(DoubleLinkedListNode<Integer> currentNode,
            Map<Integer, DoubleLinkedListNode<Integer>> nodeLUT, int turns, int highestLabel)
    {

        System.out.print(",");

        for (int turn = 0; turn < turns; turn++)
        {
            // pick up cups +1 .. +3
            List<DoubleLinkedListNode<Integer>> pickedUp = new ArrayList<>();
            Set<Integer> pickedUpValues = new HashSet<>();
            for (int j = 0; j < 3; j++)
            {
                DoubleLinkedListNode<Integer> n = currentNode.next.remove();
                pickedUp.add(n);
                pickedUpValues.add(n.value);
            }

            // select destination cup
            int destinationCup = currentNode.value - 1;
            if (destinationCup == 0)
            {
                destinationCup = highestLabel;
            }
            while (pickedUpValues.contains(destinationCup))
            {
                destinationCup--;
                if (destinationCup == 0)
                {
                    destinationCup = highestLabel;
                }
            }

            // go to destination cup
            DoubleLinkedListNode<Integer> destinationNode = nodeLUT.get(destinationCup);

            // put down the picked up cups
            for (int j = 2; j >= 0; j--)
            {
                destinationNode.insertAfter(pickedUp.get(j));
            }

            // System.out.println("#" + turn + ": " + currentNode.value + " -> " + destinationCup + " ["
            // + pickedUp.stream().map(n -> "" + n.value).reduce((a, b) -> a + "," + b).orElse("") + "]");
            // System.out.println("#" + turn + ": " + currentNode.toString(new StringBuilder(), currentNode.previous));

            // switch current cup
            currentNode = currentNode.next;
        }
        return currentNode;
    }

}
