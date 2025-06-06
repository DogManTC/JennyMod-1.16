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
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SetAnimationFollowUp
implements IMessage {
    boolean messageValid;
    String followUp;
    BlockPos entityPos;

    public SetAnimationFollowUp() {
        this.messageValid = false;
    }

    public SetAnimationFollowUp(String followUp, BlockPos entityPos) {
        this.followUp = followUp;
        this.entityPos = entityPos;
    }

    public void fromBytes(ByteBuf buf) {
        try {
            int stringLength = buf.readInt();
            byte[] bytes = new byte[stringLength];
            for (int i = 0; i < stringLength; ++i) {
                bytes[i] = buf.readByte();
            }
            this.followUp = new String(bytes);
            this.entityPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            this.messageValid = true;
            this.messageValid = true;
        }
        catch (IndexOutOfBoundsException ioe) {
            this.messageValid = false;
            System.out.println("couldn't read bytes @SetPaymentFollowUp :(");
            return;
        }
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.followUp.getBytes().length);
        buf.writeBytes(this.followUp.getBytes());
        buf.writeInt(this.entityPos.func_177958_n());
        buf.writeInt(this.entityPos.func_177956_o());
        buf.writeInt(this.entityPos.func_177952_p());
        this.messageValid = true;
    }

    public static class Handler
    implements IMessageHandler<SetAnimationFollowUp, IMessage> {
        public IMessage onMessage(SetAnimationFollowUp message, MessageContext ctx) {
            if (message.messageValid) {
                if (!GirlEntity.getGirlsByPos(message.entityPos).isEmpty()) {
                    ArrayList<GirlEntity> girlList = GirlEntity.getGirlsByPos(message.entityPos);
                    for (GirlEntity girl : girlList) {
                        girl.animationFollowUp = message.followUp;
                    }
                }
            } else {
                System.out.println("received an invalid message @ChangeAnimationParameter :(");
            }
            return null;
        }
    }
}
