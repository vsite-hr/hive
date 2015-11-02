package hr.vsite.hive.services.javafx;

import hr.vsite.hive.HiveProperties;
import hr.vsite.hive.services.javafx.scenes.SensorsScene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class FxApplication extends Application {

	/* Unfortunatelly, Guice injection can not be hacked to work in here.
	 * 
	 * Instead, use {@link FxInjector} singleton.
	 */
	public FxApplication() {}

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		super.init();
	}

	@Override
	public void start(Stage primaryStage) {

		FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(clazz -> {
            return FxInjector.get().getInstance(clazz);
        });

        // some temporray demo UI
//		Button btn = new Button();
//		btn.setText("Say 'Hello World'");
//		btn.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				System.out.println("Hello World!");
//			}
//		});

        primaryStage.setTitle("Hive + " + HiveProperties.get().getVersion()); 
        primaryStage.setScene(FxInjector.get().getInstance(SensorsScene.class));
        primaryStage.sizeToScene();

//		Text text = new Text(10, 40, "Hello World!");
//		text.setFont(new Font(40));
//		Scene scene = new Scene(new Group(text));

        primaryStage.show(); 
        
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}

}
