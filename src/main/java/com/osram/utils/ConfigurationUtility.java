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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

/**
 * This is an utility class for loading and modifying configuration related YAML files.
 */
public final class ConfigurationUtility {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationUtility.class);

	/**
	 * MAP for User configuration
	 */
	private static final Map<String, String>	USER_CONFIG_MAP	= new HashMap<String, String>();
	/**
	 * MAP for APP configuration
	 */
	private static final Map<String, String>	APP_CONFIG_MAP	= new ConcurrentHashMap<String, String>();

	/**
	 * private constructor so that method can be accessed in Static way only.
	 */
	private ConfigurationUtility() {
	};

	/**
	 * This method is used to load user configuration into class level map.
	 */
	@SuppressWarnings("unchecked")
	public static void loadUserConfig() {
		final Yaml yaml = new Yaml();
		try {
			USER_CONFIG_MAP.putAll((Map<String, String>) yaml.load(new FileInputStream(ApplicationConstants.USER_CONFIG_FILE)));
		} catch (final FileNotFoundException e) {
			LOGGER.info("When the application is launched for first time this file would not be present {}", ApplicationConstants.USER_CONFIG_FILE);
		}
	}

	/**
	 * This method is used to load APP configuration into class level map.
	 */
	@SuppressWarnings("unchecked")
	public static void loadAppConfig() {
		final Yaml yaml = new Yaml();
		APP_CONFIG_MAP.putAll((Map<String, String>) yaml.load(ResourceLoader.load(ApplicationConstants.APP_CONFIG_FILE)));
	}

	/**
	 * This method is used to over right user configuration in YAML file.
	 *
	 * @param userMap User Map
	 */
	public static void setUserConfig(final Map<String, String> userMap) {
		try {
			final Yaml yaml = new Yaml();
			final Writer writer = new FileWriter(ApplicationConstants.USER_CONFIG_FILE);
			USER_CONFIG_MAP.putAll(userMap);
			yaml.dump(USER_CONFIG_MAP, writer);
			writer.flush();
			writer.close();

		} catch (final IOException e) {
			LOGGER.error("Error occured", e);
		}
	}

	/**
	 * This method is used to get user configuration data based on key.
	 *
	 * @param key key for which data is required
	 * @return data if Key exist else null
	 */
	public static String getUserConfigData(final String key) {
		return USER_CONFIG_MAP.get(key);
	}

	/**
	 * This method is used to get APP configuration data based on key.
	 *
	 * @param key key for which data is required
	 * @return data if Key exist else null
	 */
	public static String getAppConfigData(final String key) {
		return APP_CONFIG_MAP.get(key);
	}
}