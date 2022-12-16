package adventofcode.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import adventofcode.util.IOUtil;

public class day19
{

    static class Rule
    {
        int id;
        String constant;
        List<Integer> variantA = new ArrayList<>();
        List<Integer> variantB = new ArrayList<>();

        @Override
        public String toString()
        {
            if (constant != null)
            {
                return constant;
            }
            String way1 = variantA.stream().map(a -> "R" + a + " ").reduce((a, b) -> a + b).orElse(" ");
            if (variantB.isEmpty())
            {
                return way1;
            }
            String way2 = variantB.stream().map(a -> "R" + a + " ").reduce((a, b) -> a + b).orElse(" ");

            return "(( " + way1 + " )|( " + way2 + " ))";
        }
    }

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2020/day19.data");
        int d = input.indexOf("");
        Map<Integer, Rule> rules = parseRules(input.subList(0, d));
        List<String> strings = input.subList(d + 1, input.size());
        part1(rules, strings);
        part2(rules, strings);
    }

    private static void part2(Map<Integer, Rule> rules, List<String> strings)
    {

        // compile regex
        String regex = rules.get(0).toString();
        int ruleIndex = regex.indexOf('R');

        while (ruleIndex != -1)
        {
            int endIndex = regex.indexOf(' ', ruleIndex);
            int rule2 = Integer.parseInt(regex.substring(ruleIndex + 1, endIndex));
            String r2;
            if (rule2 == 8)
            {
                r2 = " R42 + ";
            }
            else if (rule2 == 11)
            {
                r2 = " (( R42 R31 )|( R42 {2}+ R31 {2}+)|( R42 {3}+ R31 {3}+)|( R42 {4}+ R31 {4}+)|( R42 {5}+ R31 {5}+ ))";
                // r2 = " (?x) (?: R42 (?= R42 * (\\1?+ R31 )) )+ \\1 ";
            }
            else
            {
                r2 = rules.get(rule2).toString();
            }

            regex = regex.replace(regex.substring(ruleIndex, endIndex + 1), r2 + " ");
            // +1 to not confuse R1 with R12 or R123
            ruleIndex = regex.indexOf('R');
            // System.out.println("## " + regex);
        }
        regex = regex.replace(" ", "");

        System.out.println("part2 regex: " + regex);

        Pattern p = Pattern.compile(regex);

        int matching = 0;
        for (String s : strings)
        {
            if (p.matcher(s).matches())
            {
                matching++;
            }
        }
        System.out.println("part2: " + matching);
    }

    private static void part1(Map<Integer, Rule> rules, List<String> strings)
    {

        // compile regex
        String regex = rules.get(0).toString();
        int ruleIndex = regex.indexOf('R');
        while (ruleIndex != -1)
        {
            int endIndex = regex.indexOf(' ', ruleIndex);
            int rule2 = Integer.parseInt(regex.substring(ruleIndex + 1, endIndex));
            String r2 = rules.get(rule2).toString();
            regex = regex.replace(regex.substring(ruleIndex, endIndex + 1), r2 + " ");
            // +1 to not confuse R1 with R12 or R123
            ruleIndex = regex.indexOf('R');
            // System.out.println("## " + regex);
        }
        regex = regex.replace(" ", "");

        System.out.println("part1 regex: " + regex);

        Pattern p = Pattern.compile(regex);

        int matching = 0;
        for (String s : strings)
        {
            if (p.matcher(s).matches())
            {
                matching++;
            }
        }
        System.out.println("part1: " + matching);
    }

    static Map<Integer, Rule> parseRules(List<String> input1)
    {
        Map<Integer, Rule> rules = new HashMap<>();
        for (String in : input1)
        {
            StringTokenizer st = new StringTokenizer(in, ":| ", true);
            Rule rule1 = new Rule();
            int mode = 0;
            while (st.hasMoreTokens())
            {
                String t = st.nextToken();
                switch (t)
                {
                    case " ":
                        break;
                    case ":":
                        mode = 1;
                        break;
                    case "|":
                        mode = 2;
                        break;
                    case "\"a\"":
                        rule1.constant = "a";
                        break;
                    case "\"b\"":
                        rule1.constant = "b";
                        break;
                    default:
                        int r = Integer.parseInt(t);
                        switch (mode)
                        {
                            case 0:
                                rule1.id = r;
                                break;
                            case 1:
                                rule1.variantA.add(r);
                                break;
                            case 2:
                                rule1.variantB.add(r);
                                break;
                        }
                        break;
                }
            }
            rules.put(rule1.id, rule1);
        }
        return rules;
    }

}
