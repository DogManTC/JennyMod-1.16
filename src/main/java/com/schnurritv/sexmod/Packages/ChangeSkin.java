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

public class ChangeSkin
implements IMessage {
    boolean messageValid;
    int skinIndex;
    BlockPos girlPos;

    public ChangeSkin() {
        this.messageValid = false;
    }

    public ChangeSkin(int skinIndex, BlockPos girlPos) {
        this.skinIndex = skinIndex;
        this.girlPos = girlPos;
        this.messageValid = true;
    }

    public ChangeSkin(int skinIndex, GirlEntity sophi) {
        this.skinIndex = skinIndex;
        this.girlPos = sophi.func_180425_c();
        this.messageValid = true;
    }

    public ChangeSkin(int skinIndex, int x, int y, int z) {
        this.skinIndex = skinIndex;
        this.girlPos = new BlockPos(x, y, z);
        this.messageValid = true;
    }

    public void fromBytes(ByteBuf buf) {
        try {
            this.skinIndex = buf.readInt();
            this.girlPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            this.messageValid = true;
        }
        catch (IndexOutOfBoundsException ioe) {
            System.out.println("couldn't read bytes @ChangeSkin :(");
            return;
        }
    }

    public void toBytes(ByteBuf buf) {
        if (!this.messageValid) {
            return;
        }
        buf.writeInt(this.skinIndex);
        buf.writeInt(this.girlPos.func_177958_n());
        buf.writeInt(this.girlPos.func_177956_o());
        buf.writeInt(this.girlPos.func_177952_p());
    }

    public static class Handler
    implements IMessageHandler<ChangeSkin, IMessage> {
        public IMessage onMessage(ChangeSkin message, MessageContext ctx) {
            if (message.messageValid) {
                ArrayList<GirlEntity> girlList = GirlEntity.getGirlsByPos(message.girlPos);
                for (GirlEntity girl : girlList) {
                    girl.currentModel = message.skinIndex;
                }
            } else {
                System.out.println("recieved an unvalid message @ChangeSkin :(");
            }
            return null;
        }
    }
}
