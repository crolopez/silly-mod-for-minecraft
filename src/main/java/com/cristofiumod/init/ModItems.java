package com.cristofiumod.init;

import com.cristofiumod.CristofiuMod;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CristofiuMod.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Items

    // Block items
    public static final RegistryObject<Item> CACHIMBA_BLOCK = registerCachimbaBlock();

    // Register methods
    private static RegistryObject<Item> registerCachimbaBlock() {
        return ITEMS.register(ModBlocks.CACHIMBA_ID,
                () -> new BlockItem(ModBlocks.CACHIMBA_BLOCK.get(), new Item.Properties().group(CristofiuMod.TAB)));
    }
}
