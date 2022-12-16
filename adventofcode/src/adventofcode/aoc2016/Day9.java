package adventofcode.aoc2016;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day9
{
    public static void main(String[] args)
    {
        List<String> input2 = IOUtil.readFile("2016/day09.data");

        part1("ADVENT");
        part1("A(1x5)BC");
        part1("(3x3)XYZ");
        part1("A(2x2)BCD(2x2)EFG");
        part1("(6x1)(1x3)A");
        part1("X(8x2)(3x3)ABCY");

        part1(input2.get(0));

        part2("ADVENT");
        part2("(3x3)XYZ");
        part2("X(8x2)(3x3)ABCY");
        part2("(27x12)(20x12)(13x14)(7x10)(1x12)A");
        part2("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN");
        part2(input2.get(0));
    }

    private static void part2(String in)
    {
        // int length = 0;
        // String out = in;
        // while (length < out.length())
        // {
        // length = out.length();
        // out = decompress(out);
        // System.out.println("- " + out.length());
        // }

        System.out.println("part2: " + decompress2(in));// + " " + out);
    }

    private static void part1(String in)
    {
        // TODO Auto-generated method stub
        String out = decompress(in);
        out = out.replace(" ", "");

        System.out.println("part1: " + out.length());// + " " + out);
    }

    private static String decompress(String in)
    {
        String result = "";
        int startIndex = 0;

        while (startIndex < in.length())
        {
            int openBracket = in.indexOf("(", startIndex);
            if (openBracket == -1)
            {
                result += in.substring(startIndex);
                break;
            }
            if (openBracket > startIndex)
            {
                result += in.substring(startIndex, openBracket);
            }
            int closeBracket = in.indexOf(")", openBracket);

            String[] c = in.substring(openBracket + 1, closeBracket).split("x");

            int seqlength = Integer.parseInt(c[0]);
            int repeats = Integer.parseInt(c[1]);

            String seq = in.substring(closeBracket + 1, closeBracket + 1 + seqlength);
            for (int i = 0; i < repeats; i++)
            {
                result += seq;
            }
            startIndex = closeBracket + 1 + seqlength;
        }
        return result;
    }

    private static long decompress2(String in)
    {
        long result = 0;
        String in1 = in;
        while (in1.length() > 0)
        {
            int openBracket = in1.indexOf("(");
            if (openBracket == -1)
            {
                result += in1.length();
                break;
            }
            else if (openBracket > 0)
            {
                result += openBracket;
                in1 = in1.substring(openBracket);
            }
            else
            {
                int closeBracket = in1.indexOf(")", openBracket);

                String[] c = in1.substring(openBracket + 1, closeBracket).split("x");

                int seqlength = Integer.parseInt(c[0]);
                int repeats = Integer.parseInt(c[1]);

                String seq = in1.substring(closeBracket + 1, closeBracket + 1 + seqlength);
                long seqlength2 = decompress2(seq);

                result += seqlength2 * repeats;

                in1 = in1.substring(closeBracket + 1 + seqlength);
            }
        }
        return result;
    }

}
