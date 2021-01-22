package io.github.lummertzjoao.homes.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.lummertzjoao.homes.Main;

public class HomesCommand implements CommandExecutor {

	private final Main main;
	
	public HomesCommand(Main main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return true;
	}
}
