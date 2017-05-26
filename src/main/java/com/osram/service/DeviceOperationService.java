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

import com.osram.model.DeviceOperationVO;
import com.osram.model.ReturnObjectVO;
import com.osram.model.TransferObjectVO;
import com.osram.utils.UIUtility;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;

/**
 * Service for performing device operation
 */
public class DeviceOperationService extends Service<ReturnObjectVO> {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceOperationService.class);

	/**
	 * PROGRESS INDICATOR
	 */
	private final transient ProgressIndicator	progressIndicator;
	/**
	 * JAVAFX Button
	 */
	private final transient Button				button;
	/**
	 * LIGHT/GROUP/SCENE object
	 */
	private final transient Object				object;
	/**
	 * DeviceOperationVO
	 */
	private final transient DeviceOperationVO	deviceOperationVO;

	/**
	 * Constructor for initializing the service
	 *
	 * @param button Button
	 * @param object : Light/Group/Scene object
	 * @param deviceOperationVO DeviceOperationVO
	 * @param progressIndicator Progress Indicator
	 */
	public DeviceOperationService(final Button button, final Object object, final DeviceOperationVO deviceOperationVO, final ProgressIndicator progressIndicator) {
		super();
		this.button = button;
		this.object = object;
		this.deviceOperationVO = deviceOperationVO;
		this.progressIndicator = progressIndicator;
	}

	/*
	 * (non-Javadoc)
	 * @see javafx.concurrent.Service#createTask()
	 */
	@Override
	protected Task<ReturnObjectVO> createTask() {
		LOGGER.info("Device Operation Task called");
		return new Task<ReturnObjectVO>() {
			{
				setOnSucceeded(workerStateEvent -> {
					final ReturnObjectVO returnObjectVO = getValue();
					if (returnObjectVO.isStatus()) {
						final ReturnObjectVO obj = DeviceOperationHelper.performOperation(button, object, deviceOperationVO);
						if (obj.isStatus()) {
							DeviceOperationHelper.updateUIAfterOperation(button, object, deviceOperationVO);
							Platform.runLater(() -> enableControls());
						} else {
							UIUtility.showJsonError(obj.getMessage());
							enableControls();
						}
					} else {
						UIUtility.showSimpleError(returnObjectVO.getMessage());
						enableControls();
					}
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
				LOGGER.info("Device Operation Serivce Task CALL called");
				disableControls();
				final ReturnObjectVO returnObjectVO = DeviceOperationHelper.validate(object, deviceOperationVO);
				return returnObjectVO;
			}
		};
	}

	/**
	 * This method would enable the controls.
	 */
	private void enableControls() {
		TransferObjectVO.getDeviceParent().setDisable(false);
		button.setDisable(false);
		progressIndicator.setVisible(false);
	}

	/**
	 * This method would disable the controls.
	 */
	private void disableControls() {
		progressIndicator.setCenterShape(true);
		progressIndicator.setVisible(true);
		TransferObjectVO.getDeviceParent().setDisable(true);
		button.setDisable(true);
	}
}