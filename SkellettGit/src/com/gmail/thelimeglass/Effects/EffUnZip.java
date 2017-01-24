package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import java.io.IOException;

import javax.annotation.Nullable;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.SkellettFiles;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("[skellett] unzip %string% to %string%")
@Config("Main.Files")
@FullConfig
public class EffUnZip extends Effect {

	private Expression<String> file1;
	private Expression<String> file2;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		file1 = (Expression<String>) e[0];
		file2 = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] unzip %string% to %string%";
	}
	@Override
	protected void execute(Event e) {
		try {
			SkellettFiles.unzip(file1.getSingle(e), file2.getSingle(e));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
