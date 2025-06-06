/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.network.ByteBufUtils
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.MessageContext
 */
package com.schnurritv.sexmod.Packages;

import com.schnurritv.sexmod.girls.GirlEntity;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ChangeAnimationParameter
implements IMessage {
    boolean messageValid;
    EnumMap<GirlEntity.AnimationParameters, Boolean> parameters = new EnumMap(GirlEntity.AnimationParameters.class);
    BlockPos entityPos;

    public ChangeAnimationParameter() {
        this.messageValid = false;
    }

    public ChangeAnimationParameter(BlockPos entityPos, GirlEntity.AnimationParameters parameterName, boolean parameterValue) {
        this.parameters.put(parameterName, parameterValue);
        this.entityPos = entityPos;
        this.messageValid = true;
    }

    public ChangeAnimationParameter(BlockPos entityPos, EnumMap<GirlEntity.AnimationParameters, Boolean> parameters) {
        this.parameters = parameters;
        this.entityPos = entityPos;
        this.messageValid = true;
    }

    public void fromBytes(ByteBuf buf) {
        this.entityPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        int amount = buf.readInt();
        for (int i = 0; i < amount; ++i) {
            String key = ByteBufUtils.readUTF8String((ByteBuf)buf);
            boolean value = buf.readBoolean();
            this.parameters.put(GirlEntity.AnimationParameters.valueOf(key), value);
        }
        this.messageValid = true;
    }

    public void toBytes(ByteBuf buf) {
        if (!this.messageValid) {
            return;
        }
        buf.writeInt(this.entityPos.func_177958_n());
        buf.writeInt(this.entityPos.func_177956_o());
        buf.writeInt(this.entityPos.func_177952_p());
        buf.writeInt(this.parameters.size());
        for (Map.Entry<GirlEntity.AnimationParameters, Boolean> parameter : this.parameters.entrySet()) {
            ByteBufUtils.writeUTF8String((ByteBuf)buf, (String)parameter.getKey().name());
            buf.writeBoolean(parameter.getValue().booleanValue());
        }
    }

    public static class Handler
    implements IMessageHandler<ChangeAnimationParameter, IMessage> {
        public IMessage onMessage(ChangeAnimationParameter message, MessageContext ctx) {
            if (message.messageValid) {
                if (!GirlEntity.getGirlsByPos(message.entityPos).isEmpty()) {
                    ArrayList<GirlEntity> girlList = GirlEntity.getGirlsByPos(message.entityPos);
                    for (GirlEntity girl : girlList) {
                        for (Map.Entry<GirlEntity.AnimationParameters, Boolean> parameter : message.parameters.entrySet()) {
                            girl.animationParameters.replace(parameter.getKey(), parameter.getValue());
                        }
                    }
                }
            } else {
                System.out.println("recieved an unvalid message @ChangeAnimationParameter :(");
            }
            return null;
        }
    }
}
