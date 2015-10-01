package hr.vsite.hive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiveCmd {

	public static void main(String[] args) {

		Hive instance = Hive.get();

		instance.init();
		instance.start();
        try {
        	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        	in.readLine();	// TODO should wait for Ctrl+C instead
		} catch (IOException e) {
			log.error("Exception in main event loop", e);
		}

		instance.stop();
		instance.destroy();

	}

	private static final Logger log = LoggerFactory.getLogger(HiveCmd.class);

}
