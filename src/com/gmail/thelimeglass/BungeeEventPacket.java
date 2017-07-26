package com.gmail.thelimeglass;

import java.io.Serializable;

public class BungeeEventPacket implements Serializable {

	private static final long serialVersionUID = -1800776792866810328L;
	private final Boolean settable;
	private final Object object, setObject;
	private final BungeeEventPacketType type;

	public BungeeEventPacket(Boolean settable, Object object, BungeeEventPacketType type) {
		this.settable = settable;
		this.object = object;
		this.type = type;
		this.setObject = null;
	}
	
	public BungeeEventPacket(Boolean returnable, Object object, Object setObject, BungeeEventPacketType type) {
		this.settable = returnable;
		this.object = object;
		this.type = type;
		this.setObject = setObject;
	}

	public Boolean isSettable() {
		return settable;
	}
	
	public Object getObject() {
		return object;
	}
	
	public BungeeEventPacketType getType() {
		return type;
	}
	
	public Object getSetObject() {
		return setObject;
	}
}
