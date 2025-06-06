/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.entity.living.LivingAttackEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.schnurritv.sexmod.events;

import com.schnurritv.sexmod.girls.GirlEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoDamageForGirlsWhileHavingTheSex {
    @SubscribeEvent
    public void NoDamageForGirlsWhileHavingTheSex(LivingAttackEvent event) {
        if (event.getEntity() instanceof GirlEntity) {
            // empty if block
        }
    }
}
