package io.github.lummertzjoao.homes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.lummertzjoao.homes.command.HomesCommand;
import io.github.lummertzjoao.homes.domain.Home;
import io.github.lummertzjoao.homes.listener.MenuListener;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;

public class Main extends JavaPlugin {

	private int homesLimit;
	
	private ConversationFactory conversationFactory;

	private File homesDataFile;
	private FileConfiguration homesDataConfig;
	
	private final Map<Player, List<Home>> homes = new HashMap<>();
	private final Map<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

	@Override
	public void onEnable() {
		getLogger().info("Loading homes from 'data.yml' file...");
		setupHomesDataFile();
		loadHomes();
		
		conversationFactory = new ConversationFactory(this);
		getCommand("homes").setExecutor(new HomesCommand(this));
		getServer().getPluginManager().registerEvents(new MenuListener(), this);
		
		getLogger().info("The plugin has been enabled successfully!");
	}

	@Override
	public void onDisable() {
		getLogger().info("Saving homes to 'data.yml' file...");
		saveHomes();
		getLogger().info("The plugin has been disabled successfully!");
	}
	
	private void setupHomesDataFile() {
		homesDataFile = new File(getDataFolder(), "data.yml");
		if (!homesDataFile.exists()) {
			homesDataFile.getParentFile().mkdirs();
			saveResource("data.yml", false);
		}
		
		homesDataConfig = new YamlConfiguration();
		try {
			homesDataConfig.load(homesDataFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	private void saveHomes() {
		for (Player player : homes.keySet()) {
			List<Home> playerHomes = getPlayerHomes(player);
			for (Home home : playerHomes) {
				String uuid = player.getUniqueId().toString();
				String homeName = home.getName();
				homesDataConfig.set("homes." + uuid + "." + homeName + ".location", home.getLocation());
				homesDataConfig.set("homes." + uuid + "." + homeName + ".icon", home.getIcon().toString());
			}
		}
		try {
			homesDataConfig.save(homesDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadHomes() {
		for (String uuid : homesDataConfig.getConfigurationSection("homes").getKeys(false)) {
			Player player = Bukkit.getPlayer(UUID.fromString(uuid));
			List<Home> playerHomes = getPlayerHomes(player);
			for (String name : homesDataConfig.getConfigurationSection("homes." + uuid).getKeys(false)) {
				Location location = homesDataConfig.getLocation("homes." + uuid + "." + name + ".location");
				Material icon = Material.valueOf(homesDataConfig.getString("homes." + uuid + "." + name + ".icon"));
				playerHomes.add(new Home(name, location, player, icon));
			}
		}
	}
	
	public List<Home> getPlayerHomes(Player player) {
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

	public ConversationFactory getConversationFactory() {
		return conversationFactory;
	}
	
	public Map<Player, List<Home>> getHomes() {
		return homes;
	}
	
	public FileConfiguration getHomesDataConfig() {
		return homesDataConfig;
	}

	public int getHomesLimit() {
		return homesLimit;
	}

	public void setHomesLimit(int homesLimit) {
		this.homesLimit = homesLimit;
	}
}
