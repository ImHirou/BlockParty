package me.secretlovers.blockparty.Runnables;

import me.secretlovers.blockparty.BlockParty;
import me.secretlovers.blockparty.GameState;
import me.secretlovers.blockparty.arena.Arena;
import me.secretlovers.blockparty.arena.ArenaUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class StartGame extends BukkitRunnable implements ArenaUtils {

    private BlockParty main;
    public StartGame(BlockParty main)  {
        this.main=main;
    }
    @Override
    public void run() {
        Arena arena = this.main.getArena();
        arena.setCurrentBlock(getRandomMaterial(arena));
        Long waitingDelay = arena.getDelays().get("waiting");
        Long findingDelay = arena.getDelays().get("finding");
        Long checkingDelay = arena.getDelays().get("checking");
        if(arena.getGameState().equals(GameState.FINISH)) {
            cancel();
        }
        main.setArena(arena);
        if(arena.getGameState().equals(GameState.WAITING)) {
            for(Player p : arena.getPlayers()) {
                p.sendMessage(ChatColor.BLUE + "Start in " + ChatColor.AQUA + "" + ChatColor.BOLD + waitingDelay/1000L + " seconds");
            }
            try {
                Thread.sleep(waitingDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            arena.setGameState(GameState.FINDING);
        }
        else if(arena.getGameState().equals(GameState.FINDING)) {
            arena.setCurrentBlock(getRandomMaterial(arena));
            for(Player p : arena.getPlayers()) {
                p.getInventory().setItem(4,new ItemStack(arena.getCurrentBlock()));
                p.sendMessage("Go to find");
            }
            try {
                Thread.sleep(findingDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Long tmp = findingDelay-100L;
            arena.getDelays().remove("finding");
            arena.getDelays().put("finding", tmp);
            BukkitTask checkColor = new CheckColor(main).runTask(main);
            arena.setGameState(GameState.CHECKING);
        }
        else if(arena.getGameState().equals(GameState.CHECKING)) {
            for(Player p: arena.getPlayers()) {
                p.getInventory().clear();
                p.sendMessage("Check");
            }
            try {
                Thread.sleep(checkingDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for(Player p: arena.getPlayers()) {
                if(p.getLocation().getY()<arena.getLocation().getY()-5) {
                    arena.removePlayer(p);
                }
            }
            if(arena.getPlayers().size()<1) {
                arena.setGameState(GameState.FINISH);
            }
            arena.setGameState(GameState.FINDING);
            BukkitTask newArena = new NewArena(main).runTask(main);
        }
        main.setArena(arena);
    }
}
