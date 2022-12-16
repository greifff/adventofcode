package adventofcode.aoc2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import adventofcode.util.IOUtil;
import adventofcode.util.Patterns;

public class Day22
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2016/day22.data");
        input.remove(0);
        input.remove(0);
        Map<Integer, Node> nodes = new HashMap<>();
        input.stream().map(s -> new Node(s)).forEach(node -> {
            nodes.put(node.x * 100 + node.y, node);
        });

        int maxx = 34;
        int maxy = 28;

        part1(nodes, maxx, maxy);
        part2(nodes, maxx, maxy);
    }

    private static void part1(Map<Integer, Node> nodes, int maxx, int maxy)
    {
        int count = 0;

        List<Node> nodes1 = new ArrayList<>(nodes.values());

        for (int i = 0; i < nodes1.size() - 1; i++)
        {
            for (int j = i + 1; j < nodes1.size(); j++)
            {
                Node a = nodes1.get(i);
                Node b = nodes1.get(j);
                if ((a.used > 0 && b.avail >= a.used) || (b.used > 0 && a.avail >= b.used))
                {
                    count++;
                }
            }
        }

        System.out.println("part1: " + count);
    }

    private static boolean isValidMove(Map<Integer, Node> nodes, int x1, int y1, int x2, int y2)
    {
        Node node1 = get(nodes, x1, y1);
        Node node2 = get(nodes, x2, y2);
        return (node1.avail >= node2.used);
    }

    private static Map<Integer, Node> move(Map<Integer, Node> nodes, int x1, int y1, int x2, int y2)
    {
        Node node1 = get(nodes, x1, y1);
        Node node2 = get(nodes, x2, y2);
        Map<Integer, Node> nodes2 = new HashMap<>(nodes);

        nodes2.put(x1 * 100 + y1, new Node(node1.x, node1.y, node1.size, node1.used + node2.used));
        nodes2.put(x2 * 100 + y2, new Node(node2.x, node2.y, node2.size, 0));
        return nodes2;
    }

    private static void part2(Map<Integer, Node> nodes, int maxx, int maxy)
    {

        // Node n1 = get(nodes, 34, 0); // top-right node, 66T payload

        for (int y = 0; y <= maxy; y++)
        {
            for (int x = 0; x <= maxx; x++)
            {
                Node n2 = get(nodes, x, y);
                // System.out.print(n2 == null ? "#" : n2.used == 0 ? "_" : '.');
                System.out.print(" " + n2.used + "/" + n2.size);
                // if (n2.used == 0)
                // {
                // System.out.println("? " + x + "," + y);
                // }
            }
            System.out.println();
        }

        int moves = 0;
        System.out.println("# " + get(nodes, 8, 28).used);
        // move 8,28 to 1,28 to 1,22 (to circum vent big wall) -> 7+6=13

        // move 1,22 to 1,0 to 33,0 -> 22+32=54

        // move 34,0 to 0,0 -> 33*5+1 = 166

        // sum: 166+54+13= 233

        // move 8,28 to 33,28
        {
            int y = 28;
            for (int x = 8; x < 33; x++)
            {
                // System.out.println("# " + get(nodes, x, y).avail);
                if (isValidMove(nodes, x, y, x + 1, y))
                {
                    nodes = move(nodes, x, y, x + 1, y);
                    moves++;
                }
                else
                {
                    System.out.println("DANG!");
                    return;
                }
            }
        }
        System.out.println("# " + get(nodes, 33, 28).used);
        System.out.println("* " + moves);
        // move 33,28 to 33,0
        {

            int x = 33;
            for (int y = 28; y > 0; y--)
            {
                // System.out.println("# " + get(nodes, x, y).avail);
                if (isValidMove(nodes, x, y, x, y - 1))
                {
                    nodes = move(nodes, x, y, x, y - 1);
                    moves++;
                }
                else
                {
                    System.out.println("DANG! " + x + "," + y);
                }
            }
        }

        System.out.println("* " + moves);
        // 25+28=53 moves Leerstelle nach (33,0)
        /// 1 move vip 34,0->33,0
        /// 4 moves Leerstelle nach 32,0
        // -> 53 prep + 32*5 + 1 = 175

        // 7 moves Leerstelle nach (34,1)
        // 1 move vip 34,0->34,1
        // 2 moves Leerstelle nach 33,1
        // 1 move vip 34,1->33,1

    }

    private static Node get(Map<Integer, Node> nodes, int x, int y)
    {
        return nodes.get(x * 100 + y);
    }

    static class Node
    {
        int x;
        int y;
        int size;
        int used;
        int avail;

        Node(int x, int y, int size, int used)
        {
            this.x = x;
            this.y = y;
            this.size = size;
            this.used = used;
            avail = size - used;
        }

        Node(String data)
        {
            Matcher m = Patterns.number.matcher(data);
            m.find();
            x = Integer.parseInt(m.group());
            m.find();
            y = Integer.parseInt(m.group());
            m.find();
            size = Integer.parseInt(m.group());
            m.find();
            used = Integer.parseInt(m.group());
            m.find();
            avail = Integer.parseInt(m.group());
        }
    }
}
