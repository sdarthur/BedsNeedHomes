package com.sdarthur.bedsneedhomes;

import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(BedsNeedHomes.MODID)
public class BedsNeedHomes {
    public static final String MODID = "bedsneedhomes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BedsNeedHomes(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, BNHConfig.SPEC);
    }
}
