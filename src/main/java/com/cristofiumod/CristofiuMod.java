package com.cristofiumod;

import com.cristofiumod.entities.CristofiuEntity;
import com.cristofiumod.init.ModBlocks;
import com.cristofiumod.init.ModEntityType;
import com.cristofiumod.init.ModItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CristofiuMod.MODID)
public class CristofiuMod
{
    public static final String MODID = "cristofiumod";
    public static final String MODNAME = "Cristofiu Mod";

    private static final Logger LOGGER = LogManager.getLogger();

    public CristofiuMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        ModItems.init();
        ModEntityType.init();
        ModBlocks.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(ModEntityType.CRISTOFIU.get(), CristofiuEntity .setCustomAttributes().create());
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(ModBlocks.CACHIMBA_BLOCK.get(), RenderType.getTranslucent());
    }

    // Custom tab for Cristofiu Mod
    public static final ItemGroup TAB = new ItemGroup("cristofiu_mod_tab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.CACHIMBA_BLOCK.get());
        }
    };
}
