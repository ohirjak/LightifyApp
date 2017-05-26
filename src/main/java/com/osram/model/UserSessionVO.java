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
 * VO for UserSession
 */
public class UserSessionVO {

	/*
	 * private String errorMsg; private boolean success;
	 */
	private String	status;
	/**
	 * userId
	 */
	private String	userId;

	/**
	 * securityToken
	 */
	private String securityToken;

	private String expires;

	/**
	 * Default Constructor
	 */
	public UserSessionVO() {
		super();
	}

	/**
	 * Constructor to initialize UserSessionVO
	 *
	 * @param userId
	 * @param securityToken
	 */
	public UserSessionVO(final String userId, final String securityToken, String expires) {
		this.userId = userId;
		this.securityToken = securityToken;
		this.expires = expires;
	}

	/**
	 * @return the userId
	 */
	public final String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public final void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * @return the securityToken
	 */
	public final String getSecurityToken() {
		return securityToken;
	}

	/**
	 * @param securityToken the securityToken to set
	 */
	public final void setSecurityToken(final String securityToken) {
		this.securityToken = securityToken;
	}

	/**
	 * @return the status
	 */
	public final String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public final void setStatus(final String status) {
		this.status = status;
	}


	public String getExpires() {
		return expires;
	}


	public void setExpires(String expires) {
		this.expires = expires;
	}
}