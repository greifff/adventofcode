package adventofcode.aoc2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import adventofcode.util.IOUtil;

public class Day08
{
    public static void main(String[] args)
    {
        List<String> input = IOUtil.readFile("2018/day08.data");

        List<Integer> rawData = Arrays.asList(input.get(0).split(" ")).stream().map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        TreeNode root = new TreeNode(rawData);

        System.out.println("part1: " + root.sumMetadata());
        System.out.println("part2: " + root.value());
    }

    static class TreeNode
    {
        List<TreeNode> children = new ArrayList<>();
        List<Integer> metadata = new ArrayList<>();

        TreeNode(List<Integer> rawData)
        {
            int childCount = rawData.remove(0);
            int metadataCount = rawData.remove(0);

            for (int i = 0; i < childCount; i++)
            {
                children.add(new TreeNode(rawData));
            }
            for (int i = 0; i < metadataCount; i++)
            {
                metadata.add(rawData.remove(0));
            }
        }

        int sumMetadata()
        {
            int sum = metadata.stream().reduce((a, b) -> a + b).orElse(0);
            for (TreeNode c : children)
            {
                sum += c.sumMetadata();
            }
            return sum;
        }

        int value()
        {
            if (children.isEmpty())
            {
                return metadata.stream().reduce((a, b) -> a + b).orElse(0);
            }
            int value = 0;
            for (int i : metadata)
            {
                if (i - 1 < children.size())
                {
                    value += children.get(i - 1).value();
                }
            }
            return value;
        }
    }
}
