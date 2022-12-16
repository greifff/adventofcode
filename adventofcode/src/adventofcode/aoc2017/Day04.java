package adventofcode.aoc2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import adventofcode.util.IOUtil;

public class Day04
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2017/day04.data");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input)
    {
        int c = 0;

        outer: for (String line : input)
        {

            List<String> words = new ArrayList<>(Arrays.asList(line.split(" ")));

            while (!words.isEmpty())
            {
                String w = words.remove(0);
                if (words.contains(w))
                {
                    continue outer;
                }
            }
            c++;
        }

        System.out.println("part1: " + c);
    }

    private static void part2(List<String> input)
    {
        int c = 0;

        outer: for (String line : input)
        {

            List<String> words = new ArrayList<>(Arrays.asList(line.split(" ")));

            for (int i = 0; i < words.size(); i++)
            {
                String w = words.get(i);
                List<String> ch = new ArrayList<>();
                for (int j = 0; j < w.length(); j++)
                {
                    ch.add(w.substring(j, j + 1));
                }
                Collections.sort(ch);
                w = String.join("", ch);
                words.set(i, w);
            }

            while (!words.isEmpty())
            {
                String w = words.remove(0);
                if (words.contains(w))
                {
                    continue outer;
                }
            }
            c++;
        }

        System.out.println("part2: " + c);
    }

}
