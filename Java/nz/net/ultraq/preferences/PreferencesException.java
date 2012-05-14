
package nz.net.ultraq.preferences;

/**
 * Unchecked exception used for errors that occur during the use of the
 * alternate preferences entrypoint class.
 * 
 * @author Emanuel Rabina
 */
public class PreferencesException extends RuntimeException {

	/**
	 * Constructor, takes an error message as parameter.
	 *
	 * @param message Additional error message.
	 */
	PreferencesException(String message) {

		super(message);
	}

	/**
	 * Constructor, wraps the original exception in this unchecked type.
	 * 
	 * @param message Error message to accompany the exception.
	 * @param cause The cause for this exception to be raised.
	 */
	PreferencesException(String message, Exception cause) {

		super(message, cause);
	}
}
