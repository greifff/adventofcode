package adventofcode.aoc2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day13
{
    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2017/day13.test");
        List<String> input2 = IOUtil.readFile("2017/day13.data");

        Map<Integer, Integer> conf1 = getFirewallConfig(input1);
        Map<Integer, Integer> conf2 = getFirewallConfig(input2);

        part1(conf1);
        part1(conf2);
        part2(conf1);
        part2(conf2);
    }

    private static void part2(Map<Integer, Integer> conf)
    {
        // int severity = 0;
        int delay = 0;

        while (true)
        {
            boolean caught = false;
            for (Map.Entry<Integer, Integer> e : conf.entrySet())
            {
                int ps = e.getKey();
                int depth = e.getValue();

                if ((ps + delay) % (2 * depth - 2) == 0)
                {
                    // severity += ps * depth;
                    caught = true;
                }
            }
            if (!caught)
            {
                System.out.println("part2: " + delay);
                return;
            }
            delay++;
        }

    }

    private static void part1(Map<Integer, Integer> conf)
    {
        int severity = 0;
        for (Map.Entry<Integer, Integer> e : conf.entrySet())
        {
            int ps = e.getKey();
            int depth = e.getValue();

            if (ps % (2 * depth - 2) == 0)
            {
                severity += ps * depth;
            }
        }

        System.out.println("part1: " + severity);
    }

    private static Map<Integer, Integer> getFirewallConfig(List<String> input)
    {
        Map<Integer, Integer> c = new HashMap<>();
        for (String in : input)
        {
            String[] f = in.replace(" ", "").split(":");
            c.put(Integer.parseInt(f[0]), Integer.parseInt(f[1]));
        }
        return c;
    }
}
