package io.github.lummertzjoao.homes.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.lummertzjoao.homes.Main;

public class HomesCommand implements CommandExecutor {

	private final Main main;
	
	public HomesCommand(Main main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getLogger().info("This command can not be executed from console.");
			return false;
		}
		// open menu
		
		return true;
	}
}
