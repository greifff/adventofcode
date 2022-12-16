package adventofcode.aoc2016;

import java.util.HashSet;
import java.util.Set;

public class Day11
{

    private static final int[] elements =
    { 1, 0x10, 0x100, 0x1000, 0x1_0000, 0x10_0000, 0x100_0000 };
    private static final int lift = 0x1000_0000;

    // Level 1: A,B,C,D,E & a,b,c,d,e & L -> all zero (44 bit)
    // Level 2: a,b
    private static int configurationPart1 = 0x44;

    // Level 1: A,B,C,D,E & a,b,c,d,e & L
    // Level 2: a,b
    private static int configurationPart2 = 0x44;

    // Level 1: a,b & L
    // Level 2: A
    // Level 3: B
    private static int configurationTest1 = 0x21;

    public static void main(String[] args)
    {
        part1(configurationTest1, 0x3000_00FF, 2);
        part1(configurationPart1, 0x300F_FFFF, 5);
        part1(configurationPart2, 0x3FFF_FFFF, 7);
    }

    private static void part1(int configuration, int targetConfiguration, int elementCount)
    {
        // map configuration -> turn
        Set<Integer> visited = new HashSet<>();
        visited.add(configuration);
        Set<Integer> configurations1 = new HashSet<>();
        configurations1.add(configuration);
        Set<Integer> configurations2 = new HashSet<>();
        int moves = 0;
        while (!configurations1.isEmpty() && !configurations1.contains(targetConfiguration))
        {
            // System.out.println("===========");
            for (int config : configurations1)
            {
                configurations2.addAll(doMoves(config, elementCount));
            }
            configurations2.removeAll(visited);
            configurations1 = configurations2;
            visited.addAll(configurations1);
            configurations2 = new HashSet<>();
            moves++;
            // System.out.println("%%: " + moves + " " + configurations1.size() + " " + configurations1.stream()
            // .map(i -> Integer.toHexString(i)).reduce((a, b) -> a + "," + b).orElse(""));
        }

        System.out.println("part1: " + moves + " " + configurations1.size());
    }

    private static Set<Integer> doMoves(int config, int elementCount)
    {
        int liftLevel = (config & (lift * 3)) / lift;
        Set<Integer> result = new HashSet<>();
        // System.out.println("# " + liftLevel + " " + Integer.toHexString(config));
        if (liftLevel > 0)
        {
            result.addAll(move(config, elementCount, liftLevel, -1));
        }
        if (liftLevel < 3)
        {
            result.addAll(move(config, elementCount, liftLevel, 1));
        }
        return result;
    }

