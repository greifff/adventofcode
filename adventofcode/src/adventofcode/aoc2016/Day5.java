package adventofcode.aoc2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import adventofcode.util.HexUtil;

public class Day5
{
    public static void main(String[] args)
    {
        System.out.println("test1: " + find("abc", "00000"));
        System.out.println("part1: " + find("cxdnnyjw", "00000"));
        System.out.println("test2: " + find2("abc", "00000"));
        System.out.println("part2: " + find2("cxdnnyjw", "00000"));
    }

    private static String find(String secret, String prefix)
    {
        String pwd = "";
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            int i = 0;
            while (pwd.length() < 8)
            {
                String testString = secret + i;

                byte[] b1 = digest.digest(testString.getBytes());
                String result = HexUtil.toHexString(b1);

                if (result.startsWith(prefix))
                {
                    System.out.println("## " + testString + " " + result);
                    pwd += result.charAt(prefix.length());
                }
                i++;
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return pwd;
    }

    private static String find2(String secret, String prefix)
    {
        char[] pwd = new char[8];
        boolean[] found = new boolean[8];
        int foundChars = 0;
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            int i = 0;
            while (foundChars < 8)
            {
                String testString = secret + i;

                byte[] b1 = digest.digest(testString.getBytes());
                String result = HexUtil.toHexString(b1);

                if (result.startsWith(prefix))
                {
                    int index = Integer.parseInt(result.substring(prefix.length(), prefix.length() + 1), 16);
                    if (index < 8 && !found[index])
                    {
                        System.out.println("## " + testString + " " + result);
                        pwd[index] = result.charAt(prefix.length() + 1);
                        found[index] = true;
                        foundChars++;
                    }
                }
                i++;
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return new String(pwd);
    }
}
