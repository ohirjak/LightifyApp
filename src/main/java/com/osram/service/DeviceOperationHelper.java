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

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.response.Response;
import com.osram.model.DataVO;
import com.osram.model.DeviceOperationVO;
import com.osram.model.DeviceVO;
import com.osram.model.OperationType;
import com.osram.model.ReturnObjectVO;
import com.osram.model.TransferObjectVO;
import com.osram.utils.ApplicationConstants;
import com.osram.utils.RestUtility;
import com.osram.utils.UIUtility;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

/**
 * This is a helper class for performing device operation.
 */
public class DeviceOperationHelper {
	private static final String		COLOR_START_HASH		= "#";
	/**
	 * LOGGER
	 */
	private static final Logger		LOGGER					= LoggerFactory.getLogger(DeviceOperationHelper.class);
	private static String			decimalPattern			= "###.###";
	private static String			intPattern				= "###";
	private static DecimalFormat	decimalFormat			= new DecimalFormat(decimalPattern);
	private static DecimalFormat	intFormat				= new DecimalFormat(intPattern);
	private static final String		ERR_INVALID_OPERTION	= "Please select valid operation";
	private static final String		DEVICE_IS_ALREADY_ON	= "Device is already ON.";
	private static final String		ERR_DEVICE_OFF			= "Operation cannot be performed as device is OFF";

	/**
	 * This method is used to validate the operation that is been performed.
	 *
	 * @param object Light, Group or Scene Object
	 * @param deviceOperationVO DeviceOperationVO
	 * @return true is successful else false.
	 */
	public static ReturnObjectVO validate(final Object object, final DeviceOperationVO deviceOperationVO) {
		boolean isValidateOperation = true;
		final OperationType operationType = deviceOperationVO.getOperationType();
		final ReturnObjectVO returnObjectVO = new ReturnObjectVO();
		String errorMessage = null;
		Integer isOn = ApplicationConstants.DEVICE_OFF;
		if (!OperationType.SCENE_RECALL.equals(deviceOperationVO.getOperationType())
				&& (deviceOperationVO.getOperationValue() == null || ApplicationConstants.BLANK.equals(deviceOperationVO.getOperationValue()))) {
			isValidateOperation = false;
			errorMessage = DeviceOperationHelper.ERR_INVALID_OPERTION;
		} else if (object instanceof DeviceVO) {
			final DeviceVO deviceVO = (DeviceVO) object;
			isOn = deviceVO.getOn();
			if (OperationType.CHANGE_STATE.equals(operationType)) {
				if (isOn == ApplicationConstants.DEVICE_ON && isOn.equals(deviceOperationVO.getOperationValue())) {
					errorMessage = DeviceOperationHelper.DEVICE_IS_ALREADY_ON;
					isValidateOperation = false;
				} else if (isOn.equals(deviceOperationVO.getOperationValue())) {
					errorMessage = DeviceOperationHelper.ERR_DEVICE_OFF;
					isValidateOperation = false;
				}
			} else if (isOn == ApplicationConstants.DEVICE_OFF) {
				errorMessage = DeviceOperationHelper.ERR_DEVICE_OFF;
				isValidateOperation = false;
			}
		}
		returnObjectVO.setStatus(isValidateOperation);
		returnObjectVO.setMessage(errorMessage);
		return returnObjectVO;
	}

	/**
	 * This method is used to perform operation on Light, Group and Scene.
	 *
	 * @param button Button
	 * @param object Light, Group or Scene Object
	 * @param deviceOperationVO DeviceOperationVO
	 * @return true is operation performed successfully else false
	 */

	public static ReturnObjectVO performOperation(final Button button, final Object object, final DeviceOperationVO deviceOperationVO) {
		LOGGER.info("Device Operation Action Called {}", deviceOperationVO.getOperationOn());
		ReturnObjectVO returnObjectVO = null;
		try {
			switch (deviceOperationVO.getOperationOn()) {
				case ApplicationConstants.LIGHT:
					returnObjectVO = performOperation(button, object, ApplicationConstants.URL_LIGHT_OPERATION, deviceOperationVO);
					break;
				case ApplicationConstants.GROUP:
					returnObjectVO = performOperation(button, object, ApplicationConstants.URL_GROUP_OPERATION, deviceOperationVO);
					break;
				case ApplicationConstants.SCENE:
					returnObjectVO = performOperation(button, object, ApplicationConstants.URL_SCENE_OPERATION, deviceOperationVO);
					break;
				case ApplicationConstants.LIGHT_BROADCAST:
					returnObjectVO = performOperation(button, object, ApplicationConstants.URL_BROADCAST_OPERATION, deviceOperationVO);
					break;
				default:
					break;
			}
		} catch (final Exception e) {
			LOGGER.error("Error occured while performing operation", e);
		}
		return returnObjectVO;
	}

