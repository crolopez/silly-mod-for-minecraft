package com.cristofiumod.init;

import com.cristofiumod.CristofiuMod;
import com.cristofiumod.blocks.CachimbaBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CristofiuMod.MODID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Blocks ID
    public static final String CACHIMBA_ID = "cachimba";

    // Blocks
    public static final RegistryObject<Block> CACHIMBA_BLOCK = BLOCKS.register(CACHIMBA_ID, CachimbaBlock::new);
}
