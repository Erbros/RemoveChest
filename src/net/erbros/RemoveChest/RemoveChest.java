package net.erbros.RemoveChest;

import java.util.HashMap;
import java.util.logging.Logger;
import net.erbros.RemoveChest.PlayerEventListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RemoveChest extends JavaPlugin {
	protected final Logger log = Logger.getLogger("Minecraft");
	public HashMap<CommandSender, Boolean> trackPlayers = new HashMap<CommandSender, Boolean>();
	
	PluginManager pm = Bukkit.getServer().getPluginManager();
	PlayerEventListener pL = new PlayerEventListener(this);


	

	@Override
	public void onDisable() {
		// Disable all running timers. Just in case we have any.. (which we don't at the moment).
		Bukkit.getServer().getScheduler().cancelTasks(this);
	}

	@Override
	public void onEnable() {
		pm.registerEvents(pL, this);
		
		getCommand("removechest").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
				
			    
			    if(sender.hasPermission("removechest.remove") || sender.isOp()) {
					trackPlayers.put(sender, true);
					sender.sendMessage(ChatColor.GREEN + "[RemoveChest]" + ChatColor.WHITE + " Left click the container you wish to remove.");
				} else {
					sender.sendMessage(ChatColor.GREEN + "[RemoveChest]" + ChatColor.WHITE + " You don't have rights.");
				}
				return true;
			}
		});
		
	}
}



