package io.github.lummertzjoao.homes;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.lummertzjoao.homes.command.HomesCommand;
import io.github.lummertzjoao.homes.listener.MenuListener;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.model.dao.DaoFactory;
import io.github.lummertzjoao.homes.model.dao.HomeDao;

public class Main extends JavaPlugin {

	private HomeDao homeDao;
	
	private ConversationFactory conversationFactory;
	
	private final Map<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

	@Override
	public void onEnable() {
		homeDao = DaoFactory.createHomeDao(this);
		homeDao.setup();
		saveDefaultConfig();

		conversationFactory = new ConversationFactory(this);
		getCommand("homes").setExecutor(new HomesCommand(this));
		getServer().getPluginManager().registerEvents(new MenuListener(), this);
		
		getLogger().info("The plugin has been enabled successfully!");
	}

	@Override
	public void onDisable() {
		homeDao.save();
		saveConfig();
		
		getLogger().info("The plugin has been disabled successfully!");
	}
	
	public HomeDao getHomeDao() {
		return homeDao;
	}
	
	public ConversationFactory getConversationFactory() {
		return conversationFactory;
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
	
	public int getHomesLimit() {
		return getConfig().getInt("homesLimit");
	}
	
	public void setHomesLimit(int limit) {
		getConfig().set("homesLimit", limit);
		saveConfig();
	}
	
	public int getHomesMenuSize() {
		int size = getConfig().getInt("homesMenuSize");
		if (size % 9 == 0 && size >= 27 && size <= 54) {
			return size;
		} else {
			throw new IllegalStateException("The homes menu size needs to be 27, 36, 45 or 54");
		}
	}
	
	public void setHomesMenuSize(int size) {
		getConfig().set("homesMenuSize", size);
		saveConfig();
	}
}
