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
import io.github.lummertzjoao.homes.model.entity.Home;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class IconColorSelectionMenu extends Menu {

	private final boolean adminView;
	
	private final Set<Material> iconColors = new HashSet<>();

	public IconColorSelectionMenu(PlayerMenuUtility playerMenuUtility, Main main, boolean adminView) {
		super(playerMenuUtility, main);
		this.adminView = adminView;
		
		for (Material icon : CommonUtils.icons) {
			iconColors.add(CommonUtils.getColorFromIcon(icon));
		}
	}
	
	@Override
	public void onInventoryClick(InventoryClickEvent event) {
		Material type = event.getCurrentItem().getType();
		Home selectedHome = playerMenuUtility.getSelectedHome();
		
		if (iconColors.contains(type)) {
			if (type == CommonUtils.getColorFromIcon(playerMenuUtility.getSelectedHome().getIcon()))
				return;
			
			selectedHome.setIcon(CommonUtils.getIconFromColor(type));
			main.getHomeDao().update(selectedHome);
			String name = CommonUtils.getColorName(type);
			playerMenuUtility.getPlayer().sendMessage(
					CommonUtils.INFO_MESSAGE_PREFIX + "Selected " + ChatColor.GOLD + name + ChatColor.GREEN + "icon.");
			
			if (adminView) {
				new HomesMenu(playerMenuUtility, main, selectedHome.getOwnerUniqueId()).open();
			} else {
				new HomesMenu(playerMenuUtility, main).open();
			}
		} else if (type == Material.ARROW) {
			new HomeEditMenu(playerMenuUtility, main, adminView).open();
		}
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		for (Material material : iconColors) {
			String name = ChatColor.GREEN
					+ StringUtils.capitalize(CommonUtils.getColorName(material));

			if (material != CommonUtils.getColorFromIcon(playerMenuUtility.getSelectedHome().getIcon())) {
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

	@Override
	public String getMenuName() {
		return "Icon color selection menu";
	}

	@Override
	public int getSlots() {
		return 45;
	}
}
