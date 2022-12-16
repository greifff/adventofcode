package adventofcode.aoc2016;

import java.util.Collections;
import java.util.List;

import adventofcode.util.IOUtil;

public class Day21
{

    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2016/day21.test");
        List<String> input2 = IOUtil.readFile("2016/day21.data");

        part1("abcde", input1);
        part1("abcdefgh", input2);

        part2("decab", input1);
        part2("fbgdceah", input2);
    }

    private static void part1(String string, List<String> input)
    {
        String c = string;
        for (String cmd : input)
        {
            // System.out.println("? " + cmd);
            String[] f = cmd.split(" ");
            switch (f[0])
            {
                case "swap":
                    if ("position".equals(f[1]))
                    {
                        c = swap(c, Integer.parseInt(f[2]), Integer.parseInt(f[5]));
                    }
                    else
                    {
                        c = swap(c, f[2], f[5]);
                    }
                    break;
                case "rotate":
                    switch (f[1])
                    {
                        case "left":
                            c = rotate(c, Integer.parseInt(f[2]));
                            break;
                        case "right":
                            c = rotate(c, -Integer.parseInt(f[2]));
                            break;
                        default:
                            c = rotateByIndexOf(c, f[6]);
                            break;
                    }
                    break;
                case "reverse":
                    c = reverse(c, Integer.parseInt(f[2]), Integer.parseInt(f[4]));
                    break;
                case "move":
                    c = move(c, Integer.parseInt(f[2]), Integer.parseInt(f[5]));
                    break;
            }
            // System.out.println("= " + c);
        }
        System.out.println("part1: " + c);

    }

    private static void part2(String string, List<String> input)
    {
        Collections.reverse(input);
        String c = string;
        for (String cmd : input)
        {
            System.out.println("? " + cmd);
            String[] f = cmd.split(" ");
            switch (f[0])
            {
                case "swap":
                    if ("position".equals(f[1]))
                    {
                        c = swap(c, Integer.parseInt(f[5]), Integer.parseInt(f[2]));
                    }
                    else
                    {
                        c = swap(c, f[5], f[2]);
                    }
                    break;
                case "rotate":
                    switch (f[1])
                    {
                        case "left":
                            c = rotate(c, -Integer.parseInt(f[2]));
                            break;
                        case "right":
                            c = rotate(c, Integer.parseInt(f[2]));
                            break;
                        default:
                            c = unrotateByIndexOf(c, f[6]);
                            break;
                    }
                    break;
                case "reverse":
                    c = reverse(c, Integer.parseInt(f[2]), Integer.parseInt(f[4]));
                    break;
                case "move":
                    c = move(c, Integer.parseInt(f[5]), Integer.parseInt(f[2]));
                    break;
            }
            System.out.println("= " + c);
        }
        System.out.println("part2: " + c);

    }

    public static String rotate(String in, int rotate)
    {
        int rotate1 = rotate;
        while (rotate1 < 0)
        {
            rotate1 += in.length();
        }
        rotate1 = rotate1 % in.length();

        // System.out.println("* " + rotate + " " + rotate1);
        return in.substring(rotate1) + in.substring(0, rotate1);
    }

    public static String swap(String in, int a, int b)
    {
        int a1 = Math.min(a, b);
        int b1 = Math.max(a, b);
        return in.substring(0, a1) + in.charAt(b1) + in.substring(a1 + 1, b1) + in.charAt(a1) + in.substring(b1 + 1);
    }

    public static String swap(String in, String a, String b)
    {
        int a1 = in.indexOf(a);
        int b1 = in.indexOf(b);
        return swap(in, a1, b1);
    }

    public static String rotateByIndexOf(String in, String a)
    {
        int i = in.indexOf(a);
        if (i >= 4)
        {
            i++;
        }
        return rotate(in, -i - 1);
    }

    public static String unrotateByIndexOf(String in, String a)
    {
        int i = in.indexOf(a);
        int rotation = 0;
        while (rotation == 0)
        {
            if (i == 0)
            {
                // Sonderfall
                rotation = 1;
            }
            if (i <= 7 && (i & 1) == 1)
            {
                rotation = (i + 1) / 2;
            }
            else if (i >= 10 && (i & 1) == 0)
            {
                rotation = i / 2 + 1;
            }
            i += in.length();
        }
        System.out.println("+ " + rotation);
        return rotate(in, rotation);
    }

    public static String reverse(String in, int x, int y)
    {
        return in.substring(0, x) //
                + new StringBuilder(in.substring(x, y + 1)).reverse().toString() //
                + in.substring(y + 1);
    }

    public static String move(String in, int x, int y)
    {
        String t = in.substring(0, x) + in.substring(x + 1);
        return t.substring(0, y) + in.charAt(x) + t.substring(y);
    }
}
