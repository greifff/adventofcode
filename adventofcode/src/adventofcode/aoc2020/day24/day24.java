package adventofcode.aoc2020.day24;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class day24
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2020/day24.data");
        List<List<Direction>> directions = input.stream().map(s -> Direction.parseDirections(s))
                .collect(Collectors.toList());
        Map<Integer, Map<Integer, Tile>> initialFloor = part1(directions);
        part2(initialFloor);
    }

    private static void part2(Map<Integer, Map<Integer, Tile>> floor)
    {
        // TODO Auto-generated method stub

        for (int i = 1; i <= 100; i++)
        {

            flipTiles(floor);

            if (i <= 10 || i % 10 == 0)
            {
                int sum = countBlackTiles(floor);

                System.out.println("day " + i + ": " + sum);
            }
        }
    }

    private static void flipTiles(Map<Integer, Map<Integer, Tile>> floor)
    {
        // TODO Auto-generated method stub
        List<Tile> toFlip = new LinkedList<>();
        List<Tile> newTiles = new LinkedList<>();
        floor.values().stream().flatMap(c -> c.values().stream()).forEach(currentTile -> {
            // black and ( =0 or >2 are black) -> flip
            // white and =2 black -> flip
            int blackAround = 0;
            for (Direction d : Direction.values())
            {
                Tile other = getTile(floor, currentTile, d);
                if (other == null && currentTile.isBlack)
                {
                    newTiles.add(new Tile(currentTile, d));
                }
                else if (other != null && other.isBlack)
                {
                    blackAround++;
                }
            }
            if ((currentTile.isBlack && (blackAround == 0 || blackAround > 2))
                    || (!currentTile.isBlack && blackAround == 2))
            {
                toFlip.add(currentTile);
            }
        });
        for (Tile currentTile : newTiles)
        {
            // determine color for new tiles and insert them
            int blackAround = 0;
            for (Direction d : Direction.values())
            {
                Tile other = getTile(floor, currentTile, d);
                if (other != null && other.isBlack)
                {
                    blackAround++;
                }
            }
            if (blackAround == 2)
            {
                toFlip.add(currentTile);
            }
            insertTile(floor, currentTile);
        }
        for (Tile currentTile : toFlip)
        {
            currentTile.isBlack = !currentTile.isBlack;
        }
    }

    private static void insertTile(Map<Integer, Map<Integer, Tile>> floor, Tile tile)
    {
        Map<Integer, Tile> m1 = floor.get(tile.x);
        if (m1 == null)
        {
            m1 = new HashMap<>();
            floor.put(tile.x, m1);
        }
        m1.put(tile.y, tile);
    }

    private static Tile getTile(Map<Integer, Map<Integer, Tile>> floor, Tile from, Direction d)
    {
        int lx = from.x + d.getDX();
        int ly = from.y + d.getDY();
        Map<Integer, Tile> m1 = floor.get(lx);
        return m1 == null ? null : m1.get(ly);
    }

    private static Map<Integer, Map<Integer, Tile>> part1(List<List<Direction>> directions)
    {
        Map<Integer, Map<Integer, Tile>> flippedTiles = new HashMap<>();
        for (List<Direction> directions1 : directions)
        {
            int x = 0;
            int y = 0;
            for (Direction d : directions1)
            {
                x += d.getDX();
                y += d.getDY();
            }

            Map<Integer, Tile> m1 = flippedTiles.get(x);
            if (m1 == null)
            {
                m1 = new HashMap<>();
                flippedTiles.put(x, m1);
            }
            Tile tile = m1.get(y);
            if (tile == null)
            {
                m1.put(y, new Tile(x, y, true));
            }
            else
            {
                tile.isBlack = !tile.isBlack;
            }
        }

        int sum = countBlackTiles(flippedTiles);

        System.out.println("part1: " + sum);
        return flippedTiles;
    }

    private static int countBlackTiles(Map<Integer, Map<Integer, Tile>> floor)
    {
        return floor.values().stream().flatMap(c -> c.values().stream()).map(t -> t.isBlack ? 1 : 0)
                .reduce((a, b) -> a + b).orElse(0);
    }
}
