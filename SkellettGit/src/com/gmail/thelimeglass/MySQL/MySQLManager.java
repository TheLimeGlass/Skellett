package com.gmail.thelimeglass.MySQL;

import org.bukkit.event.Listener;

import com.gmail.thelimeglass.Skellett;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MySQLManager implements Listener {
	private static Connection con;

	public static Connection getConnection() {
		return con;
	}

	public static void setConnection(String host, String user, String password, String database) {
		try {
			if (Skellett.mysqlData.getBoolean("AutoReconnect", false)) {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + Skellett.mysqlData.getInt("MySQLSetup.Port", 3306) + "/" + database + "?autoReconnect=true", user, password);
			} else {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + Skellett.mysqlData.getInt("MySQLSetup.Port", 3306) + "/" + database, user, password);
			}
			if (Bukkit.getServer().getPluginManager().getPlugin("Skellett").getConfig().getBoolean("debug")) {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&aMySQL connected! Host: " + host + " Port: " + Skellett.mysqlData.getInt("MySQLSetup.Port", 3306) + " User: " + user + " Password: " + password.replaceAll("", "*") + " user: " + user));
			}
		}
		catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cMySQL connect error: " + e.getMessage()));
		}
	}

	public static void connect() {
		String host = Skellett.mysqlData.getString("MySQLSetup.Host", "");
		String user = Skellett.mysqlData.getString("MySQLSetup.Username", "");
		String password = Skellett.mysqlData.getString("MySQLSetup.Password", "");
		String database = Skellett.mysqlData.getString("MySQLSetup.Database", "");
		if (host.equalsIgnoreCase("")) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cMySQL error: Host is blank"));
		} else if (user.equalsIgnoreCase("")) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cMySQL error: Username is blank"));
		} else if (password.equalsIgnoreCase("")) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cMySQL error: Password is blank"));
		} else if (database.equalsIgnoreCase("")) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cMySQL error: Database is blank"));
		} else if (getConnection() == null) {
			setConnection(host, user, password, database);
		}
	}

	public static void disconnect() {
		try {
			if (getConnection() != null) {
				con.close();
				if (Bukkit.getServer().getPluginManager().getPlugin("Skellett").getConfig().getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cMySQL disconnected."));
				}
			}
		}
		catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cMySQL disconnect error: " + e.getMessage()));
		}
	}

	public static void update(String command) {
		if (command == null) {
			return;
		}
		connect();
		try {
			Statement st = getConnection().createStatement();
			st.executeUpdate(command);
			st.close();
		}
		catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage((Object)ChatColor.RED + "MySQL Update:");
			Bukkit.getConsoleSender().sendMessage((Object)ChatColor.RED + "Command: " + command);
			Bukkit.getConsoleSender().sendMessage((Object)ChatColor.RED + "Error: " + e.getMessage());
		}
	}

	public static ResultSet query(String command) {
		if (command == null) {
			return null;
		}
		connect();
		ResultSet rs = null;
		try {
			Statement st = getConnection().createStatement();
			rs = st.executeQuery(command);
		}
		catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage((Object)ChatColor.RED + "MySQL Query:");
			Bukkit.getConsoleSender().sendMessage((Object)ChatColor.RED + "Command: " + command);
			Bukkit.getConsoleSender().sendMessage((Object)ChatColor.RED + "Error: " + e.getMessage());
		}
		return rs;
	}
}
