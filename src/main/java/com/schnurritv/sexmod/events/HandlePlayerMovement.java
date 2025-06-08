/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.MovementInput
 *  net.minecraftforge.client.event.InputUpdateEvent
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.schnurritv.sexmod.events;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HandlePlayerMovement {
    public static boolean active = true;
    public static boolean isThrusting = false;
    public static boolean isCumming = false;

    @SubscribeEvent
    public void PreventPlayerFromMoving(InputUpdateEvent event) {
        if (!active) {
            // Disable movement while interactions are active
            event.getMovementInput().leftImpulse = 0;
            event.getMovementInput().forwardImpulse = 0;
        }
    }

    @SubscribeEvent
    public void PreventPlayerFromTakingAction(InputEvent.MouseInputEvent event) {
        event.setCanceled(!active);
    }
}
