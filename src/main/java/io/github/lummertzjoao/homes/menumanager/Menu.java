package io.github.lummertzjoao.homes.menumanager;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.lummertzjoao.homes.Main;

public abstract class Menu implements InventoryHolder {

	protected final PlayerMenuUtility playerMenuUtility;
	protected Inventory inventory;
	protected final ItemStack FILLER_GLASS = createItem(Material.GRAY_STAINED_GLASS_PANE, " ");

	protected final Main main;

	public Menu(PlayerMenuUtility playerMenuUtility, Main main) {
		this.playerMenuUtility = playerMenuUtility;
		this.main = main;
	}

	public void open() {
		inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
		this.setMenuItems();
		playerMenuUtility.getPlayer().openInventory(inventory);
	}

	protected void addMenuBorder() {
		for (int i = 0; i < 10; i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, FILLER_GLASS);
			}
		}

		inventory.setItem(17, FILLER_GLASS);
		inventory.setItem(18, FILLER_GLASS);
		inventory.setItem(26, FILLER_GLASS);
		
		if (getSlots() > 27) {
			inventory.setItem(27, FILLER_GLASS);
			inventory.setItem(35, FILLER_GLASS);
		}
		
		if (getSlots() > 36) {
			inventory.setItem(36, FILLER_GLASS);
		}

		for (int i = getSlots() - 10; i < getSlots(); i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, FILLER_GLASS);
			}
		}
	}
	
	public ItemStack createItem(Material material, String displayName, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack createItem(Material material, String displayName, List<String> lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public Inventory getInventory() {
		return inventory;
	}

	public Main getMain() {
		return main;
	}
	
	public abstract void onInventoryClick(InventoryClickEvent event);

	public abstract void setMenuItems();

	public abstract String getMenuName();

	public abstract int getSlots();
}
