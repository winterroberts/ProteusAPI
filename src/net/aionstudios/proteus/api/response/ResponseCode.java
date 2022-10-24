package net.aionstudios.proteus.api.response;

public enum ResponseCode {
	
	/**
	 * Recursive "ternary action trees" - AI
	 *  - Action define cyclic ternary edges on a graph.
	 *  - Actions define their children (also there parents because of strictly linearly non-linear nature of progressive thought).
	 *  - "Shortest path to intent."
	 *  - Actions inherit contexts of their type with the base context type somehow (?) enabling all children types to add their
	 *    own context accessibility code from their runtime classes and use reflections to interface those with a consistent base-class
	 *    which can safely cast up at any time due to the mere presence of the subclass/type to be located and directly integrated into
	 *    the super class at runtime.
	 *     - Allows context retention across contexts with no knowledge of the the other type or access to it.
	 *  * The text editor in eclipse for comments is actually kinda epic and surprising me how it recognizes consistent formatting
	 *    after a bit. Maybe I should look at source and start doing some open source work with Eclipse?
	 */
	
	OK(200, "OK", "The request was completed without serious error and returned valid data."),
	CREATED(201, "Created", "The request created a resource."),
	ACCEPTED(202, "Accepted", "The request hasn't been acted on yet."),
	NON_AUTHORITATIVE(203, "Non-Authoritative Information", "The response may not be up-to-date."),
	FOUND(302, "Found", "The resource has been moved."),
	NO_CONTENT(204, "No Content", "The response contains no data apart from headers."),
	RESET_CONTENT(205, "Reset Content", "The response expects the client to reset the document."),
	PARTIAL_CONTENT(206, "Partial Content", "The response is incomplete because the client asked for a range."),
	MULTIPLE_CHOICE(300, "Multiple Choice", "The response requires a client decision."),
	MOVED_PERMANENTLY(301, "Moved Permanently", "The resource has been moved permanently."),
	SEE_OTHER(303, "See Other", "The response is an explicit redirect."),
	NOT_MODIFIED(304, "Not Modified", "The cached resource is up-to-date."),
	TEMPORARY_REDIRECT(307, "Temporary Redirect", "The resource has been moved, the request method should preserved."),
	PERMANENT_REDIRECT(308, "Permanent Redirect", "The resource has been moved permanently, the request method should be preserved."),
	BAD_REQUEST(400, "Bad Request", "The server couldn't understand the request."),
	UNUATHORIZED(401, "Unauthorized", "Access to the resource requires authentication."),
	FORBIDDEN(403, "Forbidden", "The server will not allow the client to access this resource."),
	NOT_FOUND(404, "Not Found", "The requested resource, was not found on this server."),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed", "The request method is disabled by the server or doesn't exist."),
	NOT_ACCEPTABLE(406, "Not Acceptable", "The server couldn't meet client content expectations."),
	PROXY_AUTHETICATION_REQUIRED(407, "Proxy Authentication Required", "Access to the resource requires authentication through a proxy."),
	REQUEST_TIMEOUT(408, "Request Timeout", "The server couldn't complete and transmit the resource in a reasonable time."),
	CONFLICT(409, "Conflict", "The request conflicts with the server's current state."),
	GONE(410, "Gone", "The resource has been removed."),
	LENGTH_REQUIRED(411, "Length Required", "The resource requires a Content-Length header form the client."),
	PRECONDITION_FAILED(412, "Precondition Failed", "The server does not meet client request preconditions."),
	PAYLOAD_TOO_LARGE(413, "Payload Too Large", "The client attempted to send more content than the server allows."),
	URI_TOO_LONG(414, "URI Too Long", "The client requested a URI of a length which the server will not interpret."),
	UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type", "The client request contained a media type the server rejected."),
	REQUEST_RANGE_NOT_SATISFIABLE(416, "Request Range Not Satisfiable", "The resource cannot be provided at the client-specified range."),
	EXPECTATION_FAILED(417, "Expectation Failed", "The server cannot meet the client-specified expectations."),
	UNPROCESSABLE_ENTITY(422, "Unprocessable Entity", "The request was accepted but was unprocessable due to semantic errors."),
	TOO_EARLY(425, "Too Early", "The server wants to avoid processing a request that may be replayed."),
	UPGRADE_REQUIRED(426, "Upgrade Required", "The server will not accept requests from the current protocol."),
	PRECONDITION_REQUIRED(428, "Precondition Failed", "The server requires the request to be conditional."),
	TOO_MANY_REQUESTS(429, "Too Many Requests", "The client is being rate-limited."),
	REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large", "The client header is of a length which the server will not interpret."),
	UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons", "The resource could not be legally transmitted to the client."),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error", "The server encountered an unhandled internal error and was unable to complete your request."),
	NOT_IMPLEMENTED(501, "Not Implemented", "The request method is not implemented by the server."),
	BAD_GATEWAY(502, "Bad Gateway", "A downstream resource from the server, as a gateway, errored."),
	SERVICE_UNAVAILABLE(503, "Service Unavailable", "The server is unable to process your request right now."),
	GATEWAY_TIMEOUT(504, "Gateway Timeout", "A downstream resource from the server, as a gateway, timed out."),
	HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported", "The client request specifies a version of HTTP that the server does not support."),
	VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates", "The end resource requires content negotiation; inherently misconfigured."),
	NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required", "Authentication is required to access resources on this network.");

	private int value;
	private String name;
	private String desc;

	/**
	 * Creates a {@link ResponseStatus} by value.
	 * @param newValue An integer representing the value of an enum in this class.
	 */
	ResponseCode(int value, String name, String desc) {
		this.value = value;
		this.name = name;
		this.desc = desc;
	}

	/**
	 * @return The numeric value of a definition.
	 */
	public int getCode() {
		return value;
	}
	
	/**
	 * @return A string representing the name of this {@link ResponseCode}.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return A string describing what this {@link ResponseCode} means.
	 */
	public String getDesc() {
		return desc;
	}
	
}