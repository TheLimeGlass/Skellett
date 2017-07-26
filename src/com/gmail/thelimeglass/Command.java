package com.gmail.thelimeglass;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Command implements CommandExecutor, Listener {
	
	private Integer page = 0;
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cOnly players may use this command!"));
			return true;
		}
		Player player = (Player) sender;
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("version")) {
				player.sendMessage(Skellett.cc(Skellett.prefix + "Version: &f" + Skellett.plugin.getDescription().getVersion()));
			} else {
				player.performCommand("/skellett");
			}
		} else {
			if (player.hasPermission("skellett.use")) {
				player.openInventory(makeSkellettMenu());
			}
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
		HumanEntity player = e.getWhoClicked();
		Inventory inv = e.getClickedInventory();
		if (e.getClick() == ClickType.CREATIVE || inv == null || player == null) {
			return;
		}
		if (inv.getTitle().equals(Skellett.cc("              &2&l&nSkellett"))) {
			e.setCancelled(true);
			if (inv.getSize() == 27) {
				if (inv.getSize() <= 27) {
					if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE && e.getCurrentItem().getData().getData() == 5) {
						e.setCurrentItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)13));
					} else if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE && e.getCurrentItem().getData().getData() == 13) {
						e.setCurrentItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)15));
					} else if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE && e.getCurrentItem().getData().getData() == 15) {
						e.setCurrentItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)5));
					}
				}
				StringJoiner joiner = new StringJoiner("");
				if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
					for (ItemStack item : e.getClickedInventory().getStorageContents()) {
						joiner.add("" + item.getData().getData());
					}
					if (joiner.toString().equals("1315515515515131501550515015131551551551513")) {
						ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)0);
						for (int i = 0; i < 4; i++) {
							Integer slot = i;
							Bukkit.getScheduler().scheduleSyncDelayedTask(Skellett.instance, new Runnable() {
								@Override
								public void run() {
									inv.setItem(slot, pane);
									inv.setItem(slot + 9, pane);
									inv.setItem(slot + 18, pane);
								}
							}, i*2);
						}
						ItemStack pane2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)10);
						for (int i = 1; i < 5; i++) {
							Integer slot = 9 - i;
							Bukkit.getScheduler().scheduleSyncDelayedTask(Skellett.instance, new Runnable() {
								@Override
								public void run() {
									inv.setItem(slot, pane2);
									inv.setItem(slot + 9, pane2);
									inv.setItem(slot + 18, pane2);
								}
							}, i*2);
						}
						ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
						SkullMeta headMeta = (SkullMeta)head.getItemMeta();
						headMeta.setOwner(player.getName());
						headMeta.setDisplayName(Skellett.cc("&6&l" + player.getName()));
						headMeta.setLore(Arrays.asList(Skellett.cc("&5&l" + "Well hi there " + player.getName())));
						head.setItemMeta(headMeta);
						inv.setItem(13, head);		
						player.sendMessage(Skellett.cc(Skellett.prefix + "&f&lhttp://bit.ly/2h2zKWh"));
					}
				}
				if (e.getSlot() == 13) {
					player.closeInventory();
					player.openInventory(makeSyntaxMenu(null));
				}
			} else if (inv.getSize() == 54) {
				if (e.getSlot() == 49) {
					player.closeInventory();
					player.openInventory(makeSkellettMenu());
				} else if (e.getSlot() == 53) {
					page++;
					player.openInventory(makeSyntaxMenu(51 * page));
				} else if (e.getSlot() == 45) {
					page--;
					if (page < 0) {
						page = 0;
					}
					player.openInventory(makeSyntaxMenu(51 * page));
				} else {
					if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
						ItemMeta clickedMeta = e.getCurrentItem().getItemMeta();
						String[] data = clickedMeta.getDisplayName().split(" ");
						String node = "Syntax." + ChatColor.stripColor(data[0]) + "." + ChatColor.stripColor(data[1]);
						if (Skellett.syntaxToggleData.getBoolean(node)) {
							Skellett.syntaxToggleData.set(node, false);
						} else {
							Skellett.syntaxToggleData.set(node, true);
						}
						try {
							Skellett.syntaxToggleData.save(Skellett.syntaxToggleFile);
						} catch (IOException error) {
							error.printStackTrace();
						}
						String value = Skellett.syntaxToggleData.getBoolean(node) ? " &aTrue" : " &cFalse";
						if (Skellett.syntaxToggleData.getBoolean(node)) {
							ItemStack finalPane1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)5);
							ItemMeta finalPane1Meta = finalPane1.getItemMeta();
							finalPane1Meta.setDisplayName(Skellett.cc(data[0] + " " + data[1] + value));
							finalPane1.setItemMeta(finalPane1Meta);
							inv.setItem(e.getSlot(), finalPane1);
						} else {
							ItemStack finalPane1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)14);
							ItemMeta finalPane1Meta = finalPane1.getItemMeta();
							finalPane1Meta.setDisplayName(Skellett.cc(data[0] + " " + data[1] + value));
							finalPane1.setItemMeta(finalPane1Meta);
							inv.setItem(e.getSlot(), finalPane1);
						}
					}
				}
			}
		}
	}
	private Inventory makeSkellettMenu() {
		page = 0;
		Inventory inv = Bukkit.createInventory(null, 3*9, Skellett.cc("              &2&l&nSkellett"));
		List<Number> nums = Arrays.asList(0, 2, 3, 5, 6, 8, 18, 20, 21, 23, 24, 26);
		for (int i = 0; i < inv.getSize(); i++) {
			if (nums.contains(i)) {
				inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)5));
			} else {
				inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)13));
			}
		}
		ItemStack version = new ItemStack(Material.PAPER, 1);
		ItemMeta versionMeta = version.getItemMeta();
		versionMeta.setDisplayName(Skellett.cc("&a&lSkellett version"));
		versionMeta.setLore(Arrays.asList(Skellett.cc("&f" + Skellett.plugin.getDescription().getVersion())));
		version.setItemMeta(versionMeta);
		inv.setItem(10, version);
		ItemStack command = new ItemStack(Material.POWERED_MINECART, 1);
		ItemMeta commandMeta = command.getItemMeta();
		commandMeta.setDisplayName(Skellett.cc("&a&lSyntax toggles"));
		commandMeta.setLore(Arrays.asList(Skellett.cc("&fEnable/Disable syntax values"), "", Skellett.cc("&cRestart server for changes to apply")));
		command.setItemMeta(commandMeta);
		inv.setItem(13, command);
		ItemStack config = new ItemStack(Material.STORAGE_MINECART, 1);
		ItemMeta configMeta = config.getItemMeta();
		configMeta.setDisplayName(Skellett.cc("&a&lConfig toggles"));
		configMeta.setLore(Arrays.asList(Skellett.cc("&fEnable/Disable config values"), "", Skellett.cc("&cRestart server for some changes to apply"), Skellett.cc("&4Coming soon")));
		config.setItemMeta(configMeta);
		inv.setItem(16, config);
		return inv;
	}
	private Inventory makeSyntaxMenu(Integer item) {
		Inventory syntax = Bukkit.createInventory(null, 6*9, Skellett.cc("              &2&l&nSkellett"));
		Integer slot = 0 ;
		if (item == null) {
			item = 0;
		}
		Integer itemSpot = 0;
		String[] types = {"Effects", "Expressions", "Conditions", "Events"};
		out : for (String type : types) {
			Set<String> nodes = Skellett.syntaxToggleData.getConfigurationSection("Syntax." + type).getKeys(true);
			in : for (String node : nodes) {
				if (!node.contains("Syntax") && Skellett.syntaxToggleData.getConfigurationSection("Syntax." + type + "." + node) == null) {
					if (itemSpot < item) {
						itemSpot++;
						continue in;
					}
					if (Skellett.syntaxToggleData.getBoolean("Syntax." + type + "." + node)) {
						ItemStack trueItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)5);
						ItemMeta trueItemMeta = trueItem.getItemMeta();
						trueItemMeta.setDisplayName(Skellett.cc("&f" + type + " &6" + node + " &aTrue"));
						trueItem.setItemMeta(trueItemMeta);
						syntax.setItem(slot, trueItem);
					} else {
						ItemStack trueItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)14);
						ItemMeta trueItemMeta = trueItem.getItemMeta();
						trueItemMeta.setDisplayName(Skellett.cc("&f" + type + " &6" + node + " &cFalse"));
						trueItem.setItemMeta(trueItemMeta);
						syntax.setItem(slot, trueItem);
					}
					slot++;
					if (slot == 45 || slot == 49) {
						slot++;
					}
					if (slot >= syntax.getSize() - 1) {
						break out;
					}
				}
			}
		}
		ItemStack next = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
		SkullMeta nextMeta = (SkullMeta)next.getItemMeta();
		nextMeta.setOwner("MHF_ArrowRight");
		nextMeta.setDisplayName(Skellett.cc("&6&lNext &r&6(&l" + (page + 2) + "&r&6)"));
		next.setItemMeta(nextMeta);
		syntax.setItem(53, next);
		ItemStack backMenu = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta backMenuMeta = backMenu.getItemMeta();
		backMenuMeta.setDisplayName(Skellett.cc("&4&lBack to Main menu"));
		backMenu.setItemMeta(backMenuMeta);
		syntax.setItem(49, backMenu);
		if (page > 0) {
			ItemStack back = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
			SkullMeta backMeta = (SkullMeta)back.getItemMeta();
			backMeta.setOwner("MHF_ArrowLeft");
			backMeta.setDisplayName(Skellett.cc("&c&lBack &r&c(&l" + page + "&r&c)"));
			back.setItemMeta(backMeta);
			syntax.setItem(45, back);
		}
		return syntax;
	}
	
}