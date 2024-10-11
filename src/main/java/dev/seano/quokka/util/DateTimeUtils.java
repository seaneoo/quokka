package dev.seano.quokka.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateTimeUtils {

	public static final ZoneOffset DEFAULT_ZONE = ZoneOffset.UTC;

	public static ZonedDateTime now() {
		return ZonedDateTime.now(DEFAULT_ZONE);
	}
}
