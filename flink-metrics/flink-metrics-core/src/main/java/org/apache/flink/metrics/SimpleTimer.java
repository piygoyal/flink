/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
