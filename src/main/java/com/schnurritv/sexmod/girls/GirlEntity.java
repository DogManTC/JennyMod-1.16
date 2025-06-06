/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITempt
 *  net.minecraft.entity.ai.EntityAIWanderAvoidWater
 *  net.minecraft.entity.ai.EntityAIWatchClosest2
 *  net.minecraft.entity.passive.EntitySheep
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.SoundEvent
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.GameType
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.network.NetworkRegistry$TargetPoint
 *  net.minecraftforge.fml.common.network.simpleimpl.IMessage
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  software.bernie.geckolib3.core.IAnimatable
 *  software.bernie.geckolib3.core.PlayState
 *  software.bernie.geckolib3.core.builder.AnimationBuilder
 *  software.bernie.geckolib3.core.controller.AnimationController
 *  software.bernie.geckolib3.core.controller.AnimationController$ISoundListener
 *  software.bernie.geckolib3.core.event.SoundKeyframeEvent
 *  software.bernie.geckolib3.core.event.predicate.AnimationEvent
 *  software.bernie.geckolib3.core.manager.AnimationData
 *  software.bernie.geckolib3.core.manager.AnimationFactory
 */
package com.schnurritv.sexmod.girls;

import com.google.common.collect.Sets;
import com.schnurritv.sexmod.Packages.ChangeAnimationParameter;
import com.schnurritv.sexmod.Packages.ChangeGirlMovement;
import com.schnurritv.sexmod.Packages.ChangeSkin;
import com.schnurritv.sexmod.Packages.ChangeTransitionTicks;
import com.schnurritv.sexmod.Packages.ResetGirl;
import com.schnurritv.sexmod.Packages.SendChatMessage;
import com.schnurritv.sexmod.Packages.TeleportPlayer;
import com.schnurritv.sexmod.events.HandlePlayerMovement;
import com.schnurritv.sexmod.util.Handlers.LootTableHandler;
import com.schnurritv.sexmod.util.Handlers.PacketHandler;
import com.schnurritv.sexmod.util.Reference;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class GirlEntity
extends EntitySheep
implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory((IAnimatable)this);
    public EntityAIBase aiWander;
    public EntityAIBase aiLookAtPlayer;
    public static ArrayList<GirlEntity> girlEntities = new ArrayList();
    public int currentModel = 1;
    protected String girlName = "girl";
    public boolean shouldBeAtTarget = false;
    public boolean shouldBeAtTargetYaw = false;
    public boolean shouldGetDamage = true;
    public Vec3d targetPos;
    public float targetYaw;
    public boolean playerIsInPosition = false;
    public Vec3d playerCamPos;
    protected float playerYaw;
    public GameType playerGameMode;
    public boolean isForPreloading = false;
    public EntityPlayer playerSheHasSexWith;
    public double walkSpeed;
    public String animationFollowUp = "";
    public boolean playerIsThrusting = false;
    public boolean playerIsCumming = false;
    public int paymentItemsAmount = 0;
    public PaymentItems paymentItem = PaymentItems.DIAMOND;
    private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet((Object[])new Item[]{Items.field_151166_bC, Items.field_151045_i, Items.field_151043_k, Items.field_151079_bi, Items.field_151100_aR});
    public AnimationController actionController = new AnimationController((IAnimatable)this, "action", 10.0f, this::predicate);
    public AnimationController movementController = new AnimationController((IAnimatable)this, "movement", 5.0f, this::predicate);
    public AnimationController eyesController = new AnimationController((IAnimatable)this, "eyes", 10.0f, this::predicate);
    public EnumMap<AnimationParameters, Boolean> animationParameters = this.createAnimationParameters();
    int preloadTick = 0;

    private EnumMap<AnimationParameters, Boolean> createAnimationParameters() {
        return new EnumMap<AnimationParameters, Boolean>(AnimationParameters.class){
            {
                this.put(AnimationParameters.AUTOHEAD, true);
                this.put(AnimationParameters.AUTOEYES, true);
                this.put(AnimationParameters.MOVEMENTACTIVE, true);
                this.put(AnimationParameters.ISDOINGACTION, false);
                this.put(AnimationParameters.STARTSTRIP, false);
                this.put(AnimationParameters.STARTBLOWJOB, false);
                this.put(AnimationParameters.SUCKBLOWJOB, false);
                this.put(AnimationParameters.CUMBLOWJOB, false);
                this.put(AnimationParameters.THRUSTBLOWJOB, false);
                this.put(AnimationParameters.STARTPAYMENT, false);
                this.put(AnimationParameters.PLAYERSHOULDBERENDERED, false);
                this.put(AnimationParameters.STARTDOGGY, false);
                this.put(AnimationParameters.WAITDOGGY, false);
                this.put(AnimationParameters.DOGGYSTART, false);
                this.put(AnimationParameters.DOGGYSLOW, false);
                this.put(AnimationParameters.DOGGYFAST, false);
                this.put(AnimationParameters.DOGGYCUM, false);
                this.put(AnimationParameters.DASH, false);
                this.put(AnimationParameters.HUG, false);
            }
        };
    }

    protected boolean func_70692_ba() {
        return false;
    }

    protected GirlEntity(World worldIn) {
        super(worldIn);
        girlEntities.add(this);
        this.walkSpeed = 0.35;
    }

    protected GirlEntity(World worldIn, boolean isForPreloading) {
        super(worldIn);
        this.isForPreloading = isForPreloading;
        this.walkSpeed = 0.35;
    }

    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(15.0);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5);
    }

    protected void func_184651_r() {
        Reference.server = this.func_184102_h();
        this.walkSpeed = 0.35;
        this.aiWander = new EntityAIWanderAvoidWater((EntityCreature)this, this.walkSpeed);
        this.aiLookAtPlayer = new EntityAIWatchClosest2((EntityLiving)this, EntityPlayer.class, 3.0f, 1.0f);
        this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAITempt((EntityCreature)this, 0.4, false, TEMPTATION_ITEMS));
    }

    public void func_70619_bc() {
        if (this.isForPreloading) {
            ++this.preloadTick;
            if (this.preloadTick > 20) {
                this.field_70170_p.func_72900_e((Entity)this);
            }
        }
        if (this.shouldBeAtTarget) {
            this.func_180426_a(this.targetPos.field_72450_a, this.targetPos.field_72448_b, this.targetPos.field_72449_c, this.targetYaw, 0.0f, 1, true);
        } else if (this.shouldBeAtTargetYaw) {
            this.func_70101_b(this.targetYaw, this.field_70125_A);
        }
    }

    protected void ChangeTransitionTicks(int ticks) {
        if (this.actionController.transitionLengthTicks != (double)ticks) {
            PacketHandler.INSTANCE.sendToAll((IMessage)new ChangeTransitionTicks(ticks, this.func_180425_c()));
        }
    }

    protected void TurnPlayerIntoCamera(EntityPlayerMP player, boolean autoMoveCamera) {
        this.playerGameMode = player.field_71134_c.func_73081_b();
        player.func_71033_a(GameType.SPECTATOR);
        Vec3d forward = player.func_189651_aD();
        if (autoMoveCamera) {
            player.func_70634_a(player.field_70165_t + forward.field_72450_a * 0.35, player.field_70163_u, player.field_70161_v + forward.field_72449_c * 0.35);
        }
        this.targetYaw = player.field_70759_as + 180.0f;
    }

    protected void TurnPlayerIntoCamera(EntityPlayerMP player) {
        this.playerGameMode = player.field_71134_c.func_73081_b();
        player.func_71033_a(GameType.SPECTATOR);
        Vec3d forward = player.func_189651_aD();
        player.func_70634_a(player.field_70165_t + forward.field_72450_a * 0.35, player.field_70163_u, player.field_70161_v + forward.field_72449_c * 0.35);
        this.targetYaw = player.field_70759_as + 180.0f;
    }

    protected void prepareAction() {
        this.field_70714_bg.func_85156_a(this.aiLookAtPlayer);
        this.field_70714_bg.func_85156_a(this.aiWander);
        this.func_70661_as().func_75499_g();
        this.ChangeAnimationParameters(new EnumMap<AnimationParameters, Boolean>(AnimationParameters.class){
            {
                this.put(AnimationParameters.AUTOHEAD, false);
                this.put(AnimationParameters.AUTOEYES, false);
            }
        });
        this.TurnPlayerIntoCamera(Objects.requireNonNull(this.func_184102_h()).func_184103_al().func_177451_a(this.playerSheHasSexWith.getPersistentID()));
        this.targetPos = this.getInFrontOfPlayer();
    }

    public static ArrayList<GirlEntity> getGirlsByPos(BlockPos pos) {
        int amountOfGirls;
        ArrayList<GirlEntity> girlList = new ArrayList<GirlEntity>();
        for (GirlEntity sophi : girlEntities) {
            if (sophi.func_180425_c().func_177958_n() != pos.func_177958_n() || sophi.func_180425_c().func_177956_o() != pos.func_177956_o() || sophi.func_180425_c().func_177952_p() != pos.func_177952_p()) continue;
            girlList.add(sophi);
        }
        try {
            amountOfGirls = ((GirlEntity)((Object)girlList.get((int)0))).field_70170_p.field_73010_i.size() + 1;
        }
        catch (IndexOutOfBoundsException e) {
            amountOfGirls = GirlEntity.girlEntities.get((int)0).field_70170_p.field_73010_i.size();
        }
        if (girlList.size() != amountOfGirls) {
            girlList.clear();
            for (GirlEntity girl : girlEntities) {
                BlockPos distance = girl.func_180425_c().func_177973_b((Vec3i)pos);
                if ((distance = new BlockPos(Math.abs(distance.func_177958_n()), Math.abs(distance.func_177956_o()), Math.abs(distance.func_177952_p()))).func_177958_n() + distance.func_177956_o() + distance.func_177952_p() > 2) continue;
                girlList.add(girl);
            }
        }
        return girlList;
    }

    protected BlockPos findBlock(BlockPos pos) {
        int step = 1;
        int dir = -1;
        BlockPos searchPos = pos;
        while (step < 22) {
            for (int move = 0; move < 2; ++move) {
                int y;
                int stepTaken;
                dir *= -1;
                for (stepTaken = 0; stepTaken < step; ++stepTaken) {
                    searchPos = searchPos.func_177982_a(0, 0, dir);
                    for (y = -3; y < 4; ++y) {
                        if (this.field_70170_p.func_180495_p(searchPos.func_177982_a(0, y, dir)).func_177230_c() != Blocks.field_150324_C) continue;
                        return searchPos.func_177982_a(0, y, dir);
                    }
                }
                for (stepTaken = 0; stepTaken < step; ++stepTaken) {
                    searchPos = searchPos.func_177982_a(dir, 0, 0);
                    for (y = -3; y < 4; ++y) {
                        if (this.field_70170_p.func_180495_p(searchPos.func_177982_a(dir, y, 0)).func_177230_c() != Blocks.field_150324_C) continue;
                        return searchPos.func_177982_a(dir, y, 0);
                    }
                }
                ++step;
            }
        }
        return null;
    }

    protected ResourceLocation func_184647_J() {
        return LootTableHandler.GIRL;
    }

    public void changeOutfit(BlockPos pos) {
        PacketHandler.INSTANCE.sendToAll((IMessage)new ChangeSkin(this.currentModel ^ 1, pos));
    }

    public abstract void startAnimation(String var1);

    protected abstract <E extends IAnimatable> PlayState predicate(AnimationEvent<E> var1);

    protected void createAnimation(String path, boolean looped, AnimationEvent event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(path, Boolean.valueOf(looped)));
    }

    protected void ChangeAnimationParameter(AnimationParameters parameterName, boolean parameterValue) {
        if (this.animationParameters.get((Object)parameterName) != parameterValue) {
            PacketHandler.INSTANCE.sendToAll((IMessage)new ChangeAnimationParameter(this.func_180425_c(), parameterName, parameterValue));
            PacketHandler.INSTANCE.sendToServer((IMessage)new ChangeAnimationParameter(this.func_180425_c(), parameterName, parameterValue));
        }
    }

    protected void ChangeAnimationParameters(EnumMap<AnimationParameters, Boolean> unfilteredParameters) {
        EnumMap<AnimationParameters, Boolean> filteredParameters = new EnumMap<AnimationParameters, Boolean>(AnimationParameters.class);
        for (Map.Entry<AnimationParameters, Boolean> parameter : unfilteredParameters.entrySet()) {
            if (this.animationParameters.get((Object)parameter.getKey()) == parameter.getValue()) continue;
            filteredParameters.put((AnimationParameters)((Enum)parameter.getKey()), parameter.getValue());
        }
        if (filteredParameters.size() != 0) {
            PacketHandler.INSTANCE.sendToAll((IMessage)new ChangeAnimationParameter(this.func_180425_c(), filteredParameters));
            PacketHandler.INSTANCE.sendToServer((IMessage)new ChangeAnimationParameter(this.func_180425_c(), filteredParameters));
        }
    }

    public void registerControllers(AnimationData data) {
        AnimationController.ISoundListener movementSoundListener = new AnimationController.ISoundListener(){

            public <EntitySophi extends IAnimatable> void playSound(SoundKeyframeEvent<EntitySophi> event) {
                if ("idle".equals(event.sound)) {
                    GirlEntity.this.ChangeTransitionTicks(10);
                }
            }
        };
        this.movementController.registerSoundListener(movementSoundListener);
        data.addAnimationController(this.movementController);
        data.addAnimationController(this.eyesController);
    }

    protected void resetPlayer() {
        this.playerCamPos = null;
        HandlePlayerMovement.active = true;
        PacketHandler.INSTANCE.sendToServer((IMessage)new ResetGirl(this.func_180425_c(), true));
    }

    protected void resetGirl() {
        this.playerCamPos = null;
        this.playerIsThrusting = false;
        this.playerIsCumming = false;
        HandlePlayerMovement.active = true;
        this.func_189654_d(false);
        this.ChangeAnimationParameters(new EnumMap<AnimationParameters, Boolean>(AnimationParameters.class){
            {
                this.put(AnimationParameters.ISDOINGACTION, false);
                this.put(AnimationParameters.AUTOHEAD, true);
                this.put(AnimationParameters.AUTOEYES, true);
                this.put(AnimationParameters.MOVEMENTACTIVE, true);
                this.put(AnimationParameters.PLAYERSHOULDBERENDERED, false);
            }
        });
        PacketHandler.INSTANCE.sendToServer((IMessage)new ChangeGirlMovement(0.5, this.func_180425_c()));
        PacketHandler.INSTANCE.sendToServer((IMessage)new ResetGirl(this.func_180425_c()));
    }

    @SideOnly(value=Side.CLIENT)
    public static void sendThrust(UUID playerUUID) {
        for (GirlEntity girl : girlEntities) {
            if (girl.playerSheHasSexWith == null || !girl.playerSheHasSexWith.getPersistentID().equals(playerUUID) || girl.playerIsThrusting) continue;
            girl.thrust();
        }
    }

    @SideOnly(value=Side.CLIENT)
    public static void sendCum(UUID playerUUID) {
        for (GirlEntity girl : girlEntities) {
            if (girl.playerSheHasSexWith == null || !girl.playerSheHasSexWith.getPersistentID().equals(playerUUID) || girl.playerIsCumming) continue;
            girl.cum();
        }
    }

    @SideOnly(value=Side.CLIENT)
    protected abstract void thrust();

    @SideOnly(value=Side.CLIENT)
    protected abstract void cum();

    protected void moveCamera(double x, double y, double z, float yaw, float pitch) {
        if (this.playerCamPos == null) {
            this.playerCamPos = this.playerSheHasSexWith.func_174791_d();
        }
        Vec3d newPos = this.playerCamPos;
        newPos = newPos.func_72441_c(-Math.sin((double)(this.playerYaw + 90.0f) * (Math.PI / 180)) * x, 0.0, Math.cos((double)(this.playerYaw + 90.0f) * (Math.PI / 180)) * x);
        newPos = newPos.func_72441_c(0.0, y, 0.0);
        newPos = newPos.func_72441_c(-Math.sin((double)this.playerYaw * (Math.PI / 180)) * z, 0.0, Math.cos((double)this.playerYaw * (Math.PI / 180)) * z);
        if (this.field_70170_p.field_72995_K) {
            PacketHandler.INSTANCE.sendToServer((IMessage)new TeleportPlayer(this.playerSheHasSexWith.getPersistentID().toString(), newPos, this.playerYaw + yaw, pitch));
        } else {
            this.playerSheHasSexWith.func_70080_a(newPos.field_72450_a, newPos.field_72448_b, newPos.field_72449_c, this.playerYaw + yaw, pitch);
            this.playerSheHasSexWith.func_70634_a(newPos.field_72450_a, newPos.field_72448_b, newPos.field_72449_c);
            this.playerSheHasSexWith.func_70016_h(0.0, 0.0, 0.0);
        }
    }

    protected abstract void checkFollowUp();

    protected void say(String msg) {
        PacketHandler.INSTANCE.sendToAllAround((IMessage)new SendChatMessage("<" + this.girlName + "> " + msg), new NetworkRegistry.TargetPoint(this.field_71093_bK, (double)this.func_180425_c().func_177958_n(), (double)this.func_180425_c().func_177956_o(), (double)this.func_180425_c().func_177952_p(), 40.0));
    }

    protected void say(String msg, boolean noPrefix) {
        if (noPrefix) {
            PacketHandler.INSTANCE.sendToAllAround((IMessage)new SendChatMessage(msg), new NetworkRegistry.TargetPoint(this.field_71093_bK, (double)this.func_180425_c().func_177958_n(), (double)this.func_180425_c().func_177956_o(), (double)this.func_180425_c().func_177952_p(), 40.0));
        }
    }

    protected void playSoundAroundHer(SoundEvent sound) {
        if (this.field_70170_p.field_72995_K) {
            this.field_70170_p.func_184134_a((double)this.func_180425_c().func_177958_n(), (double)this.func_180425_c().func_177956_o(), (double)this.func_180425_c().func_177952_p(), sound, SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
        }
        this.field_70170_p.func_184133_a(null, this.func_180425_c(), sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }

    protected void playSoundAroundHer(SoundEvent sound, float volume) {
        if (this.field_70170_p.field_72995_K) {
            this.field_70170_p.func_184134_a((double)this.func_180425_c().func_177958_n(), (double)this.func_180425_c().func_177956_o(), (double)this.func_180425_c().func_177952_p(), sound, SoundCategory.NEUTRAL, volume, 1.0f, false);
            return;
        }
        this.field_70170_p.func_184133_a(null, this.func_180425_c(), sound, SoundCategory.PLAYERS, volume, 1.0f);
    }

    protected Vec3d getInFrontOfPlayer() {
        float playerYaw = this.playerSheHasSexWith.field_70177_z;
        return this.playerSheHasSexWith.func_174791_d().func_72441_c(-Math.sin((double)playerYaw * (Math.PI / 180)), 0.0, Math.cos((double)playerYaw * (Math.PI / 180)));
    }

    public AnimationFactory getFactory() {
        return this.factory;
    }

    public static enum AnimationParameters {
        AUTOHEAD,
        AUTOEYES,
        MOVEMENTACTIVE,
        ISDOINGACTION,
        STARTSTRIP,
        STARTBLOWJOB,
        SUCKBLOWJOB,
        CUMBLOWJOB,
        THRUSTBLOWJOB,
        STARTPAYMENT,
        PLAYERSHOULDBERENDERED,
        DRAWITEMS,
        STARTDOGGY,
        WAITDOGGY,
        DOGGYSTART,
        DOGGYSLOW,
        DOGGYFAST,
        DOGGYCUM,
        DASH,
        HUG;

    }

    public static enum PaymentItems {
        DIAMOND,
        GOLD,
        EMERALD;

    }
}
