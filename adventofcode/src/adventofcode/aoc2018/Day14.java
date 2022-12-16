package adventofcode.aoc2018;

import java.util.ArrayList;
import java.util.List;

public class Day14
{

    public static void main(String[] args)
    {

        part1(9);
        part1(5);
        part1(18);
        part1(2018);

        part1(209231);

        part2("51589");
        part2("01245");
        part2("92510");
        part2("59414");
        part2("209231");
    }

    private static void part1(int recipeCount)
    {
        List<Integer> recipes = new ArrayList<>();
        int[] elves =
        { 0, 1 };
        recipes.add(3);
        recipes.add(7);

        do
        {
            int newRecipe = recipes.get(elves[0]) + recipes.get(elves[1]);
            if (newRecipe >= 10)
            {
                recipes.add(1);
                recipes.add(newRecipe - 10);
            }
            else
            {
                recipes.add(newRecipe);
            }

            for (int i = 0; i < 2; i++)
            {
                elves[i] = (elves[i] + recipes.get(elves[i]) + 1) % recipes.size();
            }
        }
        while (recipes.size() < recipeCount + 10);

        String r = "";
        for (int i = recipeCount; i < recipeCount + 10; i++)
        {
            r += recipes.get(i);
        }
        System.out.println("part1: " + r);
    }

    private static void part2(String pattern)
    {

        int[] elves =
        { 0, 1 };

        StringBuilder recipes = new StringBuilder("37");

        int k = -1;
        int r = 0;
        do
        {
            for (int s = 0; s < 1000; s++)
            {
                int newRecipe = Integer.parseInt("" + recipes.charAt(elves[0]))
                        + Integer.parseInt("" + recipes.charAt(elves[1]));
                recipes.append("" + newRecipe);

                for (int i = 0; i < 2; i++)
                {
                    elves[i] = (elves[i] + Integer.parseInt("" + recipes.charAt(elves[i])) + 1) % recipes.length();
                }
            }
            k = recipes.indexOf(pattern);
            if (r == 100)
            {
                System.out.println(".");
                r = 0;
            }
            else
            {
                System.out.print(".");
                r++;
            }
        }
        while (k == -1);
        System.out.println();
        System.out.println("part2: " + k);
    }
}
