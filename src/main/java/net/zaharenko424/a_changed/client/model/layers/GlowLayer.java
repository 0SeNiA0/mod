package net.zaharenko424.a_changed.client.model.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.zaharenko424.a_changed.client.model.AbstractLatexEntityModel;
import net.zaharenko424.a_changed.client.model.geom.ModelPart;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class GlowLayer<E extends LivingEntity, M extends AbstractLatexEntityModel<E>> extends RenderLayer<E, M> {

    public GlowLayer(RenderLayerParent<E, M> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, E pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        M model = getParentModel();
        if(!model.hasGlowParts()) return;
        model.setAllDraw(false);
        setDrawGlow(model, model.root());
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.eyes(renderer.getTextureLocation(pLivingEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1,1, 1, 1);
        model.setAllDraw(true);
    }

    protected void setDrawGlow(M model, ModelPart part){
        part.getChildren().forEach((name, modelpart) -> {
            if(name.startsWith("glow_")) model.setAllDraw(modelpart, true);
            else if(modelpart.hasChildren()) setDrawGlow(model, modelpart);
        });
    }
}