	/**
	 * This method is used to perform actual operation by calling the REST API.
	 *
	 * @param button Button
	 * @param object Light, Group or Scene Object
	 * @param endPoint end point on which operation needs to be performed.
	 * @param deviceOperationVO DeviceOperationVO
	 * @return true is successful else false.
	 */
	private static ReturnObjectVO performOperation(final Button button, final Object object, final String endPoint, final DeviceOperationVO deviceOperationVO) {
		final ReturnObjectVO returnObjectVO = new ReturnObjectVO();
		boolean operationPerformed = false;
		LOGGER.info("Operation Type : {} URL {}", deviceOperationVO.getOperationType(), endPoint);

		final DataVO<DeviceOperationVO> deviceOperationDataVO = new DataVO<DeviceOperationVO>();
		deviceOperationDataVO.setContentType(ApplicationConstants.CONTENT_TYPE_JSON);
		deviceOperationDataVO.setRequestMethod(ApplicationConstants.REQUEST_METHOD_GET);
		deviceOperationDataVO.setRequestBody(deviceOperationVO);
		deviceOperationDataVO.setHeader(ApplicationConstants.HD_AUTHORIZATION + ApplicationConstants.COLON + TransferObjectVO.getSecurityToken());

		deviceOperationDataVO.setUrl(TransferObjectVO.getBaseURL() + endPoint + buildGetURL(deviceOperationVO));
		final Response deviceOperation = RestUtility.execute(deviceOperationDataVO);
		if (deviceOperation.getStatusCode() == 200) {
			LOGGER.info("Operation performed Sucessfully");
			operationPerformed = true;
		}
		returnObjectVO.setStatus(operationPerformed);
		returnObjectVO.setMessage(deviceOperation.asString());
		return returnObjectVO;
	}

	private static String buildGetURL(final DeviceOperationVO deviceOperationVO) {
		final StringBuilder getURLAppender = new StringBuilder();
		deviceOperationVO.setFadingTime(0);

		switch (deviceOperationVO.getOperationType()) {
			case BROADCAST:
				buildGetURL(ApplicationConstants.PARAM_TIME, deviceOperationVO.getFadingTime(), getURLAppender);
				buildGetURL(ApplicationConstants.PARAM_ONOFF, UIUtility.getDeviceOn(deviceOperationVO.getOperationValue()), getURLAppender);
				break;
			case CHANGE_STATE:
				buildGetURL(ApplicationConstants.PARAM_IDX, deviceOperationVO.getDeviceId(), getURLAppender);
				buildGetURL(ApplicationConstants.PARAM_TIME, deviceOperationVO.getFadingTime(), getURLAppender);
				buildGetURL(ApplicationConstants.PARAM_ONOFF, UIUtility.getDeviceOn(deviceOperationVO.getOperationValue()), getURLAppender);
				break;
			case CHANGE_BRIGHTNESS:
				final Double brightness = Double.valueOf(deviceOperationVO.getOperationValue()) / 100;
				buildGetURL(ApplicationConstants.PARAM_IDX, deviceOperationVO.getDeviceId(), getURLAppender);
				buildGetURL(ApplicationConstants.PARAM_TIME, deviceOperationVO.getFadingTime(), getURLAppender);
				buildGetURL(ApplicationConstants.PARAM_LEVEL, brightness, getURLAppender);
				break;
			case CHANGE_RGB:
				buildGetURL(ApplicationConstants.PARAM_IDX, deviceOperationVO.getDeviceId(), getURLAppender);
				buildGetURL(ApplicationConstants.PARAM_TIME, deviceOperationVO.getFadingTime(), getURLAppender);
				if (deviceOperationVO.getOperationValue().startsWith(DeviceOperationHelper.COLOR_START_HASH)) {
					buildGetURL(ApplicationConstants.PARAM_COLOR, deviceOperationVO.getOperationValue().substring(1), getURLAppender);
				} else {
					buildGetURL(ApplicationConstants.PARAM_COLOR, deviceOperationVO.getOperationValue(), getURLAppender);
				}
				break;
			case CHANGE_COLOR:
				final Color color = Color.valueOf(deviceOperationVO.getOperationValue());
				buildGetURL(ApplicationConstants.PARAM_IDX, deviceOperationVO.getDeviceId(), getURLAppender);
				buildGetURL(ApplicationConstants.PARAM_TIME, deviceOperationVO.getFadingTime(), getURLAppender);
				buildGetURL(ApplicationConstants.PARAM_HUE, intFormat.format(color.getHue()), getURLAppender);
				buildGetURL(ApplicationConstants.PARAM_SATURATION, decimalFormat.format(color.getSaturation()), getURLAppender);
				break;
			case CHANGE_TEMPERATURE:
				buildGetURL(ApplicationConstants.PARAM_IDX, deviceOperationVO.getDeviceId(), getURLAppender);
				buildGetURL(ApplicationConstants.PARAM_TIME, deviceOperationVO.getFadingTime(), getURLAppender);
				buildGetURL(ApplicationConstants.PARAM_CTEMP, Integer.valueOf(deviceOperationVO.getOperationValue()), getURLAppender);
				break;
			case SCENE_RECALL:
				buildGetURL(ApplicationConstants.PARAM_SCENE_ID, deviceOperationVO.getDeviceId(), getURLAppender);
				break;
			default:
				break;
		}
		return getURLAppender.substring(0, getURLAppender.length() - 1);
	}

