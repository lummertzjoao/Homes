package io.github.lummertzjoao.homes.util;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.google.common.collect.ImmutableList;

public class CommonUtils {

	public static final String INFO_MESSAGE_PREFIX = ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "!"
			+ ChatColor.DARK_GREEN + "]" + ChatColor.GREEN + " ";
	public static final String ERROR_MESSAGE_PREFIX = ChatColor.DARK_RED + "[" + ChatColor.RED + "!"
			+ ChatColor.DARK_RED + "]" + ChatColor.RED + " ";

	public static final List<Material> icons = ImmutableList.of(Material.WHITE_BED, Material.ORANGE_BED,
			Material.MAGENTA_BED, Material.LIGHT_BLUE_BED, Material.YELLOW_BED, Material.LIME_BED, Material.PINK_BED,
			Material.GRAY_BED, Material.LIGHT_GRAY_BED, Material.CYAN_BED, Material.PURPLE_BED, Material.BLUE_BED,
			Material.BROWN_BED, Material.GREEN_BED, Material.RED_BED, Material.BLACK_BED);
}
