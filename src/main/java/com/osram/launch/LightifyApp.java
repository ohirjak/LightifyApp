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
package com.osram.launch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osram.action.LoginController;
import com.osram.model.TransferObjectVO;
import com.osram.utils.ConfigurationUtility;
import com.osram.utils.UIUtility;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for launching the APP
 */
public class LightifyApp extends Application {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LightifyApp.class);

	/*
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(final Stage loginStage)
			throws Exception {
		try {

			TransferObjectVO.setMainStage(loginStage);

			ConfigurationUtility.loadAppConfig();
			ConfigurationUtility.loadUserConfig();

			new LoginController().loginUI();
		} catch (final Exception e) {
			LOGGER.error("Error occured while initializing the application", e);
			UIUtility.showSimpleError("Please contact system administrator [" + e.getMessage() + "]");
		}
	}

	/**
	 * Main method for launching the application
	 * 
	 * @param args
	 */
	public static void main(final String... args) {
		launch(args);
	}
}