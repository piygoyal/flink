package org.apache.flink.metrics;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for SimpleTimer.
 **/
public class SimpleTimerTest {

	@Test
	public void testRecordDuration() {
		Timer t = new SimpleTimer();

		t.record(100, TimeUnit.MILLISECONDS);
		t.record(5, TimeUnit.MILLISECONDS);

		// verify the total count of event recorded.
		assertEquals(2, t.getCount());
		// verify the total time in milliseconds.
		assertEquals(105, t.totalTime(TimeUnit.MILLISECONDS));
	}

	@Test
	public void testRecordRunnable() {

		final long beforeTime = 1000L;
		final long afterTime = 2000L;

		// set up a mock clock to return controlled times.
		Clock mockClock = mock(Clock.class);
		when(mockClock.getTime()).thenReturn(beforeTime).thenReturn(afterTime);

		Timer t = new SimpleTimer(mockClock);
		t.record(() -> {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {

			}
		});

		// verify the total count of event recorded.
		assertEquals(1, t.getCount());
		// verify the total time in nanosecond.
		assertEquals(afterTime - beforeTime, t.totalTime(TimeUnit.NANOSECONDS));
	}

	@Test
	public void testRecordCallable() throws Exception {

		final long beforeTime = 1000L;
		final long afterTime = 2000L;
		final Integer callableReturnValue = 21;

		// set up a mock clock to return controlled times.
		Clock mockClock = mock(Clock.class);
		when(mockClock.getTime()).thenReturn(beforeTime).thenReturn(afterTime);

		Timer t = new SimpleTimer(mockClock);
		Integer result = t.record(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep(10);
				return callableReturnValue;
			}
		});

		// verify that the returned value from record is equal to the value returned by the callable.
		assertEquals(callableReturnValue, result);
		// verify the total count of event recorded.
		assertEquals(1, t.getCount());
		// verify the total time in nanosecond.
		assertEquals(afterTime - beforeTime, t.totalTime(TimeUnit.NANOSECONDS));
	}
}
