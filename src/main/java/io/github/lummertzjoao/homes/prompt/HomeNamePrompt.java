package io.github.lummertzjoao.homes.prompt;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import io.github.lummertzjoao.homes.domain.Home;
import io.github.lummertzjoao.homes.menumanager.PaginatedMenu;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class HomeNamePrompt extends StringPrompt {

	private final PaginatedMenu menu;

	public HomeNamePrompt(PaginatedMenu menu) {
		this.menu = menu;
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return ChatColor.YELLOW + "Enter the desired name for your home or '" + ChatColor.GOLD + "cancel"
				+ ChatColor.YELLOW + "' to cancel this action.";
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		Player player = (Player) context.getForWhom();
		List<Home> playerHomes = menu.getMain().getPlayerHomesList(player);
		if (input.equalsIgnoreCase("cancel")) {
			player.sendRawMessage(CommonUtils.ERROR_MESSAGE_PREFIX + "Action canceled.");
		} else {
			if (playerHomes.stream().anyMatch(x -> x.getName().equalsIgnoreCase(input))) {
				player.sendRawMessage(CommonUtils.ERROR_MESSAGE_PREFIX + "Home " + ChatColor.GOLD + input
						+ ChatColor.RED + " already exists. Action canceled.");
			} else {
				playerHomes.add(new Home(input, player));
				player.sendRawMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Home " + ChatColor.GOLD + input + ChatColor.GREEN
						+ " has been set.");
			}
		}
		menu.open();
		return END_OF_CONVERSATION;
	}
}
