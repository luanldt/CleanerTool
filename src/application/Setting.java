package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Setting{
	public void start() {
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Setting.fxml"));
			primaryStage.setTitle("Setting Cleaner Tool");
			primaryStage.setScene(new Scene(root, 600, 400));
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.initStyle(StageStyle.UTILITY);
			primaryStage.showAndWait();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
