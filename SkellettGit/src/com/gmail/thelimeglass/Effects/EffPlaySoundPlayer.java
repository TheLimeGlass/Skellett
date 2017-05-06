package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] play [(skellett|better)] [sound] (1¦%-sound%|2¦%-string%) (for|to) %players% (with|at|and) volume %number% (and|with|at) pitch %number%")
@Config("Sound")
public class EffPlaySoundPlayer extends Effect {
	
	private Expression<Sound> sound;
	private Expression<String> soundstring;
	private Expression<Player> player;
	private Expression<Number> volume;
	private Expression<Number> pitch;
	private Integer marker = 1;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		marker = parser.mark;
		sound = (Expression<Sound>) e[0];
		soundstring = (Expression<String>) e[1];
		player = (Expression<Player>) e[2];
		volume = (Expression<Number>) e[3];
		pitch = (Expression<Number>) e[4];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "play [(skellett|better)] [sound] (1¦%skellettsound%|2¦%string%) (for|to) %players% (with|at|and) volume %number% (and|with|at) pitch %number%";
	}
	@Override
	protected void execute(Event e) {
		Number[] pitches = new Number[]{0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0};
		Integer pit = pitch.getSingle(e).intValue();
		if (pit >= pitches.length || pit < 0) {
			pit = 0;
		}
		Object s = null;
		if (marker == 1 && sound.getSingle(e) instanceof Sound) {
			s = sound.getSingle(e);
		} else {
			try {
				s = Sound.valueOf(soundstring.getSingle(e).replace("\"", "").trim().replace(" ", "_").toUpperCase());
			} catch (IllegalArgumentException error) {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cUnknown sound type " + soundstring.getSingle(e)));
				return;
			}
		}
		if (s == null) {
			return;
		}
		for (Player player : player.getAll(e)) {
			player.playSound(player.getLocation(), (Sound) s, volume.getSingle(e).floatValue(), pitches[pit].floatValue());
		}
	}

}