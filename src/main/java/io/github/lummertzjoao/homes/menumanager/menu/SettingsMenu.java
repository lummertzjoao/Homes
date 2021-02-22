package io.github.lummertzjoao.homes.menumanager.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.prompt.HomesLimitPrompt;

public class SettingsMenu extends Menu {

	public SettingsMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		switch (event.getCurrentItem().getType()) {
		case OAK_DOOR:
			player.closeInventory();
			Conversation conversation = main.getConversationFactory()
					.withLocalEcho(false)
					.withFirstPrompt(new HomesLimitPrompt(main))
					.buildConversation(player);
			conversation.begin();
			break;
		case ITEM_FRAME:
			// open size selection menu
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
		inventory.setItem(11, createItem(Material.OAK_DOOR, ChatColor.GREEN + "Homes limit",
				ChatColor.GRAY + "Click here to change the homes limit"));
		inventory.setItem(15, createItem(Material.ITEM_FRAME, ChatColor.GREEN + "Homes menu size",
				ChatColor.GRAY + "Click here to change the homes menu size"));
		inventory.setItem(22, createItem(Material.ARROW, ChatColor.RED + "Back",
				ChatColor.GRAY + "Click here to go back to the homes menu"));
	}

	@Override
	public String getMenuName() {
		return "Settings";
	}

	@Override
	public int getSlots() {
		return 27;
	}
}
