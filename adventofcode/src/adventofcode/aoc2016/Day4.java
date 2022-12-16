package adventofcode.aoc2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day4
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2016/day04.data");

        part1(input);
        part2(input);
    }

    private static void part2(List<String> input)
    {
        // TODO Auto-generated method stub
        for (String room : input)
        {
            if (isReal(room))
            {
                String realname = decipherRoomName(room);
                if (realname.contains("north"))
                {
                    System.out.println("# " + realname);
                    System.out.println(
                            "part2: " + Integer.parseInt(room.substring(room.lastIndexOf("-") + 1, room.indexOf("["))));
                }
            }
        }
    }

    private static String decipherRoomName(String r)
    {
        int k = r.lastIndexOf("-");
        String name = r.substring(0, k);
        int id = Integer.parseInt(r.substring(k + 1, r.indexOf("[")));

        name = name.replace("-", " ");
        String deciphered = "";
        for (int i = 0; i < name.length(); i++)
        {
            char c = name.charAt(i);
            if (c == ' ')
            {
                deciphered += " ";
            }
            else
            {
                deciphered += (char) ((id + c - 'a') % 26 + 'a');
            }
        }
        return deciphered;
    }

    private static void part1(List<String> input)
    {
        int sum = 0;
        for (String room : input)
        {
            if (isReal(room))
            {
                sum += Integer.parseInt(room.substring(room.lastIndexOf("-") + 1, room.indexOf("[")));
            }
        }
        System.out.println("part1: " + sum);
    }

    private static boolean isReal(String r)
    {
        int k = r.lastIndexOf("-");
        String name = r.substring(0, k);
        name = name.replace("-", "");
        Map<Character, Occurrence> occ = new HashMap<>();
        for (int i = 0; i < name.length(); i++)
        {
            char c = name.charAt(i);
            Occurrence o1 = occ.get(c);
            if (o1 == null)
            {
                o1 = new Occurrence();
                o1.c = c;
                occ.put(c, o1);
            }
            o1.count++;
        }
        List<Occurrence> occ2 = new ArrayList<>(occ.values());
        Collections.sort(occ2);
        String checksum1 = occ2.stream().map(o -> "" + o.c).reduce((a, b) -> a + b).orElse("");
        String checksum2 = r.substring(r.length() - 6, r.length() - 1);
        return checksum1.substring(0, 5).equals(checksum2);
    }

    static class Occurrence implements Comparable<Occurrence>
    {
        int count;
        char c;

        @Override
        public int compareTo(Occurrence o)
        {
            int delta = o.count - count;
            if (delta != 0)
                return delta;
            return c - o.c;
        }

    }
}
