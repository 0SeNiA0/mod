package net.zaharenko424.a_changed.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.zaharenko424.a_changed.AChanged;
import net.zaharenko424.a_changed.block.blocks.GasTank;
import net.zaharenko424.a_changed.registry.*;
import net.zaharenko424.a_changed.transfurSystem.DamageSources;
import net.zaharenko424.a_changed.transfurSystem.TransfurEvent;
import net.zaharenko424.a_changed.util.NBTUtils;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class GasTankEntity extends BlockEntity {

    private static final AABB aabb = Shapes.block().bounds().inflate(3,2,3);
    private final AABB ab = aabb.move(worldPosition.above().relative(getBlockState().getValue(GasTank.FACING),3));
    private final Vec3 pos = worldPosition.above().getCenter();
    private final BlockPos target = new BlockPos(0,0,0).relative(getBlockState().getValue(HorizontalDirectionalBlock.FACING));
    private ItemStack canister = new ItemStack(ItemRegistry.GAS_TANK_ITEM.get());
    private boolean open = false;
    private int tick = 0;

    public GasTankEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityRegistry.GAS_TANK_ENTITY.get(), p_155229_, p_155230_);
    }

    public void setCanister(ItemStack canister){
        if(!canister.is(ItemRegistry.GAS_TANK_ITEM.get())) return;
        this.canister=canister.copy();
    }

    public ItemStack canister(){
        return canister;
    }

    public boolean isOpen(){
        return open;
    }

    public boolean isEmpty(){
        return canister.getDamageValue()==canister.getMaxDamage();
    }

    public void setOpenClose(){
        if(!isEmpty()) open=!open;
    }

    public void tick(){
        if(!open) return;
        tick++;
        if(tick % 2 == 0) ((ServerLevel)level).sendParticles(AChanged.BLUE_GAS_PARTICLE.get(), pos.x, pos.y, pos.z,0, target.getX(), target.getY(), target.getZ(),.4);
        if(tick < 20) return;
        tick = 0;
        canister.hurt(1, level.random,null);
        level.playSound(null, worldPosition, SoundRegistry.GAS_LEAK.get(), SoundSource.BLOCKS);
        level.getEntitiesOfClass(LivingEntity.class,ab, DamageSources::checkTarget).forEach((entity -> {
            if(entity.hasEffect(MobEffectRegistry.FRESH_AIR.get()) || isFullHazmat(entity)) return;
            TransfurEvent.ADD_TRANSFUR_DEF.accept(entity, TransfurRegistry.GAS_WOLF_TF.get(), 5f);
        }));
        if(isEmpty()) open = false;
    }

    private boolean isFullHazmat(LivingEntity entity){
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.HAZMAT_HELMET.get())
                && entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.HAZMAT_CHESTPLATE.get())
                && entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.HAZMAT_LEGGINGS.get())
                && entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.HAZMAT_BOOTS.get());
    }

    @Override
    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);
        CompoundTag tag = NBTUtils.modTag(p_155245_);
        canister = ItemStack.of(tag.getCompound("canister"));
        open = tag.getBoolean("open");
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        super.saveAdditional(p_187471_);
        CompoundTag tag = NBTUtils.modTag(p_187471_);
        tag.put("canister", canister.save(new CompoundTag()));
        tag.putBoolean("open", open);
    }
}