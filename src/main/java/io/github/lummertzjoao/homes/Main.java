package io.github.lummertzjoao.homes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.lummertzjoao.homes.command.HomesCommand;
import io.github.lummertzjoao.homes.domain.Home;
import io.github.lummertzjoao.homes.listener.MenuListener;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;

public class Main extends JavaPlugin {

	public static final String INFO_MESSAGE_PREFIX = ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "!"
			+ ChatColor.DARK_GREEN + "]" + ChatColor.GREEN + " ";
	public static final String ERROR_MESSAGE_PREFIX = ChatColor.DARK_RED + "[" + ChatColor.RED + "!"
			+ ChatColor.DARK_RED + "]" + ChatColor.RED + " ";

	private static final List<Material> icons = List.of(Material.WHITE_BED, Material.ORANGE_BED, Material.MAGENTA_BED,
			Material.LIGHT_BLUE_BED, Material.YELLOW_BED, Material.LIME_BED, Material.PINK_BED, Material.GRAY_BED,
			Material.LIGHT_GRAY_BED, Material.CYAN_BED, Material.PURPLE_BED, Material.BLUE_BED, Material.BROWN_BED,
			Material.GREEN_BED, Material.RED_BED, Material.RED_BED, Material.BLACK_BED);

	private final Map<Player, List<Home>> homes = new HashMap<>();
	private final Map<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

	@Override
	public void onEnable() {
		getCommand("homes").setExecutor(new HomesCommand(this));
		getServer().getPluginManager().registerEvents(new MenuListener(), this);
		getLogger().info("The plugin has been enabled successfully!");
	}

	@Override
	public void onDisable() {
		getLogger().info("The plugin has been disabled successfully!");
	}

	public Map<Player, List<Home>> getHomes() {
		return homes;
	}

	public List<Home> getPlayerHomesList(Player player) {
		List<Home> playerHomes = homes.get(player);
		if (playerHomes == null) {
			playerHomes = new ArrayList<>();
		}
		return playerHomes;
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
