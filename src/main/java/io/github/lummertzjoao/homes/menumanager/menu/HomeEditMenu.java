package io.github.lummertzjoao.homes.menumanager.menu;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.model.entity.Home;
import io.github.lummertzjoao.homes.prompt.HomeNamePrompt;

public class HomeEditMenu extends Menu {

	private final boolean adminView;
	
	public HomeEditMenu(PlayerMenuUtility playerMenuUtility, Main main, boolean adminView) {
		super(playerMenuUtility, main);
		this.adminView = adminView;
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		switch (event.getCurrentItem().getType()) {
		case PAPER:
			player.closeInventory();
			Conversation conversation = main.getConversationFactory().withLocalEcho(false)
					.withFirstPrompt(new HomeNamePrompt(this)).buildConversation(player);
			conversation.begin();
			break;
		case CLOCK:
			new IconColorSelectionMenu(playerMenuUtility, main, adminView).open();
			break;
		case BARRIER:
			new DeletionConfirmationMenu(playerMenuUtility, main, adminView).open();
			break;
		case ARROW:
			UUID otherUniqueId = playerMenuUtility.getSelectedHome().getOwnerUniqueId();
			HomesMenu menu;
			if (adminView) {
				menu = new HomesMenu(playerMenuUtility, main, otherUniqueId);
			} else {
				menu = new HomesMenu(playerMenuUtility, main);
			}
			menu.open();
			break;
		default:
			break;
		}
	}

	@Override
	public void setMenuItems() {
		super.addMenuBorder();

		Home home = playerMenuUtility.getSelectedHome();
		inventory.setItem(4, createItem(home.getIcon(), ChatColor.GREEN + home.getName(),
				ChatColor.GRAY + "You are editing home " + ChatColor.WHITE + home.getName()));

		inventory.setItem(20, createItem(Material.PAPER, ChatColor.YELLOW + "Rename",
				ChatColor.GRAY + "Click here to rename this home"));
		inventory.setItem(22, createItem(Material.CLOCK, ChatColor.YELLOW + "Select icon color",
				ChatColor.GRAY + "Click here to open the icon color selection menu"));
		inventory.setItem(24, createItem(Material.BARRIER, ChatColor.YELLOW + "Delete",
				ChatColor.GRAY + "Click here to delete this home", ChatColor.GRAY + "This action can not be undone"));
		
		inventory.setItem(40, createItem(Material.ARROW, ChatColor.RED + "Back",
				ChatColor.GRAY + "Click here to go back to the homes menu"));
	}

	@Override
	public String getMenuName() {
		return playerMenuUtility.getSelectedHome().getName();
	}

	@Override
	public int getSlots() {
		return 45;
	}
}
