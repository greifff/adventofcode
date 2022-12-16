package adventofcode.aoc2016;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day7
{

    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2016/day07.test");
        List<String> input2 = IOUtil.readFile("2016/day07.data");

        System.out.println("test 1: " + input1.stream().map(address -> isSnoopable(address)).filter(b -> b).count());
        System.out.println("part 1: " + input2.stream().map(address -> isSnoopable(address)).filter(b -> b).count());

        System.out.println("test 2: " + input1.stream().map(address -> isListenable(address)).filter(b -> b).count());
        System.out.println("part 2: " + input2.stream().map(address -> isListenable(address)).filter(b -> b).count());
    }

    private static boolean isListenable(String address)
    {
        StringTokenizer st = new StringTokenizer(address, "[]", true);
        boolean negativeMode = false;
        List<ABA> aba = new ArrayList<>();
        Set<String> bab = new HashSet<>();
        int offset = 0;
        while (st.hasMoreTokens())
        {
            String t = st.nextToken();
            if ("[".equals(t))
            {
                negativeMode = true;
            }
            else if ("]".equals(t))
            {
                negativeMode = false;
            }
            else
            {
                Set<ABA> aba1 = findABA(t, offset);
                if (negativeMode)
                {

                    bab.addAll(aba1.stream().map(aba2 -> aba2.value).collect(Collectors.toList()));
                }
                else
                {
                    aba.addAll(aba1);
                }
            }
            offset += 1000;
        }
        for (ABA aba1 : aba)
        {
            String bab1 = convertABA(aba1.value);
            if (bab.contains(bab1))
            {
                System.out.println("# " + aba1.value + " " + address);
                return true;
            }
            // for (ABA aba2 : aba)
            // {
            // if (bab1.equals(aba2.value) && Math.abs(aba1.index - aba2.index) > 2)
            // {
            // System.out.println("X " + aba1.value + " " + address);
            // return true;
            // }
            // }
        }
        return false;
    }

    private static final String convertABA(String s)
    {
        return "" + s.charAt(1) + s.charAt(0) + s.charAt(1);
    }

    private static boolean isSnoopable(String address)
    {
        StringTokenizer st = new StringTokenizer(address, "[]", true);
        boolean hasABBA = false;
        boolean negativeMode = false;
        while (st.hasMoreTokens())
        {
            String t = st.nextToken();
            if ("[".equals(t))
            {
                negativeMode = true;
            }
            else if ("]".equals(t))
            {
                negativeMode = false;
            }
            else
            {
                boolean b = containsABBA(t);
                if (b && negativeMode)
                {
                    return false;
                }
                hasABBA |= b;
            }
        }

        return hasABBA;
    }

    private static Set<ABA> findABA(String s, int offset)
    {
        Set<ABA> aba = new HashSet<>();
        for (int i = 2; i < s.length(); i++)
        {
            char a = s.charAt(i - 2);
            char b = s.charAt(i - 1);
            char c = s.charAt(i);

            if ((a != b) && (a == c))
            {
                aba.add(new ABA(s.substring(i - 2, i + 1), i - 2 + offset));
            }
        }
        return aba;
    }

    static class ABA
    {
        final String value;
        final int index;

        ABA(String value, int index)
        {
            this.value = value;
            this.index = index;
        }
    }

    private static boolean containsABBA(String s)
    {
        for (int i = 3; i < s.length(); i++)
        {
            char a = s.charAt(i - 3);
            char b = s.charAt(i - 2);
            char c = s.charAt(i - 1);
            char d = s.charAt(i);

            if ((a != b) && (a == d) && (b == c))
            {
                return true;
            }
        }
        return false;
    }
}
