package net.aionstudios.proteus.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ProxyClient {
	
	private Socket clientSocket;
	
	private InputStream in;
	private OutputStream out;
	
	private boolean opened = false;
	
	public ProxyClient(String ip, int port, InputStream in, OutputStream out) {
		try {
			clientSocket = new Socket(ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.in = in;
		this.out = out;
	}
	
	public void write(String content) throws IOException {
		OutputStream clientOut = clientSocket.getOutputStream();
		clientOut.write(content.getBytes(StandardCharsets.UTF_8));
	}
	
	public void open() throws IOException {
		if (!opened) {
			opened = true;
			InputStream clientIn = clientSocket.getInputStream();
			OutputStream clientOut = clientSocket.getOutputStream();
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						while(!clientSocket.isClosed()) {
							clientOut.write(in.read());
						}
						clientOut.close();
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}).start();
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						while(!clientSocket.isClosed()) {
							out.write(clientIn.read());
						}
						clientIn.close();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}).start();
		}
	}

}
