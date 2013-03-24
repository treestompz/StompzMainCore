package me.treestompz.StompzMainCore;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StompzMainCore extends JavaPlugin 
{
	public void onEnable()
	{		
		Bukkit.getPluginManager().registerEvents(new StompzMainCoreListener() , this);

	}

	public void onDisable()
	{

	}
	ArrayList<String> playersMeow = new ArrayList<String>();
	ArrayList<String> meowDisabledList = new ArrayList<String>();
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("meow"))
		{			
			if(sender instanceof Player)
			{
				final Player player = (Player) sender;
				if(player.hasPermission("stompz.meow"))
				{
					if(args.length == 0)
					{
						player.sendMessage(ChatColor.GREEN + "StompzMainCore 0.2: " + ChatColor.AQUA + "/meow" + ChatColor.GREEN + " Plugin author: " + ChatColor.AQUA + "treestompz");
						player.sendMessage(ChatColor.RED + "Please use: /meow <playername>");
						player.sendMessage(ChatColor.RED + "Or, to toggle meowing: /meowtoggle");
					}
					if(args.length == 1)						
					{					
						if(!playersMeow.contains(player.getName()))
						{												
							if(!meowDisabledList.contains(player.getName()))
							{
								Player target = getServer().getPlayer(args[0]);	
								if(target != null)
								{	
									if(meowDisabledList.contains(target.getName()))
									{
										player.sendMessage(ChatColor.RED + "That player has meowing disabled :(");	
										return true;
									}
									else
									{
										playersMeow.add(player.getName());
										player.sendMessage(ChatColor.LIGHT_PURPLE + "You meow'd " + target.getName() + "!");
										target.sendMessage(ChatColor.LIGHT_PURPLE + "You have been meow'd by " + sender.getName() + "! /meowtoggle to disable meowing.");	
										target.playSound(target.getLocation(), Sound.CAT_MEOW, 10, 1);
										Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
											public void run()
											{
												playersMeow.remove(player.getName());
												player.sendMessage(ChatColor.GREEN + "Meow cooldown complete.");
												player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
											}								
										}, 20 * 5);
									}
								}
								else
								{
									player.sendMessage(ChatColor.RED + "That player is not online or is not a valid username!");	
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "To /meow again, use /meowtoggle");	
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + "Your /meow is still cooling down! Meow wisely, young kitteh!");	
						}
					}
					return true;
				}
			}			
		} 
		if(cmd.getName().equalsIgnoreCase("meowtoggle"))
		{
			if(sender instanceof Player)
			{
				if(sender.hasPermission("stompz.meow"))
				{
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
