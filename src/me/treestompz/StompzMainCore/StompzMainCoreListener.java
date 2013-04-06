package me.treestompz.StompzMainCore;



import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;



public class StompzMainCoreListener implements Listener
{

	public static StompzMainCoreListener instance;

	public StompzMainCoreListener()
	{
		instance = this;        
	}

	
	@EventHandler (priority = EventPriority.MONITOR)
	//When a player uses a bucket
	public void blockplaceevent(PlayerBucketEmptyEvent event)
	{
		//If the bucket is a lava bucket AND the player does not have the permission "stompz.placelava"
		if(event.getBucket() == Material.LAVA_BUCKET && !event.getPlayer().hasPermission("stompz.placelava"))
		{
			//Cancel the event of dropping lava
			event.setCancelled(true);
			//Alert the player
			event.getPlayer().sendMessage(ChatColor.RED + "Non-donators cannot place lava because many new players use it to grief and kill other players! It's the sad Minecraft world we live in :(" + ChatColor.GREEN + " If you'd like to consider donating for tons of other perks, check out: " + ChatColor.AQUA + "http://stompzcraft.com/shop");
		}
	}
	/*Messing with fishing-not implemented
	@EventHandler
	public void onFish(PlayerFishEvent event)
	{
		if(event.getState() == State.FAILED_ATTEMPT)
		{
			event.getPlayer().sendMessage(ChatColor.RED + "That feels a littly heavy...");			
			List<Entity> nearby = event.getPlayer().getNearbyEntities(50,50,50); // searches in a 100*100*100 radius around the player for other entities 
			Entity hook = null; // holds the future hook
			for (Entity e : nearby)
			{ // loop through entities
			    if (e.getType() == EntityType.FISHING_HOOK)
			    { // it is a hook!
			        hook = e;
			        break;
			    }
			}
			if (hook != null)
			{
			    Location hookLocation = hook.getLocation(); // the location of the hook
			    event.getPlayer().getWorld().spawnEntity(hookLocation, EntityType.SQUID);
			} 
			
		}
	}*/
}