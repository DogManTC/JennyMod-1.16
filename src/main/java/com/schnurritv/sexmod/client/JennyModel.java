package com.schnurritv.sexmod.client;

import com.schnurritv.sexmod.Main;
import com.schnurritv.sexmod.entity.JennyEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JennyModel extends AnimatedGeoModel<JennyEntity> {
    @Override
    public ResourceLocation getModelLocation(JennyEntity object) {
        return new ResourceLocation(Main.MODID, "geo/jenny/jennydressed.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(JennyEntity object) {
        // The original mod supported an alternate texture, but binary files are
        // excluded from this port. Both skin indices now use the default
        // texture so the game can compile without referencing missing assets.
        return new ResourceLocation(Main.MODID, "textures/entity/jenny/jenny.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(JennyEntity animatable) {
        return new ResourceLocation(Main.MODID, "animations/jenny/jenny.animation.json");
    }
}
