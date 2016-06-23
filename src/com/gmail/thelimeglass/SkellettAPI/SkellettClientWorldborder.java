package com.gmail.thelimeglass.SkellettAPI;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.thelimeglass.Main;

public class SkellettClientWorldborder {
	private static Method handle;
    private static Method sendPacket;
    private static Method center;
    private static Method distance;
    private static Method time;
    private static Method movement;
    private static Field player_connection;
    private static Constructor<?> constructor;
    private static Constructor<?> border_constructor;
    private static Object constant;
	public static void sendWorldBorderPacket(Player p, int dist, double oldradius, double newradius, long delay) {
        try {
            Object worldborder = border_constructor.newInstance(new Object[0]);
            center.invoke(worldborder, p.getLocation().getX(), p.getLocation().getY());
            distance.invoke(worldborder, dist);
            time.invoke(worldborder, 15);
            movement.invoke(worldborder, oldradius, newradius, delay);
            Object packet = constructor.newInstance(worldborder, constant);
            sendPacket.invoke(player_connection.get(handle.invoke((Object)p, new Object[0])), packet);
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
	public static void setBorder(Player p, int percentage) {
        int dist = -10000 * percentage + 1300000;
        sendWorldBorderPacket(p, dist, 200000.0, 200000.0, 0);
        Bukkit.getConsoleSender().sendMessage(Main.cc("Set " + percentage + "% border for player " + p.getName()));
    }
}