package com.gmail.thelimeglass;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.gmail.thelimeglass.Conditions.CondClientTimeRelative;
import com.gmail.thelimeglass.Maps.SkellettMapRenderer;
import com.gmail.thelimeglass.Scoreboards.CondObjectiveExists;
import com.gmail.thelimeglass.Scoreboards.CondObjectiveIsModifiable;
import com.gmail.thelimeglass.Scoreboards.CondTeamHasEntry;
import com.gmail.thelimeglass.Scoreboards.EffDeleteScoreboard;
import com.gmail.thelimeglass.Scoreboards.EffRegisterObjective;
import com.gmail.thelimeglass.Scoreboards.EffRegisterTeam;
import com.gmail.thelimeglass.Scoreboards.EffResetEntryScores;
import com.gmail.thelimeglass.Scoreboards.EffScoreboardClearSlot;
import com.gmail.thelimeglass.Scoreboards.EffTeamAddEntry;
import com.gmail.thelimeglass.Scoreboards.EffTeamRemoveEntry;
import com.gmail.thelimeglass.Scoreboards.EffUnregisterObjective;
import com.gmail.thelimeglass.Scoreboards.EffUnregisterTeam;
import com.gmail.thelimeglass.Scoreboards.ExprEntries;
import com.gmail.thelimeglass.Scoreboards.ExprGetEntryScores;
import com.gmail.thelimeglass.Scoreboards.ExprGetEntryTeam;
import com.gmail.thelimeglass.Scoreboards.ExprGetObjective;
import com.gmail.thelimeglass.Scoreboards.ExprGetScoreboard;
import com.gmail.thelimeglass.Scoreboards.ExprGetTeam;
import com.gmail.thelimeglass.Scoreboards.ExprNewScoreboard;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveCriteria;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveDisplayName;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveDisplaySlot;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveGetScore;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveName;
import com.gmail.thelimeglass.Scoreboards.ExprObjectives;
import com.gmail.thelimeglass.Scoreboards.ExprObjectivesByCriteria;
import com.gmail.thelimeglass.Scoreboards.ExprPlayerScoreboard;
import com.gmail.thelimeglass.Scoreboards.ExprScore;
import com.gmail.thelimeglass.Scoreboards.ExprScoreEntry;
import com.gmail.thelimeglass.Scoreboards.ExprScoreObjective;
import com.gmail.thelimeglass.Scoreboards.ExprTeamDisplayName;
import com.gmail.thelimeglass.Scoreboards.ExprTeamEntries;
import com.gmail.thelimeglass.Scoreboards.ExprTeamFriendlyFire;
import com.gmail.thelimeglass.Scoreboards.ExprTeamFriendlyInvisibles;
import com.gmail.thelimeglass.Scoreboards.ExprTeamName;
import com.gmail.thelimeglass.Scoreboards.ExprTeamOptions;
import com.gmail.thelimeglass.Scoreboards.ExprTeamPrefix;
import com.gmail.thelimeglass.Scoreboards.ExprTeamSize;
import com.gmail.thelimeglass.Scoreboards.ExprTeamSuffix;
import com.gmail.thelimeglass.Scoreboards.ExprTeams;
import com.gmail.thelimeglass.Stylishboards.StyleManager;
import com.gmail.thelimeglass.Utils.EnumClassInfo;
import com.gmail.thelimeglass.Utils.PvpListener;
import com.gmail.thelimeglass.Utils.TypeClassInfo;
import com.gmail.thelimeglass.Utils.Annotations.AntiDependency;
import com.gmail.thelimeglass.Utils.Annotations.AntiDependencyEnum;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Dependency;
import com.gmail.thelimeglass.Utils.Annotations.Disabled;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.RegisterEnum;
import com.gmail.thelimeglass.Utils.Annotations.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.gmail.thelimeglass.Utils.Annotations.Version;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Timespan;

public class Skellett extends JavaPlugin {

	public static final HashMap<String, Scoreboard> skellettBoards = new HashMap<>();
	private static Skellett instance;
	public static String prefix = "&8[&aSkellett&8] &e";
	public FileConfiguration config = getConfig();
	private File ceFile;
	public static FileConfiguration ceData;
	private File syntaxFile;
	private static FileConfiguration syntaxData;
	public static File syntaxToggleFile;
	public static FileConfiguration syntaxToggleData;

