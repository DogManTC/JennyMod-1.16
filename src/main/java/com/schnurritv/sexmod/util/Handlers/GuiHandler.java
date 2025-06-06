/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.network.IGuiHandler
 */
package com.schnurritv.sexmod.util.Handlers;

import com.schnurritv.sexmod.girls.GirlEntity;
import com.schnurritv.sexmod.gui.ContainerUI;
import com.schnurritv.sexmod.gui.MenuUI;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler
implements IGuiHandler {
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) {
            return new ContainerUI();
        }
        return null;
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) {
            ArrayList<GirlEntity> girls = GirlEntity.getGirlsByPos(new BlockPos(x, y, z));
            for (GirlEntity girl : girls) {
                if (!girl.animationParameters.get((Object)GirlEntity.AnimationParameters.ISDOINGACTION).booleanValue()) continue;
                return null;
            }
            return new MenuUI(player.field_71071_by, x, y, z);
        }
        return null;
    }
}
