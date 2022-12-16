package adventofcode.turing;

import java.util.HashMap;
import java.util.Map;

public class TuringMachine
{
    private int index;
    private Map<Integer, Integer> band = new HashMap<>();

    private State state;

    public void move(int delta)
    {
        index += delta;
    }

    public int get()
    {
        Integer value = band.get(index);
        return (value == null) ? 0 : value;
    }

    public void set(int value)
    {
        band.put(index, value);
    }

    public int checksum()
    {
        return band.values().stream().reduce((a, b) -> a + b).orElse(0);
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

}
