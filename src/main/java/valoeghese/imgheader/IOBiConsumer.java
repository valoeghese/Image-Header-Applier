package valoeghese.imgheader;

import java.io.IOException;

/**
 * A bi consumer that can throw an IOException.
 * @param <T> The type of the first input for the operation.
 * @param <U> The type of the second input for the operation.
 */
@FunctionalInterface
public interface IOBiConsumer<T, U> {
	/**
	 * Performs this consumer's operation on the given argument.
	 * @param t The first input argument.
	 * @param u The second input argument.
	 * @throws IOException If an I/O error occurs.
	 */
	void accept(T t, U u) throws IOException;
}