    private static Set<Integer> move(int config, int elementCount, int liftLevel, int direction)
    {

        int rtgsOnThisLevel = rtgsOnLevel(config, liftLevel, elementCount);
        int rtgsOnOtherLevel = rtgsOnLevel(config, liftLevel + direction, elementCount);
        int chipsOnThisLevel = chipsOnLevel(config, liftLevel, elementCount);
        int chipsOnOtherLevel = chipsOnLevel(config, liftLevel + direction, elementCount);

        // System.out.println(
        // "// " + Integer.toBinaryString(chipsOnThisLevel) + " " + Integer.toBinaryString(rtgsOnThisLevel) + " "
        // + Integer.toBinaryString(chipsOnOtherLevel) + " " + Integer.toBinaryString(rtgsOnOtherLevel));
        Set<Integer> result = new HashSet<Integer>();
        // try all variants and test them

        int config1 = config + direction * lift; // move lift

        for (int i = 0; i < elementCount; i++)
        {
            int p1map = 1 << i;

            // move 1 chip
            if ((p1map & chipsOnThisLevel) != 0 && !destroysChip(rtgsOnOtherLevel, chipsOnOtherLevel | p1map))
            {
                int nc = modify(config1, i, direction, false);
                // System.out.println("*1 " + Integer.toHexString(nc));
                result.add(nc);
            }
            // move 1 rtg
            if ((p1map & rtgsOnThisLevel) != 0 && !destroysChip(rtgsOnOtherLevel | p1map, chipsOnOtherLevel))
            {
                int nc = modify(config1, i, direction, true);
                // System.out.println("*2 " + Integer.toHexString(nc));
                result.add(nc);
            }

            for (int j = i; j < elementCount; j++)
            {
                int p2map = 1 << j;
                // move 2 chips
                if (i != j && (p1map & chipsOnThisLevel) != 0 && (p2map & chipsOnThisLevel) != 0
                        && !destroysChip(rtgsOnOtherLevel, chipsOnOtherLevel | p1map | p2map))
                {
                    int nc = modify(modify(config1, i, direction, false), j, direction, false);
                    // System.out.println("*3 " + Integer.toHexString(nc));
                    result.add(nc);
                }
                // move 2 rtgs
                if (i != j && (p1map & rtgsOnThisLevel) != 0 && (p2map & rtgsOnThisLevel) != 0
                        && !destroysChip(rtgsOnOtherLevel | p1map | p2map, chipsOnOtherLevel))
                {
                    int nc = modify(modify(config1, i, direction, true), j, direction, true);
                    // System.out.println("*4 " + Integer.toHexString(nc));
                    result.add(nc);
                }
                // move 1 rtg & 1 chip A
                if ((p1map & chipsOnThisLevel) != 0 && (p2map & rtgsOnThisLevel) != 0
                        && !destroysChip(rtgsOnOtherLevel | p2map, chipsOnOtherLevel | p1map))
                {
                    int nc = modify(modify(config1, i, direction, false), j, direction, true);
                    // System.out.println("*5 " + Integer.toHexString(nc));
                    result.add(nc);
                }
                // move 1 rtg & 1 chip B
                if (i != j && (p2map & chipsOnThisLevel) != 0 && (p1map & rtgsOnThisLevel) != 0
                        && !destroysChip(rtgsOnOtherLevel | p1map, chipsOnOtherLevel | p2map))
                {
                    int nc = modify(modify(config1, i, direction, true), j, direction, false);
                    // System.out.println("*6 " + Integer.toHexString(nc));
                    result.add(nc);
                }
            }

        }
        // System.out.println("## " + result.size() + " "
        // + result.stream().map(i -> Integer.toHexString(i)).reduce((a, b) -> a + "," + b).orElse(""));
        return result;
    }

    private static int modify(int config, int element, int direction, boolean rtg)
    {
        int factor = elements[element] * (rtg ? 1 : 4);
        return config + factor * direction;
    }

    private static int chipsOnLevel(int config, int level, int elementCount)
    {
        int result = 0;
        // System.out.println("% " + Integer.toHexString(config));
        for (int i = 0; i < elementCount; i++)
        {
            int m = elements[i] * 0xC;
            // System.out.println("+ " + Integer.toHexString(m));
            // System.out.println("% " + Integer.toHexString(config & m));
            int onLevel = (config & m) / elements[i] / 4;
            if (onLevel == level)
            {
                result |= (1 << i);
            }
        }
        // System.out.println("- " + Integer.toBinaryString(result));
        // System.exit(0);
        return result;
    }

    private static int rtgsOnLevel(int config, int level, int elementCount)
    {
        int result = 0;
        for (int i = 0; i < elementCount; i++)
        {
            int m = elements[i] * 3;
            int onLevel = (config & m) / elements[i];
            if (onLevel == level)
            {
                result |= (1 << i);
            }
        }
        return result;
    }

    private static boolean destroysChip(int rtgs, int chips)
    {
        int chipsUnmatched = chips - (chips & rtgs);
        // System.out.println("$ " + Integer.toBinaryString(chipsUnmatched) + " " + Integer.toBinaryString(chips) + " "
        // + Integer.toBinaryString(rtgs));
        return chipsUnmatched > 0 && rtgs > 0;
    }

}
