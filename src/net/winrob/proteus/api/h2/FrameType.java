package net.winrob.proteus.api.h2;

import java.util.HashSet;
import java.util.Set;

public enum FrameType {
	
	DATA((byte) 0x0, false, new StreamState[] {StreamState.OPEN, StreamState.HALF_CLOSED_LOCAL}),
	HEADERS((byte) 0x1, false),
	PRIORITY((byte) 0x2, true),
	RST_STREAM((byte) 0x3, true),
	SETTINGS((byte) 0x4, true),
	PUSH_PROMISE((byte) 0x5, false),
	PING((byte) 0x6, true),
	GOAWAY((byte) 0x7, true),
	WINDOW_UPDATE((byte) 0x8, true),
	CONTINUATION((byte) 0x9, false);

	private byte value;
	private boolean isCtrlFrame;
	private Set<StreamState> allowRecv;
	
	private FrameType(byte typeCode, boolean isCtrlFrame, StreamState... allowRecv) {
		this.value = typeCode;
		this.isCtrlFrame = isCtrlFrame;
		this.allowRecv = new HashSet<>();
		for (StreamState allow : allowRecv) {
			this.allowRecv.add(allow);
		}
	}
	
	public boolean isCtrlFrame() {
		return isCtrlFrame;
	}
	
	public byte getValue() {
		return value;
	}
	
	public boolean allows(StreamState state) {
		return allowRecv != null ? allowRecv.contains(state) : true;
	}
	
	public static FrameType forValue(byte opCode) {
		switch(opCode) {
		case (0x0):
			return DATA;
		case (0x1):
			return HEADERS;
		case (0x2):
			return PRIORITY;
		case (0x3):
			return RST_STREAM;
		case (0x4):
			return SETTINGS;
		case (0x5):
			return PUSH_PROMISE;
		case (0x6):
			return PING;
		case (0x7):
			return GOAWAY;
		case (0x8):
			return WINDOW_UPDATE;
		case (0x9):
			return CONTINUATION;
		default:
			return null;
		}
	}
	
}
