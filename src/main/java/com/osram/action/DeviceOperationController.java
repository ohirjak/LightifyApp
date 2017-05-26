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
package com.osram.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.response.Response;
import com.osram.model.DataVO;
import com.osram.model.TransferObjectVO;
import com.osram.utils.ApplicationConstants;
import com.osram.utils.RestUtility;
import com.osram.utils.UIUtility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * This class is the basic controller class for performing operation on devices.
 */
public class DeviceOperationController extends DeviceLoadController {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceOperationController.class);

	@FXML
	private transient ComboBox<String> operationToPerformId, statusChangeComboId, themChangeId;

	@FXML
	private transient TextField operationValueId;

	@FXML
	private transient ColorPicker colorChooserId;

	@FXML
	private transient Button refreshBtnId, logoutBtnId;

	/**
	 * This method is called when theme of the application is changed.
	 *
	 * @param event ActionEvent
	 */
	@FXML
	private void themeChangeAction(final ActionEvent event) {
		switch (themChangeId.getSelectionModel().getSelectedItem()) {
			case "THEME1":
				TransferObjectVO.getDeviceScene().getStylesheets().remove(ApplicationConstants.THEME2);
				TransferObjectVO.getDeviceScene().getStylesheets().add(ApplicationConstants.THEME1);
				break;
			case "THEME2":
				TransferObjectVO.getDeviceScene().getStylesheets().remove(ApplicationConstants.THEME1);
				TransferObjectVO.getDeviceScene().getStylesheets().add(ApplicationConstants.THEME2);
				break;
			case "BASIC":
				TransferObjectVO.getDeviceScene().getStylesheets().remove(ApplicationConstants.THEME1);
				TransferObjectVO.getDeviceScene().getStylesheets().remove(ApplicationConstants.THEME2);
				break;
			default:
				TransferObjectVO.getDeviceScene().getStylesheets().remove(ApplicationConstants.THEME1);
				TransferObjectVO.getDeviceScene().getStylesheets().remove(ApplicationConstants.THEME2);
				break;
		}
	}

	/**
	 * This method is called when operation to perform is changed.
	 *
	 * @param event ActionEvent
	 */
	@FXML
	private void operationChangeAction(final ActionEvent event) {
		operationValueId.setText(ApplicationConstants.BLANK);
		switch (operationToPerformId.getSelectionModel().getSelectedItem()) {
			case "CHANGE_STATE":
				temperatureSliderId.setVisible(false);
				statusChangeComboId.setVisible(true);
				statusChangeComboId.setValue(ApplicationConstants.BLANK);
				brightnessSliderId.setVisible(false);
				colorChooserId.setVisible(false);
				break;
			case "CHANGE_RGB":
			case "CHANGE_COLOR":
				temperatureSliderId.setVisible(false);
				statusChangeComboId.setVisible(false);
				brightnessSliderId.setVisible(false);
				colorChooserId.setVisible(true);
				colorSelection(null);
				break;
			case "CHANGE_BRIGHTNESS":
				temperatureSliderId.setVisible(false);
				statusChangeComboId.setVisible(false);
				brightnessSliderId.setVisible(true);
				colorChooserId.setVisible(false);
				brightnessSliderId.setValue(0.00);
				break;
			case "CHANGE_TEMPERATURE":
				temperatureSliderId.setVisible(true);
				statusChangeComboId.setVisible(false);
				brightnessSliderId.setVisible(false);
				colorChooserId.setVisible(false);
				temperatureSliderId.setValue(0.00);
				break;
			case "APPLY_SCENE":
				temperatureSliderId.setVisible(false);
				statusChangeComboId.setVisible(false);
				brightnessSliderId.setVisible(false);
				colorChooserId.setVisible(false);
				break;
			default:
				break;
		}
	}

	/**
	 * This method is called when ON/OFF operation is changed.
	 *
	 * @param event ActionEvent
	 */
	@FXML
	private void statusOnOFF(final ActionEvent event) {
		operationValueId.setText(statusChangeComboId.getSelectionModel().getSelectedItem());
	}

	/**
	 * This method is called when Color Selection operation is changed.
	 *
	 * @param event ActionEvent
	 */
	@FXML
	private void colorSelection(final ActionEvent event) {
		final String colorCode = "#" + Integer.toHexString(colorChooserId.getValue().hashCode()).substring(0, 6).toUpperCase();
		operationValueId.setText(colorCode);
	}

	/**
	 * This method is called when logout operation is performed.
	 *
	 * @param event ActionEvent
	 */
	@FXML
	private void logout(final ActionEvent event) {
		LOGGER.info("Logout Event Called");
		final DataVO<String> logOutDataVO = new DataVO<String>();

		logOutDataVO.setRequestBody(null);
		logOutDataVO.setContentType(ApplicationConstants.CONTENT_TYPE_JSON);
		logOutDataVO.setRequestMethod(ApplicationConstants.REQUEST_METHOD_DELETE);
		logOutDataVO.setHeader(ApplicationConstants.HD_AUTHORIZATION + ApplicationConstants.COLON + TransferObjectVO.getSecurityToken());
		logOutDataVO.setUrl(TransferObjectVO.getBaseURL() + ApplicationConstants.URL_SESSION);

		final Response response = RestUtility.execute(logOutDataVO);
		if (response.getStatusCode() == 200) {
			LOGGER.info("Logged out successfully");
			TransferObjectVO.setSecurityToken(null);
			TransferObjectVO.setUsername(null);
			TransferObjectVO.setPassword(null);
			new LoginController().loginUI();
		} else {
			UIUtility.showJsonError(response.asString());
		}
	}

	/**
	 * This method is called when refresh operation is performed.
	 *
	 * @param event ActionEvent
	 */
	@FXML
	private void refresh(final ActionEvent event) {
		getAllDevices();
	}
}