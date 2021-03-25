package io.github.lummertzjoao.homes.model.entity;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class Home {

	private Integer id;
	private String name;
	private final UUID ownerUniqueId;
	private final Location location;
	private Material icon;

	public Home(String name, UUID ownerUniqueId) {
		this.id = null;
		this.name = name;
		this.location = Bukkit.getPlayer(ownerUniqueId).getLocation();
		this.ownerUniqueId = ownerUniqueId;
		this.icon = Material.RED_BED;
	}

	public Home(Integer id, String name, UUID ownerUniqueId, Location location, Material icon) {
		this.id = id;
		this.name = name;
		this.ownerUniqueId = ownerUniqueId;
		this.location = location;
		this.icon = icon;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getOwnerUniqueId() {
		return ownerUniqueId;
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
		result = (int) (prime * result + id);
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
}
