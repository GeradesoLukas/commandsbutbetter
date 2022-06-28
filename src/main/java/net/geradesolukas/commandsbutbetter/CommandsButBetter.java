package net.geradesolukas.commandsbutbetter;

import net.fabricmc.api.ModInitializer;
import net.geradesolukas.commandsbutbetter.util.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandsButBetter implements ModInitializer {
	public static final String MODID = "commandsbutbetter";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		ModRegistries.registerModStuffs();
	}
}
