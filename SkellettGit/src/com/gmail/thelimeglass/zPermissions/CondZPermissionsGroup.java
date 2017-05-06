package com.gmail.thelimeglass.zPermissions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("zpermission[s] [(of|from)] group %string% (1¦ha(s|ve)|2¦(do[es]n't|don't|do[es] not) [have]) [the] permission[s] %strings% [in [world] %-string%] [and] [in [region[s]] %-strings%]")
@Config("PluginHooks.zPermissions")
@FullConfig
@MainConfig
public class CondZPermissionsGroup extends Condition {
	
	private Expression<String> group;
	private Expression<String> permissions;
	private Expression<String> world;
	private Expression<String> regions;
	private Integer marker;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		group = (Expression<String>) e[0];
		permissions = (Expression<String>) e[1];
		world = (Expression<String>) e[2];
		regions = (Expression<String>) e[3];
		marker = parser.mark;
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "zpermission[s] [(of|from)] group %string% (1¦ha(s|ve)|2¦(do[es]n't|don't|do[es] not) [have]) [the] permission[s] %strings% [in [world] %-string%] [and] [in [region[s]] %-strings%]";
	}
	public boolean check(Event e) {
		Set<String> regionSet = new HashSet<>(Arrays.asList(regions.getAll(e)));
		Map<String, Boolean> map = Skellett.zPermissions.getGroupPermissions(world.getSingle(e), regionSet, group.getSingle(e));
		if (map == null) {
			return false;
		}
		for (String perm : permissions.getAll(e)) {
			if (map.get(perm)) {
				if (marker == 2) {
					return false;
				}
			} else {
				if (marker == 1) {
					return false;
				}
			}
		}
		return true;
	}
}