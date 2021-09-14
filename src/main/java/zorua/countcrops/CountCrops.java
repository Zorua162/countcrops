package zorua.countcrops;

import org.bukkit.plugin.java.JavaPlugin;

import zorua.countcrops.commands.SetPos1Command;
import zorua.countcrops.commands.SetPos2Command;
import zorua.countcrops.commands.StartCountCommand;


public final class CountCrops extends JavaPlugin {

    private DataStorage dataStorage = new DataStorage();

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void registerCommands() {
        getCommand("startcount").setExecutor(new StartCountCommand(this));
        getCommand("setpos1").setExecutor(new SetPos1Command(this));
        getCommand("setpos2").setExecutor(new SetPos2Command(this));

    }

    public DataStorage getDataStorage() {
        return dataStorage;
    }
}
