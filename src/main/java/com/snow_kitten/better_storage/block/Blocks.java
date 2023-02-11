package com.snow_kitten.better_storage.block;

import com.snow_kitten.better_storage.BetterStorage;
import com.snow_kitten.better_storage.block.custom.DataCableBlock;
import com.snow_kitten.better_storage.block.custom.DataHardwareBlock;
import com.snow_kitten.better_storage.item.Items;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Blocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BetterStorage.MODID);

    public static final RegistryObject<Block> DATA_HARDWARE = registerBlock("data_hardware",
            () -> new DataHardwareBlock(BlockBehaviour.Properties.of(Material.STONE)));

    public static final RegistryObject<Block> DATA_CABLE = registerBlock("data_cable_center",
            () -> new DataCableBlock(BlockBehaviour.Properties.of(Material.METAL)));

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
