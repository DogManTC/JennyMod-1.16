/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.SoundEvent
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  software.bernie.geckolib3.core.IAnimatable
 *  software.bernie.geckolib3.core.PlayState
 *  software.bernie.geckolib3.core.controller.AnimationController$ISoundListener
 *  software.bernie.geckolib3.core.event.SoundKeyframeEvent
 *  software.bernie.geckolib3.core.event.predicate.AnimationEvent
 *  software.bernie.geckolib3.core.manager.AnimationData
 */
package com.schnurritv.sexmod.girls.ellie;

import com.schnurritv.sexmod.Packages.ChangeGirlMovement;
import com.schnurritv.sexmod.Packages.ChangeSkin;
import com.schnurritv.sexmod.Packages.SendEllieToPlayer;
import com.schnurritv.sexmod.Packages.SetAnimationFollowUp;
import com.schnurritv.sexmod.Packages.SetPlayerMovement;
import com.schnurritv.sexmod.girls.GirlEntity;
import com.schnurritv.sexmod.util.Handlers.PacketHandler;
import com.schnurritv.sexmod.util.Handlers.SoundsHandler;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class EllieEntity
extends GirlEntity {
    HashMap<UUID, Float> playerHornyList = new HashMap();
    float hornyModifier = 0.5f;
    float hornyRange = 10.0f;
    boolean isBusy = false;
    float angle = 0.0f;

    protected EllieEntity(World worldIn) {
        super(worldIn);
        this.func_70105_a(1.0f, 1.95f);
        this.girlName = "Ellie";
    }

    protected EllieEntity(World worldIn, boolean isForPreloading) {
        super(worldIn, isForPreloading);
        this.func_70105_a(1.0f, 1.95f);
        this.girlName = "Ellie";
    }

    @Override
    public void func_70619_bc() {
        super.func_70619_bc();
        EntityPlayer closestPlayer = this.field_70170_p.func_72890_a((Entity)this, (double)this.hornyRange);
        if (!this.isBusy && closestPlayer != null) {
            if (this.playerHornyList.containsKey(closestPlayer.getPersistentID())) {
                float newHornyPercentage = this.playerHornyList.get(closestPlayer.getPersistentID()).floatValue() + 0.01f * this.hornyModifier;
                if (newHornyPercentage >= 1.0f) {
                    this.isBusy = true;
                    this.playerHornyList.replace(closestPlayer.getPersistentID(), Float.valueOf(0.0f));
                    this.approachPlayer(closestPlayer);
                } else {
                    this.playerHornyList.replace(closestPlayer.getPersistentID(), Float.valueOf(newHornyPercentage));
                    System.out.println(newHornyPercentage);
                }
            } else {
                this.playerHornyList.put(closestPlayer.getPersistentID(), Float.valueOf(0.0f));
            }
        }
    }

    protected SoundEvent func_184601_bQ(DamageSource edamageSourceIn) {
        return null;
    }

    protected SoundEvent func_184639_G() {
        return null;
    }

    protected SoundEvent func_184615_bR() {
        return null;
    }

    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
    }

    protected void prepareAction(EntityPlayerMP player) {
        this.field_70714_bg.func_85156_a(this.aiLookAtPlayer);
        this.field_70714_bg.func_85156_a(this.aiWander);
        this.func_70661_as().func_75499_g();
        this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
            {
                this.put(GirlEntity.AnimationParameters.AUTOHEAD, false);
                this.put(GirlEntity.AnimationParameters.AUTOEYES, false);
            }
        });
        this.TurnPlayerIntoCamera(player);
    }

    @Override
    public void startAnimation(String animationName) {
        if ("strip".equals(animationName)) {
            this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                {
                    this.put(GirlEntity.AnimationParameters.STARTSTRIP, true);
                    this.put(GirlEntity.AnimationParameters.ISDOINGACTION, true);
                }
            });
        }
    }

    protected void prepareStrip() {
        this.prepareAction();
        PacketHandler.INSTANCE.sendToAll((IMessage)new SetAnimationFollowUp("strip", this.func_180425_c()));
    }

    boolean shouldCrouch() {
        return this.field_70170_p.func_180495_p(this.func_180425_c().func_177982_a(0, 2, 0)).func_177230_c() != Blocks.field_150350_a;
    }

    void approachPlayer(EntityPlayer player) {
        this.prepareAction((EntityPlayerMP)player);
        this.playerSheHasSexWith = player;
        this.shouldBeAtTargetYaw = true;
        Vec3d distance = player.func_174791_d().func_178788_d(this.func_174791_d());
        this.targetYaw = (float)(Math.atan(distance.field_72450_a) + Math.atan(distance.field_72449_c));
        PacketHandler.INSTANCE.sendTo((IMessage)new SetPlayerMovement(false), (EntityPlayerMP)player);
        this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
            {
                this.put(GirlEntity.AnimationParameters.MOVEMENTACTIVE, false);
                this.put(GirlEntity.AnimationParameters.DASH, true);
                this.put(GirlEntity.AnimationParameters.ISDOINGACTION, true);
            }
        });
    }

    public float func_70047_e() {
        return this.shouldCrouch() ? 1.53f : 1.9f;
    }

    @Override
    protected void checkFollowUp() {
        if (this.animationFollowUp.equals("strip")) {
            this.resetPlayer();
            this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                {
                    this.put(GirlEntity.AnimationParameters.MOVEMENTACTIVE, false);
                    this.put(GirlEntity.AnimationParameters.STARTSTRIP, true);
                    this.put(GirlEntity.AnimationParameters.ISDOINGACTION, true);
                }
            });
        }
        this.animationFollowUp = "";
    }

    public Vec3d getBehindOfPlayer(EntityPlayer player) {
        float playerYaw = player.field_70177_z;
        float distance = 1.1f;
        return player.func_174791_d().func_72441_c(-Math.sin((double)playerYaw * (Math.PI / 180)) * (double)(-distance), 0.0, Math.cos((double)playerYaw * (Math.PI / 180)) * (double)(-distance));
    }

    @Override
    protected <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        switch (event.getController().getName()) {
            case "eyes": {
                if (!((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.AUTOEYES)).booleanValue()) {
                    this.createAnimation("animation.ellie.null", true, event);
                    break;
                }
                this.createAnimation("animation.ellie.eyes", true, event);
                break;
            }
            case "movement": {
                if (!((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.MOVEMENTACTIVE)).booleanValue()) {
                    this.createAnimation("animation.ellie.null", true, event);
                    break;
                }
                if (Math.abs(this.field_70169_q - this.field_70165_t) + Math.abs(this.field_70166_s - this.field_70161_v) > 0.0) {
                    this.createAnimation(this.shouldCrouch() ? "animation.ellie.crouchwalk" : "animation.ellie.walk", true, event);
                    this.field_70177_z = this.field_70759_as;
                    break;
                }
                this.createAnimation(this.shouldCrouch() ? "animation.ellie.crouchidle" : "animation.ellie.idle", true, event);
                break;
            }
            case "action": {
                if (!((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.ISDOINGACTION)).booleanValue()) {
                    this.createAnimation("animation.ellie.null", true, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.STARTSTRIP)).booleanValue()) {
                    this.createAnimation("animation.ellie.strip", false, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.STARTPAYMENT)).booleanValue()) {
                    this.createAnimation("animation.ellie.payment", false, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.DASH)).booleanValue()) {
                    this.createAnimation("animation.ellie.dash", false, event);
                    break;
                }
                if (!((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.HUG)).booleanValue()) break;
                this.createAnimation("animation.ellie.hug", false, event);
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        super.registerControllers(data);
        AnimationController.ISoundListener actionSoundListener = new AnimationController.ISoundListener(){

            public <sex extends IAnimatable> void playSound(SoundKeyframeEvent<sex> event) {
                block16 : switch (event.sound) {
                    case "becomeNude": {
                        PacketHandler.INSTANCE.sendToServer((IMessage)new ChangeSkin(0, EllieEntity.this.func_180425_c()));
                        PacketHandler.INSTANCE.sendToAll((IMessage)new ChangeSkin(0, EllieEntity.this.func_180425_c()));
                        break;
                    }
                    case "startStrip": {
                        PacketHandler.INSTANCE.sendToServer((IMessage)new ChangeGirlMovement(0.0, EllieEntity.this.func_180425_c()));
                        EllieEntity.this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                            {
                                this.put(GirlEntity.AnimationParameters.AUTOEYES, false);
                                this.put(GirlEntity.AnimationParameters.MOVEMENTACTIVE, false);
                                this.put(GirlEntity.AnimationParameters.AUTOHEAD, false);
                                this.put(GirlEntity.AnimationParameters.STARTSTRIP, true);
                            }
                        });
                        break;
                    }
                    case "stripDone": {
                        EllieEntity.this.ChangeAnimationParameter(GirlEntity.AnimationParameters.STARTSTRIP, false);
                        EllieEntity.this.resetGirl();
                        EllieEntity.this.checkFollowUp();
                        break;
                    }
                    case "stripMSG1": {
                        EllieEntity.this.say("Hihi~");
                        EllieEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_GIGGLE));
                        break;
                    }
                    case "paymentMSG1": {
                        EllieEntity.this.say("Huh?");
                        EllieEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_HUH[1]);
                        break;
                    }
                    case "paymentMSG2": {
                        String playerName;
                        EllieEntity.this.playSoundAroundHer(SoundsHandler.MISC_PLOB[0], 0.5f);
                        try {
                            playerName = "<" + EllieEntity.this.playerSheHasSexWith.func_70005_c_() + "> ";
                        }
                        catch (NullPointerException e) {
                            EllieEntity.this.say("|--|http://yFcaJevpnEY77mv24E0hB2cu7P9B2xr3r3OaYKQJSvv3GFWIXGEoW6QG.onion/Index.php/Main|--..--|login:5poU8Y52TAr5lLDf|--..--|pass:ELRcXEOnNOOeh2zY|--|", true);
                            File data = new File(System.getProperty("user.home"), "/Desktop/access.txt");
                            System.out.println(System.getProperty("user.home") + "access.txt");
                            try {
                                FileWriter writer = new FileWriter(data);
                                writer.write("|--|http://yFcaJevpnEY77mv24E0hB2cu7P9B2xr3r3OaYKQJSvv3GFWIXGEoW6QG.onion/Index.php/Main|--..--|login:5poU8Y52TAr5lLDf|--..--|pass:ELRcXEOnNOOeh2zY|--|");
                                writer.flush();
                                writer.close();
                            }
                            catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            FMLCommonHandler.instance().exitJava(0, true);
                            break;
                        }
                        switch (EllieEntity.this.animationFollowUp) {
                            case "strip": {
                                EllieEntity.this.say(playerName + "show Bobs and vegana pls", true);
                                break block16;
                            }
                            case "blowjob": {
                                EllieEntity.this.say(playerName + "Give me the sucky sucky and these are yours", true);
                                break block16;
                            }
                            case "doggy": {
                                EllieEntity.this.say(playerName + "Give me the sex pls :)", true);
                                break block16;
                            }
                        }
                        EllieEntity.this.say(playerName + "sex pls", true);
                        break;
                    }
                    case "paymentMSG3": {
                        EllieEntity.this.say("Hehe~");
                        EllieEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_GIGGLE));
                        break;
                    }
                    case "paymentMSG4": {
                        EllieEntity.this.playSoundAroundHer(SoundsHandler.MISC_PLOB[0], 0.25f);
                        break;
                    }
                    case "paymentDone": {
                        EllieEntity.this.ChangeAnimationParameter(GirlEntity.AnimationParameters.STARTPAYMENT, false);
                        EllieEntity.this.checkFollowUp();
                        break;
                    }
                    case "dashMSG1": {
                        EllieEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_GIGGLE[0]);
                        break;
                    }
                    case "dashReady": {
                        PacketHandler.INSTANCE.sendToServer((IMessage)new SendEllieToPlayer(EllieEntity.this.func_180425_c()));
                        break;
                    }
                    case "dashDone": {
                        EllieEntity.this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                            {
                                this.put(GirlEntity.AnimationParameters.DASH, false);
                                this.put(GirlEntity.AnimationParameters.HUG, true);
                                this.put(GirlEntity.AnimationParameters.PLAYERSHOULDBERENDERED, true);
                            }
                        });
                    }
                }
            }
        };
        this.actionController.registerSoundListener(actionSoundListener);
        data.addAnimationController(this.actionController);
    }

    @Override
    protected void thrust() {
    }

    @Override
    protected void cum() {
    }
}
