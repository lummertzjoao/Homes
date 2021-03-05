package io.github.lummertzjoao.homes.menumanager.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;

public class AdminPanelMenu extends Menu {

	public AdminPanelMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		switch (event.getCurrentItem().getType()) {
		case PLAYER_HEAD:
			new PlayerSelectionMenu(playerMenuUtility, main).open();
			break;
		case CLOCK:
			new SettingsMenu(playerMenuUtility, main).open();
			break;
		case ARROW:
			new HomesMenu(playerMenuUtility, main).open();
			break;
		default:
			break;
		}
	}

	@Override
	public void setMenuItems() {
		inventory.setItem(11, createItem(Material.PLAYER_HEAD, ChatColor.GREEN + "View player homes",
				ChatColor.GRAY + "Click here to view the homes of specific players"));
		inventory.setItem(15, createItem(Material.CLOCK, ChatColor.GREEN + "Settings",
				ChatColor.GRAY + "Click here open the settings menu"));
		inventory.setItem(22, createItem(Material.ARROW, ChatColor.RED + "Back",
				ChatColor.GRAY + "Click here to go back to the homes menu"));
	}

	@Override
	public String getMenuName() {
		return "Admin panel";
	}

	@Override
	public int getSlots() {
		return 27;
	}
}
