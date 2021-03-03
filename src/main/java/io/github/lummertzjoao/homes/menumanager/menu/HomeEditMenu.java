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
			player.closeInventory();
			Conversation conversation = main.getConversationFactory()
					.withLocalEcho(false)
					.withFirstPrompt(new HomeNamePrompt(this))
					.buildConversation(player);
			conversation.begin();
			break;
		case CLOCK:
			new IconColorSelectionMenu(playerMenuUtility, main).open();
			break;
		case BARRIER:
			new DeletionConfirmationMenu(playerMenuUtility, main).open();
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
		addMenuBorder();
		
		inventory.setItem(20, createItem(Material.PAPER, ChatColor.YELLOW + "Rename",
				ChatColor.GRAY + "Click here to rename this home"));
		inventory.setItem(22, createItem(Material.CLOCK, ChatColor.YELLOW + "Select icon color",
				ChatColor.GRAY + "Click here to open the icon color selection menu"));
		inventory.setItem(24,
				createItem(Material.BARRIER, ChatColor.YELLOW + "Delete",
						ChatColor.GRAY + "Click here to delete this home",
						ChatColor.GRAY + "This action can not be undone"));
		inventory.setItem(40, createItem(Material.ARROW, ChatColor.RED + "Back",
				ChatColor.GRAY + "Click here to go back to the homes menu"));
	}
	
	public void addMenuBorder() {
		for (int i = 0; i < 10; i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, FILLER_GLASS);
			}
		}

		inventory.setItem(17, FILLER_GLASS);
		inventory.setItem(18, FILLER_GLASS);
		inventory.setItem(26, FILLER_GLASS);
		inventory.setItem(27, FILLER_GLASS);

		for (int i = 35; i < 45; i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, FILLER_GLASS);
			}
		}
	}

	@Override
	public String getMenuName() {
		return "Home edit menu";
	}

	@Override
	public int getSlots() {
		return 45;
	}
}