	public void onEnable() {
		instance = this;
		if (!getDescription().getVersion().equalsIgnoreCase(config.getString("version", getDescription().getVersion()))) {
			Bukkit.getConsoleSender().sendMessage(cc(prefix + "&6New update found! Updating files..."));
			//saveConfig = getConfig().getBoolean("SaveConfig", false);
			File configFile = new File(getDataFolder(), "config.yml");
			if (configFile.exists()) {
				configFile.delete();
			}
			File syntaxToggles = new File(getDataFolder(), "SyntaxToggles.yml");
			if (syntaxToggles.exists()) {
				syntaxToggles.delete();
			}
			saveDefaultConfig();
		}
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdirs();
			}
			File file = new File(getDataFolder(), "config.yml");
			ceFile = new File(getDataFolder(), "CustomEvents.yml");
			syntaxFile = new File(getDataFolder(), "Syntax.yml");
			syntaxToggleFile = new File(getDataFolder(), "SyntaxToggles.yml");
			if (!file.exists()) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cconfig.yml not found, generating a new config!"));
				saveDefaultConfig();
			}
			if (!ceFile.exists()) {
				ceFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cCustomEvents.yml not found, generating a new config!"));
				saveResource("CustomEvents.yml", false);
			}
			ceData = new YamlConfiguration();
			try {
				ceData.load(ceFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
			if (!syntaxFile.exists()) {
				syntaxFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cSyntax.yml not found, generating a new file!"));
				saveResource("Syntax.yml", false);
			}
			syntaxData = new YamlConfiguration();
			try {
				syntaxData.load(syntaxFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
			if (!syntaxToggleFile.exists()) {
				syntaxToggleFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cSyntaxToggles.yml not found, generating a new file!"));
				saveResource("SyntaxToggles.yml", false);
			}
			syntaxToggleData = new YamlConfiguration();
			try {
				syntaxToggleData.load(syntaxToggleFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
		} catch (Exception error) {
			error.printStackTrace();
		}
		if (config.getBoolean("Enable1_8pvp")) getServer().getPluginManager().registerEvents(new PvpListener(), this);
		try {
			Skript.registerAddon(this)
				.setLanguageFileDirectory("lang")
				.loadClasses("me.limeglass.skellett", "elements");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Register.events();
		Register.types();
		if (syntaxToggleData.getBoolean("Syntax.Conditions.ClientTime")) {
			Skript.registerCondition(CondClientTimeRelative.class, "[skellett] [client] relative time of %player% [is] [%-boolean%] [relative] [to] [server]");
		}
		if (syntaxToggleData.getBoolean("Main.Scoreboards")) {
			Skript.registerExpression(ExprGetScoreboard.class, Scoreboard.class, ExpressionType.SIMPLE, "[get] (score[ ][board]|[skellett[ ]]board)) [(with|named)] [(name|id)] %string%");
			Skript.registerExpression(ExprNewScoreboard.class, Scoreboard.class, ExpressionType.SIMPLE, "[create] [a] new (score[ ][board]|[skellett[ ]]board) [(with|named)] [(name|id)] %string%");
			Skript.registerExpression(ExprPlayerScoreboard.class, Scoreboard.class, ExpressionType.SIMPLE, "(score[ ][board]|[skellett[ ]]board) of [player] %player%", "%player%'s (score[ ][board]|[skellett[ ]]board)");
			Skript.registerEffect(EffDeleteScoreboard.class, "(delete|clear|remove) (score[ ][board]|[skellett[ ]]board)) [(with|named)] [(name|id)] %string%");
			Skript.registerExpression(ExprGetObjective.class, Objective.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprObjectiveCriteria.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective criteria [of] %objective%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%'s objective criteria");
			Skript.registerEffect(EffRegisterObjective.class, "register [new] (score[ ][board]|[skellett[ ]]board) objective %string% with [criteria] %string% [[(in|from)] %-scoreboard%]", "register [new] objective %string% with [criteria] %string% [(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]");
			Skript.registerCondition(CondObjectiveExists.class, "objective %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]] (1¦(is set|[does] exist[s])|2¦(is(n't| not) set|does(n't| not) exist[s]))");
			Skript.registerExpression(ExprObjectives.class, Objective.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] [(score[ ][board]|board)[[']s]] objectives [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprObjectivesByCriteria.class, Objective.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] (score[ ][board]|board)[[']s] objectives (by|with) [criteria] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerEffect(EffUnregisterObjective.class, "unregister (score[ ][board]|[skellett[ ]]board) objective %objective%");
			Skript.registerExpression(ExprObjectiveDisplayName.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective display name [(for|from|of)] %objective%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%['s] objective['s] display name", "[the] (score[ ][board]|[skellett[ ]]board) objective %objective%['s] display name");
			Skript.registerExpression(ExprObjectiveName.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective name [(for|from|of)] %objective%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%['s] objective['s] name", "[the] (score[ ][board]|[skellett[ ]]board) objective %objective%['s] name");
			Skript.registerExpression(ExprObjectiveDisplaySlot.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective [display] slot [(for|from|of)] %objective%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%['s] objective['s] [display] slot", "[the] (score[ ][board]|[skellett[ ]]board) objective %objective%['s] [display] slot");
			Skript.registerCondition(CondObjectiveIsModifiable.class, "[the] (score[ ][board]|[skellett[ ]]board) objective %objective% (1¦is modifiable|2¦is(n't| not) modifiable)");
			Skript.registerExpression(ExprObjectiveGetScore.class, Score.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) [objective] %objective%['s] score [(for|from|of)] [entry] %string%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%['s] [objective['s]] score [(for|from|of)] [entry] %string%");
			Skript.registerEffect(EffScoreboardClearSlot.class, "clear (score[ ][board]|board) [display] slot %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprEntries.class, String.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] (score[ ][board]|board)[[']s] entr(ies|y[[']s]) [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprGetEntryTeam.class, Team.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|board) team of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "[the] (score[ ][board]|board) [entry] %string%'s team [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprGetEntryScores.class, Score.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) scores of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "[(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) [entry] %string%'s scores [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerEffect(EffResetEntryScores.class, "reset [(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) scores of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "reset [(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) [entry] %string%'s scores [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "(score[ ][board]|[skellett[ ]]board) reset [(the|all)] [of] [the] scores of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerEffect(EffRegisterTeam.class, "register [a] [new] (score[ ][board]|[skellett[ ]]board) team %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprTeams.class, Team.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] teams [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprGetTeam.class, Team.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) %string% team [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "(score[ ][board]|[skellett[ ]]board) [get] team %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprScoreEntry.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) [get] entry [(for|from|of)] score %score%", "(score[ ][board]|[skellett[ ]]board) %score%'s score entry");
			Skript.registerExpression(ExprScoreObjective.class, Objective.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective [(for|from|of)] score %score%", "[the] (score[ ][board]|[skellett[ ]]board) %score%'s scores objective");
			Skript.registerExpression(ExprScore.class, Number.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) (score|number|slot) [(for|from|of)] %score%", "(score[ ][board]|[skellett[ ]]board) %score%'s (score|number|slot)");
			Skript.registerEffect(EffTeamAddEntry.class, "(score[ ][board]|[skellett[ ]]board) add [the] entry [(from|of)] %string% to [the] [team] %team%");
			Skript.registerExpression(ExprTeamFriendlyFire.class, Boolean.class, ExpressionType.SIMPLE, "[the] [(score[ ][board]|[skellett[ ]]board)] friendly [fire] state [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamFriendlyInvisibles.class, Boolean.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) [friendly] invisible[s] [state] [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamDisplayName.class, String.class, ExpressionType.SIMPLE, "[the] [(score[ ][board]|[skellett[ ]]board)] team display name [(for|from|of)] %team%");
			Skript.registerExpression(ExprTeamEntries.class, String.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board)[[']s] entr(ies|y[[']s]) (in|within) [the] [team] %team%");
			Skript.registerExpression(ExprTeamName.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board)) [team] name [(for|of)] [team] %team%");
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				Skript.registerExpression(ExprTeamOptions.class, Team.OptionStatus.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) [team] option[s] [status] %teamoption% [(for|of)] [the] [team] %team%");
			}
			Skript.registerExpression(ExprTeamPrefix.class, String.class, ExpressionType.SIMPLE, "[(score[ ][board]|[skellett[ ]]board)] [team] prefix [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamSuffix.class, String.class, ExpressionType.SIMPLE, "[(score[ ][board]|[skellett[ ]]board)] [team] suffix [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamSize.class, Number.class, ExpressionType.SIMPLE, "[(score[ ][board]|[skellett[ ]]board)] team size [(for|of)] [team] %team%");
			Skript.registerCondition(CondTeamHasEntry.class, "[the] (score[ ][board]|[skellett[ ]]board) (1¦(ha(s|ve)|contain[s])|2¦(do[es](n't| not) have| do[es](n't| not) contain)) [the] [entry] %string% [(in|within)] the [team] %team%");
			Skript.registerEffect(EffTeamRemoveEntry.class, "[(score[ ][board]|[skellett[ ]]board)] remove [the] entry [(from|of)] %string% from [the] [team] %team%");
			Skript.registerEffect(EffUnregisterTeam.class, "unregister [the] (score[ ][board]|[skellett[ ]]board) team %team%");
		}
		if (ceData.getBoolean("CustomEvents")) {
			for(int i = 1; i <= ceData.getInt("CustomEventSetup.NumberOfEvents"); i++) {
				try {
					@SuppressWarnings("unchecked")
					Class<? extends Event> classType = ((Class<? extends Event>) Class.forName(ceData.getString("CustomEventSetup." + i + ".Event")));
					Skript.registerEvent(ceData.getString("CustomEventSetup." + i + ".Syntax"), SimpleEvent.class, classType, "[skellett] " + ceData.getString("CustomEventSetup." + i + ".Syntax"));
					Register.addEvent(classType);
					Bukkit.getConsoleSender().sendMessage(cc("&aRegistered custom event: &5" + ceData.getString("CustomEventSetup." + i + ".Syntax")));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
		Skript.registerEvent("[on] entity sho[o]t:", SimpleEvent.class, EntityShootBowEvent.class, "[on] entity sho[o]t");
		Register.metrics(new Metrics(this));
		if (config.getBoolean("debug")) {
			Bukkit.getConsoleSender().sendMessage(cc(prefix + "&aMetrics registered!"));
		}
		try {
			register();
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException | InstantiationException e2) {
			e2.printStackTrace();
		}
	}

	public static Skellett getInstance() {
		return instance;
	}

	public static String cc(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	@SuppressWarnings("deprecation")
	public static int getTicks(Timespan time) {
		if (Skript.methodExists(Timespan.class, "getTicks_i")) {
			Number tick = time.getTicks_i();
			return tick.intValue();
		} else {
			return time.getTicks();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void register() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		
		int effN = 0;
		int condN = 0;
		int exprN = 0;
		
		Set<Class<?>> classes = new HashSet<>();		
		Method method = JavaPlugin.class.getDeclaredMethod("getFile");
		
		method.setAccessible(true);
		File file = (File) method.invoke(this);
		try {
			JarFile jar = new JarFile(file);
			for (Enumeration<JarEntry> entry = jar.entries(); entry.hasMoreElements();) {
				String name = entry.nextElement().getName().replace("/", ".");
				if (name.startsWith("com.gmail.thelimeglass") && name.endsWith(".class")) {
					try {
						classes.add(Class.forName(name.substring(0, name.length() - 6)));
					} catch (ClassNotFoundException error) {
						error.printStackTrace();
					} catch (NoClassDefFoundError | ExceptionInInitializerError e) {}
				}
			}
			jar.close();
		} catch(Exception error) {
			error.printStackTrace();
		}
		if (classes != null) {
			run: for (Class c : classes) {
				String statement = "Effects";
				if (Condition.class.isAssignableFrom(c)) {
					statement = "Conditions";
				} else if (Expression.class.isAssignableFrom(c)) {
					statement = "Expressions";
				}
				for (Annotation a : c.getAnnotations()) {
					if (a instanceof Disabled) {
						continue run;
					}
					if (a instanceof Dependency) {
						List<String> plugins = new ArrayList<String>(Arrays.asList(((Dependency) c.getAnnotation(Dependency.class)).value()));
						for (String pl : plugins) {
							if (Bukkit.getPluginManager().getPlugin(pl) == null) {
								continue run;
							}
						}
					}
					if (a instanceof AntiDependency) {
						List<String> plugins = new ArrayList<String>(Arrays.asList(((AntiDependency) c.getAnnotation(AntiDependency.class)).value()));
						for (String pl : plugins) {
							if (Bukkit.getPluginManager().getPlugin(pl) != null) {
								continue run;
							}
						}
					}
					if (a instanceof Version) {
						String[] versions = {"1.8", "1.8R3", "1.9", "1.9R1", "1.10" , "1.11", "1.11.2", "1.12", "1.13", "1.14", "1.14.4", "1.15", "1.16", "1.17", "1.18"};
						Integer server = null;
						Integer serverTag = null;
						for (int i = 0; i < versions.length; i++) {
							if (versions[i].contains("R")) {
								String[] subVersion = versions[i].split("R");
								if (Bukkit.getVersion().contains(subVersion[0]) && Bukkit.getVersion().contains("R0." + subVersion[1])) {
									server = i;
								}
							} else if (Bukkit.getVersion().contains(versions[i])) {
								server = i;
							}
							if (versions[i].equals(((Version) c.getAnnotation(Version.class)).value())) {
								serverTag = i;
							}
						}
						if (serverTag == null || server == null || serverTag > server) {
							if (!config.getBoolean("DisableCompatableWarning") && c.isAnnotationPresent(Config.class)) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + ((Config) c.getAnnotation(Config.class)).value() + " syntax is for " + versions[serverTag] + "+ spigot versions!"));
							}
							continue run;
						}
					}
					if (a instanceof Config) {
						FileConfiguration data = syntaxToggleData;
						String node = ((Config) c.getAnnotation(Config.class)).value();
						if (c.isAnnotationPresent(MainConfig.class)) {
							data = config;
						}
						if (!c.isAnnotationPresent(FullConfig.class)) {
							node = "Syntax." + statement + "." + node;
						}
						if (data.getBoolean(node) == false) {
							if (config.getBoolean("NotRegisteredSyntax", false)) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + node.toString() + " didn't register!"));
							}
							continue run;
						}
					}
					if (a instanceof RegisterSimpleEnum) {
						Class clazz = ((RegisterSimpleEnum) c.getAnnotation(RegisterSimpleEnum.class)).ExprClass();
						String type = ((RegisterSimpleEnum) c.getAnnotation(RegisterSimpleEnum.class)).value();
						TypeClassInfo.create(clazz, type).register();
						if (config.getBoolean("debug")) {
							Bukkit.getConsoleSender().sendMessage(cc(prefix + "&eRegistered simple type " + type));
						}
					} else if (a instanceof RegisterEnum) {
						Boolean register = true;
						if (c.isAnnotationPresent(AntiDependencyEnum.class)) {
							List<String> plugins = new ArrayList<String>(Arrays.asList(((AntiDependencyEnum) c.getAnnotation(AntiDependencyEnum.class)).value()));
							for (String pl : plugins) {
								if (Bukkit.getPluginManager().getPlugin(pl) != null) {
									register = false;
								}
							}
						}
						if (register) {
							Class clazz = ((RegisterEnum) c.getAnnotation(RegisterEnum.class)).ExprClass();
							String type = ((RegisterEnum) c.getAnnotation(RegisterEnum.class)).value();
							if (clazz.equals(String.class)) {
								if (Classes.getExactClassInfo(((Expression) c.getDeclaredConstructor().newInstance()).getReturnType()) == null) {
									EnumClassInfo.create(((Expression) c.getDeclaredConstructor().newInstance()).getReturnType(), type).register();
								}
							} else {
								EnumClassInfo.create(clazz, type).register();
							}
							if (config.getBoolean("debug")) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&eRegistered type " + type));
							}
						}
					}
				}
				if (c.isAnnotationPresent(Syntax.class)) {
					String node = null;
					if (c.isAnnotationPresent(Config.class)) {
						node = ((Config) c.getAnnotation(Config.class)).value();
					}
					String[] syntax = ((Syntax) c.getAnnotation(Syntax.class)).value();
					if (node != null) {
						if (syntaxData.get(node) == null) {
							List<String> list = new ArrayList<String>(Arrays.asList(syntax));
							if (list.size() <= 1) {
								list.add(null);
							}
							syntaxData.set(node, list);
						} if (syntaxData.get(node) != null) {
							if (syntaxData.getList(node) == null) {
								syntaxData.getList(node).add(null);
							}
							List<Object> list = (List<Object>) syntaxData.getList(node);
							for (String s : syntax) {
								if (!syntaxData.getList(node).contains(s)) {
									list.add(s);
								}
								list.remove(null);
							}
						}
						try {
							syntaxData.save(syntaxFile);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					FileConfiguration data = syntaxToggleData;
					if (c.isAnnotationPresent(MainConfig.class)) {
						data = config;
					}
					if (!c.isAnnotationPresent(FullConfig.class)) {
						if (node != null) {
							node = "Syntax." + statement + "." + node;
						}
					}
					if (ch.njol.skript.lang.Effect.class.isAssignableFrom(c)) {
						if (config.getBoolean("debug")) {
							if (node.equals(config.getString("syntaxDebug"))) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cRegistering effect " + node));
							} else {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&5Registering effect " + node));
							}
						}
						if (node != null) {
							if (data.getBoolean(node)) {
								Skript.registerEffect(c, syntax);
								effN++;
							}
						} else {
							Skript.registerEffect(c, syntax);
							effN++;
							/*if (syntaxData.getList(node) != null) {
								strings.addAll((Collection<? extends String>) syntaxData.get(node));
								Skript.registerEffect(c, strings.toArray(new String[strings.size()]));
							} else {
								Skript.registerEffect(c, syntaxData.getString(node));
							}*/
						}
					} else if (Condition.class.isAssignableFrom(c)) {
						if (config.getBoolean("debug")) {
							if (node.equals(config.getString("syntaxDebug"))) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cRegistering condition " + node));
							} else {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&5Registering condition " + node));
							}
						}
						if (node != null) {
							if (data.getBoolean(node)) {
								Skript.registerCondition(c, syntax);
								condN++;
							}
						} else {
							Skript.registerCondition(c, syntax);
							condN++;
						}
					} else if (Expression.class.isAssignableFrom(c)) {
						if (config.getBoolean("debug")) {
							if (node.equals(config.getString("syntaxDebug"))) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cRegistering expression " + node));
							} else {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&5Registering expression " + node));
							}
						}
						if (c.isAnnotationPresent(PropertyType.class)) {
							if (node != null) {
								if (data.getBoolean(node)) {
									try {
										Skript.registerExpression(c, ((Expression) c.getDeclaredConstructor().newInstance()).getReturnType(), ((PropertyType) c.getAnnotation(PropertyType.class)).value(), syntax);
										exprN++;
									} catch (IllegalAccessException e) {
										Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cFailed to register expression " + c.getCanonicalName()));
										e.printStackTrace();
									} catch (InstantiationException e) {
										Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cFailed to register expression " + c.getCanonicalName()));
										e.printStackTrace();
									}
								}
							} else {
								try {
									Skript.registerExpression(c, ((Expression) c.getDeclaredConstructor().newInstance()).getReturnType(), ((PropertyType) c.getAnnotation(PropertyType.class)).value(), syntax);
									exprN++;
								} catch (IllegalAccessException e) {
									Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cFailed to register expression " + c.getCanonicalName()));
									e.printStackTrace();
								} catch (InstantiationException e) {
									Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cFailed to register expression " + c.getCanonicalName()));
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		if (!config.getBoolean("DisableRegisteredInfo", false)) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "Registered &a" + effN + " &eEffects, &a" + condN + "&e Conditions, &a" + exprN + "&e Expressions and &a" + Register.getClasses().size() + "&e Events"));
		}
		//MathhulkDocs.sendDoc("Clicked item", "[the] [skellett] [click[ed]] item", "Grabs the clicked item in the inventory clicking event", "expression", null, "on inventory click:\\n    broadcast \"%clicked item%\"");
	}
	public void onDisable(){
		StyleManager.dump();
		SkellettMapRenderer.dump();
		Register.dump();
	}

}
