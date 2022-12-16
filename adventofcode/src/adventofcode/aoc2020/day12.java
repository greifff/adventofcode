package adventofcode.aoc2020;

import java.util.List;

import adventofcode.util.IOUtil;

public class day12
{

    private static final String directions = "NESW"; // turning right

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2020/day12.data");
        part1(input);
        part2(input);
    }

    private static void part2(List<String> input)
    {
        int x = 0;
        int y = 0;
        int xw = 10;
        int yw = -1;

        for (String command : input)
        {
            char order = command.charAt(0);
            int value = Integer.parseInt(command.substring(1));
            switch (order)
            {
                case 'N':
                    yw -= value;
                    break;
                case 'S':
                    yw += value;
                    break;
                case 'E':
                    xw += value;
                    break;
                case 'W':
                    xw -= value;
                    break;
                case 'L':
                case 'R':
                    int h;
                    switch (command)
                    {
                        case "L270":
                        case "R90":
                            h = xw;
                            xw = -yw;
                            yw = h;
                            break;
                        case "L180":
                        case "R180":
                            xw = -xw;
                            yw = -yw;
                            break;
                        case "L90":
                        case "R270":
                            h = xw;
                            xw = yw;
                            yw = -h;
                            break;
                    }
                    break;
                case 'F':
                    x += value * xw;
                    y += value * yw;
                    break;
            }
        }

        System.out.println("part2: " + (Math.abs(x) + Math.abs(y)));
    }

    private static void part1(List<String> input)
    {
        int x = 0;
        int y = 0;
        char heading = 'E';

        for (String command : input)
        {
            char order = command.charAt(0);
            int value = Integer.parseInt(command.substring(1));
            switch (order)
            {
                case 'N':
                    y -= value;
                    break;
                case 'S':
                    y += value;
                    break;
                case 'E':
                    x += value;
                    break;
                case 'W':
                    x -= value;
                    break;
                case 'L':
                case 'R':
                    heading = turn(heading, command);
                    break;
                case 'F':
                    switch (heading)
                    {
                        case 'N':
                            y -= value;
                            break;
                        case 'S':
                            y += value;
                            break;
                        case 'E':
                            x += value;
                            break;
                        case 'W':
                            x -= value;
                            break;
                    }
                    break;
            }
        }

        System.out.println("part1: " + (Math.abs(x) + Math.abs(y)));
    }

    private static char turn(char heading, String command)
    {
        int turn = command.charAt(0) == 'L' ? -1 : 1;
        turn *= Integer.parseInt(command.substring(1)) / 90;

        char newHeading = directions.charAt((directions.indexOf(heading) + turn) & 3);

        return newHeading;
    }
}
