package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.SkellettFiles;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

@Syntax("[skellett] c[reate][ ][f][ile] %string%")
@Config("Main.Files")
@FullConfig
public class EffFilesCreate extends Effect {
	
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
		File fileExistance = new File(file.getSingle(e));
		if (file.getSingle(e).contains("/")) {
			fileExistance = new File(file.getSingle(e).replaceAll("/'", File.separator));
		}
		if (!fileExistance.exists()) {
			try {
				SkellettFiles.createFileAndPath(fileExistance);
			} catch (IOException error) {
				error.printStackTrace();
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
