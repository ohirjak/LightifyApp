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
package com.osram.service;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.response.Response;
import com.osram.model.DataVO;
import com.osram.model.LoginDataVO;
import com.osram.model.ReturnObjectVO;
import com.osram.model.TransferObjectVO;
import com.osram.model.UserSessionVO;
import com.osram.model.UserVO;
import com.osram.utils.ApplicationConstants;
import com.osram.utils.ConfigurationUtility;
import com.osram.utils.RestUtility;

/**
 * Helper class for login service
 */
public final class LoginServiceHelper {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceHelper.class);

	/**
	 * Private constructor so that method can be accessed only in static way
	 */
	private LoginServiceHelper() {
	};

	/**
	 * This method is used to update user default configuration
	 */
	private static void updateUserConfiguration(final LoginDataVO loginDataVO) {
		final Map<String, String> userConfigMap = new HashMap<String, String>();
		if (loginDataVO.isSaveUserConfig()) {
			userConfigMap.put(ApplicationConstants.REMEMBER_ME, ApplicationConstants.YES);
			userConfigMap.put(ApplicationConstants.USERNAME, loginDataVO.getUsername());
			userConfigMap.put(ApplicationConstants.PASSWORD, loginDataVO.getPassword());
			userConfigMap.put(ApplicationConstants.ENVIRONMENT, loginDataVO.getEnvironment());
			userConfigMap.put(ApplicationConstants.DEVICE_NAME, loginDataVO.getDeviceName());

		} else {
			userConfigMap.put(ApplicationConstants.REMEMBER_ME, ApplicationConstants.NO);
			userConfigMap.put(ApplicationConstants.USERNAME, ApplicationConstants.BLANK);
			userConfigMap.put(ApplicationConstants.PASSWORD, ApplicationConstants.BLANK);
			userConfigMap.put(ApplicationConstants.DEVICE_NAME, ApplicationConstants.BLANK);
			userConfigMap.put(ApplicationConstants.ENVIRONMENT, ApplicationConstants.DEFAULT_ENVIRONMENT);
		}
		ConfigurationUtility.setUserConfig(userConfigMap);
	}

	/**
	 * This method is used to authenticate the user from REST API.
	 *
	 * @return true if authenticated else false.
	 */
	public static ReturnObjectVO authenticate(final LoginDataVO loginDataVO) {
		final ReturnObjectVO returnObjectVO = new ReturnObjectVO();
		boolean isAuthenticate = false;
		TransferObjectVO.setUsername(loginDataVO.getUsername());
		TransferObjectVO.setPassword(loginDataVO.getPassword());

		final UserVO userVO = new UserVO(TransferObjectVO.getUsername(), TransferObjectVO.getPassword(), loginDataVO.getDeviceName());

		final DataVO<UserVO> loginRequestVO = new DataVO<UserVO>();

		loginRequestVO.setRequestBody(userVO);
		loginRequestVO.setContentType(ApplicationConstants.CONTENT_TYPE_JSON);
		loginRequestVO.setRequestMethod(ApplicationConstants.REQUEST_METHOD_POST);
		loginRequestVO.setUrl(TransferObjectVO.getBaseURL() + ApplicationConstants.URL_SESSION);

		try {
			final Response response = RestUtility.execute(loginRequestVO);
			final String json = response.asString();
			// final String json ="{\"securityToken\":\"113533-iNNbPUf05QcAPnJ13rWV\", \"userId\":\"113533\"}";
			if (response.getStatusCode() == 200) {
				final UserSessionVO userSessionVO = new ObjectMapper().readValue(json, UserSessionVO.class);
				TransferObjectVO.setSecurityToken(userSessionVO.getSecurityToken());
				isAuthenticate = true;
				updateUserConfiguration(loginDataVO);
			} else {
				isAuthenticate = false;
			}
			returnObjectVO.setStatus(isAuthenticate);
			returnObjectVO.setMessage(json);
		} catch (final Exception e) {
			LOGGER.error("Error occured while login into application", e);
		}
		return returnObjectVO;
	}
}
