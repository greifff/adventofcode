package adventofcode.aoc2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day16
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day16.data");
        List<Map<String, Integer>> auntSues = input.stream().map(s -> readAuntSue(s)).collect(Collectors.toList());

        Map<String, Integer> criteria = new HashMap<>();
        criteria.put("children", 3);
        criteria.put("cats", 7);
        criteria.put("samoyeds", 2);
        criteria.put("pomeranians", 3);
        criteria.put("akitas", 0);
        criteria.put("vizslas", 0);
        criteria.put("goldfish", 5);
        criteria.put("trees", 3);
        criteria.put("cars", 2);
        criteria.put("perfumes", 1);

        part1(criteria, auntSues);
        part2(criteria, auntSues);
    }

    private static void part2(Map<String, Integer> criteria, List<Map<String, Integer>> auntSues)
    {
        for (Map<String, Integer> auntSue : auntSues)
        {
            boolean matches = true;
            for (Map.Entry<String, Integer> c : criteria.entrySet())
            {
                String key = c.getKey();
                int value2 = c.getValue();
                Integer value1 = auntSue.get(key);
                if (value1 != null)
                {
                    switch (key)
                    {
                        case "cats":
                        case "trees":
                            matches &= value1 > value2;
                            break;
                        case "pomeranians":
                        case "goldfish":
                            matches &= value1 < value2;
                            break;
                        default:
                            matches &= value1 == value2;
                            break;
                    }
                }
            }
            if (matches)
            {
                System.out.println("part1: " + auntSue.get("Sue"));
            }
        }
    }

    private static void part1(Map<String, Integer> criteria, List<Map<String, Integer>> auntSues)
    {
        for (Map<String, Integer> auntSue : auntSues)
        {
            boolean matches = true;
            for (Map.Entry<String, Integer> c : criteria.entrySet())
            {
                Integer value = auntSue.get(c.getKey());
                if (value != null)
                {
                    matches &= value.equals(c.getValue());
                }
            }
            if (matches)
            {
                System.out.println("part1: " + auntSue.get("Sue"));
            }
        }
    }

    private static Map<String, Integer> readAuntSue(String in)
    {
        String[] f = in.replace(":", "").replace(",", "").split(" ");
        Map<String, Integer> result = new HashMap<>();
        for (int i = 1; i < f.length; i += 2)
        {
            result.put(f[i - 1], Integer.parseInt(f[i]));
        }
        return result;
    }
}
