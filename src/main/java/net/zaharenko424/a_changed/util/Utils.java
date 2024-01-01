package net.zaharenko424.a_changed.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zaharenko424.a_changed.AChanged;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Utils {

    public static <T> @NotNull ResourceKey<T> featureKey(ResourceKey<? extends Registry<T>> registry, @NotNull String str){
        return ResourceKey.create(registry,new ResourceLocation(AChanged.MODID,str));
    }

    public static void addItemOrDrop(@NotNull Player player, @NotNull ItemStack item){
        if(!player.addItem(item)) player.drop(item,false);
    }

    public static void dropItem(@NotNull Level level, @NotNull BlockPos pos, ItemStack item){
        level.addFreshEntity(new ItemEntity(level,pos.getX(),pos.getY(),pos.getZ(),item));
    }

    public static @NotNull VoxelShape rotateShape(@NotNull Direction direction, @NotNull VoxelShape source) {
        AtomicReference<VoxelShape> newShape = new AtomicReference<>(Shapes.empty());
        source.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            Vec3 min = new Vec3(minX - 0.5, minY - 0.5, minZ - 0.5);
            Vec3 max = new Vec3(maxX - 0.5, maxY - 0.5, maxZ - 0.5);
            Vec3 v1 = rotateVec3(min, direction);
            Vec3 v2 = rotateVec3(max, direction);
            VoxelShape s = Shapes.create(0.5 + Math.min(v1.x, v2.x), 0.5 + Math.min(v1.y, v2.y), 0.5 + Math.min(v1.z, v2.z),
                    0.5 + Math.max(v1.x, v2.x), 0.5 + Math.max(v1.y, v2.y), 0.5 + Math.max(v1.z, v2.z));
            newShape.set(Shapes.or(newShape.get(), s));
        });
        return newShape.get();
    }

    public static void fixCreativeDoubleBlockDrops(@NotNull Level level,@NotNull BlockPos pos, @NotNull BlockState state,@NotNull Player player){
        int part = state.getValue(StateProperties.PART2);
        if (part == 1) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.is(state.getBlock()) && blockstate.getValue(StateProperties.PART2) == 0) {
                level.setBlock(blockpos, blockstate.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }
    }

    public static @NotNull Vec3 rotateVec3(@NotNull Vec3 vec, @NotNull Direction dir) {
        double cos = 1;
        double sin = 0;
        switch (dir) {
            case SOUTH -> {
                cos = -1;
                sin = 0;
            }
            case WEST -> {
                cos = 0;
                sin = 1;
            }
            case EAST -> {
                cos = 0;
                sin = -1;
            }
        }

        return new Vec3(vec.x * cos + vec.z * sin, vec.y, vec.z * cos - vec.x * sin);
    }

    public static void writeToTag(@NotNull CompoundTag tag, @NotNull List<String> list){
        if(list.isEmpty()) return;
        tag.putInt("Size",list.size());
        for(int i=0;i<list.size();i++){
            tag.putString(String.valueOf(i),list.get(i));
        }
    }

    public static void readFromTag(@NotNull CompoundTag tag,@NotNull List<String> list){
        if(!tag.contains("Size")) return;
        int size=tag.getInt("Size");
        for(int i=0;i<size;i++){
            list.add(tag.getString(String.valueOf(i)));
        }
    }

    public static void saveAllItems(@NotNull CompoundTag tag, @NotNull NonNullList<ItemStack> list){
        if(list.isEmpty()) return;
        tag.putInt("Size",list.size());
        CompoundTag itemTag;
        for(int i=0;i<list.size();i++){
            itemTag=new CompoundTag();
            tag.put(String.valueOf(i),list.get(i).save(itemTag));
        }
    }

    public static void loadAllItems(@NotNull CompoundTag tag, @NotNull NonNullList<ItemStack> list){
        if(!tag.contains("Size")) return;
        int size=tag.getInt("Size");
        for(int i=0;i<size;i++){
            list.add(ItemStack.of(tag.getCompound(String.valueOf(i))));
        }
    }

    public static float rotlerpRad(float angle, float maxAngle, float mul) {
        float f = (mul - maxAngle) % (float) (Math.PI * 2);
        if (f < (float) -Math.PI) {
            f += (float) (Math.PI * 2);
        }

        if (f >= (float) Math.PI) {
            f -= (float) (Math.PI * 2);
        }

        return maxAngle + angle * f;
    }

    public static float quadraticArmUpdate(float limbSwing) {
        return -65.0F * limbSwing + limbSwing * limbSwing;
    }

    @NotNull
    public static String getArmorTexture(@NotNull ItemStack stack, EquipmentSlot slot, @Nullable String type) {
        ArmorItem item = (ArmorItem) stack.getItem();
        String texture = item.getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        return String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (slot ==EquipmentSlot.LEGS ? 2 : 1), type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));
    }
}