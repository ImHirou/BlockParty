package me.secretlovers.blockparty.Runnables;

import me.secretlovers.blockparty.BlockParty;
import me.secretlovers.blockparty.arena.ArenaUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class CheckColor extends BukkitRunnable implements ArenaUtils {

    private BlockParty main;

    public CheckColor(BlockParty main) {
        this.main=main;
    }

    @Override
    public void run() {
        checkColor(main.getArena());
    }
}
