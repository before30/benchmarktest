package com.skplanet.bechmark.atstest.common.controller;

import com.skplanet.bechmark.atstest.common.service.LocalStorageManager;
import com.skplanet.bechmark.atstest.common.service.PlanetSpaceStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by before30 on 2014. 5. 12..
 */

@Controller
@RequestMapping("/")
public class APIController {

	@Autowired
	LocalStorageManager localStorageManager;

	@Autowired
	PlanetSpaceStorageManager planetSpaceStorageManager;

	@RequestMapping(value="/local/{objectId}", method = RequestMethod.GET)
	public void getLocalData(@PathVariable String objectId, HttpServletRequest request, HttpServletResponse response){

		localStorageManager.getObject(objectId, request, response);
	}

	@RequestMapping(value="/nas/{objectId}", method = RequestMethod.GET)
	public ResponseEntity<String> getNasData(@PathVariable String objectId, HttpServletRequest request){

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");

		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("NAS GOOD");

		ResponseEntity<String> entity = getResponseEntity(responseHeaders, strBuffer, HttpStatus.OK);

		return entity;
	}

	@RequestMapping(value="/sgw/{objectId}", method = RequestMethod.GET)
	public void getSgwData(@PathVariable String objectId, HttpServletRequest request, HttpServletResponse response){
		planetSpaceStorageManager.getObject(objectId, request, response);
	}



	private ResponseEntity<String> getResponseEntity( HttpHeaders responseHeaders,StringBuffer stringBuffer, HttpStatus httpStatus){
		ResponseEntity<String> entity = new ResponseEntity<>(stringBuffer.toString(), responseHeaders, httpStatus);

		return entity;
	}

} // end of class
