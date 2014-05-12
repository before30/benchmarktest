package com.skplanet.bechmark.atstest.common.service;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by before30 on 2014. 5. 12..
 */

@Service("LocalStorageManager")
public class LocalStorageManager {
	@Value("#{config['localstorage.path']}")
	private String localstoragePath;

	private Logger logger = LoggerFactory.getLogger(LocalStorageManager.class);

	public void getObject(String objectId, HttpServletRequest request, HttpServletResponse response) {
		logger.info("localStorageManager getObject " + localstoragePath + "," + objectId);
		File file = new File(localstoragePath + objectId + ".out");

		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			response.setHeader("Content-type", MediaType.TEXT_PLAIN_VALUE);
			IOUtils.copy(fileInputStream, response.getOutputStream());

		} catch (FileNotFoundException e) {
			logger.info("file not found exception " + e.getMessage());
			response.setStatus(HttpStatus.NOT_FOUND.value());
		} catch (IOException e) {
			logger.info("IOException " + e.getMessage());
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}

} // end of class
