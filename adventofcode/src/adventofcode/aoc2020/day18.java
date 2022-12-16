package adventofcode.aoc2020;

import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import adventofcode.util.IOUtil;

public class day18
{

    static class BufferedOp
    {
        long value;
        boolean multiply;
    }

    static class BufferedOp2
    {
        long addValue;
        long multiplyValue;
        boolean multiply;
    }

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2020/day18.data");

        // System.out.println(calculate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"));

        part1(input);
        // part2(input);
    }

    private static void part1(List<String> input)
    {
        // TODO Auto-generated method stub

        long result = input.stream().map(s -> calculate(s)).reduce((a, b) -> a + b).orElse(0L);

        System.out.println("part1: " + result);
    }

    private static long calculate(String line)
    {

        Stack<BufferedOp> buffer = new Stack<>();

        BufferedOp currentOp = new BufferedOp();

        StringTokenizer st = new StringTokenizer(line, " ()", true);

        while (st.hasMoreTokens())
        {
            String t = st.nextToken();
            switch (t)
            {
                case " ":
                    // do nothing
                    break;
                case "(":
                    buffer.push(currentOp);
                    currentOp = new BufferedOp();
                    break;
                case ")":
                    BufferedOp op1 = buffer.pop();
                    op1.value = op1.multiply ? op1.value * currentOp.value : op1.value + currentOp.value;
                    currentOp = op1;
                    break;
                case "*":
                    currentOp.multiply = true;
                    break;
                case "+":
                    currentOp.multiply = false;
                    break;
                default:
                    long v = Long.parseLong(t);
                    currentOp.value = currentOp.multiply ? v * currentOp.value : v + currentOp.value;
                    break;
            }
        }
        return currentOp.value;
    }

}
