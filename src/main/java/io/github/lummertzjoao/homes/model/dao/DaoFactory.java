package io.github.lummertzjoao.homes.model.dao;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.database.Database;
import io.github.lummertzjoao.homes.model.dao.implementation.HomeDaoJdbc;
import io.github.lummertzjoao.homes.model.dao.implementation.HomeDaoYaml;

public class DaoFactory {

	public static HomeDao createHomeDao(Main main) {
		if (main.getConfig().getBoolean("mysql.enabled"))  {
			Database.loadProperties(main);
			return new HomeDaoJdbc(Database.getConnection());
		} else {
			return new HomeDaoYaml(main);
		}
	}
}
