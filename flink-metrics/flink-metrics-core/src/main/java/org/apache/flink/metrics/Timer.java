package org.apache.flink.metrics;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Timer metric type to measure the time it takes for a certain event.
 **/
public interface Timer extends Metric {
	/**
	 * Updates the statistics kept by the counter with the specified amount.
	 *
	 * @param amount
	 *     Duration of a single event being measured by this timer. If the amount is less than 0
	 *     the value will be dropped.
	 * @param unit
	 *     Time unit for the amount being recorded.
	 */
	void record(long amount, TimeUnit unit);

	/**
	 * Executes the runnable `f` and records the time taken.
	 *
	 * @param f
	 *     Function to execute and measure the execution time.
	 */
	void record(Runnable f);

	/**
	 * Executes the callable `f` and records the time taken.
	 *
	 * @param f
	 *     Function to execute and measure the execution time.
	 * @return
	 *     The return value of `f`.
	 */
	<T> T record(Callable<T> f) throws Exception;

	/**
	 * The number of times that record has been called.
	 */
	long getCount();

	/**
	 * The total time of all recorded events.
	 *
	 * @param timeUnit
	 *     Desired timeunit to express the time into.
	 */
	long totalTime(TimeUnit timeUnit);
}

