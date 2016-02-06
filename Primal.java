package me.Dustin.com;


import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
	public static Permission perms = null;

	public static Plugin instance;

	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&m----------------------------"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[SimpleVote] has been enabled!"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e      Created by SpeedQuickCoding"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&m----------------------------"));
		instance = this;
		setupPermissions();
		saveDefaultConfig();
	}
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&m----------------------------"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[SimpleVote] has been disabled!"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&m----------------------------"));
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}



	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("vote")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4ERROR: &cYou must be a player to execute this command!"));
				return true;
			}
			Player p = (Player) sender;
			if(args.length == 0) {
				if(getConfig().getStringList("votelinks") != null){
					String top = "" + getConfig().getString("Prefixtop") + "";
					String bottom = "" + getConfig().getString("Prefixbottom") + "";
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', top));
					int counter = 0;
					for(String link : getConfig().getStringList("votelinks")){
						counter ++;
						p.sendMessage(counter + ". "+ ChatColor.translateAlternateColorCodes('&',link));
					}
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', bottom));
					return true;
				}else{
					p.sendMessage(ChatColor.RED + "There are no configured voting links! please alert the server administration");
					return true;
				}	
			}else if(args[0].equalsIgnoreCase("reload")) {
				if(Core.perms.playerHas(p, "simplevote.admin")) {
					reloadConfig();
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&m--------------------------------------"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[SimpleVote] &aYou have sucessfully reloaded the config! "));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&m--------------------------------------"));
				} else {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&m---------------------------------------"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[SimpleVote] &cYou don't have permission to reload this config! "));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&m----------------------------------------"));
				}

			} else if(args[0].equalsIgnoreCase("stats")) {
				String pUUID = p.getUniqueId().toString();
				if(getConfig().getStringList("Players." + pUUID + "votes") != null){
					return false;
				}
			}
		}
		return false;
	}
}
