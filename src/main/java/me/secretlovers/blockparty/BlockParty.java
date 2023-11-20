package me.secretlovers.blockparty;

import me.secretlovers.blockparty.arena.Arena;
import me.secretlovers.blockparty.arena.ArenaCommands;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockParty extends JavaPlugin {

    Arena arena = new Arena();

    @Override
    public void onEnable() {

        getCommand("arena").setExecutor(new ArenaCommands(this));

    }

    public Arena getArena() {
        return arena;
    }
    public void setArena(Arena arena) {
        this.arena = arena;
    }
}
