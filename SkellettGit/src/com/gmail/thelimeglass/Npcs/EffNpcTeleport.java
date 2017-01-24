package com.gmail.thelimeglass.Npcs;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("teleport (npc|citizen) %npc% to %location% [with (1¦%-teleportcause%|2¦%-string%)]")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
public class EffNpcTeleport extends Effect {
	
	private Expression<NPC> npc;
	private Expression<Location> location;
	private Expression<TeleportCause> teleportcause;
	private Expression<String> teleportcausestring;
	private Integer marker = 1;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		marker = parser.mark;
		npc = (Expression<NPC>) e[0];
		location = (Expression<Location>) e[1];
		teleportcause = (Expression<TeleportCause>) e[2];
		teleportcausestring = (Expression<String>) e[3];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "teleport (npc|citizen) %npc% to %location% [with (1¦%teleportcause%|2¦%string%)]";
	}
	@Override
	protected void execute(Event e) {
		if (teleportcause == null && teleportcausestring == null && location != null) {
			npc.getSingle(e).teleport(location.getSingle(e), TeleportCause.PLUGIN);
		} else if (location != null) {
			if (marker == 1) {
				npc.getSingle(e).teleport(location.getSingle(e), teleportcause.getSingle(e));
			} else {
				TeleportCause t = TeleportCause.valueOf(teleportcausestring.getSingle(e).replace("\"", "").trim().replace(" ", "_").toUpperCase());
				try {
					t = TeleportCause.valueOf(t.toString().replace("\"", "").trim().replace(" ", "_").toUpperCase());
				} catch (IllegalArgumentException error) {
					Skript.error(t.toString() + " Unknown teleortcause type");
					return;
				}
				if (t != null) {
					npc.getSingle(e).teleport(location.getSingle(e), t);
				}
			}
		}
	}
}
