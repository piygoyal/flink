package org.apache.flink.metrics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Simple Thread-safe Timer implementation.
 **/
public class SimpleTimer extends AbstractTimer {

	private final AtomicLong count = new AtomicLong(0);
	private final AtomicLong totalTimeInNanos = new AtomicLong(0);

	public SimpleTimer(Clock clock) {
		super(clock);
	}

	/**
	 * Default constructor uses the system clock.
	 */
	public SimpleTimer() {
		this(Clock.SYSTEM);
	}

	@Override
	public void record(long amount, TimeUnit unit) {
		if (amount >= 0) {
			final long nanos = TimeUnit.NANOSECONDS.convert(amount, unit);
			totalTimeInNanos.addAndGet(nanos);
			count.incrementAndGet();
		}
	}

	@Override
	public long getCount() {
		return count.get();
	}

	@Override
	public long totalTime(TimeUnit timeUnit) {
		return timeUnit.convert(totalTimeInNanos.get(), TimeUnit.NANOSECONDS);
	}
}
