package adventofcode.aoc2020;

import java.util.List;

import adventofcode.util.IOUtil;

public class day11
{
    static int maxX;
    static int maxY;

    enum seat
    {
        floor,
        empty,
        occupied
    }

    public static void main(String[] args)
    {
        part1();
        part2();
    }

    static seat[][] load()
    {
        List<String> input = IOUtil.readFile("2020/day11.data");

        maxX = input.get(0).length() - 1;
        maxY = input.size() - 1;

        seat[][] seats = new seat[maxX + 1][maxY + 1];

        // initial fill
        for (int y = 0; y <= maxY; y++)
        {
            String line = input.get(y);
            for (int x = 0; x <= maxX; x++)
            {
                seats[x][y] = line.charAt(x) == '.' ? seat.floor : seat.empty;
            }
        }

        return seats;
    }

    public static void part1()
    {

        seat[][] seats = load();

        // part1
        seat[][] seats1 = new seat[maxX + 1][maxY + 1];
        seat[][] seats2 = new seat[maxX + 1][maxY + 1];

        System.arraycopy(seats, 0, seats1, 0, seats.length);

        int occupied = 0;
        int previously = -1;

        int round = 1;

        while (occupied != previously)
        {
            // place persons into seats2 based on seats1
            place(seats1, seats2);

            // switch seats1 and seats2
            seat[][] seats3 = seats2;
            seats2 = seats1;
            seats1 = seats3;

            // count
            previously = occupied;
            occupied = count(seats1);

            System.out.println("round " + round + " occupied " + occupied);

            round++;
        }

        System.out.println("part1: " + occupied);
    }

    public static void part2()
    {
        seat[][] seats = load();

        // part2

        seat[][] seats1 = new seat[maxX + 1][maxY + 1];
        seat[][] seats2 = new seat[maxX + 1][maxY + 1];

        System.arraycopy(seats, 0, seats1, 0, seats.length);

        int occupied = 0;
        int previously = -1;

        int round = 1;

        while (occupied != previously)
        {
            // place persons into seats2 based on seats1
            place2(seats1, seats2);

            // switch seats1 and seats2
            seat[][] seats3 = seats2;
            seats2 = seats1;
            seats1 = seats3;

            // count
            previously = occupied;
            occupied = count(seats1);

            System.out.println("round " + round + " occupied " + occupied);

            round++;
        }

        System.out.println("part2: " + occupied);
    }

    private static int count(seat[][] seats1)
    {
        int c = 0;
        for (int x = 0; x <= maxX; x++)
        {
            for (int y = 0; y <= maxY; y++)
            {
                if (seats1[x][y] == seat.occupied)
                {
                    c++;
                }
            }
        }
        return c;
    }

    private static void place(seat[][] seats1, seat[][] seats2)
    {
        for (int x = 0; x <= maxX; x++)
        {
            for (int y = 0; y <= maxY; y++)
            {
                if (seats1[x][y] == seat.floor)
                {
                    seats2[x][y] = seat.floor;
                    continue;
                }

                int neighbors = isOccupied(seats1, x - 1, y - 1) + isOccupied(seats1, x - 1, y)
                        + isOccupied(seats1, x - 1, y + 1) + isOccupied(seats1, x, y - 1) + isOccupied(seats1, x, y + 1)
                        + isOccupied(seats1, x + 1, y - 1) + isOccupied(seats1, x + 1, y)
                        + isOccupied(seats1, x + 1, y + 1);

                if (seats1[x][y] == seat.occupied && neighbors >= 4)
                {
                    seats2[x][y] = seat.empty;
                }
                else if (seats1[x][y] == seat.empty && neighbors == 0)
                {
                    seats2[x][y] = seat.occupied;
                }
                else
                {
                    seats2[x][y] = seats1[x][y];
                }
            }
        }
    }

    private static int isOccupied(seat[][] seats1, int x, int y)
    {
        if (x == -1 || x == maxX + 1 || y == -1 || y == maxY + 1)
        {
            return 0; // wall
        }
        return seats1[x][y] == seat.occupied ? 1 : 0;
    }

    private static int scanOccupied(seat[][] seats1, int x, int y, int dx, int dy)
    {
        int ax = x;
        int ay = y;
        while (true)
        {
            ax += dx;
            ay += dy;
            if (ax < 0 || ax > maxX || ay < 0 || ay > maxY)
            {
                return 0; // wall
            }

            switch (seats1[ax][ay])
            {
                case occupied:
                    return 1;
                case empty:
                    return 0;
                default:
                    break;
            }
        }
    }

    private static void place2(seat[][] seats1, seat[][] seats2)
    {
        for (int x = 0; x <= maxX; x++)
        {
            for (int y = 0; y <= maxY; y++)
            {
                if (seats1[x][y] == seat.floor)
                {
                    seats2[x][y] = seat.floor;
                    continue;
                }

                int neighbors = scanOccupied(seats1, x, y, -1, -1) + scanOccupied(seats1, x, y, -1, 0)
                        + scanOccupied(seats1, x, y, -1, 1) + scanOccupied(seats1, x, y, 0, -1)
                        + scanOccupied(seats1, x, y, 0, 1) + scanOccupied(seats1, x, y, 1, -1)
                        + scanOccupied(seats1, x, y, 1, 0) + scanOccupied(seats1, x, y, 1, 1);

                if (seats1[x][y] == seat.occupied && neighbors >= 5)
                {
                    seats2[x][y] = seat.empty;
                }
                else if (seats1[x][y] == seat.empty && neighbors == 0)
                {
                    seats2[x][y] = seat.occupied;
                }
                else
                {
                    seats2[x][y] = seats1[x][y];
                }
            }
        }
    }

}
