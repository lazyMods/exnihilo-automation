package lazy.exnihiloauto.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.inventory.container.AutoSieveContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public class AutoSieveScreen extends ContainerScreen<AutoSieveContainer> {

    public static final ResourceLocation BACK = new ResourceLocation(Ref.MOD_ID, "textures/gui/auto_sieve.png");

    public AutoSieveScreen(AutoSieveContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if(this.hoveredSlot != null) {
            if(!this.hoveredSlot.getHasStack()) {
                if(this.hoveredSlot.slotNumber == 0) {
                    this.renderTooltip(matrixStack, new StringTextComponent("Input(Siftable Block)"), mouseX, mouseY);
                } else if(this.hoveredSlot.slotNumber == 1) {
                    this.renderTooltip(matrixStack, new StringTextComponent("Mesh"), mouseX, mouseY);
                }
            } else {
                this.renderTooltip(matrixStack, this.hoveredSlot.getStack(), mouseX, mouseY);
            }
        }
        if(mouseX > this.guiLeft + 7 && mouseX < this.guiLeft + 7 + 18 && mouseY > this.guiTop + 15 && mouseY < this.guiTop + 15 + 54) {
            this.renderTooltip(matrixStack,
                    new StringTextComponent("Energy: " + this.container.getData().get(0) + "/" + this.container.getData().get(1)),
                    mouseX, mouseY);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
        GlStateManager.color4f(1f, 1f, 1f, 1f);
        if(this.minecraft != null) {
            this.minecraft.getTextureManager().bindTexture(BACK);
            this.blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
            this.renderEnergyBar(matrixStack);
            this.renderSieveTimer(matrixStack);
        }
    }

    private void renderEnergyBar(MatrixStack matrixStack) {
        float energyPer = this.container.getData().get(0) / (float) this.container.getData().get(1);
        int startY = this.guiTop + 16 + 52 - (int) (energyPer * 52);
        this.blit(matrixStack, this.guiLeft + 8, startY, 176, 14, 16, (int) (energyPer * 52));
    }

    private void renderSieveTimer(MatrixStack matrixStack) {
        float timePer = this.container.getData().get(2) / (float) this.container.getData().get(3);
        this.blit(matrixStack, this.guiLeft + 54, this.guiTop + 36, 176, 0, 14, (int) (timePer * 14));
    }

}
