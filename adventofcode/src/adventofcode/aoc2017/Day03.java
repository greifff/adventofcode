package adventofcode.aoc2017;

public class Day03
{

    public static void main(String[] args)
    {

        int z = 347991;

        // 589<sqrt(x)<590

        int k = 589;

        int k2 = k * k;

        int x = 294;
        int y = 294;

        if (z > k2)
        {
            x++;
        }

        if (z > k2 + k + 1)
        {
            y -= 590;
        }

        x -= z - (k2 + k + 1);

        System.out.println("X " + x + "," + y);

        System.out.println("? " + (Math.abs(x) + Math.abs(y)));

    }

}
