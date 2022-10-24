package net.aionstudios.proteus.util;

public class URLUtils {
	
	private static String PATH_SEGMENT_REGEX = "(\\/[a-z0-9\\-._~%!$&'()*+,;=:@]+)*\\/?|\\/([#?]|$)";
	
	/**
	 * Recognizes valid path segments (single portion from '/' to '/' or EOF of the path component of a url string).
	 * @param pathSegmentCandidate The path segment string to be tested.
	 * @return True if the pathSegmentCandidate is valid, false otherwise.
	 */
	public static boolean isValidPathSegment(String pathSegmentCandidate) {
		return pathSegmentCandidate.matches(PATH_SEGMENT_REGEX);
	}

}
