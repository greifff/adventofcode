package adventofcode.aoc2018;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day12
{
    public static void main(String[] args)
    {
        List<String> test1 = IOUtil.readFile("2018/day12.test");
        List<String> input = IOUtil.readFile("2018/day12.data");

        Set<Integer> initialTest = readConfig(test1);
        Set<String> rulesTest = readRules(test1);

        Set<Integer> initial = readConfig(input);
        Set<String> rules = readRules(input);

        part1(initialTest, rulesTest);
        part1(initial, rules);

        part2(initial, rules);

        part2b();
    }

    private static void part1(Set<Integer> initial, Set<String> rules)
    {
        Set<Integer> pots = initial;

        for (int i = 0; i < 20; i++)
        {
            int min = pots.stream().reduce(Math::min).orElse(0) - 2;
            int max = pots.stream().reduce(Math::max).orElse(0) + 2;
            Set<Integer> pots2 = new HashSet<>();
            for (int k = min; k <= max; k++)
            {
                String s = "";
                for (int p = k - 2; p <= k + 2; p++)
                {
                    s += pots.contains(p) ? "#" : ".";
                }
                if (rules.contains(s))
                {
                    pots2.add(k);
                }
            }
            pots = pots2;
        }

        int value = pots.stream().reduce((a, b) -> a + b).orElse(0);
        System.out.println("part1: " + value);
    }

    private static void part2(Set<Integer> initial, Set<String> rules)
    {
        Set<Long> pots = initial.stream().map(i -> (long) i).collect(Collectors.toSet());

        long previous = 0;
        for (long i = 0; i < 1000L; i++) // 50_000_000_000L
        {
            long min = pots.stream().reduce(Math::min).orElse(0L) - 2;
            long max = pots.stream().reduce(Math::max).orElse(0L) + 2;
            Set<Long> pots2 = new HashSet<>();
            for (long k = min; k <= max; k++)
            {
                String s = "";
                for (long p = k - 2; p <= k + 2; p++)
                {
                    s += pots.contains(p) ? "#" : ".";
                }
                if (rules.contains(s))
                {
                    pots2.add(k);
                }
            }
            pots = pots2;
            long value = pots.stream().reduce((a, b) -> a + b).orElse(0L);
            long delta = value - previous;
            System.out.println("# " + (i + 1) + " " + value + " " + delta);
            previous = value;
        }

        // long value = pots.stream().reduce((a, b) -> a + b).orElse(0L);
        // System.out.println("part2: " + value);
    }

    private static void part2b()
    {
        // #154: 8628, any other adds 53
        long active = 8628;

        long remainingTurns = 50_000_000_000L - 154;

        long result = active + remainingTurns * 53;

        System.out.println("part2: " + result);
    }

    private static Set<String> readRules(List<String> input)
    {
        Set<String> rules = new HashSet<>();

        for (int i = 2; i < input.size(); i++)
        {
            String[] rule = input.get(i).replace("=> ", "").split(" ");
            if (rule[1].equals("#"))
                rules.add(rule[0]);
        }

        return rules;
    }

    private static Set<Integer> readConfig(List<String> input)
    {
        String line1 = input.get(0);
        int k = line1.indexOf(':') + 2;
        line1 = line1.substring(k);

        Set<Integer> data = new HashSet<>();
        for (int i = 0; i < line1.length(); i++)
        {
            if (line1.charAt(i) == '#')
                data.add(i);
        }
        return data;
    }

}
