package me.blmp.universe;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends JavaPlugin {

    private final Set<UUID> disabled = new HashSet<>();

    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {

                if (disabled.contains(p.getUniqueId())) continue;

                int ping = 0;
                try {
                    ping = p.getPing();
                } catch (Exception ignored) {}

                int fps = ThreadLocalRandom.current().nextInt(120, 601);

                p.sendActionBar("§0§lBLMP §a§lUNIVERSE §8| §aPing: §f" + ping + "ms §8| §aFPS: §f" + fps);
            }
        }, 0L, 20L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return true;

        Player p = (Player) sender;
        UUID id = p.getUniqueId();

        if (args.length == 0) return true;

        if (args[0].equalsIgnoreCase("false")) {
            disabled.add(id);
            return true;
        }

        if (args[0].equalsIgnoreCase("true")) {
            disabled.remove(id);
            return true;
        }

        return true;
    }
}
