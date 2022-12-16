package adventofcode.aoc2018;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;
import adventofcode.util.Patterns;

public class Day04
{
    public static void main(String[] args)
    {
        List<String> input2 = IOUtil.readFile("2018/day04.test");
        List<String> input1 = IOUtil.readFile("2018/day04.data");
        List<Event> events1 = input1.stream().map(s -> new Event(s)).collect(Collectors.toList());
        List<Event> events2 = input2.stream().map(s -> new Event(s)).collect(Collectors.toList());

        part1(events2);
        part1(events1);
    }

    private static void part1(List<Event> events)
    {

        Collections.sort(events);

        Map<Integer, Guard> lookup = new HashMap<>();

        Guard currentGuard = new Guard();
        int asleepSince = 0;
        for (Event e : events)
        {
            if (e.guard != -1)
            {
                currentGuard = lookup.get(e.guard);
                if (currentGuard == null)
                {
                    currentGuard = new Guard();
                    lookup.put(e.guard, currentGuard);
                    currentGuard.id = e.guard;
                }
            }
            else if (e.event.equals("falls asleep"))
            {
                int hour = e.date.get(Calendar.HOUR);
                int minute = e.date.get(Calendar.MINUTE);
                asleepSince = hour == 23 ? 0 : minute;
            }
            else if (e.event.equals("wakes up"))
            {
                int hour = e.date.get(Calendar.HOUR);
                int minute = e.date.get(Calendar.MINUTE);
                if (hour != 23)
                {
                    currentGuard.sleep(asleepSince, hour == 1 ? 60 : minute);
                }
            }
        }

        List<Guard> guards = new ArrayList<>(lookup.values());
        guards.forEach(g -> g.evaluate());
        Collections.sort(guards, (g1, g2) -> g1.sleepingMinutes - g2.sleepingMinutes);

        Collections.reverse(guards);

        System.out.println("# " + guards.get(0).sleepingMinutes);

        Guard g1 = guards.get(0);
        System.out.println("part1: " + (g1.id * g1.sleepiestMinute()));

        Collections.sort(guards, (g3, g4) -> g3.sleepIntensity - g4.sleepIntensity);
        Collections.reverse(guards);
        g1 = guards.get(0);
        System.out.println("part2: " + (g1.id * g1.sleepiestMinute()));
    }

    static class Guard
    {
        int[] minutes = new int[60];
        int id;

        int sleepingMinutes;
        int sleepIntensity;

        void sleep(int from, int to)
        {
            for (int i = from; i < to; i++)
            {
                minutes[i]++;
            }
        }

        void evaluate()
        {
            for (int m : minutes)
            {
                sleepingMinutes += m;
                sleepIntensity = Math.max(sleepIntensity, m);
            }
        }

        int sleepiestMinute()
        {
            int sleepiestSoFar = -1;
            int maxSleepiness = 0;

            for (int i = 0; i < 60; i++)
            {
                if (maxSleepiness < minutes[i])
                {
                    sleepiestSoFar = i;
                    maxSleepiness = minutes[i];
                }
            }

            return sleepiestSoFar;
        }
    }

    static class Event implements Comparable<Event>
    {
        Calendar date = new GregorianCalendar();
        String event;
        int guard = -1;

        Event(String s)
        {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try
            {
                date.setTime(formatter.parse(s.substring(1, 17)));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            String e = s.substring(19);

            Matcher m = Patterns.number.matcher(e);

            if (m.find())
            {
                guard = Integer.parseInt(m.group());
                event = "begins shift";
            }
            else
            {
                event = e;
            }
        }

        @Override
        public int compareTo(Event o)
        {
            return date.compareTo(o.date);
        }
    }
}
