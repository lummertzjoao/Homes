package io.github.lummertzjoao.homes.menumanager.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.prompt.HomeNamePrompt;

public class HomeEditMenu extends Menu {

	public HomeEditMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		switch (event.getCurrentItem().getType()) {
		case PAPER:
			Conversation conversation = main.getConversationFactory().withLocalEcho(false)
					.withFirstPrompt(new HomeNamePrompt(this)).buildConversation(player);
			conversation.begin();
			break;
		case CLOCK:
			// open icon selection menu
			break;
		case BARRIER:
			// delete home
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
		inventory.setItem(11, createItem(Material.PAPER, ChatColor.YELLOW + "Rename",
				ChatColor.GRAY + "Click here to rename this home"));
		inventory.setItem(13, createItem(Material.CLOCK, ChatColor.YELLOW + "Select icon",
				ChatColor.GRAY + "Click here to open the icon selection menu"));
		inventory.setItem(15,
				createItem(Material.BARRIER, ChatColor.YELLOW + "Delete",
						ChatColor.GRAY + "Click here to delete this home",
						ChatColor.GRAY + "IMPORTANT: This action can not be undone"));
		inventory.setItem(22, createItem(Material.ARROW, ChatColor.RED + "Back",
				ChatColor.GRAY + "Click here to go back to the homes menu"));
	}

	@Override
	public String getMenuName() {
		return "Home Edit Menu";
	}

	@Override
	public int getSlots() {
		return 27;
	}
}
