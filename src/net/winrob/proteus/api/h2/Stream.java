package net.winrob.proteus.api.h2;

import net.winrob.proteus.api.request.StreamBuffer;

public interface Stream {
	
	public int getID();
	
	public StreamState getState();
	
	public StreamBuffer getStreamBuffer();

}
