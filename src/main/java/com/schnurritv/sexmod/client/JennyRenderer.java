package com.schnurritv.sexmod.client;

import com.schnurritv.sexmod.entity.JennyEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class JennyRenderer extends GeoEntityRenderer<JennyEntity> {
    public JennyRenderer(EntityRendererManager renderManager) {
        super(renderManager, new JennyModel());
        this.shadowRadius = 0.5f;
    }
}
