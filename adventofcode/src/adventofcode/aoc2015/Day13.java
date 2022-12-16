package adventofcode.aoc2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day13
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day13.data");
        Map<String, Guest> guests = readGuests(input);
        part1(guests);
        part2(guests);
    }

    private static void part2(Map<String, Guest> guests)
    {
        Map<String, Guest> guests2 = new HashMap<>(guests);
        Guest me = new Guest();
        me.name = "Me";
        guests2.put("Me", me);
        System.out.println("part2: " + getBest(guests2));
    }

    private static void part1(Map<String, Guest> guests)
    {
        System.out.println("part1: " + getBest(guests));
    }

    private static int getBest(Map<String, Guest> guests)
    {
        List<List<Guest>> placements = new ArrayList<>();
        placements.add(new ArrayList<>());
        List<Guest> available = new ArrayList<>(guests.values());

        for (int i = 0; i < guests.size(); i++)
        {
            List<List<Guest>> nextIteration = new ArrayList<>();
            for (List<Guest> placement : placements)
            {
                nextIteration.addAll(nextStep(placement, available));
            }
            placements = nextIteration;
            // System.out.println("## " + placements.size());
        }

        int best = 0;
        for (List<Guest> g1 : placements)
        {
            int value = evaluateRelation(g1.get(0), g1.get(g1.size() - 1));

            for (int i = 1; i < g1.size(); i++)
            {
                value += evaluateRelation(g1.get(i - 1), g1.get(i));
            }
            // System.out.println("// " + value);
            best = Math.max(value, best);
        }

        return best;
    }

    private static int evaluateRelation(Guest gu1, Guest gu2)
    {
        Integer i1 = gu1.relation.get(gu2.name);
        Integer i2 = gu2.relation.get(gu1.name);

        // System.out.println("-- " + gu1.name + " " + gu2.name + " " + i1 + " " + i2);

        return (i1 == null ? 0 : i1) + (i2 == null ? 0 : i2);
    }

    private static List<List<Guest>> nextStep(List<Guest> placement, List<Guest> available)
    {
        List<Guest> a1 = new ArrayList<>(available);
        a1.removeAll(placement);
        List<List<Guest>> b = new ArrayList<>();
        for (Guest a2 : a1)
        {
            List<Guest> b1 = new ArrayList<>(placement);
            b1.add(a2);
            b.add(b1);
        }
        return b;
    }

    private static Map<String, Guest> readGuests(List<String> input)
    {
        Map<String, Guest> guests = new HashMap<>();
        for (String s : input)
        {
            String[] t = s.replace("would ", "").replace("gain ", "").replace("lose ", "-").replace(".", "")
                    .replace("happiness units by sitting next to ", "").split(" ");

            String name1 = t[0];
            String name2 = t[2];
            int score = Integer.parseInt(t[1]);

            Guest g = guests.get(name1);
            if (g == null)
            {
                g = new Guest();
                g.name = name1;
                guests.put(name1, g);
            }
            g.relation.put(name2, score);
        }
        return guests;
    }

    static class Guest
    {
        String name;
        Map<String, Integer> relation = new HashMap<>();
    }

}
