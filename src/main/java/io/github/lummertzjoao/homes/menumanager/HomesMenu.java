package io.github.lummertzjoao.homes.menumanager;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.domain.Home;
import io.github.lummertzjoao.homes.utils.CommonUtils;

public class HomesMenu extends PaginatedMenu {

	public HomesMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		List<Home> playerHomes = main.getPlayerHomesList(player);
		Material type = event.getCurrentItem().getType();
		
		if (type.equals(Material.DARK_OAK_DOOR)) {
			player.closeInventory();
			return;
		} else if (type.equals(Material.DARK_OAK_BUTTON)) {
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
		}
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();
		inventory.setItem(4, createItem(Material.BEACON, ChatColor.GREEN + "Create a new home",
				ChatColor.GRAY + "Click here to set a new home in", ChatColor.GRAY + "your current location"));

		List<Home> playerHomes = main.getPlayerHomesList(playerMenuUtility.getPlayer());
		if (!playerHomes.isEmpty()) {
			for (int i = 0; i < maxItemsPerPage; i++) {
				index = maxItemsPerPage * page + i;
				if (index >= playerHomes.size())
					break;
				Home home = playerHomes.get(index);
				if (home != null) {
					inventory.addItem(createItem(home.getIcon(), ChatColor.GREEN + "" + ChatColor.BOLD + home.getName(),
							ChatColor.GRAY + "Click here for more information"));
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
