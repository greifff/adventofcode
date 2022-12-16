package adventofcode.aoc2017;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;
import adventofcode.util.Patterns;

public class Day20
{
    public static void main(String[] args)
    {
        List<String> input1 = IOUtil.readFile("2017/day20.test");
        List<String> input2 = IOUtil.readFile("2017/day20.data");

        part1(input1.stream().map(s -> new Particle(s)).collect(Collectors.toList()));
        part1(input2.stream().map(s -> new Particle(s)).collect(Collectors.toList()));
        part2(input1.stream().map(s -> new Particle(s)).collect(Collectors.toList()));
        part2(input2.stream().map(s -> new Particle(s)).collect(Collectors.toList()));
    }

    private static void part1(List<Particle> particles)
    {
        for (int i = 0; i < particles.size(); i++)
        {
            particles.get(i).id = i;
        }

        for (int i = 0; i < 1000; i++)
        {
            for (Particle p : particles)
            {
                p.step();
            }
        }

        Collections.sort(particles, new Comparator<Particle>()
        {
            @Override
            public int compare(Particle p1, Particle p2)
            {
                int delta = p1.manhattanDistance() - p2.manhattanDistance();
                if (delta != 0)
                {
                    return delta;
                }
                return p1.manhattanDistanceNext() - p2.manhattanDistanceNext();
            }
        });

        System.out.println("part1: " + particles.get(0).id + " " + particles.get(0).manhattanDistance());
    }

    private static void part2(List<Particle> particles)
    {
        for (int i = 0; i < particles.size(); i++)
        {
            particles.get(i).id = i;
        }

        for (int i = 0; i < 1000; i++)
        {
            for (Particle p : particles)
            {
                p.step();
            }

            Map<Integer, List<Particle>> lookup = new HashMap<>();
            particles.forEach(p -> {
                int d = p.manhattanDistance();
                List<Particle> p1 = lookup.get(d);
                if (p1 == null)
                {
                    p1 = new LinkedList<>();
                    lookup.put(d, p1);
                }
                p1.add(p);
            });

            Set<Particle> delete = new HashSet<>();
            lookup.values().forEach(p1 -> {
                while (!p1.isEmpty())
                {
                    Particle p = p1.remove(0);
                    int k = 0;
                    while (k < p1.size())
                    {
                        Particle pk = p1.get(k);
                        if (p.p[0] == pk.p[0] && p.p[1] == pk.p[1] && p.p[2] == pk.p[2])
                        {
                            delete.add(p);
                            delete.add(pk);
                            p1.remove(pk);
                        }
                        else
                        {
                            k++;
                        }
                    }
                }
            });

            particles.removeAll(delete);
        }

        System.out.println("part2: " + particles.size());
    }

    static class Particle implements Comparable<Particle>
    {
        int[] p = new int[3];

        int[] v = new int[3];

        int[] a = new int[3];

        int id;

        Particle(String input)
        {
            Matcher m = Patterns.number.matcher(input);
            for (int i = 0; i < 3; i++)
            {
                m.find();
                p[i] = Integer.parseInt(m.group());
            }
            for (int i = 0; i < 3; i++)
            {
                m.find();
                v[i] = Integer.parseInt(m.group());
            }
            for (int i = 0; i < 3; i++)
            {
                m.find();
                a[i] = Integer.parseInt(m.group());
            }
        }

        void step()
        {
            for (int i = 0; i < 3; i++)
            {
                v[i] += a[i];
                p[i] += v[i];
            }
        }

        int manhattanDistance()
        {
            return Math.abs(p[0]) + Math.abs(p[1]) + Math.abs(p[2]);
        }

        int manhattanDistanceNext()
        {
            return Math.abs(p[0] + v[0]) + Math.abs(p[1] + v[1]) + Math.abs(p[2] + v[2]);
        }

        @Override
        public int compareTo(Particle o)
        {
            return manhattanDistance() - o.manhattanDistance();
        }
    }
}
