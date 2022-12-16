package adventofcode.aoc2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class day8
{

    static class stmt
    {
        String op;
        int mod;

        stmt(String input)
        {
            String[] f = input.split(" ");
            op = f[0];
            mod = Integer.parseInt(f[1]);
        }
    }

    public static void main(String[] args)
    {
        List<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("2020/day8.data")), StandardCharsets.UTF_8)))
        {
            String line = reader.readLine();
            while (line != null)
            {
                input.add(line);
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<stmt> program = new ArrayList<>();
        input.forEach(i -> program.add(new stmt(i)));

        // part1
        int accumulator = 0;
        int line = 0;
        Set<Integer> visited = new HashSet<>();

        while (line < program.size())
        {
            if (visited.contains(line))
            {
                break;
            }
            visited.add(line);
            switch (program.get(line).op)
            {
                case "acc":
                    accumulator += program.get(line).mod;
                case "nop":
                    line++;
                    break;
                case "jmp":
                    line += program.get(line).mod;
                    break;
            }
        }

        System.out.println("part1: " + accumulator);

        // part 2

        int result = 0;

        line = 0;
        while (line < program.size())
        {
            if (program.get(line).op.equals("acc"))
            {
                line++;
                continue;
            }

            boolean toJump = program.get(line).op.equals("nop");
            program.get(line).op = toJump ? "jmp" : "nop";

            try
            {
                result = runProgram(program);
                break;
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }

            program.get(line).op = toJump ? "nop" : "jmp";
            line++;
        }

        System.out.println("part2: " + result);
    }

    private static int runProgram(List<stmt> program) throws Exception
    {
        int accumulator = 0;
        int line = 0;
        Set<Integer> visited = new HashSet<>();

        while (line < program.size())
        {
            if (visited.contains(line))
            {
                throw new Exception("crap");
            }
            visited.add(line);
            switch (program.get(line).op)
            {
                case "acc":
                    accumulator += program.get(line).mod;
                case "nop":
                    line++;
                    break;
                case "jmp":
                    line += program.get(line).mod;
                    break;
            }
        }
        return accumulator;
    }
}
