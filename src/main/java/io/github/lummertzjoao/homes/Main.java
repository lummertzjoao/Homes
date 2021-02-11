package io.github.lummertzjoao.homes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.lummertzjoao.homes.command.HomesCommand;
import io.github.lummertzjoao.homes.domain.Home;
import io.github.lummertzjoao.homes.listener.MenuListener;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;

public class Main extends JavaPlugin {

	private ConversationFactory conversationFactory;;
	
	private final Map<Player, List<Home>> homes = new HashMap<>();
	private final Map<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

	@Override
	public void onEnable() {
		conversationFactory = new ConversationFactory(this);
		getCommand("homes").setExecutor(new HomesCommand(this));
		getServer().getPluginManager().registerEvents(new MenuListener(), this);
		getLogger().info("The plugin has been enabled successfully!");
	}

	@Override
	public void onDisable() {
		getLogger().info("The plugin has been disabled successfully!");
	}

	public ConversationFactory getConversationFactory() {
		return conversationFactory;
	}
	
	public Map<Player, List<Home>> getHomes() {
		return homes;
	}

	public List<Home> getPlayerHomesList(Player player) {
		List<Home> playerHomes = homes.get(player);
		if (playerHomes == null) {
			playerHomes = new ArrayList<>();
			homes.put(player, playerHomes);
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
