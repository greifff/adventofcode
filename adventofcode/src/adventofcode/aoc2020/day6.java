package adventofcode.aoc2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class day6
{

    public static void main(String[] args)
    {
        List<String> customs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("2020/day6.data")), StandardCharsets.UTF_8)))
        {
            String line = reader.readLine();
            while (line != null)
            {
                customs.add(line);
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // part1

        int sum = 0;

        int i = 0;
        while (i < customs.size())
        {
            String entry = customs.get(i);
            Set<Integer> checked = new HashSet<>();
            while (!"".equals(entry))
            {
                entry.chars().forEach(c -> checked.add(c));
                i++;
                entry = i < customs.size() ? customs.get(i) : "";
            }
            sum += checked.size();
            i++;
        }

        System.out.println("part1: " + sum);

        // part2

        sum = 0;

        i = 0;
        while (i < customs.size())
        {
            String entry = customs.get(i);
            Set<Integer> checked = new HashSet<>();
            for (char c = 'a'; c <= 'z'; c++)
            {
                checked.add((int) c);
            }
            while (!"".equals(entry))
            {
                Set<Integer> found = new HashSet<>();
                entry.chars().forEach(c -> found.add(c));
                checked.retainAll(found);
                i++;
                entry = i < customs.size() ? customs.get(i) : "";
            }
            sum += checked.size();
            i++;
        }

        System.out.println("part2: " + sum);
    }
}
