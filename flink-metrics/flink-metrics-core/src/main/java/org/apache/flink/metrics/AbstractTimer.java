package org.apache.flink.metrics;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Abstract implementation for timer.
 **/
public abstract class AbstractTimer implements Timer {

	/** Clock to use for measuring the time of calls. */
	protected final Clock clock;

	public AbstractTimer(Clock clock) {
		this.clock = clock;
	}

	@Override
	public void record(Runnable f) {
		final long s = clock.getTime();
		try {
			f.run();
		} finally {
			final long e = clock.getTime();
			record(e - s, TimeUnit.NANOSECONDS);
		}
	}

	@Override
	public <T> T record(Callable<T> f) throws Exception {
		final long s = clock.getTime();
		try {
			return f.call();
		} finally {
			final long e = clock.getTime();
			record(e - s, TimeUnit.NANOSECONDS);
		}
	}
}
