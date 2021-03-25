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

	@Override
	protected void addMenuBorder() {
		super.addMenuBorder();

		inventory.setItem(getSlots() - 6, createItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Previous page",
				ChatColor.GRAY + "Click here to go to the previous page"));
		inventory.setItem(getSlots() - 5, createItem(Material.DARK_OAK_DOOR, ChatColor.DARK_RED + "Close",
				ChatColor.GRAY + "Click here to close this menu"));
		inventory.setItem(getSlots() - 4, createItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Next page",
				ChatColor.GRAY + "Click here to go to the next page"));
	}

	protected int getMaxItemsPerPage() {
		int size = main.getHomesMenuSize();
		if (size == 27)
			return 7;
		else if (size == 36)
			return 14;
		else if (size == 45)
			return 21;
		else
			return 28;
	}
}
