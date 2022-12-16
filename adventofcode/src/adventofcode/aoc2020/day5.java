package adventofcode.aoc2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class day5
{

    public static void main(String[] args)
    {
        List<String> boardingPass = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("2020/day5.data")), StandardCharsets.UTF_8)))
        {
            String line = reader.readLine();
            while (line != null)
            {
                boardingPass.add(line);
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<seat> seats = boardingPass.stream().map(p -> convert(p)).collect(Collectors.toList());

        // part 1
        Optional<Integer> highestID = seats.stream().map(s -> s.getid()).reduce((a, b) -> Math.max(a, b));

        System.out.println("part1: " + highestID.orElse(-1));

        // part 2

        List<Integer> ids = seats.stream().map(s -> s.getid()).collect(Collectors.toList());

        for (int s = 0; s < 932; s++)
        {
            if (!ids.contains(s))
            {
                System.out.println("part2: " + s);
            }
        }
    }

    private static seat convert(String boardingPass)
    {
        String row1 = boardingPass.substring(0, 7);
        String col1 = boardingPass.substring(7);

        int row = Integer.parseInt(row1.replace('F', '0').replace('B', '1'), 2);
        int col = Integer.parseInt(col1.replace('L', '0').replace('R', '1'), 2);

        return new seat(row, col);
    }

    private static class seat
    {

        int row;
        int column;

        seat(int row, int column)
        {
            this.row = row;
            this.column = column;
        }

        int getid()
        {
            return row * 8 + column;
        }
    }
}
