package com.sdarthur.bedsneedhomes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class BNHConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue SEARCH_RADIUS = BUILDER
            .comment(" The size of the search radius for a wall")
            .defineInRange("searchRadius", 10, 3, 30);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> BLACKLISTED_BLOCKS = BUILDER
            .comment(" Blocks that will NOT be considerer part of a wall")
            .defineList("blacklistedBlocks",
                    List.of("minecraft:air", "minecraft:water", "minecraft:crafting_table", "minecraft:chest", "minecraft:torch", "minecraft:soul_torch"),
                    () -> "minecraft:air", BNHConfig::validateBlockName);

    private static boolean validateBlockName(final Object obj) {
        return obj instanceof String blockName && BuiltInRegistries.BLOCK.containsKey(ResourceLocation.parse(blockName));
    }

    static final ModConfigSpec SPEC = BUILDER.build();
}
