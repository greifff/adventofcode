package adventofcode.aoc2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day07
{

    public static void main(String[] args)
    {
        List<String> test1 = IOUtil.readFile("2018/day07.test");
        List<String> input = IOUtil.readFile("2018/day07.data");

        part1(link(test1));
        part1(link(input));
        part2(link(test1), 2, 0);
        part2(link(input), 5, 60);
    }

    private static void part2(Map<Character, List<Character>> rel, int workerCount, int delay)
    {

        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < workerCount; i++)
        {
            workers.add(new Worker());
        }

        List<Character> used = new ArrayList<>();
        List<Character> available = new ArrayList<>();
        int time = 0;

        while (!rel.isEmpty() || !available.isEmpty())
        {
            for (Map.Entry<Character, List<Character>> e : rel.entrySet())
            {
                e.getValue().removeAll(used);
                if (e.getValue().isEmpty())
                {
                    available.add(e.getKey());
                }
            }

            Collections.sort(available);
            rel.keySet().removeAll(available);

            int nextGoal = Integer.MAX_VALUE;
            for (Worker w : workers)
            {
                if (w.workingOn == 0 && !available.isEmpty())
                {
                    char c = available.remove(0);
                    w.workingOn = c;
                    w.willCompleteAt = time + delay + 1 + c - 'A';
                }
                if (w.workingOn != 0)
                {
                    nextGoal = Math.min(nextGoal, w.willCompleteAt);
                }
            }

            time = nextGoal;

            for (Worker w : workers)
            {
                if (w.workingOn != 0 && w.willCompleteAt == time)
                {
                    // System.out.println("# " + time + " " + w.workingOn);
                    used.add(w.workingOn);
                    w.workingOn = 0;

                }
            }
        }
        System.out.println("part2: " + time);
    }

    static class Worker
    {

        char workingOn;
        int willCompleteAt;
    }

    private static void part1(Map<Character, List<Character>> rel)
    {
        Map<Character, List<Character>> relx = new HashMap<>(rel);

        List<Character> used = new ArrayList<>();
        List<Character> available = new ArrayList<>();

        while (!relx.isEmpty() || !available.isEmpty())
        {
            for (Map.Entry<Character, List<Character>> e : relx.entrySet())
            {
                e.getValue().removeAll(used);
                if (e.getValue().isEmpty())
                {
                    available.add(e.getKey());
                }
            }

            Collections.sort(available);
            relx.keySet().removeAll(available);

            used.add(available.remove(0));
        }

        System.out.println("part1: " + used.stream().map(c -> "" + c).reduce((c1, c2) -> c1 + c2).orElse(""));
    }

    private static Map<Character, List<Character>> link(List<String> input)
    {
        Map<Character, List<Character>> result = new HashMap<>();
        for (String in : input)
        {
            char a = in.charAt(5);
            char b = in.charAt(36);
            if (!result.containsKey(a))
            {
                result.put(a, new ArrayList<>());
            }
            if (!result.containsKey(b))
            {
                result.put(b, new ArrayList<>());
            }
            result.get(b).add(a);
        }
        return result;
    }
}
