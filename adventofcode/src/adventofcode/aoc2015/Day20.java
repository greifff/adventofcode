package adventofcode.aoc2015;

public class Day20
{

    public static void main(String[] args)
    {
        part1();
        part2();
    }

    private static void part1()
    {

        for (int k = 260_000; k < 900_000; k++)
        {
            int teilersumme = 1 + k;
            for (int t = 2; t < k; t++)
            {
                if (k % t == 0)
                {
                    teilersumme += t;
                }
            }
            // System.out.println("## " + k + " " + teilersumme);
            if (teilersumme >= 3_600_000)
            {
                System.out.println("part1: " + k + " " + teilersumme);
            }
        }

    }

    private static void part2()
    {

        for (int k = 850_000; k < 1_800_000; k++)
        {
            int teilersumme = k;
            for (int t = k / 50; t < k; t++)
            {
                if (k % t == 0)
                {
                    if (k <= t * 50)
                    {
                        teilersumme += t;
                    }
                }
            }

            // System.out.println("## " + k + " " + teilersumme);
            if (teilersumme * 11 >= 36_000_000)
            {
                System.out.println("part1: " + k + " " + teilersumme);
            }
        }

    }
}
