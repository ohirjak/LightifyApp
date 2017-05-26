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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This POJO is used for getting and setting details related to API and Gateway Version.
 */
@XmlRootElement
public class APIVersionVO {

	/**
	 * Current version of API
	 */
	private String	apiversion;
	/**
	 * Current Version of Gateway
	 */
	private String	gatewayversion;

	/**
	 * @return the apiversion
	 */
	public final String getApiversion() {
		return apiversion;
	}

	/**
	 * @param apiversion the apiversion to set
	 */
	public final void setApiversion(final String apiversion) {
		this.apiversion = apiversion;
	}

	/**
	 * @return the gatewayversion
	 */
	public final String getGatewayversion() {
		return gatewayversion;
	}

	/**
	 * @param gatewayversion the gatewayversion to set
	 */
	public final void setGatewayversion(final String gatewayversion) {
		this.gatewayversion = gatewayversion;
	}
}