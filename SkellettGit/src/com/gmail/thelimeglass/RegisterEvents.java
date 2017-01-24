package com.gmail.thelimeglass;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import com.gmail.thelimeglass.Expressions.ExprBlockXP;
import com.gmail.thelimeglass.Expressions.ExprBreedingBreeder;
import com.gmail.thelimeglass.Expressions.ExprBreedingEntity;
import com.gmail.thelimeglass.Expressions.ExprBreedingFather;
import com.gmail.thelimeglass.Expressions.ExprBreedingItem;
import com.gmail.thelimeglass.Expressions.ExprBreedingMother;
import com.gmail.thelimeglass.Expressions.ExprBreedingXP;
import com.gmail.thelimeglass.Expressions.ExprUnleashHitch;
import com.gmail.thelimeglass.Expressions.ExprUnleashReason;
import com.gmail.thelimeglass.Expressions.ExprWorldChangeFrom;
import com.gmail.thelimeglass.Utils.EnumClassInfo;
import com.gmail.thelimeglass.Utils.ExprNewMaterial;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.citizensnpcs.api.event.EntityTargetNPCEvent;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCCollisionEvent;
import net.citizensnpcs.api.event.NPCCombustByBlockEvent;
import net.citizensnpcs.api.event.NPCCombustByEntityEvent;
import net.citizensnpcs.api.event.NPCCreateEvent;
import net.citizensnpcs.api.event.NPCDamageByBlockEvent;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCDamageEntityEvent;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCEnderTeleportEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCPushEvent;
import net.citizensnpcs.api.event.NPCRemoveEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.event.NPCSelectEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.event.NPCTeleportEvent;
import net.citizensnpcs.api.event.PlayerCreateNPCEvent;

public class RegisterEvents {
	
	@SuppressWarnings("rawtypes")
	private static List<Class> classes = new ArrayList<>();
	
