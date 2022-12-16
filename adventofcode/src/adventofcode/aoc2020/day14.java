package adventofcode.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class day14
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2020/day14.data");
        part1(input);
        part2(input);
    }

    private static void part2(List<String> input)
    {
        List<long[]> masks = new ArrayList<>();

        Map<Long, Long> memory = new HashMap<>();

        for (String in : input)
        {
            if (in.startsWith("mask = "))
            {
                masks.clear();
                String mask1 = in.substring("mask = ".length()).trim();

                long zeroMaskTemplate = (1L << 36) - 1L;
                long oneMaskTemplate = Long.parseLong(mask1.replace('X', '0'), 2);

                masks.add(new long[]
                { zeroMaskTemplate, oneMaskTemplate });
                int index = mask1.indexOf('X');
                while (index != -1)
                {
                    long point = 1L << (35 - index);
                    System.out.println("x1: ");
                    List<long[]> high = new ArrayList<>();
                    for (int i = 0; i < masks.size(); i++)
                    {
                        long zeroMask = masks.get(i)[0];
                        long oneMask = masks.get(i)[1];

                        long zeroMask1 = zeroMask - point;
                        long oneMask2 = oneMask | point;
                        masks.set(i, new long[]
                        { zeroMask1, oneMask });

                        high.add(new long[]
                        { zeroMask, oneMask2 });

                    }
                    masks.addAll(high);
                    index = mask1.indexOf('X', index + 1);
                }
            }
            else
            {
                // startsWith "mem[.*] = "

                long offset = Integer.parseInt(in.substring(4, in.indexOf(']')));
                long value = Long.parseLong(in.substring(in.indexOf('=') + 1).trim());

                for (long[] mask : masks)
                {
                    long offset1 = offset;
                    offset1 = offset1 & mask[0] | mask[1];
                    memory.put(offset1, value);

                }
            }
        }
        long sum = memory.values().stream().reduce((a, b) -> a + b).get();

        System.out.println("part2: " + sum);
    }

    private static void part1(List<String> input)
    {
        long zeroMask = 0L;
        long oneMask = 0L;
        Map<Integer, Long> memory = new HashMap<>();

        for (String in : input)
        {
            if (in.startsWith("mask = "))
            {
                String mask1 = in.substring("mask = ".length()).trim();
                zeroMask = Long.parseLong(mask1.replace('X', '1'), 2);
                oneMask = Long.parseLong(mask1.replace('X', '0'), 2);
            }
            else
            {
                // startsWith "mem[.*] = "

                int offset = Integer.parseInt(in.substring(4, in.indexOf(']')));
                long value = Long.parseLong(in.substring(in.indexOf('=') + 1).trim());

                value = value & zeroMask | oneMask;

                memory.put(offset, value);
            }
        }
        long sum = memory.values().stream().reduce((a, b) -> a + b).get();

        System.out.println("part1: " + sum);
    }
}
