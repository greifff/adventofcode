package adventofcode.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class day4
{

    private static final String[] expectedFields =
    { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" }; // "cid" optional

    public static void main(String[] args)
    {
        List<String> passportData = IOUtil.readFile("2020/day4.data");

        List<Map<String, String>> passports = new ArrayList<>();

        Map<String, String> passport = new HashMap<>();
        passports.add(passport);

        for (String passportInfo : passportData)
        {
            if ("".equals(passportInfo))
            {
                passport = new HashMap<>();
                passports.add(passport);
                System.out.println("new");
            }
            else
            {
                String[] properties = passportInfo.split(" ");
                for (String property : properties)
                {

                    String[] entry = property.split(":");
                    System.out.println(property + " " + entry[0] + " " + entry[1]);
                    passport.put(entry[0], entry[1]);
                }
            }
        }

        // part 1
        List<String> required = Arrays.asList(expectedFields);
        int valid = 0;
        for (Map<String, String> passport1 : passports)
        {
            System.out.println("" + passport1.keySet().size() + " " + String.join("_", passport1.keySet()));
            boolean isvalid = true;
            for (String r : required)
            {
                isvalid &= passport1.keySet().contains(r);
                System.out.println(r + " " + isvalid);
            }
            if (isvalid)// (passport1.keySet().containsAll(required))
            {
                valid++;
            }
        }

        System.out.println("Part1: " + valid);

        // part 2
        List<String> eyecolors = Arrays.asList(new String[]
        { "amb", "blu", "brn", "gry", "grn", "hzl", "oth" });
        valid = 0;
        for (Map<String, String> passport1 : passports)
        {
            try
            {
                int birthyear = Integer.parseInt(passport1.get("byr"));
                int issueyear = Integer.parseInt(passport1.get("iyr"));
                int expyear = Integer.parseInt(passport1.get("eyr"));
                String hair = passport1.get("hcl");
                String eyecolor = passport1.get("ecl");
                String pid = passport1.get("pid");
                String height = passport1.get("hgt");
                boolean heightIsValid = false;
                if (height.endsWith("cm"))
                {
                    int h1 = Integer.parseInt(height.substring(0, height.length() - 2));
                    heightIsValid = h1 >= 150 && h1 <= 193;
                }
                else if (height.endsWith("in"))
                {
                    int h1 = Integer.parseInt(height.substring(0, height.length() - 2));
                    heightIsValid = h1 >= 59 && h1 <= 76;
                }
                if (birthyear >= 1920 && birthyear <= 2002 && issueyear >= 2010 && issueyear <= 2020 && expyear >= 2020
                        && expyear <= 2030 && heightIsValid && eyecolors.contains(eyecolor) && pid.matches("[0-9]{9}")
                        && hair.matches("\\#[0-9a-f]{6}"))
                // && hair.matches("\\#[0-9a-f]{6}")
                {
                    valid++;
                }
            }
            catch (NumberFormatException | NullPointerException e)
            {
                // invalid
            }
        }

        System.out.println("Part2: " + valid);
    }
}
