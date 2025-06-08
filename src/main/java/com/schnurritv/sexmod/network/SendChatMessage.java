package com.schnurritv.sexmod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SendChatMessage {
    boolean messageValid;
    String chatMessage;

    public SendChatMessage() {
        this.messageValid = false;
    }

    public SendChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
        this.messageValid = true;
    }

    public static SendChatMessage decode(PacketBuffer buf) {
        SendChatMessage msg = new SendChatMessage();
        msg.chatMessage = buf.readUtf();
        msg.messageValid = true;
        return msg;
    }

    public static void encode(SendChatMessage msg, PacketBuffer buf) {
        buf.writeUtf(msg.chatMessage);
    }

    public static void handle(SendChatMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        if (!message.messageValid) {
            ctx.setPacketHandled(true);
            return;
        }
        ctx.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                ITextComponent text = new StringTextComponent(message.chatMessage);
                mc.player.sendMessage(text, mc.player.getUUID());
            }
        });
        ctx.setPacketHandled(true);
    }
}
