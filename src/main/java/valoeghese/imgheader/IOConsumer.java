package valoeghese.imgheader;

import java.io.IOException;

/**
 * A consumer that throws an IOException.
 * @param <T> The type of the input to the operation.
 */
@FunctionalInterface
public interface IOConsumer<T> {
	/**
	 * Performs this consumer's operation on the given argument.
	 * @param t The input argument.
	 * @throws IOException If an I/O error occurs.
	 */
	void accept(T t) throws IOException;
}
