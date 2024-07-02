package net.winrob.proteus.api.request;

import net.winrob.proteus.api.h2.FrameType;

public interface StreamBuffer {
	
	public boolean put(byte[] payload);
	
	public FrameType getFrameType();
	
	public int getBufferSize();
	
	public byte[] getData();

}
