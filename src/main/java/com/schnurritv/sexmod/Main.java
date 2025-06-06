package com.schnurritv.sexmod;

import com.schnurritv.sexmod.girls.GirlEntity;
import com.schnurritv.sexmod.proxy.CommonProxy;
import com.schnurritv.sexmod.util.Handlers.RegistryHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "sexmod";
    public static Main instance;
    public static CommonProxy proxy = new CommonProxy();

    public Main() {
        instance = this;
        GeckoLib.initialize();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        RegistryHandler.preInitRegistries();
        RegistryHandler.initRegistries();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // client only setup
    }

    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        GirlEntity.girlEntities.clear();
    }
}
