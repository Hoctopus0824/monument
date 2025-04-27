package org.plugin.monumentplugin;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Monument {
    private String name;
    private Location location;
    private Slime entity;
    private double health = 2000.0;
    private MonumentPlugin plugin;

    public Monument(String name, Location location, MonumentPlugin plugin) {
        this.name = name;
        this.location = location;
        this.plugin = plugin;
    }

    public void spawn() {
        entity = (Slime) location.getWorld().spawnEntity(location, EntityType.SLIME);
        entity.setSize(10);
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(2000.0);
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(1.0);
        entity.setHealth(health);
        entity.setCustomName(name);
        entity.setCustomNameVisible(true);
        entity.setInvisible(false);
        entity.setAI(false);
        entity.setMetadata("MonumentName", new FixedMetadataValue(plugin, name));
    }

    public Slime getEntity() {
        return entity;
    }

    public void setHealth(double health) {
        this.health = Math.max(0, health);
        entity.setHealth(this.health);
    }

    public boolean isDeath() {
        return health <= 0.0;
    }

    public @NotNull String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }
}