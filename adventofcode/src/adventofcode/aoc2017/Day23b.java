package adventofcode.aoc2017;

public class Day23b
{
    public static void main(String[] args)
    {
        Day23b p = new Day23b();
        p.f9();

        System.out.println("part2: " + p.h);
    }

    int h;

    void f9()
    {
        int b1 = 79 * 100 + 100_000;
        for (int b = b1; b <= b1 + 17000; b += 17)
        {
            boolean f = true;
            for (int d = 2; d < b; d++)
            {
                if (b % d == 0)
                {
                    f = false;
                    break;
                }
            }
            if (!f)
                h++;
        }
    }

}
