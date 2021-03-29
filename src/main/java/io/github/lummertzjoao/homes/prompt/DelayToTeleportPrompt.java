package io.github.lummertzjoao.homes.prompt;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

import io.github.lummertzjoao.homes.menumanager.Menu;
import io.github.lummertzjoao.homes.util.CommonUtils;

public class DelayToTeleportPrompt extends NumericPrompt {

	private final Menu menu;

	public DelayToTeleportPrompt(Menu menu) {
		this.menu = menu;
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return ChatColor.YELLOW + "Enter the desired number of seconds for the delay to teleport or '" + ChatColor.GOLD
				+ "0" + ChatColor.YELLOW + "' to cancel this action";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
		int seconds = input.intValue();
		if (seconds == 0) {
			context.getForWhom().sendRawMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Action canceled.");
			menu.open();
			return END_OF_CONVERSATION;
		}
		if (seconds < 0) {
			context.getForWhom().sendRawMessage(CommonUtils.ERROR_MESSAGE_PREFIX
					+ "The number of seconds must be greater than " + ChatColor.GOLD + "0" + ChatColor.RED + ".");
			return this;
		}
		menu.getMain().setDelayToTeleport(seconds);
		context.getForWhom().sendRawMessage(CommonUtils.INFO_MESSAGE_PREFIX + "Delay to teleport set to "
				+ ChatColor.GOLD + seconds + ChatColor.GREEN + " seconds.");
		menu.open();
		return END_OF_CONVERSATION;
	}

}
