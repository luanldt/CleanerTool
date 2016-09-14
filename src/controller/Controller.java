package controller;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import static java.nio.file.StandardCopyOption.*;

public class Controller {
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

	@FXML
	void chooseFolderSource(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		sourceDir = directoryChooser.showDialog(null);
		LabelSource.setText(sourceDir.toString());
	}

	@FXML
	void doMove(ActionEvent event) {
		File[] sources = null;
		if(RadioButtonImage.isSelected()) {
			sources = sourceDir.listFiles((File file) -> file.getName().endsWith(".jpg"));
		}
		if(RadioButtonVideo.isSelected()) {
			sources = sourceDir.listFiles((File file) -> file.getName().endsWith(".mp3"));
		}
		if(RadioButtonDocument.isSelected()) {
			sources = sourceDir.listFiles((File file) -> file.getName().endsWith(".pdf"));
		}
		if(RadioButtonMusic.isSelected()) {
			sources = sourceDir.listFiles((File file) -> file.getName().endsWith(".mp3"));
		}
		for(File file : sources) {
			try {
				Files.move(file.toPath(), new File(destinationDir.getAbsolutePath() +"/" + file.getName()).toPath(), REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
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
}
