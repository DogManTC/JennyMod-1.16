/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.MessageContext
 *  net.minecraftforge.fml.relauncher.Side
 */
package com.schnurritv.sexmod.Packages;

import com.schnurritv.sexmod.events.HandlePlayerMovement;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SetPlayerMovement
implements IMessage {
    boolean messageValid;
    boolean setActive;

    public SetPlayerMovement(boolean setActive) {
        this.setActive = setActive;
        this.messageValid = true;
    }

    public SetPlayerMovement() {
        this.messageValid = false;
    }

    public void fromBytes(ByteBuf buf) {
        this.setActive = buf.readBoolean();
        this.messageValid = true;
    }

    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.setActive);
        this.messageValid = true;
    }

    public static class Handler
    implements IMessageHandler<SetPlayerMovement, IMessage> {
        public IMessage onMessage(SetPlayerMovement message, MessageContext ctx) {
            if (message.messageValid && ctx.side == Side.CLIENT) {
                HandlePlayerMovement.active = message.setActive;
                Minecraft.func_71410_x().field_71439_g.func_70016_h(0.0, 0.0, 0.0);
            } else {
                System.out.println("received an invalid message @SetPlayerMovement :(");
            }
            return null;
        }
    }
}
