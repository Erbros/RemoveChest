package net.erbros.RemoveChest;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class PlayerEventListener implements Listener {

	private RemoveChest plugin;
    
    PlayerEventListener(RemoveChest instance) {
    	plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract (PlayerInteractEvent event) {
    	if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if(plugin.trackPlayers.isEmpty() == false) {
        		if(plugin.trackPlayers.containsKey(event.getPlayer()) == true) {
        			// Lets remove the player from the hash so no confusion happens.
        			plugin.trackPlayers.remove(event.getPlayer());
        			// Did he/she point at a chest?
        			if(event.getClickedBlock().getType().compareTo(Material.CHEST) == 0 ||
        				event.getClickedBlock().getType().compareTo(Material.FURNACE) == 0 ||		
        				event.getClickedBlock().getType().compareTo(Material.DISPENSER) == 0) 
        			{
        				// Lets remove the container.
        				Block block = event.getClickedBlock();
        				// Lets open the container and empty it.
        				Chest container = (Chest) block.getState();
        				container.getInventory().clear();
        				//Is it a chest, and if so, is it a double chest?
        				if(block.getTypeId() == Material.CHEST.getId()) {
        					Chest chest = GetDoubleChest(block);
        					if(chest != null) {
        						container = (Chest) chest;
        						container.getInventory().clear();
        						chest.getBlock().setType(Material.AIR);
        					}
        				}
        				// Remove the chest clicked.
        				block.setType(Material.AIR);
        				event.getPlayer().sendMessage(ChatColor.GREEN + "[RemoveChest]" + ChatColor.WHITE + " Container was successfully removed.");
        			} else {
        				event.getPlayer().sendMessage(ChatColor.GREEN + "[RemoveChest]" + ChatColor.WHITE + " You need to click a container.");
        			}
        		}
        	}
    	}
    }
    
	//Originally stolen from Babarix. Thank you :)
    public Chest GetDoubleChest(Block block) {
		Chest chest = null;
		if (block.getRelative(BlockFace.NORTH).getTypeId() == 54) {
			chest = (Chest) block.getRelative(BlockFace.NORTH).getState();
			return chest;
		} else if (block.getRelative(BlockFace.EAST).getTypeId() == 54) {
			chest = (Chest) block.getRelative(BlockFace.EAST).getState();
			return chest;
		} else if (block.getRelative(BlockFace.SOUTH).getTypeId() == 54) {
			chest = (Chest) block.getRelative(BlockFace.SOUTH).getState();
			return chest;
		} else if (block.getRelative(BlockFace.WEST).getTypeId() == 54) {
			chest = (Chest) block.getRelative(BlockFace.WEST).getState();
			return chest;
		}
		return chest;
	}
    
}
