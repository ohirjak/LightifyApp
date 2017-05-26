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
 * This POJO is used for storing the login Data
 */
public class LoginDataVO {
	/**
	 * User Name
	 */
	private String	username;
	/**
	 * Password
	 */
	private String	password;

	/**
	 * Environment
	 */
	private String environment;

	/**
	 * Device Name
	 */
	private String deviceName;

	/**
	 * Remember Me
	 */
	private boolean saveUserConfig;

	/**
	 * @return the username
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
	 * @return the saveUserConfig
	 */
	public final boolean isSaveUserConfig() {
		return saveUserConfig;
	}

	/**
	 * @param saveUserConfig the saveUserConfig to set
	 */
	public final void setSaveUserConfig(final boolean saveUserConfig) {
		this.saveUserConfig = saveUserConfig;
	}

	/**
	 * @return the environment
	 */
	public final String getEnvironment() {
		return environment;
	}

	/**
	 * @param environment the environment to set
	 */
	public final void setEnvironment(final String environment) {
		this.environment = environment;
	}

	/**
	 * @return the deviceName
	 */
	public final String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public final void setDeviceName(final String deviceName) {
		this.deviceName = deviceName;
	}
}