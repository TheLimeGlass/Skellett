package me.limeglass.skellett.elements;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarFlag;
import org.bukkit.entity.FishHook;
import org.bukkit.event.player.PlayerFishEvent;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.EnumUtils;
import ch.njol.yggdrasil.Fields;
import me.limeglass.skellett.objects.BlockSave;

public class Types {

	static {
		Classes.registerClass(new ClassInfo<>(FishHook.class, "fishinghook")
				.user("fish(ing)? ?hooks")
				.name("Fishing Hook")
				.description("Represents the fishing hook in a <a href='events.html#fishing'>fishing</a> event.")
				.defaultExpression(new EventValueExpression<>(FishHook.class))
				.since("INSERT VERSION")
				.parser(new Parser<FishHook>() {
					@Override
					public boolean canParse(ParseContext context) {
						return false;
					}

					@Override
					public String toString(FishHook o, int flags) {
						return "Fish hook " + o.toString();
					}

					@Override
					public String toVariableNameString(FishHook o) {
						return "Fish hook " + o.toString();
					}
				}));
		@SuppressWarnings("unchecked")
		Parser<Location> locationParser = (Parser<Location>) Classes.getExactClassInfo(Location.class).getParser();
		Classes.registerClass(new ClassInfo<BlockSave>(BlockSave.class, "blocksave")
				.user("blocksaves?")
				.name("BlockSave")
				.defaultExpression(new EventValueExpression<>(BlockSave.class))
				.parser(new Parser<BlockSave>() {
	
					@Override
					public boolean canParse(ParseContext context) {
						return false;
					}
	
					@Override
					public String toString(BlockSave save, int flags) {
						String location = locationParser.toString(save.getLocation(), flags);
						return "Location: " + location + " BlockData: " + save.getBlockData().getAsString();
					}
	
					@Override
					public String toVariableNameString(BlockSave save) {
						return toString(save, 0);
					}
		}).serializer(new Serializer<BlockSave>() {

			@Override
			protected boolean canBeInstantiated() {
				return false;
			}

			@Override
			public BlockSave deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
				Location location = fields.getAndRemoveObject("location", Location.class);
				BlockData data = Bukkit.createBlockData(fields.getAndRemoveObject("data", String.class));
				return new BlockSave(location, data);
			}

			@Override
			public boolean mustSyncDeserialization() {
				return false;
			}

			@Override
			public Fields serialize(BlockSave save) throws NotSerializableException {
				Fields fields = new Fields();
				fields.putObject("data", save.getBlockData().getAsString());
				fields.putObject("location", save.getLocation());
				return fields;
			}

			@Override
			public void deserialize(BlockSave save, Fields fields) throws StreamCorruptedException, NotSerializableException {
				assert false;
			}

		}));
		if (Classes.getExactClassInfo(PlayerFishEvent.State.class) == null) {
			EnumUtils<PlayerFishEvent.State> fishStateUtils = new EnumUtils<>(PlayerFishEvent.State.class, "fishingstate");
			Classes.registerClass(new ClassInfo<>(PlayerFishEvent.State.class, "fishingstate")
					.user("fish(ing)? ?states?")
					.name("Fishing State")
					.description("Represents the fishing state in a fishing event.")
					.usage(fishStateUtils.getAllNames())
					.parser(new Parser<PlayerFishEvent.State>() {
						@Override
						public PlayerFishEvent.State parse(String s, ParseContext context) {
							return fishStateUtils.parse(s);
						}
	
						@Override
						public String toString(PlayerFishEvent.State o, int flags) {
							return fishStateUtils.toString(o, flags);
						}
	
						@Override
						public String toVariableNameString(PlayerFishEvent.State o) {
							return o.name();
						}
					})
					.serializer(new EnumSerializer<>(PlayerFishEvent.State.class)));
		}
		if (Classes.getExactClassInfo(BarFlag.class) == null) {
			EnumUtils<BarFlag> flags = new EnumUtils<>(BarFlag.class, "bossbarflag");
			Classes.registerClass(new ClassInfo<>(BarFlag.class, "bossbarflag")
					.user("(boss ?)?bar ?flags?")
					.name("BossBar Flags")
					.description("The flags of a bossbar.")
					.usage(flags.getAllNames())
					.parser(new Parser<BarFlag>() {
						@Override
						public BarFlag parse(String s, ParseContext context) {
							return flags.parse(s);
						}
	
						@Override
						public String toString(BarFlag o, int f) {
							return flags.toString(o, f);
						}
	
						@Override
						public String toVariableNameString(BarFlag o) {
							return o.name();
						}
					})
					.serializer(new EnumSerializer<>(BarFlag.class)));
		}
	}

}
