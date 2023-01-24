package me.limeglass.skellett.lang;

import java.util.Arrays;
import java.util.function.BiConsumer;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAPIException;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

/**
 * A class that is to be used for boolean expressions.
 * <p>
 * A boolean expression should be a condition for getting it's value.
 */
public abstract class SetEffect<T> extends Effect {

	private Expression<Boolean> value;
	private Expression<T> expression;
	private boolean make;

	/**
	 * Registers an effect with patterns "set property of %type% to %boolean%" and "set %types%'[s] property to %boolean%"
	 * 
	 * @param effect The SetEffect class that you are registering.
	 * @param property The property of the syntax.
	 * @param type The type classinfo of the syntax. Can be plural.
	 */
	public static void register(Class<? extends SetEffect<?>> effect, String property, String type) {
		Skript.registerEffect(effect, "set " + property + " of %" + type + "% to %boolean%",
				"set %" + type + "%'[s] " + property + " to %boolean%",
				"make %" + type + "% " + property);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		make = matchedPattern == 1;
		expression = (Expression<T>) exprs[matchedPattern];
		if (exprs.length != 2) {
			// SetEffect was not registered with this classes' register method.
			if (!make) {
				throw new SkriptAPIException("There was not two expressions in the SetEffect class '" + getClass().getName() + "' exprs: " + Arrays.toString(exprs));
			}
			return true;
		}
		value = (Expression<Boolean>) exprs[matchedPattern ^ 1];
		return true;
	}

	/**
	 * Returns the value of the boolean expression used.
	 * 
	 * @param event The event that is calling this syntax.
	 * @return boolean from the boolean expression used in this set effect.
	 */
	protected boolean getBoolean(Event event) {
		return value.getSingle(event);
	}

	/**
	 * Returns the expression that was registered as the type for this SetEffect.
	 * 
	 * @return Expression<T> for getting the values of the expression used.
	 */
	protected Expression<T> getExpression() {
		return expression;
	}

	/**
	 * Return the property name of this SetEffect used for the {@link #toString(Event, boolean)} method.
	 * 
	 * @return String representing the name of the property registered for this syntax.
	 */
	protected abstract String getPropertyName();

	/**
	 * The method that will execute the effect.
	 * 
	 * @param event The event that is calling this syntax.
	 * @param biconsumer The BiConsumer that will execute the boolean on the type value. Loops through all values.
	 */
	protected void apply(Event event, BiConsumer<T, Boolean> biconsumer) {
		boolean value = make ? true : getBoolean(event);
		getExpression().stream(event).forEach(expression -> biconsumer.accept(expression, value));
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (debug || event == null)
			return "setting " + getPropertyName();
		return "set " + getPropertyName() + " of " + expression.toString(event, debug) + " to " + value.toString(event, debug);
	}

}
