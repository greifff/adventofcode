package adventofcode.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adventofcode.util.IOUtil;

public class Day12
{
    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2017/day12.test");
        List<String> input2 = IOUtil.readFile("2017/day12.data");
        Map<Integer, Set<Integer>> conn1 = mapConnections(input1);
        Map<Integer, Set<Integer>> conn2 = mapConnections(input2);

        part1(conn1);
        part1(conn2);
        part2(conn1);
        part2(conn2);
    }

    private static void part2(Map<Integer, Set<Integer>> conn1)
    {
        int groups = 1;
        Set<Integer> group = findGroup(0, conn1);
        Map<Integer, Set<Integer>> conn2 = new HashMap<>(conn1);
        conn2.keySet().removeAll(group);
        while (!conn2.isEmpty())
        {
            group = findGroup(conn2.keySet().iterator().next(), conn1);
            groups++;
            conn2.keySet().removeAll(group);
        }
        System.out.println("part2: " + groups);
    }

    private static void part1(Map<Integer, Set<Integer>> conn1)
    {
        System.out.println("part1: " + findGroup(0, conn1).size());
    }

    private static Set<Integer> findGroup(int a, Map<Integer, Set<Integer>> conn1)
    {
        Set<Integer> checked = new HashSet<>();
        Set<Integer> toCheck = new HashSet<>();

        toCheck.add(a);

        while (!toCheck.isEmpty())
        {
            Set<Integer> toCheck2 = new HashSet<>();
            for (int c : toCheck)
            {
                toCheck2.addAll(conn1.get(c));
            }
            checked.addAll(toCheck);
            toCheck = toCheck2;
            toCheck.removeAll(checked);
        }
        return checked;
    }

    private static Map<Integer, Set<Integer>> mapConnections(List<String> input)
    {
        Map<Integer, Set<Integer>> connections = new HashMap<>();
        for (String in : input)
        {
            String f[] = in.replace("<->", ",").replace(" ", "").split(",");
            List<Integer> k = new ArrayList<>();
            for (String f1 : f)
            {
                k.add(Integer.parseInt(f1));
            }

            int j = k.remove(0);
            Set<Integer> sj = connections.get(j);
            if (sj == null)
            {
                sj = new HashSet<>(k);
                connections.put(j, sj);
            }
            else
            {
                sj.addAll(k);
            }

            for (int k1 : k)
            {
                Set<Integer> sk1 = connections.get(k1);
                if (sk1 == null)
                {
                    sk1 = new HashSet<>();
                    connections.put(k1, sk1);
                }
                sk1.add(j);
            }
        }
        return connections;
    }

}
