package net.zaharenko424.testmod.transfurTypes;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractGenderedTransfurType extends AbstractTransfurType{
    public AbstractGenderedTransfurType(@NotNull Properties properties) {
        super(properties);
    }

    public boolean isMale(){
        return properties.male;
    }
}