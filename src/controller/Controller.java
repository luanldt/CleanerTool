package controller;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import application.Setting;
import helper.CustomFileNameFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;

import static java.nio.file.StandardCopyOption.*;

public class Controller {

	@FXML
	private MenuItem MenuItemTypeExtension;

	@FXML
	private Button ButtonChooseFolder;

	@FXML
	private RadioButton RadioButtonVideo;

	@FXML
	private Label LabelSource;

	@FXML
	private RadioButton RadioButtonMusic;

	@FXML
	private Label LabelDestination;

	@FXML
	private RadioButton RadioButtonDocument;

	@FXML
	private RadioButton RadioButtonImage;

	@FXML
	private ToggleGroup type;

	@FXML
	private Button ButtonChooseFolderDestination;

	@FXML
	private Button ButtonMove;

	private File sourceDir = null;
	private File destinationDir = null;
	private FilenameFilter fileFilerMusic;
	private FilenameFilter fileFilerDocument;
	private FilenameFilter fileFilerVideo;
	private FilenameFilter fileFilerImage;

	@FXML
	void initialize() {
		loadFilter();
	}

	@FXML
	void chooseFolderSource(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		sourceDir = directoryChooser.showDialog(null);
		LabelSource.setText(sourceDir.toString());
	}

	@FXML
	void doMove(ActionEvent event) {
		File[] sources = null;
		if (RadioButtonImage.isSelected()) {
			sources = sourceDir.listFiles(fileFilerImage);
		}
		if (RadioButtonVideo.isSelected()) {
			sources = sourceDir.listFiles(fileFilerVideo);
		}
		if (RadioButtonDocument.isSelected()) {
			sources = sourceDir.listFiles(fileFilerDocument);
		}
		if (RadioButtonMusic.isSelected()) {
			sources = sourceDir.listFiles(fileFilerMusic);
		}
		for (File file : sources) {
			try {
				Files.move(file.toPath(), new File(destinationDir.getAbsolutePath() + "/" + file.getName()).toPath(),
						REPLACE_EXISTING);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Move " + sources.length + " files success!!!");
		alert.showAndWait();
	}

	@FXML
	void chooseFolderDestination(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		destinationDir = directoryChooser.showDialog(null);
		LabelDestination.setText(destinationDir.toString());
	}

	@FXML
	void showSetting(ActionEvent event) {
		try {
			new Setting().start();
			loadFilter();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Load file filter by setting.json
	 */
	@SuppressWarnings("unchecked")
	public void loadFilter() {
		JSONParser jsonParser = new JSONParser();
		try {
			Object object = jsonParser.parse(new FileReader("setting.json"));
			JSONObject jsonObject = (JSONObject) object;
			
			System.out.println(jsonObject);

			JSONArray imageTypes = (JSONArray) jsonObject.get("ImageType");
			JSONArray videoTypes = (JSONArray) jsonObject.get("VideoType");
			JSONArray documentTypes = (JSONArray) jsonObject.get("DocumentType");
			JSONArray musicTypes = (JSONArray) jsonObject.get("MusicType");

			String[] typeImages = new String[imageTypes.size()];
			String[] typeVideos = new String[videoTypes.size()];
			String[] typeDocuments = new String[documentTypes.size()];
			String[] typeMusics = new String[musicTypes.size()];

			imageTypes.toArray(typeImages);
			videoTypes.toArray(typeVideos);
			musicTypes.toArray(typeMusics);
			documentTypes.toArray(typeDocuments);
			
			System.out.println(imageTypes);

			fileFilerDocument = new CustomFileNameFilter(typeDocuments);
			fileFilerImage = new CustomFileNameFilter(typeImages);
			fileFilerVideo = new CustomFileNameFilter(typeVideos);
			fileFilerMusic = new CustomFileNameFilter(typeMusics);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
