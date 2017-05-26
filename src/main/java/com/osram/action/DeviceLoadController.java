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
package com.osram.action;

import static com.osram.utils.ApplicationConstants.NO_OF_BUTTONS;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.response.Response;
import com.osram.model.DataVO;
import com.osram.model.DeviceOperationVO;
import com.osram.model.DeviceVO;
import com.osram.model.GroupVO;
import com.osram.model.OperationType;
import com.osram.model.SceneVO;
import com.osram.model.TransferObjectVO;
import com.osram.service.DeviceOperationService;
import com.osram.utils.ApplicationConstants;
import com.osram.utils.ResourceLoader;
import com.osram.utils.RestUtility;
import com.osram.utils.UIUtility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

/**
 * This class is the basic controller class for loading of devices.
 */
public class DeviceLoadController implements Initializable {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceLoadController.class);

	private static final String RGB_TO_HEX_PATTERN = "#%02x%02x%02x";

	@FXML
	private transient ComboBox<String> operationToPerformId;

	@FXML
	private transient TextField operationValueId;

	@FXML
	public Slider brightnessSliderId, temperatureSliderId;

	@FXML
	private transient Tab lightIdTab, groupIdTab, sceneIdTab, detailIdTab;

	@FXML
	private transient TabPane tabPaneId;

	@FXML
	private transient ScrollPane lightScrollPaneId, groupScrollPaneId, sceneScrollPaneId, scrollPaneOperationId;

	@FXML
	private transient GridPane lightGridPaneId, groupGridPaneId, sceneGridPaneId;

	@FXML
	public TableView<DeviceVO> lightTableId;

	@FXML
	public TableView<GroupVO> groupTableId;

	@FXML
	public TableView<SceneVO> sceneTableId;

	@FXML
	public TableColumn<DeviceVO, String> lightColumnName, lightColumnFirmware, lightColumnModel, lightColumnId, lightColumnOn, lightColumnOnline, lightColumnBrightness,
			lightColumnColor, lightColumnTemperature;

	@FXML
	public TableColumn<GroupVO, String> groupColumnId, groupColumnName, groupColumnLights, groupColumnScenes;

	@FXML
	public TableColumn<SceneVO, String> sceneColumnName, sceneColumnId, sceneColumnGroup;

	@FXML
	private transient ProgressIndicator progressIndicatorId1;

	public static Image deviceOnImg;

	/**
	 * This method is used to get all devices from REST and display.
	 */
	public void getAllDevices() {
		try {
			final Parent parentDevice = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource(ApplicationConstants.FXML_DEVICES));
			final Scene deviceScene = new Scene(parentDevice);
			TransferObjectVO.getMainStage().setScene(deviceScene);
			TransferObjectVO.getMainStage().setResizable(false);
			TransferObjectVO.getMainStage().show();

			TransferObjectVO.setDeviceParent(parentDevice);
			TransferObjectVO.setDeviceScene(deviceScene);
		} catch (final Exception e) {
			LOGGER.error("Error occured", e);
			UIUtility.showSimpleError("Please contact system administrator [" + e.getMessage() + "]");
		}
		LOGGER.info("Loading All Devices.End");
	}

	/*
	 * (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		DeviceLoadController.deviceOnImg = new Image(ResourceLoader.load("light_on.jpg"));

		populateData();

		brightnessSliderId.valueProperty().addListener((observable, oldValue, newValue) -> {
			operationValueId.setText(Integer.toString(newValue.intValue()));
		});
		temperatureSliderId.valueProperty().addListener((observable, oldValue, newValue) -> {
			operationValueId.setText(Integer.toString(newValue.intValue()));
		});

		lightIdTab.setOnSelectionChanged(event -> tabChanged(event, ApplicationConstants.LIGHT));
		groupIdTab.setOnSelectionChanged(event -> tabChanged(event, ApplicationConstants.GROUP));
		sceneIdTab.setOnSelectionChanged(event -> tabChanged(event, ApplicationConstants.SCENE));
		detailIdTab.setOnSelectionChanged(event -> tabChanged(event, ApplicationConstants.DETAIL));
	}

	/**
	 * This method is the controller method for loading data in UI.
	 */
	public void populateData() {
		loadDevicesData();
		loadGroupAndSceneData();
	}

	public void loadDevicesData() {
		LOGGER.info("Loading All Devices.Start");
		final DataVO<String> allDevicesDataVO = new DataVO<String>();
		allDevicesDataVO.setContentType(ApplicationConstants.CONTENT_TYPE_JSON);
		allDevicesDataVO.setRequestMethod(ApplicationConstants.REQUEST_METHOD_GET);
		allDevicesDataVO.setHeader(ApplicationConstants.HD_AUTHORIZATION + ApplicationConstants.COLON + TransferObjectVO.getSecurityToken());
		allDevicesDataVO.setUrl(TransferObjectVO.getBaseURL() + ApplicationConstants.URL_DEVICE_RETRIVAL);
		final Response response = RestUtility.execute(allDevicesDataVO);
		final String json = response.asString();
		try {
			if (response.getStatusCode() == 200) {
				final List<DeviceVO> deviceVO = Arrays.asList(new ObjectMapper().readValue(json, DeviceVO[].class));
				populateLights(deviceVO);
			} else {
				UIUtility.showJsonError(json);
			}
		} catch (final Exception e) {
			LOGGER.error("Error occured", e);
			UIUtility.showSimpleError("Please contact system administrator [" + e.getMessage() + "]");
		}
		LOGGER.info("Loading All Devices.End");
	}

	public void loadGroupAndSceneData() {
		LOGGER.info("Loading Group.Start");
		final DataVO<String> allDevicesDataVO = new DataVO<String>();
		allDevicesDataVO.setContentType(ApplicationConstants.CONTENT_TYPE_JSON);
		allDevicesDataVO.setRequestMethod(ApplicationConstants.REQUEST_METHOD_GET);
		allDevicesDataVO.setHeader(ApplicationConstants.HD_AUTHORIZATION + ApplicationConstants.COLON + TransferObjectVO.getSecurityToken());
		allDevicesDataVO.setUrl(TransferObjectVO.getBaseURL() + ApplicationConstants.URL_GROUP_RETRIVAL);
		final Response response = RestUtility.execute(allDevicesDataVO);

		final String json = response.asString();
		try {
			if (response.getStatusCode() == 200) {
				final List<GroupVO> groupVOList = Arrays.asList(new ObjectMapper().readValue(json, GroupVO[].class));
				populateGroupsAndScene(groupVOList);
			} else {
				UIUtility.showJsonError(json);
			}
		} catch (final Exception e) {
			LOGGER.error("Error occured", e);
			UIUtility.showSimpleError("Please contact system administrator [" + e.getMessage() + "]");
		}
		LOGGER.info("Loading Group.End");
	}

	/**
	 * This method is used to load all the lights in UI.
	 *
	 * @param gatewayVO response from REST which has light information.
	 */
	private void populateLights(final List<DeviceVO> deviceTableVOList) {
		LOGGER.info("Loading Device details.Start");
		if (deviceTableVOList != null && !deviceTableVOList.isEmpty()) {
			int rowcount = 0;
			int colCount = 0;
			final ObservableList<DeviceVO> data = FXCollections.observableArrayList();
			for (final Object element : deviceTableVOList) {
				final DeviceVO deviceVO = (DeviceVO) element;
				if (deviceVO != null) {
					if (!ApplicationConstants.BLANK.equals(deviceVO.getName())) {
						if (colCount > NO_OF_BUTTONS) {
							colCount = 0;
							rowcount++;
						}
						lightGridPaneId.add(buildButton(deviceVO, ApplicationConstants.LIGHT), colCount, rowcount);
						colCount++;
						data.add(deviceVO);
					}
				}
			}

			lightColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
			lightColumnFirmware.setCellValueFactory(new PropertyValueFactory<>("firmwareVersion"));
			lightColumnBrightness.setCellValueFactory(new PropertyValueFactory<>("brightnessLevel"));
			lightColumnTemperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
			lightColumnColor.setCellValueFactory(new PropertyValueFactory<>("color"));
			lightColumnOn.setCellValueFactory(new PropertyValueFactory<>("on"));
			lightColumnOnline.setCellValueFactory(new PropertyValueFactory<>("online"));
			lightColumnModel.setCellValueFactory(new PropertyValueFactory<>("modelName"));
			lightColumnId.setCellValueFactory(new PropertyValueFactory<>("deviceId"));
			lightTableId.setItems(data);
			// lightBoxId.getChildren().add(buildButton(null,ApplicationConstants.LIGHT_BROADCAST));
			LOGGER.info("Loading Device details.End");
		}
	}

	/**
	 * This method is used to load all the Group in UI.
	 *
	 * @param gatewayVO response from REST which has group information.
	 */

	private void populateGroupsAndScene(final List<GroupVO> groupVOList) {
		LOGGER.info("Loading Group details.Start");
		if (groupVOList != null && !groupVOList.isEmpty()) {
			final List<SceneVO> sceneVOList = new ArrayList<SceneVO>();
			final ObservableList<GroupVO> data = FXCollections.observableArrayList();
			int rowcount = 0;
			int colCount = 0;
			for (final GroupVO groupVO : groupVOList) {
				if (colCount > NO_OF_BUTTONS) {
					colCount = 0;
					rowcount++;
				}
				final Map<String, String> sceneMap = groupVO.getScenes();
				sceneMap.forEach((k, v) -> {
					final SceneVO sceneVO = new SceneVO();
					sceneVO.setGroupId(groupVO.getGroupId());
					sceneVO.setSceneId(k);
					sceneVO.setName(v);
					sceneVOList.add(sceneVO);
				});
				groupGridPaneId.add(buildButton(groupVO, ApplicationConstants.GROUP), colCount, rowcount);
				colCount++;
				data.add(groupVO);
			}
			groupColumnId.setCellValueFactory(new PropertyValueFactory<>("groupId"));
			groupColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
			groupColumnLights.setCellValueFactory(new PropertyValueFactory<>("devices"));
			groupColumnScenes.setCellValueFactory(new PropertyValueFactory<>("scenes"));
			groupTableId.setItems(data);
			LOGGER.info("Loading Group details.End");
			populateScenes(sceneVOList);
		}
	}

	/**
	 * This method is used to load all the scene in UI.
	 *
	 * @param gatewayVO response from REST which has scene information.
	 */
	private void populateScenes(final List<SceneVO> sceneVOList) {
		LOGGER.info("Loading Scene details.Start");
		if (sceneVOList != null && !sceneVOList.isEmpty()) {
			final ObservableList<SceneVO> data = FXCollections.observableArrayList();

			int rowcount = 0;
			int colCount = 0;
			for (final SceneVO sceneVO : sceneVOList) {
				if (colCount > NO_OF_BUTTONS) {
					colCount = 0;
					rowcount++;
				}
				data.add(sceneVO);
				sceneGridPaneId.add(buildButton(sceneVO, ApplicationConstants.SCENE), colCount, rowcount);
				colCount++;
			}
			sceneColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
			sceneColumnId.setCellValueFactory(new PropertyValueFactory<>("sceneId"));
			sceneColumnGroup.setCellValueFactory(new PropertyValueFactory<>("groupId"));
			sceneTableId.setItems(data);
			LOGGER.info("Loading Scene details.End");
		}
	}

	/**
	 * This method is called when tabs are changed.
	 *
	 * @param event Event
	 * @param tabName tab name
	 */
	public void tabChanged(final Event event, final String tabName) {

		if (ApplicationConstants.LIGHT.equals(tabName) || ApplicationConstants.GROUP.equals(tabName)) {
			scrollPaneOperationId.setVisible(true);
		} else {
			scrollPaneOperationId.setVisible(false);
		}

		if (ApplicationConstants.DETAIL.equals(tabName)) {
			// populateData();
		}
	}

	/**
	 * This method is used to build the buttons for Light, Group and Scene.
	 *
	 * @param object Light, Group or Scene Object
	 * @param operationOn Specify on which the operation is performed e.g. Light or Group or Scene
	 * @return button
	 */
	private Button buildButton(final Object object, final String operationOn) {
		String buttonText = ApplicationConstants.BLANK;
		String buttonId = null;
		String tooltip = ApplicationConstants.BLANK;
		String color = ApplicationConstants.BLANK;
		Integer isOn = ApplicationConstants.DEVICE_ON;
		Integer isOnline = ApplicationConstants.DEVICE_ON;
		if (object instanceof DeviceVO) {
			final DeviceVO deviceVO = (DeviceVO) object;
			buttonText = deviceVO.getName();
			if (buttonText == null || buttonText.isEmpty()) {
				buttonText = deviceVO.getModelName();
			}
			buttonId = deviceVO.getDeviceId().toString();
			tooltip = UIUtility.getLightToolTip(deviceVO);
			deviceVO.getOnline();
			isOn = deviceVO.getOn();
			isOnline = deviceVO.getOnline();
			if (deviceVO.getHue() != null && deviceVO.getSaturation() != null && deviceVO.getBrightnessLevel() != null) {
				final Color colorRGB = Color.getHSBColor(Float.valueOf(deviceVO.getHue()), Float.valueOf(deviceVO.getSaturation()),
						Float.valueOf(deviceVO.getBrightnessLevel()));
				color = String.format(DeviceLoadController.RGB_TO_HEX_PATTERN, colorRGB.getRed(), colorRGB.getGreen(), colorRGB.getBlue());
			}
		} else if (object instanceof GroupVO) {
			final GroupVO groupVO = (GroupVO) object;
			buttonText = groupVO.getName();
			buttonId = groupVO.getGroupId().toString();
			tooltip = UIUtility.getGroupToolTip(groupVO);
			isOn = ApplicationConstants.DEVICE_OFF;
		} else if (object instanceof SceneVO) {
			final SceneVO sceneVO = (SceneVO) object;
			buttonText = sceneVO.getName();
			buttonId = sceneVO.getSceneId();
			if (buttonId == null) {
				buttonId = buttonText;
			}
			tooltip = UIUtility.getSceneToolTip(sceneVO);
			isOn = ApplicationConstants.DEVICE_OFF;
		} else if (ApplicationConstants.LIGHT_BROADCAST.equals(operationOn)) {
			buttonText = "BroadCast";
			// buttonId = "broadCastBtnId";
			tooltip = "Broadcast ON/OFF all lights";
		}
		final Button button = new Button(buttonText);
		button.setId(buttonId);
		button.setMinHeight(32);
		button.setMinWidth(100);

		final Tooltip toolTip = new Tooltip();
		toolTip.setText(tooltip);
		button.setTooltip(toolTip);
		if (!ApplicationConstants.BLANK.equals(color)) {
			button.setStyle("-fx-cursor: hand;-fx-border-color: grey;");
		}
		if (ApplicationConstants.DEVICE_ON.equals(isOn)) {
			// button.setGraphic(new ImageView(DeviceLoadController.deviceOnImg));
			button.setStyle("-fx-font-weight: bold;");
		}
		if (isOnline == null || Integer.compare(isOnline, 0) == 0) {
			button.setDisable(true);
		}

		button.setOnMouseClicked(event -> {
			final DeviceOperationVO deviceOperationVO = buildDeviceOperationVO(operationOn, button.getId());
			final DeviceOperationService deviceOperationService = new DeviceOperationService(button, object, deviceOperationVO, progressIndicatorId1);
			deviceOperationService.start();
		});
		return button;
	}

	/**
	 * This method is used to build DeviceOperationVO required by REST API
	 *
	 * @param operationOn Specify on which the operation is performed e.g. Light or Group or Scene
	 * @param deviceId deviceId
	 * @return DeviceOperationVO
	 */
	private DeviceOperationVO buildDeviceOperationVO(final String operationOn, final String deviceId) {
		final DeviceOperationVO deviceOperationVO = new DeviceOperationVO();
		if (ApplicationConstants.SCENE.equals(operationOn)) {
			deviceOperationVO.setOperationType(OperationType.SCENE_RECALL);
			deviceOperationVO.setOperationValue(operationValueId.getText());
		} else {
			deviceOperationVO.setOperationType(OperationType.valueOf(operationToPerformId.getSelectionModel().getSelectedItem()));
			deviceOperationVO.setOperationValue(operationValueId.getText());
		}
		deviceOperationVO.setOperationOn(operationOn);
		deviceOperationVO.setDeviceId(Integer.valueOf(deviceId));
		deviceOperationVO.setFadingTime(00);
		return deviceOperationVO;
	}
}