package net.zaharenko424.a_changed.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.zaharenko424.a_changed.AChanged;
import net.zaharenko424.a_changed.block.blocks.CryoChamber;
import net.zaharenko424.a_changed.entity.block.CryoChamberEntity;

public class CryoChamberOverlay {

    private static final ResourceLocation TEXTURE = AChanged.textureLoc("overlay/cryo_chamber");

    public static final LayeredDraw.Layer OVERLAY = (guiGraphics, partialTick) -> {
        Player player = Minecraft.getInstance().player;
        Level level = player.level();
        CryoChamberEntity chamberEntity = CryoChamber.getEntity(player.blockPosition(), level);

        if(chamberEntity == null || chamberEntity.isOpen()
                || chamberEntity.getFluidAmount() * .0625 <= player.getEyeHeight(player.getPose()) + (player.getY() % 1)) return;

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();

        guiGraphics.blit(TEXTURE, 0, 0, 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight());
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    };
}