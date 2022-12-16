package adventofcode.aoc2016;

public class Day15
{

    public static void main(String[] args)
    {
        int[][] discs1 =
        {
                { 1, 17, 15 },
                { 2, 3, 2 },
                { 3, 19, 4 },
                { 4, 13, 2 },
                { 5, 7, 2 },
                { 6, 5, 0 } };

        int[][] discs2 =
        {
                { 1, 17, 15 },
                { 2, 3, 2 },
                { 3, 19, 4 },
                { 4, 13, 2 },
                { 5, 7, 2 },
                { 6, 5, 0 },
                { 7, 11, 0 } };

        int[][] testDiscs =
        {
                { 1, 5, 4 },
                { 2, 2, 1 } };

        part1(testDiscs);
        part1(discs1);
        part1(discs2);
    }

    private static void part1(int[][] discs)
    {
        // TODO Auto-generated method stub
        int k = -1;
        int result = -1;
        while (result != 0)
        {
            k++;
            result = 0;
            for (int[] disc : discs)
            {
                result += (disc[0] + disc[2] + k) % disc[1];
            }

        }

        System.out.println("part1: " + k);
    }

}
