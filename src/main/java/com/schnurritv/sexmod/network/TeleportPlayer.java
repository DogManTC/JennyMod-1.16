/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.fml.common.network.ByteBufUtils
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.MessageContext
 *  net.minecraftforge.fml.relauncher.Side
 */
package com.schnurritv.sexmod.network;

import com.schnurritv.sexmod.util.Reference;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;
import java.util.UUID;
import java.util.function.Supplier;

public class TeleportPlayer {
    boolean messageValid;
    String playerUUID;
    Vector3d pos;
    float yaw;
    float pitch;

    public TeleportPlayer() {
        this.messageValid = false;
    }

    public TeleportPlayer(String playerUUID, Vector3d pos) {
        this.playerUUID = playerUUID;
        this.pos = pos;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.messageValid = true;
    }

    public TeleportPlayer(String playerUUID, Vector3d pos, float yaw, float pitch) {
        this.playerUUID = playerUUID;
        this.pos = pos;
        this.yaw = yaw;
        this.pitch = pitch;
        this.messageValid = true;
    }

    public TeleportPlayer(String playerUUID, double x, double y, double z, float yaw, float pitch) {
        this.playerUUID = playerUUID;
        this.pos = new Vector3d(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
        this.messageValid = true;
    }

    public static TeleportPlayer decode(PacketBuffer buf) {
        TeleportPlayer msg = new TeleportPlayer();
        msg.playerUUID = buf.readUtf();
        msg.pos = new Vector3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        msg.yaw = buf.readFloat();
        msg.pitch = buf.readFloat();
        msg.messageValid = true;
        return msg;
    }

    public static void encode(TeleportPlayer msg, PacketBuffer buf) {
        buf.writeUtf(msg.playerUUID);
        buf.writeDouble(msg.pos.x);
        buf.writeDouble(msg.pos.y);
        buf.writeDouble(msg.pos.z);
        buf.writeFloat(msg.yaw);
        buf.writeFloat(msg.pitch);
    }

    public static void handle(TeleportPlayer message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        if (!message.messageValid) {
            ctx.setPacketHandled(true);
            return;
        }
        ctx.enqueueWork(() -> {
            try {
                ServerPlayerEntity player = Reference.server.getPlayerList().getPlayer(UUID.fromString(message.playerUUID));
                if (player != null) {
                    player.connection.teleport(message.pos.x, message.pos.y, message.pos.z, message.yaw, message.pitch);
                } else {
                    System.out.println("couldn't find player with UUID: " + message.playerUUID);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ctx.setPacketHandled(true);
    }
}
