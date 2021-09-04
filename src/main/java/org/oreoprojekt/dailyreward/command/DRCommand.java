package org.oreoprojekt.dailyreward.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.oreoprojekt.dailyreward.DailyRewardMain;
import static org.oreoprojekt.dailyreward.DailyRewardMain.prefix;

public class DRCommand implements CommandExecutor {


    private DailyRewardMain plugin;

    public DRCommand(DailyRewardMain plugin) {
        this.plugin = plugin;
    }

    public boolean checkPermission(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("now")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                int count = getData(player);
                sender.sendMessage(prefix + ChatColor.GREEN + "당신의 접속 횟수는 : " + count + "번 입니다.");
                return true;
            }
            else {
                sender.sendMessage(prefix + ChatColor.GREEN + "콘솔에서 될거라고 생각했습니까 휴먼?");
                return false;
            }
        }
        else {
            if (!checkPermission(sender, "administrator")) {
                sender.sendMessage(prefix + ChatColor.RED + "권한이 없습니다.");
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage(prefix + ChatColor.GREEN + "/dr reset (플레이어 이름) or /dr now");
                return false;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("reset")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    editData(player, 0);
                    player.sendMessage(prefix + ChatColor.GREEN + "접속 카운트 초기화가 완료 되었습니다.");
                    return true;
                } else {
                    sender.sendMessage(prefix + ChatColor.GREEN + "콘솔에서는 이렇게 못쓴다..");
                    return false;
                }
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("reset")) {
                if (Bukkit.getOnlinePlayers().toString().contains(args[1])) {
                    Player player = Bukkit.getPlayerExact(args[1]);
                    editData(player, 0);
                    sender.sendMessage(prefix + ChatColor.GREEN + player.getName() + " 의 접속 카운트 초기화가 완료 되었습니다.");
                    return true;
                } else {
                    sender.sendMessage(prefix + ChatColor.GREEN + "this player doesnt exist.");
                    return false;
                }
            } else {
                sender.sendMessage(prefix + ChatColor.GREEN + "/dr reset 플레이어 이름");
                return false;
            }
        }
    }

    public void editData(Player player, int count) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".nickname." + player.getName().toString() + ".count", count);
        plugin.data.saveConfig();
        plugin.check(player);
    }

    public int getData(Player player) {
        int count = 0;
        if (plugin.data.getConfig().contains("players." + player.getUniqueId().toString() + ".nickname." + player.getName().toString() + ".count")) {
            count = plugin.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".nickname." + player.getName().toString() + ".count");
        }
        return count;
    }
}