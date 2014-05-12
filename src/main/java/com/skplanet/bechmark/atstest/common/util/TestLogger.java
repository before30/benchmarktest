package com.skplanet.bechmark.atstest.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide core logger which is always visible by LOGGER.
 * Even verbose mode is off,
 * LOG reported by this Logger is always shown in log file.
 *
 * This logger is subject to use for reporting the major execution steps.
 *
 * @author wonsuk.yang
 * @since 0.1
 *
 */
public class TestLogger {
	/**
	 * Core logger.
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(TestLogger.class);

	private static int taskIdNum = 0;

	public static synchronized int getTaskId() {
		return (++taskIdNum % 100000000);
	}

} // end of class
