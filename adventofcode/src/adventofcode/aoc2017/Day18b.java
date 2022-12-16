package adventofcode.aoc2017;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventofcode.util.IOUtil;

public class Day18b
{
    public static void main(String[] args)
    {

        List<String> input2 = IOUtil.readFile("2017/day18.data");

        Deque<Long> queue1 = new ArrayDeque<>();
        Deque<Long> queue2 = new ArrayDeque<>();

        AssemDuet duet1 = new AssemDuet(input2, queue1, queue2);
        duet1.registers.put("p", 1L);
        AssemDuet duet2 = new AssemDuet(input2, queue2, queue1);
        duet2.registers.put("p", 0L);

        Thread t1 = new Thread(duet1);
        Thread t2 = new Thread(duet2);

        t1.start();
        t2.start();

        Thread overwatch = new Thread()
        {
            @Override
            public void run()
            {
                while (t1.isAlive() || t2.isAlive())
                {
                    try
                    {
                        Thread.sleep(50);
                    }
                    catch (InterruptedException e)
                    {
                        //
                    }
                    if ((duet1.waiting && (duet2.waiting || !t2.isAlive()))
                            || (duet2.waiting && (duet1.waiting || !t1.isAlive())))
                    {
                        System.out.println("part2: " + duet1.valuesSend);
                        System.exit(0);
                    }
                }
            }
        };

        overwatch.start();

        try
        {
            t1.join();
        }
        catch (InterruptedException e)
        {
            //
        }
        try
        {
            t2.join();
        }
        catch (InterruptedException e)
        {
            //
        }
        System.out.println("part2: " + duet1.valuesSend);
    }

    static class AssemDuet implements Runnable
    {
        Map<String, Long> registers = new HashMap<>();
        int instruction;
        List<String> program;
        Deque<Long> sendQueue;
        Deque<Long> receiveQueue;
        boolean waiting;

        int valuesSend;

        AssemDuet(List<String> program, Deque<Long> sendQueue, Deque<Long> receiveQueue)
        {
            this.program = program;
            this.sendQueue = sendQueue;
            this.receiveQueue = receiveQueue;
        }

        @Override
        public void run()
        {
            while (instruction >= 0 && instruction < program.size())
            {
                execute(program.get(instruction));
            }
        }

        void execute(String stmt)
        {
            String[] f = stmt.split(" ");
            switch (f[0])
            {
                case "snd":
                    sendQueue.add(getValue(f[1]));
                    valuesSend++;
                    break;
                case "set":
                    registers.put(f[1], getValue(f[2]));
                    break;
                case "add":
                    registers.put(f[1], getValue(f[1]) + getValue(f[2]));
                    break;
                case "mul":
                    registers.put(f[1], getValue(f[1]) * getValue(f[2]));
                    break;
                case "mod":
                    registers.put(f[1], getValue(f[1]) % getValue(f[2]));
                    break;
                case "rcv":
                    Long receive = receiveQueue.poll();
                    while (receive == null)
                    {
                        waiting = true;
                        Thread.onSpinWait();
                        receive = receiveQueue.poll();
                    }
                    waiting = false;
                    registers.put(f[1], receive);
                    break;
                case "jgz":
                    if (getValue(f[1]) > 0)
                    {
                        instruction += getValue(f[2]) - 1;
                    }
                    break;
            }
            instruction++;
        }

        private long getValue(String s)
        {
            try
            {
                return Long.parseLong(s);
            }
            catch (NumberFormatException e)
            {
                //
            }
            Long v = registers.get(s);
            return v == null ? 0 : v;
        }

    }
}
