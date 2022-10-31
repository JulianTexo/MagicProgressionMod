package net.juliantexo.magicprogression.block.entity;

import net.juliantexo.magicprogression.item.ModItems;
import net.juliantexo.magicprogression.networking.ModMessages;
import net.juliantexo.magicprogression.networking.packet.ManaEnergySyncS2CPacket;
import net.juliantexo.magicprogression.recipe.ManaFurnaceRecipe;
import net.juliantexo.magicprogression.screen.ManaFurnaceMenu;
import net.juliantexo.magicprogression.util.ModMachineMana;
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
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.juliantexo.magicprogression.block.custom.ManaFurnaceBlock.LIT;

public class ManaFurnaceBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final ModMachineMana ENERGY_STORAGE = new ModMachineMana(10000, 150) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new ManaEnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private static final int MANA_REQ = 10;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;

    public ManaFurnaceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.MANA_FURNACE.get(), blockPos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch(index){
                    case 0 -> ManaFurnaceBlockEntity.this.progress;
                    case 1 -> ManaFurnaceBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> ManaFurnaceBlockEntity.this.progress = value;
                    case 1 -> ManaFurnaceBlockEntity.this.maxProgress = value;
                }

            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Mana Furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ManaFurnaceMenu(id, inventory, this, this.data);
    }


    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEneryLevel(int mana) {
        this.ENERGY_STORAGE.setEnergy(mana);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ENERGY){
            return lazyEnergyHandler.cast();
        }

        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("mana_furnace_progress", this.progress);
        nbt.putInt("mana_furnace_energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.progress = nbt.getInt("mana_furnace_progress");
        ENERGY_STORAGE.setEnergy(nbt.getInt("mana_furnace_energy"));
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ManaFurnaceBlockEntity pEntity) {
        if(level.isClientSide()){
            return;
        }

        if(hasManaCrystalInFourthSlot(pEntity)){
            pEntity.ENERGY_STORAGE.receiveEnergy(60, false);
        }

        if(hasRecipe(pEntity) && hasEnoughEnergy(pEntity)){
            pEntity.getLevel().setBlock(blockPos, blockState.setValue(LIT, true), 3);
            pEntity.progress++;
            extractEnergy(pEntity);
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress){
                craftItem(pEntity);
            }
        } else {
            pEntity.getLevel().setBlock(blockPos, blockState.setValue(LIT, false), 3);
            pEntity.resetProgress();
            setChanged(level, blockPos,blockState);
        }

    }

    private static void extractEnergy(ManaFurnaceBlockEntity pEntity) {
        pEntity.ENERGY_STORAGE.extractEnergy(MANA_REQ, false);
    }

    private static boolean hasEnoughEnergy(ManaFurnaceBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= MANA_REQ * (pEntity.maxProgress - pEntity.progress);
    }

    private static boolean hasManaCrystalInFourthSlot(ManaFurnaceBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(2).getItem() == ModItems.MANA_CRYSTAL.get();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(ManaFurnaceBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for(int i = 0; i < pEntity.itemHandler.getSlots(); i++){
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<ManaFurnaceRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ManaFurnaceRecipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(pEntity)){
            pEntity.itemHandler.extractItem(0,1,false);
            pEntity.itemHandler.setStackInSlot(1, new ItemStack(recipe.get().getResultItem().getItem(),
                    pEntity.itemHandler.getStackInSlot(1).getCount() + 1));

            pEntity.resetProgress();
        }

    }

    private static boolean hasRecipe(ManaFurnaceBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for(int i = 0; i < entity.itemHandler.getSlots(); i++){
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<ManaFurnaceRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ManaFurnaceRecipe.Type.INSTANCE, inventory, level);


        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem());

    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(1).getItem() == itemStack.getItem() || inventory.getItem(1).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(1).getMaxStackSize() > inventory.getItem(1).getCount();
    }

}
