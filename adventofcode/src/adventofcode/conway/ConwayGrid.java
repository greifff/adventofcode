package adventofcode.conway;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public class ConwayGrid
{

    private int maxx;
    private int maxy;
    private BiPredicate<Boolean, List<Boolean>> operator = (v, n) -> false;
    private BiPredicate<Boolean, int[]> overrideOperator = (v, c) -> v;

    Map<Integer, Map<Integer, Boolean>> nodes = new HashMap<>();

    public ConwayGrid(int maxx, int maxy, BiPredicate<Boolean, List<Boolean>> operator)
    {
        this.maxx = maxx;
        this.maxy = maxy;
        this.operator = operator;
    }

    public void setOverride(BiPredicate<Boolean, int[]> overrideOperator)
    {
        this.overrideOperator = overrideOperator;
    }

    public void set(int x, int y, boolean value)
    {
        if (x >= 0 && x < maxx && y >= 0 && y < maxy)
        {
            Map<Integer, Boolean> n1 = nodes.get(x);
            if (n1 == null)
            {
                n1 = new HashMap<>();
                nodes.put(x, n1);
            }
            n1.put(y, value);
        }
    }

    public boolean get(int x, int y)
    {
        Map<Integer, Boolean> n1 = nodes.get(x);
        if (n1 == null)
        {
            return false;
        }
        Boolean n2 = n1.get(y);
        return n2 == null ? false : n2;
    }

    public void step()
    {
        Map<Integer, Map<Integer, Boolean>> nodes2 = new HashMap<>();
        for (int x = 0; x < maxx; x++)
        {
            Map<Integer, Boolean> n2 = new HashMap<>();
            nodes2.put(x, n2);
            for (int y = 0; y < maxy; y++)
            {
                List<Boolean> adjacent = Arrays.asList(get(x - 1, y - 1), get(x - 1, y), get(x - 1, y + 1),
                        get(x, y - 1), get(x, y + 1), get(x + 1, y - 1), get(x + 1, y), get(x + 1, y + 1));
                n2.put(y, overrideOperator.test(operator.test(get(x, y), adjacent), new int[]
                { x, y }));
            }
        }
        nodes = nodes2;
    }

    public int countActive()
    {
        return nodes.values().stream().flatMap(n -> n.values().stream()).map(b -> b ? 1 : 0).reduce((a, b) -> a + b)
                .orElse(0);
    }

}
