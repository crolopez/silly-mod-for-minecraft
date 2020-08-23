package com.cristofiumod.init;

import com.cristofiumod.CristofiuMod;
import com.cristofiumod.entities.CristofiuEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityType {

    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, CristofiuMod.MODID);

    public static void init() {
        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Entities
    public static final RegistryObject<EntityType<CristofiuEntity>> CRISTOFIU = registerCristofiu();

    // Register methods
    private static RegistryObject<EntityType<CristofiuEntity>> registerCristofiu() {
        return ENTITY_TYPES.register("cristofiu",
                () -> EntityType.Builder.create(CristofiuEntity::new, EntityClassification.CREATURE)
                        .size(0.6f, 2.5f)
                        .build(new ResourceLocation(CristofiuMod.MODID, "cristofiu").toString()));
    }
}