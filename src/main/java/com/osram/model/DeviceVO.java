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

import java.util.ArrayList;
import java.util.List;

/**
 * This POJO is used to get/set details of all the devices.
 */
public class DeviceVO {
	/**
	 * Key Index of the device e.g. 1
	 */
	private Integer			deviceId;
	/**
	 * Device Type "Light", "Switch"
	 */
	private String			deviceType;
	/**
	 * Manufacturer name "OSRAM"
	 */
	private String			manufacturer;
	/**
	 * Model Name
	 */
	private String			modelName;
	/**
	 * Name of device
	 */
	private String			name;
	/**
	 * A Bitmap telling which group this device belongs to
	 */
	private List<Integer>	groupList	= new ArrayList<Integer>();
	/**
	 * A Bitmap indicating which features are implemented
	 */
	private List<String>	bmpClusters;
	/**
	 * Online e.g. 0 – OffLine 1 – OnLine
	 */
	private Integer			online;
	/**
	 * Current state of the device e.g. 0 – Off 1 – On (-1) – Not Supported
	 */
	private Integer			on;
	/**
	 * level New dimming level e.g. (-1) – Not Supported valid range 0.00 to 1
	 */
	private Float			brightnessLevel;
	/**
	 * New hue e.g. (-1) – Not Supported valid range 1 … 360
	 */
	private Integer			hue;
	/**
	 * New saturation e.g. (-1) – Not Supported valid range 0.00 to 1
	 */
	private Float			saturation;
	/**
	 * New color temperature e.g. (-1) – Not Supported 0 – undefined 1..65279 – valid color temperature
	 */
	private Integer			temperature;
	/**
	 * Firmware Version of device
	 */
	private String			firmwareVersion;

	/**
	 * Color of device
	 */
	private String color;

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
	 * @return the deviceType
	 */
	public final String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public final void setDeviceType(final String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the manufacturer
	 */
	public final String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public final void setManufacturer(final String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the modelName
	 */
	public final String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName the modelName to set
	 */
	public final void setModelName(final String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the groupList
	 */
	public final List<Integer> getGroupList() {
		return groupList;
	}

	/**
	 * @param groupList the groupList to set
	 */
	public final void setGroupList(final List<Integer> groupList) {
		this.groupList = groupList;
	}

	/**
	 * @return the online
	 */
	public final Integer getOnline() {
		return online;
	}

	/**
	 * @param online the online to set
	 */
	public final void setOnline(final Integer online) {
		this.online = online;
	}

	/**
	 * @return the on
	 */
	public final Integer getOn() {
		return on;
	}

	/**
	 * @param onoff the on to set
	 */
	public final void setOn(final Integer on) {
		this.on = on;
	}

	/**
	 * @return the hue
	 */
	public final Integer getHue() {
		return hue;
	}

	/**
	 * @param hue the hue to set
	 */
	public final void setHue(final Integer hue) {
		this.hue = hue;
	}

	/**
	 * @return the saturation
	 */
	public final Float getSaturation() {
		return saturation;
	}

	/**
	 * @param saturation the saturation to set
	 */
	public final void setSaturation(final Float saturation) {
		this.saturation = saturation;
	}

	/**
	 * @return the temperature
	 */
	public final Integer getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public final void setTemperature(final Integer temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the firmwareVersion
	 */
	public final String getFirmwareVersion() {
		return firmwareVersion;
	}

	/**
	 * @param firmwareVersion the firmwareVersion to set
	 */
	public final void setFirmwareVersion(final String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	/**
	 * @return the bmpClusters
	 */
	public final List<String> getBmpClusters() {
		return bmpClusters;
	}

	/**
	 * @param bmpCluster the bmpClusters to set
	 */
	public final void setBmpClusters(final List<String> bmpClusters) {
		this.bmpClusters = bmpClusters;
	}

	/**
	 * @return the brightnessLevel
	 */
	public final Float getBrightnessLevel() {
		return brightnessLevel;
	}

	/**
	 * @param brightnessLevel the brightnessLevel to set
	 */
	public final void setBrightnessLevel(final Float brightnessLevel) {
		this.brightnessLevel = brightnessLevel;
	}

	/**
	 * @return the color
	 */
	public final String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public final void setColor(final String color) {
		this.color = color;
	}
}