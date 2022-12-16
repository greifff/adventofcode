package adventofcode.aoc2017;

import adventofcode.util.IOUtil;

public class Day16
{

    public static void main(String[] args)
    {
        String[] input1 = IOUtil.readFile("2017/day16.test").get(0).split(",");
        String[] input2 = IOUtil.readFile("2017/day16.data").get(0).split(",");

        part1(input1, "abcde");
        part1(input2, "abcdefghijklmnop");

        // part2(input1, "abcde");
        part2(input2, "abcdefghijklmnop");

    }

    private static void part1(String[] input, String programs)
    {
        for (String in : input)
        {
            programs = process(programs, in);
            // System.out.println("# " + programs + " " + in);
        }
        System.out.println("part1: " + programs);
    }

    private static void part2(String[] input, String programs1)
    {
        String programs = programs1;
        long cycleLength = 0;
        for (long i = 0; i < 1_000L; i++)
        {
            for (String in : input)
            {
                programs = process(programs, in);
            }
            System.out.println("# cycle: " + i + " " + programs);
            if (programs.equals(programs1))
            {
                cycleLength = i + 1;
                break;
            }
        }

        long necessaryCycles = 1_000_000_000L % cycleLength;
        programs = programs1;
        for (long i = 0; i < necessaryCycles; i++)
        {
            for (String in : input)
            {
                programs = process(programs, in);
            }
        }
        System.out.println("part2: " + programs);
    }

    private static String process(String programs, String op)
    {
        switch (op.charAt(0))
        {
            case 's':
                return spin(programs, Integer.parseInt(op.substring(1)));
            case 'x':
                int sep = op.indexOf('/');
                return exchange(programs, Integer.parseInt(op.substring(1, sep)),
                        Integer.parseInt(op.substring(sep + 1)));
            case 'p':
                return exchange(programs, programs.indexOf(op.charAt(1)), programs.indexOf(op.charAt(3)));
        }
        return programs;
    }

    private static String exchange(String programs, int a, int b)
    {
        // System.out.println("?x " + a + " " + b);
        int i = Math.min(a, b);
        int j = Math.max(a, b);
        return programs.substring(0, i) + programs.charAt(j) + programs.substring(i + 1, j) + programs.charAt(i)
                + programs.substring(j + 1);
    }

    private static String spin(String programs, int spin)
    {
        // System.out.println("?s " + spin);
        return programs.substring(programs.length() - spin) + programs.substring(0, programs.length() - spin);
    }
}
