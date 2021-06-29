package net.aionstudios.proteus.request;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.net.httpserver.HttpExchange;

import net.aionstudios.proteus.api.util.RequestUtils;
import net.aionstudios.proteus.compression.CompressionEncoding;

public class ProteusHttpRequest {
	
	private String host;
	private String path;
	
	private ParameterMap urlParameters;
	private ParameterMap postParameters;
	private ParameterMap cookies;
	
	private Set<MultipartFile> files;
	
	private CompressionEncoding compressionEncoding;
	
	private HttpExchange exchange;
	
	public ProteusHttpRequest(HttpExchange exchange, CompressionEncoding compression) {
		resolveURI(exchange.getRequestURI().toString());
		resolveBody(exchange);
		compressionEncoding = compression;
		host = exchange.getRequestHeaders().getFirst("Host").split(":")[0];
		this.exchange = exchange;
	}
	
	HttpExchange getHttpExchange() {
		return exchange;
	}
	
	private void resolveURI(String uri) {
		String[] requestSplit;
		if(uri.contains("?")) {
			requestSplit = uri.split("\\?", 2);
		} else {
			requestSplit = new String[2];
			requestSplit[0] = uri.toString();
			requestSplit[1] = "";
		}
		Map<String, String> getP = new HashMap<String, String>();
		if(requestSplit.length>1) {
			getP = RequestUtils.resolveGetQuery(requestSplit[1]);
		}
		path = requestSplit[0];
		urlParameters = new ParameterMap(getP);
	}
	
	private void resolveBody(HttpExchange he) {
		final String cT = he.getRequestHeaders().containsKey("Content-Type") ? he.getRequestHeaders().getFirst("Content-Type") : "text/html";
		if(cT.contains("multipart/form-data")||cT.contains("multipart/stream")) {
			DiskFileItemFactory d = new DiskFileItemFactory();
			try {
				ServletFileUpload up = new ServletFileUpload(d);
				List<FileItem> result = up.parseRequest(new RequestContext() {

					@Override
					public String getCharacterEncoding() {
						return "UTF-8";
					}

					@Override
					public int getContentLength() {
						return 0; //tested to work with 0 as return
					}

					@Override
					public String getContentType() {
						return cT;
					}

					@Override
					public InputStream getInputStream() throws IOException {
						return he.getRequestBody();
					}

				});
				for(FileItem fi : result) {
					if(!fi.isFormField()) {
			        	files.add(new MultipartFile(fi));
			        } else {
			        	if (postParameters == null) postParameters = new ParameterMap(new HashMap<>());
			        	postParameters.putParameter(fi.getFieldName(), fi.getString());
			        }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if(he.getRequestMethod().equalsIgnoreCase("POST")) {
				postParameters = new ParameterMap(RequestUtils.resolvePostQuery(he));
			}
		}
		cookies = new ParameterMap(RequestUtils.resolveCookies(he));
	}
	
	public String getHost() {
		return host;
	}

	public String getPath() {
		return path;
	}

	public ParameterMap getUrlParameters() {
		return urlParameters;
	}

	public ParameterMap getPostParameters() {
		return postParameters;
	}

	public ParameterMap getCookies() {
		return cookies;
	}

	public Set<MultipartFile> getFiles() {
		return files;
	}

	public CompressionEncoding getCompressionEncoding() {
		return compressionEncoding;
	}

}
