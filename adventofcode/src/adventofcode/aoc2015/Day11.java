package adventofcode.aoc2015;

import java.util.ArrayList;
import java.util.List;

public class Day11
{

    public static void main(String[] args)
    {
        part1("vzbxkghb");
        part1("vzbxxyzz");
    }

    private static void part1(String pwd1)
    {
        String pwd2 = pwd1;
        do
        {
            pwd2 = iteratePassword(pwd2);
            System.out.println("# " + pwd2);
        }
        while (!matchesConstraints(pwd2));
        System.out.println("part1: " + pwd2);
    }

    private static String iteratePassword(String pwd1)
    {
        char[] c = pwd1.toCharArray();

        int i = 7;
        boolean complete = false;

        while (!complete && i >= 0)
        {
            if (c[i] == 'z')
            {
                c[i] = 'a';
                i--;
            }
            else
            {
                complete = true;
                c[i]++;
            }
        }

        return new String(c);
    }

    private static boolean matchesConstraints(String pwd2)
    {
        int forbiddenChar = Math.max(pwd2.indexOf('i'), Math.max(pwd2.indexOf('o'), pwd2.indexOf('l')));
        if (forbiddenChar != -1)
        {
            return false;
        }
        List<Integer> doubleLetters = new ArrayList<>();

        int i = 1;
        while (i < pwd2.length())
        {
            if (pwd2.charAt(i - 1) == pwd2.charAt(i))
            {
                doubleLetters.add(i - 1);
                i++;
            }
            i++;
        }

        if (doubleLetters.size() < 2)
        {
            return false;
        }

        boolean containsStraight = false;
        for (int j = 2; j < pwd2.length(); j++)
        {
            char a = pwd2.charAt(j - 2);
            char b = pwd2.charAt(j - 1);
            char c = pwd2.charAt(j);

            containsStraight |= (b - a == 1) && (c - b == 1);
        }

        return containsStraight;
    }
}
