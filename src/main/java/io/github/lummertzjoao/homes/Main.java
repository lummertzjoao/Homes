package io.github.lummertzjoao.homes;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;

public class Main extends JavaPlugin {

	private final Map<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
	
	@Override
	public void onEnable() {
		getLogger().info("The plugin has been enabled successfully!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("The plugin has been disabled successfully!");
	}
	
	public PlayerMenuUtility getPlayerMenuUtility(Player player) {
		PlayerMenuUtility playerMenuUtility;
		if (!(playerMenuUtilityMap.containsKey(player))) {
			playerMenuUtility = new PlayerMenuUtility(player);
			playerMenuUtilityMap.put(player, playerMenuUtility);
			return playerMenuUtility;
		} else {
			return playerMenuUtilityMap.get(player);
		}
	}
}
