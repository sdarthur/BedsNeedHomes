package com.sdarthur.bedsneedhomes.util;

import com.sdarthur.bedsneedhomes.BNHConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class HomeChecker {

    private static final int SEARCH_RADIUS = BNHConfig.SEARCH_RADIUS.get();

    private static final Set<Block> BLACKLISTED_BLOCKS = new HashSet<>();

    private static final Queue<BlockPos> queue = new ArrayDeque<>();
    private static final Set<BlockPos> visited = new HashSet<>();

    /*
     TODO:  is this too resource intensive? can it be optimized? -> flood fill is a known algorithm, probably not
            add blacklisted blocks to config
     */
    public static boolean isHome(Player player, BlockPos pos, Level level) {

        if(level.canSeeSky(pos)) {
            return false;
        }

        queue.clear();
        visited.clear();

        queue.add(pos);
        visited.add(pos);

        while(!queue.isEmpty()) {
            BlockPos current = queue.element();
            queue.remove();

            if(current.distManhattan(pos) >= SEARCH_RADIUS) {
                return false;
            }

            for(Direction dir : Direction.values()) {
                BlockPos adj = current.relative(dir);

                if(visited.contains(adj)) {
                    continue;
                }

                if(isValidBlock(adj, player)) {
                    continue;
                }

                visited.add(adj.immutable());
                queue.add(adj.immutable());
            }
        }

        return true;
    }

    private static boolean isValidBlock(BlockPos pos, Player player) {
        BlockState block = player.level().getBlockState(pos);

        return !BLACKLISTED_BLOCKS.contains(block.getBlock());
    }

    public static void refreshConfigCache() {
        BLACKLISTED_BLOCKS.clear();

        for(String name : BNHConfig.BLACKLISTED_BLOCKS.get()) {
            ResourceLocation location = ResourceLocation.tryParse(name);

            if(location != null && BuiltInRegistries.BLOCK.containsKey(location)) {
                BLACKLISTED_BLOCKS.add(BuiltInRegistries.BLOCK.get(location));
            }
        }
    }
}
