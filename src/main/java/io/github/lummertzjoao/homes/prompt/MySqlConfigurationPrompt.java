package io.github.lummertzjoao.homes.prompt;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class MySqlConfigurationPrompt extends StringPrompt {

	private final Menu menu;

	private final MySqlConfigurationPromptType type;

	public MySqlConfigurationPrompt(Menu menu, MySqlConfigurationPromptType type) {
		this.menu = menu;
		this.type = type;
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return ChatColor.YELLOW + "Enter the " + type.toString().toLowerCase() + " or '" + ChatColor.GOLD + "cancel"
				+ ChatColor.YELLOW + "' to cancel this action";
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		if (input.equalsIgnoreCase("cancel")) {
			context.getForWhom().sendRawMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Action canceled.");
		} else {
			menu.getMain().getConfig().set("mysql." + type.toString().toLowerCase(), input);
			menu.getMain().saveConfig();
			context.getForWhom().sendRawMessage(
					CommonUtils.INFO_MESSAGE_PREFIX + StringUtils.capitalize(type.toString().toLowerCase()) + " set to "
							+ ChatColor.GOLD + input + ChatColor.GREEN + ".");
		}
		menu.open();
		return END_OF_CONVERSATION;
	}
}
