package io.github.lummertzjoao.homes.menumanager.menu;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.PaginatedMenu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class PlayerSelectionMenu extends PaginatedMenu {

	public PlayerSelectionMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Material type = item.getType();
		Player player = (Player) event.getWhoClicked();
		List<UUID> players = main.getHomeDao().getAllPlayers();
		if (type == Material.PLAYER_HEAD) {
			UUID selectedPlayer = null;

			NamespacedKey key = new NamespacedKey(main, "owner-uuid");
			PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
			if (container.has(key, PersistentDataType.STRING)) {
				for (UUID uuid : main.getHomeDao().getAllPlayers()) {
					if (uuid.equals(UUID.fromString(container.get(key, PersistentDataType.STRING))))
						selectedPlayer = uuid;
				}
			}
			
			new HomesMenu(playerMenuUtility, main, selectedPlayer).open();
		} else if (type == Material.ARROW) {
			new AdminPanelMenu(playerMenuUtility, main).open();
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
				if (index + 1 < players.size()) {
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

		inventory.setItem(49, createItem(Material.ARROW, ChatColor.GREEN + "Back",
				ChatColor.GRAY + "Click here to go back to the admin panel menu"));

		List<UUID> players = main.getHomeDao().getAllPlayers();
		if (!players.isEmpty()) {
			for (int i = 0; i < getMaxItemsPerPage(); i++) {
				index = getMaxItemsPerPage() * page + i;
				if (index >= players.size())
					break;
				UUID uuid = players.get(index);
				if (uuid != null) {
					ItemStack item = new ItemStack(Material.PLAYER_HEAD);
					SkullMeta meta = (SkullMeta) item.getItemMeta();
					OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
					meta.setOwningPlayer(player);
					meta.getPersistentDataContainer().set(new NamespacedKey(main, "owner-uuid"),
							PersistentDataType.STRING, uuid.toString());
					meta.setDisplayName(ChatColor.GREEN + player.getName());
					meta.setLore(Arrays.asList(ChatColor.GRAY + "Click here to view " + ChatColor.WHITE
							+ player.getName() + ChatColor.GRAY + "'s homes"));
					item.setItemMeta(meta);
					inventory.addItem(item);
				}
			}
		}
	}

	@Override
	public String getMenuName() {
		return "Select a player";
	}

	@Override
	public int getSlots() {
		return 54;
	}
}
