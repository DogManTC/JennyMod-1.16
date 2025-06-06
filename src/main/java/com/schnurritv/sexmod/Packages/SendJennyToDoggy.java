/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.MessageContext
 */
package com.schnurritv.sexmod.Packages;

import com.schnurritv.sexmod.girls.GirlEntity;
import com.schnurritv.sexmod.girls.jenny.JennyEntity;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SendJennyToDoggy
implements IMessage {
    boolean messageValid;
    BlockPos girlPos;

    public SendJennyToDoggy() {
        this.messageValid = false;
    }

    public SendJennyToDoggy(BlockPos girlPos) {
        this.girlPos = girlPos;
    }

    public void fromBytes(ByteBuf buf) {
        this.girlPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        this.messageValid = true;
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.girlPos.func_177958_n());
        buf.writeInt(this.girlPos.func_177956_o());
        buf.writeInt(this.girlPos.func_177952_p());
    }

    public static class Handler
    implements IMessageHandler<SendJennyToDoggy, IMessage> {
        public IMessage onMessage(SendJennyToDoggy message, MessageContext ctx) {
            if (message.messageValid) {
                ArrayList<GirlEntity> girlList = GirlEntity.getGirlsByPos(message.girlPos);
                for (GirlEntity girl : girlList) {
                    if (girl.field_70170_p.field_72995_K || !(girl instanceof JennyEntity)) continue;
                    ((JennyEntity)girl).goForDoggy();
                }
            } else {
                System.out.println("received an invalid message @SendGirlToSex :(");
            }
            return null;
        }
    }
}
