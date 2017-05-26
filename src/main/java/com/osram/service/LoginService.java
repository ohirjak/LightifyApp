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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osram.action.DeviceOperationController;
import com.osram.model.LoginDataVO;
import com.osram.model.ReturnObjectVO;
import com.osram.model.TransferObjectVO;
import com.osram.utils.UIUtility;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

/**
 * Login Service for performing login operation.
 */
public class LoginService extends Service<ReturnObjectVO> {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

	/**
	 * Progress Indicator
	 */
	private final transient ProgressIndicator progressIndicator;

	/**
	 * Login Data VO
	 */
	private final transient LoginDataVO loginDataVO;

	/**
	 * Constructor to initialize Login Service task
	 *
	 * @param loginDataVO
	 * @param progressIndicator
	 */
	public LoginService(final LoginDataVO loginDataVO, final ProgressIndicator progressIndicator) {
		super();
		this.loginDataVO = loginDataVO;
		this.progressIndicator = progressIndicator;
	}

	/*
	 * (non-Javadoc)
	 * @see javafx.concurrent.Service#createTask()
	 */
	@Override
	protected Task<ReturnObjectVO> createTask() {
		LOGGER.info("Login Task called");

		return new Task<ReturnObjectVO>() {
			{
				setOnSucceeded(workerStateEvent -> {
					final ReturnObjectVO returnObjectVO = getValue();
					if (returnObjectVO.isStatus()) {
						new DeviceOperationController().getAllDevices();
					} else {
						UIUtility.showJsonError(returnObjectVO.getMessage());
					}
					enableControls();
				});
				setOnFailed(workerStateEvent -> {
					getException().printStackTrace();
					UIUtility.showSimpleError("Please contact system administrator [" + getException().getMessage() + "]");
					enableControls();
				});
			}

			/*
			 * (non-Javadoc)
			 * @see javafx.concurrent.Task#call()
			 */
			@Override
			protected ReturnObjectVO call()
					throws Exception {
				LOGGER.info("Login Task CALL called");
				disableControls();
				final ReturnObjectVO returnObjectVO = LoginServiceHelper.authenticate(loginDataVO);
				return returnObjectVO;
			}
		};
	}

	/**
	 * This method would enable the controls.
	 */
	private void enableControls() {
		progressIndicator.setVisible(false);
		TransferObjectVO.getLoginParent().setDisable(false);
	}

	/**
	 * This method would disable the controls.
	 */
	private void disableControls() {
		progressIndicator.setVisible(true);
		TransferObjectVO.getLoginParent().setDisable(true);
	}
}