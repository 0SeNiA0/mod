package net.zaharenko424.a_changed.datagen.recipe;

import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredItem;
import net.zaharenko424.a_changed.datagen.ItemTagProvider;
import net.zaharenko424.a_changed.registry.BlockRegistry;
import net.zaharenko424.a_changed.registry.DNATypeRegistry;
import net.zaharenko424.a_changed.registry.TransfurRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.zaharenko424.a_changed.registry.ItemRegistry.*;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {
    public RecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput out) {
        latexBlockItem(false, out);
        latexBlockItem(true, out);

        //TEMPORARY
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LATEX_ENCODER_COMPONENTS)
                .pattern("GAG")
                .pattern("DDD")
                .pattern("GAG")
                .define('G', ItemTagProvider.PLATES_GOLD)
                .define('A', Tags.Items.GEMS_AMETHYST)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .unlockedBy(getHasName(Items.DIAMOND), has(Tags.Items.GEMS_DIAMOND))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LATEX_PURIFIER_COMPONENTS)
                .pattern("CIC")
                .pattern("GGG")
                .pattern("III")
                .define('C', ItemTagProvider.WIRES_COPPER)
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('G', ItemTagProvider.PLATES_GOLD)
                .unlockedBy(getHasName(GOLDEN_PLATE), has(ItemTagProvider.PLATES_GOLD))
                .save(out);


        //Compressor recipes
        new CompressorRecipeBuilder("compressor", Ingredient.of(EMPTY_CANISTER.get().getDefaultInstance()), COMPRESSED_AIR_CANISTER.get().getDefaultInstance())
                .unlockedBy(getHasName(EMPTY_CANISTER), has(EMPTY_CANISTER))
                .save(out);


        //DNA extractor recipes
        DNAExtractorRecipeBuilder.of(DNATypeRegistry.APPLE_DNA).unlockedByMaterial().save(out);
        DNAExtractorRecipeBuilder.of(DNATypeRegistry.CAT_DNA).unlockedByMaterial().save(out);
        DNAExtractorRecipeBuilder.of(DNATypeRegistry.COD_DNA).unlockedByMaterial().save(out);
        DNAExtractorRecipeBuilder.of(DNATypeRegistry.SALMON_DNA).unlockedByMaterial().save(out);
        DNAExtractorRecipeBuilder.of(DNATypeRegistry.WOLF_DNA).unlockedByMaterial().save(out);


        //Latex encoder recipes
        LatexEncoderRecipeBuilder.of(TransfurRegistry.BEI_FENG_TF.get())
                .setLatexBaseIngredient(WHITE_LATEX_BASE.toStack())
                .unlockedBy(getHasName(WHITE_LATEX_BASE), has(WHITE_LATEX_BASE))
                .save(out);

        LatexEncoderRecipeBuilder.of(TransfurRegistry.BENIGN_TF.get())
                .setLatexBaseIngredient(DARK_LATEX_BASE.toStack())
                .addMiscIngredient(BLACK_LATEX_SHORTS.toStack())
                .unlockedBy(getHasName(DARK_LATEX_BASE), has(DARK_LATEX_BASE))
                .save(out);

        LatexEncoderRecipeBuilder.of(TransfurRegistry.DARK_LATEX_WOLF_F_TF.get())
                .setLatexBaseIngredient(DARK_LATEX_BASE.toStack())
                .addDNASampleIngredient(DNATypeRegistry.WOLF_DNA)
                .unlockedBy(getHasName(DARK_LATEX_BASE), has(DARK_LATEX_BASE))
                .save(out);

        LatexEncoderRecipeBuilder.of(TransfurRegistry.DARK_LATEX_WOLF_M_TF.get())
                .setLatexBaseIngredient(DARK_LATEX_BASE.toStack())
                .addDNASampleIngredient(DNATypeRegistry.WOLF_DNA)
                .unlockedBy(getHasName(DARK_LATEX_BASE), has(DARK_LATEX_BASE))
                .save(out);

        LatexEncoderRecipeBuilder.of(TransfurRegistry.HYPNO_CAT_TF.get())
                .setLatexBaseIngredient(WHITE_LATEX_BASE.toStack())
                .addDNASampleIngredient(DNATypeRegistry.CAT_DNA)
                .addMiscIngredient(Items.GLOW_BERRIES.getDefaultInstance())
                .unlockedBy(getHasName(WHITE_LATEX_BASE), has(WHITE_LATEX_BASE))
                .save(out);

        LatexEncoderRecipeBuilder.of(TransfurRegistry.PURE_WHITE_LATEX_WOLF_TF.get())
                .setLatexBaseIngredient(WHITE_LATEX_BASE.toStack())
                .addDNASampleIngredient(DNATypeRegistry.WOLF_DNA)
                .addMiscIngredient(WHITE_LATEX_BASE.get().getDefaultInstance())
                .unlockedBy(getHasName(WHITE_LATEX_BASE), has(WHITE_LATEX_BASE))
                .save(out);

        LatexEncoderRecipeBuilder.of(TransfurRegistry.LATEX_SHARK_F_TF.get())
                .setLatexBaseIngredient(WHITE_LATEX_BASE.toStack())
                .addDNASampleIngredient(DNATypeRegistry.COD_DNA)
                .addDNASampleIngredient(DNATypeRegistry.SALMON_DNA)
                .unlockedBy(getHasName(WHITE_LATEX_BASE), has(WHITE_LATEX_BASE))
                .save(out);

        LatexEncoderRecipeBuilder.of(TransfurRegistry.LATEX_SHARK_M_TF.get())
                .setLatexBaseIngredient(WHITE_LATEX_BASE.toStack())
                .addDNASampleIngredient(DNATypeRegistry.COD_DNA)
                .addDNASampleIngredient(DNATypeRegistry.SALMON_DNA)
                .unlockedBy(getHasName(WHITE_LATEX_BASE), has(WHITE_LATEX_BASE))
                .save(out);

        LatexEncoderRecipeBuilder.of(TransfurRegistry.SNOW_LEOPARD_F_TF.get())
                .setLatexBaseIngredient(WHITE_LATEX_BASE.toStack())
                .addDNASampleIngredient(DNATypeRegistry.CAT_DNA)
                .addMiscIngredient(Items.SNOWBALL.getDefaultInstance())
                .unlockedBy(getHasName(WHITE_LATEX_BASE), has(WHITE_LATEX_BASE))
                .save(out);

        LatexEncoderRecipeBuilder.of(TransfurRegistry.SNOW_LEOPARD_M_TF.get())
                .setLatexBaseIngredient(WHITE_LATEX_BASE.toStack())
                .addDNASampleIngredient(DNATypeRegistry.CAT_DNA)
                .addMiscIngredient(Items.SNOWBALL.getDefaultInstance())
                .unlockedBy(getHasName(WHITE_LATEX_BASE), has(WHITE_LATEX_BASE))
                .save(out);

        LatexEncoderRecipeBuilder.of(TransfurRegistry.SPECIAL_TF.get())
                .setLatexBaseIngredient(WHITE_LATEX_BASE.toStack())
                .addMiscIngredient(DARK_LATEX_BASE.toStack())
                .unlockedBy(getHasName(WHITE_LATEX_BASE), has(WHITE_LATEX_BASE))
                .save(out);

        LatexEncoderRecipeBuilder.of(TransfurRegistry.WHITE_LATEX_WOLF_F_TF.get())
                .setLatexBaseIngredient(WHITE_LATEX_BASE.toStack())
                .addDNASampleIngredient(DNATypeRegistry.WOLF_DNA)
                .unlockedBy(getHasName(WHITE_LATEX_BASE), has(WHITE_LATEX_BASE))
                .save(out);

        LatexEncoderRecipeBuilder.of(TransfurRegistry.WHITE_LATEX_WOLF_M_TF.get())
                .setLatexBaseIngredient(WHITE_LATEX_BASE.toStack())
                .addDNASampleIngredient(DNATypeRegistry.WOLF_DNA)
                .unlockedBy(getHasName(WHITE_LATEX_BASE), has(WHITE_LATEX_BASE))
                .save(out);


        //Latex purifier recipes
        new LatexPurifierRecipeBuilder(Ingredient.of(DARK_LATEX_ITEM.get()), DARK_LATEX_BASE.toStack())
                .unlockedBy(getHasName(DARK_LATEX_ITEM), has(DARK_LATEX_ITEM))
                .save(out);

        new LatexPurifierRecipeBuilder(Ingredient.of(WHITE_LATEX_ITEM.get()), WHITE_LATEX_BASE.toStack())
                .unlockedBy(getHasName(WHITE_LATEX_ITEM), has(WHITE_LATEX_ITEM))
                .save(out);

        //Crafting recipes
        Criterion<?> hasCopper = has(Tags.Items.INGOTS_COPPER);
        Criterion<?> hasIron = has(Tags.Items.INGOTS_IRON);
        Criterion<?> hasCopperPlates = has(ItemTagProvider.PLATES_COPPER);
        Criterion<?> hasIronPlates = has(ItemTagProvider.PLATES_IRON);
        Criterion<?> hasLRCoating = has(LATEX_RESISTANT_COATING);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AIR_CONDITIONER_ITEM)
                .pattern("IBI")
                .pattern("BCB")
                .pattern("IBI")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('B', Items.IRON_BARS)
                .define('C', COPPER_COIL)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, BIG_LAB_DOOR_ITEM)
                .requires(LAB_DOOR_ITEM)
                .requires(LAB_DOOR_ITEM)
                .unlockedBy(getHasName(LAB_DOOR_ITEM), has(LAB_DOOR_ITEM))
                .save(out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, BIG_LAB_LAMP_ITEM)
                .requires(LAB_LAMP_ITEM, 2)
                .unlockedBy(getHasName(LAB_LAMP_ITEM), has(LAB_LAMP_ITEM))
                .save(out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, BIG_LIBRARY_DOOR_ITEM)
                .requires(LIBRARY_DOOR_ITEM)
                .requires(LIBRARY_DOOR_ITEM)
                .unlockedBy(getHasName(LIBRARY_DOOR_ITEM), has(LIBRARY_DOOR_ITEM))
                .save(out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, BIG_MAINTENANCE_DOOR_ITEM)
                .requires(MAINTENANCE_DOOR_ITEM)
                .requires(MAINTENANCE_DOOR_ITEM)
                .unlockedBy(getHasName(MAINTENANCE_DOOR_ITEM), has(MAINTENANCE_DOOR_ITEM))
                .save(out);

        labBlock(BLUE_LAB_TILE_ITEM, Items.LIGHT_BLUE_CONCRETE, out);

        stonecuttingAllToAll(new DeferredItem[]{BLUE_LAB_TILE_ITEM, BLUE_LAB_TILE_SLAB_ITEM, BLUE_LAB_TILE_STAIRS_ITEM,
                BOLTED_BLUE_LAB_TILE_ITEM, CONNECTED_BLUE_LAB_TILE_ITEM}, RecipeCategory.BUILDING_BLOCKS, out);


        labBlock(BROWN_LAB_BLOCK_ITEM, Items.BROWN_CONCRETE, out);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, CANNED_ORANGES_ITEM)
                .pattern("SSS")
                .pattern("OSO")
                .pattern("OCO")
                .define('S', Items.SUGAR)
                .define('O', ORANGE_ITEM)
                .define('C', METAL_CAN_ITEM)
                .unlockedBy(getHasName(ORANGE_ITEM), has(ORANGE_ITEM))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CAPACITOR_ITEM)
                .pattern("ICI")
                .pattern("PPP")
                .pattern("III")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('C', ItemTagProvider.WIRES_COPPER)
                .define('P', POWER_CELL)
                .unlockedBy(getHasName(POWER_CELL), has(POWER_CELL))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CARDBOARD)
                .pattern("P")
                .pattern("P")
                .pattern("P")
                .define('P', Items.PAPER)
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CARPET_BLOCK_ITEM,4)
                .pattern("WW")
                .pattern("WW")
                .define('W', ItemTags.WOOL)
                .unlockedBy(getHasName(Items.WHITE_WOOL), has(ItemTags.WOOL))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CARDBOARD_BOX_ITEM)
                .pattern("CC")
                .pattern("CC")
                .define('C', CARDBOARD)
                .unlockedBy(getHasName(CARDBOARD), has(CARDBOARD))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CHAIR_ITEM, 3)
                .pattern("I ")
                .pattern("II")
                .pattern("BB")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('B', Items.IRON_BARS)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, COMPRESSOR_ITEM)
                .pattern("WCW")
                .pattern("P P")
                .pattern("III")
                .define('W', COPPER_WIRE_ITEM)
                .define('C', POWER_CELL)
                .define('P', Items.PISTON)
                .define('I', ItemTagProvider.PLATES_IRON)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        //TODO computer, make better?
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, COMPUTER_ITEM, 2)
                .pattern("PPP")
                .pattern("ILI")
                .pattern("WGW")
                .define('P', Tags.Items.GLASS_PANES)
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('L', LAB_LAMP_ITEM)
                .define('W', ItemTagProvider.WIRES_COPPER)
                .define('G', ItemTagProvider.PLATES_GOLD)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, COPPER_COIL)
                .pattern("CCC")
                .pattern("CIC")
                .pattern("CCC")
                .define('C', ItemTagProvider.WIRES_COPPER)
                .define('I', ItemTagProvider.PLATES_IRON)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, COPPER_PLATE, 2)
                .pattern("CCC")
                .define('C', Tags.Items.INGOTS_COPPER)
                .unlockedBy(getHasName(Items.COPPER_INGOT), hasCopper)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, COPPER_WIRE_ITEM, 12)
                .pattern("CCC")
                .define('C', ItemTagProvider.PLATES_COPPER)
                .unlockedBy(getHasName(COPPER_PLATE), hasCopperPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, COPPER_WRENCH)
                .pattern("C C")
                .pattern(" C ")
                .pattern(" C ")
                .define('C', ItemTagProvider.PLATES_COPPER)
                .unlockedBy(getHasName(COPPER_PLATE), hasCopperPlates)
                .save(out);

        //TODO cryo chamber?

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CUP_ITEM, 2)
                .pattern("C C")
                .pattern(" C ")
                .define('C', Items.CLAY_BALL)
                .unlockedBy(getHasName(Items.CLAY_BALL), has(Items.CLAY_BALL))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DANGER_SIGN_ITEM,3)
                .pattern(" B ")
                .pattern("BYB")
                .define('Y', Items.YELLOW_CONCRETE)
                .define('B', Items.BLACK_CONCRETE)
                .unlockedBy(getHasName(Items.YELLOW_CONCRETE), has(Items.YELLOW_CONCRETE))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DARK_LATEX_ICE_ITEM, 4)
                .pattern("SS")
                .pattern("SS")
                .define('S', DARK_LATEX_CRYSTAL_SHARD)
                .unlockedBy(getHasName(DARK_LATEX_CRYSTAL_SHARD), has(DARK_LATEX_CRYSTAL_SHARD))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.DARK_LATEX_PUDDLE_F,3)
                .pattern("L L")
                .pattern(" L ")
                .define('L', DARK_LATEX_ITEM)
                .unlockedBy(getHasName(DARK_LATEX_ITEM), has(DARK_LATEX_ITEM))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.DARK_LATEX_PUDDLE_M,3)
                .pattern(" L ")
                .pattern("L L")
                .define('L', DARK_LATEX_ITEM)
                .unlockedBy(getHasName(DARK_LATEX_ITEM), has(DARK_LATEX_ITEM))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DNA_EXTRACTOR_ITEM)
                .pattern("GIG")
                .pattern("ICI")
                .pattern("IPI")
                .define('G', Tags.Items.GLASS_PANES)
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('C', COPPER_COIL)
                .define('P', POWER_CELL)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EMPTY_CANISTER, 4)
                .pattern(" I ")
                .pattern("IGI")
                .pattern(" I ")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('G', Tags.Items.GLASS_PANES)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, FLASK_ITEM, 3)
                .pattern(" G ")
                .pattern("GGG")
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy(getHasName(Items.GLASS), has(Tags.Items.GLASS_BLOCKS))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GAS_TANK_ITEM)
                .pattern(" I ")
                .pattern("ILI")
                .pattern("ILI")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('L', DARK_LATEX_ITEM)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GENERATOR_ITEM)
                .pattern("III")
                .pattern("IPI")
                .pattern("IFI")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('P', POWER_CELL)
                .define('F', Items.FURNACE)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GOLDEN_PLATE, 2)
                .pattern("GGG")
                .define('G', Tags.Items.INGOTS_GOLD)
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Tags.Items.INGOTS_GOLD))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, HAZARD_BLOCK_ITEM,9)
                .pattern("OOB")
                .pattern("OBO")
                .pattern("BOO")
                .define('O', ORANGE_LAB_BLOCK_ITEM)
                .define('B', Items.BLACK_CONCRETE)
                .unlockedBy(getHasName(Items.ORANGE_CONCRETE), has(Items.ORANGE_CONCRETE))
                .save(out);

        stonecuttingAllToAll(new DeferredItem[]{HAZARD_BLOCK_ITEM, HAZARD_SLAB_ITEM, HAZARD_STAIRS_ITEM, HAZARD_LAB_BLOCK_ITEM},
                RecipeCategory.BUILDING_BLOCKS, out);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, HAZMAT_HELMET)
                .pattern(" O ")
                .pattern("FHF")
                .pattern(" G ")
                .define('O', Tags.Items.DYES_ORANGE)
                .define('F', LATEX_RESISTANT_FABRIC)
                .define('H', Items.IRON_HELMET)
                .define('G', LATEX_RESISTANT_GLASS_ITEM)
                .unlockedBy(getHasName(LATEX_RESISTANT_FABRIC), has(LATEX_RESISTANT_FABRIC))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, HAZMAT_CHESTPLATE)
                .pattern(" O ")
                .pattern("FCF")
                .pattern(" F ")
                .define('O', Tags.Items.DYES_ORANGE)
                .define('F', LATEX_RESISTANT_FABRIC)
                .define('C', Items.IRON_CHESTPLATE)
                .unlockedBy(getHasName(LATEX_RESISTANT_FABRIC), has(LATEX_RESISTANT_FABRIC))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, HAZMAT_LEGGINGS)
                .pattern(" O ")
                .pattern("FLF")
                .pattern(" F ")
                .define('O', Tags.Items.DYES_ORANGE)
                .define('F', LATEX_RESISTANT_FABRIC)
                .define('L', Items.IRON_LEGGINGS)
                .unlockedBy(getHasName(LATEX_RESISTANT_FABRIC), has(LATEX_RESISTANT_FABRIC))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, HAZMAT_BOOTS)
                .pattern(" O ")
                .pattern("FBF")
                .pattern(" C ")
                .define('O', Tags.Items.DYES_ORANGE)
                .define('F', LATEX_RESISTANT_FABRIC)
                .define('B', Items.IRON_BOOTS)
                .define('C', LATEX_RESISTANT_COATING)
                .unlockedBy(getHasName(LATEX_RESISTANT_FABRIC), has(LATEX_RESISTANT_FABRIC))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IRON_PLATE, 2)
                .pattern("III")
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(Items.IRON_INGOT), hasIron)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IV_RACK_ITEM, 3)
                .pattern("BBB")
                .pattern(" B ")
                .pattern("III")
                .define('B', Items.IRON_BARS)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(Items.IRON_INGOT), hasIron)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, KEYPAD_ITEM)
                .pattern("WCW")
                .pattern("BBB")
                .pattern("III")
                .define('W', ItemTagProvider.WIRES_COPPER)
                .define('C', ItemTagProvider.PLATES_COPPER)
                .define('B', ItemTags.STONE_BUTTONS)
                .define('I', ItemTagProvider.PLATES_IRON)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        labBlock(LAB_BLOCK_ITEM, Items.QUARTZ_BLOCK, out);

        stonecuttingAllToAll(new DeferredItem[]{BOLTED_LAB_TILE_ITEM, CONNECTED_LAB_TILE_ITEM, EXPOSED_PIPES_ITEM, LAB_BLOCK_ITEM,
                LAB_SLAB_ITEM, LAB_STAIRS_ITEM, LAB_TILE_ITEM, LAB_TILE_SLAB_ITEM, LAB_TILE_STAIRS_ITEM},
                RecipeCategory.BUILDING_BLOCKS, out);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, LAB_DOOR_ITEM)
                .pattern("III")
                .pattern("PIP")
                .pattern("III")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('P', Blocks.PISTON)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, LAB_DOOR_ITEM, 2)
                .requires(BIG_LAB_DOOR_ITEM)
                .unlockedBy(getHasName(BIG_LAB_DOOR_ITEM), has(BIG_LAB_DOOR_ITEM))
                .save(out, LAB_DOOR_ITEM.getId().withSuffix("_from_big"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, LAB_LAMP_ITEM, 4)
                .pattern("IWI")
                .pattern("GWG")
                .pattern("GGG")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('W', ItemTagProvider.WIRES_COPPER)
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, LAB_LAMP_ITEM, 2)
                .requires(BIG_LAB_LAMP_ITEM)
                .unlockedBy(getHasName(BIG_LAB_LAMP_ITEM), has(BIG_LAB_LAMP_ITEM))
                .save(out, LAB_LAMP_ITEM.getId().withSuffix("_from_big"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, LASER_EMITTER_ITEM)
                .pattern("III")
                .pattern("LAG")
                .pattern("III")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('L', Items.REDSTONE_LAMP)
                .define('A', Tags.Items.GEMS_AMETHYST)
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LATEX_CONTAINER_ITEM)
                .pattern("ICI")
                .pattern(" G ")
                .pattern("ICI")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('C', LATEX_RESISTANT_COATING)
                .define('G', LATEX_RESISTANT_GLASS_ITEM)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LATEX_ENCODER_ITEM)
                .pattern("GCG")
                .pattern("DPD")
                .pattern("III")
                .define('G', ItemTagProvider.PLATES_GOLD)
                .define('C', LATEX_ENCODER_COMPONENTS)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('P', POWER_CELL)
                .define('I', ItemTagProvider.PLATES_IRON)
                .unlockedBy(getHasName(LATEX_ENCODER_COMPONENTS), has(LATEX_ENCODER_COMPONENTS))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LATEX_PURIFIER_ITEM)
                .pattern("GCG")
                .pattern("APA")
                .pattern("III")
                .define('G', ItemTagProvider.PLATES_GOLD)
                .define('C', LATEX_PURIFIER_COMPONENTS)
                .define('A', Tags.Items.GEMS_AMETHYST)
                .define('P', POWER_CELL)
                .define('I', ItemTagProvider.PLATES_IRON)
                .unlockedBy(getHasName(LATEX_PURIFIER_COMPONENTS), has(LATEX_PURIFIER_COMPONENTS))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LATEX_RESISTANT_BLOCK_ITEM, 8)
                .pattern("CC")
                .pattern("CC")
                .define('C', LATEX_RESISTANT_COATING)
                .unlockedBy(getHasName(LATEX_RESISTANT_COATING), hasLRCoating)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LATEX_RESISTANT_COATING)
                .pattern(" C ")
                .pattern("CEC")
                .pattern(" C ")
                .define('C', LATEX_RESISTANT_COMPOUND)
                .define('E', ItemTagProvider.PLATES_IRON)
                .unlockedBy(getHasName(LATEX_RESISTANT_COMPOUND), has(LATEX_RESISTANT_COMPOUND))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LATEX_RESISTANT_FABRIC)
                .pattern(" C ")
                .pattern("CEC")
                .pattern(" C ")
                .define('C', LATEX_RESISTANT_COMPOUND)
                .define('E', ItemTags.WOOL)
                .unlockedBy(getHasName(LATEX_RESISTANT_COMPOUND), has(LATEX_RESISTANT_COMPOUND))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LATEX_RESISTANT_GLASS_ITEM, 8)
                .pattern(" C ")
                .pattern("CGC")
                .pattern(" C ")
                .define('C', LATEX_RESISTANT_COATING)
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy(getHasName(LATEX_RESISTANT_COATING), hasLRCoating)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LATEX_RESISTANT_GLASS_PANE_ITEM, 16)
                .pattern("GGG")
                .pattern("GGG")
                .define('G', LATEX_RESISTANT_GLASS_ITEM)
                .unlockedBy(getHasName(LATEX_RESISTANT_GLASS_ITEM), has(LATEX_RESISTANT_GLASS_ITEM))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, LIBRARY_DOOR_ITEM)
                .pattern("IGI")
                .pattern("PGP")
                .pattern("IGI")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('P', Blocks.PISTON)
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, LIBRARY_DOOR_ITEM, 2)
                .requires(BIG_LIBRARY_DOOR_ITEM)
                .unlockedBy(getHasName(BIG_LIBRARY_DOOR_ITEM), has(BIG_LIBRARY_DOOR_ITEM))
                .save(out, LIBRARY_DOOR_ITEM.getId().withSuffix("_from_big"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, MAINTENANCE_DOOR_ITEM)
                .pattern("IPI")
                .pattern("III")
                .pattern("IPI")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('P', Blocks.PISTON)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, MAINTENANCE_DOOR_ITEM, 2)
                .requires(BIG_MAINTENANCE_DOOR_ITEM)
                .unlockedBy(getHasName(BIG_MAINTENANCE_DOOR_ITEM), has(BIG_MAINTENANCE_DOOR_ITEM))
                .save(out, MAINTENANCE_DOOR_ITEM.getId().withSuffix("_from_big"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, METAL_BOX_ITEM)
                .pattern("III")
                .pattern("I I")
                .pattern("III")
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(Items.IRON_INGOT), hasIron)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, METAL_CAN_ITEM, 4)
                .pattern("I")
                .pattern("P")
                .pattern("I")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('P', PIPE_ITEM)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, METAL_CAN_ITEM)
                .requires(Ingredient.of(CANNED_ORANGES_ITEM))
                .unlockedBy(getHasName(CANNED_ORANGES_ITEM), has(CANNED_ORANGES_ITEM))
                .save(out, METAL_CAN_ITEM.getId().withSuffix("_from_canned_oranges"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NOTEPAD_ITEM)
                .pattern("I ")
                .pattern("IP")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Items.PAPER)
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ORANGE_JUICE_ITEM)
                .requires(ORANGE_ITEM,4)
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(ORANGE_ITEM), has(ORANGE_ITEM))
                .save(out);

        buttonBuilder(ORANGE_BUTTON_ITEM, Ingredient.of(ORANGE_PLANKS_ITEM))
                .unlockedBy(getHasName(ORANGE_PLANKS_ITEM), has(ORANGE_PLANKS_ITEM))
                .save(out);

        doorBuilder(ORANGE_DOOR_ITEM, Ingredient.of(ORANGE_PLANKS_ITEM))
                .unlockedBy(getHasName(ORANGE_PLANKS_ITEM), has(ORANGE_PLANKS_ITEM))
                .save(out);

        fenceBuilder(ORANGE_FENCE_ITEM, Ingredient.of(ORANGE_PLANKS_ITEM))
                .unlockedBy(getHasName(ORANGE_PLANKS_ITEM), has(ORANGE_PLANKS_ITEM))
                .save(out);

        fenceGateBuilder(ORANGE_FENCE_GATE_ITEM, Ingredient.of(ORANGE_PLANKS_ITEM))
                .unlockedBy(getHasName(ORANGE_PLANKS_ITEM), has(ORANGE_PLANKS_ITEM))
                .save(out);

        hangingSign(out, ORANGE_HANGING_SIGN_ITEM, ORANGE_PLANKS_ITEM);

        labBlock(ORANGE_LAB_BLOCK_ITEM, Items.ORANGE_CONCRETE, out);

        stonecuttingAllToAll(new DeferredItem<?>[]{ORANGE_LAB_BLOCK_ITEM, ORANGE_LAB_SLAB_ITEM, ORANGE_LAB_STAIRS_ITEM,
                STRIPED_ORANGE_LAB_BLOCK_ITEM}, RecipeCategory.BUILDING_BLOCKS, out);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ORANGE_PLANKS_ITEM, 4)
                .requires(ORANGE_TREE_LOG_ITEM)
                .unlockedBy(getHasName(ORANGE_TREE_LOG_ITEM), has(ORANGE_TREE_LOG_ITEM))
                .save(out);

        pressurePlateBuilder(RecipeCategory.REDSTONE, ORANGE_PRESSURE_PLATE_ITEM, Ingredient.of(ORANGE_PLANKS_ITEM))
                .unlockedBy(getHasName(ORANGE_PLANKS_ITEM), has(ORANGE_PLANKS_ITEM))
                .save(out);

        signBuilder(ORANGE_SIGN_ITEM, Ingredient.of(ORANGE_PLANKS_ITEM))
                .unlockedBy(getHasName(ORANGE_PLANKS_ITEM), has(ORANGE_PLANKS_ITEM))
                .save(out);

        slabBuilder(RecipeCategory.BUILDING_BLOCKS, ORANGE_SLAB_ITEM, Ingredient.of(ORANGE_PLANKS_ITEM))
                .unlockedBy(getHasName(ORANGE_PLANKS_ITEM), has(ORANGE_PLANKS_ITEM))
                .save(out);

        stairBuilder(ORANGE_STAIRS_ITEM, Ingredient.of(ORANGE_PLANKS_ITEM))
                .unlockedBy(getHasName(ORANGE_PLANKS_ITEM), has(ORANGE_PLANKS_ITEM))
                .save(out);

        trapdoorBuilder(ORANGE_TRAPDOOR_ITEM, Ingredient.of(ORANGE_PLANKS_ITEM))
                .unlockedBy(getHasName(ORANGE_PLANKS_ITEM), has(ORANGE_PLANKS_ITEM))
                .save(out);

        woodFromLogs(out, ORANGE_WOOD_ITEM, ORANGE_TREE_LOG_ITEM);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PIPE_ITEM, 8)
                .pattern("III")
                .define('I', ItemTagProvider.PLATES_IRON)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, PNEUMATIC_SYRINGE_RIFLE)
                .pattern(" I ")
                .pattern("EPP")
                .pattern("I  ")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('E', EMPTY_CANISTER)
                .define('P', PIPE_ITEM)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POWER_CELL)
                .pattern(" W ")
                .pattern("IRI")
                .pattern("IRI")
                .define('W', COPPER_WIRE_ITEM)
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ROTATING_CHAIR_ITEM,3)
                .pattern("I  ")
                .pattern("III")
                .pattern(" B ")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('B', Items.IRON_BARS)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SCANNER_ITEM)
                .pattern(" I ")
                .pattern("IOI")
                .pattern("IPI")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('O', Items.OBSERVER)
                .define('P', POWER_CELL)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SMALL_CARDBOARD_BOX_ITEM)
                .pattern("CCC")
                .define('C', CARDBOARD)
                .unlockedBy(getHasName(CARDBOARD), has(CARDBOARD))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, STATE_KEY)
                .pattern("III")
                .pattern("  I")
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(Items.IRON_INGOT), hasIron)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, STUN_BATON)
                .pattern("CI ")
                .pattern("CI ")
                .pattern("IPI")
                .define('C', ItemTagProvider.WIRES_COPPER)
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('P', POWER_CELL)
                .unlockedBy(getHasName(POWER_CELL), has(POWER_CELL))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SYRINGE_COIL_GUN)
                .pattern("WW ")
                .pattern("CCC")
                .pattern("PII")
                .define('W', ItemTagProvider.WIRES_COPPER)
                .define('C', COPPER_COIL)
                .define('P', POWER_CELL)
                .define('I', ItemTagProvider.PLATES_IRON)
                .unlockedBy(getHasName(POWER_CELL), has(POWER_CELL))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SYRINGE_ITEM,8)
                .pattern(" II")
                .pattern(" GI")
                .pattern("I  ")
                .define('I', Tags.Items.NUGGETS_IRON)
                .define('G', Tags.Items.GLASS_PANES)
                .unlockedBy(getHasName(Items.IRON_INGOT), hasIron)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TABLE_ITEM,3)
                .pattern("III")
                .pattern("B B")
                .pattern("B B")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', Items.IRON_BARS)
                .unlockedBy(getHasName(Items.IRON_INGOT), hasIron)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TALL_CARDBOARD_BOX_ITEM,1)
                .pattern("CCC")
                .pattern("C C")
                .pattern("CCC")
                .define('C',CARDBOARD)
                .unlockedBy(getHasName(CARDBOARD), has(CARDBOARD))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TEST_TUBES_ITEM, 3)
                .pattern("GGG")
                .pattern("I I")
                .define('G', Tags.Items.GLASS_BLOCKS)
                .define('I', ItemTagProvider.PLATES_IRON)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TRAFFIC_CONE_ITEM,3)
                .pattern(" O ")
                .pattern("BOB")
                .define('O', Items.ORANGE_CONCRETE)
                .define('B', Items.BLACK_CONCRETE)
                .unlockedBy(getHasName(Items.ORANGE_CONCRETE), has(Items.ORANGE_CONCRETE))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TV_SCREEN_ITEM)
                .pattern("WB")
                .pattern("WC")
                .define('W', ItemTagProvider.WIRES_COPPER)
                .define('B', BIG_LAB_LAMP_ITEM)
                .define('C', ItemTagProvider.PLATES_COPPER)
                .unlockedBy(getHasName(COPPER_PLATE), hasCopperPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VENT_DUCT_ITEM, 6)
                .pattern("III")
                .pattern("B B")
                .pattern("III")
                .define('I', ItemTagProvider.PLATES_IRON)
                .define('B', Items.IRON_BARS)
                .unlockedBy(getHasName(IRON_PLATE), hasIronPlates)
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, VENT_HATCH_ITEM,2)
                .pattern("IBI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', Items.IRON_BARS)
                .unlockedBy(getHasName(Items.IRON_INGOT), hasIron)
                .save(out);

        labBlock(VENT_WALL_ITEM, Items.GRAY_CONCRETE, out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.WHITE_LATEX_PUDDLE_F,3)
                .pattern("L L")
                .pattern(" L ")
                .define('L', WHITE_LATEX_ITEM)
                .unlockedBy(getHasName(WHITE_LATEX_ITEM), has(WHITE_LATEX_ITEM))
                .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.WHITE_LATEX_PUDDLE_M,3)
                .pattern(" L ")
                .pattern("L L")
                .define('L', WHITE_LATEX_ITEM)
                .unlockedBy(getHasName(WHITE_LATEX_ITEM), has(WHITE_LATEX_ITEM))
                .save(out);

        labBlock(YELLOW_LAB_BLOCK_ITEM, Items.YELLOW_CONCRETE, out);

        stonecuttingAllToAll(new DeferredItem[]{YELLOW_LAB_BLOCK_ITEM, YELLOW_LAB_SLAB_ITEM, YELLOW_LAB_STAIRS_ITEM},
                RecipeCategory.BUILDING_BLOCKS, out);
    }

    private void labBlock(DeferredItem<?> result, Item material, RecipeOutput out){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 24)
                .pattern("CIC")
                .pattern("ICI")
                .pattern("CIC")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('C', material)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Tags.Items.INGOTS_IRON))
                .save(out);
    }

    private void latexBlockItem(boolean white, RecipeOutput out){
        DeferredItem<?> block = white ? WHITE_LATEX_BLOCK_ITEM : DARK_LATEX_BLOCK_ITEM;
        DeferredItem<?> latex = white ? WHITE_LATEX_ITEM : DARK_LATEX_ITEM;
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block,1)
                .pattern("LL")
                .pattern("LL")
                .define('L', latex)
                .unlockedBy(getHasName(latex), has(latex))
                .save(out);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, latex,4)
                .requires(block)
                .unlockedBy(getHasName(block), has(block))
                .save(out);
    }

    private void stonecuttingAllToAll(DeferredItem<?> @NotNull [] items, RecipeCategory category, RecipeOutput out){
        for(DeferredItem<?> material : items){
            for(DeferredItem<?> result : items){
                if(material == result) continue;
                SingleItemRecipeBuilder.stonecutting((Ingredient.of(material)), category, result)
                        .unlockedBy(getHasName(material), has(material))
                        .save(out, result.getId().withSuffix("_stonecutting_from_" + material.getId().getPath()));
            }
        }
    }
}