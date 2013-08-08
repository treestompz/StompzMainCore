package me.treestompz.StompzMainCore;



import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;



public class StompzMainCoreListener implements Listener
{

	private StompzMainCore plugin;
	private ArrayList<String> commandCooldown;

	public StompzMainCoreListener(StompzMainCore plugin)
	{
		this.plugin = plugin;     
		commandCooldown = new ArrayList<String>();
	}

	// When a player uses a bucket
	@EventHandler (priority = EventPriority.MONITOR)
	public void blockplaceevent(PlayerBucketEmptyEvent event)
	{
		// If the bucket is a lava bucket AND the player does not have the permission "stompz.placelava"
		if(event.getBucket() == Material.LAVA_BUCKET && !event.getPlayer().hasPermission("stompz.placelava"))
		{
			// Cancel the event of dropping lava
			event.setCancelled(true);
			// Alert the player
			event.getPlayer().sendMessage(ChatColor.RED + "Non-donators cannot place lava because many new players use it to grief and kill other players! It's the sad Minecraft world we live in :(" + ChatColor.GREEN + " If you'd like to consider donating for tons of other perks, check out: " + ChatColor.AQUA + "http://stompzcraft.com/shop");
		}
	}

	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		// Avoid out of bounds exception
		if(event.getMessage().length() > 6)
		{
			final Player p = event.getPlayer();
			if(event.getMessage().substring(0, 7).equalsIgnoreCase("/ps add"))
			{
				if(!commandCooldown.contains(p.getName()))
				{
					commandCooldown.add(event.getPlayer().getName());
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
					{
						public void run()
						{
							commandCooldown.remove(p.getName());
						}
					}
					, 20 * 20);
				}
				else
				{
					event.setCancelled(true);
					p.sendMessage(ChatColor.RED + "You are sending that command too often!");
				}
			}
		}
	}
}