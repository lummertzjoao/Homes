package io.github.lummertzjoao.homes.prompt;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.menumanager.PlayerMenuUtility;
import io.github.lummertzjoao.homes.menumanager.menu.HomeEditMenu;
import io.github.lummertzjoao.homes.menumanager.menu.HomesMenu;
import io.github.lummertzjoao.homes.model.entity.Home;
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
			new HomesMenu(playerMenuUtility, menu.getMain()).open();
			return END_OF_CONVERSATION;
		}

		// Checking if a home with the prompted name already exists
		if (menu.getMain().getHomeDao().getPlayerHomes(player.getUniqueId()).stream()
				.anyMatch(x -> x.getName().equalsIgnoreCase(input))) {
			player.sendRawMessage(CommonUtils.ERROR_MESSAGE_PREFIX + "Home " + ChatColor.GOLD + input + ChatColor.RED
					+ " already exists. Action canceled.");
			new HomesMenu(playerMenuUtility, menu.getMain()).open();
			return END_OF_CONVERSATION;
		}

		if (menu instanceof HomesMenu) {
			createHome(input, player);
		} else if (menu instanceof HomeEditMenu) {
			renameHome(playerMenuUtility.getSelectedHome(), input, player);
		}

		new HomesMenu(playerMenuUtility, menu.getMain()).open();
		return END_OF_CONVERSATION;
	}

	private void createHome(String input, Player player) {
		menu.getMain().getHomeDao().insert(new Home(input, player.getUniqueId()));
		player.sendRawMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Home " + ChatColor.GOLD + input + ChatColor.GREEN
				+ " has been set.");
	}

	private void renameHome(Home home, String input, Player player) {
		String previousName = home.getName();
		home.setName(input);
		menu.getMain().getHomeDao().update(home);
		player.sendRawMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Renamed home " + ChatColor.GOLD + previousName
				+ ChatColor.GREEN + " to " + ChatColor.GOLD + input + ChatColor.GREEN + ".");
	}
}
