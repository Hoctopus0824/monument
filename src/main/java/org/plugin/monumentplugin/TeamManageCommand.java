package org.plugin.monumentplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamManageCommand implements CommandExecutor {

    private final MonumentPlugin plugin;

    public TeamManageCommand(MonumentPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length < 3) {
            sender.sendMessage("사용법: /teammanage <팀 이름> <색상> <플레이어1> <플레이어2> ...");
            return true;
        }

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        String teamName = args[0];
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }

        String colorName = args[1].toUpperCase();
        ChatColor color;
        try {
            color = ChatColor.valueOf(colorName);
            team.setColor(color);
        } catch (IllegalArgumentException e) {
            player.sendMessage("잘못된 색상입니다. 사용 가능한 색상을 확인하세요.");
            return true;
        }

        for (int i = 2; i < args.length; i++) {
            Player target = Bukkit.getPlayer(args[i]);
            if (target != null) {
                team.addEntry(target.getName());
                target.sendMessage(color + teamName + " 팀에 추가되었습니다!");
            } else {
                player.sendMessage(args[i] + " 플레이어를 찾을 수 없습니다.");
            }
        }

        player.sendMessage(color + teamName + " 팀이 생성되고 플레이어가 추가되었습니다.");
        return true;
    }
}