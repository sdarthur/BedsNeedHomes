package com.sdarthur.bedsneedhomes.mixin;

import com.sdarthur.bedsneedhomes.util.HomeChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public abstract class BedMixin {

    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void checkHouse(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {

        if(level.isClientSide || !(level instanceof ServerLevel) || level.isDay()) {
            return;
        }

        if(!HomeChecker.isHome(player, pos, level) && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.displayClientMessage(Component.translatable("sleep.bedsneedhomes.no_home"), true);
            cir.setReturnValue(InteractionResult.SUCCESS_NO_ITEM_USED);
        }
    }
}
