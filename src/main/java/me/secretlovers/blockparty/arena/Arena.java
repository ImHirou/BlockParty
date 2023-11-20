package me.secretlovers.blockparty.arena;

import com.sk89q.worldedit.world.World;
import me.secretlovers.blockparty.GameState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Arena {

    private boolean isCreated;
    private GameState gameState;
    private int round;
    private HashMap<String, Long> delays = new HashMap<>();
    private Material currentBlock;
    private Location location;
    private World world;
    private ArrayList<Material> blocks;
    private ArrayList<Player> players;
    public Arena() {
        isCreated = false;
        gameState=GameState.WAITING;
        round=0;
        delays.put("finding",5000L);
        delays.put("waiting",1000L);
        delays.put("checking",3000L);
        currentBlock=Material.AIR;
        location = null;
        world = null;
        blocks = new ArrayList<>();
        players = new ArrayList<>();
    }

    public boolean isCreated() {
        return isCreated;
    }
    public void setCreated(boolean created) {
        isCreated = created;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public HashMap<String, Long> getDelays() {
        return delays;
    }

    public void setDelays(HashMap<String, Long> delays) {
        this.delays = delays;
    }

    public Long getNowDelay() {
        if(getGameState().equals(null)) return null;
        if(getGameState().equals(GameState.WAITING)) {
            return delays.get("waiting");
        }
        if(getGameState().equals(GameState.FINDING)) {
            return delays.get("finding");
        }
        if(getGameState().equals(GameState.CHECKING)) {
            return delays.get("checking");
        }
        if (getGameState().equals(GameState.FINISH)) {
            return 0L;
        }
        return 0L;
    }

    public Material getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(Material currentBlock) {
        this.currentBlock = currentBlock;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public ArrayList<Material> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Material> blocks) {
        this.blocks = blocks;
    }

    public void addBlock(Material material) {
        this.blocks.add(material);
    }
    public void removeBlock(Material material) {
        this.blocks.remove(material);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }
    public void removePlayer(Player player) {
        this.players.remove(player);
    }
}
