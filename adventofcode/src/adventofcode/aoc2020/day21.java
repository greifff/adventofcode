package adventofcode.aoc2020;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class day21
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2020/day21.data");
        List<Entry> entries = input.stream().map(i -> new Entry(i)).collect(Collectors.toList());
        Map<String, String> allergens = identifyAllergens(entries);
        part1(entries, allergens);
        part2(allergens);
    }

    private static void part2(Map<String, String> allergens)
    {

        Map<String, String> allergens2 = new TreeMap<>(allergens);

        for (Map.Entry<String, String> e : allergens2.entrySet())
        {
            System.out.println("## " + e.getKey() + " " + e.getValue());
        }
        System.out.println("part2: " + String.join(",", allergens2.values()));
    }

    private static void part1(List<Entry> entries, Map<String, String> allergens)
    {

        // for (Map.Entry<String, Set<String>> s : allergens.entrySet())
        // {
        // System.out.println("# " + s.getKey() + " " + String.join(",", s.getValue()));
        // }

        // Set<String> riskCandidates = new HashSet<>();
        // for (Set<String> a : allergens.values())
        // {
        // riskCandidates.addAll(a);
        // }

        long c = 0;
        for (Entry e : entries)
        {
            c += e.ingredients.stream().filter(i -> !allergens.values().contains(i)).count();
        }
        System.out.println("part1: " + c);
    }

    private static Map<String, String> identifyAllergens(List<Entry> entries)
    {
        Map<String, String> allergens = new HashMap<>();
        Map<String, Set<String>> candidates = new HashMap<>();

        Set<String> allergensToIdentify = new HashSet<>(
                entries.stream().flatMap(e -> e.allergens.stream()).collect(Collectors.toList()));

        // round 1: raw data

        for (String a : allergensToIdentify)
        {
            Set<String> ingredients = new HashSet<String>();
            for (Entry e : entries)
            {
                if (e.allergens.contains(a))
                {
                    if (ingredients.isEmpty())
                    {
                        ingredients.addAll(e.ingredients);
                    }
                    else
                    {
                        ingredients.retainAll(e.ingredients);
                    }
                }
            }
            candidates.put(a, ingredients);
        }
        // round 2: remove identified stuff from candidates
        Collection<String> v1 = allergens.values();
        while (!candidates.isEmpty())
        {
            for (Map.Entry<String, Set<String>> c : candidates.entrySet())
            {
                Set<String> v2 = c.getValue();
                v2.removeAll(v1);
                if (v2.size() == 1)
                {
                    allergens.put(c.getKey(), v2.iterator().next());
                }
            }
            candidates.keySet().removeAll(allergens.keySet());
        }

        return allergens;
    }

    static class Entry
    {
        List<String> ingredients;
        List<String> allergens;

        Entry(String line)
        {
            int i = line.indexOf('(');
            ingredients = Arrays.asList(line.substring(0, i).trim().split(" "));
            allergens = Arrays.asList(line.substring(i + 9).trim().replace(",", "").replace(")", "").split(" "));
        }
    }
}
