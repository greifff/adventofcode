package adventofcode.aoc2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import adventofcode.util.HexUtil;

public class Day14
{

    public static void main(String[] args)
    {
        part1("abc");
        part1("cuanljph");
        part2("abc");
        part2("cuanljph");
    }

    private static void part1(String salt)
    {
        List<Integer> matchingIndizes = new ArrayList<>();
        List<HashCandidate> candidates = new ArrayList<>();
        int index = 0;
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            while (matchingIndizes.size() < 64)
            {
                byte[] b1 = digest.digest((salt + index).getBytes());
                String result = HexUtil.toHexString(b1);
                String v = isValid(result);
                if (v != null)
                {
                    HashCandidate c1 = new HashCandidate(index, v);
                    Iterator<HashCandidate> c2 = candidates.iterator();
                    // System.out.println("/" + index + " " + result);
                    while (c2.hasNext())
                    {
                        HashCandidate c3 = c2.next();
                        if (c3.index + 1000 < index)
                        {
                            c2.remove(); // expired
                        }
                        else if (result.indexOf(c3.testString) != -1)
                        {
                            c2.remove();
                            matchingIndizes.add(c3.index);
                            // System.out.println("#" + matchingIndizes.size() + " " + c3.index);
                        }
                    }
                    candidates.add(c1);
                }
                index++;
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        System.out.println("part1: " + matchingIndizes.get(63));
    }

    private static void part2(String salt)
    {
        List<Integer> matchingIndizes = new ArrayList<>();
        List<HashCandidate> candidates = new ArrayList<>();
        int index = 0;
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            while (matchingIndizes.size() < 64)
            {
                String result = overhash(digest, salt + index);
                String v = isValid(result);
                if (v != null)
                {
                    HashCandidate c1 = new HashCandidate(index, v);
                    Iterator<HashCandidate> c2 = candidates.iterator();
                    // System.out.println("/" + index + " " + result);
                    while (c2.hasNext())
                    {
                        HashCandidate c3 = c2.next();
                        if (c3.index + 1000 < index)
                        {
                            c2.remove(); // expired
                        }
                        else if (result.indexOf(c3.testString) != -1)
                        {
                            c2.remove();
                            matchingIndizes.add(c3.index);
                            // System.out.println("#" + matchingIndizes.size() + " " + c3.index);
                        }
                    }
                    candidates.add(c1);
                }
                index++;
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        System.out.println("part2: " + matchingIndizes.get(63));
    }

    private static String overhash(MessageDigest md5, String input)
    {
        String t = input;
        for (int i = 0; i < 2017; i++)
        {
            byte[] b1 = md5.digest(t.getBytes());
            t = HexUtil.toHexString(b1);
        }
        return t;
    }

    private static String isValid(String hash)
    {
        for (int i = 2; i < hash.length(); i++)
        {
            char a = hash.charAt(i - 2);
            char b = hash.charAt(i - 1);
            char c = hash.charAt(i);
            if (a == b && a == c)
            {
                return "" + a + a + a + a + a;
            }
        }
        return null;
    }

    static class HashCandidate
    {
        int index;
        String testString;

        HashCandidate(int index, String testString)
        {
            this.index = index;
            this.testString = testString;
        }
    }
}
