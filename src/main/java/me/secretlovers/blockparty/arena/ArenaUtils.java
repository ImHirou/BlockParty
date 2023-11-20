package me.secretlovers.blockparty.arena;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.function.mask.BlockMask;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockState;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public interface ArenaUtils {

    default void addBlocks(Arena arena) {

        arena.addBlock(Material.BLUE_CONCRETE);
        arena.addBlock(Material.GREEN_CONCRETE);
        arena.addBlock(Material.RED_CONCRETE);
        arena.addBlock(Material.PURPLE_CONCRETE);

    }

    default void createArena(Arena arena) {
        World world = arena.getWorld();
        CuboidRegion region = new CuboidRegion(world,
                BlockVector3.at(arena.getLocation().getX()-15, arena.getLocation().getY()-1, arena.getLocation().getZ()-15),
                BlockVector3.at(arena.getLocation().getX()+15, arena.getLocation().getY()-1, arena.getLocation().getZ()+15));
        try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            RandomPattern pattern = new RandomPattern();
            for(Material m : arena.getBlocks()) {
                BlockState block = BukkitAdapter.adapt(m.createBlockData());
                double v = 1.0 / arena.getBlocks().size();
                pattern.add(block, v);
            }

            editSession.setBlocks(region, pattern);
            arena.setCreated(true);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    default void checkColor(Arena arena) {
        Material currentBlock = arena.getCurrentBlock();
        World world = arena.getWorld();
        try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            double y = arena.getLocation().getY()-1;
            Pattern pat = BukkitAdapter.adapt(Material.AIR.createBlockData());
            for(double x=arena.getLocation().getX()-15; x<=arena.getLocation().getX()+15; x++) {
                for(double z=arena.getLocation().getZ()-15; z<=arena.getLocation().getZ()+15; z++) {
                    BlockVector3 block = BlockVector3.at(x,y,z);
                    CuboidRegion region = new CuboidRegion(world,
                            block,
                            block);
                    BlockState blockState = BukkitAdapter.asBlockState(new ItemStack(currentBlock));
                    BlockState blockStateRemove = editSession.getBlock(block);
                    if(blockState.getBlockType().equals(blockStateRemove.getBlockType())) continue;
                    else editSession.setBlocks(region, pat);
                }
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    default Material getRandomMaterial(Arena arena) {
        int materials = ThreadLocalRandom.current().nextInt(0,arena.getBlocks().size());
        return arena.getBlocks().get(materials);
    }

}
