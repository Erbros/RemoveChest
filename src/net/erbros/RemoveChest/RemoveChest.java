package net.erbros.RemoveChest;

import java.util.HashMap;
import java.util.logging.Logger;
import net.erbros.RemoveChest.PlayerEventListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;

import com.nijikokun.bukkit.Permissions.Permissions;

public class RemoveChest extends JavaPlugin {
	public static PermissionHandler Permissions;
	protected final Logger log = Logger.getLogger("Minecraft");
	public HashMap<CommandSender, Boolean> trackPlayers = new HashMap<CommandSender, Boolean>();
	
	PluginManager pm = Bukkit.getServer().getPluginManager();
	PlayerEventListener pL = new PlayerEventListener(this);


	

	@Override
	public void onDisable() {
		// Disable all running timers. Just in case we have any.. (which we don't at the moment).
		Bukkit.getServer().getScheduler().cancelTasks(this);
		
		log.info(getDescription().getName() + ": has been disabled.");
	}

	@Override
	public void onEnable() {
		log.info("[" + getDescription().getName() + "] version " + getDescription().getVersion() + " is enabled." );
		pm.registerEvent(Event.Type.PLAYER_INTERACT, pL, Event.Priority.Lowest, this);
		
		getCommand("removechest").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
				if(hasPermission(sender, "removechest.remove", true) == true) {
					trackPlayers.put(sender, true);
					sender.sendMessage(ChatColor.GREEN + "[RemoveChest]" + ChatColor.WHITE + " Left click the container you wish to remove.");
				} else {
					sender.sendMessage(ChatColor.GREEN + "[RemoveChest]" + ChatColor.WHITE + " You don't have rights.");
				}
				return true;
			}
		});
		
	}
	
	// Stolen from ltguide! Thank you so much :)
	public Boolean hasPermission(CommandSender sender, String node, Boolean needOp) {
		if (!(sender instanceof Player)) return true;
	
		Player player = (Player) sender;
		if (Permissions != null) return Permissions.has(player, node);
		else {
			Plugin test = getServer().getPluginManager().getPlugin("Permissions");
			if (test != null) {
				Permissions = ((Permissions) test).getHandler();
				return Permissions.has(player, node);
			}
		}
		if(needOp) {
			return player.isOp();
		}
		return true;
	}
	

}



