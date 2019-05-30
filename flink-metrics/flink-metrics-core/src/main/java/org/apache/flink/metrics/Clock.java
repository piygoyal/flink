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
