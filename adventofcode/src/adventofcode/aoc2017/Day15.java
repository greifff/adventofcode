package adventofcode.aoc2017;

public class Day15
{

    public static void main(String[] args)
    {
        int startA1 = 65;
        int startB1 = 8921;

        int startA2 = 116;
        int startB2 = 299;

        int factorA = 16807;
        int factorB = 48271;

        part1(new Generator(startA1, factorA, 1), new Generator(startB1, factorB, 1));
        part1(new Generator(startA2, factorA, 1), new Generator(startB2, factorB, 1));

        part2(new Generator(startA1, factorA, 4), new Generator(startB1, factorB, 8));
        part2(new Generator(startA2, factorA, 4), new Generator(startB2, factorB, 8));
    }

    private static void part1(Generator generatorA, Generator generatorB)
    {
        int matches = 0;
        for (int i = 0; i < 40_000_000; i++)
        {
            long a = generatorA.next();
            long b = generatorB.next();
            if ((a & 0xFFFF) == (b & 0xFFFF))
            {
                matches++;
            }
        }
        System.out.println("part1: " + matches);
    }

    private static void part2(Generator generatorA, Generator generatorB)
    {
        int matches = 0;
        for (int i = 0; i < 5_000_000; i++)
        {
            long a = generatorA.next();
            long b = generatorB.next();
            if ((a & 0xFFFF) == (b & 0xFFFF))
            {
                matches++;
            }
        }
        System.out.println("part1: " + matches);
    }

    static class Generator
    {

        private long value;
        private long factor;
        private long divider;

        Generator(long start, long factor, long divider)
        {
            value = start;
            this.factor = factor;
            this.divider = divider;
        }

        long next()
        {
            do
            {
                value = (value * factor) % 2_147_483_647L;
            }
            while (value % divider != 0);
            return value;
        }
    }

}
