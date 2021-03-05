package io.github.lummertzjoao.homes.menumanager.menu;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.PaginatedMenu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.model.entity.Home;
import io.github.lummertzjoao.homes.prompt.HomeNamePrompt;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class HomesMenu extends PaginatedMenu {

	private final UUID playerUniqueId;
	
	public HomesMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
		playerUniqueId = null;
	}
	
	public HomesMenu(PlayerMenuUtility playerMenuUtility, Main main, UUID player) {
		super(playerMenuUtility, main);
		this.playerUniqueId = player;
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		List<Home> playerHomes = main.getHomeDao().getPlayerHomes(player.getUniqueId());
		Material type = event.getCurrentItem().getType();

		if (type == Material.FILLED_MAP) {
			player.closeInventory();
			if (playerHomes.size() < main.getHomesLimit()) {
				Conversation conversation = main.getConversationFactory().withLocalEcho(false)
						.withFirstPrompt(new HomeNamePrompt(this)).buildConversation(player);
				conversation.begin();
			} else {
				player.sendMessage(
						CommonUtils.ERROR_MESSAGE_PREFIX + "You have reached the maximum number of homes.");
			}
			return;
		} else if (CommonUtils.icons.contains(type)) {
			Home selectedHome = null;
			
			NamespacedKey key = new NamespacedKey(main, "home-id");
			ItemStack item = event.getCurrentItem();
			PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
			if (container.has(key, PersistentDataType.INTEGER)) {
				selectedHome = main.getHomeDao().findById(container.get(key, PersistentDataType.INTEGER));
			}
			
			if (event.isLeftClick()) {
				player.teleport(selectedHome.getLocation());
				player.sendMessage(CommonUtils.INFO_MESSAGE_PREFIX + "You have been teleported to home "
						+ ChatColor.GOLD + selectedHome.getName() + ChatColor.GREEN + ".");
			} else {
				playerMenuUtility.setSelectedHome(selectedHome);
				new HomeEditMenu(playerMenuUtility, main).open();
			}
			return;
		} else if (type == Material.NETHER_STAR) {
			new AdminPanelMenu(playerMenuUtility, main).open();
		} else if (type == Material.DARK_OAK_DOOR) {
			player.closeInventory();
			return;
		} else if (type == Material.ARROW) {
			new PlayerSelectionMenu(playerMenuUtility, main).open();
			return;
		}  else if (type == Material.DARK_OAK_BUTTON) {
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
		
		if (this.playerUniqueId == null) {
			inventory.setItem(4, createItem(Material.FILLED_MAP, ChatColor.GREEN + "Create a new home",
					ChatColor.GRAY + "Click here to set a new home in", ChatColor.GRAY + "your current location"));
			if (playerMenuUtility.getPlayer().hasPermission("homes.admin")) {
				inventory.setItem(53, createItem(Material.NETHER_STAR, ChatColor.GREEN + "Admin panel",
						ChatColor.GRAY + "Click here to open the admin panel"));
			}
		} else {
			ItemStack item = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			OfflinePlayer player = Bukkit.getOfflinePlayer(this.playerUniqueId);
			meta.setOwningPlayer(player);
			meta.setDisplayName(ChatColor.GREEN + player.getName());
			meta.setLore(Arrays.asList(ChatColor.GRAY + "You are viewing " + ChatColor.WHITE
					+ player.getName() + ChatColor.GRAY + "'s homes"));
			item.setItemMeta(meta);
			inventory.setItem(4, item);
			inventory.setItem(49, createItem(Material.ARROW, ChatColor.GREEN + "Back",
					ChatColor.GRAY + "Click here to go back to the admin panel menu"));
		}
		

		List<Home> playerHomes = main.getHomeDao().getPlayerHomes(playerMenuUtility.getPlayer().getUniqueId());
		if (!playerHomes.isEmpty()) {
			for (int i = 0; i < maxItemsPerPage; i++) {
				index = maxItemsPerPage * page + i;
				if (index >= playerHomes.size())
					break;
				Home home = playerHomes.get(index);
				if (home != null) {
					ItemStack item = new ItemStack(home.getIcon());
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + home.getName());
					meta.setLore(
							Arrays.asList(ChatColor.WHITE + "Left click " + ChatColor.GRAY + "to teleport to this home",
									ChatColor.WHITE + "Right click " + ChatColor.GRAY + "to edit this home"));
					meta.getPersistentDataContainer().set(new NamespacedKey(main, "home-id"),
							PersistentDataType.INTEGER, home.getId());
					item.setItemMeta(meta);
					inventory.addItem(item);
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
