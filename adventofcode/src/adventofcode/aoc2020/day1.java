package adventofcode.aoc2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day1
{

    public static void main(String[] args)
    {
        List<Integer> values = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("2020/day1.data")), StandardCharsets.UTF_8)))
        {
            String line = reader.readLine();
            while (line != null)
            {
                values.add(Integer.parseInt(line));
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Collections.sort(values);

        // Part 1

        for (int a = 0; a < values.size() - 1; a++)
        {
            int a1 = values.get(a);
            for (int b = a + 1; b < values.size(); b++)
            {
                int b1 = values.get(b);
                if (a1 + b1 == 2020)
                {
                    System.out.println("Part 1: " + a1 + " " + b1 + " -> " + (a1 * b1));
                }
            }
        }

        // Part 2

        for (int a = 0; a < values.size() - 2; a++)
        {
            int a1 = values.get(a);
            for (int b = a + 1; b < values.size() - 1; b++)
            {
                int b1 = values.get(b);
                for (int c = b + 1; c < values.size(); c++)
                {
                    int c1 = values.get(c);
                    if (a1 + b1 + c1 == 2020)
                    {
                        System.out.println("Part 2: " + a1 + " " + b1 + " " + c1 + " -> " + (a1 * b1 * c1));
                    }
                }
            }
        }

    }
}
