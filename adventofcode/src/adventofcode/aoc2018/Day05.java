package adventofcode.aoc2018;

import adventofcode.util.IOUtil;

public class Day05
{

    public static void main(String[] args)
    {
        String test1 = "dabAcCaCBAcCcaDA";
        String input1 = IOUtil.readFile("2018/day05.data").get(0);

        part1(test1);
        part1(input1);
        part2(test1);
        part2(input1);
    }

    private static void part1(String s)
    {
        System.out.println("part1: " + react(s));
    }

    private static void part2(String s)
    {
        int l = Integer.MAX_VALUE;
        for (char c = 'A'; c <= 'Z'; c++)
        {
            String u = s.replace("" + c, "").replace(("" + c).toLowerCase(), "");
            l = Math.min(l, react(u).length());
        }
        System.out.println("part2: " + l);
    }

    private static String react(String s)
    {
        String t = s;
        final int d = 'a' - 'A';
        int l1 = t.length() + 1;
        while (t.length() < l1)
        {
            l1 = t.length();

            int i = 1;
            while (i < t.length())
            {
                char c1 = t.charAt(i - 1);
                char c2 = t.charAt(i);
                if ((c1 >= 'a' && c1 <= 'z' && c1 - d == c2) || (c1 >= 'A' && c1 <= 'Z' && c1 + d == c2))
                {
                    t = (i > 1 ? t.substring(0, i - 1) : "") + (i + 1 < t.length() ? t.substring(i + 1) : "");
                }
                i++;
            }
        }
        return t;
    }
}
