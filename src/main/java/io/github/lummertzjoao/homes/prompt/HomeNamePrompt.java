package io.github.lummertzjoao.homes.prompt;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import io.github.lummertzjoao.homes.domain.Home;
import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.menumanager.menu.HomeEditMenu;
import io.github.lummertzjoao.homes.menumanager.menu.HomesMenu;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class HomeNamePrompt extends StringPrompt {

	private final Menu menu;

	public HomeNamePrompt(Menu menu) {
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
		PlayerMenuUtility playerMenuUtility = menu.getMain().getPlayerMenuUtility(player);

		if (input.equalsIgnoreCase("cancel")) {
			player.sendRawMessage(CommonUtils.ERROR_MESSAGE_PREFIX + "Action canceled.");
			return END_OF_CONVERSATION;
		}

		if (menu instanceof HomesMenu) {
			createHome(input, player);
		} else if (menu instanceof HomeEditMenu) {
			String previousName = playerMenuUtility.getSelectedHome().getName();
			// Checking if a home with the prompted name already exists
			if (!menu.getMain().getPlayerHomesList(player).stream()
					.anyMatch(x -> x.getName().equalsIgnoreCase(input))) {
				playerMenuUtility.getSelectedHome().setName(input);
				player.sendRawMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Renamed home " + ChatColor.GOLD + previousName
						+ ChatColor.GREEN + " to " + ChatColor.GOLD + input + ChatColor.GREEN + ".");
			} else {
				player.sendRawMessage(CommonUtils.ERROR_MESSAGE_PREFIX + "Home " + ChatColor.GOLD + input
						+ ChatColor.RED + " already exists. Action canceled.");
			}

		}

		new HomesMenu(playerMenuUtility, menu.getMain()).open();
		return END_OF_CONVERSATION;
	}

	private void createHome(String input, Player player) {
		List<Home> playerHomes = menu.getMain().getPlayerHomesList(player);
		if (playerHomes.stream().anyMatch(x -> x.getName().equalsIgnoreCase(input))) {
			player.sendRawMessage(CommonUtils.ERROR_MESSAGE_PREFIX + "Home " + ChatColor.GOLD + input + ChatColor.RED
					+ " already exists. Action canceled.");
		} else {
			playerHomes.add(new Home(input, player));
			player.sendRawMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Home " + ChatColor.GOLD + input + ChatColor.GREEN
					+ " has been set.");
		}
	}
}
