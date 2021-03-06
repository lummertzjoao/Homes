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
		this.setHomeDao(DaoFactory.createHomeDao(this));
		this.loadConfig();
		
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
	
	private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public HomeDao getHomeDao() {
		return homeDao;
	}
	
	public void setHomeDao(HomeDao homeDao) {
		homeDao.setup();
		this.homeDao = homeDao;
	}
	
	public ConversationFactory getConversationFactory() {
		return conversationFactory;
	}

	public PlayerMenuUtility getPlayerMenuUtility(Player player) {
		if (!(playerMenuUtilityMap.containsKey(player))) {
			PlayerMenuUtility playerMenuUtility = new PlayerMenuUtility(player);
			playerMenuUtilityMap.put(player, playerMenuUtility);
			return playerMenuUtility;
		} else {
			return playerMenuUtilityMap.get(player);
		}
	}
	
	public int getHomesLimit() {
		return getConfig().getInt("homes-limit");
	}
	
	public void setHomesLimit(int limit) {
		getConfig().set("homes-limit", limit);
		saveConfig();
	}
	
	public int getHomesMenuSize() {
		int size = getConfig().getInt("homes-menu-size");
		if (size % 9 == 0 && size >= 27 && size <= 54) {
			return size;
		} else {
			throw new IllegalStateException("The homes menu size must be 27, 36, 45 or 54");
		}
	}
	
	public void setHomesMenuSize(int size) {
		getConfig().set("homes-menu-size", size);
		saveConfig();
	}
	
	public boolean isDelayToTeleportEnabled() {
		return getConfig().getBoolean("delay-to-teleport.enabled");
	}
	
	public int getDelayToTeleport() {
		int seconds = getConfig().getInt("delay-to-teleport.seconds");
		if (seconds > 0) {
			return seconds;
		} else {
			throw new IllegalStateException("The seconds to teleport must be greater than 0");
		}
	}
	
	public void setDelayToTeleport(int seconds) {
		getConfig().set("delay-to-teleport.seconds", seconds);
		saveConfig();
	}
	
	public boolean isCachingEnabled() {
		return getConfig().getBoolean("yaml.caching");
	}
}
