/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityHanging
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.MathHelper
 *  org.lwjgl.opengl.GL11
 *  software.bernie.geckolib3.core.IAnimatableModel
 *  software.bernie.geckolib3.core.event.predicate.AnimationEvent
 *  software.bernie.geckolib3.geo.render.built.GeoModel
 *  software.bernie.geckolib3.model.AnimatedGeoModel
 *  software.bernie.geckolib3.model.provider.GeoModelProvider
 *  software.bernie.geckolib3.model.provider.data.EntityModelData
 *  software.bernie.geckolib3.renderers.geo.GeoEntityRenderer
 *  software.bernie.geckolib3.renderers.geo.RenderHurtColor
 *  software.bernie.shadowed.eliotlash.mclib.utils.Interpolations
 */
package com.schnurritv.sexmod.girls;

import com.schnurritv.sexmod.girls.GirlEntity;
import com.schnurritv.sexmod.util.Reference;
import java.awt.Color;
import java.util.Collections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.RenderHurtColor;
import software.bernie.shadowed.eliotlash.mclib.utils.Interpolations;

public class GirlRenderer<T extends GirlEntity>
extends GeoEntityRenderer<T> {
    double leashHeightOffset;

    public GirlRenderer(RenderManager renderManager, AnimatedGeoModel<T> model, double leashHeightOffset) {
        super(renderManager, model);
        this.leashHeightOffset = leashHeightOffset;
    }

    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (entity.func_110167_bD()) {
            this.renderLeash((GirlEntity)((Object)entity), x, y + this.leashHeightOffset, z, partialTicks);
        }
        if (((GirlEntity)((Object)entity)).isForPreloading) {
            GL11.glScalef((float)0.1f, (float)0.1f, (float)0.1f);
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b((double)x, (double)y, (double)z);
        GL11.glDisable((int)2896);
        boolean shouldSit = entity.func_184187_bx() != null && entity.func_184187_bx().shouldRiderSit();
        EntityModelData entityModelData = new EntityModelData();
        entityModelData.isSitting = shouldSit;
        entityModelData.isChild = entity.func_70631_g_();
        float f = Interpolations.lerpYaw((float)((GirlEntity)((Object)entity)).field_70760_ar, (float)((GirlEntity)((Object)entity)).field_70761_aq, (float)partialTicks);
        float f1 = Interpolations.lerpYaw((float)((GirlEntity)((Object)entity)).field_70758_at, (float)((GirlEntity)((Object)entity)).field_70759_as, (float)partialTicks);
        float netHeadYaw = f1 - f;
        if (shouldSit && entity.func_184187_bx() instanceof EntityLivingBase) {
            EntityLivingBase livingentity = (EntityLivingBase)entity.func_184187_bx();
            f = Interpolations.lerpYaw((float)livingentity.field_70760_ar, (float)livingentity.field_70761_aq, (float)partialTicks);
            netHeadYaw = f1 - f;
            float f3 = MathHelper.func_76142_g((float)netHeadYaw);
            if (f3 < -85.0f) {
                f3 = -85.0f;
            }
            if (f3 >= 85.0f) {
                f3 = 85.0f;
            }
            f = f1 - f3;
            if (f3 * f3 > 2500.0f) {
                f += f3 * 0.2f;
            }
            netHeadYaw = f1 - f;
        }
        float headPitch = Interpolations.lerp((float)((GirlEntity)((Object)entity)).field_70127_C, (float)((GirlEntity)((Object)entity)).field_70125_A, (float)partialTicks);
        float f7 = this.handleRotationFloat((EntityLivingBase)entity, partialTicks);
        this.applyRotations((EntityLivingBase)entity, f7, f, partialTicks);
        float limbSwingAmount = 0.0f;
        float limbSwing = 0.0f;
        if (!shouldSit && entity.func_70089_S()) {
            limbSwingAmount = Interpolations.lerp((float)((GirlEntity)((Object)entity)).field_184618_aE, (float)((GirlEntity)((Object)entity)).field_70721_aZ, (float)partialTicks);
            limbSwing = ((GirlEntity)((Object)entity)).field_184619_aG - ((GirlEntity)((Object)entity)).field_70721_aZ * (1.0f - partialTicks);
            if (entity.func_70631_g_()) {
                limbSwing *= 3.0f;
            }
            if (limbSwingAmount > 1.0f) {
                limbSwingAmount = 1.0f;
            }
        }
        entityModelData.headPitch = -headPitch;
        entityModelData.netHeadYaw = -netHeadYaw;
        AnimationEvent predicate = new AnimationEvent(entity, limbSwing, limbSwingAmount, partialTicks, !(limbSwingAmount > -0.15f) || !(limbSwingAmount < 0.15f), Collections.singletonList(entityModelData));
        GeoModelProvider modelProvider = super.getGeoModelProvider();
        ResourceLocation location = modelProvider.getModelLocation(entity);
        GeoModel model = modelProvider.getModel(location);
        if (modelProvider instanceof IAnimatableModel) {
            ((IAnimatableModel)modelProvider).setLivingAnimations(entity, this.getUniqueID(entity), predicate);
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)0.0f, (float)0.01f, (float)0.0f);
        Minecraft.func_71410_x().field_71446_o.func_110577_a(this.getEntityTexture((EntityLivingBase)entity));
        Color renderColor = this.getRenderColor(entity, partialTicks);
        boolean flag = this.setDoRenderBrightness((EntityLivingBase)entity, partialTicks);
        this.render(model, entity, partialTicks, (float)renderColor.getRed() / 255.0f, (float)renderColor.getBlue() / 255.0f, (float)renderColor.getGreen() / 255.0f, (float)renderColor.getAlpha() / 255.0f);
        if (flag) {
            RenderHurtColor.unset();
        }
        GL11.glEnable((int)2896);
        GlStateManager.func_179121_F();
        GlStateManager.func_179121_F();
    }

    protected void renderLeash(GirlEntity entityLivingIn, double x, double y, double z, float partialTicks) {
        Entity entity = entityLivingIn.func_110166_bE();
        if (entity != null) {
            y -= (1.6 - (double)entityLivingIn.field_70131_O) * 0.5;
            Tessellator tessellator = Tessellator.func_178181_a();
            BufferBuilder bufferbuilder = tessellator.func_178180_c();
            double d0 = Reference.Lerp(entity.field_70126_B, entity.field_70177_z, (double)(partialTicks * 0.5f)) * 0.01745329238474369;
            double d1 = Reference.Lerp(entity.field_70127_C, entity.field_70125_A, (double)(partialTicks * 0.5f)) * 0.01745329238474369;
            double d2 = Math.cos(d0);
            double d3 = Math.sin(d0);
            double d4 = Math.sin(d1);
            if (entity instanceof EntityHanging) {
                d2 = 0.0;
                d3 = 0.0;
                d4 = -1.0;
            }
            double d5 = Math.cos(d1);
            double d6 = Reference.Lerp(entity.field_70169_q, entity.field_70165_t, (double)partialTicks) - d2 * 0.7 - d3 * 0.5 * d5;
            double d7 = Reference.Lerp(entity.field_70167_r + (double)entity.func_70047_e() * 0.7, entity.field_70163_u + (double)entity.func_70047_e() * 0.7, (double)partialTicks) - d4 * 0.5 - 0.25;
            double d8 = Reference.Lerp(entity.field_70166_s, entity.field_70161_v, (double)partialTicks) - d3 * 0.7 + d2 * 0.5 * d5;
            double d9 = Reference.Lerp(entityLivingIn.field_70760_ar, entityLivingIn.field_70761_aq, (double)partialTicks) * 0.01745329238474369 + 1.5707963267948966;
            d2 = Math.cos(d9) * (double)entityLivingIn.field_70130_N * 0.4;
            d3 = Math.sin(d9) * (double)entityLivingIn.field_70130_N * 0.4;
            double d10 = Reference.Lerp(entityLivingIn.field_70169_q, entityLivingIn.field_70165_t, (double)partialTicks) + d2;
            double d11 = Reference.Lerp(entityLivingIn.field_70167_r, entityLivingIn.field_70163_u, (double)partialTicks);
            double d12 = Reference.Lerp(entityLivingIn.field_70166_s, entityLivingIn.field_70161_v, (double)partialTicks) + d3;
            x += d2;
            z += d3;
            double d13 = (float)(d6 - d10);
            double d14 = (float)(d7 - d11);
            double d15 = (float)(d8 - d12);
            GlStateManager.func_179090_x();
            GlStateManager.func_179140_f();
            GlStateManager.func_179129_p();
            bufferbuilder.func_181668_a(5, DefaultVertexFormats.field_181706_f);
            for (int j = 0; j <= 24; ++j) {
                float f = 0.5f;
                float f1 = 0.4f;
                float f2 = 0.3f;
                if (j % 2 == 0) {
                    f *= 0.7f;
                    f1 *= 0.7f;
                    f2 *= 0.7f;
                }
                float f3 = (float)j / 24.0f;
                bufferbuilder.func_181662_b(x + d13 * (double)f3 + 0.0, y + d14 * (double)(f3 * f3 + f3) * 0.5 + (double)((24.0f - (float)j) / 18.0f + 0.125f), z + d15 * (double)f3).func_181666_a(f, f1, f2, 1.0f).func_181675_d();
                bufferbuilder.func_181662_b(x + d13 * (double)f3 + 0.025, y + d14 * (double)(f3 * f3 + f3) * 0.5 + (double)((24.0f - (float)j) / 18.0f + 0.125f) + 0.025, z + d15 * (double)f3).func_181666_a(f, f1, f2, 1.0f).func_181675_d();
            }
            tessellator.func_78381_a();
            bufferbuilder.func_181668_a(5, DefaultVertexFormats.field_181706_f);
            for (int k = 0; k <= 24; ++k) {
                float f4 = 0.5f;
                float f5 = 0.4f;
                float f6 = 0.3f;
                if (k % 2 == 0) {
                    f4 *= 0.7f;
                    f5 *= 0.7f;
                    f6 *= 0.7f;
                }
                float f7 = (float)k / 24.0f;
                bufferbuilder.func_181662_b(x + d13 * (double)f7 + 0.0, y + d14 * (double)(f7 * f7 + f7) * 0.5 + (double)((24.0f - (float)k) / 18.0f + 0.125f) + 0.025, z + d15 * (double)f7).func_181666_a(f4, f5, f6, 1.0f).func_181675_d();
                bufferbuilder.func_181662_b(x + d13 * (double)f7 + 0.025, y + d14 * (double)(f7 * f7 + f7) * 0.5 + (double)((24.0f - (float)k) / 18.0f + 0.125f), z + d15 * (double)f7 + 0.025).func_181666_a(f4, f5, f6, 1.0f).func_181675_d();
            }
            tessellator.func_78381_a();
            GlStateManager.func_179145_e();
            GlStateManager.func_179098_w();
            GlStateManager.func_179089_o();
        }
    }
}
