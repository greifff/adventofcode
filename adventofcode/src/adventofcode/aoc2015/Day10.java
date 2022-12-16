package adventofcode.aoc2015;

public class Day10
{

    public static void main(String[] args)
    {
        part1("1", 5);
        part1("1113222113", 40);
        part1("1113222113", 50); // used a lot of computing power
        part2("1113222113", 40);
        part2("1113222113", 50);
    }

    private static void part2(String input, int turns)
    {
        double conwayConstant = 1.30577269;
        System.out.println("part 2: " + input.length() * Math.pow(conwayConstant, turns));

    }

    private static void part1(String input, int turns)
    {

        String current = input;

        for (int i = 0; i < turns; i++)
        {
            String next = "";
            char currentChar = current.charAt(0);
            int currentCount = 1;

            for (int j = 1; j < current.length(); j++)
            {
                char c = current.charAt(j);
                if (c == currentChar)
                {
                    currentCount++;
                }
                else
                {
                    next += "" + currentCount + currentChar;
                    currentCount = 1;
                    currentChar = c;
                }
            }
            current = next + currentCount + currentChar;
            System.out.println("# " + i + " " + current.length());
        }

        System.out.println("part1: " + current.length());
    }
}
