package io.github.lummertzjoao.homes.menumanager.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.prompt.DelayToTeleportPrompt;
import io.github.lummertzjoao.homes.prompt.HomesLimitPrompt;
import io.github.lummertzjoao.homes.util.CommonUtils;

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
			Conversation limitConversation = main.getConversationFactory().withLocalEcho(false)
					.withFirstPrompt(new HomesLimitPrompt(this)).buildConversation(player);
			limitConversation.begin();
			break;
		case ITEM_FRAME:
			new SizeSelectionMenu(playerMenuUtility, main).open();
			break;
		case CHEST:
			new DatabaseSelectionMenu(playerMenuUtility, main).open();
			break;
		case ENDER_PEARL:
			if (event.isLeftClick()) {
				if (main.isDelayToTeleportEnabled()){
					main.getConfig().set("delay-to-teleport.enabled", false);
					player.sendMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Disabled delay to teleport.");
				} else {
					main.getConfig().set("delay-to-teleport.enabled", true);
					player.sendMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Enabled delay to teleport.");
				}
				main.saveConfig();
				super.open();
			} else {
				player.closeInventory();
				Conversation delayConversation = main.getConversationFactory().withLocalEcho(false)
						.withFirstPrompt(new DelayToTeleportPrompt(this)).buildConversation(player);
				delayConversation.begin();
			}
			break;
		case ARROW:
			new AdminPanelMenu(playerMenuUtility, main).open();
			break;
		default:
			break;
		}
	}

	@Override
	public void setMenuItems() {
		inventory.setItem(10,
				createItem(Material.OAK_DOOR, ChatColor.GREEN + "Homes limit",
						ChatColor.GRAY + "Click here to set the homes limit",
						ChatColor.GRAY + "Current: " + ChatColor.WHITE + main.getHomesLimit()));

		inventory.setItem(12, createItem(Material.ITEM_FRAME, ChatColor.GREEN + "Homes menu size",
				ChatColor.GRAY + "Click here to set the homes menu size"));

		inventory.setItem(14, createItem(Material.CHEST, ChatColor.GREEN + "Database",
				ChatColor.GRAY + "Click here to select the database"));

		List<String> lore = new ArrayList<>();
		lore.addAll(Arrays.asList(ChatColor.WHITE + "Left click " + ChatColor.GRAY + "to enable/disable",
				ChatColor.WHITE + "Right click " + ChatColor.GRAY + "to set the delay", ""));
		if (main.getConfig().getBoolean("delay-to-teleport.enabled")) {
			lore.add(ChatColor.GRAY + "Status: " + ChatColor.GREEN + "enabled");
		} else {
			lore.add(ChatColor.GRAY + "Status: " + ChatColor.RED + "disabled");
		}
		lore.add(ChatColor.GRAY + "Current delay (in seconds): " + ChatColor.WHITE + main.getDelayToTeleport());

		ItemStack cooldown = createItem(Material.ENDER_PEARL, ChatColor.GREEN + "Delay to teleport", lore);
		inventory.setItem(16, cooldown);

		inventory.setItem(31, createItem(Material.ARROW, ChatColor.RED + "Back",
				ChatColor.GRAY + "Click here to go back to the admin panel"));
	}

	@Override
	public String getMenuName() {
		return "Settings";
	}

	@Override
	public int getSlots() {
		return 36;
	}
}
