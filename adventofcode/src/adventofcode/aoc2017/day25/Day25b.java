package adventofcode.aoc2017.day25;

import adventofcode.turing.State;
import adventofcode.turing.TuringMachine;

public class Day25b
{

    public static void main(String[] args)
    {
        TuringMachine tm = new TuringMachine();
        tm.setState(new StateA());
        int i = 0;
        while (i < 12_523_873) // 12_523_873
        {
            State s = tm.getState();
            s.execute(tm);
            i++;
        }
        System.out.println("part1: " + tm.checksum());
    }

    static class StateA implements State
    {
        @Override
        public void execute(TuringMachine tm)
        {
            if (tm.get() == 0)
            {
                tm.set(1);
                tm.move(1);
                tm.setState(new StateB());
            }
            else
            {
                tm.set(1);
                tm.move(-1);
                tm.setState(new StateE());
            }
        }
    }

    static class StateB implements State
    {
        @Override
        public void execute(TuringMachine tm)
        {
            if (tm.get() == 0)
            {
                tm.set(1);
                tm.move(1);
                tm.setState(new StateC());
            }
            else
            {
                tm.set(1);
                tm.move(1);
                tm.setState(new StateF());
            }
        }
    }

    static class StateC implements State
    {
        @Override
        public void execute(TuringMachine tm)
        {
            if (tm.get() == 0)
            {
                tm.set(1);
                tm.move(-1);
                tm.setState(new StateD());
            }
            else
            {
                tm.set(0);
                tm.move(1);
                tm.setState(new StateB());
            }
        }
    }

    static class StateD implements State
    {
        @Override
        public void execute(TuringMachine tm)
        {
            if (tm.get() == 0)
            {
                tm.set(1);
                tm.move(1);
                tm.setState(new StateE());
            }
            else
            {
                tm.set(0);
                tm.move(-1);
                tm.setState(new StateC());
            }
        }
    }

    static class StateE implements State
    {
        @Override
        public void execute(TuringMachine tm)
        {
            if (tm.get() == 0)
            {
                tm.set(1);
                tm.move(-1);
                tm.setState(new StateA());
            }
            else
            {
                tm.set(0);
                tm.move(1);
                tm.setState(new StateD());
            }
        }
    }

    static class StateF implements State
    {
        @Override
        public void execute(TuringMachine tm)
        {
            if (tm.get() == 0)
            {
                tm.set(1);
                tm.move(1);
                tm.setState(new StateA());
            }
            else
            {
                tm.set(1);
                tm.move(1);
                tm.setState(new StateC());
            }
        }
    }

}
