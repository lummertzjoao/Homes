package io.github.lummertzjoao.homes.model.entity;

import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class Home {

	private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	
	private final int id;
	private String name;
	private final String ownerName;
	private final Location location;
	private Material icon;

	public Home(String name, String playerName) {
		this.id = ID_GENERATOR.getAndIncrement();
		this.name = name;
		this.location = Bukkit.getPlayer(playerName).getLocation();
		this.ownerName = playerName;
		this.icon = Material.RED_BED;
	}

	public Home(int id, String name, String playerName, Location location, Material icon) {
		this.id = id;
		this.name = name;
		this.ownerName = playerName;
		this.location = location;
		this.icon = icon;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerName() {
		return ownerName;
	}
	
	public Location getLocation() {
		return location;
	}

	public Material getIcon() {
		return icon;
	}

	public void setIcon(Material icon) {
		this.icon = icon;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Home other = (Home) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Home [name=" + name + ", location=" + location + ", owner=" + ownerName + ", icon=" + icon + "]";
	}
}
