package io.github.lummertzjoao.homes.menumanager.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.model.dao.DaoFactory;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class YamlConfigurationMenu extends Menu {

	public YamlConfigurationMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		switch (event.getCurrentItem().getType()) {
		case PAPER:
			if (main.getConfig().getBoolean("mysql.enabled")) {
				main.getConfig().set("mysql.enabled", false);
				main.saveConfig();
				main.setHomeDao(DaoFactory.createHomeDao(main));
				playerMenuUtility.getPlayer()
						.sendMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Changed database to YAML file");
			}
		case ARROW:
			new DatabaseSelectionMenu(playerMenuUtility, main).open();
			break;
		default:
			break;
		}
	}

	@Override
	public void setMenuItems() {
		super.addMenuBorder();

		List<String> lore = new ArrayList<>();
		if (!main.getConfig().getBoolean("mysql.enabled")) {
			lore.add(ChatColor.GRAY + "Status: " + ChatColor.GREEN + "enabled");
		} else {
			lore.add(ChatColor.GRAY + "Status: " + ChatColor.RED + "disabled");
		}
		lore.addAll(Arrays.asList("", ChatColor.GRAY + "Click here to enable YAML and disable",
				ChatColor.GRAY + "MySQL database"));

		inventory.setItem(4, createItem(Material.PAPER, ChatColor.GREEN + "YAML file", lore));
		inventory.setItem(22,
				createItem(main.isCachingEnabled() ? Material.LIME_DYE : Material.GRAY_DYE, ChatColor.GREEN + "Caching",
						ChatColor.GRAY + "Status: "
								+ (main.isCachingEnabled() ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled"),
						"", ChatColor.GRAY + "Click to enable/disable", "",
						ChatColor.GRAY + "Caching can drastically improve perfomance"));

		inventory.setItem(40, createItem(Material.ARROW, ChatColor.RED + "Back",
				ChatColor.GRAY + "Click here to go back to the database selection menu"));
	}

	@Override
	public String getMenuName() {
		return "YAML configuration";
	}

	@Override
	public int getSlots() {
		return 45;
	}
}
