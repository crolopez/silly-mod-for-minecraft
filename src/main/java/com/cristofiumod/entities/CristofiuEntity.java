package com.cristofiumod.entities;

import com.cristofiumod.init.ModEntityType;
import com.cristofiumod.utils.RegisterHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CristofiuEntity extends AnimalEntity {

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.RED_WOOL);

    private EatGrassGoal eatGrassGoal;
    private int hogTimer;

    public CristofiuEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return ModEntityType.CRISTOFIU.get().create(this.world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.eatGrassGoal = new EatGrassGoal(this);
        setEntitiesToAvoid(0, 6.0F, 1.0F, 3.0F);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.1D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, this.eatGrassGoal);
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(4);
    }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_PIG_AMBIENT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_PIG_DEATH; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    @Override
    protected void updateAITasks() {
        this.hogTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    public void livingTick() {
        if (this.world.isRemote) {
            this.hogTimer = Math.max(0, this.hogTimer - 1);
        }
        super.livingTick();
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.hogTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    //func_233666_p_ ---> registerAttributes()
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    private void setEntitiesToAvoid(int priority, float distance, float farSpeed, float nearSpeed) {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, ZombieEntity.class, distance, nearSpeed, nearSpeed));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, CreeperEntity.class, distance, nearSpeed, nearSpeed));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, SpiderEntity.class, distance, farSpeed, nearSpeed));
    }
}
