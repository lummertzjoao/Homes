package io.github.lummertzjoao.homes.util;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableList;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.domain.Home;

public class CommonUtils {

	public static final String INFO_MESSAGE_PREFIX = ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "!"
			+ ChatColor.DARK_GREEN + "]" + ChatColor.GREEN + " ";
	public static final String ERROR_MESSAGE_PREFIX = ChatColor.DARK_RED + "[" + ChatColor.RED + "!"
			+ ChatColor.DARK_RED + "]" + ChatColor.RED + " ";

	public static final List<Material> icons = ImmutableList.of(Material.WHITE_BED, Material.ORANGE_BED,
			Material.MAGENTA_BED, Material.LIGHT_BLUE_BED, Material.YELLOW_BED, Material.LIME_BED, Material.PINK_BED,
			Material.GRAY_BED, Material.LIGHT_GRAY_BED, Material.CYAN_BED, Material.PURPLE_BED, Material.BLUE_BED,
			Material.BROWN_BED, Material.GREEN_BED, Material.RED_BED, Material.BLACK_BED);

	public static Material getColorFromIcon(Material icon) {
		return Material.valueOf(icon.toString().replace("BED", "DYE"));
	}
	
	public static Material getIconFromColor(Material color) {
		return Material.valueOf(color.toString().replace("DYE", "BED"));
	}
	
	public static String getColorName(Material color) {
		return color.toString().toLowerCase().replaceAll("_", " ").replace("dye", "");
	}
	
	public static Home getHomeByName(String name, Player player, Main main) {
		for (Home home : main.getPlayerHomesList(player)) {
			if (home.getName().equals(name))
				return home;
		}
		return null;
	}
}
