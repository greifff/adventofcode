package adventofcode.aoc2020.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day20
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2020/day20.data");

        List<Tile> tiles = new ArrayList<>();

        int j = input.indexOf("");
        while (j != -1)
        {
            tiles.add(new Tile(input.subList(0, j)));
            input = input.subList(j + 1, input.size());
            j = input.indexOf("");
        }

        part1(tiles);
        part2(tiles);
    }

    private static void part2(List<Tile> tiles)
    {
        Puzzle puzzle = new Puzzle(tiles);
        puzzle.analyze();
        List<List<TilePointer>> grid1 = puzzle.solve();

        List<String> grid2 = new ArrayList<>();
        // int gridsize = 8 * grid1.size();

        for (int i = 0; i < grid1.size(); i++)
        {
            List<StringBuilder> sb = new ArrayList<>();
            for (int k = 0; k < 8; k++)
            {
                sb.add(new StringBuilder());
            }
            // List<TilePointer> tps = grid1.get(i);
            for (int j = 0; j < grid1.size(); j++)
            {
                TilePointer tp = grid1.get(i).get(j);
                System.out.print("" + tp.tile.getId() + " (" + tp.rotation + ")");
                List<String> content = tp.tile.getContent(tp.rotation);
                for (int k = 0; k < 8; k++)
                {
                    sb.get(k).append(content.get(k));
                }
            }
            System.out.println();
            grid2.addAll(sb.stream().map(s -> s.toString()).collect(Collectors.toList()));
        }

        // for (int i = 0; i < gridsize; i++)
        // {
        // List<TilePointer> tps = grid1.get(i / 8);
        // String s = "";
        // for (int j = 0; j < grid1.size(); j++)
        // {
        // TilePointer tp = tps.get(j);
        // s += tp.tile.getContent(tp.rotation).get(i % 8);
        // }
        // System.out.println(s);
        // grid2.add(s);
        // }

        SeamonsterImage im = new SeamonsterImage(grid2);

        // im.flipDiagonal();

        // for (int i = 0; i < 8; i++)
        // {
        // if (i == 4)
        // {
        // im.flip();
        // }
        // else if (i != 0)
        // {
        // im.rotate();
        // }
        List<int[]> found = im.findSeamonsters();

        System.out.println("Seamonsters: " + found.size() + " "
                + found.stream().map(a -> "(" + a[0] + "," + a[1] + ")").reduce((a, b) -> a + b).orElse(""));

        int waves = im.replaceSeamonstersAndCountWaves();
        System.out.println("part2: " + waves);

        // }
    }

    private static void part1(List<Tile> tiles)
    {
        Map<Integer, List<TilePointer>> tileLookupBySignature = new HashMap<>();

        for (Tile tile : tiles)
        {
            for (int side = 0; side < 4; side++)
            {
                for (int rotation = 0; rotation < 4; rotation++)
                {
                    TilePointer p1 = new TilePointer();
                    p1.tile = tile;
                    p1.side = side;
                    p1.rotation = rotation;
                    int sig = tile.getSignature(side, rotation);
                    List<TilePointer> p = tileLookupBySignature.get(sig);
                    if (p == null)
                    {
                        p = new ArrayList<>();
                        tileLookupBySignature.put(sig, p);
                    }
                    p.add(p1);
                }
            }
        }

        // System.out.println("## " + tileLookupBySignature.keySet());

        // find corner candidates
        for (Tile tile : tiles)
        {
            List<Long> adjacents = new ArrayList<>();
            for (int s = 0; s < 4; s++)
            {
                int sig = tile.getSignature(s, 0);
                List<TilePointer> p1 = tileLookupBySignature.get(sig);
                List<TilePointer> p2 = tileLookupBySignature.get(reverseSignature(sig));
                long found = 0;
                if (p1 != null)
                {
                    found += p1.stream().filter(pa -> (pa.tile != tile)).count();
                }
                if (p2 != null)
                {
                    found += p2.stream().filter(pa -> (pa.tile != tile)).count();
                }
                adjacents.add(found);
            }
            // System.out.println("" + tile.getId() + " " + String.join(",",
            // Arrays.asList(adjacents).stream().map(l -> "" + l).collect(Collectors.toList())));
            // boolean match1 = false;
            boolean match2 = false;
            for (int s = 0; s < 4; s++)
            {
                // match1 |= adjacents.get(s) == 0;
                match2 |= adjacents.get(s) == 0 && adjacents.get((s + 1) & 3) == 0;
                // match |= adjacents[s] == 0 && adjacents[(s + 1) & 3] == 0 && adjacents[(s + 2) & 3] != 0
                // && adjacents[(s + 3) & 3] != 0;
            }
            if (match2)
            {
                System.out.println("candidate: " + tile.getId() + (match2 ? " *" : ""));
            }
        }
    }

    static int reverseSignature(int sig)
    {
        int sig1 = sig;
        int sig2 = 0;
        for (int i = 0; i < 10; i++)
        {
            sig2 = (sig2 << 1) + (sig1 & 1);
            sig1 = sig1 >> 1;
        }
        return sig2;
    }
}
