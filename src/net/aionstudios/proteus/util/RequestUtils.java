package net.aionstudios.proteus.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import net.aionstudios.proteus.api.request.ProteusHttpRequest;
import net.aionstudios.proteus.header.ProteusHttpHeaders;

/**
 * A utility class to standardize and ease access to header variables.
 * @author Winter Roberts
 */
public class RequestUtils {
	
	/**
	 * Gets the IP address of a client if it traveled through a normal proxy or other system that provides the X-Forwarded-For header.
	 * @param request The {@link ProteusHttpRequest}
	 * @return The IP address of the request origin.
	 */
	public static String getRequestIP(ProteusHttpRequest request) {
		ProteusHttpHeaders headers = request.getHeaders();
		if(headers.hasHeader("X-Forwarded-For")) {
			return headers.getHeader("X-Forwarded-For").getFirst().getValue();
		}
		return request.getRemoteAddress();
	}
	
	/**
	 * Maps elements of a get query for easy processing.
	 * @param qs A query string from url.
	 * @return A HashMap of get parameters and their values.
	 */
	public static Map<String, String> resolveQueryString(String qs) {
	    Map<String, String> result = new HashMap<>();
	    if (qs == null)
	        return result;

	    int last = 0, next, l = qs.length();
	    while (last < l) {
	        next = qs.indexOf('&', last);
	        if (next == -1)
	            next = l;

	        if (next > last) {
	            int eqPos = qs.indexOf('=', last);
	            try {
	                if (eqPos < 0 || eqPos > next)
	                    result.put(URLDecoder.decode(qs.substring(last, next), "UTF-8"), "");
	                else
	                    result.put(URLDecoder.decode(qs.substring(last, eqPos), "UTF-8"), URLDecoder.decode(qs.substring(eqPos + 1, next), "UTF-8"));
	            } catch (UnsupportedEncodingException e) {
	                throw new RuntimeException(e); // will never happen, utf-8 support is mandatory for java
	            }
	        }
	        last = next + 1;
	    }
	    return result;
	}

}
