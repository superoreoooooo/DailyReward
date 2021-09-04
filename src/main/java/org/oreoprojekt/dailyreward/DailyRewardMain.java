package org.oreoprojekt.dailyreward;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.oreoprojekt.dailyreward.eventListener.DREventListener;
import org.oreoprojekt.dailyreward.manager.DRManager;

import java.util.Arrays;


public final class DailyRewardMain extends JavaPlugin {

    public DRManager data;
    public static String prefix = ChatColor.GRAY + "[" + ChatColor.GOLD + "REWARD" + ChatColor.GRAY + "] ";

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "DailyReward Plugin ON!");
        getServer().getPluginManager().registerEvents(new DREventListener(this), this);

        this.data = new DRManager(this);
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(prefix +ChatColor.RED + "DailyReward Plugin OFF!");
    }

    public void check(Player player) {
        int check = 0;
        check = this.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".nickname." + player.getName().toString() + ".count");
        if (check == 1) {
            Material type = Material.matchMaterial("PLAYER_HEAD");
            ItemStack item = new ItemStack(type, 10);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwner("ElMarcosFTW");
            meta.setDisplayName("랜덤" + " 박스");
            String strCommonMsg = ChatColor.RED + "※" + ChatColor.BOLD + "주의" + "※" + "아이템창에 공간이 없으면 아이템이 소멸됩니다.";
            meta.setLore(Arrays.asList(ChatColor.LIGHT_PURPLE + "랜덤 아이템 박스" + "가 담긴 박스다.", ChatColor.GREEN + "클릭시 오픈된다.", strCommonMsg));
            item.setItemMeta(meta);
            player.getInventory().addItem(item);
            player.sendMessage(prefix + ChatColor.GREEN + "최초 접속 보상을 받으셨습니다!");
            double sizenow = player.getWorld().getWorldBorder().getSize();
            player.getWorld().getWorldBorder().setSize(sizenow + 2);
            String Prefix = ChatColor.YELLOW + "[wbPL] ";
            Bukkit.broadcastMessage(Prefix + ChatColor.AQUA + "월드가 확장되었습니다.");
        }
    }
}