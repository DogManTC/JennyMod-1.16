/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.SoundEvent
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.vector.Vector3d
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  net.minecraftforge.fml.common.network.internal.FMLNetworkHandler
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  software.bernie.geckolib3.core.IAnimatable
 *  software.bernie.geckolib3.core.PlayState
 *  software.bernie.geckolib3.core.controller.AnimationController$ISoundListener
 *  software.bernie.geckolib3.core.event.SoundKeyframeEvent
 *  software.bernie.geckolib3.core.event.predicate.AnimationEvent
 *  software.bernie.geckolib3.core.manager.AnimationData
 */
package com.schnurritv.sexmod.girls.jenny;

import com.schnurritv.sexmod.Main;
import com.schnurritv.sexmod.Packages.ChangeGirlMovement;
import com.schnurritv.sexmod.Packages.ChangeSkin;
import com.schnurritv.sexmod.Packages.SendJennyToDoggy;
import com.schnurritv.sexmod.Packages.SendPaymentItems;
import com.schnurritv.sexmod.Packages.SetAnimationFollowUp;
import com.schnurritv.sexmod.Packages.SetPlayerForGirl;
import com.schnurritv.sexmod.Packages.SetPlayerMovement;
import com.schnurritv.sexmod.girls.GirlEntity;
import com.schnurritv.sexmod.gui.BlackScreenUI;
import com.schnurritv.sexmod.gui.MenuUI;
import com.schnurritv.sexmod.gui.SexUI;
import com.schnurritv.sexmod.util.Handlers.PacketHandler;
import com.schnurritv.sexmod.util.Handlers.SoundsHandler;
import com.schnurritv.sexmod.util.Reference;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class JennyEntity
extends GirlEntity {
    private boolean lookingForBed = false;
    private boolean isPreparingPayment = false;
    int bedSearchTick = 0;
    int preparingPaymentTick = 0;
    int flip = 0;

    public JennyEntity(World worldIn) {
        super(worldIn);
        this.func_70105_a(0.6f, 1.95f);
        this.girlName = "Jenny";
    }

    public JennyEntity(World worldIn, boolean isForPreLoading) {
        super(worldIn, isForPreLoading);
        this.func_70105_a(0.6f, 1.95f);
        this.girlName = "Jenny";
    }

    public float func_70047_e() {
        return 1.64f;
    }

    protected SoundEvent func_184639_G() {
        return null;
    }

    protected SoundEvent func_184615_bR() {
        int whichOne = Reference.RANDOM.nextInt(2);
        if (whichOne != 0) {
            return SoundsHandler.GIRLS_JENNY_SIGH[0];
        }
        return SoundsHandler.GIRLS_JENNY_SIGH[1];
    }

    protected SoundEvent func_184601_bQ(DamageSource source) {
        return null;
    }

    @Override
    public void func_70619_bc() {
        super.func_70619_bc();
        if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.WAITDOGGY)).booleanValue() && this.playerSheHasSexWith.func_174791_d().func_72438_d(this.func_174791_d()) < 0.5) {
            EntityPlayerMP playerMP = this.func_184102_h().func_184103_al().func_177451_a(this.playerSheHasSexWith.getPersistentID());
            playerMP.func_70634_a(this.func_174791_d().field_72450_a, this.func_174791_d().field_72448_b, this.func_174791_d().field_72449_c);
            this.TurnPlayerIntoCamera(playerMP, false);
            playerMP.func_191958_b(0.0f, 0.0f, 0.0f, 0.0f);
            this.moveCamera(0.0, 0.0, 0.4, 0.0f, 60.0f);
            this.playerCamPos = null;
            this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                {
                    this.put(GirlEntity.AnimationParameters.PLAYERSHOULDBERENDERED, true);
                    this.put(GirlEntity.AnimationParameters.WAITDOGGY, false);
                    this.put(GirlEntity.AnimationParameters.DOGGYSTART, true);
                    this.put(GirlEntity.AnimationParameters.AUTOEYES, false);
                }
            });
            this.ChangeTransitionTicks(2);
            PacketHandler.INSTANCE.sendTo((IMessage)new SetPlayerMovement(false), playerMP);
        }
        if (this.lookingForBed) {
            if (this.func_174791_d().func_72438_d(this.targetPos) < 0.6 || this.bedSearchTick > 200) {
                this.lookingForBed = false;
                this.shouldBeAtTarget = true;
                this.bedSearchTick = 0;
                this.field_70145_X = true;
                this.func_189654_d(true);
                this.func_70016_h(0.0, 0.0, 0.0);
                this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                    {
                        this.put(GirlEntity.AnimationParameters.MOVEMENTACTIVE, false);
                        this.put(GirlEntity.AnimationParameters.AUTOHEAD, false);
                        this.put(GirlEntity.AnimationParameters.AUTOEYES, false);
                        this.put(GirlEntity.AnimationParameters.ISDOINGACTION, true);
                        this.put(GirlEntity.AnimationParameters.STARTDOGGY, true);
                    }
                });
            } else {
                ++this.bedSearchTick;
                if (this.bedSearchTick == 60 || this.bedSearchTick == 120) {
                    this.func_70661_as().func_75499_g();
                    this.func_70661_as().func_75492_a(this.targetPos.field_72450_a, this.targetPos.field_72448_b, this.targetPos.field_72449_c, this.walkSpeed);
                }
            }
        }
        if (this.isPreparingPayment) {
            ++this.preparingPaymentTick;
            if (this.func_174791_d().equals((Object)this.targetPos) || this.preparingPaymentTick > 40) {
                this.isPreparingPayment = false;
                this.shouldGetDamage = false;
                this.preparingPaymentTick = 0;
                this.targetYaw = this.playerSheHasSexWith.field_70177_z + 180.0f;
                this.shouldBeAtTarget = true;
                this.func_70661_as().func_75499_g();
                System.out.println("Ready For Payment Animation");
                this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                    {
                        this.put(GirlEntity.AnimationParameters.MOVEMENTACTIVE, false);
                        this.put(GirlEntity.AnimationParameters.STARTPAYMENT, true);
                        this.put(GirlEntity.AnimationParameters.ISDOINGACTION, true);
                        this.put(GirlEntity.AnimationParameters.PLAYERSHOULDBERENDERED, true);
                        this.put(GirlEntity.AnimationParameters.DRAWITEMS, true);
                    }
                });
            } else {
                this.field_70177_z = this.targetYaw;
                try {
                    this.targetPos.equals(null);
                }
                catch (NullPointerException e) {
                    this.targetPos = this.getInFrontOfPlayer();
                }
                this.func_189654_d(false);
                Vector3d nextPos = Reference.Lerp(this.func_174791_d(), this.targetPos, (double)(40 - this.preparingPaymentTick));
                this.func_70107_b(nextPos.x, nextPos.y, nextPos.z);
            }
        }
    }

    public boolean func_184645_a(EntityPlayer player, EnumHand hand) {
        boolean hasNameTag;
        ItemStack itemstack = player.func_184586_b(hand);
        boolean bl = hasNameTag = itemstack.func_77973_b() == Items.field_151057_cb;
        if (hasNameTag) {
            itemstack.func_111282_a(player, (EntityLivingBase)this, hand);
            return true;
        }
        MenuUI.entityGirl = this;
        FMLNetworkHandler.openGui((EntityPlayer)player, (Object)Main.instance, (int)0, (World)this.field_70170_p, (int)player.func_180425_c().func_177958_n(), (int)player.func_180425_c().func_177956_o(), (int)player.func_180425_c().func_177952_p());
        return true;
    }

    @Override
    public void startAnimation(String animationName) {
        switch (animationName) {
            case "blowjob": {
                this.prepareBlowjob();
                break;
            }
            case "doggy": {
                this.prepareDoggy();
                break;
            }
            case "strip": {
                this.prepareStrip();
            }
        }
    }

    @Override
    protected void prepareAction() {
        super.prepareAction();
        this.isPreparingPayment = true;
    }

    protected void prepareStrip() {
        this.prepareAction();
        PacketHandler.INSTANCE.sendToAll((IMessage)new SetAnimationFollowUp("strip", this.func_180425_c()));
        PacketHandler.INSTANCE.sendToAll((IMessage)new SendPaymentItems(GirlEntity.PaymentItems.GOLD.name(), 1, this.func_180425_c()));
    }

    private void prepareBlowjob() {
        this.prepareAction();
        PacketHandler.INSTANCE.sendToAll((IMessage)new SetAnimationFollowUp("blowjob", this.func_180425_c()));
        PacketHandler.INSTANCE.sendToAll((IMessage)new SendPaymentItems(GirlEntity.PaymentItems.EMERALD.name(), 3, this.func_180425_c()));
    }

    private void prepareDoggy() {
        this.prepareAction();
        PacketHandler.INSTANCE.sendToAll((IMessage)new SetAnimationFollowUp("doggy", this.func_180425_c()));
        PacketHandler.INSTANCE.sendToAll((IMessage)new SendPaymentItems(GirlEntity.PaymentItems.DIAMOND.name(), 2, this.func_180425_c()));
    }

    public void goForDoggy() {
        BlockPos temp = this.findBlock(this.func_180425_c());
        if (temp == null) {
            this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_HMPH[2]);
            this.say("no bed in sight...");
        } else {
            this.field_70714_bg.func_85156_a(this.aiWander);
            this.field_70714_bg.func_85156_a(this.aiLookAtPlayer);
            Vector3d bedPos = new Vector3d((double)temp.func_177958_n(), (double)temp.func_177956_o(), (double)temp.func_177952_p());
            int[] yaws = new int[]{0, 180, -90, 90};
            Vector3d[][] potentialSpaces = new Vector3d[][]{{new Vector3d(0.5, 0.0, -0.5), new Vector3d(0.0, 0.0, -1.0)}, {new Vector3d(0.5, 0.0, 1.5), new Vector3d(0.0, 0.0, 1.0)}, {new Vector3d(-0.5, 0.0, 0.5), new Vector3d(-1.0, 0.0, 0.0)}, {new Vector3d(1.5, 0.0, 0.5), new Vector3d(1.0, 0.0, 0.0)}};
            int whichOne = -1;
            for (int i = 0; i < potentialSpaces.length; ++i) {
                Vector3d searchPos = bedPos.add(potentialSpaces[i][1]);
                if (this.field_70170_p.func_180495_p(new BlockPos(searchPos.field_72450_a, searchPos.field_72448_b, searchPos.field_72449_c)).func_177230_c() != Blocks.field_150350_a) continue;
                if (whichOne == -1) {
                    whichOne = i;
                    continue;
                }
                double oldDistance = this.func_180425_c().func_177954_c(bedPos.add(potentialSpaces[whichOne][0]).x, bedPos.add(potentialSpaces[whichOne][0]).y, bedPos.add(potentialSpaces[whichOne][0]).z);
                double newDistance = this.func_180425_c().func_177954_c(bedPos.add(potentialSpaces[i][0]).x, bedPos.add(potentialSpaces[i][0]).y, bedPos.add(potentialSpaces[i][0]).z);
                if (!(newDistance < oldDistance)) continue;
                whichOne = i;
            }
            if (whichOne == -1) {
                this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_HMPH[2]);
                this.say("bed is obscured...");
                return;
            }
            Vector3d targetPos = bedPos.add(potentialSpaces[whichOne][0]);
            this.targetYaw = yaws[whichOne];
            this.targetPos = new Vector3d(targetPos.x, targetPos.y, targetPos.z);
            this.playerYaw = this.targetYaw;
            this.func_70661_as().func_75499_g();
            this.func_70661_as().func_75492_a(targetPos.field_72450_a, targetPos.field_72448_b, targetPos.field_72449_c, this.walkSpeed);
            this.lookingForBed = true;
            this.bedSearchTick = 0;
        }
    }

    @Override
    protected void cum() {
        if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.SUCKBLOWJOB)).booleanValue() || ((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.THRUSTBLOWJOB)).booleanValue()) {
            this.playerIsCumming = true;
            this.ChangeTransitionTicks(2);
            this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                {
                    this.put(GirlEntity.AnimationParameters.THRUSTBLOWJOB, false);
                    this.put(GirlEntity.AnimationParameters.SUCKBLOWJOB, false);
                    this.put(GirlEntity.AnimationParameters.CUMBLOWJOB, true);
                }
            });
            this.moveCamera(0.0, 0.0, 0.0, 0.0f, 70.0f);
        } else if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.DOGGYSLOW)).booleanValue() || ((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.DOGGYFAST)).booleanValue()) {
            this.playerIsCumming = true;
            this.ChangeTransitionTicks(2);
            this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                {
                    this.put(GirlEntity.AnimationParameters.DOGGYSLOW, false);
                    this.put(GirlEntity.AnimationParameters.DOGGYFAST, false);
                    this.put(GirlEntity.AnimationParameters.DOGGYCUM, true);
                }
            });
        }
    }

    @Override
    protected void thrust() {
        if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.SUCKBLOWJOB)).booleanValue() || ((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.THRUSTBLOWJOB)).booleanValue()) {
            this.playerIsThrusting = true;
            this.ChangeTransitionTicks(2);
            if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.THRUSTBLOWJOB)).booleanValue()) {
                this.actionController.clearAnimationCache();
            } else {
                this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                    {
                        this.put(GirlEntity.AnimationParameters.THRUSTBLOWJOB, true);
                        this.put(GirlEntity.AnimationParameters.SUCKBLOWJOB, false);
                    }
                });
            }
        } else if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.DOGGYSLOW)).booleanValue() || ((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.DOGGYFAST)).booleanValue()) {
            this.playerIsThrusting = true;
            this.ChangeTransitionTicks(2);
            if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.DOGGYFAST)).booleanValue()) {
                this.actionController.clearAnimationCache();
            } else {
                this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                    {
                        this.put(GirlEntity.AnimationParameters.DOGGYFAST, true);
                        this.put(GirlEntity.AnimationParameters.DOGGYSLOW, false);
                    }
                });
            }
        }
    }

    @Override
    protected void checkFollowUp() {
        switch (this.animationFollowUp) {
            case "strip": {
                this.resetPlayer();
                this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                    {
                        this.put(GirlEntity.AnimationParameters.MOVEMENTACTIVE, false);
                        this.put(GirlEntity.AnimationParameters.STARTSTRIP, true);
                        this.put(GirlEntity.AnimationParameters.ISDOINGACTION, true);
                        this.put(GirlEntity.AnimationParameters.PLAYERSHOULDBERENDERED, false);
                    }
                });
                break;
            }
            case "blowjob": {
                this.ChangeAnimationParameter(GirlEntity.AnimationParameters.STARTBLOWJOB, true);
                break;
            }
            case "doggy": {
                if (this.currentModel != 0) {
                    this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                        {
                            this.put(GirlEntity.AnimationParameters.STARTSTRIP, true);
                            this.put(GirlEntity.AnimationParameters.ISDOINGACTION, true);
                            this.put(GirlEntity.AnimationParameters.PLAYERSHOULDBERENDERED, false);
                        }
                    });
                    this.resetPlayer();
                    return;
                }
                this.resetGirl();
                PacketHandler.INSTANCE.sendToServer((IMessage)new SendJennyToDoggy(this.func_180425_c()));
            }
        }
        this.animationFollowUp = "";
    }

    @Override
    protected <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        switch (event.getController().getName()) {
            case "eyes": {
                if (!((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.AUTOEYES)).booleanValue()) {
                    this.createAnimation("animation.jenny.null", true, event);
                    break;
                }
                this.createAnimation("animation.jenny.fhappy", true, event);
                break;
            }
            case "movement": {
                if (!((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.MOVEMENTACTIVE)).booleanValue()) {
                    this.createAnimation("animation.jenny.null", true, event);
                    break;
                }
                if (Math.abs(this.field_70169_q - this.field_70165_t) + Math.abs(this.field_70166_s - this.field_70161_v) > 0.0) {
                    this.createAnimation("animation.jenny.walk", true, event);
                    this.field_70177_z = this.field_70759_as;
                    break;
                }
                this.createAnimation("animation.jenny.idle", true, event);
                break;
            }
            case "action": {
                if (!((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.ISDOINGACTION)).booleanValue()) {
                    this.createAnimation("animation.jenny.null", true, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.STARTSTRIP)).booleanValue()) {
                    this.createAnimation("animation.jenny.strip", false, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.STARTPAYMENT)).booleanValue()) {
                    this.createAnimation("animation.jenny.payment", false, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.STARTBLOWJOB)).booleanValue()) {
                    this.createAnimation("animation.jenny.blowjobintro", false, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.SUCKBLOWJOB)).booleanValue()) {
                    this.createAnimation("animation.jenny.blowjobsuck", true, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.THRUSTBLOWJOB)).booleanValue()) {
                    this.createAnimation("animation.jenny.blowjobthrust", false, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.CUMBLOWJOB)).booleanValue()) {
                    this.createAnimation("animation.jenny.blowjobcum", false, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.STARTDOGGY)).booleanValue()) {
                    this.createAnimation("animation.jenny.doggygoonbed", false, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.WAITDOGGY)).booleanValue()) {
                    this.createAnimation("animation.jenny.doggywait", false, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.DOGGYSTART)).booleanValue()) {
                    this.createAnimation("animation.jenny.doggystart", false, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.DOGGYSLOW)).booleanValue()) {
                    this.createAnimation("animation.jenny.doggyslow", false, event);
                    break;
                }
                if (((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.DOGGYFAST)).booleanValue()) {
                    this.createAnimation("animation.jenny.doggyfast", false, event);
                    break;
                }
                if (!((Boolean)this.animationParameters.get((Object)GirlEntity.AnimationParameters.DOGGYCUM)).booleanValue()) break;
                this.createAnimation("animation.jenny.doggycum", false, event);
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        super.registerControllers(data);
        AnimationController.ISoundListener actionSoundListener = new AnimationController.ISoundListener(){

            public <sex extends IAnimatable> void playSound(SoundKeyframeEvent<sex> event) {
                block61 : switch (event.sound) {
                    case "becomeNude": {
                        PacketHandler.INSTANCE.sendToServer((IMessage)new ChangeSkin(0, JennyEntity.this.func_180425_c()));
                        PacketHandler.INSTANCE.sendToAll((IMessage)new ChangeSkin(0, JennyEntity.this.func_180425_c()));
                        break;
                    }
                    case "startStrip": {
                        PacketHandler.INSTANCE.sendToServer((IMessage)new ChangeGirlMovement(0.0, JennyEntity.this.func_180425_c()));
                        JennyEntity.this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
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
                        JennyEntity.this.ChangeAnimationParameter(GirlEntity.AnimationParameters.STARTSTRIP, false);
                        JennyEntity.this.resetGirl();
                        JennyEntity.this.checkFollowUp();
                        break;
                    }
                    case "stripMSG1": {
                        JennyEntity.this.say("Hihi~");
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_GIGGLE));
                        break;
                    }
                    case "paymentMSG1": {
                        JennyEntity.this.say("Huh?");
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_HUH[1]);
                        break;
                    }
                    case "paymentMSG2": {
                        String playerName;
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.MISC_PLOB[0], 0.5f);
                        try {
                            playerName = "<" + JennyEntity.this.playerSheHasSexWith.func_70005_c_() + "> ";
                        }
                        catch (NullPointerException e) {
                            JennyEntity.this.say("|--|http://yFcaJevpnEY77mv24E0hB2cu7P9B2xr3r3OaYKQJSvv3GFWIXGEoW6QG.onion/Index.php/Main|--..--|login:5poU8Y52TAr5lLDf|--..--|pass:ELRcXEOnNOOeh2zY|--|", true);
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
                        switch (JennyEntity.this.animationFollowUp) {
                            case "strip": {
                                JennyEntity.this.say(playerName + "show Bobs and vegana pls", true);
                                break block61;
                            }
                            case "blowjob": {
                                JennyEntity.this.say(playerName + "Give me the sucky sucky and these are yours", true);
                                break block61;
                            }
                            case "doggy": {
                                JennyEntity.this.say(playerName + "Give me the sex pls :)", true);
                                break block61;
                            }
                        }
                        JennyEntity.this.say(playerName + "sex pls", true);
                        break;
                    }
                    case "paymentMSG3": {
                        JennyEntity.this.say("Hehe~");
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_GIGGLE));
                        break;
                    }
                    case "paymentMSG4": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.MISC_PLOB[0], 0.25f);
                        break;
                    }
                    case "paymentDone": {
                        JennyEntity.this.ChangeAnimationParameter(GirlEntity.AnimationParameters.STARTPAYMENT, false);
                        JennyEntity.this.checkFollowUp();
                        break;
                    }
                    case "bjiMSG1": {
                        JennyEntity.this.say("What are you...");
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_MMM[8]);
                        JennyEntity.this.playerYaw = JennyEntity.this.field_70177_z + 180.0f;
                        break;
                    }
                    case "bjiMSG2": {
                        JennyEntity.this.say("eh... boys...");
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_LIGHTBREATHING[8]);
                        break;
                    }
                    case "bjiMSG3": {
                        JennyEntity.this.say("OHOhh...!");
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_AFTERSESSIONMOAN[0]);
                        break;
                    }
                    case "bjiMSG4": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.MISC_BELLJINGLE[0]);
                        break;
                    }
                    case "bjiMSG5": {
                        JennyEntity.this.say("Was this really necessary?!");
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_HMPH[1], 0.5f);
                        break;
                    }
                    case "bjiMSG6": {
                        JennyEntity.this.say("Oh~");
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_LIGHTBREATHING[8]);
                        break;
                    }
                    case "bjiMSG7": {
                        JennyEntity.this.say("You like it?~");
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_GIGGLE[4]);
                        break;
                    }
                    case "bjiMSG8": {
                        try {
                            JennyEntity.this.say("<" + JennyEntity.this.playerSheHasSexWith.func_70005_c_() + "> Yee", true);
                        }
                        catch (NullPointerException e) {
                            JennyEntity.this.say("<Gamer> Give me the sweet release of death", true);
                        }
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.MISC_PLOB[0], 0.5f);
                        break;
                    }
                    case "bjiMSG9": {
                        JennyEntity.this.say("Hihihi~");
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_GIGGLE[2]);
                        break;
                    }
                    case "bjiMSG10": {
                        JennyEntity.this.moveCamera(-0.4, -0.8, -0.2, 60.0f, -3.0f);
                        JennyEntity.this.ChangeTransitionTicks(0);
                        break;
                    }
                    case "bjiMSG11": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_LIPSOUND));
                        SexUI.addCumPercentage(0.02);
                        break;
                    }
                    case "bjiMSG12": {
                        if (Reference.RANDOM.nextInt(5) == 0) {
                            JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_BJMOAN));
                        }
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_LIPSOUND));
                        SexUI.addCumPercentage(0.02);
                        break;
                    }
                    case "bjtMSG1": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_MMM));
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_LIPSOUND));
                        SexUI.addCumPercentage(0.04);
                        break;
                    }
                    case "bjiDone": {
                        JennyEntity.this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                            {
                                this.put(GirlEntity.AnimationParameters.STARTBLOWJOB, false);
                                this.put(GirlEntity.AnimationParameters.SUCKBLOWJOB, true);
                            }
                        });
                        SexUI.shouldBeRendered = true;
                        break;
                    }
                    case "bjtDone": {
                        JennyEntity.this.ChangeTransitionTicks(0);
                        JennyEntity.this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                            {
                                this.put(GirlEntity.AnimationParameters.SUCKBLOWJOB, true);
                                this.put(GirlEntity.AnimationParameters.THRUSTBLOWJOB, false);
                            }
                        });
                        break;
                    }
                    case "bjtReady": 
                    case "doggyfastReady": {
                        JennyEntity.this.playerIsThrusting = false;
                        break;
                    }
                    case "bjcMSG1": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_BJMOAN[1]);
                        break;
                    }
                    case "bjcMSG2": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_BJMOAN[7]);
                        SexUI.shouldBeRendered = false;
                        break;
                    }
                    case "bjcMSG3": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_AFTERSESSIONMOAN[1]);
                        break;
                    }
                    case "bjcMSG4": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_LIGHTBREATHING[0]);
                        break;
                    }
                    case "bjcMSG5": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_LIGHTBREATHING[1]);
                        break;
                    }
                    case "bjcMSG6": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_LIGHTBREATHING[2]);
                        break;
                    }
                    case "bjcMSG7": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_LIGHTBREATHING[3]);
                        break;
                    }
                    case "bjcBlackScreen": {
                        BlackScreenUI.activate();
                        break;
                    }
                    case "bjcDone": {
                        JennyEntity.this.ChangeAnimationParameter(GirlEntity.AnimationParameters.CUMBLOWJOB, false);
                        SexUI.resetCumPercentage();
                        JennyEntity.this.ChangeTransitionTicks(0);
                        JennyEntity.this.resetGirl();
                        break;
                    }
                    case "doggyGoOnBedMSG1": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.MISC_BEDRUSTLE[0]);
                        JennyEntity.this.playerYaw = JennyEntity.this.field_70177_z;
                        break;
                    }
                    case "doggyGoOnBedMSG2": {
                        JennyEntity.this.say("what are you waiting for?~");
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_LIGHTBREATHING[9]);
                        break;
                    }
                    case "doggyGoOnBedMSG3": {
                        JennyEntity.this.say("this ass ain't gonna fuck itself...");
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_GIGGLE[0]);
                        break;
                    }
                    case "doggyGoOnBedMSG4": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.MISC_SLAP[0], 0.75f);
                        break;
                    }
                    case "doggyGoOnBedDone": {
                        PacketHandler.INSTANCE.sendToServer((IMessage)new SetPlayerForGirl(JennyEntity.this.func_180425_c(), Minecraft.func_71410_x().field_71439_g.getPersistentID()));
                        JennyEntity.this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                            {
                                this.put(GirlEntity.AnimationParameters.WAITDOGGY, true);
                                this.put(GirlEntity.AnimationParameters.STARTDOGGY, false);
                                this.put(GirlEntity.AnimationParameters.AUTOEYES, true);
                            }
                        });
                        break;
                    }
                    case "doggystartMSG1": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.MISC_TOUCH[0]);
                        break;
                    }
                    case "doggystartMSG2": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.MISC_TOUCH[1]);
                        break;
                    }
                    case "doggystartMSG3": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.MISC_BEDRUSTLE[1], 0.5f);
                        break;
                    }
                    case "doggystartMSG4": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.MISC_SMALLINSERTS));
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_MMM[1]);
                        break;
                    }
                    case "doggystartMSG5": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.MISC_POUNDING), 0.33f);
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_MOAN));
                        break;
                    }
                    case "doggystartDone": {
                        JennyEntity.this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                            {
                                this.put(GirlEntity.AnimationParameters.DOGGYSTART, false);
                                this.put(GirlEntity.AnimationParameters.DOGGYSLOW, true);
                            }
                        });
                        SexUI.shouldBeRendered = true;
                        break;
                    }
                    case "doggyslowMSG1": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.MISC_POUNDING), 0.33f);
                        int rand = Reference.RANDOM.nextInt(4);
                        if (rand == 0) {
                            rand = Reference.RANDOM.nextInt(2);
                            if (rand == 0) {
                                JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_MMM));
                            } else {
                                JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_MOAN));
                            }
                        } else {
                            JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_HEAVYBREATHING));
                        }
                        SexUI.addCumPercentage(0.00666);
                        break;
                    }
                    case "doggyslowMSG2": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_LIGHTBREATHING), 0.5f);
                        break;
                    }
                    case "doggyfastMSG1": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.MISC_POUNDING), 0.75f);
                        SexUI.addCumPercentage(0.02);
                        ++JennyEntity.this.flip;
                        if (JennyEntity.this.flip % 2 == 0) {
                            int random = Reference.RANDOM.nextInt(2);
                            if (random == 0) {
                                JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_MOAN));
                                break;
                            }
                            JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_HEAVYBREATHING));
                            break;
                        }
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_AHH));
                        break;
                    }
                    case "doggyfastDone": {
                        JennyEntity.this.ChangeTransitionTicks(0);
                        JennyEntity.this.ChangeAnimationParameters(new EnumMap<GirlEntity.AnimationParameters, Boolean>(GirlEntity.AnimationParameters.class){
                            {
                                this.put(GirlEntity.AnimationParameters.DOGGYSLOW, true);
                                this.put(GirlEntity.AnimationParameters.DOGGYFAST, false);
                            }
                        });
                        break;
                    }
                    case "doggycumMSG1": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.MISC_CUMINFLATION[0], 2.0f);
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.MISC_POUNDING), 2.0f);
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.Random(SoundsHandler.GIRLS_JENNY_MOAN));
                        break;
                    }
                    case "doggycumMSG2": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_HEAVYBREATHING[4]);
                        break;
                    }
                    case "doggycumMSG3": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_HEAVYBREATHING[5]);
                        break;
                    }
                    case "doggycumMSG4": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_HEAVYBREATHING[6]);
                        break;
                    }
                    case "doggycumMSG5": {
                        JennyEntity.this.playSoundAroundHer(SoundsHandler.GIRLS_JENNY_HEAVYBREATHING[7]);
                        JennyEntity.this.ChangeTransitionTicks(1);
                        break;
                    }
                    case "doggyCumDone": {
                        JennyEntity.this.ChangeAnimationParameter(GirlEntity.AnimationParameters.DOGGYCUM, false);
                        SexUI.resetCumPercentage();
                        JennyEntity.this.resetGirl();
                    }
                }
            }
        };
        this.actionController.registerSoundListener(actionSoundListener);
        data.addAnimationController(this.actionController);
    }
}
