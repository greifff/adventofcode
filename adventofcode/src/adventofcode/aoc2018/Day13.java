package adventofcode.aoc2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day13
{

    public static void main(String[] args)
    {
        List<String> test1 = IOUtil.readFile("2018/day13.test");
        List<String> test2 = IOUtil.readFile("2018/day13.test2");
        List<String> input = IOUtil.readFile("2018/day13.data");

        List<Cart> testCarts = parseCarts(test1);
        List<Cart> testCarts2 = parseCarts(test2);
        List<Cart> carts = parseCarts(input);
        List<Cart> carts2 = parseCarts(input);// for second run
        clean(test1);
        clean(test2);
        clean(input);

        part1(test1, testCarts);
        part1(input, carts);
        part2(test2, testCarts2);
        part2(input, carts2);
    }

    private static void part2(List<String> map, List<Cart> carts)
    {
        do
        {
            int i = 0;
            while (i < carts.size())
            {
                Cart cart = carts.get(i);
                if (!cart.crashed)
                {
                    cart.move(map);
                    Cart crashedInto = hasCrashed(cart, carts);
                    if (crashedInto != null)
                    {
                        cart.crashed = true;
                        crashedInto.crashed = true;
                    }
                }
                i++;
            }

            carts = carts.stream().filter(c -> !c.crashed).collect(Collectors.toList());

            Collections.sort(carts, (c1, c2) -> {
                int delta = c1.y - c2.y;
                if (delta != 0)
                {
                    return delta;
                }
                return c1.x - c2.x;
            });
        }
        while (carts.size() > 1);
        System.out.println("part2: " + carts.get(0).x + "," + carts.get(0).y);
    }

    private static void part1(List<String> map, List<Cart> carts)
    {
        do
        {
            for (Cart cart : carts)
            {
                cart.move(map);
                if (hasCrashed(cart, carts) != null)
                {
                    System.out.println("part1: " + cart.x + "," + cart.y);
                    return;
                }
            }
            Collections.sort(carts, (c1, c2) -> {
                int delta = c1.y - c2.y;
                if (delta != 0)
                {
                    return delta;
                }
                return c1.x - c2.x;
            });
        }
        while (true);
    }

    private static void clean(List<String> input)
    {
        for (int i = 0; i < input.size(); i++)
        {
            String a = input.get(i);
            a = a.replace('^', '|').replace('v', '|').replace('<', '-').replace('>', '-');
            input.set(i, a);
        }

    }

    private static List<Cart> parseCarts(List<String> input)
    {
        List<Cart> carts = new ArrayList<>();
        for (int y = 0; y < input.size(); y++)
        {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++)
            {
                switch (line.charAt(x))
                {
                    case '^':
                        carts.add(new Cart(x, y, 0));
                        break;
                    case '>':
                        carts.add(new Cart(x, y, 1));
                        break;
                    case '<':
                        carts.add(new Cart(x, y, 3));
                        break;
                    case 'v':
                        carts.add(new Cart(x, y, 2));
                        break;
                }
            }
        }
        return carts;
    }

    private static Cart hasCrashed(Cart cart, List<Cart> carts)
    {
        List<Cart> carts2 = carts.stream().filter(c -> c != cart).filter(c -> cart.x == c.x && cart.y == c.y)
                .collect(Collectors.toList());

        return carts2.isEmpty() ? null : carts2.get(0);
    }

    static class Cart
    {
        int x;
        int y;
        int direction;
        private int intersectionCounter;
        boolean crashed;

        Cart(int x, int y, int direction)
        {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        private void intersection()
        {
            intersectionCounter = (intersectionCounter + 1) % 3;
            switch (intersectionCounter)
            {
                case 1:
                    direction = (direction + 3) % 4;
                    break;
                case 0:
                    direction = (direction + 1) % 4;
                    break;
            }
        }

        private void curveA()
        {
            // direction = (direction + 1) % 4;
            switch (direction)
            {
                case 0:
                    direction = 3;
                    break;
                case 1:
                    direction = 2;
                    break;
                case 2:
                    direction = 1;
                    break;
                case 3:
                    direction = 0;
                    break;
            }
        }

        private void curveB()
        {
            // direction = (direction + 3) % 4;
            switch (direction)
            {
                case 0:
                    direction = 1;
                    break;
                case 1:
                    direction = 0;
                    break;
                case 2:
                    direction = 3;
                    break;
                case 3:
                    direction = 2;
                    break;
            }
        }

        void move(List<String> board)
        {
            // System.out.println("? " + x + "," + y + " " + direction);
            switch (direction)
            {
                case 0:
                    y--;
                    break;
                case 1:
                    x++;
                    break;
                case 2:
                    y++;
                    break;
                case 3:
                    x--;
                    break;
            }

            switch (board.get(y).charAt(x))
            {
                case '\\':
                    curveA();
                    break;
                case '/':
                    curveB();
                    break;
                case '+':
                    intersection();
                    break;
            }
            // System.out.println("! " + x + "," + y + " " + direction);
        }
    }

}
