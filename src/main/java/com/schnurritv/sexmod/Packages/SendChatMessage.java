/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.MessageContext
 *  net.minecraftforge.fml.relauncher.Side
 */
package com.schnurritv.sexmod.Packages;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SendChatMessage
implements IMessage {
    boolean messageValid;
    String chatMessage;

    public SendChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
        this.messageValid = true;
    }

    public SendChatMessage() {
        this.messageValid = false;
    }

    public void fromBytes(ByteBuf buf) {
        try {
            int stringLength = buf.readInt();
            byte[] bytes = new byte[stringLength];
            for (int i = 0; i < stringLength; ++i) {
                bytes[i] = buf.readByte();
            }
            this.chatMessage = new String(bytes);
            this.messageValid = true;
            this.messageValid = true;
        }
        catch (IndexOutOfBoundsException ioe) {
            this.messageValid = false;
            System.out.println("couldn't read bytes @SendChatMessage :(");
            return;
        }
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.chatMessage.getBytes().length);
        buf.writeBytes(this.chatMessage.getBytes());
    }

    public static class Handler
    implements IMessageHandler<SendChatMessage, IMessage> {
        public IMessage onMessage(SendChatMessage message, MessageContext ctx) {
            if (message.messageValid && ctx.side == Side.CLIENT) {
                Minecraft.func_71410_x().field_71439_g.func_145747_a((ITextComponent)new TextComponentString(message.chatMessage));
            } else {
                System.out.println("recieved an unvalid message @SendChatMessage :(");
            }
            return null;
        }
    }
}
