package net.zaharenko424.a_changed.registry;

import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zaharenko424.a_changed.transfurSystem.transfurTypes.AbstractTransfurType;
import net.zaharenko424.a_changed.transfurSystem.transfurTypes.BeiFeng;
import net.zaharenko424.a_changed.transfurSystem.transfurTypes.LatexWolf;

import static net.zaharenko424.a_changed.AChanged.MODID;
import static net.zaharenko424.a_changed.AChanged.resourceLoc;

public final class TransfurRegistry {

    public static final DeferredRegister<AbstractTransfurType> TRANSFUR_TYPES = DeferredRegister.create(resourceLoc("transfur_registry"),MODID);
    public static final Registry<AbstractTransfurType> TRANSFUR_REGISTRY = TRANSFUR_TYPES.makeRegistry(builder->{});

    //Transfur types
    public static final DeferredHolder<AbstractTransfurType, BeiFeng> BEI_FENG_TF = TRANSFUR_TYPES
            .register("bei_feng", ()-> new BeiFeng(AbstractTransfurType.Properties
                    .of(resourceLoc("bei_feng"))));

    public static final DeferredHolder<AbstractTransfurType, LatexWolf> DARK_LATEX_WOLF_M_TF = TRANSFUR_TYPES
            .register("dark_latex_wolf_male", ()-> new LatexWolf(AbstractTransfurType.Properties
                    .of(resourceLoc("dark_latex_wolf_male"))));
    public static final DeferredHolder<AbstractTransfurType, LatexWolf> DARK_LATEX_WOLF_F_TF = TRANSFUR_TYPES
            .register("dark_latex_wolf_female", ()-> new LatexWolf(AbstractTransfurType.Properties
                    .of(resourceLoc("dark_latex_wolf_female")).gender(false)));

    public static final DeferredHolder<AbstractTransfurType, LatexWolf> GAS_WOLF_TF = TRANSFUR_TYPES
            .register("gas_wolf", ()-> new LatexWolf(AbstractTransfurType.Properties.of(resourceLoc("gas_wolf"))));

    public static final DeferredHolder<AbstractTransfurType, LatexWolf> WHITE_LATEX_WOLF_M_TF = TRANSFUR_TYPES
            .register("white_latex_wolf_male", ()-> new LatexWolf(AbstractTransfurType.Properties
                    .of(resourceLoc("white_latex_wolf_male"))));
    public static final DeferredHolder<AbstractTransfurType, LatexWolf> WHITE_LATEX_WOLF_F_TF = TRANSFUR_TYPES
            .register("white_latex_wolf_female", ()-> new LatexWolf(AbstractTransfurType.Properties
                    .of(resourceLoc("white_latex_wolf_female")).gender(false)));
}