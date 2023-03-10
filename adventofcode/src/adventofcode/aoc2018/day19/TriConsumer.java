package adventofcode.aoc2018.day19;

import java.util.Objects;

/**
 * Represents an operation that accepts two input arguments and returns no result. This is the three-arity specialization of {@link Consumer}. Unlike
 * most other functional interfaces, {@code TriConsumer} is expected to operate via side-effects.
 *
 * <p>
 * This is a <a href="package-summary.html">functional interface</a> whose functional method is {@link #accept(Object, Object)}.
 *
 * @param <T1>
 *            the type of the first argument to the operation
 * @param <T2>
 *            the type of the second argument to the operation
 * @param <T3>
 *            the type of the third argument to the operation
 *
 * @see Consumer
 */
@FunctionalInterface
public interface TriConsumer<T1, T2, T3> {

	/**
	 * Performs this operation on the given arguments.
	 *
	 * @param t
	 *            the first input argument
	 * @param u
	 *            the second input argument
	 */
	void accept(T1 tt, T2 t2, T3 t3);

	/**
	 * Returns a composed {@code TriConsumer} that performs, in sequence, this operation followed by the {@code after} operation. If performing either
	 * operation throws an exception, it is relayed to the caller of the composed operation. If performing this operation throws an exception, the
	 * {@code after} operation will not be performed.
	 *
	 * @param after
	 *            the operation to perform after this operation
	 * @return a composed {@code TriConsumer} that performs in sequence this operation followed by the {@code after} operation
	 * @throws NullPointerException
	 *             if {@code after} is null
	 */
	default TriConsumer<T1, T2, T3> andThen(TriConsumer<? super T1, ? super T2, ? super T3> after) {
		Objects.requireNonNull(after);

		return (a1, a2, a3) -> {
			accept(a1, a2, a3);
			after.accept(a1, a2, a3);
		};
	}
}
