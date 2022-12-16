package adventofcode.aoc2017.day25;

import adventofcode.turing.State;
import adventofcode.turing.TuringMachine;

public class Day25a
{

    public static void main(String[] args)
    {
        TuringMachine tm = new TuringMachine();
        tm.setState(new StateA());
        for (int i = 0; i < 6; i++)
        {
            tm.getState().execute(tm);
        }
        System.out.println("test1: " + tm.checksum());
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
            }
            else
            {
                tm.set(0);
                tm.move(-1);
            }
            tm.setState(new StateB());
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
                tm.move(-1);
            }
            else
            {
                tm.set(1);
                tm.move(1);
            }
            tm.setState(new StateA());
        }

    }
}
