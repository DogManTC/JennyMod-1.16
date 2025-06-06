/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.server.management.PlayerList
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.network.ByteBufUtils
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.MessageContext
 *  net.minecraftforge.fml.relauncher.Side
 */
package com.schnurritv.sexmod.Packages;

import com.schnurritv.sexmod.girls.GirlEntity;
import com.schnurritv.sexmod.util.Reference;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SetPlayerForGirl
implements IMessage {
    boolean messageValid;
    BlockPos girlPos;
    UUID playerUUID;

    public SetPlayerForGirl() {
        this.messageValid = false;
    }

    public SetPlayerForGirl(BlockPos girlPos, UUID playerUUID) {
        this.girlPos = girlPos;
        this.playerUUID = playerUUID;
        this.messageValid = true;
    }

    public void fromBytes(ByteBuf buf) {
        this.girlPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        this.playerUUID = UUID.fromString(ByteBufUtils.readUTF8String((ByteBuf)buf));
        this.messageValid = true;
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.girlPos.func_177958_n());
        buf.writeInt(this.girlPos.func_177956_o());
        buf.writeInt(this.girlPos.func_177952_p());
        ByteBufUtils.writeUTF8String((ByteBuf)buf, (String)this.playerUUID.toString());
    }

    public static class Handler
    implements IMessageHandler<SetPlayerForGirl, IMessage> {
        public IMessage onMessage(SetPlayerForGirl message, MessageContext ctx) {
            if (message.messageValid && ctx.side == Side.SERVER) {
                ArrayList<GirlEntity> girlList = GirlEntity.getGirlsByPos(message.girlPos);
                for (GirlEntity girl : girlList) {
                    PlayerList playerList = Reference.server.func_184103_al();
                    try {
                        playerList.func_177451_a(message.playerUUID).func_70005_c_();
                    }
                    catch (NullPointerException e) {
                        System.out.println("couldn't find player with UUID: " + message.playerUUID);
                        System.out.println("could only find players with thsese UUID's:");
                        for (EntityPlayerMP player : playerList.func_181057_v()) {
                            System.out.println(player.func_70005_c_() + " " + player.func_110124_au());
                        }
                        continue;
                    }
                    girl.playerSheHasSexWith = Reference.server.func_184103_al().func_177451_a(message.playerUUID);
                }
            } else {
                System.out.println("received an invalid message @SetPlayerForGirl :(");
            }
            return null;
        }
    }
}
