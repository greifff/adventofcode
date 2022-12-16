package adventofcode.aoc2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import adventofcode.util.HexUtil;

public class Day4
{
    public static void main(String[] args)
    {
        System.out.println("part1: " + find("yzbqklnj", "00000"));
        System.out.println("part1: " + find("yzbqklnj", "000000"));
    }

    private static int find(String secret, String prefix)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            int i = 1;
            while (true)
            {
                String testString = secret + i;

                byte[] b1 = digest.digest(testString.getBytes());
                String result = HexUtil.toHexString(b1);

                if (result.startsWith(prefix))
                {
                    return i;
                }
                i++;
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}
