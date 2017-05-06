package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("(leash|lead) %livingentities% to %block%")
@Config("LeashBlock")
public class EffLeashBlock extends Effect {
	
	private Expression<LivingEntity> entities;
	private Expression<Block> block;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entities = (Expression<LivingEntity>) e[0];
		block = (Expression<Block>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(leash|lead) %livingentities% to %block%";
	}
	@Override
	protected void execute(Event e) {
		Entity hitch = null;
		if (block.getSingle(e) == null) return;
		if (block.getSingle(e).getType() != Material.FENCE) {
			Material type = block.getSingle(e).getType();
			block.getSingle(e).setType(Material.FENCE);
			hitch = block.getSingle(e).getLocation().getWorld().spawn(block.getSingle(e).getLocation(), LeashHitch.class);
			block.getSingle(e).setType(type);
		} else {
			hitch = block.getSingle(e).getLocation().getWorld().spawn(block.getSingle(e).getLocation(), LeashHitch.class);
		}
		if (hitch != null && entities.getAll(e) != null ) {
			for (LivingEntity entity : entities.getAll(e)) {
				entity.setLeashHolder(hitch);
			}
		}
	}
}