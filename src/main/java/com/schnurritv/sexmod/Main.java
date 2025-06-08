package com.schnurritv.sexmod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import com.schnurritv.sexmod.entity.ModEntities;
import com.schnurritv.sexmod.network.TeleportPlayer;
import com.schnurritv.sexmod.network.SendChatMessage;
import com.schnurritv.sexmod.network.ChangeSkin;
import com.schnurritv.sexmod.events.HandlePlayerMovement;
import com.schnurritv.sexmod.events.NoDamageForGirlsWhileHavingTheSex;
import com.schnurritv.sexmod.events.PreloadModels;
import com.schnurritv.sexmod.events.RemoveEntityFromList;
import com.schnurritv.sexmod.events.SetFOVForSex;
import com.schnurritv.sexmod.client.JennyRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import com.schnurritv.sexmod.util.Reference;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "sexmod";
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel NETWORK;

    public Main() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::serverStarting);
        ModEntities.ENTITIES.register(modBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        NETWORK = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
        );
        int id = 0;
        NETWORK.registerMessage(id++, TeleportPlayer.class,
            TeleportPlayer::encode,
            TeleportPlayer::decode,
            TeleportPlayer::handle
        );
        NETWORK.registerMessage(id++, SendChatMessage.class,
            SendChatMessage::encode,
            SendChatMessage::decode,
            SendChatMessage::handle
        );
        NETWORK.registerMessage(id++, ChangeSkin.class,
            ChangeSkin::encode,
            ChangeSkin::decode,
            ChangeSkin::handle
        );

        // Register event handlers
        MinecraftForge.EVENT_BUS.register(new HandlePlayerMovement());
        MinecraftForge.EVENT_BUS.register(new NoDamageForGirlsWhileHavingTheSex());
        MinecraftForge.EVENT_BUS.register(new PreloadModels());
        MinecraftForge.EVENT_BUS.register(new RemoveEntityFromList());
        MinecraftForge.EVENT_BUS.register(new SetFOVForSex());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.JENNY.get(), JennyRenderer::new);
    }

    private void serverStarting(final FMLServerStartingEvent event) {
        Reference.server = event.getServer();
    }
}
