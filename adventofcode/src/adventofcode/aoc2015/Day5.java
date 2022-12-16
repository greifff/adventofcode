package adventofcode.aoc2015;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adventofcode.util.IOUtil;

public class Day5
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day05.data");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input)
    {
        int nice = 0;

        for (String s : input)
        {
            if (isNice(s))
            {
                nice++;
            }
        }

        System.out.println("part1: " + nice);
    }

    private static void part2(List<String> input)
    {
        int nice = 0;

        for (String s : input)
        {
            if (isNice2(s))
            {
                nice++;
            }
        }

        System.out.println("part2: " + nice);
    }

    private static final Set<String> naughty = new HashSet<>(Arrays.asList("ab", "cd", "pq", "xy"));
    private static final Set<String> vowel = new HashSet<>(Arrays.asList("a", "e", "i", "o", "u"));

    private static boolean isNice(String s)
    {
        int vowels = 0;
        boolean hasDoubleLetter = false;

        if (vowel.contains(s.substring(0, 1)))
        {
            vowels++;
        }

        for (int i = 1; i < s.length(); i++)
        {
            if (vowel.contains(s.substring(i, i + 1)))
            {
                vowels++;
            }
            if (naughty.contains(s.substring(i - 1, i + 1)))
            {
                return false;
            }
            hasDoubleLetter |= s.charAt(i - 1) == s.charAt(i);
        }

        return hasDoubleLetter && vowels >= 3;
    }

    private static boolean isNice2(String s)
    {

        boolean hasRepeatingPair = false;

        for (int i = 1; i < s.length() - 2; i++)
        {
            String c = s.substring(i - 1, i + 1);
            int k = s.indexOf(c, i + 1);
            if (k != -1)
            {
                hasRepeatingPair = true;
                break;
            }
        }

        boolean hasRepeatingLetter = false;

        for (int i = 2; i < s.length(); i++)
        {
            hasRepeatingLetter |= (s.charAt(i - 2) == s.charAt(i));
        }

        return hasRepeatingPair && hasRepeatingLetter;
    }

}
