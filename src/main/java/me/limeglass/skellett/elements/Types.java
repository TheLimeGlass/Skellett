package me.limeglass.skellett.elements;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

import com.gmail.thelimeglass.objects.BlockSave;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;

public class Types {

	static {
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
	
					@Override
					public String getVariableNamePattern() {
						return ".+";
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
	}

}
