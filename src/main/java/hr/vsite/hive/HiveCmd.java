package hr.vsite.hive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiveCmd {

	public static void main(String[] args) throws Exception {

		Hive hive = Hive.get();

		hive.init();
		hive.start();
        try {
        	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        	String line;
        	do {
        		line = in.readLine();	// TODO should wait for Ctrl+C instead
        	} while (line != null && !line.equals("q"));   
		} catch (IOException e) {
			log.error("Exception in main event loop", e);
		}

		hive.stop();
		hive.destroy();

	}

	private static final Logger log = LoggerFactory.getLogger(HiveCmd.class);

}
