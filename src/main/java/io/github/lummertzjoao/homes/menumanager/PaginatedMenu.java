package io.github.lummertzjoao.homes.menumanager;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import io.github.lummertzjoao.homes.Main;

public abstract class PaginatedMenu extends Menu {

	protected int page = 0;
	protected int maxItemsPerPage = 28;
	protected int index = 0;

	public PaginatedMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	public void addMenuBorder() {
		inventory.setItem(48, createItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Previous page",
				ChatColor.GRAY + "Click here to go to the previous page"));
		inventory.setItem(49, createItem(Material.DARK_OAK_DOOR, ChatColor.DARK_RED + "Close",
				ChatColor.GRAY + "Click here to close this menu"));
		inventory.setItem(50, createItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Next page",
				ChatColor.GRAY + "Click here to go to the next page"));

		for (int i = 0; i < 10; i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, FILLER_GLASS);
			}
		}

		inventory.setItem(17, FILLER_GLASS);
		inventory.setItem(18, FILLER_GLASS);
		inventory.setItem(26, FILLER_GLASS);
		inventory.setItem(27, FILLER_GLASS);
		inventory.setItem(35, FILLER_GLASS);
		inventory.setItem(36, FILLER_GLASS);

		for (int i = 44; i < 54; i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, FILLER_GLASS);
			}
		}
	}
}
