package me.secretlovers.blockparty.Runnables;

import me.secretlovers.blockparty.BlockParty;
import me.secretlovers.blockparty.arena.ArenaUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class NewArena extends BukkitRunnable implements ArenaUtils {

    private BlockParty main;

    public NewArena(BlockParty main) {
        this.main=main;
    }

    @Override
    public void run() {
        createArena(main.getArena());
    }
}
