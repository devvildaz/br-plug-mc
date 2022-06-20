package spigot.server.mobs;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;

import spigot.server.PluginApp;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

// import net.minecraft.server.v1_15_R1.EntityTypes;



public class Clumps implements Listener{

	static PluginApp plugin;
	
	public Clumps(PluginApp plugin) {
		this.plugin = plugin;
	}
	
	public static void createClumps(Location location) {
		Spider vill = location.getWorld().spawn(location, Spider.class);
		vill.setCustomName(ChatColor.DARK_GRAY + "Clumps");
		vill.setCustomNameVisible(true);
		
		Attributable villAt = vill;
		AttributeInstance attribute = 
				villAt.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		attribute.setBaseValue(100);
		vill.setHealth(100);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(!vill.isDead()) {
					if(vill.getTarget() == null) {
						for(Entity entity : vill.getNearbyEntities(10,10,10)) {
							if(entity instanceof Player) {
								Player player = (Player) entity;
								vill.setTarget(player);
							}
						}
					} else {
						LivingEntity target = vill.getTarget();
						if(target.getLocation().distanceSquared(vill.getLocation()) > 25) {
							vill.getWorld().playSound(vill.getLocation(), Sound.ENTITY_WITHER_SHOOT, 5, 5);
							vill.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, vill.getLocation(), 10);
							vill.setVelocity(
									target.getLocation().add(0,2,0).subtract(
											vill.getLocation()
									).toVector().multiply(0.275)
								);
						}
					}
				} else {
					cancel();
				}
			}
		}.runTaskTimer(plugin, 0L, 20L);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Spider) {
			Entity test = event.getDamager();
			if(
					test.getCustomName() != null &&
					test.getCustomName().equals(ChatColor.DARK_GRAY + "Clumps")
			) {
				if(event.getEntity() instanceof Player) {
					Player player = (Player) event.getEntity();
					player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 0));
				}
			}
		}
	}
	
}
