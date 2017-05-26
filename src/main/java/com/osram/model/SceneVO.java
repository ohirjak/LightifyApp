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
 * This method is used for details of Scene.
 */
public class SceneVO {

	/**
	 * Name of Scene
	 */
	private String	name;
	/**
	 * Scene Id
	 */
	private String	sceneId;
	/**
	 * Group Id
	 */
	private Integer	groupId;

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
	 * @return the sceneId
	 */
	public final String getSceneId() {
		return sceneId;
	}

	/**
	 * @param sceneId the sceneId to set
	 */
	public final void setSceneId(final String sceneId) {
		this.sceneId = sceneId;
	}

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
}