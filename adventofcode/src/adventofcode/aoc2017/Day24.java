package adventofcode.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day24
{

    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2017/day24.test");
        List<String> input2 = IOUtil.readFile("2017/day24.data");

        Map<Integer, List<Component>> lookup1 = createLookup(input1);
        Map<Integer, List<Component>> lookup2 = createLookup(input2);

        part1(lookup1);
        part1(lookup2);
    }

    private static void part1(Map<Integer, List<Component>> lookup)
    {
        List<List<Component>> bridges = findBridges(new ArrayList<>(), 0, lookup);
        List<Integer> bridgeWeights = bridges.stream()
                .map(lc -> lc.stream().map(c -> c.a + c.b).reduce((a, b) -> a + b).orElse(0))
                .collect(Collectors.toList());
        System.out.println("part1: " + bridgeWeights.stream().reduce(Math::max).orElse(0));

        Map<Integer, List<List<Component>>> bridgesByLength = new HashMap<>();
        for (List<Component> bridge : bridges)
        {
            int length = bridge.size();
            List<List<Component>> b1 = bridgesByLength.get(length);
            if (b1 == null)
            {
                b1 = new ArrayList<>();
                bridgesByLength.put(length, b1);
            }
            b1.add(bridge);
        }

        int maxlength = bridgesByLength.keySet().stream().reduce(Math::max).orElse(0);
        List<Integer> bridgeWeights2 = bridgesByLength.get(maxlength).stream()
                .map(lc -> lc.stream().map(c -> c.a + c.b).reduce((a, b) -> a + b).orElse(0))
                .collect(Collectors.toList());
        System.out.println("part2: " + bridgeWeights2.stream().reduce(Math::max).orElse(0));
    }

    private static List<List<Component>> findBridges(List<Component> used, int pinCount,
            Map<Integer, List<Component>> lookup)
    {
        List<List<Component>> result = new ArrayList<>();

        List<Component> a = new ArrayList<>(lookup.get(pinCount));

        a.removeAll(used);

        if (a.isEmpty())
        {
            result.add(used);
        }
        else
        {
            for (Component a1 : a)
            {
                List<Component> used1 = new ArrayList<>(used);
                used1.add(a1);
                result.addAll(findBridges(used1, a1.getOtherEnd(pinCount), lookup));
            }
        }

        return result;
    }

    private static Map<Integer, List<Component>> createLookup(List<String> input)
    {
        Map<Integer, List<Component>> lookup = new HashMap<>();
        input.stream().map(s -> new Component(s)).forEach(c -> {
            List<Component> lc = lookup.get(c.a);
            if (lc == null)
            {
                lc = new ArrayList<>();
                lookup.put(c.a, lc);
            }
            lc.add(c);

            lc = lookup.get(c.b);
            if (lc == null)
            {
                lc = new ArrayList<>();
                lookup.put(c.b, lc);
            }
            lc.add(c);
        });
        return lookup;
    }

    static class Component
    {
        int a;
        int b;

        Component(String input)
        {
            String[] f = input.split("/");
            a = Integer.parseInt(f[0]);
            b = Integer.parseInt(f[1]);
        }

        int getOtherEnd(int end)
        {
            return (end == a) ? b : a;
        }
    }

}
