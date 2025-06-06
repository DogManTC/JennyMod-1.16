/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.event.RenderGameOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.schnurritv.sexmod.gui;

import com.schnurritv.sexmod.gui.SexUI;
import com.schnurritv.sexmod.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlackScreenUI
extends Gui {
    private static boolean shouldBeRendered = false;
    private static double step = 0.0;
    static ResourceLocation transitionScreen = new ResourceLocation("sexmod", "textures/gui/transitionscreen.png");
    static ResourceLocation mirroredTransitionScreen = new ResourceLocation("sexmod", "textures/gui/mirroredtransitionscreen.png");
    static ResourceLocation blackScreen = new ResourceLocation("sexmod", "textures/gui/blackscreen.png");

    public static void activate() {
        shouldBeRendered = true;
    }

    @SubscribeEvent
    public void renderUI(RenderGameOverlayEvent event) {
        if (shouldBeRendered && event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            Minecraft mc = Minecraft.func_71410_x();
            float xOffset = (float)Reference.Lerp(-900.0, 450.0, 0.5 * Math.cos((step += (double)(mc.func_193989_ak() * 0.75f)) / 25.0) + 0.5);
            mc.field_71446_o.func_110577_a(transitionScreen);
            this.func_175174_a(xOffset, 0.0f, 0, (int)(step * 1.5), 256, 256);
            mc.field_71446_o.func_110577_a(mirroredTransitionScreen);
            this.func_175174_a(xOffset + 600.0f, 0.0f, 0, (int)(step * 1.5), 256, 256);
            mc.field_71446_o.func_110577_a(blackScreen);
            this.func_175174_a(xOffset + 200.0f, 0.0f, 0, 0, 400, 256);
            if (step > 30.0) {
                SexUI.shouldBeRendered = false;
            }
            if (step > 69.0) {
                step = 0.0;
                shouldBeRendered = false;
            }
        }
    }
}
