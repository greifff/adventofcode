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

public class day9
{
    public static void main(String[] args)
    {
        List<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("2020/day9.data")), StandardCharsets.UTF_8)))
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

        List<Long> numbers = new ArrayList<>();

        input.forEach(s -> numbers.add(Long.parseLong(s)));

        // part 1

        List<Long> relevant25 = new ArrayList<>();

        int i = 0;
        while (i < 26)
        {
            relevant25.add(numbers.get(i));
            i++;
        }

        long troublemaker = 0;
        while (i < 1000)
        {
            boolean valid = isValid(numbers.get(i), relevant25);
            if (!valid)
            {
                troublemaker = numbers.get(i);
                System.out.println("part 1: " + numbers.get(i) + " (" + i + ")");
                break;
            }
            relevant25.remove(0);
            relevant25.add(numbers.get(i));
            i++;
        }

        // part 2

        for (int j = 0; j < i; j++)
        {
            long testsum = numbers.get(j);
            for (int k = j + 1; k <= i; k++)
            {
                testsum += numbers.get(k);
                if (testsum == troublemaker)
                {
                    List<Long> sublist = numbers.subList(j, k + 1);
                    Collections.sort(sublist);
                    System.out.println(
                            "part 2: " + testsum + " -- " + (sublist.get(0) + sublist.get(sublist.size() - 1)));
                    break;
                }
                else if (testsum > troublemaker)
                {
                    break;
                }
            }
        }
    }

    private static boolean isValid(long contestant, List<Long> relevant25)
    {
        for (int i = 0; i < 25; i++)
        {
            for (int j = i + 1; j < 26; j++)
            {
                if (relevant25.get(i) + relevant25.get(j) == contestant)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
