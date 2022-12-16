package adventofcode.aoc2020.day20;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Tile
{
    private int id;
    private int[] signatures = new int[4];
    private List<String> content = new ArrayList<>();

    Tile(List<String> data)
    {
        id = Integer.parseInt(data.get(0).substring(5, 9));

        String left = "";
        String right = "";
        for (int i = 1; i < data.size(); i++)
        {
            String s = data.get(i);
            left += s.charAt(0);
            right += s.charAt(s.length() - 1);
        }

        signatures[0] = Integer.parseInt(toBoolean(data.get(1)), 2);
        signatures[1] = Integer.parseInt(toBoolean(right), 2);
        signatures[2] = Integer.parseInt(toBoolean(reverse(data.get(data.size() - 1))), 2);
        signatures[3] = Integer.parseInt(toBoolean(reverse(left)), 2);

        for (int i = 2; i < data.size() - 1; i++)
        {
            String s = data.get(i);
            content.add(s.substring(1, s.length() - 1));
        }
    }

    private String toBoolean(String s)
    {
        return s.replace('#', '1').replace('.', '0');
    }

    private String reverse(String s)
    {
        return new StringBuilder(s).reverse().toString();
    }

    int getId()
    {
        return id;
    }

    int getSignature(int side, int rotation)
    {
        int index = rotation * 100 + side;
        switch (index)
        {
            case 0:
            case 101:
            case 202:
            case 303:
                return signatures[0];
            case 400:
            case 503:
            case 602:
            case 701:
                return Day20.reverseSignature(signatures[0]);
            case 1:
            case 102:
            case 203:
            case 300:
                return signatures[1];
            case 403:
            case 502:
            case 601:
            case 700:
                return Day20.reverseSignature(signatures[1]);
            case 2:
            case 103:
            case 200:
            case 301:
                return signatures[2];
            case 402:
            case 501:
            case 600:
            case 703:
                return Day20.reverseSignature(signatures[2]);
            case 3:
            case 100:
            case 201:
            case 302:
                return signatures[3];
            default:
                return Day20.reverseSignature(signatures[3]);
        }
    }

    List<String> getContent(int rotation)
    {
        List<String> tmp = new ArrayList<>(content);
        List<String> result = new ArrayList<>();
        for (int k = 0; k < (rotation & 3); k++)
        {
            for (int y = 0; y < 8; y++)
            {
                String row = "";
                for (int x = 0; x < 8; x++)
                {
                    row += tmp.get(7 - x).charAt(y);
                }
                result.add(row);
            }
            tmp = result;
            result = new ArrayList<>();
        }
        if ((rotation & 4) != 0)
        {
            tmp = tmp.stream().map(s -> new StringBuilder(s).reverse().toString()).collect(Collectors.toList());
        }
        return tmp;
    }

}