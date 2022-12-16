package adventofcode.aoc2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import adventofcode.util.HexUtil;

public class Day17
{

    public static void main(String[] args)
    {
        part1("hijkl");
        part1("ihgpwlah");
        part1("kglvqrro");
        part1("ulqzkmiv");
        part1("rrrbmfta");

        part2("ihgpwlah");
        part2("kglvqrro");
        part2("ulqzkmiv");
        part2("rrrbmfta");
    }

    private static void part1(String passcode)
    {
        MessageDigest digest = null;
        try
        {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        List<Path> paths = new ArrayList<>();
        paths.add(new Path("", 0, 0));
        String shortestPath = null;
        while (!paths.isEmpty() && shortestPath == null)
        {
            List<Path> paths2 = new ArrayList<>();

            for (Path path : paths)
            {
                paths2.addAll(path.walk(passcode, digest));
            }

            paths = paths2;
            for (Path path : paths)
            {
                if (path.x == 3 && path.y == 3)
                {
                    shortestPath = path.path;
                }
            }
        }

        System.out.println("part1: " + shortestPath);
    }

    private static void part2(String passcode)
    {
        MessageDigest digest = null;
        try
        {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        List<Path> paths = new ArrayList<>();
        paths.add(new Path("", 0, 0));
        int length = 0;
        while (!paths.isEmpty())
        {
            List<Path> paths2 = new ArrayList<>();

            for (Path path : paths)
            {
                paths2.addAll(path.walk(passcode, digest));
            }

            paths = paths2;
            List<Path> dontfollow = new ArrayList<>();
            for (Path path : paths)
            {
                if (path.x == 3 && path.y == 3)
                {
                    length = Math.max(length, path.path.length());
                    dontfollow.add(path);
                }
            }
            paths.removeAll(dontfollow);
        }

        System.out.println("part2: " + length);
    }

    static class Path
    {
        String path;
        int x;
        int y;

        Path(String path, int x, int y)
        {
            this.path = path;
            this.x = x;
            this.y = y;
        }

        List<Path> walk(String passcode, MessageDigest digest)
        {
            String hash = HexUtil.toHexString(digest.digest((passcode + path).getBytes()));
            List<Path> p = new ArrayList<>();
            if (y > 0 && isOpen(hash.charAt(0)))
            {
                p.add(new Path(path + "U", x, y - 1));
            }
            if (y < 3 && isOpen(hash.charAt(1)))
            {
                p.add(new Path(path + "D", x, y + 1));
            }
            if (x > 0 && isOpen(hash.charAt(2)))
            {
                p.add(new Path(path + "L", x - 1, y));
            }
            if (x < 3 && isOpen(hash.charAt(3)))
            {
                p.add(new Path(path + "R", x + 1, y));
            }
            return p;
        }

        boolean isOpen(char c)
        {
            return c >= 'b' && c <= 'f';
        }
    }

}
