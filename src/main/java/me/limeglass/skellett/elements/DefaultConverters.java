package me.limeglass.skellett.elements;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.objects.BlockSave;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.registrations.Converters;

public class DefaultConverters {

	static {
		Converters.registerConverter(BlockSave.class, BlockData.class, new Converter<BlockSave, BlockData>() {
			@Override
			@Nullable
			public BlockData convert(BlockSave save) {
				return save.getBlockData();
			}
		});
		Converters.registerConverter(BlockSave.class, Location.class, new Converter<BlockSave, Location>() {
			@Override
			@Nullable
			public Location convert(BlockSave save) {
				return save.getLocation();
			}
		});
	}

}
