package adventofcode.aoc2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day23
{
    public static void main(String[] args)
    {
        List<String> input2 = IOUtil.readFile("2017/day23.data");

        part1(input2);
        part2(input2);
    }

    private static void part1(List<String> input)
    {
        AssemDuet duet = new AssemDuet();
        duet.execute(input);

        System.out.println("part1: " + duet.mulExecuted);
    }

    private static void part2(List<String> input)
    {
        AssemDuet duet = new AssemDuet();
        duet.registers.put("a", 1L);
        duet.execute(input);

        System.out.println("part2: " + duet.registers.get("h"));
    }

    static class AssemDuet
    {
        Map<String, Long> registers = new HashMap<>();
        long sound;
        int instruction;
        int mulExecuted = 0;

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
                // case "snd":
                // sound = getValue(f[1]);
                // // System.out.println("# " + sound);
                // break;
                case "set":
                    registers.put(f[1], getValue(f[2]));
                    break;
                case "sub":
                    registers.put(f[1], getValue(f[1]) - getValue(f[2]));
                    break;
                case "mul":
                    registers.put(f[1], getValue(f[1]) * getValue(f[2]));
                    mulExecuted++;
                    break;
                // case "mod":
                // registers.put(f[1], getValue(f[1]) % getValue(f[2]));
                // break;
                // case "rcv":
                // if (getValue(f[1]) != 0)
                // {
                // System.out.println("received: " + sound);
                // result = true;
                // }
                // break;
                case "jnz":
                    if (getValue(f[1]) != 0)
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
