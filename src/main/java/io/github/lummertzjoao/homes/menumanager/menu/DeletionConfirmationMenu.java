package io.github.lummertzjoao.homes.menumanager.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.model.entity.Home;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class DeletionConfirmationMenu extends Menu {

	public DeletionConfirmationMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		Home selectedHome = playerMenuUtility.getSelectedHome();
		switch (event.getCurrentItem().getType()) {
		case RED_WOOL:
			new HomeEditMenu(playerMenuUtility, main).open();
			break;
		case LIME_WOOL:
			main.getHomeDao().deleteById(selectedHome.getId());
			event.getWhoClicked().sendMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Deleted home " + ChatColor.GOLD
					+ selectedHome.getName() + ChatColor.GREEN + " successfully.");
			new HomesMenu(playerMenuUtility, main).open();
			break;
		default:
			break;
		}
	}

	@Override
	public void setMenuItems() {
		inventory.setItem(11, createItem(Material.RED_WOOL, ChatColor.RED + "Cancel",
				ChatColor.GRAY + "Click here to cancel this action"));
		inventory.setItem(13,
				createItem(Material.PAPER, ChatColor.YELLOW + playerMenuUtility.getSelectedHome().getName(),
						ChatColor.GRAY + "Are you sure you want to delete home " + ChatColor.WHITE
								+ playerMenuUtility.getSelectedHome().getName() + ChatColor.GRAY + "?",
						ChatColor.GRAY + "This action can not be undone"));
		inventory.setItem(15, createItem(Material.LIME_WOOL, ChatColor.GREEN + "Confirm",
				ChatColor.GRAY + "Click here to delete this home"));
	}

	@Override
	public String getMenuName() {
		return "Are you sure?";
	}

	@Override
	public int getSlots() {
		return 27;
	}
}
