package adventofcode.aoc2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day18
{
    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2017/day18.test");
        List<String> input2 = IOUtil.readFile("2017/day18.data");

        part1(input1);
        part1(input2);
    }

    private static void part1(List<String> input)
    {
        AssemDuet duet = new AssemDuet();
        duet.execute(input);
    }

    static class AssemDuet
    {
        Map<String, Long> registers = new HashMap<>();
        long sound;
        int instruction;

        void execute(List<String> stmts)
        {
            boolean received = false;
            while (!received && instruction >= 0 && instruction < stmts.size())
            {
                received = execute(stmts.get(instruction));
            }
        }

        boolean execute(String stmt)
        {
            boolean result = false;
            String[] f = stmt.split(" ");
            switch (f[0])
            {
                case "snd":
                    sound = getValue(f[1]);
                    // System.out.println("# " + sound);
                    break;
                case "set":
                    registers.put(f[1], getValue(f[2]));
                    break;
                case "add":
                    registers.put(f[1], getValue(f[1]) + getValue(f[2]));
                    break;
                case "mul":
                    registers.put(f[1], getValue(f[1]) * getValue(f[2]));
                    break;
                case "mod":
                    registers.put(f[1], getValue(f[1]) % getValue(f[2]));
                    break;
                case "rcv":
                    if (getValue(f[1]) != 0)
                    {
                        System.out.println("received: " + sound);
                        result = true;
                    }
                    break;
                case "jgz":
                    if (getValue(f[1]) > 0)
                    {
                        instruction += getValue(f[2]) - 1;
                    }
                    break;
            }
            instruction++;
            return result;
        }

        private long getValue(String s)
        {
            try
            {
                return Long.parseLong(s);
            }
            catch (NumberFormatException e)
            {
                //
            }
            Long v = registers.get(s);
            return v == null ? 0 : v;
        }

    }
}
