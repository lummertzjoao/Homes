package io.github.lummertzjoao.homes.menumanager.menu;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class SizeSelectionMenu extends Menu {

	public SizeSelectionMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Material type = item.getType();
		Player player = (Player) event.getWhoClicked();

		if (type == Material.GRAY_DYE) {
			String displayName = ChatColor.stripColor(item.getItemMeta().getDisplayName());
			main.setHomesMenuSize(getSizeFromDisplayName(ChatColor.stripColor(displayName)));
			player.sendMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Changed homes menu size to "
					+ ChatColor.GOLD + displayName + ChatColor.GREEN + ".");
			new HomesMenu(playerMenuUtility, main).open();
			return;
		} else if (type == Material.ARROW) {
			new SettingsMenu(playerMenuUtility, main).open();
			return;
		}
	}

	@Override
	public void setMenuItems() {
		ItemStack twentySeven = createItem(Material.GRAY_DYE, ChatColor.GREEN + "3 rows (27 slots)",
				ChatColor.GRAY + "Click here to select this size");
		ItemStack thirtySix = createItem(Material.GRAY_DYE, ChatColor.GREEN + "4 rows (36 slots)",
				ChatColor.GRAY + "Click here to select this size");
		ItemStack fortyFive = createItem(Material.GRAY_DYE, ChatColor.GREEN + "5 rows (45 slots)",
				ChatColor.GRAY + "Click here to select this size");
		ItemStack fiftyFour = createItem(Material.GRAY_DYE, ChatColor.GREEN + "6 rows (54 slots)",
				ChatColor.GRAY + "Click here to select this size");

		ItemStack[] sizes = { twentySeven, thirtySix, fortyFive, fiftyFour };

		for (ItemStack size : sizes) {
			if (getSizeFromDisplayName(ChatColor.stripColor(size.getItemMeta().getDisplayName())) == main
					.getHomesMenuSize()) {
				size.setType(Material.LIME_DYE);
				size.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
				ItemMeta meta = size.getItemMeta();
				meta.setLore(Arrays.asList(ChatColor.GRAY + "Current size"));
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				size.setItemMeta(meta);
			}
		}

		inventory.setItem(10, twentySeven);
		inventory.setItem(12, thirtySix);
		inventory.setItem(14, fortyFive);
		inventory.setItem(16, fiftyFour);

		inventory.setItem(31, createItem(Material.ARROW, ChatColor.RED + "Back",
				ChatColor.GRAY + "Click here to go back to the settings menu"));
	}

	private int getSizeFromDisplayName(String displayName) {
		String sizeStr = Character.toString(displayName.charAt(8)) + Character.toString(displayName.charAt(9));
		return Integer.parseInt(sizeStr);
	}

	@Override
	public String getMenuName() {
		return "Homes menu size";
	}

	@Override
	public int getSlots() {
		return 36;
	}
}
