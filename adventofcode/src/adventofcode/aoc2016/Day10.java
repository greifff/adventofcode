package adventofcode.aoc2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;
import adventofcode.util.Patterns;

public class Day10
{
    public static void main(String[] args)
    {
        // List<String> input1 = IOUtil.readFile("2016/day10.test");
        List<String> input2 = IOUtil.readFile("2016/day10.data");

        Map<Integer, List<Integer>> bots = new HashMap<>();
        initial(bots, input2);
        process(bots, input2);
        part1(bots);
        part2(bots);
    }

    private static void part2(Map<Integer, List<Integer>> bots)
    {
        // TODO Auto-generated method stub
        System.out.println("% " + bots.get(-10).stream().map(i -> "" + i).reduce((s1, s2) -> s1 + "," + s2).orElse(""));
        System.out.println("% " + bots.get(-11).stream().map(i -> "" + i).reduce((s1, s2) -> s1 + "," + s2).orElse(""));
        System.out.println("% " + bots.get(-12).stream().map(i -> "" + i).reduce((s1, s2) -> s1 + "," + s2).orElse(""));

        System.out.println("part2: " + (bots.get(-10).get(0) * bots.get(-11).get(0) * bots.get(-12).get(0)));
    }

    private static void part1(Map<Integer, List<Integer>> bots)
    {
        // TODO Auto-generated method stub
        for (Map.Entry<Integer, List<Integer>> chips : bots.entrySet())
        {
            if (chips.getValue().contains(61) && chips.getValue().contains(17))
            {
                System.out.println("part1: " + chips.getKey());
            }
        }
    }

    private static void process(Map<Integer, List<Integer>> bots, List<String> in)
    {
        List<String> in2 = new ArrayList<>(in.stream().filter(s -> s.startsWith("bot"))
                .map(s -> s.replace("bot ", "").replace("output ", "-1")).collect(Collectors.toList()));
        int i = 0;
        while (!in2.isEmpty())
        {
            i = i % in2.size();
            String s = in2.get(i);
            Matcher m = Patterns.number.matcher(s);
            m.find();
            int bot = Integer.parseInt(m.group());
            m.find();
            int lowtarget = Integer.parseInt(m.group());
            m.find();
            int hightarget = Integer.parseInt(m.group());
            List<Integer> c = bots.get(bot);
            if (c != null && c.size() == 2)
            {
                in2.remove(i);
                Collections.sort(c);
                assign(bots, lowtarget, c.get(0));
                assign(bots, hightarget, c.get(1));
            }
            i++;
        }
    }

    private static void assign(Map<Integer, List<Integer>> bots, int bot, int value)
    {
        List<Integer> values = bots.get(bot);
        if (values == null)
        {
            values = new ArrayList<>();
            bots.put(bot, values);
        }
        values.add(value);
    }

    private static void initial(Map<Integer, List<Integer>> bots, List<String> in)
    {
        in.stream().filter(s -> s.startsWith("value")).forEach(s -> {
            Matcher m = Patterns.number.matcher(s);
            m.find();
            int value = Integer.parseInt(m.group());
            m.find();
            int bot = Integer.parseInt(m.group());

            assign(bots, bot, value);
        });
    }
}
