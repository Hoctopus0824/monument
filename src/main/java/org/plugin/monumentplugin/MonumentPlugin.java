package org.plugin.monumentplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Slime;
import org.bukkit.metadata.MetadataValue;

import java.util.HashMap;
import java.util.Map;

public class MonumentPlugin extends JavaPlugin {

    private Map<Slime, Monument> monuments = new HashMap<>();

    @Override
    public void onEnable() {
        this.getCommand("teammanage").setExecutor(new TeamManageCommand(this));
        this.getCommand("monument").setExecutor(new MonumentCommand(this));
        getServer().getPluginManager().registerEvents(new SlimeListener(this), this);
    }

    public Map<Slime, Monument> getMonuments() {
        return monuments;
    }

    public Monument findMonumentByName(String targetName) {
        for (Map.Entry<Slime, Monument> entry : monuments.entrySet()) {
            Slime slime = entry.getKey();
            Monument monument = entry.getValue();

            if (slime.hasMetadata("MonumentName")) {
                for (MetadataValue value : slime.getMetadata("MonumentName")) {
                    if (value.asString().equals(targetName)) {
                        return monument;
                    }
                }
            }
        }
        return null;
    }
}