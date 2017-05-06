package com.gmail.thelimeglass.Maps;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[skellett] [the] image (of|from) [the] file [(location|path)] %string%")
@Config("Main.Maps")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprImageFile extends SimpleExpression<Image> {
	
	private Expression<String> path;
	public Class<? extends Image> getReturnType() {
		return  Image.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		path = (Expression<String>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[skellett] [the] image (of|from) [the] file [(location|path)] %string%";
	}
	@Nullable
	protected Image[] get(Event e) {
		if (path != null) {
			try {
				File f = new File(path.getSingle(e));
				if (f != null) {
					return new Image[]{ImageIO.read(f)};
				}
			} catch (IOException error) {
				error.printStackTrace();
			}
		}
		return null;
	}
}