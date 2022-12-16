package adventofcode.aoc2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class day7
{

    static class BagRule
    {
        String color;
        List<Integer> containedCount = new ArrayList<>();
        List<String> containedColor = new ArrayList<>();

        BagRule(String input)
        {
            String s = input.replace("bags", "").replace("bag", "").replace("contain", "#").replace(',', '#')
                    .replace('.', ' ');
            String[] f = s.split("#");
            color = f[0].trim();
            if (f[1].trim().equals("no other"))
            {
                return;
            }
            for (int i = 1; i < f.length; i++)
            {
                String[] c = f[i].trim().split(" ", 2);
                if (!c[0].equals("no"))
                {
                    int c1 = Integer.parseInt(c[0]);
                    if (c1 > 0)
                    {
                        containedCount.add(c1);

                        containedColor.add(c[1].trim());
                        System.out.println("l38: " + c[1]);
                    }
                }
            }
        }
    }

    public static void main(String[] args)
    {
        List<String> bagrule1 = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("2020/day7.data")), StandardCharsets.UTF_8)))
        {
            String line = reader.readLine();
            while (line != null)
            {
                bagrule1.add(line);
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // part1
        List<BagRule> rules = new ArrayList<>();
        for (String bagrule2 : bagrule1)
        {
            rules.add(new BagRule(bagrule2));
        }

        int resultCount = 0;

        List<String> testColors = new ArrayList<>();
        Set<String> checked = new HashSet<>();

        testColors.add("shiny gold");
        while (!testColors.isEmpty())
        {
            String testColor = testColors.remove(0);

            checked.add(testColor);
            for (BagRule rule1 : rules)
            {
                if (rule1.containedColor.contains(testColor) && !checked.contains(rule1.color))
                {
                    testColors.add(rule1.color);
                    resultCount++;
                }
            }
        }

        System.out.println("part 1: " + resultCount + " " + checked.size());

        // Part 2

        Map<String, BagRule> lookup = new HashMap<>();
        for (BagRule rule3 : rules)
        {
            lookup.put(rule3.color, rule3);
        }
        Map<String, Integer> toPack = new HashMap<>();
        toPack.put("shiny gold", 1);

        resultCount = -1;
        while (!toPack.isEmpty())
        {
            String c3 = toPack.keySet().iterator().next();
            int n3 = toPack.remove(c3);
            resultCount += n3;

            BagRule rule4 = lookup.get(c3);
            for (int i = 0; i < rule4.containedColor.size(); i++)
            {
                String c5 = rule4.containedColor.get(i);
                int n5 = n3 * rule4.containedCount.get(i);
                Integer n6 = toPack.get(c5);
                toPack.put(c5, n5 + (n6 == null ? 0 : n6));
            }
        }

        System.out.println("part 2: " + resultCount);
    }
}
