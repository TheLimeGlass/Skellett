package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockSpreadEvent;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprSpreadSource extends SimpleExpression<Block> {
	
	//[spread] source block
	
	public Class<? extends Block> getReturnType() {
		return Block.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)BlockSpreadEvent.class)) {
			Skript.error((String)"You can not use Source Block expression in any event but 'on spread:' event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Source block spread";
	}
	@Nullable
	protected Block[] get(Event e) {
		return new Block[]{((BlockSpreadEvent)e).getSource()};
	}
}