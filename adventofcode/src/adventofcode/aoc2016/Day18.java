package adventofcode.aoc2016;

public class Day18
{

    public static void main(String[] args)
    {
        part1("..^^.", 3);
        part1(".^^.^.^^^^", 10);
        part1(".^..^....^....^^.^^.^.^^.^.....^.^..^...^^^^^^.^^^^.^.^^^^^^^.^^^^^..^.^^^.^^..^.^^.^....^.^...^^.^.",
                40);
        part1(".^..^....^....^^.^^.^.^^.^.....^.^..^...^^^^^^.^^^^.^.^^^^^^^.^^^^^..^.^^^.^^..^.^^.^....^.^...^^.^.",
                400_000);
    }

    private static void part1(String toprow, int rows)
    {
        boolean[][] grid = new boolean[2][toprow.length()];
        int countSafe = 0;
        for (int i = 0; i < toprow.length(); i++)
        {
            grid[0][i] = toprow.charAt(i) == '^';
            if (!grid[0][i])
            {
                countSafe++;
            }
        }

        for (int j = 1; j < rows; j++)
        {
            for (int i = 0; i < toprow.length(); i++)
            {
                boolean[] prevrow = grid[(j - 1) & 1];
                boolean left = (i > 0) && prevrow[i - 1];
                boolean right = (i < toprow.length() - 1) && prevrow[i + 1];
                // System.out.print(left && right ? '#' : left ? '<' : right ? '>' : '.');
                grid[j & 1][i] = left != right;
                // System.out.print(row[i] ? '^' : '.');
                if (!grid[j & 1][i])
                {
                    countSafe++;
                }
            }
            // System.out.println();

        }
        System.out.println("part1: " + countSafe);
    }

}
