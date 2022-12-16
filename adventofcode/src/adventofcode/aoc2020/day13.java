package adventofcode.aoc2020;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class day13
{

    public static void main(String[] args)
    {
        // List<String> input = util.readFile("day13.data");
        long timestamp = // 939L;
                1002578L;
        String[] input = //
                // "7,13,x,x,59,x,31,19" // test data
                // "17,x,13,19"// test data 2
                "19,x,x,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,751,x,29,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,431,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,17"
                        .split(",");
        final List<Long> ids = Arrays.asList(input).stream().map(s -> "x".equals(s) ? -1L : Long.parseLong(s))
                .collect(Collectors.toList());
        part1(timestamp, ids);
        part2(ids);
    }

    {

    }

    private static void part2(List<Long> ids)
    {
        long lcm = -1, time = -1;
        int index = 0;
        while (true)
        {
            final long id = ids.get(index);
            if (id == -1)
            {
                index++;
                continue;
            }

            if (lcm == -1)
            {
                lcm = id;
                time = id - index;
                index++;
                continue;
            }

            if ((time + index) % id == 0)
            {
                if (++index >= ids.size())
                {
                    System.out.println(time);
                    return;
                }

                lcm *= id;
                continue;
            }

            time += lcm;
        }
    }

    private static void part1(long timestamp, List<Long> ids)
    {

        List<Long> ids2 = ids.stream().filter(i -> i != -1).collect(Collectors.toList());

        for (long bus : ids2)
        {
            long count = timestamp / bus;
            long waittime = bus * (count + 1) - timestamp;
            System.out.println(" " + bus + " " + waittime + " " + (bus * waittime));
        }

        System.out.println("part1 :");
    }

}
