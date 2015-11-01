package hr.vsite.hive.services.javafx.scenes;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import hr.vsite.hive.HiveEventBus;
import hr.vsite.hive.dao.HiveDao;
import hr.vsite.hive.sensors.Sensor;
import hr.vsite.hive.sensors.SensorFilter;
import hr.vsite.hive.ticks.ValueTickEvent;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Window;

/**
 * <p><b>WARNING</b>:</p>
 * 
 * <p>Prototype class, for presentation only.</p>
 * 
 * </>Heavy refactoring needed!</p>
 */
public class SensorsScene extends Scene {

	@Inject
	public SensorsScene(HiveDao dao, HiveEventBus eventBus) {

		super(new GridPane());
		
        root = GridPane.class.cast(getRoot());
        root.setHgap(50);
        root.setVgap(10);
        
        for (Sensor sensor : dao.listSensors(new SensorFilter(), 10, 0)) {
        	addSensor(sensor);
        }
		
        eventBus.register(this);

	}

	@Subscribe
	public void onValueTick(final ValueTickEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Text text = sensorTexts.get(event.getTick().getSensor());
				if (text == null)
					text = addSensor(event.getTick().getSensor());
				text.setText(String.valueOf(event.getTick().getValue()));
			}
		});
	}
	
	private Text addSensor(Sensor sensor) {

		Text nameLabel = new Text(sensor.getDeviceId());
		nameLabel.setFont(Font.font("Ubuntu", FontWeight.NORMAL, 40));
		Text valueLabel = new Text("#");
		valueLabel.setFont(Font.font("Ubuntu Mono", FontWeight.BOLD, 40));

		root.add(nameLabel, 0, sensorTexts.size());
		root.add(valueLabel, 1, sensorTexts.size());
		
    	sensorTexts.put(sensor, valueLabel);

    	Window window = windowProperty().get();
    	if (window != null)
    		window.sizeToScene(); 

    	return valueLabel;
    	
	}

	private final Map<Sensor, Text> sensorTexts = new HashMap<Sensor, Text>();
	private final GridPane root;

}
