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
