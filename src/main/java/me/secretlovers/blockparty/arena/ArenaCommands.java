package me.secretlovers.blockparty.arena;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import me.secretlovers.blockparty.BlockParty;
import me.secretlovers.blockparty.GameState;
import me.secretlovers.blockparty.Runnables.CheckColor;
import me.secretlovers.blockparty.Runnables.NewArena;
import me.secretlovers.blockparty.Runnables.StartGame;
import org.apache.commons.lang.UnhandledException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class ArenaCommands implements CommandExecutor,ArenaUtils {

    BlockParty main;

    public ArenaCommands(BlockParty main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!(sender instanceof Player)) return true;

        if(args.length==0) {
            return true;
        }
        Player player = (Player) sender;
        Arena arena = main.getArena();
        if(args[0].equalsIgnoreCase("create")) {
            if(arena.isCreated()) return true;
            arena.setWorld(BukkitAdapter.adapt(player.getWorld()));
            arena.setLocation(player.getLocation());
            arena.addPlayer(player);
            main.setArena(arena);
            addBlocks(arena);
            createArena(arena);
            player.sendMessage("Yes");
            return true;
        }
        if(args[0].equalsIgnoreCase("join")) {
            if(arena.getPlayers().contains(player)) return true;
            arena.addPlayer(player);
            main.setArena(arena);
            return true;
        }
        if(args[0].equalsIgnoreCase("start")) {
            BukkitTask startGame = new StartGame(main).runTaskTimer(main,0L, 0L);
        }
        if(args[0].equalsIgnoreCase("delete")) {
            arena = new Arena();
        }

        return true;
    }
}
