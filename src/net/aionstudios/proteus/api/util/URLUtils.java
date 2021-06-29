package net.aionstudios.proteus.api.util;

public class URLUtils {
	
	private static String PATH_SEGMENT_REGEX = "(\\/[a-z0-9\\-._~%!$&'()*+,;=:@]+)*\\/?|\\/([#?]|$)";
	
	public static boolean isValidPathSegment(String pathSegmentCandidate) {
		return pathSegmentCandidate.matches(PATH_SEGMENT_REGEX);
	}

}
