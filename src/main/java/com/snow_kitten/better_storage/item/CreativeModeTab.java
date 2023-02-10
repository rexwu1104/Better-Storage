package com.snow_kitten.better_storage.item;

import com.snow_kitten.better_storage.BetterStorage;
import com.snow_kitten.better_storage.block.Blocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterStorage.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeModeTab {
    public static net.minecraft.world.item.CreativeModeTab BETTER_STORAGE_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        BETTER_STORAGE_TAB = event.registerCreativeModeTab(new ResourceLocation(BetterStorage.MODID, "better_storage_tab"),
                builder -> builder.icon(() -> new ItemStack(Blocks.DATA_HARDWARE.get().asItem()))
                        .title(Component.literal("Better Storage"))
                        .build());
    }
}
