package zorua.countcrops.commands;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import zorua.countcrops.CountCrops;
import zorua.countcrops.DataStorage;

public class StartCountCommand implements CommandExecutor {

    private DataStorage dataStorage;

    public StartCountCommand(CountCrops countCrops) {
        this.dataStorage = countCrops.getDataStorage();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to use this command");
            return false;
        }
        if (!(commandSender.hasPermission("countcrops.command.startcount"))) {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        Player p = (Player) commandSender;
        Location pos1 = dataStorage.getPlayerPos1().get(p);
        Location pos2 = dataStorage.getPlayerPos2().get(p);

        commandSender.sendMessage(String.format("%sStarting counting for region pos1: %s to pos2: %s",
                ChatColor.LIGHT_PURPLE,
                formatPos(pos1),
                formatPos(pos2)));

        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("countcrops");
        int countTime = 10;
        assert plugin != null;
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> countWheat(dataStorage, p), 0, countTime *20);

        return true;
    }

    private String formatPos(Location pos) {
        return "x=" + pos.getX() + ", y=" + pos.getY() + ", z=" + pos.getZ();
    }

    private void countWheat(DataStorage dataStorage, Player player) {
        Integer wheatCount;
        Integer countCount;
        wheatCount = dataStorage.getWheatCount().getOrDefault(player, 0);
        countCount = dataStorage.getCountCount().getOrDefault(player, 0);
        Location pos1 = dataStorage.getPlayerPos1().get(player);
        Location pos2 = dataStorage.getPlayerPos2().get(player);
        World world = player.getWorld();
        Location checkLoc = new Location(world, pos1.getX(), pos1.getY(), pos1.getZ());

        Integer startX =  getMin(pos1.getX(), pos2.getX());
        Integer endX = getMax(pos1.getX(), pos2.getX());

        Integer startY =  getMin(pos1.getY(), pos2.getY());
        Integer endY = getMax(pos1.getY(), pos2.getY());

        Integer startZ =  getMin(pos1.getZ(), pos2.getZ());
        Integer endZ = getMax(pos1.getZ(), pos2.getZ());
        player.sendMessage(startX + " " + endX);
        player.sendMessage(startY + " " + endY);
        player.sendMessage(startZ + " " + endZ);

        for (int x = startX; x <= endX + 1; x++) {
            checkLoc.setX(x);
            for (int y = startY; y <= endY + 1; y++) {
                checkLoc.setY(y);
                for (int z = startZ; z <= endZ + 1; z++) {
                    checkLoc.setZ(z);
                    Block block = world.getBlockAt(checkLoc);
                    Material material = block.getBlockData().getMaterial();
                    // player.sendMessage("Material " + material + x + " " + y + " " + z);
                    if (material == Material.WHEAT) {
                        // player.sendMessage("Found wheat at " + x + " " + y + " " + z);
                        Ageable ageable = (Ageable) block.getBlockData();
                        // For the first pass set all the wheat age to 0
                        if (countCount == 0){
                            ageable.setAge(0);
                            block.setBlockData(ageable);
                        } else {
                            if (ageable.getAge() == 7) {
                                wheatCount++;
                                ageable.setAge(0);
                                block.setBlockData(ageable);
                            }
                        }
                    }
                }
            }
        }
        if (countCount == 0) {
            player.sendMessage("Resetting on first pass");
        } else {
            player.sendMessage("Counted " + wheatCount + " Wheat. Count pass: " + countCount + "");
        }
        countCount++;
        dataStorage.setPlayerCountCount(player, countCount);
        dataStorage.setPlayerWheatCount(player, wheatCount);
    }
    public Integer getMin(double X1, double X2) {
        if (X1 > X2) {
            return (int) X2;
        } else {
            return (int) X1;
        }
    }

    public Integer getMax(double X1, double X2) {
        if (X1 < X2) {
            return (int) X2;
        } else {
            return (int) X1;
        }
    }
}
