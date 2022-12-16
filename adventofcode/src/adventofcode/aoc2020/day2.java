package adventofcode.aoc2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class day2
{

    private static class pwdEntry
    {
        int min;
        int max;
        char letter;
        String pwd;

        pwdEntry(String input)
        {
            String[] parts = input.split("[-\\s:]", 4);

            min = Integer.parseInt(parts[0]);
            max = Integer.parseInt(parts[1]);
            letter = parts[2].charAt(0);
            pwd = parts[3].trim();
        }

        @Override
        public String toString()
        {
            return "" + min + "-" + max + " " + letter + ": " + pwd;
        }

        boolean validate()
        {
            return (pwd.charAt(min - 1) == letter) ^ (pwd.charAt(max - 1) == letter);
        }
    }

    public static void main(String[] args)
    {
        List<pwdEntry> passwords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("2020/day2.data")), StandardCharsets.UTF_8)))
        {

            // n-m l: pwd -> extract n,m,l and pwd
            String line = reader.readLine();
            while (line != null)
            {
                passwords.add(new pwdEntry(line));
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Part 1
        int valid = 0;
        for (pwdEntry entry : passwords)
        {

            int c = countOccurrence(entry.letter, entry.pwd);
            if (c >= entry.min && c <= entry.max)
            {
                System.out.println("valid: " + entry);
                valid++;
            }

        }
        System.out.println("valid: " + valid);

        // Part 2
        valid = 0;
        for (pwdEntry entry : passwords)
        {

            if (entry.validate())
            {
                System.out.println("valid: " + entry);
                valid++;
            }

        }
        System.out.println("valid: " + valid);
    }

    private static int countOccurrence(char letter, String password)

    {
        int occurrence = 0;
        for (int i = 0; i < password.length(); i++)
        {
            if (password.charAt(i) == letter)
            {
                occurrence++;
            }
        }
        return occurrence;
    }
}
