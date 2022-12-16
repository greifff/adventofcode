package adventofcode.aoc2016;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AssembunnyComputer
{

    private Map<String, Integer> registers = new HashMap<>();

    private StringBuilder out = new StringBuilder();

    public AssembunnyComputer()
    {
        registers.put("a", 0);
        registers.put("b", 0);
        registers.put("c", 0);
        registers.put("d", 0);
    }

    public void setRegister(String r, int v)
    {
        registers.put(r, v);
    }

    public int getRegister(String r)
    {
        return registers.get(r);
    }

    public void execute(List<String> statements)
    {
        executeInternal(statements.stream().map(s -> new Statement(s)).collect(Collectors.toList()));
    }

    private void executeInternal(List<Statement> statements)
    {
        int index = 0;

        while (index >= 0 && index < statements.size())
        {
            Statement s = statements.get(index);
            // System.out.println("# " + s);
            switch (s.op)
            {
                case "inc":
                    registers.put(s.register1, registers.get(s.register1) + 1);
                    break;
                case "dec":
                    registers.put(s.register1, registers.get(s.register1) - 1);
                    break;
                case "cpy":
                    if (s.register2 != null)
                    {
                        registers.put(s.register2, s.register1 != null ? registers.get(s.register1) : s.value1);
                    }
                    break;
                case "jnz":
                    int v = s.register1 != null ? registers.get(s.register1) : s.value1;
                    if (v != 0)
                    {
                        index = index + (s.register2 != null ? registers.get(s.register2) : s.value2) - 1;
                    }
                    break;
                case "tgl":
                    toogle(index, s, statements);
                    break;
                case "out":
                    out.append(registers.get(s.register1));
                    if (out.length() == 40)
                    {
                        return;
                    }
                    break;
            }
            index++;
            // System.out.println("## " + index + ": " + registers.get("a") + " " + registers.get("b") + " "
            // + registers.get("c") + " " + registers.get("d"));
        }
    }

    private void toogle(int index, Statement s, List<Statement> statements)
    {
        int targetIndex = index + registers.get(s.register1);

        if (targetIndex < 0 || targetIndex >= statements.size())
        {
            return;
        }

        Statement o = statements.get(targetIndex);

        switch (o.op)
        {
            case "inc":
                o.op = "dec";
                break;
            case "dec":
            case "tgl":
                o.op = "inc";
                break;
            case "jnz":
                o.op = "cpy";
                break;
            case "cpy":
                o.op = "jnz";
                break;
        }
    }

    public String getOutput()
    {
        return out.toString();
    }

    static class Statement
    {

        String op;
        String register1;
        String register2;
        int value1;
        int value2;

        Statement(String s)
        {
            String[] f = s.replace("+", "").replace(",", "").split(" ");
            op = f[0];
            switch (f[1])
            {
                case "a":
                case "b":
                case "c":
                case "d":
                    register1 = f[1];
                    break;
                default:
                    value1 = Integer.parseInt(f[1]);
                    break;
            }
            if (f.length == 3)
            {
                switch (f[2])
                {
                    case "a":
                    case "b":
                    case "c":
                    case "d":
                        register2 = f[2];
                        break;
                    default:
                        value2 = Integer.parseInt(f[2]);
                        break;
                }
            }
        }

        @Override
        public String toString()
        {
            return op + " " + register1 + "|" + value1 + " " + register2 + "|" + value2;
        }
    }

}
