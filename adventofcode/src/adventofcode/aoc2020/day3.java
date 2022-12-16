package adventofcode.aoc2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class day3
{

    public static void main(String[] args)
    {
        List<String> mapData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("2020/day3.data")), StandardCharsets.UTF_8)))
        {
            String line = reader.readLine();
            while (line != null)
            {
                mapData.add(line);
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // part 1
        long trees = 0;
        int x = 0;
        for (int y = 0; y < mapData.size(); y++)
        {
            if (mapData.get(y).charAt(x) == '#')
            {
                trees++;
            }
            x = (x + 3) % mapData.get(0).length();
        }
        System.out.println("part1: " + trees);

        // part 2
        int[] xstep =
        { 1, 3, 5, 7, 1 };
        int[] ystep =
        { 1, 1, 1, 1, 2 };

        trees = 1;
        for (int s = 0; s < 5; s++)
        {
            int trees1 = 0;
            x = 0;
            for (int y = 0; y < mapData.size(); y += ystep[s])
            {
                if (mapData.get(y).charAt(x) == '#')
                {
                    trees1++;
                }
                x = (x + xstep[s]) % mapData.get(0).length();
            }
            trees *= trees1;
        }

        System.out.println("part2: " + trees);
    }
}
