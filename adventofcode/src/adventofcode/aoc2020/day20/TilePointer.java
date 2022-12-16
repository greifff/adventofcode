package adventofcode.aoc2020.day20;

import java.util.List;

class TilePointer
{
    Tile tile;
    int rotation;
    int side;

    TilePointer()
    {

    }

    TilePointer(Tile tile, int leftSignature, int topSignature, List<Integer> adjacent)
    {
        this.tile = tile;
        if (topSignature == -1)
        {
            int foundBorder = adjacent.indexOf(0);
            rotation = 4 - foundBorder + 0;
        }
        else
        {
            for (int i = 0; i < 4; i++)
            {
                if (tile.getSignature(i, 0) == topSignature)
                {
                    rotation = 4 - i;
                }
            }
        }

        // decide to flip
        if (leftSignature == -1)
        {
            if (adjacent.get((4 + rotation - 1) & 3) != 0)
            {
                rotation += 4;
            }
        }
        else
        {
            if (tile.getSignature(3, rotation) != leftSignature)
            {
                rotation += 4;
            }
        }
    }
}