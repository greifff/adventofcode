package adventofcode.aoc2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day14
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day14.data");
        List<Reindeer> reindeers = input.stream().map(s -> new Reindeer(s)).collect(Collectors.toList());

        part1(reindeers, 2503);
        part2(reindeers, 2503);
    }

    private static void part1(List<Reindeer> reindeers, int time)
    {
        int lead = 0;
        for (Reindeer r : reindeers)
        {
            lead = Math.max(lead, r.travelledDistance(time));
        }
        System.out.println("part1 : " + lead);
    }

    private static void part2(List<Reindeer> reindeers, int time)
    {
        for (int i = 1; i <= time; i++)
        {
            Map<Integer, List<Reindeer>> performance = new HashMap<>();
            for (Reindeer r : reindeers)
            {
                int d = r.travelledDistance(i);
                List<Reindeer> r2 = performance.get(d);
                if (r2 == null)
                {
                    r2 = new ArrayList<>();
                    performance.put(d, r2);
                }
                r2.add(r);
            }

            int leading = performance.keySet().stream().reduce((a, b) -> Math.max(a, b)).orElse(0);
            performance.get(leading).forEach(r -> r.points++);
        }

        int maxPoints = reindeers.stream().map(r -> r.points).reduce((a, b) -> Math.max(a, b)).orElse(0);
        System.out.println("part2 : " + maxPoints);
    }

    static class Reindeer
    {
        String name;
        int speed;
        int flightTime;
        int restTime;
        int points;

        private static final Pattern number = Pattern.compile("[0-9]+");

        Reindeer(String in)
        {
            name = in.split(" ").clone()[0];
            Matcher matcher = number.matcher(in);
            matcher.find();
            speed = Integer.parseInt(matcher.group());
            matcher.find();
            flightTime = Integer.parseInt(matcher.group());
            matcher.find();
            restTime = Integer.parseInt(matcher.group());
        }

        int travelledDistance(int time)
        {
            int etappeTime = flightTime + restTime;
            int etappes = time / etappeTime;
            int lastEtappe = time % etappeTime;

            int totalFlightTime = etappes * flightTime + Math.min(lastEtappe, flightTime);

            return totalFlightTime * speed;
        }
    }
}
