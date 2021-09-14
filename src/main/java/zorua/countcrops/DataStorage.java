package zorua.countcrops;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private Map<Player, Location> playerPos1 = new HashMap<Player, Location>();
    private Map<Player, Location> playerPos2 = new HashMap<Player, Location>();
    private Map<Player, Integer> wheatCount = new HashMap<Player, Integer>();
    private Map<Player, Integer> countCount = new HashMap<Player, Integer>();

    public Map<Player, Location> getPlayerPos1(){
        return playerPos1;
    }

    public Map<Player, Location> getPlayerPos2(){
        return playerPos2;
    }

    public Map<Player, Integer> getWheatCount() {
        return wheatCount;
    }

    public Map<Player, Integer> getCountCount() {
        return countCount;
    }

    public void setPlayerPos1(Player player, Location location) {
        playerPos1.put(player, location);
    }

    public void setPlayerPos2(Player player, Location location) {
        playerPos2.put(player, location);
    }

    public void setPlayerWheatCount(Player player, Integer integer) {
        wheatCount.put(player, integer);
    }

    public void setPlayerCountCount(Player player, Integer integer) {
        countCount.put(player, integer);
    }
}
