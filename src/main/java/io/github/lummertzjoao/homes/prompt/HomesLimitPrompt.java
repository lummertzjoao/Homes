package io.github.lummertzjoao.homes.prompt;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class HomesLimitPrompt extends NumericPrompt {

	private final Menu menu;

	public HomesLimitPrompt(Menu menu) {
		this.menu = menu;
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return ChatColor.YELLOW + "Enter the desired number for the homes limit or '" + ChatColor.GOLD + "0"
				+ ChatColor.YELLOW + "' to cancel this action";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
		int value = input.intValue();
		if (value == 0) {
			context.getForWhom().sendRawMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Action canceled.");
			menu.open();
			return END_OF_CONVERSATION;
			
		}
		if (value < 0) {
			context.getForWhom().sendRawMessage(CommonUtils.ERROR_MESSAGE_PREFIX
					+ "The homes limit must be greater than " + ChatColor.GOLD + "0" + ChatColor.RED + ".");
			return this;
		}
		menu.getMain().setHomesLimit(value);
		context.getForWhom().sendRawMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Changed homes limit to " + ChatColor.GOLD
				+ value + ChatColor.GREEN + ".");
		menu.open();
		return END_OF_CONVERSATION;
	}

}
