package hr.vsite.hive.services.jetty.rest.v1.jaxb;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class InstantAdapter extends XmlAdapter<String, Instant> {

	@Override
	public String marshal(Instant instant) throws Exception {
		return formatter.format(instant);
	}

	@Override
	public Instant unmarshal(String string) throws Exception {
		return ZonedDateTime.parse(string, formatter).toInstant();
	}

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").withZone(ZoneId.of("UTC"));

}
