package cn.org.snowskystudio.basictechnology.screen;

import cn.org.snowskystudio.basictechnology.BasicTechnology;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PressMachineScreen extends AbstractContainerScreen<PressMachineMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BasicTechnology.MODID, "textures/gui/press_machine_gui.png");

    public PressMachineScreen(PressMachineMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected void init() {
        super.init();
        this.imageHeight = 155;
        this.inventoryLabelY = this.imageHeight - 94;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderHasEnergy(guiGraphics, x, y);
    }

    private void renderHasEnergy(GuiGraphics guiGraphics, int x, int y) {
        if(menu.hasEnergy()) {
            guiGraphics.blit(TEXTURE, x + 157, y + 6, 176, 20, 11, 7);
        } else {
            guiGraphics.blit(TEXTURE, x + 157, y + 6, 176, 27, 15, 7);
        }
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 75, y + 28, 176, 0, menu.getScaledProgress(), 20);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
