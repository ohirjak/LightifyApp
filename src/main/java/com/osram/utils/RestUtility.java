/*******************************************************************************
 * Copyright 2015, OSRAM GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *******************************************************************************
 *
 * OSRAM License Details : Refer OSRAM License agreement included in Package
 *
 *******************************************************************************/
package com.osram.utils;

import static com.jayway.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.osram.model.DataVO;

/**
 * This is an utility class for REST web service request and response.
 */
public final class RestUtility {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RestUtility.class);

	/**
	 * private constructor so that method can be accessed in Static way only.
	 */
	private RestUtility() {
	};

	/**
	 * This method acts as a controller for executing various REST request.
	 *
	 * @param dataVO : Input data for request
	 * @return Response from REST API
	 */
	@SuppressWarnings(ApplicationConstants.RAWTYPES)
	public static Response execute(final DataVO dataVO) {
		final String executeMethod = dataVO.getRequestMethod();
		LOGGER.info("URL Requested {} and method {}", dataVO.getUrl(), executeMethod);
		Response returnResponse = null;
		switch (executeMethod.toUpperCase(Locale.getDefault())) {
			case ApplicationConstants.REQUEST_METHOD_POST:
				returnResponse = executePostRequest(dataVO);
				break;
			case ApplicationConstants.REQUEST_METHOD_GET:
				returnResponse = executeGetRequest(dataVO);
				break;
			case ApplicationConstants.REQUEST_METHOD_DELETE:
				returnResponse = executeDeleteRequest(dataVO);
				break;
			default:
				break;
		}
		return returnResponse;
	}

	/**
	 * This method is used to execute POST request.
	 *
	 * @param dataVO : Input data for request
	 * @return Response from REST API
	 */
	@SuppressWarnings(ApplicationConstants.RAWTYPES)
	private static Response executePostRequest(final DataVO dataVO) {
		Response response = null;
		try {
			RestAssured.baseURI = dataVO.getUrl();
			final Map<String, String> headerMap = getHeaderMap(dataVO.getHeader());
			if (dataVO.getRequestBody() == null) {
				response = given().relaxedHTTPSValidation().contentType(dataVO.getContentType()).headers(headerMap).when().post(ApplicationConstants.BLANK);
			} else {
				response = given().relaxedHTTPSValidation().contentType(dataVO.getContentType()).headers(headerMap).body(dataVO.getRequestBody()).when()
						.post(ApplicationConstants.BLANK);
			}
		} catch (final Exception e) {
			LOGGER.error("Error occured while executing POST request ", e);
		}
		return response;
	}

	/**
	 * This method is used to execute GET request.
	 *
	 * @param dataVO : Input data for request
	 * @return Response from REST API
	 */
	@SuppressWarnings(ApplicationConstants.RAWTYPES)
	private static Response executeGetRequest(final DataVO dataVO) {
		Response response = null;
		try {
			// RestAssured.baseURI = dataVO.getUrl();
			final Map<String, String> headerMap = getHeaderMap(dataVO.getHeader());
			response = given().relaxedHTTPSValidation().contentType(dataVO.getContentType()).headers(headerMap).when().get(dataVO.getUrl());
		} catch (final Exception e) {
			LOGGER.error("Error occured while executing GET request ", e);
		}
		return response;
	}

	/**
	 * This method is used to execute DELETE request.
	 *
	 * @param dataVO : Input data for request
	 * @return Response from REST API
	 */
	@SuppressWarnings(ApplicationConstants.RAWTYPES)
	private static Response executeDeleteRequest(final DataVO dataVO) {
		Response response = null;
		try {
			RestAssured.baseURI = dataVO.getUrl();
			final Map<String, String> headerMap = getHeaderMap(dataVO.getHeader());
			response = given().relaxedHTTPSValidation().contentType(dataVO.getContentType()).headers(headerMap).when().delete();
		} catch (final Exception e) {
			LOGGER.error("Error occured while executing DELETE request ", e);
		}
		return response;
	}

	/**
	 * This method is used to build header map from header data.
	 *
	 * @param headerData header data separated by COMMA(,) and internally by COLON (:).
	 * @return map of Header name and value
	 */
	private static Map<String, String> getHeaderMap(final String headerData) {
		final Map<String, String> headerMap = new HashMap<String, String>();
		if (headerData != null) {
			final String[] headerList = headerData.split(ApplicationConstants.COMMA);
			for (final String headers : headerList) {
				final String[] headerDataArr = headers.split(ApplicationConstants.COLON);
				headerMap.put(headerDataArr[0], headerDataArr[1]);
			}
		}
		return headerMap;
	}
}