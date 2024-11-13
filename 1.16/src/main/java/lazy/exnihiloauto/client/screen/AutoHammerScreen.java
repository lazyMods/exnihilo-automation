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
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;

public class AutoHammerScreen extends ContainerScreen<AutoHammerContainer> {

    public static final ResourceLocation BACK = new ResourceLocation(Ref.MOD_ID, "textures/gui/auto_hammer.png");

    public AutoHammerScreen(AutoHammerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageWidth = 204;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (this.hoveredSlot != null) {
            if (!this.hoveredSlot.hasItem()) {
                if (this.hoveredSlot.index == 1) {
                    this.renderTooltip(matrixStack, new TranslationTextComponent("hammer.input"), mouseX, mouseY);
                } else if (this.hoveredSlot.index == 0) {
                    this.renderTooltip(matrixStack, new TranslationTextComponent("hammer.hammer"), mouseX, mouseY);
                }
            } else {
                this.renderTooltip(matrixStack, this.hoveredSlot.getItem(), mouseX, mouseY);
            }
        }
        if (mouseX > this.leftPos + 7 && mouseX < this.leftPos + 7 + 18 && mouseY > this.topPos + 15 && mouseY < this.topPos + 15 + 54) {
            this.renderTooltip(matrixStack, new TranslationTextComponent("tiles.energy").append(new StringTextComponent(this.menu.getData().get(0) + "/" + this.menu.getData().get(1))), mouseX, mouseY);
        }
        if(mouseX > this.leftPos + 181 && mouseX < this.leftPos + 181 + 18 && mouseY > this.topPos + 69 && mouseY < this.topPos + 69 + 16){
            this.renderTooltip(matrixStack, new TranslationTextComponent("tiles.openbookentry"), mouseX, mouseY);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
        GlStateManager._color4f(1f, 1f, 1f, 1f);
        if (this.minecraft != null) {
            this.minecraft.getTextureManager().bind(BACK);
            this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
            this.renderEnergyBar(matrixStack);
            this.renderHammerTimer(matrixStack);
        }
    }

    private void renderEnergyBar(MatrixStack matrixStack) {
        float energyPer = this.menu.getData().get(0) / (float) this.menu.getData().get(1);
        int startY = this.topPos + 16 + 52 - (int) (energyPer * 52);
        this.blit(matrixStack, this.leftPos + 8, startY, 204, 14, 16, (int) (energyPer * 52));
    }

    private void renderHammerTimer(MatrixStack matrixStack) {
        float timePer = this.menu.getData().get(2) / (float) this.menu.getData().get(3);
        this.blit(matrixStack, this.leftPos + 73, this.topPos + 33, 220, 0, (int) (timePer * 22), 16);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(mouseX > this.leftPos + 181 && mouseX < this.leftPos + 181 + 18 && mouseY > this.topPos + 69 && mouseY < this.topPos + 69 + 16){
            if(button != 0) return super.mouseClicked(mouseX, mouseY, button);
            PatchouliAPI.get().openBookEntry(new ResourceLocation(Ref.MOD_ID, "docs"), new ResourceLocation(Ref.MOD_ID, "autohammer"), 0);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
