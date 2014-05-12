package com.skplanet.bechmark.atstest.common.service;

import com.skplanet.bechmark.atstest.common.util.TestLogger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by before30 on 2014. 3. 10..
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {
	public final static String STARTTIME = "STARTTIME";
	public final static String TRANSACTIONID = "TRANSACTIONID";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		long startTime = System.currentTimeMillis();
		request.setAttribute(STARTTIME, startTime);
		request.setAttribute(TRANSACTIONID, TestLogger.getTaskId());

		if (TestLogger.LOGGER.isInfoEnabled()){
			TestLogger.LOGGER.info("[{}] - Transaction Started.", request.getAttribute(TRANSACTIONID));
			TestLogger.LOGGER.info("[{}] - {}, {}, {}", request.getAttribute(TRANSACTIONID), request.getRemoteAddr(), request.getRequestURI(), request.getMethod());
		}

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		long startTime = (Long)request.getAttribute(STARTTIME);
		long endTime = System.currentTimeMillis();

		if (TestLogger.LOGGER.isInfoEnabled()){
			TestLogger.LOGGER.info("[{}] - Transaction Finished.", request.getAttribute(TRANSACTIONID));
		}
	}
} // end of class
