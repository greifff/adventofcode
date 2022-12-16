package adventofcode.aoc2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day7
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day07.data");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input)
    {
        Map<String, Integer> values = new HashMap<>();
        Map<String, Integer> overrides = new HashMap<>();

        process(input, values, overrides);

        // for (Map.Entry<String, Short> e : values.entrySet())
        // {
        // System.out.println("## " + e.getKey() + " " + e.getValue());
        // }

        System.out.println("part1: " + values.get("a"));
    }

    private static void part2(List<String> input)
    {
        Map<String, Integer> values = new HashMap<>();
        Map<String, Integer> overrides = new HashMap<>();
        overrides.put("b", 3176);

        process(input, values, overrides);

        // for (Map.Entry<String, Short> e : values.entrySet())
        // {
        // System.out.println("## " + e.getKey() + " " + e.getValue());
        // }

        System.out.println("part2: " + values.get("a"));
    }

    private static void process(List<String> input, Map<String, Integer> values, Map<String, Integer> overrides)
    {
        List<String> instructions = new ArrayList<>(input);

        while (!instructions.isEmpty())
        {
            List<String> done = new LinkedList<>();
            for (String instruction : instructions)
            {
                String[] a = instruction.split(" ");
                try
                {
                    switch (a.length)
                    {
                        case 3:
                            values.put(a[2], 0xFFFF & getValue(a[0], values, overrides));
                            break;
                        case 4:
                            values.put(a[3], 0xFFFF & (~getValue(a[1], values, overrides)));
                            break;
                        case 5:
                            int value1 = getValue(a[0], values, overrides);
                            int value2 = getValue(a[2], values, overrides);
                            int value3 = 0;
                            switch (a[1])
                            {
                                case "AND":
                                    value3 = value1 & value2;
                                    break;
                                case "OR":
                                    value3 = value1 | value2;
                                    break;
                                case "XOR":
                                    value3 = value1 ^ value2;
                                    break;
                                case "LSHIFT":
                                    value3 = value1 << value2;
                                    break;
                                case "RSHIFT":
                                    value3 = value1 >> value2;
                                    break;
                            }
                            values.put(a[4], value3 & 0xFFFF);
                            break;
                        default:
                            System.out.println("DANG! " + instruction);
                            return;
                    }
                    done.add(instruction);
                }
                catch (NumberFormatException e)
                {
                    // e.printStackTrace();
                    // return;
                }
            }
            // System.out.println("// " + done.size());
            instructions.removeAll(done);
        }
    }

    private static int getValue(String key, Map<String, Integer> variables, Map<String, Integer> overrides)
    {
        Integer value = overrides.get(key);
        if (value != null)
        {
            return value;
        }
        value = variables.get(key);
        if (value != null)
        {
            return value;
        }
        return Integer.parseInt(key);
    }

}
