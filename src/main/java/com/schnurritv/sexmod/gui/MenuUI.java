/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.Container
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 */
package com.schnurritv.sexmod.gui;

import com.schnurritv.sexmod.events.HandlePlayerMovement;
import com.schnurritv.sexmod.girls.GirlEntity;
import com.schnurritv.sexmod.gui.ContainerUI;
import com.schnurritv.sexmod.util.Reference;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MenuUI
extends GuiContainer {
    private static final ResourceLocation SOPHI_HEAD = new ResourceLocation("sexmod:textures/gui/sophihead.png");
    private final InventoryPlayer player;
    public static GirlEntity entityGirl;
    private GirlEntity girl = entityGirl;
    static float buttonTransitionStep;
    static float priceTransitionStep;

    public MenuUI(InventoryPlayer player, int x, int y, int z) {
        super((Container)new ContainerUI());
        this.player = player;
    }

    protected void func_146979_b(int mouseX, int mouseY) {
        this.field_146292_n.clear();
        ScaledResolution resolution = new ScaledResolution(this.field_146297_k);
        int screenWidth = resolution.func_78326_a();
        int screenHeight = resolution.func_78328_b();
        buttonTransitionStep = buttonTransitionStep < 1.0f ? (buttonTransitionStep += this.field_146297_k.func_193989_ak() / 5.0f) : 1.0f;
        int x = (int)Reference.Lerp(-30.0, 120.0, (double)buttonTransitionStep);
        this.field_146292_n.add(new GuiButton(1, screenWidth - x, screenHeight - 150, 100, 20, "Blowjob"));
        this.field_146292_n.add(new GuiButton(2, screenWidth - x, screenHeight - 120, 100, 20, "Sex"));
        this.field_146292_n.add(new GuiButton(3, screenWidth - x, screenHeight - 90, 100, 20, "Strip"));
    }

    public void func_146281_b() {
        buttonTransitionStep = 0.0f;
        priceTransitionStep = 0.0f;
        super.func_146281_b();
    }

    protected void func_146284_a(GuiButton button) {
        ArrayList<GirlEntity> girlList = GirlEntity.getGirlsByPos(this.girl.func_180425_c());
        for (GirlEntity girl : girlList) {
            girl.playerSheHasSexWith = this.player.field_70458_d;
        }
        this.player.field_70458_d.func_71053_j();
        switch (button.field_146127_k) {
            case 1: {
                HandlePlayerMovement.active = false;
                this.girl.startAnimation("blowjob");
                break;
            }
            case 2: {
                HandlePlayerMovement.active = false;
                this.girl.startAnimation("doggy");
                break;
            }
            case 3: {
                HandlePlayerMovement.active = false;
                this.girl.startAnimation("strip");
            }
        }
    }

    protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {
        ScaledResolution resolution = new ScaledResolution(this.field_146297_k);
        int screenWidth = resolution.func_78326_a();
        int screenHeight = resolution.func_78328_b();
        if (priceTransitionStep < 1.0f) {
            priceTransitionStep += this.field_146297_k.func_193989_ak() / 5.0f;
            return;
        }
        priceTransitionStep = priceTransitionStep < 2.0f ? (priceTransitionStep += this.field_146297_k.func_193989_ak() / 5.0f) : 2.0f;
        int xText = (int)Reference.Lerp(120.0, 161.0, (double)(priceTransitionStep - 1.0f));
        int xItem = (int)Reference.Lerp(96.0, 137.0, (double)(priceTransitionStep - 1.0f));
        this.func_146279_a("3x    ", screenWidth - xText, screenHeight - 132);
        this.field_146296_j.func_175042_a(new ItemStack(Items.field_151166_bC, 1), screenWidth - xItem, screenHeight - 148);
        this.func_146279_a("2x    ", screenWidth - xText, screenHeight - 102);
        this.field_146296_j.func_175042_a(new ItemStack(Items.field_151045_i, 1), screenWidth - xItem, screenHeight - 118);
        this.func_146279_a("1x    ", screenWidth - xText, screenHeight - 72);
        this.field_146296_j.func_175042_a(new ItemStack(Items.field_151043_k, 1), screenWidth - xItem, screenHeight - 88);
    }

    static {
        buttonTransitionStep = 0.0f;
        priceTransitionStep = 0.0f;
    }
}
