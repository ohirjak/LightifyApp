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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This method is used for details of Group.
 */
public class GroupVO {

	/**
	 * Group ID
	 */
	private Integer				groupId;
	/**
	 * Group Name
	 */
	private String				name;
	/**
	 * List of Devices in the Group
	 */
	private List<Integer>		devices	= new ArrayList<Integer>();
	/**
	 * Map of Scenes inside the Group
	 */
	private Map<String, String>	scenes	= new HashMap<String, String>();

	/**
	 * @return the groupId
	 */
	public final Integer getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public final void setGroupId(final Integer groupId) {
		this.groupId = groupId;
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
	 * @return the devices
	 */
	public final List<Integer> getDevices() {
		return devices;
	}

	/**
	 * @param devices the devices to set
	 */
	public final void setDevices(final List<Integer> devices) {
		this.devices = devices;
	}

	/**
	 * @return the scenes
	 */
	public final Map<String, String> getScenes() {
		return scenes;
	}

	/**
	 * @param scenes the scenes to set
	 */
	public final void setScenes(final Map<String, String> scenes) {
		this.scenes = scenes;
	}
}