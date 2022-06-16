package me.limeglass.skellett.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Name("Fishing Hooked Entity")
@Description("Returns the hooked entity of the fishing hook.")
public class ExprFishingHookEntity extends SimplePropertyExpression<FishHook, Entity> {

	static {
		register(ExprFishingHookEntity.class, Entity.class, "hook[ed] entity", "fishinghook");
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		setExpr((Expression<FishHook>) exprs[0]);
		return true;
	}

	@Override
	protected String getPropertyName() {
		return "hooked entity of fishing hook";
	}

	@Override
	public @Nullable Entity convert(FishHook fishHook) {
		return fishHook.getHookedEntity();
	}

	@Override
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}

	@Nullable
	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		switch (mode) {
			case DELETE:
			case SET:
				return CollectionUtils.array(Entity.class);
			default:
				return null;
		}
	}

	@Override
	public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
		if (delta == null || delta[0] == null) {
			return;
		}

		FishHook[] hooks = getExpr().getArray(e);
		switch (mode) {
			case SET:
				for (FishHook fh : hooks)
					fh.setHookedEntity((Entity) delta[0]);
				break;
			case DELETE:
				for (FishHook fh : hooks) {
					if (fh.getHookedEntity() != null && !(fh.getHookedEntity() instanceof Player))
						fh.getHookedEntity().remove();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "hooked entity of " + getExpr().toString(e, debug);
	}

}
