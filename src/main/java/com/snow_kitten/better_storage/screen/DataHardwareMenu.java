package com.snow_kitten.better_storage.screen;

import com.snow_kitten.better_storage.BetterStorage;
import com.snow_kitten.better_storage.block.Blocks;
import com.snow_kitten.better_storage.block.custom.DataHardwareBlock;
import com.snow_kitten.better_storage.block.entity.DataHardwareBlockEntity;
import com.snow_kitten.better_storage.structs.DataCard;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class DataHardwareMenu extends AbstractContainerMenu {
    public final DataHardwareBlockEntity entity;
    private final Level level;
    private final ContainerData data;

    public DataHardwareMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
        this(id, inventory, inventory.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(8));
    }

    public DataHardwareMenu(int id, Inventory inventory, BlockEntity block, ContainerData data) {
        super(MenuTypes.DATA_HARDWARE_MENU.get(), id);
        checkContainerSize(inventory, 8);
        this.entity = (DataHardwareBlockEntity) block;
        this.level = inventory.player.level;
        this.data = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 53, 23));
            this.addSlot(new SlotItemHandler(handler, 1, 71, 23));
            this.addSlot(new SlotItemHandler(handler, 2, 89, 23));
            this.addSlot(new SlotItemHandler(handler, 3, 107, 23));
            this.addSlot(new SlotItemHandler(handler, 4, 53, 41));
            this.addSlot(new SlotItemHandler(handler, 5, 71, 41));
            this.addSlot(new SlotItemHandler(handler, 6, 89, 41));
            this.addSlot(new SlotItemHandler(handler, 7, 107, 41));
        });

        addDataSlots(data);
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 8;

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public void clicked(int clickIndex, int p_150401_, ClickType type, Player player) {
//        if (!(this..getItem() instanceof DataCard)) {
//            return;
//        }

        super.clicked(clickIndex, p_150401_, type, player);
        for (var slot: slots) {
            if (slot.hasItem()) {
                switch (slot.index) {
                    case TE_INVENTORY_FIRST_SLOT_INDEX -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_1, true));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 1 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_2, true));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 2 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_3, true));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 3 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_4, true));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 4 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_5, true));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 5 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_6, true));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 6 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_7, true));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 7 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_8, true));
                }
            } else {
                switch (slot.index) {
                    case TE_INVENTORY_FIRST_SLOT_INDEX -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_1, false));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 1 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_2, false));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 2 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_3, false));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 3 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_4, false));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 4 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_5, false));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 5 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_6, false));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 6 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_7, false));
                    case TE_INVENTORY_FIRST_SLOT_INDEX + 7 -> entity.setBlockState(entity.getBlockState().setValue(DataHardwareBlock.DRIVER_8, false));
                }
            }
        }

        level.setBlock(entity.getBlockPos(), entity.getBlockState(), 3);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, entity.getBlockPos()),
                player, Blocks.DATA_HARDWARE.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i=0; i<3; ++i) {
            for (int j=0; j<9; ++j) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i=0; i<9; ++i) {
            addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
