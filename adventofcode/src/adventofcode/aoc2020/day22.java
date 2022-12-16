package adventofcode.aoc2020;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class day22
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2020/day22.data");

        int i = input.indexOf("");
        List<Integer> deck1 = input.subList(1, i).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        List<Integer> deck2 = input.subList(i + 2, input.size()).stream().map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());
        ;
        part1(deck1, deck2);
        part2(deck1, deck2);
    }

    private static void part2(List<Integer> deck1, List<Integer> deck2)
    {
        int result = recursiveCombat(deck1, deck2);
        System.out.println("part 2: " + result);
    }

    public static boolean seenBefore(Set<List<Queue<Integer>>> seen, Queue<Integer> p1, Queue<Integer> p2)
    {
        List<Queue<Integer>> l = new ArrayList<>();
        l.add(p1);
        l.add(p2);
        boolean result = seen.contains(l);
        seen.add(l);
        return result;
    }

    static int recursiveCombat(List<Integer> deck1, List<Integer> deck2)
    {
        // System.out.println("35: start round; " + deck1.size() + " vs." + deck2.size());
        List<Integer> a = new ArrayList<>(deck1);
        List<Integer> b = new ArrayList<>(deck2);

        Set<String> historyA = new HashSet<>();
        Set<String> historyB = new HashSet<>();

        while (!a.isEmpty() && !b.isEmpty())
        {
            String keyA = a.stream().map(s1 -> "" + s1).reduce((s2, s3) -> s2 + "," + s3).orElse("");
            String keyB = b.stream().map(s1 -> "" + s1).reduce((s2, s3) -> s2 + "," + s3).orElse("");

            a.hashCode();
            if (historyA.contains(keyA) || historyB.contains(keyB))
            {
                // System.out.println("47: Draw Player 1 declared winner");
                return calculateDeck(a);
            }

            historyA.add(keyA);
            historyB.add(keyB);

            int a1 = a.remove(0);
            int b1 = b.remove(0);

            int outcome;
            if (a1 <= a.size() && b1 <= b.size())
            {
                outcome = recursiveCombat(a.subList(0, a1), b.subList(0, b1));
            }
            else
            {
                outcome = a1 - b1;
            }

            if (outcome > 0)
            {
                a.add(a1);
                a.add(b1);
            }
            else
            {
                b.add(b1);
                b.add(a1);
            }
        }

        // System.out.println("82: Player " + (!a.isEmpty() ? 1 : 2) + " wins.");
        List<Integer> c = new ArrayList<>(!a.isEmpty() ? a : b);
        return calculateDeck(c) * (a.isEmpty() ? -1 : 1);
    }

    static int calculateDeck(List<Integer> deck)
    {
        Collections.reverse(deck);

        int sum = 0;
        for (int i = 0; i < deck.size(); i++)
        {
            sum += deck.get(i) * (i + 1);
        }
        return sum;
    }

    static byte[] hashDecks(List<Integer> deck1, List<Integer> deck2)
    {

        byte[] d1 = new byte[deck1.size()];
        for (int i = 0; i < deck1.size(); i++)
        {
            d1[i] = (byte) (int) deck1.get(i);
        }

        byte[] d2 = new byte[deck2.size()];
        for (int i = 0; i < deck2.size(); i++)
        {
            d2[i] = (byte) (int) deck2.get(i);
        }
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] r1 = digest.digest(d1);
            byte[] r2 = digest.digest(d2);

            byte[] result = new byte[r1.length + r2.length];

            System.arraycopy(r1, 0, result, 0, r1.length);
            System.arraycopy(r2, 0, result, r1.length, r2.length);

            return result;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return new byte[] {};
        }
    }

    private static void part1(List<Integer> deck1, List<Integer> deck2)
    {
        List<Integer> a = new ArrayList<>(deck1);
        List<Integer> b = new ArrayList<>(deck2);

        while (!a.isEmpty() && !b.isEmpty())
        {
            int a1 = a.remove(0);
            int b1 = b.remove(0);
            if (a1 > b1)
            {
                a.add(a1);
                a.add(b1);
            }
            else if (b1 > a1)
            {
                b.add(b1);
                b.add(a1);
            }
        }

        List<Integer> c = new ArrayList<>(!a.isEmpty() ? a : b);
        Collections.reverse(c);

        int sum = 0;
        for (int i = 0; i < c.size(); i++)
        {
            sum += c.get(i) * (i + 1);
        }
        System.out.println("part1: " + sum);
    }

}