	public static void register(){
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.FireworkExplode")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				registerEvent(FireworkExplodeEvent.class, "firework explo(de|sion)");
				EventValues.registerEventValue(FireworkExplodeEvent.class, Entity.class, new Getter<Entity, FireworkExplodeEvent>() {
					@Override
					public Entity get(FireworkExplodeEvent e) {
						return e.getEntity();
					}
				}, 0);
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.ItemDespawn")) {
			registerEvent(ItemDespawnEvent.class, "item[ ][stack] (despawn|remove|delete)");
			EventValues.registerEventValue(ItemDespawnEvent.class, Entity.class, new Getter<Entity, ItemDespawnEvent>() {
				@Override
				public Entity get(ItemDespawnEvent e) {
					return e.getEntity();
				}
			}, 0);
			EventValues.registerEventValue(ItemDespawnEvent.class, Location.class, new Getter<Location, ItemDespawnEvent>() {
				@Override
				public Location get(ItemDespawnEvent e) {
					return e.getLocation();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.ItemMerge")) {
			if (!Bukkit.getVersion().contains("1.7") && !Bukkit.getVersion().contains("1.8")) {
				registerEvent(ItemMergeEvent.class, "item[ ][stack] (merge|combine[d])");
				EventValues.registerEventValue(ItemMergeEvent.class, Entity.class, new Getter<Entity, ItemMergeEvent>() {
					public Entity get(ItemMergeEvent e) {
						return e.getEntity();
					}
				}, 0);
				EventValues.registerEventValue(ItemMergeEvent.class, Item.class, new Getter<Item, ItemMergeEvent>() {
					@Override
					public Item get(ItemMergeEvent e) {
						return e.getTarget();
					}
				}, 0);
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "The item merge event is only for 1.9+ versions!"));
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.MultiBlockPlace")) {
			registerEvent(BlockMultiPlaceEvent.class, "(multi[ple]|double)[ ][block][ ]place");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.ArrowPickup")) {
			if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
				registerEvent(PlayerPickupArrowEvent.class, "arrow pickup");
				EventValues.registerEventValue(PlayerPickupArrowEvent.class, Arrow.class, new Getter<Arrow, PlayerPickupArrowEvent>() {
					@Override
					public Arrow get(PlayerPickupArrowEvent e) {
						return e.getArrow();
					}
				}, 0);
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "The arrow pickup event is only for 1.8+ versions!"));
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.OffhandSwitch")) {
			if (Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
				registerEvent(PlayerSwapHandItemsEvent.class, "off[ ]hand (switch|move)");
				EventValues.registerEventValue(PlayerSwapHandItemsEvent.class, ItemStack.class, new Getter<ItemStack, PlayerSwapHandItemsEvent>() {
					@Override
					public ItemStack get(PlayerSwapHandItemsEvent e) {
						return e.getMainHandItem();
					}
				}, 0);
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "The offhand switch event is only for 1.9+ versions!"));
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.CreativeInventoryClick")) {
			registerEvent(InventoryCreativeEvent.class, "creative inventory click");
			EventValues.registerEventValue(InventoryCreativeEvent.class, ItemStack.class, new Getter<ItemStack, InventoryCreativeEvent>() {
				@Override
				public ItemStack get(InventoryCreativeEvent e) {
					return e.getCursor();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.EntityTeleport")) {
			registerEvent(EntityTeleportEvent.class, "entity teleport");
			EventValues.registerEventValue(EntityTeleportEvent.class, Location.class, new Getter<Location, EntityTeleportEvent>() {
				@Override
				public Location get(EntityTeleportEvent e) {
					return e.getTo();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.EntityTeleport")) {
			registerEvent(EntityTeleportEvent.class, "entity teleport");
			EventValues.registerEventValue(EntityTeleportEvent.class, Location.class, new Getter<Location, EntityTeleportEvent>() {
				@Override
				public Location get(EntityTeleportEvent e) {
					return e.getTo();
				}
			}, 1);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.VehicleMove")) {
			registerEvent(VehicleMoveEvent.class, "(vehicle|minecart|boat) move");
			EventValues.registerEventValue(VehicleMoveEvent.class, Location.class, new Getter<Location, VehicleMoveEvent>() {
				@Override
				public Location get(VehicleMoveEvent e) {
					return e.getFrom();
				}
			}, -1);
			EventValues.registerEventValue(VehicleMoveEvent.class, Location.class, new Getter<Location, VehicleMoveEvent>() {
				@Override
				public Location get(VehicleMoveEvent e) {
					return e.getTo();
				}
			}, 1);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.EntityBlockChange")) {
			registerEvent(EntityChangeBlockEvent.class, "entity block (change|modify)");
			Skript.registerExpression(ExprNewMaterial.class, Material.class, ExpressionType.SIMPLE, "[skellett] new [changed] material");
			EventValues.registerEventValue(EntityChangeBlockEvent.class, Block.class, new Getter<Block, EntityChangeBlockEvent>() {
				@Override
				public Block get(EntityChangeBlockEvent e) {
					return e.getBlock();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.HotbarSwitch")) {
			registerEvent(PlayerItemHeldEvent.class, "(hotbar|held [(item|slot)]|inventory slot) (switch|change)");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.Breeding")) {
			if (!Bukkit.getVersion().contains("1.6") || !Bukkit.getVersion().contains("1.7") || !Bukkit.getVersion().contains("1.8") || !Bukkit.getVersion().contains("1.9") || !Bukkit.getVersion().contains("1.10")) {
				registerEvent(EntityBreedEvent.class, "bre[e]d[ing]");
				Skript.registerExpression(ExprBreedingItem.class, ItemStack.class, ExpressionType.SIMPLE, "bre[e]d[ing] (item|material) [used]");
				Skript.registerExpression(ExprBreedingBreeder.class, LivingEntity.class, ExpressionType.SIMPLE, "breeder");
				Skript.registerExpression(ExprBreedingEntity.class, LivingEntity.class, ExpressionType.SIMPLE, "[final] bre[e]d[ed] entity");
				Skript.registerExpression(ExprBreedingXP.class, Number.class, ExpressionType.SIMPLE, "bre[e]d[ing] (xp|experience)");
				Skript.registerExpression(ExprBreedingFather.class, LivingEntity.class, ExpressionType.SIMPLE, "bre[e]d[ing] father");
				Skript.registerExpression(ExprBreedingMother.class, LivingEntity.class, ExpressionType.SIMPLE, "bre[e]d[ing] mother");
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "The breeding event is only for 1.10+ versions!"));
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.WorldChange")) {
			registerEvent(PlayerChangedWorldEvent.class, "[player] world change");
			Skript.registerExpression(ExprWorldChangeFrom.class, World.class, ExpressionType.SIMPLE, "(previous|past) [changed] world");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.CropGrow")) {
			registerEvent(BlockGrowEvent.class, "(block|crop) grow[ing]");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.BlockExperienceDrop")) {
			registerEvent(BlockExpEvent.class, "block [break] (xp|exp|experience) [drop]");
			Skript.registerExpression(ExprBlockXP.class, Number.class,ExpressionType.SIMPLE, "[dropped] block[[']s] (xp|experience)");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.EntityUnleash")) {
			registerEvent(EntityUnleashEvent.class, "[entity] (un(leash|lead)|(leash|lead) break)");
			EventValues.registerEventValue(EntityUnleashEvent.class, Block.class, new Getter<Block, EntityUnleashEvent>() {
				@Override
				public Block get(EntityUnleashEvent e) {
					if (((LivingEntity) e.getEntity()).isDead()) {
						return null;
					}
					return ((LivingEntity) e.getEntity()).getLeashHolder().getLocation().getBlock();
				}
			}, 0);
			EnumClassInfo.create(EntityUnleashEvent.UnleashReason.class, "unleashreason").register();
			Skript.registerExpression(ExprUnleashReason.class, EntityUnleashEvent.UnleashReason.class, ExpressionType.SIMPLE, "(un(leash|lead)|(leash|lead) break) reason");
			Skript.registerExpression(ExprUnleashHitch.class, Entity.class, ExpressionType.SIMPLE, "event-hitch");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.Elytra")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8") && !Bukkit.getServer().getVersion().contains("MC: 1.9")) {
				registerEvent(EntityToggleGlideEvent.class, "[entity] (elytra|glide) [toggle]");
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.BrewingFuel")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8") && !Bukkit.getServer().getVersion().contains("MC: 1.9") && !Bukkit.getServer().getVersion().contains("MC: 1.10") && !Bukkit.getServer().getVersion().contains("MC: 1.11)") && !Bukkit.getServer().getVersion().contains("MC: 1.11.1")) {
				registerEvent(BrewingStandFuelEvent.class, "brew[ing] [stand] fuel [increase]");
				EventValues.registerEventValue(BrewingStandFuelEvent.class, Number.class, new Getter<Number, BrewingStandFuelEvent>() {
					@Override
					public Number get(BrewingStandFuelEvent e) {
						return e.getFuelPower();
					}
				}, 0);
				EventValues.registerEventValue(BrewingStandFuelEvent.class, ItemStack.class, new Getter<ItemStack, BrewingStandFuelEvent>() {
					@Override
					public ItemStack get(BrewingStandFuelEvent e) {
						return e.getFuel();
					}
				}, 0);
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.AnvilPrepare")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				registerEvent(PrepareAnvilEvent.class, "([item] anvil prepare|prepare [item] anvil)");
				Classes.registerClass(new ClassInfo<AnvilInventory>(AnvilInventory.class, "anvilinventory")
					.name("anvilinventory")
					.description("A getter for AnvilInventory")
					.parser(new Parser<AnvilInventory>() {
						@Override
						@Nullable
						public AnvilInventory parse(String obj, ParseContext context) {
							return null;
						}
						@Override
						public String toString(AnvilInventory e, int flags) {
							return e.toString();
						}
						@Override
						public String toVariableNameString(AnvilInventory b) {
							return b.toString();
						}
						public String getVariableNamePattern() {
							return ".+";
					}}));
				EventValues.registerEventValue(PrepareAnvilEvent.class, ItemStack.class, new Getter<ItemStack, PrepareAnvilEvent>() {
					@Override
					public ItemStack get(PrepareAnvilEvent e) {
						return e.getResult();
					}
				}, 0);
				EventValues.registerEventValue(PrepareAnvilEvent.class, Number.class, new Getter<Number, PrepareAnvilEvent>() {
					@Override
					public Number get(PrepareAnvilEvent e) {
						return e.getInventory().getRepairCost();
					}
				}, 0);
				EventValues.registerEventValue(PrepareAnvilEvent.class, String.class, new Getter<String, PrepareAnvilEvent>() {
					@Override
					public String get(PrepareAnvilEvent e) {
						return e.getInventory().getRenameText();
					}
				}, 0);
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.Resurrect")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8") && !Bukkit.getServer().getVersion().contains("MC: 1.9") && !Bukkit.getServer().getVersion().contains("MC: 1.10")) {
				registerEvent(EntityResurrectEvent.class, "[entity] (resurrect|revive)");
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.SlimeSplit")) {
			registerEvent(SlimeSplitEvent.class, "slime split");
		}
		if (Skellett.instance.getConfig().getBoolean("PluginHooks.Npc")) {
			registerEvent(NPCDamageByEntityEvent.class, "(npc|citizen) damage (by|from) [a[n]] entity");
			EventValues.registerEventValue(NPCDamageByEntityEvent.class, Entity.class, new Getter<Entity, NPCDamageByEntityEvent>() {
				@Override
				public Entity get(NPCDamageByEntityEvent e) {
					return e.getDamager();
				}
			}, 0);
			EventValues.registerEventValue(NPCDamageByEntityEvent.class, String.class, new Getter<String, NPCDamageByEntityEvent>() {
				@Override
				public String get(NPCDamageByEntityEvent e) {
					return e.getCause().toString();
				}
			}, 0);
			registerEvent(NPCDamageByBlockEvent.class, "(npc|citizen) damage (by|from) [a] block");
			EventValues.registerEventValue(NPCDamageByBlockEvent.class, Block.class, new Getter<Block, NPCDamageByBlockEvent>() {
				@Override
				public Block get(NPCDamageByBlockEvent e) {
					return e.getDamager();
				}
			}, 0);
			registerEvent(EntityTargetNPCEvent.class, "entity target (npc|citizen)");
			EventValues.registerEventValue(EntityTargetNPCEvent.class, Entity.class, new Getter<Entity, EntityTargetNPCEvent>() {
				@Override
				public Entity get(EntityTargetNPCEvent e) {
					return e.getEntity();
				}
			}, 0);
			registerEvent(NPCClickEvent.class, "(npc|citizen) click");
			registerEvent(NPCLeftClickEvent.class, "(npc|citizen) left[(-| )]click");
			registerEvent(NPCRightClickEvent.class, "(npc|citizen) right[(-| )]click");
			registerEvent(NPCCollisionEvent.class, "(npc|citizen) [entity] colli(sion|de)");
			EventValues.registerEventValue(NPCCollisionEvent.class, Entity.class, new Getter<Entity, NPCCollisionEvent>() {
				@Override
				public Entity get(NPCCollisionEvent e) {
					return e.getCollidedWith();
				}
			}, 0);
			registerEvent(NPCCombustByBlockEvent.class, "(npc|citizen) (combust[ion]|ignition) (by|from) [a] block");
			EventValues.registerEventValue(NPCCombustByBlockEvent.class, Block.class, new Getter<Block, NPCCombustByBlockEvent>() {
				@Override
				public Block get(NPCCombustByBlockEvent e) {
					return e.getCombuster();
				}
			}, 0);
			registerEvent(NPCCombustByBlockEvent.class, "(npc|citizen) (combust[ion]|ignition) (by|from) [a[n]] entity");
			EventValues.registerEventValue(NPCCombustByEntityEvent.class, Entity.class, new Getter<Entity, NPCCombustByEntityEvent>() {
				@Override
				public Entity get(NPCCombustByEntityEvent e) {
					return e.getCombuster();
				}
			}, 0);
			registerEvent(NPCCreateEvent.class, "(npc|citizen) create");
			registerEvent(NPCDamageEntityEvent.class, "(npc|citizen) entity damage");
			EventValues.registerEventValue(NPCDamageEntityEvent.class, Entity.class, new Getter<Entity, NPCDamageEntityEvent>() {
				@Override
				public Entity get(NPCDamageEntityEvent e) {
					return e.getDamaged();
				}
			}, 0);
			//TODO Death: Dropped items and XP
			registerEvent(NPCDeathEvent.class, "(npc|citizen) death");
			registerEvent(NPCDespawnEvent.class, "(npc|citizen) despawn");
			registerEvent(NPCEnderTeleportEvent.class, "(npc|citizen) ender[[ ]pearl] [teleport]");
			//TODO: Return and settable vector
			registerEvent(NPCPushEvent.class, "(npc|citizen) (push|(vector|velocity) change)");
			registerEvent(NPCRemoveEvent.class, "(npc|citizen) remove");
			//TODO: Trait events
			registerEvent(NPCSelectEvent.class, "(npc|citizen) sel[ect[ed]]");
			EventValues.registerEventValue(NPCSelectEvent.class, Player.class, new Getter<Player, NPCSelectEvent>() {
				@Override
				public Player get(NPCSelectEvent e) {
					if (Bukkit.getPlayer(e.getSelector().getName()) != null) {
						return Bukkit.getPlayer(e.getSelector().getName());
					}
					return null;
				}
			}, 0);
			registerEvent(NPCSpawnEvent.class, "(npc|citizen) spawn");
			EventValues.registerEventValue(NPCSpawnEvent.class, Location.class, new Getter<Location, NPCSpawnEvent>() {
				@Override
				public Location get(NPCSpawnEvent e) {
					return e.getLocation();
				}
			}, 0);
			//TODO: Get from and to location
			registerEvent(NPCTeleportEvent.class, "(npc|citizen) teleport");
			EventValues.registerEventValue(NPCTeleportEvent.class, Location.class, new Getter<Location, NPCTeleportEvent>() {
				@Override
				public Location get(NPCTeleportEvent e) {
					return e.getTo();
				}
			}, 0);
			registerEvent(PlayerCreateNPCEvent.class, "player create (npc|citizen)");
			EventValues.registerEventValue(PlayerCreateNPCEvent.class, Player.class, new Getter<Player, PlayerCreateNPCEvent>() {
				@Override
				public Player get(PlayerCreateNPCEvent e) {
					return e.getCreator();
				}
			}, 0);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Class> getClasses() {
		return classes;
	}
	
	@SuppressWarnings("rawtypes")
	public static void add(Class c) {
		classes.add(c);
	}
	
	@SuppressWarnings("unchecked")
	public static void registerEvent(@SuppressWarnings("rawtypes") Class clazz, String syntax) {
		Skript.registerEvent(syntax, SimpleEvent.class, clazz, "[skellett] " + syntax);
		classes.add(clazz);
	}
	
	public static void dump() {
		classes.clear();
	}
}