package adventofcode.aoc2015;

public class Day25
{

    public static void main(String[] args)
    {
        part1();
    }

    private static void part1()
    {
        // TODO Auto-generated method stub
        int row = 1;
        int column = 1;
        long code = 20151125L;

        while (row < 2978 || column < 3083)
        {
            code = nextCode(code);
            if (row == 1)
            {
                row = column + 1;
                column = 1;
            }
            else
            {
                row--;
                column++;
            }
            // System.out.println("# (" + row + "," + column + ") : " + code);
        }

        System.out.println("part1: (" + row + "," + column + ") : " + code);
    }

    private static long nextCode(long previousCode)
    {
        return (previousCode * 252533L) % 33554393;
    }
}
