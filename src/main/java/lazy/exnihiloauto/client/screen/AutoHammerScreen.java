package lazy.exnihiloauto.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.inventory.container.AutoHammerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class AutoHammerScreen extends ContainerScreen<AutoHammerContainer> {

    public static final ResourceLocation BACK = new ResourceLocation(Ref.MOD_ID, "textures/gui/auto_hammer.png");

    public AutoHammerScreen(AutoHammerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.xSize = 204;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (this.hoveredSlot != null) {
            if (!this.hoveredSlot.getHasStack()) {
                if (this.hoveredSlot.slotNumber == 1) {
                    this.renderTooltip(matrixStack, new TranslationTextComponent("hammer.input"), mouseX, mouseY);
                } else if (this.hoveredSlot.slotNumber == 0) {
                    this.renderTooltip(matrixStack, new TranslationTextComponent("hammer.hammer"), mouseX, mouseY);
                }
            } else {
                this.renderTooltip(matrixStack, this.hoveredSlot.getStack(), mouseX, mouseY);
            }
        }
        if (mouseX > this.guiLeft + 7 && mouseX < this.guiLeft + 7 + 18 && mouseY > this.guiTop + 15 && mouseY < this.guiTop + 15 + 54) {
            this.renderTooltip(matrixStack, new TranslationTextComponent("tiles.energy").append(new StringTextComponent(this.container.getData().get(0) + "/" + this.container.getData().get(1))), mouseX, mouseY);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
        GlStateManager.color4f(1f, 1f, 1f, 1f);
        if (this.minecraft != null) {
            this.minecraft.getTextureManager().bindTexture(BACK);
            this.blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
            this.renderEnergyBar(matrixStack);
            this.renderHammerTimer(matrixStack);
        }
    }

    private void renderEnergyBar(MatrixStack matrixStack) {
        float energyPer = this.container.getData().get(0) / (float) this.container.getData().get(1);
        int startY = this.guiTop + 16 + 52 - (int) (energyPer * 52);
        this.blit(matrixStack, this.guiLeft + 8, startY, 204, 14, 16, (int) (energyPer * 52));
    }

    private void renderHammerTimer(MatrixStack matrixStack) {
        float timePer = this.container.getData().get(2) / (float) this.container.getData().get(3);
        this.blit(matrixStack, this.guiLeft + 73, this.guiTop + 33, 220, 0, (int) (timePer * 22), 16);
    }


}
