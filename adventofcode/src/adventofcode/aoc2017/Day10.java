package adventofcode.aoc2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day10
{

    public static void main(String[] args)
    {
        List<Integer> input = Arrays.asList(187, 254, 0, 81, 169, 219, 1, 190, 19, 102, 255, 56, 46, 32, 2, 216);
        part1(5, Arrays.asList(3, 4, 1, 5));
        part1(256, input);

        part2("");
        part2("AoC 2017");
        part2("1,2,3");
        part2("1,2,4");
        part2("187,254,0,81,169,219,1,190,19,102,255,56,46,32,2,216");
    }

    private static void part2(String sequence)
    {
        System.out.println("part2: " + knothash(sequence));
    }

    public static String knothash(String sequence)
    {
        List<Integer> lengths = new ArrayList<>();
        byte[] inputAsString = sequence.getBytes();
        for (byte b : inputAsString)
        {
            lengths.add(b < 0 ? 256 - b : b);
        }
        lengths.addAll(Arrays.asList(17, 31, 73, 47, 23));

        List<Integer> markers = new ArrayList<>();
        for (int i = 0; i < 256; i++)
        {
            markers.add(i);
        }
        int currentPosition = 0;
        int skipSize = 0;

        // for (int length : lengths)
        // {
        // System.out.println("# " + length);
        // }

        for (int i = 0; i < 64; i++)
        {
            for (int length : lengths)
            {
                twistSubList(markers, currentPosition, length);
                currentPosition = (currentPosition + length + skipSize) % 256;
                skipSize++;
            }
        }

        // condense
        String hash = "";
        for (int i = 0; i < 16; i++)
        {
            hash += Integer
                    .toHexString(
                            0x100 + markers.subList(i * 16, (i + 1) * 16).stream().reduce((a, b) -> a ^ b).orElse(0))
                    .substring(1);
        }
        return hash;
    }

    private static void part1(int markerCount, List<Integer> lengths)
    {
        List<Integer> markers = new ArrayList<>();
        for (int i = 0; i < markerCount; i++)
        {
            markers.add(i);
        }
        int currentPosition = 0;
        int skipSize = 0;

        for (int length : lengths)
        {
            twistSubList(markers, currentPosition, length);
            currentPosition = (currentPosition + length + skipSize) % markerCount;
            skipSize++;
        }

        System.out.println("part1: " + (markers.get(0) * markers.get(1)));
    }

    private static void twistSubList(List<Integer> markers, int from, int length)
    {
        if (from + length <= markers.size())
        {
            List<Integer> sublist = markers.subList(from, from + length);
            Collections.reverse(sublist);
        }
        else
        {
            List<Integer> sublist = new ArrayList<Integer>();
            sublist.addAll(markers.subList(from, markers.size()));
            sublist.addAll(markers.subList(0, from + length - markers.size()));
            Collections.reverse(sublist);
            for (int i = 0; i < sublist.size(); i++)
            {
                markers.set((from + i) % markers.size(), sublist.get(i));
            }
        }
    }

}
