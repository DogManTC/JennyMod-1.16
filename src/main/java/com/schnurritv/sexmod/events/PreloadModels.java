/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.world.World
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.schnurritv.sexmod.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PreloadModels {
    public boolean didIt = false;

    @SubscribeEvent
    public void PenisSauce(LivingEvent.LivingUpdateEvent event) {
        if (!this.didIt && event.getEntity() instanceof PlayerEntity) {
            this.SpawnPreloders((PlayerEntity) event.getEntity(), event.getEntity().level);
            this.didIt = true;
        }
    }

    private void SpawnPreloders(PlayerEntity player, World world) {
    }
}
