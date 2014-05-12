package com.skplanet.test;

import junit.framework.TestCase;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by before30 on 2014. 5. 12..
 */
public class InsertData extends TestCase {
	@Test
	public void testInsertData(){
		// http://172.19.114.108/v1.0/ps3/yang/bench/
		// Content-Type: text/plain

		for (int i=1000; i<2000; i++) {
			String url = "http://172.19.114.108/v1.0/ps3/yang/bench/" + i;
			HttpPut putRequest = new HttpPut(url);
			putRequest.setHeader("Content-Type", "text/plain");
			File file = new File("/tmp/output/" + i + ".out");

			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				InputStreamEntity inputStreamEntity = new InputStreamEntity(fileInputStream);
				putRequest.setEntity(inputStreamEntity);
				CloseableHttpClient httpClient = HttpClients.createDefault();
				try {
					HttpResponse response = httpClient.execute(putRequest);
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode >= 200 && statusCode < 300) {
						// good
					} else {
						System.err.println("fail in " + i);
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("fail in " + i);
				}
			} catch (FileNotFoundException e) {
				System.err.println("fail in " + i);
			}
		}

	}

} // end of testclass
