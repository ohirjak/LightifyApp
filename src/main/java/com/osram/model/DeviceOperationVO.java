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

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * This class is used to take inputs for performing operation on devices
 */
public class DeviceOperationVO {

	/**
	 * {@link OperationType}
	 */
	private OperationType operationType;

	/**
	 * operation value
	 */
	private String operationValue;

	/**
	 * Operation on LIGHT/GROUP/SCENE
	 */
	private String operationOn;

	/**
	 * FADING TIME
	 */
	private Integer fadingTime;

	/**
	 * Device Id
	 */
	@JsonIgnore
	private Integer deviceId;

	/**
	 * Default Constructor
	 */
	public DeviceOperationVO() {
		// EMPTY constructor
		super();
	}

	/**
	 * Constructor to initialize DeviceOperationVO
	 *
	 * @param operationType
	 * @param operationValue
	 */
	public DeviceOperationVO(final OperationType operationType, final String operationValue) {
		this.operationType = operationType;
		this.operationValue = operationValue;
	}

	/**
	 * @return the operationType
	 */
	public OperationType getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType the operationType to set
	 */
	public void setOperationType(final OperationType operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return the operationValue
	 */
	public String getOperationValue() {
		return operationValue;
	}

	/**
	 * @param operationValue the operationValue to set
	 */
	public void setOperationValue(final String operationValue) {
		this.operationValue = operationValue;
	}

	/**
	 * @return the operationOn
	 */
	public final String getOperationOn() {
		return operationOn;
	}

	/**
	 * @param operationOn the operationOn to set
	 */
	public final void setOperationOn(final String operationOn) {
		this.operationOn = operationOn;
	}

	/**
	 * @return the deviceId
	 */
	public final Integer getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public final void setDeviceId(final Integer deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the fadingTime
	 */
	public final Integer getFadingTime() {
		return fadingTime;
	}

	/**
	 * @param fadingTime the fadingTime to set
	 */
	public final void setFadingTime(final Integer fadingTime) {
		this.fadingTime = fadingTime;
	}
}