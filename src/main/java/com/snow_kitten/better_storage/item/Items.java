package com.snow_kitten.better_storage.item;

import com.snow_kitten.better_storage.BetterStorage;
import com.snow_kitten.better_storage.structs.DataCard_128;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Items {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BetterStorage.MODID);

    public static final RegistryObject<Item> DATA_CARD_128_ITEM = ITEMS.register("data_card_128", DataCard_128::new);

    public static void register(IEventBus bus) {
        bus.register(ITEMS);
    }
}
