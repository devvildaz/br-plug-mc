package spigot.server.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import spigot.server.mobs.Clumps;

public class ClumpsCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player) {
			Player player = (Player) arg0;
			if(player.isOp()) {
				if(arg1.getName().equalsIgnoreCase("clumps")) {
					Clumps.createClumps(player.getLocation());
				}
			} else {
				player.sendMessage("You are not allowed to use this command !!!");
			}
			return true;
		} else {
			arg0.sendMessage("Only players can use that command");
			return true;
		}
	}
	
}
