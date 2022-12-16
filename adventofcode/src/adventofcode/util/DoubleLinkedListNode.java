package adventofcode.util;

import java.util.Objects;

public class DoubleLinkedListNode<T>
{

    public DoubleLinkedListNode<T> next = this;
    public DoubleLinkedListNode<T> previous = this;

    public final T value;

    public DoubleLinkedListNode(T value)
    {
        this.value = value;
    }

    public DoubleLinkedListNode<T> remove()
    {
        previous.next = next;
        next.previous = previous;

        return this;
    }

    public DoubleLinkedListNode<T> jumpAhead(int count)
    {
        DoubleLinkedListNode<T> rv = this;
        for (int i = 0; i < count; i++)
        {
            rv = rv.next;
        }
        return rv;
    }

    public DoubleLinkedListNode<T> jumpBack(int count)
    {
        DoubleLinkedListNode<T> rv = this;
        for (int i = 0; i < count; i++)
        {
            rv = rv.previous;
        }
        return rv;
    }

    public DoubleLinkedListNode<T> insertAfter(DoubleLinkedListNode<T> node)
    {
        next.previous = node;
        node.previous = this;
        node.next = next;
        this.next = node;

        return node;
    }

    public DoubleLinkedListNode<T> insertBefore(DoubleLinkedListNode<T> node)
    {
        previous.next = node;
        node.previous = previous;
        node.next = this;
        this.previous = node;

        return node;
    }

    @Override
    public String toString()
    {
        return next.toString(new StringBuilder(), this);
    }

    public String toString(StringBuilder prefix, DoubleLinkedListNode<T> stop)
    {
        prefix.append(',').append(value);
        if (Objects.equals(this, stop))
        {
            return prefix.toString();
        }
        else
        {
            return next.toString(prefix, stop);
        }
    }

    public int count(DoubleLinkedListNode<T> stop)
    {
        if (Objects.equals(this, stop))
        {
            return 1;
        }
        return 1 + next.count(stop);
    }

}
