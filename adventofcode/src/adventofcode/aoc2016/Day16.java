package adventofcode.aoc2016;

public class Day16
{

    public static void main(String[] args)
    {
        String dragon1 = "01111001100111011";
        // System.out.println("test1: " + dragonize("1", 3));
        // System.out.println("test2: " + dragonize("0", 3));
        // System.out.println("test3: " + dragonize("11111", 11));
        // System.out.println("test4: " + dragonize("111100001010", 24));
        //
        // System.out.println("test5: " + checksum("110010110100"));
        // System.out.println("part1: " + checksum(dragonize(dragon1, 272)));
        System.out.println("part2: " + checksum(dragonize(dragon1, 35651584)));
    }

    private static String dragonize(String dragon, int length)
    {
        String a = dragon;
        while (a.length() < length)
        {
            String b = new StringBuilder(a).reverse().toString();
            b = b.replace('0', 'x').replace('1', '0').replace('x', '1');
            a = a + "0" + b;
            System.out.println("& " + a.length());
        }
        return a.substring(0, length);
    }

    private static String checksum(String data)
    {
        System.out.println("% " + data.length());
        String a = data;

        do
        {
            StringBuilder c = new StringBuilder();
            System.out.println("$ " + a.length());
            for (int i = 1; i < a.length(); i += 2)
            {
                if ((i % 10000) == 0)
                    System.out.print(".");
                c.append((a.charAt(i - 1) == a.charAt(i)) ? "1" : "0");
            }
            a = c.toString();

            System.out.println("§ " + a.length());
        }
        while ((a.length() & 1) == 0);
        return a;
    }
}