	private static void buildGetURL(final String key, final String value, final StringBuilder stringBuilder) {
		stringBuilder.append(key).append("=").append(value).append("&");
	}

	private static void buildGetURL(final String key, final Integer value, final StringBuilder stringBuilder) {
		stringBuilder.append(key).append("=").append(value).append("&");
	}

	private static void buildGetURL(final String key, final double value, final StringBuilder stringBuilder) {
		stringBuilder.append(key).append("=").append(value).append("&");
	}

	/**
	 * This method is to update the UI after operation is been performed.
	 *
	 * @param button Button
	 * @param object Light, Group or Scene Object
	 * @param deviceOperationVO DeviceOperationVO
	 */
	public static void updateUIAfterOperation(final Button button, final Object object, final DeviceOperationVO deviceOperationVO) {
		try {
			final String operationValue = deviceOperationVO.getOperationValue();
			if (OperationType.CHANGE_COLOR.equals(deviceOperationVO.getOperationType())) {
				// button.setStyle("-fx-background-color: " + operationValue + ";");
			}
			final String tooltip = ApplicationConstants.BLANK;
			Integer isOn = ApplicationConstants.DEVICE_ON;
			final OperationType operationType = deviceOperationVO.getOperationType();
			if (object instanceof DeviceVO) {
				final DeviceVO deviceTableVO = (DeviceVO) object;
				if (OperationType.CHANGE_COLOR.equals(operationType)) {
					// button.setStyle("-fx-background-color: " + operationValue + ";");
				} else if (OperationType.CHANGE_BRIGHTNESS.equals(operationType)) {
					deviceTableVO.setBrightnessLevel(Float.valueOf(operationValue));
				} else if (OperationType.CHANGE_TEMPERATURE.equals(operationType)) {
					deviceTableVO.setTemperature(Integer.valueOf(operationValue));
				} else if (OperationType.CHANGE_STATE.equals(operationType)) {
					deviceTableVO.setOn(UIUtility.getDeviceOn(operationValue));
					isOn = deviceTableVO.getOn();
				}
			}
			if (!ApplicationConstants.BLANK.equals(tooltip)) {
				final Tooltip toolTip = new Tooltip();
				toolTip.setText(tooltip);
				button.setTooltip(toolTip);
			}
			if (ApplicationConstants.DEVICE_ON.equals(isOn)) {
				button.setStyle("-fx-font-weight: bold;");
				// button.setGraphic(new ImageView(DeviceLoadController.deviceOnImg));
			} else {
				button.setGraphic(null);
				button.setStyle("-fx-font-weight: normal;");
			}
		} catch (final Exception e) {
			LOGGER.error("Error occured while performing operation", e);
		} finally {
			button.setDisable(false);
		}
	}
}