package me.treestompz.StompzMainCore;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin to add fun things to the StompzCraft Main/Survival Server
 * 
 * @author treestompz
 * @version 0.2
 * 
 * Lots of commenting to help new programmers on http://stompzcraft.com/forum/m/3207642/viewforum/928324
 */


public class StompzMainCore extends JavaPlugin 
{

	public void onEnable()
	{		
		// Register Listener
		Bukkit.getPluginManager().registerEvents(new StompzMainCoreListener(this) , this);
	}

	public void onDisable()
	{

	}

	// ArrayList used to store player's usernames
	ArrayList<String> playersMeow = new ArrayList<String>();
	ArrayList<String> meowDisabledList = new ArrayList<String>();
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{	
		if(cmd.getName().equalsIgnoreCase("meow"))
		{	
			// Check if sender is a player
			if(sender instanceof Player)
			{
				// Cast sender as a Player
				final Player player = (Player) sender;
				// If player has the permission node "stompz.meow"
				if(player.hasPermission("stompz.meow"))
				{
					// If the command has not arguments
					if(args.length == 0)
					{
						player.sendMessage(ChatColor.GREEN + "StompzMainCore 0.2: " + ChatColor.AQUA + "/meow" + ChatColor.GREEN + " Plugin author: " + ChatColor.AQUA + "treestompz");
						player.sendMessage(ChatColor.RED + "Please use: /meow <playername>");
						player.sendMessage(ChatColor.RED + "Or, to toggle meowing: /meowtoggle");
					}
					// 2 if 4 u
					// Targetting a meow victim :D
					// Remember, "player" is the person running the command, "target" 
					if(args.length == 1)						
					{		
						// If the player running the command is not on cooldown
						if(!playersMeow.contains(player.getName()))
						{												
							// If the player running the command has meowing disabled. Not to be confused with the target player!
							if(!meowDisabledList.contains(player.getName()))
							{
								// The target is the first argument
								Player target = getServer().getPlayer(args[0]);									
								// If the target is a valid online player
								if(target != null)
								{	
									// If the target player has meowing disabled
									if(meowDisabledList.contains(target.getName()))
									{
										player.sendMessage(ChatColor.RED + "That player has meowing disabled :(");	
										return true;
									}
									// If the target player does not have meowing disabled
									else
									{
										// Add the player
										playersMeow.add(player.getName());
										// Send a message to the player that the meow was successful
										player.sendMessage(ChatColor.LIGHT_PURPLE + "You meow'd " + target.getName() + "!");
										// Send a message to the target that he has been meowed by player AND play the meow sound
										target.sendMessage(ChatColor.LIGHT_PURPLE + "You have been meow'd by " + sender.getName() + "! /meowtoggle to disable meowing.");	
										target.playSound(target.getLocation(), Sound.CAT_MEOW, 10, 1);
										// Start the /meow cooldown
										Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
										{
											public void run()
											{
												playersMeow.remove(player.getName());
												player.sendMessage(ChatColor.GREEN + "Meowdown complete.");
												player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
											}				
											// 60 second cooldown
										}, 20 * 60);
									}
								}
								// If the target is NOT a valid online player
								else
								{
									player.sendMessage(ChatColor.RED + "That player is not online or is not a valid username!");	
								}
							}
							// If the player has meowing disabled
							else
							{
								player.sendMessage(ChatColor.RED + "To /meow again, use /meowtoggle");	
							}
						}
						// If the player has tried to meow again when the cooldown is still running
						else
						{
							player.sendMessage(ChatColor.RED + "Your /meow is still meowingdown! Meow wisely, young kitteh!");	
						}
					}
					return true;
				}
			}			
		} 
		// If the player types /meowtoggle
		if(cmd.getName().equalsIgnoreCase("meowtoggle"))
		{
			// If the command sender is a Player
			if(sender instanceof Player)
			{
				// If the sender has the permission "stompz.meow"
				if(sender.hasPermission("stompz.meow"))
				{
					// Toggling of being able to be meow
					if(!meowDisabledList.contains(sender.getName()))
					{
						sender.sendMessage(ChatColor.RED + "People can no longer /meow you :( Use /meowtoggle to enable!");
						meowDisabledList.add(sender.getName());
						return true;
					}
					if(meowDisabledList.contains(sender.getName()))
					{
						sender.sendMessage(ChatColor.GREEN + "People can /meow you again! Use /meowtoggle to disable!");
						meowDisabledList.remove(sender.getName());
						return true;
					}
				}

			}
		}
		return false; 

	}

}
