package me.treestompz.StompzMainCore;



import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;



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
}