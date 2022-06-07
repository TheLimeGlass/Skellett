package me.limeglass.skellett.elements.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;

@Name("Fishing")
@Description("Called when a player triggers a fishing event (catching a fish, failing, etc.)")
public class EvtFishing extends SkriptEvent {

	static {
		Skript.registerEvent("Fishing", EvtFishing.class, PlayerFishEvent.class, "[player] fish[ing] [state[s] [of] %-fishingstates%]");

		EventValues.registerEventValue(PlayerFishEvent.class, FishHook.class, new Getter<FishHook, PlayerFishEvent>() {
			@Override
			public FishHook get(PlayerFishEvent e) {
				return e.getHook();
			}
		}, 0);
		EventValues.registerEventValue(PlayerFishEvent.class, State.class, new Getter<State, PlayerFishEvent>() {
			@Override
			public State get(PlayerFishEvent e) {
				return e.getState();
			}
		}, 0);
		EventValues.registerEventValue(PlayerFishEvent.class, Entity.class, new Getter<Entity, PlayerFishEvent>() {
			@Override
			@Nullable
			public Entity get(PlayerFishEvent e) {
				return e.getCaught();
			}
		}, 0);
	}

	private List<State> states = new ArrayList<>();

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
		if (args[0] != null)
			states = Arrays.asList(((Literal<State>) args[0]).getAll());

		return true;
	}

	@Override
	public boolean check(Event e) {
		return states.isEmpty() || states.contains(((PlayerFishEvent) e).getState());
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return states.isEmpty() ? "fishing" : "fishing states of " + Arrays.toString(states.toArray(State[]::new));
	}

}
