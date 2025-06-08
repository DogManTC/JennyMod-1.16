package com.schnurritv.sexmod.network;

import com.schnurritv.sexmod.Main;
import com.schnurritv.sexmod.entity.JennyEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class ChangeSkin {
    private int entityId;
    private int skinIndex;
    private boolean messageValid;

    public ChangeSkin() {
        this.messageValid = false;
    }

    public ChangeSkin(int entityId, int skinIndex) {
        this.entityId = entityId;
        this.skinIndex = skinIndex;
        this.messageValid = true;
    }

    public static ChangeSkin decode(PacketBuffer buf) {
        ChangeSkin msg = new ChangeSkin();
        msg.entityId = buf.readInt();
        msg.skinIndex = buf.readInt();
        msg.messageValid = true;
        return msg;
    }

    public static void encode(ChangeSkin msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeInt(msg.skinIndex);
    }

    public static void handle(ChangeSkin message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        if (!message.messageValid) {
            ctx.setPacketHandled(true);
            return;
        }
        ctx.enqueueWork(() -> {
            if (ctx.getSender() != null) {
                // server side
                ServerPlayerEntity player = ctx.getSender();
                ServerWorld world = player.getLevel();
                Entity e = world.getEntity(message.entityId);
                if (e instanceof JennyEntity) {
                    ((JennyEntity) e).setSkinIndex(message.skinIndex);
                    Main.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> e),
                            new ChangeSkin(message.entityId, message.skinIndex));
                }
            } else {
                // client side
                Entity e = Minecraft.getInstance().level.getEntity(message.entityId);
                if (e instanceof JennyEntity) {
                    ((JennyEntity) e).setSkinIndex(message.skinIndex);
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
