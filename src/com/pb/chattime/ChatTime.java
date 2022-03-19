package com.pb.chattime;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatTime extends JavaPlugin implements Listener {
	FileConfiguration config = this.getConfig();
	
	@Override
    public void onEnable() {
		config.options().copyDefaults(true);
		this.saveConfig();
		
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if(player == null) return;
		String msg = event.getMessage();
		
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss");
		String time = formatter.format(currentDate);
		
		// Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a [\"[" + time + "] [\",{\"color\":\"green\",\"text\":\"" + player.getLocation().getWorld().getName().replaceAll("\"", "\\\"") + "\"},{\"color\":\"white\",\"text\":\"] " + player.getName() + ": " + msg.replaceAll("\"", "\\\"").replaceAll("[&]", "¡×") + "\"}]");
		
		String format = config.getString("format");
		format = format.replaceAll("[$]TIME", time);
		format = format.replaceAll("[$]WORLD", player.getLocation().getWorld().getName());
		format = format.replaceAll("[$]PR_PREFIX", "");
		format = format.replaceAll("[$]PR_SUBPREFIX", "");
		format = format.replaceAll("[$]PR_SUBSUFFIX", "");
		format = format.replaceAll("[$]PR_SUFFIX", "");
		format = format.replaceAll("[$]USERNAME", player.getName());
		format = format.replaceAll("[$]MESSAGE", msg);
		
		event.setFormat(ChatColor.translateAlternateColorCodes('&', format));
	}

    @Override
    public void onDisable() {
		
    }
}

