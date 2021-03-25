package io.github.lummertzjoao.homes.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.github.lummertzjoao.homes.model.entity.Home;

public interface HomeDao {

	void setup();

	void save();

	void insert(Home home);

	void update(Home home);

	void deleteById(Integer id);

	Home findById(Integer id);

	List<Home> findAll();
	
	default List<Home> getPlayerHomes(UUID ownerUniqueId) {
		return findAll().stream().filter(x -> x.getOwnerUniqueId().equals(ownerUniqueId)).collect(Collectors.toList());
	}

	default List<UUID> getAllPlayers() {
		List<UUID> players = new ArrayList<>();
		for (Home home : this.findAll()) {
			if (!players.contains(home.getOwnerUniqueId()))
				players.add(home.getOwnerUniqueId());
		}
		return players;
	}
}
