package io.github.lummertzjoao.homes.menumanager.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.model.dao.DaoFactory;
import io.github.lummertzjoao.homes.prompt.MySqlConfigurationPrompt;
import io.github.lummertzjoao.homes.prompt.MySqlConfigurationPromptType;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class MySqlConfigurationMenu extends Menu {

	public MySqlConfigurationMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Material type = item.getType();

		Set<Material> mySqlConfigMaterials = Set.of(Material.NAME_TAG, Material.OAK_FENCE_GATE, Material.CHEST_MINECART,
				Material.BOOK, Material.FEATHER);

		if (mySqlConfigMaterials.contains(type)) {
			String displayName = ChatColor.stripColor(item.getItemMeta().getDisplayName()).toUpperCase();
			Conversation conversation = main.getConversationFactory().withLocalEcho(false)
					.withFirstPrompt(
							new MySqlConfigurationPrompt(this, MySqlConfigurationPromptType.valueOf(displayName)))
					.buildConversation(playerMenuUtility.getPlayer());
			conversation.begin();
			playerMenuUtility.getPlayer().closeInventory();
		} else if (type == Material.ENDER_CHEST) {
			if (!main.getConfig().getBoolean("mysql.enabled")) {
				String[] dbProperties = { "hostname", "port", "database", "username", "password" };
				for (String property : dbProperties) {
					if (main.getConfig().getString("mysql." + property).equals("your_" + property + "_here")) {
						playerMenuUtility.getPlayer()
								.sendMessage(CommonUtils.ERROR_MESSAGE_PREFIX + "Could not connect to database. "
										+ ChatColor.GOLD + StringUtils.capitalize(property) + ChatColor.RED
										+ " is not set.");
						return;
					}
				}

				main.getConfig().set("mysql.enabled", true);
				main.saveConfig();
				main.setHomeDao(DaoFactory.createHomeDao(main));
				playerMenuUtility.getPlayer()
						.sendMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Changed database to MySQL");
				this.open();
			}
		} else if (type == Material.ARROW) {
			new DatabaseSelectionMenu(playerMenuUtility, main).open();
		}
	}

	@Override
	public void setMenuItems() {
		super.addMenuBorder();

		List<String> lore = new ArrayList<>();
		if (main.getConfig().getBoolean("mysql.enabled")) {
			lore.add(ChatColor.GRAY + "Status: " + ChatColor.GREEN + "enabled");
		} else {
			lore.add(ChatColor.GRAY + "Status: " + ChatColor.RED + "disabled");
		}
		lore.addAll(Arrays.asList("", ChatColor.GRAY + "Click here to enable MySQL and disable",
				ChatColor.GRAY + "saving in YAML file"));

		inventory.setItem(4, createItem(Material.ENDER_CHEST, ChatColor.GREEN + "MySQL", lore));

		ItemStack hostname = createItem(Material.NAME_TAG, ChatColor.GREEN + "Hostname",
				ChatColor.GRAY + "Click here to set the hostname");
		ItemStack port = createItem(Material.OAK_FENCE_GATE, ChatColor.GREEN + "Port",
				ChatColor.GRAY + "Click here to set the port");
		ItemStack database = createItem(Material.CHEST_MINECART, ChatColor.GREEN + "Database",
				ChatColor.GRAY + "Click here to set the database");
		ItemStack username = createItem(Material.BOOK, ChatColor.GREEN + "Username",
				ChatColor.GRAY + "Click here to set the username");
		ItemStack password = createItem(Material.FEATHER, ChatColor.GREEN + "Password",
				ChatColor.GRAY + "Click here to set the password");

		ItemStack[] dbProperties = { hostname, port, database, username, password };
		for (ItemStack property : dbProperties) {
			ItemMeta meta = property.getItemMeta();
			String name = ChatColor.stripColor(meta.getDisplayName()).toLowerCase();
			meta.getLore().add(ChatColor.GRAY + "Current: "
					+ (main.getConfig().getString("mysql." + name).equals("your_" + name + "_here")
					? ChatColor.RED + "not set" : ChatColor.WHITE + main.getConfig().getString("mysql.name")));
			property.setItemMeta(meta);
		}

		inventory.setItem(20, hostname);
		inventory.setItem(22, port);
		inventory.setItem(24, database);
		inventory.setItem(30, username);
		inventory.setItem(32, password);

		inventory.setItem(49, createItem(Material.ARROW, ChatColor.RED + "Back",
				ChatColor.GRAY + "Click here to go back to the database selection menu"));
	}

	@Override
	public String getMenuName() {
		return "MySQL configuration";
	}

	@Override
	public int getSlots() {
		return 54;
	}
}
