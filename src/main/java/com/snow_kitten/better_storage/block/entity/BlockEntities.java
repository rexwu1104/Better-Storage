package com.snow_kitten.better_storage.block.entity;

import com.snow_kitten.better_storage.BetterStorage;
import com.snow_kitten.better_storage.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BetterStorage.MODID);

    public static final RegistryObject<BlockEntityType<DataHardwareBlockEntity>> DATA_HARDWARE_ENTITY =
            BLOCK_ENTITIES.register("data_hardware_entity", () ->
                BlockEntityType.Builder.of(DataHardwareBlockEntity::new,
                    Blocks.DATA_HARDWARE.get()).build(null));

    public static final RegistryObject<BlockEntityType<DataCableBlockEntity>> DATA_CABLE_ENTITY =
            BLOCK_ENTITIES.register("data_cable_entity", () ->
                    BlockEntityType.Builder.of(DataCableBlockEntity::new,
                            Blocks.DATA_CABLE.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
