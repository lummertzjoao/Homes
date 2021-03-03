package io.github.lummertzjoao.homes.menumanager;

import org.bukkit.entity.Player;

import io.github.lummertzjoao.homes.model.entity.Home;

public class PlayerMenuUtility {
	
	private final Player player;
	
	private Home selectedHome;
	
	public PlayerMenuUtility(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public Home getSelectedHome() {
		return selectedHome;
	}

	public void setSelectedHome(Home selectedHome) {
		this.selectedHome = selectedHome;
	}
}
