package io.github.lummertzjoao.homes.menumanager;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import io.github.lummertzjoao.homes.Main;

public abstract class PaginatedMenu extends Menu {

	protected int page = 0;
	protected int index = 0;

	public PaginatedMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	public void addMenuBorder() {
		inventory.setItem(getSlots() - 6, createItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Previous page",
				ChatColor.GRAY + "Click here to go to the previous page"));
		inventory.setItem(getSlots() - 5, createItem(Material.DARK_OAK_DOOR, ChatColor.DARK_RED + "Close",
				ChatColor.GRAY + "Click here to close this menu"));
		inventory.setItem(getSlots() - 4, createItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Next page",
				ChatColor.GRAY + "Click here to go to the next page"));

		for (int i = 0; i < 10; i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, FILLER_GLASS);
			}
		}

		inventory.setItem(17, FILLER_GLASS);
		inventory.setItem(18, FILLER_GLASS);
		inventory.setItem(26, FILLER_GLASS);
		
		if (getSlots() > 27) {
			inventory.setItem(27, FILLER_GLASS);
			inventory.setItem(35, FILLER_GLASS);
		}
		
		if (getSlots() > 36) {
			inventory.setItem(36, FILLER_GLASS);
		}

		for (int i = getSlots() - 10; i < getSlots(); i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, FILLER_GLASS);
			}
		}
	}
	
	protected int getMaxItemsPerPage() {
		int size = main.getHomesMenuSize();
		if (size == 27) return 7;
		else if (size == 36) return 14;
		else if (size == 45) return 21;
		else return 28;
	}
}
