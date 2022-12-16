package adventofcode.aoc2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day21
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2017/day21.data");
        Map<String, String> rules = parseRules(input);

        part1(rules);
        part2(rules);
    }

    static final List<String> initialGrid = Arrays.asList(".#.", "..#", "###");

    private static Map<String, String> parseRules(List<String> input)
    {
        Map<String, String> rules = new HashMap<>();
        for (String in : input)
        {
            String[] x = in.replace(" => ", " ").split(" ");
            rules.put(x[0], x[1]);
            // System.out.println("! " + x[0]);
            if (x[0].length() == 5)
            {
                char a = x[0].charAt(0), b = x[0].charAt(1), c = x[0].charAt(3), d = x[0].charAt(4);
                rules.put("" + c + a + "/" + d + b, x[1]);
                rules.put("" + d + c + "/" + b + a, x[1]);
                rules.put("" + b + d + "/" + a + c, x[1]);
            }
            else
            {
                char a = x[0].charAt(0), b = x[0].charAt(1), c = x[0].charAt(2), d = x[0].charAt(4), e = x[0].charAt(5),
                        f = x[0].charAt(6), g = x[0].charAt(8), h = x[0].charAt(9), i = x[0].charAt(10);
                rules.put("" + g + d + a + "/" + h + e + b + "/" + i + f + c, x[1]);
                rules.put("" + i + h + g + "/" + f + e + d + "/" + c + b + a, x[1]);
                rules.put("" + c + f + i + "/" + b + e + h + "/" + a + d + g, x[1]);
                rules.put("" + c + b + a + "/" + f + e + d + "/" + i + h + g, x[1]);
                rules.put("" + a + d + g + "/" + b + e + h + "/" + c + f + i, x[1]);
                rules.put("" + g + h + i + "/" + d + e + f + "/" + a + b + c, x[1]);
                rules.put("" + i + f + c + "/" + h + e + b + "/" + g + d + a, x[1]);
            }
        }
        return rules;
    }

    private static void part1(Map<String, String> rules)
    {
        List<String> grid = initialGrid;

        for (int i = 0; i < 5; i++)
        {
            if (grid.size() % 2 == 0)
            {
                grid = transform2(grid, rules);
            }
            else
            {
                grid = transform3(grid, rules);
            }
        }
        System.out.println("part1: " + countActive(grid));
    }

    private static void part2(Map<String, String> rules)
    {
        List<String> grid = initialGrid;

        for (int i = 0; i < 18; i++)
        {
            if (grid.size() % 2 == 0)
            {
                grid = transform2(grid, rules);
            }
            else
            {
                grid = transform3(grid, rules);
            }
        }
        System.out.println("part2: " + countActive(grid));
    }

    private static int countActive(List<String> grid)
    {
        int a = 0;
        for (String l : grid)
        {
            a += l.replace(".", "").length();
        }
        return a;
    }

    private static List<String> transform2(List<String> grid1, Map<String, String> rules)
    {
        int width = grid1.size();
        List<String> grid2 = new ArrayList<>();
        for (int i = 1; i < width; i += 2)
        {
            String line1a = grid1.get(i - 1);
            String line1b = grid1.get(i);
            String line2a = "";
            String line2b = "";
            String line2c = "";
            for (int j = 1; j < width; j += 2)
            {
                String square1 = line1a.substring(j - 1, j + 1) + "/" + line1b.substring(j - 1, j + 1);
                String square2 = rules.get(square1);
                String[] f = square2.split("/");
                line2a += f[0];
                line2b += f[1];
                line2c += f[2];
            }

            grid2.add(line2a);
            grid2.add(line2b);
            grid2.add(line2c);
        }
        return grid2;
    }

    private static List<String> transform3(List<String> grid1, Map<String, String> rules)
    {
        int width = grid1.size();
        List<String> grid2 = new ArrayList<>();

        for (int i = 2; i < width; i += 3)
        {
            String line1a = grid1.get(i - 2);
            String line1b = grid1.get(i - 1);
            String line1c = grid1.get(i);
            String line2a = "";
            String line2b = "";
            String line2c = "";
            String line2d = "";
            for (int j = 2; j < width; j += 3)
            {
                String square1 = line1a.substring(j - 2, j + 1) + "/" + line1b.substring(j - 2, j + 1) + "/"
                        + line1c.substring(j - 2, j + 1);
                String square2 = rules.get(square1);
                String[] f = square2.split("/");
                line2a += f[0];
                line2b += f[1];
                line2c += f[2];
                line2d += f[3];
            }

            grid2.add(line2a);
            grid2.add(line2b);
            grid2.add(line2c);
            grid2.add(line2d);
        }
        return grid2;
    }

}
