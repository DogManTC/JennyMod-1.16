/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.schnurritv.sexmod.events;

import com.schnurritv.sexmod.girls.GirlEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RemoveEntityFromList {
    @SubscribeEvent
    public void RemoveSophiFromList(LivingDeathEvent event) {
        if (event.getEntity() instanceof GirlEntity) {
            GirlEntity girl = (GirlEntity)event.getEntity();
            GirlEntity.girlEntities.remove((Object)girl);
        }
    }
}
