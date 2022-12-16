package adventofcode.aoc2016;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adventofcode.util.IOUtil;

public class Day1
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2016/day01.data");
        String[] commands = input.get(0).replace(",", "").split(" ");

        int heading = 0;
        int x = 0;
        int y = 0;

        Set<Integer> visited = new HashSet<>();
        visited.add(0);

        boolean found = false;

        for (String c : commands)
        {
            // change direction
            heading = (heading + (c.charAt(0) == 'L' ? 3 : 1)) & 3;
            // walk blocks
            int blocks = Integer.parseInt(c.substring(1));
            for (int i = 0; i < blocks; i++)
            {
                switch (heading)
                {
                    case 0:
                        y -= 1;
                        break;
                    case 1:
                        x += 1;
                        break;
                    case 2:
                        y += 1;
                        break;
                    case 3:
                        x -= 1;
                        break;
                }
                int location = x * 10000 + y;
                if (!found && visited.contains(location))
                {
                    System.out.println("part2: " + (Math.abs(x) + Math.abs(y)));
                    found = true;
                }
                else
                {
                    visited.add(location);
                }
            }
        }

        System.out.println("part1: " + (Math.abs(x) + Math.abs(y)));
    }
}
