package adventofcode.aoc2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day23
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day23.data");
        List<Statement> statements = input.stream().map(s -> new Statement(s)).collect(Collectors.toList());

        part1(statements);
        part2(statements);
    }

    private static void part1(List<Statement> statements)
    {
        Map<String, Integer> registers = new HashMap<>();
        registers.put("a", 0);
        registers.put("b", 0);

        execute(statements, registers);

        System.out.println("part1: " + registers.get("b"));
    }

    private static void part2(List<Statement> statements)
    {
        Map<String, Integer> registers = new HashMap<>();
        registers.put("a", 1);
        registers.put("b", 0);

        execute(statements, registers);

        System.out.println("part2: " + registers.get("b"));
    }

    private static void execute(List<Statement> statements, Map<String, Integer> registers)
    {
        int index = 0;

        while (index >= 0 && index < statements.size())
        {
            Statement s = statements.get(index);
            System.out.println("# " + s);
            switch (s.op)
            {
                case "inc":
                    registers.put(s.register, registers.get(s.register) + 1);
                    break;
                case "hlf":
                    registers.put(s.register, registers.get(s.register) / 2);
                    break;
                case "tpl":
                    registers.put(s.register, registers.get(s.register) * 3);
                    break;
                case "jmp":
                    index = index + s.value - 1;
                    break;
                case "jie":
                    if ((registers.get(s.register) & 1) == 0)
                    {
                        index += s.value - 1;
                    }
                    break;
                case "jio":
                    if (registers.get(s.register) == 1)
                    {
                        index += s.value - 1;
                    }
                    break;
            }
            index++;
            System.out.println("## " + (index + 1) + " " + registers.get("a") + " " + registers.get("b"));
        }
    }

    static class Statement
    {

        final String op;
        String register;
        int value;

        Statement(String s)
        {
            String[] f = s.replace("+", "").replace(",", "").split(" ");
            op = f[0];
            switch (f[1])
            {
                case "a":
                case "b":
                    register = f[1];
                    break;
                default:
                    value = Integer.parseInt(f[1]);
                    break;
            }
            if (f.length == 3)
            {
                value = Integer.parseInt(f[2]);
            }
        }

        @Override
        public String toString()
        {
            return op + " " + register + " " + value;
        }
    }

}
