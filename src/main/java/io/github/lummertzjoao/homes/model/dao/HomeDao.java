package io.github.lummertzjoao.homes.model.dao;

import java.util.List;

import io.github.lummertzjoao.homes.model.entity.Home;

public interface HomeDao {

	void setup();
	
	void save();
	
	void insert(Home home);

	void update(Home home);

	void deleteById(int id);

	Home findById(int id);
	
	List<Home> findPlayerHomes(String playerName);
}
