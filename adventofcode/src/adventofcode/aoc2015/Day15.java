package adventofcode.aoc2015;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;
import adventofcode.util.Patterns;

public class Day15
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day15.data");
        List<Ingredient> ingredients = input.stream().map(s -> new Ingredient(s)).collect(Collectors.toList());

        part1(ingredients, Integer.MAX_VALUE);
        part1(ingredients, 500);
    }

    private static void part1(List<Ingredient> ingredients, int maxCalories)
    {
        // int teaspoons = 100;
        List<Integer> perIngredient = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++)
        {
            perIngredient.add(0);
        }
        long highscore = 0;

        IngredientIterator it = new IngredientIterator(ingredients.size());
        int[] configuration = it.next();
        while (configuration != null)
        {
            int capacity = 0;
            int durability = 0;
            int flavor = 0;
            int texture = 0;
            int calories = 0;
            for (int i = 0; i < ingredients.size(); i++)
            {
                int n = configuration[i];
                Ingredient ing = ingredients.get(i);
                capacity += n * ing.capacity;
                durability += n * ing.durability;
                flavor += n * ing.flavor;
                texture += n * ing.texture;
                calories += n * ing.calories;
            }
            if (calories <= maxCalories)
            {
                if (capacity < 0)
                {
                    capacity = 0;
                }
                if (durability < 0)
                {
                    durability = 0;
                }
                if (flavor < 0)
                {
                    flavor = 0;
                }
                if (texture < 0)
                {
                    texture = 0;
                }
                long score = capacity * durability * flavor * texture;// * calories;
                // System.out.println("## " + score);
                highscore = Math.max(highscore, score);
            }
            configuration = it.next();
        }

        System.out.println("part1: " + highscore);
    }

    static class IngredientIterator implements Iterator<int[]>
    {

        int maxValue;
        int value = 0;
        int diversity;

        IngredientIterator(int diversity)
        {
            maxValue = (int) Math.pow(100, diversity);
            this.diversity = diversity;
        }

        @Override
        public boolean hasNext()
        {
            return value < maxValue;
        }

        @Override
        public int[] next()
        {
            while (value < maxValue && !isValid())
            {
                value++;
            }
            if (value >= maxValue)
            {
                return null;
            }

            int[] result = new int[diversity];
            int copy = value;
            for (int i = 0; i < diversity; i++)
            {
                result[i] = copy % 100;
                copy /= 100;
            }
            value++;
            return result;
        }

        private boolean isValid()
        {
            int sum = 0;
            int copy = value;
            for (int i = 0; i < diversity; i++)
            {
                sum += copy % 100;
                copy /= 100;
            }
            return sum == 100;
        }

    }

    static class Ingredient
    {
        String name;
        int capacity;
        int durability;
        int flavor;
        int texture;
        int calories;

        Ingredient(String in)
        {
            name = in.split(":").clone()[0];
            Matcher matcher = Patterns.number.matcher(in);
            matcher.find();
            capacity = Integer.parseInt(matcher.group());
            matcher.find();
            durability = Integer.parseInt(matcher.group());
            matcher.find();
            flavor = Integer.parseInt(matcher.group());
            matcher.find();
            texture = Integer.parseInt(matcher.group());
            matcher.find();
            calories = Integer.parseInt(matcher.group());
        }
    }
}
