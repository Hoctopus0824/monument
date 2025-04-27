package org.plugin.monumentplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

public class SlimeListener implements Listener {

    private final MonumentPlugin plugin;

    public SlimeListener(MonumentPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Slime && event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            Slime slime = (Slime) event.getEntity();

            if (slime.hasMetadata("MonumentName")) {
                Monument monument = plugin.findMonumentByName(slime.getMetadata("MonumentName").get(0).asString());

                if (monument != null) {
                    double remainingHealth = monument.getHealth() - event.getDamage();
                    monument.setHealth(remainingHealth);

                    if (remainingHealth - event.getDamage() <= 0) {
                        for (Player recPlayer : plugin.getServer().getOnlinePlayers()) {
                            recPlayer.sendMessage(ChatColor.RED + monument.getName() + ChatColor.WHITE + " 신상이 " + ChatColor.RED + player.getName() + ChatColor.WHITE + "에 의해 점령되었습니다!");
                            monument.spawn();
                            monument.setHealth(2000.0);
                        }
                    } else {
                        player.sendMessage(ChatColor.WHITE + "신상의 체력이 " + ChatColor.GREEN + (remainingHealth - event.getDamage()) + ChatColor.WHITE + " 남았습니다!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent event) {
        Slime slime = event.getEntity();
        if (slime.hasMetadata("MonumentName")) {
            event.setCancelled(true);
        }
    }
}