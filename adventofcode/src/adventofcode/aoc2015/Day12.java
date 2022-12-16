package adventofcode.aoc2015;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import adventofcode.util.IOUtil;

public class Day12
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day12.data");

        part1("[1,2,3]");
        part1("{\"a\":2,\"b\":4}");
        part1("[[[3]]]");
        part1("{\"a\":{\"b\":4},\"c\":-1}");
        part1("{\"a\":[-1,1]}");
        part1("[-1,{\"a\":1}]");
        part1("[]");
        part1("{}");
        part1(input.get(0));
        part2("[1,2,3]");
        part2("[1,{\"c\":\"red\",\"b\":2},3]");
        part2("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}");
        part2("[1,\"red\",5]");
        part2(input.get(0));
    }

    private static void part1(String in)
    {
        // TODO Auto-generated method stub
        Pattern numeric = Pattern.compile("-?[0-9]+");

        Matcher matcher = numeric.matcher(in);

        long sum = 0;

        while (matcher.find())
        {
            sum += Long.parseLong(in.substring(matcher.start(), matcher.end()));
        }

        System.out.println("part1: " + sum);
    }

    private static void part2(String in)
    {
        if (in.charAt(0) == '[')
        {
            JSONArray a = new JSONArray(new JSONTokener(in));
            System.out.println("part2: " + getSumOf(a));
        }
        else
        {
            JSONObject a = new JSONObject(new JSONTokener(in));
            System.out.println("part2: " + getSumOf(a));
        }
    }

    private static long getSumOf(JSONArray a)
    {
        long sum = 0;
        for (Object o : a)
        {
            if (o instanceof JSONArray)
            {
                sum += getSumOf((JSONArray) o);
            }
            else if (o instanceof JSONObject)
            {
                sum += getSumOf((JSONObject) o);
            }
            else if (o instanceof Number)
            {
                sum += ((Number) o).longValue();
            }
        }
        return sum;
    }

    @SuppressWarnings("unchecked")
    private static long getSumOf(List<Object> a)
    {
        long sum = 0;
        for (Object o : a)
        {
            if (o instanceof JSONArray)
            {
                sum += getSumOf((JSONArray) o);
            }
            else if (o instanceof JSONObject)
            {
                sum += getSumOf((JSONObject) o);
            }
            else if (o instanceof Number)
            {
                sum += ((Number) o).longValue();
            }
            else if (o instanceof Map)
            {
                sum += getSumOf((Map<String, Object>) o);
            }
            else if (o instanceof List)
            {
                sum += getSumOf((List<Object>) o);
            }
        }
        return sum;
    }

    private static long getSumOf(JSONObject o)
    {
        return getSumOf(o.toMap());
    }

    @SuppressWarnings("unchecked")
    private static long getSumOf(Map<String, Object> o)
    {
        long sum = 0;
        for (Object o2 : o.values())
        {
            if (o2 instanceof String && "red".equals(o2.toString()))
            {
                return 0;
            }
            else if (o2 instanceof Number)
            {
                sum += ((Number) o2).longValue();
            }
            else if (o2 instanceof JSONArray)
            {
                sum += getSumOf((JSONArray) o2);
            }
            else if (o2 instanceof JSONObject)
            {
                sum += getSumOf((JSONObject) o2);
            }
            else if (o2 instanceof Map)
            {
                sum += getSumOf((Map<String, Object>) o2);
            }
            else if (o2 instanceof List)
            {
                sum += getSumOf((List<Object>) o2);
            }
        }
        return sum;
    }

}
