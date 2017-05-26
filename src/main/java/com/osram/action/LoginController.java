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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osram.model.LoginDataVO;
import com.osram.model.TransferObjectVO;
import com.osram.service.LoginService;
import com.osram.utils.ApplicationConstants;
import com.osram.utils.ConfigurationUtility;
import com.osram.utils.UIUtility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

/**
 * This class is used as a controller for Login.
 */
public class LoginController implements Initializable {
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@FXML
	private TextField userNameId, deviceName;

	@FXML
	private PasswordField pwdId;

	@FXML
	private Button loginBtn, resetBtn;

	@FXML
	private ComboBox<String> environmentId;

	@FXML
	private CheckBox rememberMeId;

	@FXML
	private ProgressIndicator progressIndicatorId;

	/**
	 * This method is called when login operation is performed.
	 *
	 * @param event ActionEvent
	 */
	@FXML
	private void login(final ActionEvent event) {
		try {
			final LoginDataVO loginDataVO = new LoginDataVO();
			loginDataVO.setUsername(userNameId.getText());
			loginDataVO.setPassword(pwdId.getText());
			loginDataVO.setEnvironment(environmentId.getSelectionModel().getSelectedItem());
			loginDataVO.setDeviceName(deviceName.getText());
			loginDataVO.setSaveUserConfig(rememberMeId.isSelected());
			final String baseURL = ConfigurationUtility.getAppConfigData(environmentId.getSelectionModel().getSelectedItem());
			if (baseURL == null) {
				TransferObjectVO.setBaseURL(environmentId.getSelectionModel().getSelectedItem());
			} else {
				TransferObjectVO.setBaseURL(baseURL);
			}
			new LoginService(loginDataVO, progressIndicatorId).start();

		} catch (final Exception e) {
			UIUtility.showSimpleError("Please contact system administrator [" + e.getMessage() + "]");
		}
	}

	/**
	 * This method is called when reset operation is performed.
	 *
	 * @param event ActionEvent
	 */
	@FXML
	private void reset(final ActionEvent event) {
		pwdId.setText(ApplicationConstants.BLANK);
		userNameId.setText(ApplicationConstants.BLANK);
		deviceName.setText(ApplicationConstants.BLANK);
		rememberMeId.setSelected(false);
	}

	/**
	 * This method is used to load the login UI.
	 */
	public void loginUI() {
		try {
			final Parent loginParent = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource(ApplicationConstants.FXML_LOGIN));
			final Scene scene = new Scene(loginParent);
			TransferObjectVO.getMainStage().setTitle(ApplicationConstants.APP_TITTLE);
			TransferObjectVO.getMainStage().setScene(scene);
			TransferObjectVO.getMainStage().setResizable(false);
			TransferObjectVO.getMainStage().sizeToScene();
			TransferObjectVO.getMainStage().show();

			TransferObjectVO.setLoginParent(loginParent);
		} catch (final IOException e) {
			LOGGER.error("Error occured", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		final String rememberMe = ConfigurationUtility.getUserConfigData(ApplicationConstants.REMEMBER_ME);
		if (ApplicationConstants.YES.equalsIgnoreCase(rememberMe)) {
			userNameId.setText(ConfigurationUtility.getUserConfigData(ApplicationConstants.USERNAME));
			pwdId.setText(ConfigurationUtility.getUserConfigData(ApplicationConstants.PASSWORD));
			environmentId.setValue(ConfigurationUtility.getUserConfigData(ApplicationConstants.ENVIRONMENT));
			deviceName.setText(ConfigurationUtility.getUserConfigData(ApplicationConstants.DEVICE_NAME));
			rememberMeId.setSelected(true);
		}
	}
}