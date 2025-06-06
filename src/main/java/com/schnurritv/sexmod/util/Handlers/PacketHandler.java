/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.network.NetworkRegistry
 *  net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
 *  net.minecraftforge.fml.relauncher.Side
 */
package com.schnurritv.sexmod.util.Handlers;

import com.schnurritv.sexmod.Packages.ChangeAnimationParameter;
import com.schnurritv.sexmod.Packages.ChangeGirlMovement;
import com.schnurritv.sexmod.Packages.ChangeSkin;
import com.schnurritv.sexmod.Packages.ChangeTransitionTicks;
import com.schnurritv.sexmod.Packages.ResetGirl;
import com.schnurritv.sexmod.Packages.SendChatMessage;
import com.schnurritv.sexmod.Packages.SendEllieToPlayer;
import com.schnurritv.sexmod.Packages.SendJennyToDoggy;
import com.schnurritv.sexmod.Packages.SendPaymentItems;
import com.schnurritv.sexmod.Packages.SetAnimationFollowUp;
import com.schnurritv.sexmod.Packages.SetPlayerForGirl;
import com.schnurritv.sexmod.Packages.SetPlayerMovement;
import com.schnurritv.sexmod.Packages.TeleportPlayer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static SimpleNetworkWrapper INSTANCE;
    private static int ID;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("sexmodchannel");
        INSTANCE.registerMessage(ChangeSkin.Handler.class, ChangeSkin.class, PacketHandler.nextID(), Side.SERVER);
        INSTANCE.registerMessage(ChangeSkin.Handler.class, ChangeSkin.class, PacketHandler.nextID(), Side.CLIENT);
        INSTANCE.registerMessage(ChangeAnimationParameter.Handler.class, ChangeAnimationParameter.class, PacketHandler.nextID(), Side.SERVER);
        INSTANCE.registerMessage(ChangeAnimationParameter.Handler.class, ChangeAnimationParameter.class, PacketHandler.nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SendChatMessage.Handler.class, SendChatMessage.class, PacketHandler.nextID(), Side.CLIENT);
        INSTANCE.registerMessage(ChangeGirlMovement.Handler.class, ChangeGirlMovement.class, PacketHandler.nextID(), Side.SERVER);
        INSTANCE.registerMessage(SetPlayerMovement.Handler.class, SetPlayerMovement.class, PacketHandler.nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SetAnimationFollowUp.Handler.class, SetAnimationFollowUp.class, PacketHandler.nextID(), Side.CLIENT);
        INSTANCE.registerMessage(TeleportPlayer.Handler.class, TeleportPlayer.class, PacketHandler.nextID(), Side.SERVER);
        INSTANCE.registerMessage(ChangeTransitionTicks.Handler.class, ChangeTransitionTicks.class, PacketHandler.nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SendPaymentItems.Handler.class, SendPaymentItems.class, PacketHandler.nextID(), Side.CLIENT);
        INSTANCE.registerMessage(ResetGirl.Handler.class, ResetGirl.class, PacketHandler.nextID(), Side.SERVER);
        INSTANCE.registerMessage(SendJennyToDoggy.Handler.class, SendJennyToDoggy.class, PacketHandler.nextID(), Side.SERVER);
        INSTANCE.registerMessage(SetPlayerForGirl.Handler.class, SetPlayerForGirl.class, PacketHandler.nextID(), Side.SERVER);
        INSTANCE.registerMessage(SendEllieToPlayer.Handler.class, SendEllieToPlayer.class, PacketHandler.nextID(), Side.SERVER);
    }

    static {
        ID = 0;
    }
}
