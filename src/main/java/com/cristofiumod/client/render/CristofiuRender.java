package com.cristofiumod.client.render;

import com.cristofiumod.CristofiuMod;
import com.cristofiumod.client.model.CristofiuModel;
import com.cristofiumod.entities.CristofiuEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class CristofiuRender extends MobRenderer<CristofiuEntity, CristofiuModel<CristofiuEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(CristofiuMod.MODID, "textures/entity/cristofiu.png");

    public CristofiuRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new com.cristofiumod.client.model.CristofiuModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(CristofiuEntity entity) {
        return TEXTURE;
    }
}