package me.limeglass.skellett.elements.expressions;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;

@Name("Time Values")
@Description("Returns the defined time unit of the timespans.")
@Examples({
	"if hours of a day is 24 hours:",
	"if weeks of a day is 0.14285714285714285714285714285714:"
})
public class ExprTimeValues extends SimpleExpression<Double> {

	static {
		Skript.registerExpression(ExprTimeValues.class, Double.class, ExpressionType.COMBINED, "(0¦ticks|1¦milli[( |-)]seconds|2¦seconds|3¦minutes|4¦hours|5¦days|6¦weeks|7¦months|8¦years) (of|from|[with]in) %timespans%");
	}

	private Expression<Timespan> timespans;
	private int setting;

	@Override
	public Class<? extends Double> getReturnType() {
		return Double.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		timespans = (Expression<Timespan>) expressions[0];
		setting = parseResult.mark;
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (debug || timespans == null)
			return "time values of timespan";
		switch (setting) {
			case 0:
				return "ticks of timespan(s): " + timespans.toString(event, debug);
			case 1:
				return "milliseconds of timespan(s): " + timespans.toString(event, debug);
			case 2:
				return "seconds of timespan(s): " + timespans.toString(event, debug);
			case 3:
				return "minutes of timespan(s): " + timespans.toString(event, debug);
			case 4:
				return "hours of timespan(s): " + timespans.toString(event, debug);
			case 5:
				return "days of timespan(s): " + timespans.toString(event, debug);
			case 6:
				return "weeks of timespan(s): " + timespans.toString(event, debug);
			case 7:
				return "months of timespan(s): " + timespans.toString(event, debug);
			case 8:
				return "years of timespan(s): " + timespans.toString(event, debug);
			default:
				return "time values of timespan";
		}
	}

	@Override
	@Nullable
	protected Double[] get(Event event) {
		if (timespans == null)
			return null;
		return Arrays.stream(timespans.getArray(event)).map(timespan -> {
			switch (setting) {
				case 0:
					return (double) timespan.getTicks_i();
				case 1:
					return (double) timespan.getMilliSeconds();
				case 2:
					return (double) TimeUnit.MILLISECONDS.toSeconds(timespan.getMilliSeconds());
				case 3:
					return (double) TimeUnit.MILLISECONDS.toMinutes(timespan.getMilliSeconds());
				case 4:
					return (double) TimeUnit.MILLISECONDS.toHours(timespan.getMilliSeconds());
				case 5:
					return (double) TimeUnit.MILLISECONDS.toDays(timespan.getMilliSeconds());
				case 6:
					return (double) TimeUnit.MILLISECONDS.toDays(timespan.getMilliSeconds()) / 7;
				case 7:
					return (double) (TimeUnit.MILLISECONDS.toDays(timespan.getMilliSeconds()) / 7) / 4.348214;
				case 8:
					return (double) ((TimeUnit.MILLISECONDS.toDays(timespan.getMilliSeconds()) / 7) / 4.348214) / 12;
				default:
					return null;
			}
		}).filter(Objects::nonNull).toArray(Double[]::new);
	}

}
