package io.github.lummertzjoao.homes.menumanager;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.lummertzjoao.homes.Main;

public abstract class Menu implements InventoryHolder {

	protected PlayerMenuUtility playerMenuUtility;
	protected Inventory inventory;
	protected final ItemStack FILLER_GLASS = createItem(Material.GRAY_STAINED_GLASS_PANE, " ");
	
	protected Main main;
	
	public Menu(PlayerMenuUtility playerMenuUtility, Main main) {
		this.playerMenuUtility = playerMenuUtility;
		this.main = main;
	}
	
	public abstract String getMenuName();
	
	public abstract Integer getSlots();
	
	public abstract void onInventoryClick(InventoryClickEvent event);
	
	public abstract void setMenuItems();
	
	public void open() {
		inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
		this.setMenuItems();
		playerMenuUtility.getPlayer().openInventory(inventory);
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public ItemStack createItem(Material material, String displayName, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}
	
}
