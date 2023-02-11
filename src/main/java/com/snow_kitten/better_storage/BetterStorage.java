package com.snow_kitten.better_storage;

import com.mojang.logging.LogUtils;
import com.snow_kitten.better_storage.block.Blocks;
import com.snow_kitten.better_storage.block.entity.BlockEntities;
import com.snow_kitten.better_storage.item.CreativeModeTab;
import com.snow_kitten.better_storage.item.Items;
import com.snow_kitten.better_storage.screen.DataHardwareScreen;
import com.snow_kitten.better_storage.screen.MenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BetterStorage.MODID)
@Mod.EventBusSubscriber(modid = BetterStorage.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BetterStorage
{
    public static final String MODID = "better_storage";
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public BetterStorage()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Items.register(bus);
        Blocks.register(bus);

        BlockEntities.register(bus);
        MenuTypes.register(bus);


        bus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTab.BETTER_STORAGE_TAB) {
            event.accept(Blocks.DATA_HARDWARE);
            event.accept(Blocks.DATA_CABLE);
            event.accept(Items.DATA_CARD_128_ITEM);
        }
    }

//    @SubscribeEvent
//    public static void forgeEventHandler(BlockEvent.EntityPlaceEvent event) {
//        var block = event.getEntity();
//        if (block != null) {
//            System.out.println(event.getPlacedBlock().getBlock().getName());
//            System.out.println(event.getPos().getCenter());
//        }
//    }

//    @SubscribeEvent
//    public static void itemPickUpEvent(PlayerEvent.ItemPickupEvent event) {
//        CompoundTag tag = event.getOriginalEntity().serializeNBT();
//        for (var t: tag.getAllKeys()) {
//            System.out.printf("%s: %s%n", t, tag.get(t));
//        }
//    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientEvents {
        @SubscribeEvent
        public static void clientSetupEvent(FMLClientSetupEvent event) {
            MenuScreens.register(MenuTypes.DATA_HARDWARE_MENU.get(), DataHardwareScreen::new);
        }
    }
}
