package io.github.lummertzjoao.homes.domain;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Home {

	private final String name;
	private final Location location;
	private final Player owner;
	private Material icon;

	public Home(String name, Player owner) {
		this.name = name;
		this.location = owner.getLocation();
		this.owner = owner;
		this.icon = Material.RED_BED;
	}

	public Home(String name, Location location, Player owner, Material icon) {
		this.name = name;
		this.location = location;
		this.owner = owner;
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public Location getLocation() {
		return location;
	}

	public Player getOwner() {
		return owner;
	}

	public Material getIcon() {
		return icon;
	}

	public void setIcon(Material icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "Home [name=" + name + ", location=" + location + ", owner=" + owner + ", icon=" + icon + "]";
	}
}
