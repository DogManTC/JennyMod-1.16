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
 *  org.lwjgl.opengl.GL11
 */
package com.schnurritv.sexmod.gui;

import com.schnurritv.sexmod.events.HandlePlayerMovement;
import com.schnurritv.sexmod.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class SexUI
extends Gui {
    static ResourceLocation buttons = new ResourceLocation("sexmod", "textures/gui/buttons.png");
    static ResourceLocation hornyMeter = new ResourceLocation("sexmod", "textures/gui/hornymeter.png");
    public static boolean shouldBeRendered = false;
    static double cumPercentage;
    static double drawnCumPercentage;
    static float transitionStep;
    static float cumStep;
    static boolean keepSpacePressed;
    static float i;

    @SubscribeEvent
    public void renderUI(RenderGameOverlayEvent event) {
        if (shouldBeRendered && event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            Minecraft minecraft = Minecraft.func_71410_x();
            transitionStep = transitionStep < 1.0f ? (transitionStep += minecraft.func_193989_ak() / 25.0f) : 1.0f;
            GL11.glPushMatrix();
            minecraft.field_71446_o.func_110577_a(buttons);
            GL11.glScalef((float)0.35f, (float)0.35f, (float)0.35f);
            if (cumPercentage >= 1.0) {
                if (HandlePlayerMovement.isCumming) {
                    keepSpacePressed = true;
                }
                int yOffsetSpaceButton = keepSpacePressed ? 54 : 0;
                this.func_73729_b(240, 160, 0, 108 + yOffsetSpaceButton, 256, 52);
            }
            if (!keepSpacePressed) {
                int yOffsetShiftButton = HandlePlayerMovement.isThrusting ? 54 : 0;
                this.func_73729_b((int)Reference.Lerp(-200.0, 98.0, (double)transitionStep), 405, 0, yOffsetShiftButton, 158, 54);
            }
            GL11.glScalef((float)2.857143f, (float)2.857143f, (float)2.857143f);
            minecraft.field_71446_o.func_110577_a(hornyMeter);
            GL11.glScalef((float)0.75f, (float)0.75f, (float)0.75f);
            this.func_73729_b(10, (int)Reference.Lerp(-200.0, 10.0, (double)transitionStep), 0, 0, 146, 175);
            drawnCumPercentage = Reference.Lerp(drawnCumPercentage, cumPercentage, (double)minecraft.func_193989_ak());
            int height = (int)Reference.Lerp(0.0, 160.0, drawnCumPercentage);
            int textureY = (int)Reference.Lerp(167.0, 8.0, drawnCumPercentage);
            double y = Reference.Lerp(178.0, 18.0, drawnCumPercentage);
            if (!keepSpacePressed) {
                this.func_73729_b(67, (int)Reference.Lerp(-45.0, y, (double)transitionStep), 159, textureY, 32, height);
                this.func_73729_b(120, (int)Reference.Lerp(-58.0, Reference.Lerp(178.0, 149.0, 1.0 - drawnCumPercentage), (double)transitionStep), 212, (int)Reference.Lerp(169.0, 141.0, 1.0 - drawnCumPercentage), 28, (int)Reference.Lerp(1.0, 29.0, 1.0 - drawnCumPercentage));
                this.func_73729_b(18, (int)Reference.Lerp(-58.0, Reference.Lerp(178.0, 149.0, 1.0 - drawnCumPercentage), (double)transitionStep), 212, (int)Reference.Lerp(169.0, 141.0, 1.0 - drawnCumPercentage), 28, (int)Reference.Lerp(1.0, 29.0, 1.0 - drawnCumPercentage));
            } else {
                this.func_73729_b(67, (int)Reference.Lerp(18.0, -300.0, (double)(cumStep += minecraft.func_193989_ak() / 15.0f)), 159, 8, 32, 160);
            }
            GL11.glPopMatrix();
        }
    }

    public static void addCumPercentage(double amount) {
        cumPercentage = (cumPercentage += amount) > 1.0 ? 1.0 : cumPercentage;
    }

    public static void resetCumPercentage() {
        cumPercentage = 0.0;
        keepSpacePressed = false;
    }

    static {
        drawnCumPercentage = cumPercentage = 0.0;
        transitionStep = 0.0f;
        cumStep = 0.0f;
        keepSpacePressed = false;
        i = 0.0f;
    }
}
