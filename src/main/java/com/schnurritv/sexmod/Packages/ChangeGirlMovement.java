/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.MessageContext
 */
package com.schnurritv.sexmod.Packages;

import com.schnurritv.sexmod.girls.GirlEntity;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ChangeGirlMovement
implements IMessage {
    boolean messageValid;
    double speed;
    BlockPos girlPos;

    public ChangeGirlMovement() {
        this.messageValid = false;
    }

    public ChangeGirlMovement(double speed, int x, int y, int z) {
        this.speed = speed;
        this.girlPos = new BlockPos(x, y, z);
        this.messageValid = true;
    }

    public ChangeGirlMovement(double speed, BlockPos girlPos) {
        this.girlPos = girlPos;
        this.speed = speed;
        this.messageValid = true;
    }

    public void fromBytes(ByteBuf buf) {
        try {
            this.speed = buf.readDouble();
            this.girlPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            this.messageValid = true;
        }
        catch (IndexOutOfBoundsException ioe) {
            System.out.println("couldn't read bytes @ChangeGirlMovement :(");
            return;
        }
    }

    public void toBytes(ByteBuf buf) {
        if (!this.messageValid) {
            return;
        }
        buf.writeDouble(this.speed);
        buf.writeInt(this.girlPos.func_177958_n());
        buf.writeInt(this.girlPos.func_177956_o());
        buf.writeInt(this.girlPos.func_177952_p());
    }

    public static class Handler
    implements IMessageHandler<ChangeGirlMovement, IMessage> {
        public IMessage onMessage(ChangeGirlMovement message, MessageContext ctx) {
            if (message.messageValid) {
                ArrayList<GirlEntity> girlList = GirlEntity.getGirlsByPos(message.girlPos);
                for (GirlEntity girl : girlList) {
                    girl.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(message.speed);
                }
            } else {
                System.out.println("recieved an unvalid message @ChangeGirlMovement :(");
            }
            return null;
        }
    }
}
