package io.github.lummertzjoao.homes.model.dao;

import io.github.lummertzjoao.homes.Main;
import io.github.lummertzjoao.homes.model.dao.implementation.HomeDaoYaml;

public class DaoFactory {

	public static HomeDao createHomeDao(Main main) {
		return new HomeDaoYaml(main);
	}
}
