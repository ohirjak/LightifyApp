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

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This POJO is used for internal communication of data.
 */
public final class TransferObjectVO {

	/**
	 * BASE URL
	 */
	private static String	baseURL;
	/**
	 * Security Token
	 */
	private static String	securityToken;
	/**
	 * UserName
	 */
	private static String	username;
	/**
	 * Password
	 */
	private static String	password;

	/**
	 * JAVAFX main Stage
	 */
	private static Stage	mainStage;
	/**
	 * JAVAFX login parent
	 */
	private static Parent	loginParent;
	/**
	 * JavaFX device parent
	 */
	private static Parent	deviceParent;

	/**
	 * JavaFX device Scene
	 */
	private static Scene deviceScene;

	/**
	 * Private Constructor as all methods are Static
	 */
	private TransferObjectVO() {
		super();
	}

	/**
	 * @return the securityToken
	 */
	public static String getSecurityToken() {
		return securityToken;
	}

	/**
	 * @param securityToken the securityToken to set
	 */
	public static void setSecurityToken(final String securityToken) {
		TransferObjectVO.securityToken = securityToken;
	}

	/**
	 * @return the baseURL
	 */
	public static String getBaseURL() {
		return baseURL;
	}

	/**
	 * @param baseURL the baseURL to set
	 */
	public static void setBaseURL(final String baseURL) {
		TransferObjectVO.baseURL = baseURL;
	}

	/**
	 * @return the username
	 */
	public static String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public static void setUsername(final String username) {
		TransferObjectVO.username = username;
	}

	/**
	 * @return the password
	 */
	public static String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public static void setPassword(final String password) {
		TransferObjectVO.password = password;
	}

	/**
	 * @return the loginParent
	 */
	public static Parent getLoginParent() {
		return loginParent;
	}

	/**
	 * @param loginParent the loginParent to set
	 */
	public static void setLoginParent(final Parent loginParent) {
		TransferObjectVO.loginParent = loginParent;
	}

	/**
	 * @return the deviceParent
	 */
	public static Parent getDeviceParent() {
		return deviceParent;
	}

	/**
	 * @param deviceParent the deviceParent to set
	 */
	public static void setDeviceParent(final Parent deviceParent) {
		TransferObjectVO.deviceParent = deviceParent;
	}

	/**
	 * @return the deviceScene
	 */
	public static Scene getDeviceScene() {
		return deviceScene;
	}

	/**
	 * @param deviceScene the deviceScene to set
	 */
	public static void setDeviceScene(final Scene deviceScene) {
		TransferObjectVO.deviceScene = deviceScene;
	}

	/**
	 * @return the mainStage
	 */
	public static Stage getMainStage() {
		return mainStage;
	}

	/**
	 * @param mainStage the mainStage to set
	 */
	public static void setMainStage(final Stage mainStage) {
		TransferObjectVO.mainStage = mainStage;
	}
}