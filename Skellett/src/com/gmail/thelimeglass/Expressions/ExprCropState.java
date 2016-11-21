package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprCropState extends SimpleExpression<String> {
	
	//crop state
	
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(PlayerInteractEvent.class)) {
			Skript.error("You can not use CropState expression in any event but PlayerInteractEvent!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Crop State";
	}
	@SuppressWarnings("deprecation")
	@Nullable
	protected String[] get(Event e) {
		if (((PlayerInteractEvent) e).getClickedBlock().getType().equals(Material.CROPS)) {
			if (((PlayerInteractEvent) e).getClickedBlock().getData() == CropState.RIPE.getData()){
				return new String[]{"RIPE"};
			} else if (((PlayerInteractEvent) e).getClickedBlock().getData() == CropState.GERMINATED.getData()){
				return new String[]{"GERMINATED"};
			} else if (((PlayerInteractEvent) e).getClickedBlock().getData() == CropState.MEDIUM.getData()){
				return new String[]{"MEDIUM"};
			} else if (((PlayerInteractEvent) e).getClickedBlock().getData() == CropState.SEEDED.getData()){
				return new String[]{"SEEDED"};
			} else if (((PlayerInteractEvent) e).getClickedBlock().getData() == CropState.TALL.getData()){
				return new String[]{"TALL"};
			} else if (((PlayerInteractEvent) e).getClickedBlock().getData() == CropState.VERY_SMALL.getData()){
				return new String[]{"VERY_SMALL"};
			} else if (((PlayerInteractEvent) e).getClickedBlock().getData() == CropState.VERY_TALL.getData()){
				return new String[]{"VERY_TALL"};
			} else {
				return null;
			}
		}
		return null;
	}
}