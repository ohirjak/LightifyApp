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
package com.osram.model;

/**
 * This POJO is used to build request object for the REST API
 * 
 * @param <T> : Object that needs to be passed in request body.
 */
public class DataVO<T> {

	/**
	 * URL
	 */
	private String	url;
	/**
	 * Content Type
	 */
	private String	contentType;
	/**
	 * Request Method
	 */
	private String	requestMethod;
	/**
	 * Header
	 */
	private String	header;
	/**
	 * Request Body
	 */
	private T		requestBody;

	/**
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public final void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the contentType
	 */
	public final String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public final void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the requestMethod
	 */
	public final String getRequestMethod() {
		return requestMethod;
	}

	/**
	 * @param requestMethod the requestMethod to set
	 */
	public final void setRequestMethod(final String requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * @return the header
	 */
	public final String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public final void setHeader(final String header) {
		this.header = header;
	}

	/**
	 * @return the requestBody
	 */
	public final T getRequestBody() {
		return requestBody;
	}

	/**
	 * @param requestBody the requestBody to set
	 */
	public final void setRequestBody(final T requestBody) {
		this.requestBody = requestBody;
	}

}