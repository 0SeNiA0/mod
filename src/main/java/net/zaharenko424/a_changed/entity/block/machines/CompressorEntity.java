package net.zaharenko424.a_changed.entity.block.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.capabilities.Capabilities;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.util.LazyOptional;
import net.neoforged.neoforge.energy.EmptyEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import net.zaharenko424.a_changed.capability.energy.EnergyConsumer;
import net.zaharenko424.a_changed.menu.CompressorMenu;
import net.zaharenko424.a_changed.registry.BlockEntityRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CompressorEntity extends AbstractMachineEntity<ItemStackHandler, EnergyConsumer> {

    private final RangedWrapper in = new RangedWrapper(inventory, 0, 2);
    private LazyOptional<RangedWrapper> inOptional = LazyOptional.of(()-> in);
    private final RangedWrapper out = new RangedWrapper(inventory, 2, 3);
    private LazyOptional<RangedWrapper> outOptional = LazyOptional.of(()-> out);
    private int progress;

    public CompressorEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.COMPRESSOR_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    ItemStackHandler initInv() {
        return new ItemStackHandler(3){
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return slot == 1 || (slot == 0 && checkItemEnergyCap(stack));
            }

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    @Override
    EnergyConsumer initEnergy() {
        return new EnergyConsumer(10000, 128, 0);
    }

    public int getProgress(){
        return progress;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new CompressorMenu(pContainerId, pPlayerInventory, this);
    }

//Eats 32/t
    @Override
    public void tick() {
        boolean changed = false;

        if(!inventory.getStackInSlot(0).isEmpty()){
            changed = energyStorage.receiveEnergyFrom(inventory.getStackInSlot(0).getCapability(Capabilities.ENERGY)
                    .orElse(EmptyEnergyStorage.INSTANCE), energyStorage.getMaxReceive(), false) != 0;
        }

        if(getEnergy() < 32){
            if(changed) update();
            return;
        }

        //do something

        if(changed) update();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.a_changed.compressor");
    }

    @Override
    protected <CT> LazyOptional<CT> getItemCap(@NotNull Capability<CT> cap, @Nullable Direction side) {
        return side == Direction.DOWN ? outOptional.cast() : inOptional.cast();
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        progress = tag.getInt("progress");
    }

    @Override
    void save(@NotNull CompoundTag tag) {
        super.save(tag);
        if(progress > 0) tag.putInt("progress", progress);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inOptional.invalidate();
        outOptional.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        inOptional = LazyOptional.of(()-> in);
        outOptional = LazyOptional.of(()-> out);
    }
}