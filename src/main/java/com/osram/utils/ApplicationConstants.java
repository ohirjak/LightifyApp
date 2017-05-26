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
package com.osram.utils;

/**
 * Interface for defining application level constants.
 */
public class ApplicationConstants {
	public static final String	BLANK			= "";
	public static final String	COMMA			= ",";
	public static final String	COLON			= ":";
	public static final String	JSON_LIST_START	= "[";
	public static final String	RAWTYPES		= "rawtypes";

	public static final String NEW_LINE = "\n";

	public static String	YES					= "Yes";
	public static String	NO					= "No";
	public static String	ON					= "ON";
	public static String	CONTENT_TYPE_JSON	= "application/json";

	public static String HD_AUTHORIZATION = "Authorization";

	public static Integer	DEVICE_ON	= 1;
	public static Integer	DEVICE_OFF	= 0;

	public static final String	DETAIL			= "DETAIL";
	public static final String	SCENE			= "SCENE";
	public static final String	GROUP			= "GROUP";
	public static final String	LIGHT			= "LIGHT";
	public static final String	LIGHT_BROADCAST	= "ON";

	public static final String	REQUEST_METHOD_POST		= "POST";
	public static final String	REQUEST_METHOD_GET		= "GET";
	public static final String	REQUEST_METHOD_DELETE	= "DELETE";

	public static final String	URL_SESSION			= "/session";
	public static final String	URL_DEVICE_RETRIVAL	= "/devices";
	public static final String	URL_GROUP_RETRIVAL	= "/groups";

	public static final String	URL_BROADCAST_OPERATION	= "/device/all/set?";
	public static final String	URL_LIGHT_OPERATION		= "/device/set?";
	public static final String	URL_GROUP_OPERATION		= "/group/set?";
	public static final String	URL_SCENE_OPERATION		= "/scene/recall?";

	public static final int NO_OF_BUTTONS = 5;

	public static final String	USER_CONFIG_FILE	= "userconfig.yaml";
	public static final String	APP_CONFIG_FILE		= "appconfig.yaml";
	public static final String	FXML_LOGIN			= "Login.fxml";
	public static final String	FXML_DEVICES		= "DeviceList.fxml";
	public static final String	FXML_ERROR_DIALOG	= "AlertDialog.fxml";
	public static final String	APP_TITTLE			= "Lightify";

	public static String	THEME1	= "theme1.css";
	public static String	THEME2	= "theme2.css";

	public static final String	DEFAULT_ENVIRONMENT	= "PRODUCUTION-EU";
	public static final String	REMEMBER_ME			= "REMEMBER_ME";
	public static final String	USERNAME			= "USERNAME";
	public static final String	PASSWORD			= "PASSWORD";
	public static final String	DEVICE_NAME			= "DEVICE_NAME";
	public static final String	ENVIRONMENT			= "ENVIRONMENT";

	// Request Parameter.Start
	public static final String	PARAM_CTEMP			= "ctemp";
	public static final String	PARAM_SATURATION	= "saturation";
	public static final String	PARAM_HUE			= "hue";
	public static final String	PARAM_LEVEL			= "level";
	public static final String	PARAM_ONOFF			= "onoff";
	public static final String	PARAM_TIME			= "time";
	public static final String	PARAM_IDX			= "idx";
	public static final String	PARAM_SCENE_ID		= "sceneId";
	public static final String	PARAM_COLOR			= "color";
	// Request Parameter.End
}
