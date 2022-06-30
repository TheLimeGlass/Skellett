package me.limeglass.skellett.elements.sections;

import java.util.List;

import org.bukkit.FluidCollisionMode;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.util.RayTraceResult;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptConfig;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.util.Direction;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;

public class SecRayTrace extends Section {

	static {
		Skript.registerSection(SecRayTrace.class, "[make] ray trace %direction% [%livingentity/block%] [with max distance %-number%] [with %fluidcollisionmode%]");
	}

	public class RayTraceEvent extends Event {

		private final static HandlerList handlers = new HandlerList();
		private final RayTraceResult result;

		public RayTraceEvent(RayTraceResult result) {
			this.result = result;
		}

		public RayTraceResult getResult() {
			return result;
		}

		@Override
		public HandlerList getHandlers() {
			return handlers;
		}

		public static HandlerList getHandlerList() {
			return handlers;
		}

	}

	private Expression<FluidCollisionMode> fluidCollisionMode;
	private Expression<Direction> direction;
	private Expression<Number> distance;
	private Expression<Object> source;
	private TriggerItem actualNext;
	private Trigger trigger;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> triggerItems) {
		trigger = loadCode(sectionNode, "ray trace event", RayTraceEvent.class);
		direction = (Expression<Direction>) exprs[0];
		source = (Expression<Object>) exprs[1];
		distance = (Expression<Number>) exprs[2];
		fluidCollisionMode = (Expression<FluidCollisionMode>) exprs[3];
		return true;
	}

	@Override
	protected @Nullable TriggerItem walk(Event event) {
		double distance = this.distance == null ? SkriptConfig.maxTargetBlockDistance.value() : this.distance.getSingle(event).doubleValue();
		FluidCollisionMode fluidCollisionMode = this.fluidCollisionMode == null ? FluidCollisionMode.ALWAYS : this.fluidCollisionMode.getSingle(event);
		RayTraceResult result = null;
		Object source = this.source.getSingle(event);
		if (source instanceof Block) {
			Block block = (Block) source;
			result = block.rayTrace(block.getLocation(), direction.getSingle(event).getDirection(block), distance, fluidCollisionMode);
		} else {
			LivingEntity entity = (LivingEntity) source;
			result = entity.rayTraceBlocks(distance, fluidCollisionMode);
		}
		if (result == null)
			return actualNext;
		Object localVars = Variables.copyLocalVariables(event);
		RayTraceEvent raytraceEvent = new RayTraceEvent(result);
		Variables.setLocalVariables(raytraceEvent, localVars);
		trigger.execute(raytraceEvent);
		return actualNext;
	}

	@Override
	public SecRayTrace setNext(@Nullable TriggerItem next) {
		actualNext = next;
		return this;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "ray trace";
		return "ray trace in direction " + direction.toString(event, debug) + " to " + source.toString(event, debug);
	}

}
