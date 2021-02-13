package io.github.lummertzjoao.homes.menumanager.menu;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class IconColorSelectionMenu extends Menu {

	private final Set<Material> iconColors = new HashSet<>();

	public IconColorSelectionMenu(PlayerMenuUtility playerMenuUtility, Main main) {
		super(playerMenuUtility, main);

		for (Material icon : CommonUtils.icons) {
			iconColors.add(Material.valueOf(icon.toString().replace("BED", "DYE")));
		}
	}
	
	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		Material type = event.getCurrentItem().getType();

		if (iconColors.contains(type)) {
			if (type == Material
					.valueOf(playerMenuUtility.getSelectedHome().getIcon().toString().replace("BED", "DYE")))
				return;
			playerMenuUtility.getSelectedHome().setIcon(Material.valueOf(type.toString().replace("DYE", "BED")));
			String name = type.toString().toLowerCase().replaceAll("_", " ").replace("dye", "");
			playerMenuUtility.getPlayer().sendMessage(
					CommonUtils.INFO_MESSAGE_PREFIX + "Selected " + ChatColor.GOLD + name + ChatColor.GREEN + "icon.");
			new HomesMenu(playerMenuUtility, main).open();
		} else if (type == Material.ARROW) {
			new HomeEditMenu(playerMenuUtility, main).open();
		}
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		for (Material material : iconColors) {
			String name = ChatColor.GREEN
					+ StringUtils.capitalize(material.toString().toLowerCase().replaceAll("_", " ").replace("dye", ""));

			if (material != Material
					.valueOf(playerMenuUtility.getSelectedHome().getIcon().toString().replace("BED", "DYE"))) {
				inventory.addItem(createItem(material, ChatColor.GREEN + StringUtils.capitalize(name),
						ChatColor.GRAY + "Click here to select this color"));
			} else {
				ItemStack icon = createItem(material, name, ChatColor.GRAY + "Selected color");
				icon.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
				ItemMeta iconMeta = icon.getItemMeta();
				iconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				icon.setItemMeta(iconMeta);
				inventory.addItem(icon);
			}
		}

		inventory.setItem(40, createItem(Material.ARROW, ChatColor.RED + "Back",
				ChatColor.GRAY + "Click here to go back to the home edit menu"));
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
		return "Icon color selection menu";
	}

	@Override
	public int getSlots() {
		return 45;
	}
}
