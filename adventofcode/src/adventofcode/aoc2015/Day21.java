package adventofcode.aoc2015;

public class Day21
{

    static class Player
    {
        int hitpoints;
        int damage;
        int armor;
    }

    public static void main(String[] args)
    {
        part1();
        part2();
    }

    private static void part1()
    {
        for (int d = 4; d <= 11; d++)
        {
            for (int a = 1; a <= 8; a++)
            {
                if (fight(d, a))
                {
                    System.out.println("## " + d + "  " + a);
                }
            }
        }
        Player p1 = new Player();
        p1.hitpoints = 100;
        p1.damage = 0;
        p1.armor = 0;
        Player p2 = new Player();
        p2.hitpoints = 103;
        p2.damage = 9;
        p2.armor = 2;
    }

    private static void part2()
    {
        for (int d = 4; d <= 11; d++)
        {
            for (int a = 1; a <= 8; a++)
            {
                if (!fight(d, a))
                {
                    System.out.println("## " + d + "  " + a);
                }
            }
        }
        Player p1 = new Player();
        p1.hitpoints = 100;
        p1.damage = 0;
        p1.armor = 0;
        Player p2 = new Player();
        p2.hitpoints = 103;
        p2.damage = 9;
        p2.armor = 2;
    }

    private static boolean fight(int d, int a)
    {
        Player p1 = new Player();
        p1.hitpoints = 100;
        p1.damage = d;
        p1.armor = a;
        Player p2 = new Player();
        p2.hitpoints = 103;
        p2.damage = 9;
        p2.armor = 2;

        while (p1.hitpoints > 0 && p2.hitpoints > 0)
        {
            p2.hitpoints -= Math.max(1, p1.damage - p2.armor);
            if (p2.hitpoints <= 0)
            {
                return true;
            }
            p1.hitpoints -= Math.max(1, p2.damage - p1.armor);
        }
        return false;
    }
}
