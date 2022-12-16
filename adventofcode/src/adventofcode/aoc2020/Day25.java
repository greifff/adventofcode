package adventofcode.aoc2020;

public class Day25
{

    private static final int[] publicKeys = //
    { 15335876, 15086442 };
    // { 5764801, 17807724 }; // test data

    public static void main(String[] args)
    {
        part1();
    }

    private static void part1()
    {
        // TODO Auto-generated method stub
        for (int i = 0; i < 2; i++)
        {
            int loops = determineLoopSize(publicKeys[i]);
            // System.out.println();
            long accessKey = cipher(publicKeys[(i + 1) & 1], loops);

            System.out.println("part1: " + loops + " " + publicKeys[(i + 1) & 1] + " -> " + accessKey);
        }
    }

    private static long cipher(long subjectNumber, int rounds)
    {
        long value1 = 1;
        for (int r = 0; r < rounds; r++)
        {
            value1 = runOneLoop(subjectNumber, value1);
            // System.out.println("** " + value1);
        }
        return value1;
    }

    private static long runOneLoop(long subjectNumber, long value)
    {
        int modulus = 20201227;
        long value1 = value * subjectNumber;
        // System.out.println("## " + value1);
        return value1 % modulus;
    }

    private static int determineLoopSize(int publicKey)
    {
        long value = 1;
        int rounds = 0;
        while (value != publicKey)
        {
            value = runOneLoop(7, value);
            rounds++;
        }
        return rounds;
    }

}
