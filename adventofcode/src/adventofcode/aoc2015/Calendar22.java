package adventofcode.aoc2015;

public class Calendar22
{
    static int starthp = 50;
    static int startmana = 500;
    static int bosshitpoints = 71;
    static int bossdamage = 10;
    static int minmana = 65535;
    static int[] duration =
    { 0, 0, 6, 6, 5 };
    static int[] cost =
    { 53, 73, 113, 173, 229 };
    static int[] effect =
    { 4, 2, 7, 3, 101 };

    public static void main(String[] args)
    {
        fight(starthp, startmana, bosshitpoints, 0, 0, 0, 0, 0, false);
        System.out.println("minmana for win " + minmana);

        // b:
        minmana = 65535;
        System.out.println();
        fight(starthp, startmana, bosshitpoints, 0, 0, 0, 0, 0, true);
        System.out.println("minmana for win " + minmana);

    }

    static void usespell(int hp, int mp, int bhp, int armor, int shield, int poison, int regen, int mpused,
            int spellnum, boolean b)
    {
        if (mpused > minmana)
            return;
        if (spellnum == 0)
            bhp -= 4;
        if (spellnum == 1)
        {
            bhp -= 2;
            hp += 2;
        }
        if (spellnum == 2)
        {
            shield = duration[spellnum];
            armor = effect[spellnum];
        }
        if (spellnum == 3)
            poison = duration[spellnum];
        if (spellnum == 4)
            regen = duration[spellnum];
        // boss turn
        shield--;
        if (poison > 0)
        {
            bhp -= effect[3];
            poison--;
        }
        if (regen > 0)
        {
            regen--;
            mp += effect[4];
        }
        if (bhp <= 0 && mpused < minmana) // win on boss turn: new damage + poison tick
        {
            minmana = mpused;
            System.out.println("win with hp: " + hp + " mp " + mp + " used mana " + mpused);
            return;
        }
        hp -= bossdamage - armor;
        if (hp > 0)
            fight(hp, mp, bhp, armor, shield, poison, regen, mpused, b);

    }

    static void fight(int hp, int mp, int bhp, int armor, int shield, int poison, int regen, int mpused, boolean b)
    {
        if (b)
            hp--;
        if (hp <= 0)
            return;

        shield--;
        if (shield == 0)
            armor = 0;
        if (poison > 0)
        {
            bhp -= effect[3];
            poison--;
        }
        if (regen > 0)
        {
            mp += effect[4];
            regen--;
        }
        if (bhp <= 0 && mpused < minmana) // win on player turn (poison tick)
        {
            minmana = mpused;
            return;
        }
        for (int i = 0; i < 5; i++) // branch to all spells
        {
            if ((i == 2 && shield > 0) || (i == 3 && poison > 0) || (i == 4 && regen > 0)) // not active
                continue;
            if (mp < cost[i])
                continue;
            usespell(hp, mp - cost[i], bhp, armor, shield, poison, regen, mpused + cost[i], i, b);
        }

    }

}
