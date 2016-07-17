package com.gmail.thelimeglass.Events;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;

public class EvtCropTrample extends SkriptEvent {
	
	//[on] [skellett] [player] crop (trampl(e|ing)|break[ing]|stomp[ing]|step[ping] on|destroy[ing]):
	
	public boolean check(Event e) {
		return ((PlayerInteractEvent)e).getAction().equals((Object)Action.PHYSICAL);
	}	
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parser) {
		return true;
	}
	public String toString(@Nullable Event e, boolean debug) {
		return "crop trample event";
	}
}