/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fml.common.registry.EntityRegistry
 */
package com.schnurritv.sexmod.util.Handlers;

import com.schnurritv.sexmod.Main;
import com.schnurritv.sexmod.girls.ellie.EllieEntity;
import com.schnurritv.sexmod.girls.jenny.JennyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit {
    public static void registerEntities() {
        EntityInit.registerEntity("jenny", JennyEntity.class, 177013, 50, 3286592, 12655237);
        EntityInit.registerEntity("ellie", EllieEntity.class, 228922, 50, 0x161616, 0x980000);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2) {
        EntityRegistry.registerModEntity((ResourceLocation)new ResourceLocation("sexmod:" + name), entity, (String)name, (int)id, (Object)Main.instance, (int)range, (int)1, (boolean)true, (int)color1, (int)color2);
    }
}
