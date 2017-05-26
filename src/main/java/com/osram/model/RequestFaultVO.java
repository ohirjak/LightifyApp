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
 * This class is used to send fault response to the user.
 */
public class RequestFaultVO {

	/**
	 * Error Code
	 */
	protected String errorCode;

	/**
	 * Error Message
	 */
	protected String errorMessage;

	/**
	 * @return the errorCode
	 */
	public final String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public final void setErrorCode(final String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public final String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public final void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
