package io.github.lummertzjoao.homes.model.dao.implementation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.model.dao.HomeDao;
import io.github.lummertzjoao.homes.model.entity.Home;

public class HomeDaoYaml implements HomeDao {

	private final Main main;

	private File homesDataFile;
	private FileConfiguration homesDataConfig;

	public HomeDaoYaml(Main main) {
		this.main = main;
	}

	@Override
	public void setup() {
		homesDataFile = new File(main.getDataFolder(), "data.yml");
		if (!homesDataFile.exists()) {
			homesDataFile.getParentFile().mkdirs();
			main.saveResource("data.yml", false);
		}

		homesDataConfig = new YamlConfiguration();
		try {
			homesDataConfig.load(homesDataFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save() {
		try {
			homesDataConfig.save(homesDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Home home) {
		String path = "homes." + home.getId();
		homesDataConfig.set(path + ".homeName", home.getName());
		homesDataConfig.set(path + ".ownerUniqueId", home.getOwnerUniqueId().toString());
		homesDataConfig.set(path + ".location", home.getLocation());
		homesDataConfig.set(path + ".icon", home.getIcon().toString());
		this.save();
	}

	@Override
	public void update(Home home) {
		String path = "homes." + home.getId();
		// These are the only 2 attributes that are mutable in a home
		homesDataConfig.set(path + ".homeName", home.getName());
		homesDataConfig.set(path + ".icon", home.getIcon().toString());
		this.save();
	}

	@Override
	public void deleteById(int id) {
		homesDataConfig.set("homes." + id, null);
		this.save();
	}

	@Override
	public Home findById(int id) {
		String homeName = homesDataConfig.getString("homes." + id + ".homeName");
		String ownerUniqueId = homesDataConfig.getString("homes." + id + ".ownerUniqueId");
		Location location = homesDataConfig.getLocation("homes." + id + ".location");
		Material icon = Material.valueOf(homesDataConfig.getString("homes." + id + ".icon"));
		return new Home(id, homeName, UUID.fromString(ownerUniqueId), location, icon);
	}

	public List<Home> findAll() {
		List<Home> found = new ArrayList<>();
		if (homesDataConfig.getConfigurationSection("homes") == null)
			return found;
		for (String id : homesDataConfig.getConfigurationSection("homes").getKeys(false))
			found.add(this.findById(Integer.parseInt(id)));
		return found;
	}
}
