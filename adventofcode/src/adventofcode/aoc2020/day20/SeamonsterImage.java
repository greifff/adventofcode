package adventofcode.aoc2020.day20;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SeamonsterImage
{

    private List<String> content;
    private int size;

    public SeamonsterImage(List<String> content)
    {
        this.content = content;
        this.size = content.size();
    }

    public void rotate()
    {
        List<String> result = new ArrayList<>();
        for (int y = 0; y < size; y++)
        {
            String row = "";
            for (int x = 0; x < size; x++)
            {
                row += content.get(size - 1 - y).charAt(x);
            }
            result.add(row);
        }
        content = result;
    }

    public void flip()
    {
        content = content.stream().map(s -> new StringBuilder(s).reverse().toString()).collect(Collectors.toList());
    }

    public List<int[]> findSeamonsters()
    {
        String[] pattern =
        { //
                "                  # ".replace(' ', '.'), //
                "#    ##    ##    ###".replace(' ', '.'), //
                " #  #  #  #  #  #   ".replace(' ', '.')//
        };

        Pattern searchPattern = Pattern.compile(pattern[1]);
        Pattern headPattern = Pattern.compile(pattern[0]);
        Pattern footPattern = Pattern.compile(pattern[2]);

        List<int[]> result = new ArrayList<>();

        for (int i = 1; i <= content.size() - 2; i++)
        {
            Matcher m = searchPattern.matcher(content.get(i));
            while (m.find())
            {
                int index = m.start();
                String h = content.get(i - 1).substring(index, index + pattern[0].length());
                String f = content.get(i + 1).substring(index, index + pattern[0].length());
                if (headPattern.matcher(h).matches() && footPattern.matcher(f).matches())
                {
                    result.add(new int[]
                    { index, i - 1 });
                }
            }
        }
        return result;
    }

    public int replaceSeamonstersAndCountWaves()
    {
        List<int[]> seamonsters = findSeamonsters();
        String[] pattern =
        { //
                "                  X ", //
                "X    XX    XX    XXX", //
                " X  X  X  X  X  X   "//
        };

        List<String> result = new ArrayList<>(content);

        for (int[] coords : seamonsters)
        {
            for (int l = 0; l < 3; l++)
            {
                String line = result.get(coords[1] + l);

                int start = coords[0];
                int stop = coords[0] + pattern[0].length();

                result.set(coords[1] + l, //
                        line.substring(0, start) + markSeamonster(line.substring(start, stop), pattern[l])
                                + line.substring(stop));
            }
        }

        int waves = 0;
        System.out.println("\n======\n");
        for (String line : result)
        {
            waves += line.replace(" ", "").replace(".", "").replace("X", "").length();
            System.out.println(line);
        }

        return waves;
    }

    private String markSeamonster(String fragment, String pattern)
    {
        String result = "";
        for (int i = 0; i < pattern.length(); i++)
        {
            result += pattern.charAt(i) == 'X' ? 'X' : fragment.charAt(i);
        }
        return result;
    }

    public void flipDiagonal()
    {
        List<String> result = new ArrayList<>();
        for (int y = 0; y < size; y++)
        {
            String row = "";
            for (int x = 0; x < size; x++)
            {
                row += content.get(x).charAt(y);
            }
            result.add(row);
        }
        content = result;
    }
}
