package com.sdarthur.bedsneedhomes.event;

import com.sdarthur.bedsneedhomes.BedsNeedHomes;
import com.sdarthur.bedsneedhomes.util.HomeChecker;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;

@EventBusSubscriber(modid = BedsNeedHomes.MODID)
public class BNHEvents {

    @SubscribeEvent
    public static void cacheBlacklistedBlocks(ModConfigEvent.Loading event) {
        if(event.getConfig().getModId().equals(BedsNeedHomes.MODID)) {
            HomeChecker.refreshConfigCache();
        }
    }

    @SubscribeEvent
    public static void cacheBlacklistedBlocks(ModConfigEvent.Reloading event) {
        if(event.getConfig().getModId().equals(BedsNeedHomes.MODID)) {
            HomeChecker.refreshConfigCache();
        }
    }
}
