package adventofcode.aoc2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day9
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day09.data");

        Map<String, Place> lookup = parseInput(input);

        List<List<Place>> ways = new ArrayList<>();
        List<Place> way1 = new ArrayList<>();
        ways.add(way1);

        List<Place> available = new ArrayList<>(lookup.values());

        for (int i = 0; i < available.size(); i++)
        {
            List<List<Place>> nextIteration = new ArrayList<>();
            for (List<Place> way : ways)
            {
                nextIteration.addAll(nextStep(way, available));
            }
            ways = nextIteration;
        }
        int result1 = Integer.MAX_VALUE;
        int result2 = 0;
        for (List<Place> way : ways)
        {
            int currentway = 0;

            for (int i = 1; i < way.size(); i++)
            {
                currentway += way.get(i - 1).paths.get(way.get(i));
            }

            result1 = Math.min(result1, currentway);
            result2 = Math.max(result2, currentway);
        }
        System.out.println("part 1: " + result1);
        System.out.println("part 2: " + result2);
    }

    private static List<List<Place>> nextStep(List<Place> way, List<Place> available)
    {
        List<List<Place>> result = new ArrayList<>();
        List<Place> available1 = new ArrayList<>(available);
        available1.removeAll(way);

        for (Place p : available1)
        {
            List<Place> way1 = new ArrayList<>(way);
            way1.add(p);
            result.add(way1);
        }

        return result;
    }

    private static Map<String, Place> parseInput(List<String> input)
    {
        Map<String, Place> places = new HashMap<>();

        for (String in : input)
        {
            String[] parts = in.replace(" to ", " ").replace(" = ", " ").split(" ");
            Place current = places.get(parts[0]);
            if (current == null)
            {
                current = new Place();
                current.name = parts[0];
                places.put(parts[0], current);
            }
            Place target = places.get(parts[1]);
            if (target == null)
            {
                target = new Place();
                target.name = parts[1];
                places.put(parts[1], target);
            }
            int length = Integer.parseInt(parts[2]);
            current.paths.put(target, length);
            target.paths.put(current, length);
        }

        return places;
    }

    static class Place
    {
        String name;
        Map<Place, Integer> paths = new HashMap<>();
    }
}
