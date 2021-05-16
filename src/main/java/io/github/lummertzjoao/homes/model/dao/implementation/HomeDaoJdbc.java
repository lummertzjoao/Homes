package io.github.lummertzjoao.homes.model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import io.github.lummertzjoao.homes.database.Database;
import io.github.lummertzjoao.homes.model.dao.HomeDao;
import io.github.lummertzjoao.homes.model.entity.Home;

public class HomeDaoJdbc implements HomeDao {

	private Connection connection;

	public HomeDaoJdbc(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void setup() {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `" + Database.getTable() + "` ("
					+ "`id` int NOT NULL AUTO_INCREMENT," 
					+ "`name` varchar(256) NOT NULL,"
					+ "`owner_uid` varchar(36) NOT NULL," 
					+ "`world_uid` varchar(36) NOT NULL,"
					+ "`x` decimal(10) NOT NULL," 
					+ "`y` decimal(10) NOT NULL," 
					+ "`z` decimal(10) NOT NULL,"
					+ "`yaw` decimal(10) NOT NULL," 
					+ "`pitch` decimal(10) NOT NULL," 
					+ "`icon` varchar(15) NOT NULL,"
					+ "PRIMARY KEY (`id`));");
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeStatement(statement);
		}
	}

	@Override
	public void save() {
		Database.closeConnection();
	}

	@Override
	public void insert(Home home) {
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(
					"INSERT INTO " + Database.getTable() + " (name, owner_uid, world_uid, x, y, z, yaw, pitch, icon) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, home.getName());
			statement.setString(2, home.getOwnerUniqueId().toString());
			statement.setString(3, home.getLocation().getWorld().getUID().toString());
			statement.setDouble(4, home.getLocation().getX());
			statement.setDouble(5, home.getLocation().getY());
			statement.setDouble(6, home.getLocation().getZ());
			statement.setDouble(7, home.getLocation().getYaw());
			statement.setDouble(8, home.getLocation().getPitch());
			statement.setString(9, home.getIcon().toString());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = statement.getGeneratedKeys();
				if (rs.next()) {
					home.setId(rs.getInt(1));
				}
				Database.closeResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeStatement(statement);
		}
	}

	@Override
	public void update(Home home) {
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(
					"UPDATE " + Database.getTable() + " SET name = ?, icon = ? " + "WHERE id = ?",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, home.getName());
			statement.setString(2, home.getIcon().toString());
			statement.setInt(3, home.getId());

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeStatement(statement);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("DELETE FROM " + Database.getTable() + " WHERE id = ?");
			statement.setInt(1, id);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeStatement(statement);
		}
	}

	@Override
	public Home findById(Integer id) {
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			statement = connection.prepareStatement("SELECT " + Database.getTable() + ".* FROM " + Database.getTable()
					+ " WHERE " + Database.getTable() + ".id = ?");
			statement.setInt(1, id);

			rs = statement.executeQuery();
			if (rs.next()) {
				return instantiateHome(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeStatement(statement);
			Database.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Home> findAll() {
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			statement = connection.prepareStatement("SELECT " + Database.getTable() + ".* FROM " + Database.getTable());
			rs = statement.executeQuery();

			List<Home> homes = new ArrayList<>();
			while (rs.next()) {
				homes.add(instantiateHome(rs));
			}
			return homes;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.closeStatement(statement);
			Database.closeResultSet(rs);
		}
		return null;
	}

	private Home instantiateHome(ResultSet rs) throws SQLException {
		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		UUID owner_uid = UUID.fromString(rs.getString("owner_uid"));
		Location location = new Location(Bukkit.getWorld(UUID.fromString(rs.getString("world_uid"))), rs.getDouble("x"),
				rs.getDouble("y"), rs.getDouble("z"), rs.getFloat("yaw"), rs.getFloat("pitch"));
		Material icon = Material.valueOf(rs.getString("icon"));
		return new Home(id, name, owner_uid, location, icon);
	}
}
