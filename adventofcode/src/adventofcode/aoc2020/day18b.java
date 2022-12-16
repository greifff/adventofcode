package adventofcode.aoc2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import adventofcode.util.IOUtil;

public class day18b
{
    enum NodeType
    {
        BRACKET,
        VALUE,
        MULTIPLY,
        ADD
    }

    static class Node
    {
        NodeType typ;
        List<Node> children = new ArrayList<>();
        long value;

        long evaluate()
        {
            switch (typ)
            {
                case BRACKET:
                    return children.get(0).evaluate();
                default:
                case VALUE:
                    return value;
                case MULTIPLY:
                    long m = children.stream().map(c -> c.evaluate()).reduce((a, b) -> a * b).orElse(0L);
                    // System.out.println("** " + m);
                    return m;
                case ADD:
                    long l = children.stream().map(c -> c.evaluate()).reduce((a, b) -> a + b).orElse(0L);
                    // System.out.println("++ " + l);
                    return l;
            }
        }

        @Override
        public String toString()
        {
            switch (typ)
            {
                case BRACKET:
                    return "(" + children.get(0).toString() + ")";
                default:
                case VALUE:
                    return " " + value + " ";
                case MULTIPLY:
                    return "**[" + children.stream().map(c -> c.toString()).reduce((a, b) -> a + b).orElse("") + "]";
                case ADD:
                    return "++[" + children.stream().map(c -> c.toString()).reduce((a, b) -> a + b).orElse("") + "]";
            }
        }
    }

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2020/day18.data");

        // System.out.println(parse("1 + 2 * 3 + 4 * 5 + 6").evaluate());
        // System.out.println(parse("1 + (2 * 3) + (4 * (5 + 6))").evaluate());
        // System.out.println(parse("2 * 3 + (4 * 5)").evaluate());
        // System.out.println(parse("5 + (8 * 3 + 9 + 3 * 4 * 3)").evaluate());
        // System.out.println(parse("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))").evaluate());
        // System.out.println(parse("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2").evaluate());

        part2(input);
    }

    private static void part2(List<String> input)
    {

        long result = input.stream().map(s -> parse(s).evaluate()).reduce((a, b) -> a + b).orElse(0L);

        System.out.println("part2: " + result);
    }

    static Node parse(String line)
    {
        Stack<Node> path = new Stack<>();

        StringTokenizer st = new StringTokenizer(line, " ()", true);

        Node currentNode = new Node();
        currentNode.typ = NodeType.MULTIPLY;
        Node head = currentNode;

        while (st.hasMoreTokens())
        {
            String t = st.nextToken();
            Node newNode = new Node();
            switch (t)
            {
                case " ":
                    // do nothing
                    continue;
                case "(":
                    newNode.typ = NodeType.BRACKET;
                    break;
                case ")":
                    // close BRACKET - go tree up to the op the current bracket is involved in
                    Node top = path.pop();
                    while (top.typ != NodeType.BRACKET)
                    {
                        top = path.pop();
                    }
                    currentNode = path.pop();
                    continue;
                case "*":
                    newNode.typ = NodeType.MULTIPLY;
                    break;
                case "+":
                    newNode.typ = NodeType.ADD;
                    break;
                default:
                    long v = Long.parseLong(t);
                    newNode.value = v;
                    newNode.typ = NodeType.VALUE;
                    break;
            }

            switch (currentNode.typ)
            {
                case BRACKET:
                {
                    switch (newNode.typ)
                    {
                        case VALUE:
                        case BRACKET:
                            path.push(currentNode);
                            currentNode.children.add(newNode);
                            currentNode = newNode;
                            break;
                        case ADD:
                        case MULTIPLY:
                            Node child = currentNode.children.remove(0);
                            newNode.children.add(child);
                            currentNode.children.add(newNode);
                            path.push(currentNode);
                            currentNode = newNode;
                            break;
                    }

                }
                    break;
                case ADD:
                    switch (newNode.typ)
                    {
                        case ADD: // merge
                            break;
                        case VALUE:
                            currentNode.children.add(newNode);
                            break;
                        case BRACKET:
                            currentNode.children.add(newNode);
                            path.push(currentNode);
                            currentNode = newNode;
                            break;
                        case MULTIPLY:
                            Node previous = path.peek();
                            if (previous.typ == NodeType.MULTIPLY)
                            {
                                currentNode = previous;
                                path.pop();
                            }
                            else
                            { // BRACKET
                                newNode.children.add(currentNode);
                                previous.children.remove(currentNode);
                                previous.children.add(newNode);
                                currentNode = newNode;
                            }
                            break;
                    }
                    break;
                case MULTIPLY:
                    switch (newNode.typ)
                    {
                        case MULTIPLY: // merge
                            break;
                        case VALUE:
                            currentNode.children.add(newNode);
                            break;
                        case BRACKET:
                            currentNode.children.add(newNode);
                            path.push(currentNode);
                            currentNode = newNode;
                            break;
                        case ADD:
                            Node child = currentNode.children.remove(currentNode.children.size() - 1);
                            newNode.children.add(child);
                            currentNode.children.add(newNode);
                            path.push(currentNode);
                            currentNode = newNode;
                            break;
                    }
                    break;
                case VALUE:
                {
                    Node top = path.peek();
                    newNode.children.add(currentNode);
                    top.children.remove(currentNode);
                    top.children.add(newNode);
                    currentNode = newNode;
                }
                    break;
            }

        }
        return head;
    }

}
