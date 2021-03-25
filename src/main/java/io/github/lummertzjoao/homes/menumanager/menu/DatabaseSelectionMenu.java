package io.github.lummertzjoao.homes.menumanager.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;

public class DatabaseSelectionMenu extends Menu {

	public DatabaseSelectionMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		switch (event.getCurrentItem().getType()) {
		case PAPER:
			new YamlConfigurationMenu(playerMenuUtility, main).open();
			break;
		case ENDER_CHEST:
			new MySqlConfigurationMenu(playerMenuUtility, main).open();
			break;
		case ARROW:
			new SettingsMenu(playerMenuUtility, main).open();
			break;
		default:
			break;
		}
	}

	@Override
	public void setMenuItems() {
		inventory.setItem(11, createItem(Material.PAPER, ChatColor.GREEN + "YAML file", 
				ChatColor.GRAY + "Click here to configure this database"));
		inventory.setItem(15, createItem(Material.ENDER_CHEST, ChatColor.GREEN + "MySQL",
				ChatColor.GRAY + "Click here to configure this database"));
		inventory.setItem(22, createItem(Material.ARROW, ChatColor.RED + "Back",
				ChatColor.GRAY + "Click here to go back to the settings menu"));
	}

	@Override
	public String getMenuName() {
		return "Databases";
	}

	@Override
	public int getSlots() {
		return 27;
	}
}
