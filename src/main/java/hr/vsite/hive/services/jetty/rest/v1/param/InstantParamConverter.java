package hr.vsite.hive.services.jetty.rest.v1.param;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.ws.rs.ext.ParamConverter;

import org.apache.commons.lang3.StringUtils;

public class InstantParamConverter implements ParamConverter<Instant> {

	@Override
	public Instant fromString(String time) {

		if (StringUtils.isBlank(time))
			return null;
		
		for (DateTimeFormatter formatter : formatters)
			try {
				return ZonedDateTime.parse(time, formatter).toInstant();
			} catch (DateTimeParseException e) {
				// ignore and let next parser try
			}

		throw new IllegalArgumentException(time);
		
	}

	@Override
	public String toString(Instant instant) {
		if (instant == null)
			return null;
		return formatters[0].format(instant);
	}

	private static final DateTimeFormatter[] formatters = new DateTimeFormatter[] {
		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ"),
		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZ"),
		DateTimeFormatter.ofPattern("yyyy-MM-ddZZ")
	};
	
}
