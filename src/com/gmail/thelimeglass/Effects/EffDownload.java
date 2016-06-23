package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.regex.Matcher;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Events.EvtDownload;

public class EffDownload extends Effect {
	
	//[skellett] d[ownload][l] [from] %string% to %string%
	
	private Expression<String> url;
	private Expression<String> file;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		this.url = (Expression<String>) e[0];
		this.file = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] d[ownload][l] [from] %string% to %string%";
	}
	protected void execute(Event event) {
		String u = (String)url.getSingle(event);
		String f = (String)file.getSingle(event);
		if (u == null || f == null) {
			return;
		}
		EvtDownload custom = new EvtDownload(u);
		Bukkit.getServer().getPluginManager().callEvent((Event)custom);
		if (!custom.isCancelled()) {
			f = f.replaceAll("/", Matcher.quoteReplacement(File.separator));
			File file = new File(f);
			try {
				URL website = new URL(u);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				@SuppressWarnings("resource")
				FileOutputStream fos = new FileOutputStream(file);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			}
			catch (Exception e) {
	            e.printStackTrace();
			}
		}
	}
}