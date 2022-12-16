package adventofcode.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class day16
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2020/day16.data");

        int s = input.indexOf("");
        int t = input.lastIndexOf("");

        List<TicketProperty> properties = input.subList(0, s).stream().map(p -> {
            String[] f = p.split(":");
            TicketProperty tp = new TicketProperty();
            tp.name = f[0];
            List<Integer> b = Arrays.asList(f[1].trim().replace(" or ", "-").split("-")).parallelStream()
                    .map(b1 -> Integer.parseInt(b1)).collect(Collectors.toList());
            tp.low1 = b.get(0);
            tp.high1 = b.get(1);
            tp.low2 = b.get(2);
            tp.high2 = b.get(3);
            return tp;
        }).collect(Collectors.toList());

        List<Integer> yourTicket = readTicket(input.get(t - 1));
        List<List<Integer>> otherTickets = input.subList(t + 2, input.size()).stream().map(ticket -> readTicket(ticket))
                .collect(Collectors.toList());
        part1(otherTickets);
        part2(properties, yourTicket, otherTickets);
    }

    private static void part2(List<TicketProperty> properties, List<Integer> ownTicket,
            List<List<Integer>> otherTickets)
    {

        List<List<Integer>> filteredTickets = otherTickets.stream().filter(
                it -> it.stream().map(f -> (boolean) (f >= 25 && f <= 974)).reduce((a, b) -> a && b).orElse(false))
                .collect(Collectors.toList());

        List<Set<Integer>> fieldMapping = new ArrayList<>();
        for (int i = 0; i < ownTicket.size(); i++)
        {
            fieldMapping.add(possibleFields(properties, ownTicket.get(i)));
        }

        for (List<Integer> ticket : filteredTickets)
        {
            for (int i = 0; i < fieldMapping.size(); i++)
            {
                fieldMapping.get(i).retainAll(possibleFields(properties, ticket.get(i)));
            }
        }

        for (int i = 0; i < fieldMapping.size(); i++)
        {
            System.out.println(
                    "## " + i + " " + fieldMapping.get(i).stream().map(x -> "" + x).reduce((x1, x2) -> x1 + "," + x2));
        }

        // eliminate duplicates
        Map<Integer, Integer> mapping = new HashMap<>();
        while (mapping.size() < properties.size())
        {
            for (int i = 0; i < fieldMapping.size(); i++)
            {
                Set<Integer> fm1 = fieldMapping.get(i);
                if (fm1.size() == 1)
                {
                    int k = fm1.iterator().next();
                    mapping.put(i, k);
                    System.out.println("" + i + "->" + k);
                    fm1.clear();
                }
            }
            for (int i = 0; i < fieldMapping.size(); i++)
            {
                Set<Integer> fm1 = fieldMapping.get(i);
                fm1.removeAll(mapping.values());
            }
        }

        long result = 1;
        for (int i = 0; i < mapping.size(); i++)
        {
            int m = mapping.get(i);
            if (m < 6)
            {
                result *= ownTicket.get(i);
            }
        }
        System.out.println("part2: " + result);
    }

    private static Set<Integer> possibleFields(List<TicketProperty> properties, int value)
    {
        Set<Integer> matching = new HashSet<>();
        for (int i = 0; i < properties.size(); i++)
        {
            if (properties.get(i).isValidValue(value))
            {
                matching.add(i);
            }
        }
        return matching;
    }

    private static void part1(List<List<Integer>> otherTickets)
    {

        int invalidValueSum = 0;

        for (List<Integer> ticket : otherTickets)
        {
            for (int field : ticket)
            {
                if (field < 25 || field > 974)
                {
                    invalidValueSum += field;
                }
            }
        }

        System.out.println("part1: " + invalidValueSum);
    }

    private static final class TicketProperty
    {
        @SuppressWarnings("unused")
        String name;

        int low1;
        int high1;
        int low2;
        int high2;

        boolean isValidValue(int value)
        {
            return (low1 <= value && value <= high1) || (low2 <= value && value <= high2);
        }
    }

    private static List<Integer> readTicket(String ticket)
    {
        return Arrays.asList(ticket.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
    }
}
