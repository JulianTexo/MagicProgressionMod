package net.juliantexo.magicprogression.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModItemGroup {

    public static final CreativeModeTab TAB = new CreativeModeTab("magic_progression") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ModItems.MANA_CRYSTAL.get().getDefaultInstance();
        }


    };
}
