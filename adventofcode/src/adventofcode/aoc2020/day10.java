package adventofcode.aoc2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class day10
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2020/day10.data");

        List<Integer> adapters = new ArrayList<Integer>();

        input.forEach(s -> adapters.add(Integer.parseInt(s)));

        Collections.sort(adapters);

        // part 1
        int joltage = 0;

        int jolt1steps = 0;
        int jolt3steps = 1; // the built-in one

        for (int i = 0; i < adapters.size(); i++)
        {
            int a = adapters.get(i);
            switch (a - joltage)
            {
                case 0:
                case 2:
                    break;
                case 1:
                    jolt1steps++;
                    break;
                case 3:
                    jolt3steps++;
                    break;
                default:
                    System.out.println("error: difference=" + (a - joltage));
                    break;
            }
            joltage = a;
        }

        System.out.println("part1: " + (jolt1steps * jolt3steps));

        // part 2

        // adapters.forEach(a -> System.out.println(a));

        adapters.add(0, 0);
        adapters.add(adapters.get(adapters.size() - 1) + 3);

        List<Integer> groupsizes = new ArrayList<>();
        int currentgroup = 0; // count the socket and first element in
        for (int i = 1; i < adapters.size(); i++)
        {
            int delta = adapters.get(i) - adapters.get(i - 1);

            currentgroup++;

            if (delta == 3)
            {
                groupsizes.add(currentgroup);
                currentgroup = 0;
            }
        }
        groupsizes.add(currentgroup);

        System.out.println("y " + String.join(" ", groupsizes.stream().map(m -> "" + m).collect(Collectors.toList())));

        long variants = 1;
        for (int d = 0; d < groupsizes.size(); d++)
        {
            switch (groupsizes.get(d))
            {
                case 1:
                case 2:
                    break;
                case 3:
                    variants *= 2L;
                    break;
                case 4:
                    variants *= 4L;
                    break;
                case 5:
                    variants *= 7L;
                    break;
            }
        }

        System.out.println("part2 " + variants);
    }
}
