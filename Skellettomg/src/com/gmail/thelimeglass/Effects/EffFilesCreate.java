package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Nullable;
import org.bukkit.event.Event;

public class EffFilesCreate extends Effect {

	//[skellett] c[reate][ ][f][ile] %string%
	
	private Expression<String> file;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		file = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] c[reate][ ][f][ile] %string%";
	}
	@Override
	protected void execute(Event e) {
		Path directory = Paths.get(file.getSingle(e).replace(File.separator + file.getSingle(e).substring(file.getSingle(e).lastIndexOf(File.separator) + 1), ""));
		if (!Files.exists(directory)) {
			try {
				Files.createDirectories(directory);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		File f = new File(file.getSingle(e));
		if (f.exists()) {
			return;
		}
		try {
			f.createNewFile();
		}
		catch (IOException error) {
			error.printStackTrace();
		}	
	}
}
