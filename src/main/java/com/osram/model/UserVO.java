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
 * POJO for User
 */
public class UserVO {

	/**
	 * User Name
	 */
	private String	username;
	/**
	 * Password
	 */
	private String	password;

	/**
	 * Password
	 */
	private String serialNumber;

	/**
	 * Constructor to initialize UserVO
	 *
	 * @param userName
	 * @param password
	 * @param serialNumber
	 */
	public UserVO(final String userName, final String password, final String serialNumber) {
		this.username = userName;
		this.password = password;
		this.serialNumber = serialNumber;
	}

	/**
	 * @return the user name
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public final void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @return the serialNumber
	 */
	public final String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber the serialNumber to set
	 */
	public final void setSerialNumber(final String serialNumber) {
		this.serialNumber = serialNumber;
	}
}