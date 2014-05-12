package com.skplanet.bechmark.atstest.common.service;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by before30 on 2014. 5. 12..
 */

@Service("PlanetSpaceStorageManager")
public class PlanetSpaceStorageManager {

	Logger logger = LoggerFactory.getLogger(PlanetSpaceStorageManager.class);

	@Value("#{config['url.charset']}")
	protected String urlCharset;

	@Value("#{config['planetspace.request.trycount']}")
	private int storageRequestTryCount;

	@Value("#{config['planetspace.request.connection.timeout']}")
	private int storageRequestTimeout;

	@Value("#{config['planetspacestorage.ip']}")
	private String planetSpaceStorageIp;

	@Value("#{config['planetspacestorage.port']}")
	private int planetSpaceStoragePort;

	@Value("#{config['planetspacestorage.basepath']}")
	private String planetSpaceStorageBasePath;

	@Value("#{config['planetspacestorage.username']}")
	private String planetspaceUserName;

	@Autowired
	PoolingHttpClientConnectionManager planetSpaceStorageHttpClientConnectionPoolManager;

	public void getObject(String objectId, HttpServletRequest request, HttpServletResponse httpResponse) {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(planetSpaceStorageHttpClientConnectionPoolManager).build();
		CloseableHttpResponse response;
		HttpGet getRequest = null;

		try {
			String url = getPlanetSpaceUrl(objectId);

			getRequest = new HttpGet(url);

			this.requestConfigSetting(getRequest);
			response = httpClient.execute(getRequest);

			int statusCode;
			statusCode = response.getStatusLine().getStatusCode();

			if ((statusCode >= 200) && (statusCode < 300)){

				IOUtils.copy(response.getEntity().getContent(), httpResponse.getOutputStream());
			} else {
				httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
			}


		} catch (Exception ex) {
			httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
		} finally {
			if (getRequest != null)
				getRequest.releaseConnection();
		}
	}

	private void requestConfigSetting(HttpRequestBase request) {
		RequestConfig requestConfig =
				RequestConfig.custom()
						.setConnectionRequestTimeout(storageRequestTimeout)
						.setSocketTimeout(storageRequestTimeout)
						.build();

		request.setConfig(requestConfig);
	}


	private String getPlanetSpaceUrl(String objectId){
		StringBuilder stringBuilder = new StringBuilder("http://");
		stringBuilder.append(planetSpaceStorageIp)
				.append(":")
				.append(planetSpaceStoragePort)
				.append(planetSpaceStorageBasePath)
				.append("ps3")
				.append("/")
				.append(planetspaceUserName)
				.append("/bench/")
				.append(objectId);

		return stringBuilder.toString();
	}

} // end of class
