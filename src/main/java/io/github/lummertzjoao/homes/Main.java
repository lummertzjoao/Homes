package io.github.lummertzjoao.homes;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("The plugin has been enabled successfully!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("The plugin has been disabled successfully!");
	}
}
