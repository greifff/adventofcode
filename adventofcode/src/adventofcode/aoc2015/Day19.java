package adventofcode.aoc2015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day19
{

    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2015/day19.data");
        List<List<String>> replacement = new ArrayList<>();
        for (String s : input)
        {
            // System.out.println("++ " + s);
            String[] f = s.split(" ");
            replacement.add(Arrays.asList(f[0], f[2]));
        }
        String molecule = //
                // "HOH";
                "CRnCaSiRnBSiRnFArTiBPTiTiBFArPBCaSiThSiRnTiBPBPMgArCaSiRnTiMgArCaSiThCaSiRnFArRnSiRnFArTiTiBFArCaCaSiRnSiThCaCaSiRnMgArFYSiRnFYCaFArSiThCaSiThPBPTiMgArCaPRnSiAlArPBCaCaSiRnFYSiThCaRnFArArCaCaSiRnPBSiRnFArMgYCaCaCaCaSiThCaCaSiAlArCaCaSiRnPBSiAlArBCaCaCaCaSiThCaPBSiThPBPBCaSiRnFYFArSiThCaSiRnFArBCaCaSiRnFYFArSiThCaPBSiThCaSiRnPMgArRnFArPTiBCaPRnFArCaCaCaCaSiRnCaCaSiRnFYFArFArBCaSiThFArThSiThSiRnTiRnPMgArFArCaSiThCaPBCaSiRnBFArCaCaPRnCaCaPMgArSiRnFYFArCaSiThRnPBPMgAr";

        part1(replacement, molecule);
        part2(replacement, molecule);
    }

    private static String simplifyMolecule(String s)
    {
        return s.replace("Rn", "(").replace("Ar", ")");
        // .replace("Al", "U").replace("Ca", "V").replace("Mg", "W")
        // .replace("Si", "X").replace("Th", "Z").replace("Ti", "D");
    }

    private static void part2(List<List<String>> replacement, String molecule)
    {
        String molecule1 = simplifyMolecule(molecule);
        List<List<String>> replacement1 = replacement.stream()
                .map(l -> l.stream().map(s -> simplifyMolecule(s)).collect(Collectors.toList()))
                .collect(Collectors.toList());
        List<List<String>> bracketReplacement = new ArrayList<>(
                replacement1.stream().filter(l -> l.get(1).indexOf('(') != -1).collect(Collectors.toList()));

        bracketReplacement.add(Arrays.asList("Th(", "ThCa("));

        System.out.println("-- "
                + replacement1.stream().map(l -> l.stream().reduce((a, b) -> "[" + a + " -> " + b + "] ").orElse(""))
                        .reduce((a, b) -> a + b).orElse(""));

        int count = 0;
        int count2 = 0;
        System.out.println("## " + count + " " + molecule1);

        while (molecule1.length() > 1)
        {
            for (List<String> bracket : bracketReplacement)
            {
                int i = molecule1.lastIndexOf(bracket.get(1));
                while (i != -1)
                {
                    molecule1 = molecule1.substring(0, i) + bracket.get(0)
                            + molecule1.substring(i + bracket.get(1).length());
                    count++;
                    i = molecule1.lastIndexOf(bracket.get(1));
                }
            }

            System.out.println("## " + count + " " + molecule1);
            if (count == count2)
            {
                break;
            }
            count2 = count;
        }

        int k = molecule1.indexOf(')');
        while (k != -1)
        {
            int j = molecule1.lastIndexOf('(', k);

            String molecule2 = molecule1.substring(j + 1, k);

            for (List<String> bracket : replacement1)
            {
                int i = molecule2.lastIndexOf(bracket.get(1));
                while (i != -1)
                {
                    molecule2 = molecule2.substring(0, i) + bracket.get(0)
                            + molecule2.substring(i + bracket.get(1).length());
                    count++;
                    i = molecule2.lastIndexOf(bracket.get(1));
                }
            }
            molecule1 = molecule1.substring(0, j + 1) + molecule2 + molecule1.substring(k);

            System.out.println("## " + count + " " + molecule1);

            for (List<String> bracket : bracketReplacement)
            {
                int i = molecule1.lastIndexOf(bracket.get(1));
                while (i != -1)
                {
                    molecule1 = molecule1.substring(0, i) + bracket.get(0)
                            + molecule1.substring(i + bracket.get(1).length());
                    count++;
                    i = molecule1.lastIndexOf(bracket.get(1));
                }
            }

            k = molecule1.indexOf(')');
        }

        while (molecule1.length() > 1)
        {
            for (List<String> bracket : replacement1)
            {
                int i = molecule1.lastIndexOf(bracket.get(1));
                while (i != -1)
                {
                    molecule1 = molecule1.substring(0, i) + bracket.get(0)
                            + molecule1.substring(i + bracket.get(1).length());
                    count++;
                    i = molecule1.lastIndexOf(bracket.get(1));
                }
            }

            System.out.println("## " + count + " " + molecule1);
            if (count == count2)
            {
                break;
            }
            count2 = count;
        }

    }

    private static void part1(List<List<String>> replacement, String molecule)
    {
        Set<String> modifiedMolecules = new HashSet<>();

        for (List<String> r : replacement)
        {
            String a = r.get(0);
            String b = r.get(1);

            int i = molecule.indexOf(a);
            while (i != -1)
            {
                String molecule1 = molecule.substring(0, i) + b + molecule.substring(i + a.length());

                modifiedMolecules.add(molecule1);
                i = molecule.indexOf(a, i + 1);
            }
        }

        System.out.println("part1: " + modifiedMolecules.size());
    }
}
