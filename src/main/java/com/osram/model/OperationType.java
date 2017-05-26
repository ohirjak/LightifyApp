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
 * ENUM for specifying list of operations supported on devices
 */
public enum OperationType {
	CHANGE_STATE("changeState"), CHANGE_COLOR("changeColor"), CHANGE_BRIGHTNESS("changeBrightness"), CHANGE_TEMPERATURE("changeTemperature"), SCENE_RECALL("recallScene"),
	CHANGE_RGB("changeRgb"), BROADCAST("broadCast");

	/**
	 * Operation Property
	 */
	private String operation;

	/**
	 * Private Constructor
	 *
	 * @param operationType
	 */
	private OperationType(final String operationType) {
		this.setOperation(operationType);
	}

	/**
	 * @return the operation
	 */
	public final String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public final void setOperation(final String operation) {
		this.operation = operation;
	}
}