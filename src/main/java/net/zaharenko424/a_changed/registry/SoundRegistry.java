package net.zaharenko424.a_changed.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zaharenko424.a_changed.AChanged;
import org.jetbrains.annotations.NotNull;

public class SoundRegistry {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, AChanged.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> BUTTON_PRESSED = registerVariableRange("button_pressed");
    public static final DeferredHolder<SoundEvent, SoundEvent> COMPRESSOR = registerVariableRange("compressor");
    public static final DeferredHolder<SoundEvent, SoundEvent> DOOR_CLOSE = registerVariableRange("door_close");
    public static final DeferredHolder<SoundEvent, SoundEvent> DOOR_LOCKED = registerVariableRange("door_locked");
    public static final DeferredHolder<SoundEvent, SoundEvent> DOOR_OPEN = registerVariableRange("door_open");
    public static final DeferredHolder<SoundEvent, SoundEvent> GAS_LEAK = registerVariableRange("gas_leak");
    public static final DeferredHolder<SoundEvent, SoundEvent> KEYPAD_UNLOCKED = registerVariableRange("keypad_unlocked");
    public static final DeferredHolder<SoundEvent, SoundEvent> KEYPAD_WRONG_PASSWORD = registerVariableRange("keypad_wrong_password");
    public static final DeferredHolder<SoundEvent, SoundEvent> LASER = registerVariableRange("laser");
    public static final DeferredHolder<SoundEvent, SoundEvent> PNEUMATIC_RIFLE = registerVariableRange("pneumatic_rifle");
    public static final DeferredHolder<SoundEvent, SoundEvent> PUSH = registerVariableRange("push");
    public static final DeferredHolder<SoundEvent, SoundEvent> SAVE = registerVariableRange("save");
    public static final DeferredHolder<SoundEvent, SoundEvent> SMART_SEWAGE_CONSUME = registerVariableRange("smart_sewage_absorb");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPACE_DOOR_CLOSE = registerVariableRange("space_door_close");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPACE_DOOR_OPEN = registerVariableRange("space_door_open");
    public static final DeferredHolder<SoundEvent, SoundEvent> TRANSFUR = registerVariableRange("transfur");
    public static final DeferredHolder<SoundEvent, SoundEvent> TRANSFUR_1 = registerVariableRange("transfur_1");
    //SoundType ...

    private static @NotNull DeferredHolder<SoundEvent, SoundEvent> registerVariableRange(String id){
        return SOUNDS.register(id,()-> SoundEvent.createVariableRangeEvent(AChanged.resourceLoc(id)));
    }
}