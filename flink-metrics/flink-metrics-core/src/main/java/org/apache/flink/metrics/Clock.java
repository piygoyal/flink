package org.apache.flink.metrics;

/**
 * A high-resolution timing source that can be used to access the current monotonic time.
 * Most of the time the {@link #SYSTEM} implementation that calls the builtin java methods is
 * probably the right one to use. Other implementations would typically only get used for
 * unit tests or other cases where precise control of the clock is needed.
 */
public interface Clock {
	/**
	 * Current time in nanoseconds since the epoch. Typically equivalent to System.nanoTime.
	 *
	 * <p>This method should only be used to measure elapsed time and is not related to any other notion of system or
	 * wall-clock time.
	 */
	long getTime();

	/**
	 * Default clock implementation based on corresponding calls in {@link java.lang.System}.
	 */
	Clock SYSTEM = new Clock() {
		@Override public long getTime() {
			return System.nanoTime();
		}
	};
}
