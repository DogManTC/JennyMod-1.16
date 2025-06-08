package com.schnurritv.sexmod.entity;

import com.schnurritv.sexmod.Main;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
        DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MODID);

    public static final RegistryObject<EntityType<JennyEntity>> JENNY = ENTITIES.register("jenny",
        () -> EntityType.Builder.<JennyEntity>of(JennyEntity::new, EntityClassification.CREATURE)
            .sized(0.6F, 1.8F)
            .build("jenny"));

    @SubscribeEvent
    public static void registerAttributes(RegistryEvent.Register<EntityType<?>> event) {
        GlobalEntityTypeAttributes.put(JENNY.get(), JennyEntity.createAttributes().build());
    }
}
