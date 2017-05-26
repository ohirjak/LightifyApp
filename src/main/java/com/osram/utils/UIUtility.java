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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osram.model.DeviceVO;
import com.osram.model.GroupVO;
import com.osram.model.RequestFaultVO;
import com.osram.model.SceneVO;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This is an utility class for UI related requirement.
 */
public final class UIUtility {

	/**
	 * LOGGER
	 */
	private static final Logger	LOGGER	= LoggerFactory.getLogger(UIUtility.class);
	/**
	 * Alert Dialog box
	 */
	private static final Alert	ALERT	= new Alert(AlertType.ERROR);

	/**
	 * Alert Header for Error
	 */
	private static final String ERROR_HEADER = "Error";

	/**
	 * private constructor so that method can be accessed in Static way only.
	 */
	private UIUtility() {
	};

	/**
	 * This method is used to build tool tip for Light
	 *
	 * @param lightVO LightVO
	 * @return string of tool tip
	 */
	public static String getLightToolTip(final DeviceVO deviceVO) {
		final StringBuilder stringBldr = new StringBuilder(100);
		stringBldr.append(concatNewLine("Name : " + deviceVO.getName())).append(concatNewLine("On : " + deviceVO.getOn()))
				.append(concatNewLine("Model : " + deviceVO.getModelName())).append(concatNewLine("Color : " + deviceVO.getHue()))
				.append(concatNewLine("Brightness : " + deviceVO.getBrightnessLevel())).append(concatNewLine("Temperature : " + deviceVO.getTemperature()))
				.append(concatNewLine("Features : " + getSupportedFeatures(deviceVO.getBmpClusters())));
		return stringBldr.toString();
	}

	/**
	 * This method is used to build tool tip for Group
	 *
	 * @param groupVO GroupVO
	 * @return string of tool tip
	 */
	public static String getGroupToolTip(final GroupVO groupVO) {
		final StringBuilder stringBldr = new StringBuilder(50);
		stringBldr.append(concatNewLine("Name : " + groupVO.getName()));
		return stringBldr.toString();
	}

	/**
	 * This method is used to build tool tip for Scene
	 *
	 * @param sceneVO SceneVO
	 * @return string of tool tip
	 */
	public static String getSceneToolTip(final SceneVO sceneVO) {
		final StringBuilder stringBldr = new StringBuilder(20);
		stringBldr.append(concatNewLine("Name : " + sceneVO.getName()));
		return stringBldr.toString();
	}

	/**
	 * This method is used to get list of features supported by Light.
	 *
	 * @param featureName : List of features.
	 * @return String of supported features.
	 */
	private static String getSupportedFeatures(final List<String> featureList) {
		final StringBuilder stringBldr = new StringBuilder(20);
		if (featureList != null && !featureList.isEmpty()) {
			for (final String featureName : featureList) {
				stringBldr.append(concatNewLine(featureName));
			}
		}
		return stringBldr.toString();
	}

	/**
	 * This method is used to append NEW Line Character to input string.
	 *
	 * @param input Input data to which NEW LINE (\n) characters needs to be connected
	 * @return input + \n
	 */
	private static String concatNewLine(final String input) {
		return input.concat(ApplicationConstants.NEW_LINE);
	}

	/**
	 * This method is used to convert boolean to Yes/No string.
	 *
	 * @param isOn true if on else false
	 * @return Yes when light is On else No
	 */
	public static Integer getDeviceOn(final String isOn) {
		return ApplicationConstants.ON.equalsIgnoreCase(isOn) ? ApplicationConstants.DEVICE_ON : ApplicationConstants.DEVICE_OFF;
	}

	/**
	 * This message is used to show error message as is in ALERT dialog
	 *
	 * @param errorMsg Error message to be displayed
	 */
	public static void showSimpleError(final String errorMsg) {
		ALERT.setTitle(ERROR_HEADER);
		ALERT.setHeaderText(ERROR_HEADER);
		ALERT.setContentText(errorMsg);
		ALERT.showAndWait();
	}

	/**
	 * This method is used to show the JSON error message by converting the message into Object and then extracting the
	 * message from the object
	 *
	 * @param jsonErrorMsg JSON error message.
	 */
	public static void showJsonError(final String jsonErrorMsg) {
		showSimpleError(getFormattedErrorMsg(jsonErrorMsg));
	}

	/**
	 * This method converts the JSON message into object and and then extracting the message from the object
	 *
	 * @param jsonErrorMsg JSON error message.
	 * @return String of error message separated by new line(/n)
	 */
	private static String getFormattedErrorMsg(final String jsonErrorMsg) {
		final StringBuilder errorMsgBlder = new StringBuilder(50);
		final List<RequestFaultVO> faultVOList = new ArrayList<RequestFaultVO>();
		try {
			final ObjectMapper mapper = new ObjectMapper();
			if (jsonErrorMsg.startsWith(ApplicationConstants.JSON_LIST_START)) {
				faultVOList.addAll(Arrays.asList(mapper.readValue(jsonErrorMsg, RequestFaultVO[].class)));
			} else {
				faultVOList.add(new ObjectMapper().readValue(jsonErrorMsg, RequestFaultVO.class));
			}
			for (final RequestFaultVO requestFaultVO : faultVOList) {
				errorMsgBlder.append(requestFaultVO.getErrorMessage());
				errorMsgBlder.append(ApplicationConstants.NEW_LINE);
			}
		} catch (final Exception e) {
			LOGGER.error("Error occured while formating the JSON error message", e);
		}
		return errorMsgBlder.toString();
	}
}