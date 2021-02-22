package io.github.lummertzjoao.homes.menumanager.menu;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.domain.Home;
import io.github.lummertzjoao.homes.menumanager.PaginatedMenu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.prompt.HomeNamePrompt;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class HomesMenu extends PaginatedMenu {

	public HomesMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		List<Home> playerHomes = main.getPlayerHomesList(player);
		Material type = event.getCurrentItem().getType();

		if (type == Material.FILLED_MAP) {
			player.closeInventory();
			if (playerHomes.size() < main.getHomesLimit()) {
				Conversation conversation = main.getConversationFactory().withLocalEcho(false)
						.withFirstPrompt(new HomeNamePrompt(this)).buildConversation(player);
				conversation.begin();
			} else {
				player.sendMessage(
						CommonUtils.ERROR_MESSAGE_PREFIX + "You have already reached the maximum number of homes.");
			}
			return;
		} else if (CommonUtils.icons.contains(type)) {
			String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			Home selectedHome = CommonUtils.getHomeByName(name, player, main);
			if (event.isLeftClick()) {
				player.teleport(selectedHome.getLocation());
				player.sendMessage(CommonUtils.INFO_MESSAGE_PREFIX + "You have been teleported to home "
						+ ChatColor.GOLD + name + ChatColor.GREEN + ".");
			} else {
				playerMenuUtility.setSelectedHome(selectedHome);
				new HomeEditMenu(playerMenuUtility, main).open();
			}
			return;
		} else if (type == Material.CLOCK) {
			new SettingsMenu(playerMenuUtility, main).open();
		} else if (type == Material.DARK_OAK_DOOR) {
			player.closeInventory();
			return;
		} else if (type == Material.DARK_OAK_BUTTON) {
			if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())
					.equalsIgnoreCase("Previous page")) {
				if (page > 0) {
					page -= 1;
					open();
				} else {
					player.sendMessage(
							CommonUtils.ERROR_MESSAGE_PREFIX + ChatColor.RED + "You are already on the first page.");
				}
			} else {
				if (index + 1 < playerHomes.size()) {
					page += 1;
					open();
				} else {
					player.sendMessage(CommonUtils.ERROR_MESSAGE_PREFIX + ChatColor.RED + "You are on the last page.");
				}
			}
			return;
		}
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();
		inventory.setItem(4, createItem(Material.FILLED_MAP, ChatColor.GREEN + "Create a new home",
				ChatColor.GRAY + "Click here to set a new home in", ChatColor.GRAY + "your current location"));
		
		if (playerMenuUtility.getPlayer().hasPermission("homes.admin")) {
			inventory.setItem(53, createItem(Material.CLOCK, ChatColor.GREEN + "Settings",
					ChatColor.GRAY + "Click here to open the settings menu"));
		}

		List<Home> playerHomes = main.getPlayerHomesList(playerMenuUtility.getPlayer());
		if (!playerHomes.isEmpty()) {
			for (int i = 0; i < maxItemsPerPage; i++) {
				index = maxItemsPerPage * page + i;
				if (index >= playerHomes.size())
					break;
				Home home = playerHomes.get(index);
				if (home != null) {
					inventory.addItem(createItem(home.getIcon(), ChatColor.GREEN + "" + ChatColor.BOLD + home.getName(),
							ChatColor.WHITE + "Left click " + ChatColor.GRAY + "to teleport to this home",
							ChatColor.WHITE + "Right click " + ChatColor.GRAY + "to edit this home"));
				}
			}
		}
	}

	@Override
	public String getMenuName() {
		return "Homes";
	}

	@Override
	public int getSlots() {
		return 54;
	}
}
