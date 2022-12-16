package adventofcode.aoc2016;

import java.util.List;
import java.util.regex.Pattern;

import adventofcode.util.IOUtil;

public class Day25
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2016/day25.data");

        Pattern clock = Pattern.compile("(01)+");

        int i = 0;
        while (true)
        {
            AssembunnyComputer c = new AssembunnyComputer();

            c.setRegister("a", i);

            c.execute(input);
            String x = c.getOutput();
            if (clock.matcher(x).matches())
            {
                System.out.println("part1: " + i);
                break;
            }
            i++;
        }
    }
}
