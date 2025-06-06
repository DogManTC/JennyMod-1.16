/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.IWorldGenerator
 *  net.minecraftforge.fml.common.network.IGuiHandler
 *  net.minecraftforge.fml.common.network.NetworkRegistry
 *  net.minecraftforge.fml.common.registry.GameRegistry
 */
package com.schnurritv.sexmod.util.Handlers;

import com.schnurritv.sexmod.Main;
import com.schnurritv.sexmod.util.Handlers.EntityInit;
import com.schnurritv.sexmod.util.Handlers.EventHandler;
import com.schnurritv.sexmod.util.Handlers.GuiHandler;
import com.schnurritv.sexmod.util.Handlers.PacketHandler;
import com.schnurritv.sexmod.util.Handlers.RenderHandler;
import com.schnurritv.sexmod.util.Handlers.SoundsHandler;
import com.schnurritv.sexmod.world.gen.WorldGenCustomStructures;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegistryHandler {
    public static void preInitRegistries() {
        GameRegistry.registerWorldGenerator((IWorldGenerator)new WorldGenCustomStructures(), (int)0);
        EntityInit.registerEntities();
        RenderHandler.registerEntityRenders();
        EventHandler.registerEvents();
    }

    public static void initRegistries() {
        SoundsHandler.registerSounds();
        NetworkRegistry.INSTANCE.registerGuiHandler((Object)Main.instance, (IGuiHandler)new GuiHandler());
        PacketHandler.registerMessages();
    }
}
