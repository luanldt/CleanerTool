package controller;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SettingController {

	private final String IMAGE = "image";
	private final String MUSIC = "music";
	private final String DOCUMENT = "document";
	private final String VIDEO = "video";

	@FXML
	private TreeView<String> TreeViewMenu;

	@FXML
	private Button ButtonCancel;

	@FXML
	private Pane PaneImage;
	@FXML
	private Pane PaneDocument;
	@FXML
	private Pane PaneVideo;
	@FXML
	private Pane PaneMusic;

	@FXML
	void initialize() {
		loadTreeItem();
	}

	@SuppressWarnings("unchecked")
	private void loadSetting(String type) {
		JSONParser jsonParser = new JSONParser();
		try {
			Object object = jsonParser.parse(new FileReader("setting.json"));
			JSONObject jsonObject = (JSONObject) object;
			switch (type) {
			case IMAGE:
				JSONArray imageTypes = (JSONArray) jsonObject.get("ImageType");
				imageTypes.forEach((obj) -> {
					doCheckBox(IMAGE, obj.toString());
				});
				break;
			case MUSIC:
				JSONArray musicTypes = (JSONArray) jsonObject.get("MusicType");
				musicTypes.forEach((obj) -> {
					doCheckBox(MUSIC, obj.toString());
				});
				break;
			case DOCUMENT:
				JSONArray documentTypes = (JSONArray) jsonObject.get("DocumentType");
				documentTypes.forEach((obj) -> {
					doCheckBox(DOCUMENT, obj.toString());
				});
				break;
			case VIDEO:
				JSONArray videoTypes = (JSONArray) jsonObject.get("VideoType");
				videoTypes.forEach((obj) -> {
					doCheckBox(VIDEO, obj.toString());
				});
				break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void loadTreeItem() {
		TreeItem<String> root, type;
		root = new TreeItem<String>();
		type = this.makeBranch("Type Extension", root);
		makeBranch("Image", type);
		makeBranch("Music", type);
		makeBranch("Video", type);
		makeBranch("Document", type);
		TreeViewMenu.setRoot(root);
		TreeViewMenu.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			showPane(newValue.getValue().toLowerCase());
		});
	}

	private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
		TreeItem<String> node = new TreeItem<String>(title);
		node.setExpanded(true);
		parent.getChildren().add(node);
		return node;
	}

	@FXML
	private void closeSetting() {
		Stage stage = (Stage) ButtonCancel.getScene().getWindow();
		stage.close();
	}

	/**
	 * Show pane setting.
	 * 
	 * @param name
	 */
	private void showPane(String name) {
		PaneImage.setVisible(false);
		PaneVideo.setVisible(false);
		PaneDocument.setVisible(false);
		PaneMusic.setVisible(false);
		switch (name) {
		case "image":
			PaneImage.setVisible(true);
			break;
		case "video":
			PaneVideo.setVisible(true);
			break;
		case "document":
			PaneDocument.setVisible(true);
			break;
		case "music":
			PaneMusic.setVisible(true);
			break;
		}
		loadSetting(name);
	}

	@SuppressWarnings("unchecked")
	@FXML
	/**
	 * Save setting is write setting to setting.json
	 */
	private void saveSetting() {
		JSONObject jsonObject = new JSONObject();
		JSONArray imageType = new JSONArray();
		JSONArray documentType = new JSONArray();
		JSONArray videoType = new JSONArray();
		JSONArray musicType = new JSONArray();
		PaneImage.getChildren().forEach((type) -> {
			if (type instanceof CheckBox && ((CheckBox) type).isSelected()) {
				imageType.add(((CheckBox) type).getText().substring(1));
			}
		});
		PaneDocument.getChildren().forEach((type) -> {
			if (type instanceof CheckBox && ((CheckBox) type).isSelected()) {
				documentType.add(((CheckBox) type).getText().substring(1));
			}
		});
		PaneVideo.getChildren().forEach((type) -> {
			if (type instanceof CheckBox && ((CheckBox) type).isSelected()) {
				videoType.add(((CheckBox) type).getText().substring(1));
			}
		});
		PaneMusic.getChildren().forEach((type) -> {
			if (type instanceof CheckBox && ((CheckBox) type).isSelected()) {
				musicType.add(((CheckBox) type).getText().substring(1));
			}
		});
		jsonObject.put("ImageType", imageType);
		jsonObject.put("DocumentType", documentType);
		jsonObject.put("VideoType", videoType);
		jsonObject.put("MusicType", musicType);

		try (FileWriter file = new FileWriter("setting.json")) {
			file.write(jsonObject.toJSONString());
			System.out.println("Successfully JSON Object to File...");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		this.closeSetting();
	}

	/**
	 * Do CheckBox to check all CheckBox in setting.json
	 * 
	 * @param type
	 * @param extension
	 */
	public void doCheckBox(String type, String extension) {
		switch (type) {
		case VIDEO:
			PaneVideo.getChildren().forEach((chk) -> {
				if (chk instanceof CheckBox) {
					if (((CheckBox) chk).getText().substring(1).equals(extension)) {
						((CheckBox) chk).setSelected(true);
					}
				}
			});
			break;
		case MUSIC:
			PaneMusic.getChildren().forEach((chk) -> {
				if (chk instanceof CheckBox) {
					if (((CheckBox) chk).getText().substring(1).equals(extension)) {
						((CheckBox) chk).setSelected(true);
					}
				}
			});
			break;
		case DOCUMENT:
			PaneDocument.getChildren().forEach((chk) -> {
				if (chk instanceof CheckBox) {
					if (((CheckBox) chk).getText().substring(1).equals(extension)) {
						((CheckBox) chk).setSelected(true);
					}
				}
			});
			break;
		case IMAGE:
			PaneImage.getChildren().forEach((chk) -> {
				if (chk instanceof CheckBox) {
					if (((CheckBox) chk).getText().substring(1).equals(extension)) {
						((CheckBox) chk).setSelected(true);
					}
				}
			});
			break;
		}
	}

}
