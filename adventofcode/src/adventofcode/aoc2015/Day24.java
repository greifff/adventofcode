package adventofcode.aoc2015;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day24
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day24.data");
        List<Integer> packages = input.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());

        part1(packages);
        part2(packages);
    }

    private static void part1(List<Integer> packages)
    {

        int bagweight = packages.stream().reduce((a, b) -> a + b).orElse(0) / 3;

        System.out.println("## " + bagweight);

        Collections.sort(packages);
        Collections.reverse(packages);

        // find center bag configurations
        List<List<Integer>> centerBags = findBags(bagweight, new ArrayList<>(), new ArrayList<>(packages));

        System.out.println("## " + centerBags.size()); // 417649 configurations

        Collections.sort(centerBags, (o1, o2) -> o1.size() - o2.size());

        List<Long> quantumEntanglement = new ArrayList<>();
        // validate each configuration by finding at least on other bag of the same size (this implies that there is a
        // third bag)
        int i = 0;
        int s = centerBags.get(0).size();
        while (i < centerBags.size())
        {
            if (centerBags.get(i).size() > s && !quantumEntanglement.isEmpty())
            {
                break;
            }
            List<Integer> available = new ArrayList<>(packages);
            available.removeAll(centerBags.get(i));
            if (hasBags(bagweight, available))
            {
                quantumEntanglement
                        .add(centerBags.get(i).stream().map(k -> (long) k).reduce((a, b) -> a * b).orElse(0L));
            }
            i++;
        }
        System.out.println("## " + centerBags.size()); // 417649 configurations

        Collections.sort(quantumEntanglement);

        System.out.println("part 1: " + quantumEntanglement.get(0));
    }

    private static void part2(List<Integer> packages)
    {

        int bagweight = packages.stream().reduce((a, b) -> a + b).orElse(0) / 4;

        System.out.println("## " + bagweight);

        Collections.sort(packages);
        Collections.reverse(packages);

        // find center bag configurations
        List<List<Integer>> centerBags = findBags(bagweight, new ArrayList<>(), new ArrayList<>(packages));

        System.out.println("## " + centerBags.size()); // 417649 configurations

        Collections.sort(centerBags, (o1, o2) -> o1.size() - o2.size());

        List<Long> quantumEntanglement = new ArrayList<>();
        // validate each configuration by finding at least on other bag of the same size (this implies that there is a
        // third bag)
        int i = 0;
        int s = centerBags.get(0).size();
        while (i < centerBags.size())
        {
            if (centerBags.get(i).size() > s && !quantumEntanglement.isEmpty())
            {
                break;
            }
            List<Integer> available1 = new ArrayList<>(packages);
            available1.removeAll(centerBags.get(i));
            boolean valid = false;
            List<List<Integer>> trunkBags = findBags(bagweight, new ArrayList<>(), new ArrayList<>(available1));

            for (List<Integer> trunkBags1 : trunkBags)
            {
                List<Integer> available2 = new ArrayList<>(available1);
                available2.removeAll(trunkBags1);
                if (hasBags(bagweight, available2))
                {
                    valid = true;
                    break;
                }
            }
            if (valid)
            {
                quantumEntanglement
                        .add(centerBags.get(i).stream().map(k -> (long) k).reduce((a, b) -> a * b).orElse(0L));
            }
            i++;
        }

        Collections.sort(quantumEntanglement);

        System.out.println("part 2: " + quantumEntanglement.get(0));
    }

    private static List<List<Integer>> findBags(int bagweight, List<Integer> selected, List<Integer> packages)
    {
        List<List<Integer>> result = new ArrayList<>();

        while (!packages.isEmpty())
        {
            Integer package1 = packages.remove(0);
            if (package1 == bagweight)
            {
                List<Integer> selected2 = new ArrayList<>(selected);
                selected2.add(package1);
                result.add(selected2);
            }
            else if (package1 < bagweight)
            {
                List<Integer> selected2 = new ArrayList<>(selected);
                selected2.add(package1);
                result.addAll(findBags(bagweight - package1, selected2, new ArrayList<>(packages)));
            }
        }
        return result;
    }

    private static boolean hasBags(int bagweight, List<Integer> packages)
    {
        boolean result = false;

        while (!result && !packages.isEmpty())
        {
            Integer package1 = packages.remove(0);
            if (package1 == bagweight)
            {
                return true;
            }
            else if (package1 < bagweight)
            {
                result |= hasBags(bagweight - package1, new ArrayList<>(packages));
            }
        }
        return result;
    }

}
