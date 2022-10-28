package net.juliantexo.magicprogression.block.entity;

import net.juliantexo.magicprogression.recipe.ManaFurnaceRecipe;
import net.juliantexo.magicprogression.recipe.ManaInfusingRecipe;
import net.juliantexo.magicprogression.screen.ManaFurnaceMenu;
import net.juliantexo.magicprogression.screen.ManaInfusingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.juliantexo.magicprogression.block.custom.ManaFurnaceBlock.LIT;

//TODO: Rename class
public class CustomMachineBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(4){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;

    //TODO: change maxProgress if needed (make it pass it from the outside?)
    private int progress = 0;
    private int maxProgress = 200;

    public CustomMachineBlockEntity(BlockPos blockPos, BlockState blockState) {
        //TODO: register this entity in ModBLockEntities and call it here
        super(ModBlockEntities.CUSTOMBLOCKENTITY.get(), blockPos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch(index){
                    case 0 -> ManaInfusingStationBlockEntity.this.progress;
                    case 1 -> ManaInfusingStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> ManaInfusingStationBlockEntity.this.progress = value;
                    case 1 -> ManaInfusingStationBlockEntity.this.maxProgress = value;
                }

            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    //TODO: change display name
    @Override
    public Component getDisplayName() {
        return Component.literal("Custom Machine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        //TODO: call custom Menu
        return new CustomBlockMenuTemplate(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        //TODO: save progress
        nbt.putInt("custom_machine.progress", this.progress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        //TODO: load progress
        progress = nbt.getInt("custom_machine.progress");
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    //TODO: change entity parameter
    public static void tick(Level level, BlockPos blockPos, BlockState blockState, CustomBlockEntityTemplate pEntity) {
        if(level.isClientSide()){
            return;
        }

        if(hasRecipe(pEntity)){
            pEntity.progress++;
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress){
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();
            setChanged(level, blockPos,blockState);
        }

    }

    private void resetProgress() {
        this.progress = 0;
    }

    //TODO: change entity parameter
    private static void craftItem(CustomBlockEntityTemplate pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for(int i = 0; i < pEntity.itemHandler.getSlots(); i++){
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        //TODO: change Recipe
        Optional<CustomRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(CustomRecipe.Type.INSTANCE, inventory, level);

        //TODO: change number of slots according to recipes
        if(hasRecipe(pEntity)){
            pEntity.itemHandler.extractItem(0,1,false);
            pEntity.itemHandler.extractItem(1,1,false);
            pEntity.itemHandler.extractItem(2,1,false);
            pEntity.itemHandler.setStackInSlot(3, new ItemStack(recipe.get().getResultItem().getItem(),
                    pEntity.itemHandler.getStackInSlot(3).getCount() + 1));

            pEntity.resetProgress();
        }

    }

    //TODO: entity parametre
    private static boolean hasRecipe(CustomBlockEntityTemplate entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for(int i = 0; i < entity.itemHandler.getSlots(); i++){
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        //TODO: change recipe
        Optional<CustomRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(CustomRecipe.Type.INSTANCE, inventory, level);


        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem());

    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        //TODO: change slot number to match output slot
        return inventory.getItem(3).getItem() == itemStack.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        //TODO: change slot number to match output slot
        return inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }
}
