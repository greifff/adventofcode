package adventofcode.aoc2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day17
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day17.data");
        List<Integer> buckets = input.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());

        List<List<Integer>> p = findPossibilities(150, buckets);
        System.out.println("part1: " + p.size());

        Map<Integer, List<List<Integer>>> p2 = new HashMap<>();
        for (List<Integer> p1 : p)
        {
            int l = p1.size();
            List<List<Integer>> p2a = p2.get(l);
            if (p2a == null)
            {
                p2a = new ArrayList<>();
                p2.put(l, p2a);
            }
            p2a.add(p1);
        }
        int mininmumBuckets = p2.keySet().stream().reduce((a, b) -> Math.min(a, b)).orElse(0);
        System.out.println("part2: " + p2.get(mininmumBuckets).size());

    }

    private static List<List<Integer>> findPossibilities(int amount, List<Integer> buckets)
    {
        List<List<Integer>> result = new ArrayList<>();
        if (buckets.isEmpty())
        {
            return result;
        }
        for (int i = 0; i < buckets.size(); i++)
        {
            int bucket = buckets.get(i);
            if (bucket == amount)
            {
                List<Integer> b1 = new ArrayList<>();
                b1.add(bucket);
                result.add(b1);
            }
            else if (bucket < amount)
            {
                List<List<Integer>> intermediate = findPossibilities(amount - bucket,
                        buckets.subList(i + 1, buckets.size()));
                for (List<Integer> int1 : intermediate)
                {
                    int1.add(0, bucket);
                    result.add(int1);
                }
            }
        }
        return result;
    }
}
