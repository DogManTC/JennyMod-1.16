/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 */
package com.schnurritv.sexmod.util.Handlers;

import com.schnurritv.sexmod.events.HandlePlayerMovement;
import com.schnurritv.sexmod.events.NoDamageForGirlsWhileHavingTheSex;
import com.schnurritv.sexmod.events.PreloadModels;
import com.schnurritv.sexmod.events.RemoveEntityFromList;
import com.schnurritv.sexmod.events.SetFOVForSex;
import com.schnurritv.sexmod.gui.BlackScreenUI;
import com.schnurritv.sexmod.gui.SexUI;
import net.minecraftforge.common.MinecraftForge;

public class EventHandler {
    public static void registerEvents() {
        MinecraftForge.EVENT_BUS.register((Object)new NoDamageForGirlsWhileHavingTheSex());
        MinecraftForge.EVENT_BUS.register((Object)new RemoveEntityFromList());
        MinecraftForge.EVENT_BUS.register((Object)new HandlePlayerMovement());
        MinecraftForge.EVENT_BUS.register((Object)new PreloadModels());
        MinecraftForge.EVENT_BUS.register((Object)new SetFOVForSex());
        MinecraftForge.EVENT_BUS.register((Object)new SexUI());
        MinecraftForge.EVENT_BUS.register((Object)new BlackScreenUI());
    }
}
