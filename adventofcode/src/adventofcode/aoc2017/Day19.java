package adventofcode.aoc2017;

import java.util.List;

import adventofcode.util.IOUtil;

public class Day19
{
    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2017/day19.test");
        List<String> input2 = IOUtil.readFile("2017/day19.data");

        part1(input1);
        part1(input2);
    }

    static void part1(List<String> input)
    {
        String result = "";
        int steps = 0;
        int y = 0;
        int x = input.get(0).indexOf('|');

        int direction = 2;

        outer: while (x >= 0 && x < input.get(0).length() && y >= 0 && y < input.size())
        {
            switch (input.get(y).charAt(x))
            {
                default:
                    result += input.get(y).charAt(x);
                case '-':
                case '|': // continue
                    switch (direction)
                    {
                        case 0:
                            y--;
                            break;
                        case 1:
                            x++;
                            break;
                        case 2:
                            y++;
                            break;
                        case 3:
                            x--;
                            break;
                    }
                    break;
                case '+': // change direction (if necessary)
                    if (direction == 2 || direction == 0)
                    {
                        if (x + 1 < input.get(0).length() && input.get(y).charAt(x + 1) != ' ')
                        {
                            direction = 1;
                            x++;
                        }
                        else
                        {
                            direction = 3;
                            x--;
                        }
                    }
                    else
                    {
                        if (y + 1 < input.size() && input.get(y + 1).charAt(x) != ' ')
                        {
                            direction = 2;
                            y++;
                        }
                        else
                        {
                            direction = 0;
                            y--;
                        }
                    }
                    break;
                case ' ':
                    // terminate
                    break outer;
            }
            steps++;
        }

        System.out.println("part1: " + result);
        System.out.println("part2: " + steps);
    }
}
