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

public class ChangeTransitionTicks
implements IMessage {
    boolean messageValid;
    int newValue;
    BlockPos girlPos;

    public ChangeTransitionTicks() {
        this.messageValid = false;
    }

    public ChangeTransitionTicks(int newValue, BlockPos girlPos) {
        this.newValue = newValue;
        this.girlPos = girlPos;
        this.messageValid = true;
    }

    public ChangeTransitionTicks(int newValue, int x, int y, int z) {
        this.newValue = newValue;
        this.girlPos = new BlockPos(x, y, z);
        this.messageValid = true;
    }

    public void fromBytes(ByteBuf buf) {
        try {
            this.newValue = buf.readInt();
            this.girlPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            this.messageValid = true;
        }
        catch (IndexOutOfBoundsException ioe) {
            System.out.println("couldn't read bytes @ChangeTransitionTicks :(");
            return;
        }
    }

    public void toBytes(ByteBuf buf) {
        if (!this.messageValid) {
            return;
        }
        buf.writeInt(this.newValue);
        buf.writeInt(this.girlPos.func_177958_n());
        buf.writeInt(this.girlPos.func_177956_o());
        buf.writeInt(this.girlPos.func_177952_p());
    }

    public static class Handler
    implements IMessageHandler<ChangeTransitionTicks, IMessage> {
        public IMessage onMessage(ChangeTransitionTicks message, MessageContext ctx) {
            if (message.messageValid) {
                ArrayList<GirlEntity> girlList = GirlEntity.getGirlsByPos(message.girlPos);
                for (GirlEntity girl : girlList) {
                    girl.actionController.transitionLengthTicks = message.newValue;
                }
            } else {
                System.out.println("received an invalid message @ChangeTransitionTicks :(");
            }
            return null;
        }
    }
}
