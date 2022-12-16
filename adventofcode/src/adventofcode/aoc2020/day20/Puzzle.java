package adventofcode.aoc2020.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Puzzle
{

    Map<Integer, List<TilePointer>> tileLookupBySignature = new HashMap<>();

    List<Tile> corners = new ArrayList<>();
    List<Tile> borders = new ArrayList<>();

    List<Tile> tiles;

    public Puzzle(List<Tile> tiles)
    {
        this.tiles = tiles;
        for (Tile tile : tiles)
        {
            System.out.println("// " + tile.getId());
            for (int side = 0; side < 4; side++)
            {
                for (int rotation = 0; rotation < 8; rotation++)
                {
                    TilePointer p1 = new TilePointer();
                    p1.tile = tile;
                    p1.side = side;
                    p1.rotation = rotation;
                    int sig = tile.getSignature(side, rotation);
                    System.out.print(" " + Integer.toBinaryString(sig));
                    List<TilePointer> p = tileLookupBySignature.get(sig);
                    if (p == null)
                    {
                        p = new ArrayList<>();
                        tileLookupBySignature.put(sig, p);
                    }
                    p.add(p1);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public void analyze()
    {
        // find corner and border tiles
        for (Tile tile : tiles)
        {
            List<Integer> adjacents = getAdjacentTileCounts(tile);

            boolean match1 = false;
            boolean match2 = false;
            for (int s = 0; s < 4; s++)
            {
                boolean thisSideOnBorder = adjacents.get(s) == 0;
                boolean nextSideOnBorder = adjacents.get((s + 1) & 3) == 0;
                match1 |= thisSideOnBorder;
                match2 |= thisSideOnBorder && nextSideOnBorder;
            }
            if (match2)
            {
                corners.add(tile);
            }
            else if (match1)
            {
                borders.add(tile);
            }
        }

        for (List<TilePointer> p : tileLookupBySignature.values())
        {
            Set<Tile> t = p.stream().map(p1 -> p1.tile).collect(Collectors.toSet());
            t.retainAll(borders);
            if (t.size() > 2)
            {
                System.out.println("###### multiple signature");
            }
        }
    }

    private List<Integer> getAdjacentTileCounts(Tile tile)
    {
        List<Integer> adjacents = new ArrayList<>();
        for (int s = 0; s < 4; s++)
        {
            int sig = tile.getSignature(s, 0);
            List<TilePointer> p1 = tileLookupBySignature.get(sig);

            long found = 0;
            if (p1 != null)
            {
                found = p1.stream().filter(pa -> (pa.tile != tile)).count();
            }

            adjacents.add((int) found);
        }
        return adjacents;
    }

    private void rotateCorner(TilePointer tp)
    {
        List<Integer> adjacent = getAdjacentTileCounts(tp.tile);
        // adjust rotation
        if (adjacent.get(0) == 0 && adjacent.get(1) == 0)
        {
            tp.rotation = 3;
        }
        else if (adjacent.get(1) == 0 && adjacent.get(2) == 0)
        {
            tp.rotation = 2;
        }
        else if (adjacent.get(2) == 0 && adjacent.get(3) == 0)
        {
            tp.rotation = 1;
        }
    }

    // TilePointer findTile(int searchSignature) {
    // tileLookupBySignature.get
    // }

    public List<List<TilePointer>> solve()
    {
        int gridsize = (int) Math.sqrt(tiles.size());
        System.out.println("** " + tiles.size() + " " + gridsize);
        List<List<TilePointer>> grid = new ArrayList<>();

        List<Tile> remainingCorners = new ArrayList<>(corners);
        List<Tile> remainingBorders = new ArrayList<>(borders);

        List<Tile> remainingOthers = new ArrayList<>(tiles);
        remainingOthers.removeAll(corners);
        remainingOthers.removeAll(borders);

        // Step 1: find top row
        // left top corner
        List<TilePointer> row = new ArrayList<>();
        grid.add(row);
        TilePointer tp = new TilePointer();
        tp.tile = remainingCorners.remove(0);
        rotateCorner(tp);
        System.out.println("# " + tp.tile.getId() + " " + tp.rotation);
        row.add(tp);
        // filler
        int searchSignature = Day20.reverseSignature(tp.tile.getSignature(1, tp.rotation));
        for (int j = 1; j <= gridsize - 2; j++)
        {
            tp = findTileWithSignature(remainingBorders, searchSignature, 3);
            row.add(tp);
            System.out.println("= " + tp.tile.getId() + " " + tp.rotation);

            searchSignature = Day20.reverseSignature(tp.tile.getSignature(1, tp.rotation));
            remainingBorders.remove(tp.tile);
        }

        // right top corner
        tp = findTileWithSignature(remainingCorners, searchSignature, 3);
        remainingCorners.remove(tp.tile);
        System.out.println("# " + tp.tile.getId() + " " + tp.rotation);
        row.add(tp);

        // Step 2: picture body
        TilePointer topTile;
        for (int i = 1; i <= gridsize - 2; i++)
        {
            row = new ArrayList<>();
            grid.add(row);
            topTile = grid.get(i - 1).get(0);
            searchSignature = Day20.reverseSignature(topTile.tile.getSignature(2, topTile.rotation));
            // left border
            tp = findTileWithSignature(remainingBorders, searchSignature, 0);
            row.add(tp);
            System.out.println("= " + tp.tile.getId() + " " + tp.rotation);
            remainingBorders.remove(tp.tile);
            searchSignature = Day20.reverseSignature(tp.tile.getSignature(1, tp.rotation));
            // filler
            for (int j = 1; j <= gridsize - 2; j++)
            {
                System.out.println(".. " + i + ", " + j);
                tp = findTileWithSignature(remainingOthers, searchSignature, 3);
                row.add(tp);
                System.out.println(". " + tp.tile.getId() + " " + tp.rotation);
                searchSignature = Day20.reverseSignature(tp.tile.getSignature(1, tp.rotation));
                remainingOthers.remove(tp.tile);
            }
            // right border
            tp = findTileWithSignature(remainingBorders, searchSignature, 3);
            remainingBorders.remove(tp.tile);
            System.out.println("= " + tp.tile.getId() + " " + tp.rotation);
            row.add(tp);
        }

        // Step 3: bottom row
        topTile = row.get(0);
        row = new ArrayList<>();
        grid.add(row);

        searchSignature = Day20.reverseSignature(topTile.tile.getSignature(2, topTile.rotation));
        // left bottom corner
        tp = findTileWithSignature(remainingCorners, searchSignature, 0);
        row.add(tp);
        System.out.println("# " + tp.tile.getId() + " " + tp.rotation);
        remainingCorners.remove(tp.tile);
        searchSignature = Day20.reverseSignature(tp.tile.getSignature(1, tp.rotation));
        // filler
        for (int j = 1; j <= gridsize - 2; j++)
        {
            tp = findTileWithSignature(remainingBorders, searchSignature, 3);
            row.add(tp);
            searchSignature = Day20.reverseSignature(tp.tile.getSignature(1, tp.rotation));
            remainingBorders.remove(tp.tile);
            System.out.println("= " + tp.tile.getId() + " " + tp.rotation);
        }

        // right top corner
        tp = findTileWithSignature(remainingCorners, searchSignature, 3);
        remainingCorners.remove(tp.tile);
        row.add(tp);
        System.out.println("# " + tp.tile.getId() + " " + tp.rotation);
        // return result
        return grid;
    }

    private TilePointer findTileWithSignature(List<Tile> tiles, int signature, int side)
    {
        System.out.println("-> " + signature + " " + Integer.toBinaryString(signature));
        List<TilePointer> pointers = tileLookupBySignature.get(signature);
        pointers = pointers.stream().filter(p -> p.side == side).filter(p -> tiles.contains(p.tile))
                .collect(Collectors.toList());

        if (pointers.size() > 1)
        {
            System.out.println("DANG!");
        }
        return pointers.get(0);
    }

}
