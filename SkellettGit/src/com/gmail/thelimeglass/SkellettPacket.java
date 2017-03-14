package com.gmail.thelimeglass;

import java.io.Serializable;

public class SkellettPacket implements Serializable {

	private static final long serialVersionUID = -7377209366283539512L;
	private final Boolean returnable;
	private final Object object;
	private final Object settable;
	private final SkellettPacketType type;

	public SkellettPacket(Boolean returnable, Object object, SkellettPacketType type) {
		this.returnable = returnable;
		this.object = object;
		this.type = type;
		this.settable = null;
	}
	
	public SkellettPacket(Boolean returnable, Object object, Object settable, SkellettPacketType type) {
		this.returnable = returnable;
		this.object = object;
		this.type = type;
		this.settable = settable;
	}

	public Boolean isReturnable() {
		return returnable;
	}
	
	public Object getObject() {
		return object;
	}
	
	public SkellettPacketType getType() {
		return type;
	}
	
	public Object getSetObject() {
		return settable;
	}
}
