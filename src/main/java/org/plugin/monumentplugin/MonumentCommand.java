package org.plugin.monumentplugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MonumentCommand implements CommandExecutor {

    private final MonumentPlugin plugin;

    public MonumentCommand(MonumentPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length != 4) {
            sender.sendMessage("사용법: /monument <이름> <x> <y> <z>");
            return true;
        }

        String name = args[0];
        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int z = Integer.parseInt(args[3]);

        Location loc = new Location(player.getWorld(), x, y, z);
        Monument monument = new Monument(name, loc, plugin);
        monument.spawn();
        plugin.getMonuments().put(monument.getEntity(), monument);

        player.sendMessage(ChatColor.RED + name + ChatColor.WHITE + " 신상이 소환되었습니다!");
        return true;
    }
}