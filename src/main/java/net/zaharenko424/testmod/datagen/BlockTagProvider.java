package net.zaharenko424.testmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zaharenko424.testmod.TestMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static net.zaharenko424.testmod.registry.BlockRegistry.*;

public class BlockTagProvider extends BlockTagsProvider {
    public BlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TestMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider p_256380_) {
        tag(BlockTags.MINEABLE_WITH_AXE).add(CARDBOARD_BOX.get(),ORANGE_TREE_LOG.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BOLTED_LAB_TILE.get(),BROWN_LAB_BLOCK.get(),CHAIR.get(),CONNECTED_LAB_TILE.get(),HAZARD_BLOCK.get(),KEYPAD.get()
                        ,LAB_BLOCK.get(),LAB_DOOR.get(),LAB_TILE.get(),LATEX_CONTAINER.get(),LIBRARY_DOOR.get(),MAINTENANCE_DOOR.get()
                        ,METAL_BOX.get(),SCANNER.get(),TABLE.get(),VENT.get(),YELLOW_LAB_BLOCK.get());
        tag(BlockTags.LEAVES).add(ORANGE_LEAVES.get());
        tag(BlockTags.LOGS).add(ORANGE_TREE_LOG.get());
        tag(BlockTags.SAPLINGS).add(ORANGE_SAPLING.get());
    }
}