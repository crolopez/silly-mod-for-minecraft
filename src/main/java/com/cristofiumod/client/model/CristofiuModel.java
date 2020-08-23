package com.cristofiumod.client.model;

import com.cristofiumod.entities.CristofiuEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class CristofiuModel <T extends CristofiuEntity> extends EntityModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer neck;
    private final ModelRenderer neck2;
    private final ModelRenderer neck3;
    private final ModelRenderer head;
    private final ModelRenderer rightLeg;
    private final ModelRenderer leftLeg;
    private final ModelRenderer leftArm;
    private final ModelRenderer rightArm;

    public CristofiuModel() {

        textureWidth = 64;
        textureHeight = 64;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 6.0F, 0.0F);
        body.setTextureOffset(16, 19).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

        neck = new ModelRenderer(this);
        neck.setRotationPoint(0.0F, -6.0F, 1.0F);
        body.addChild(neck);
        neck.setTextureOffset(36, 57).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);

        neck2 = new ModelRenderer(this);
        neck2.setRotationPoint(0.0F, -3.0F, 2.0F);
        neck.addChild(neck2);
        neck2.setTextureOffset(34, 57).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

        neck3 = new ModelRenderer(this);
        neck3.setRotationPoint(0.0F, -2.0F, -1.0F);
        neck2.addChild(neck3);
        neck3.setTextureOffset(40, 59).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -2.0F, 0.0F);
        neck3.addChild(head);
        head.setTextureOffset(0, 0).addBox(-4.0F, -11.0F, -6.0F, 8.0F, 11.0F, 8.0F, 0.0F, false);

        rightLeg = new ModelRenderer(this);
        rightLeg.setRotationPoint(-2.0F, 8.0F, 0.0F);
        body.addChild(rightLeg);
        rightLeg.setTextureOffset(0, 19).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        leftLeg = new ModelRenderer(this);
        leftLeg.setRotationPoint(2.0F, 8.0F, 0.0F);
        body.addChild(leftLeg);
        leftLeg.setTextureOffset(16, 48).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(-6.0F, -4.0F, 0.0F);
        body.addChild(rightArm);
        setRotationAngle(rightArm, 0.0F, 0.0F, 0.4363F);
        rightArm.setTextureOffset(32, 48).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(6.0F, -4.0F, 0.0F);
        body.addChild(leftArm);
        setRotationAngle(leftArm, 0.0F, 0.0F, -0.4363F);
        leftArm.setTextureOffset(40, 19).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = entityIn.getTicksElytraFlying() > 4;
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);

        this.body.rotateAngleY = 0.0F;
        this.rightArm.rotationPointZ = 0.0F;
        this.rightArm.rotationPointX = -5.0F;
        this.leftArm.rotationPointZ = 0.0F;
        this.leftArm.rotationPointX = 5.0F;
        float f = 1.0F;
        if (flag) {
            f = (float)entityIn.getMotion().lengthSquared();
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        this.rightArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleZ = 0.0F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
        this.rightLeg.rotateAngleY = 0.0F;
        this.leftLeg.rotateAngleY = 0.0F;
        this.rightLeg.rotateAngleZ = 0.0F;
        this.leftLeg.rotateAngleZ = 0.0F;
        if (this.isSitting) {
            this.rightArm.rotateAngleX += (-(float)Math.PI / 5F);
            this.leftArm.rotateAngleX += (-(float)Math.PI / 5F);
            this.rightLeg.rotateAngleX = -1.4137167F;
            this.rightLeg.rotateAngleY = ((float)Math.PI / 10F);
            this.rightLeg.rotateAngleZ = 0.07853982F;
            this.leftLeg.rotateAngleX = -1.4137167F;
            this.leftLeg.rotateAngleY = (-(float)Math.PI / 10F);
            this.leftLeg.rotateAngleZ = -0.07853982F;
        }

        this.rightArm.rotateAngleY = 0.0F;
        this.leftArm.rotateAngleY = 0.0F;

        ModelHelper.func_239101_a_(this.rightArm, this.leftArm, ageInTicks);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
