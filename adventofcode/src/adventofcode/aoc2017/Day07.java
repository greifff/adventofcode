package adventofcode.aoc2017;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;
import adventofcode.util.Patterns;

public class Day07
{
    public static void main(String[] args)
    {
        List<P> input1 = IOUtil.readFile("2017/day07.test").stream().map(s -> new P(s)).collect(Collectors.toList());
        List<P> input2 = IOUtil.readFile("2017/day07.data").stream().map(s -> new P(s)).collect(Collectors.toList());

        part1(input1);
        part1(input2);

        part2(input1, "tknk");
        part2(input2, "qibuqqg");
    }

    private static void part2(List<P> input1, String program)
    {

        Map<String, P> data = input1.stream().collect(Collectors.toMap(p -> p.name, p -> p));

        estimateWeight(data, program);

        for (P p : data.values())
        {
            if (!p.dep.isEmpty())
            {
                String o = "# " + p.name + " ";
                boolean balanced = true;
                for (int i = 0; i < p.dep.size(); i++)
                {
                    P p2 = data.get(p.dep.get(i));
                    if (i > 0)
                    {
                        P p3 = data.get(p.dep.get(i - 1));
                        balanced &= p2.fullweight == p3.fullweight;
                    }

                    o += p2.name + "=" + p2.fullweight + " ";
                }
                if (!balanced)
                {
                    System.out.println(o);
                }
            }
        }
        System.out.println();
        // System.out.println("part2: " + data.get(0).name);
    }

    private static int estimateWeight(Map<String, P> data, String program)
    {
        P p = data.get(program);
        int sum = 0;
        for (String p2 : p.dep)
        {
            sum += estimateWeight(data, p2);
        }
        p.fullweight = p.weight + sum;
        return p.fullweight;
    }

    private static void part1(List<P> input1)
    {
        List<P> data = input1;
        while (data.size() > 1)
        {
            List<String> names = data.stream().map(p -> p.name).collect(Collectors.toList());
            data = data.stream().filter(p -> {
                List<String> d = new ArrayList<>(p.dep);
                d.retainAll(names);
                return !d.isEmpty();
            }).collect(Collectors.toList());

        }
        System.out.println("part1: " + data.get(0).name);
    }

    static class P
    {
        String name;
        int weight;
        int fullweight;
        List<String> dep = new ArrayList<>();

        P(String info)
        {
            String[] f = info.replace(",", "").split(" ");
            Matcher m = Patterns.number.matcher(info);
            m.find();
            weight = Integer.parseInt(m.group());

            name = f[0];
            for (int i = 3; i < f.length; i++)
            {
                dep.add(f[i]);
            }
        }
    }

}
