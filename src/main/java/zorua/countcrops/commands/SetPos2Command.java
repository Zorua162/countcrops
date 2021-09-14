package zorua.countcrops.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import zorua.countcrops.CountCrops;
import zorua.countcrops.DataStorage;

import static zorua.countcrops.Utils.center;

public class SetPos2Command implements CommandExecutor {

    private final DataStorage dataStorage;

    public SetPos2Command(CountCrops countCrops) {
        this.dataStorage = countCrops.getDataStorage();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to use this command");
            return false;
        }
        if (!(commandSender.hasPermission("countcrops.command.setpos2"))) {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return false;
        }
        Player p = (Player) commandSender;
        Location plocation = center(p.getLocation());
        String playerx = String.valueOf(plocation.getX());
        String playery = String.valueOf(plocation.getY());
        String playerz = String.valueOf(plocation.getZ());
        dataStorage.setPlayerPos2(p, plocation);
        commandSender.sendMessage("Successfully set position2 to x=" + playerx + ", y=" + playery + ", z=" + playerz);
        return true;
    }
}
