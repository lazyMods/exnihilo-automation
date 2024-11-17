package net.marcosantos.exnihiloauto.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.world.inventory.AutoSilkerMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class AutoSilkerScreen extends AbstractContainerScreen<AutoSilkerMenu> {

	public static final ResourceLocation BACK = new ResourceLocation(ExNihiloAuto.MODID, "textures/gui/auto_silker.png");

	public AutoSilkerScreen(AutoSilkerMenu screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		this.imageWidth = 204;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void render(PoseStack guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		if (this.hoveredSlot != null) {
			if (!this.hoveredSlot.hasItem()) {
				if (this.hoveredSlot.index == 1) {
					this.renderTooltip(guiGraphics, Component.translatable("silker.leaves"), mouseX, mouseY);
				} else if (this.hoveredSlot.index == 0) {
					this.renderTooltip(guiGraphics, Component.translatable("silker.silk_worm"), mouseX, mouseY);
				}
			} else {
				this.renderTooltip(guiGraphics, mouseX, mouseY);
			}
		}
		if (mouseX > this.leftPos + 7 && mouseX < this.leftPos + 7 + 18 && mouseY > this.topPos + 15 && mouseY < this.topPos + 15 + 54) {
			this.renderTooltip(guiGraphics, Component.translatable("tiles.energy").append(Component.literal(this.menu.getData().get(0) + "/" + this.menu.getData().get(1))), mouseX, mouseY);
		}
		if (mouseX > this.leftPos + 181 && mouseX < this.leftPos + 181 + 18 && mouseY > this.topPos + 69 && mouseY < this.topPos + 69 + 16) {
			this.renderTooltip(guiGraphics, Component.translatable("tiles.openbookentry"), mouseX, mouseY);
		}
		if (ExNihiloAuto.HAS_PATCHOULI) {
			if (mouseX > this.leftPos + 73 && mouseX < this.leftPos + 73 + 22 && mouseY > this.topPos + 52 && mouseY < this.topPos + 52 + 5) {
				this.renderTooltip(guiGraphics, Component.translatable("silker.silkworm_timer"), mouseX, mouseY);
			}
		}
	}

	@Override
	protected void renderBg(@Nonnull PoseStack guiGraphics, float v, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, BACK);
		this.blit(guiGraphics, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		this.renderEnergyBar(guiGraphics);
		this.renderSilkerTimer(guiGraphics);
		this.renderSilkwormTimer(guiGraphics);
		if (ExNihiloAuto.HAS_PATCHOULI) {
			this.blit(guiGraphics, this.leftPos + 177, this.topPos + 64, 204, 52, 28, 26);
		}
	}

	private void renderEnergyBar(PoseStack guiGraphics) {
		int startY = this.topPos + 16;
		float scaled = 52.f * ((float) menu.getData().get(0) / menu.getData().get(1));
		this.blit(guiGraphics, this.leftPos + 8, startY + (52 - (int) scaled), 204, 0, 16, (int) scaled);
	}

	private void renderSilkerTimer(PoseStack guiGraphics) {
		float timePer = this.menu.getData().get(2) / (float) this.menu.getData().get(3);
		this.blit(guiGraphics, this.leftPos + 73, this.topPos + 33, 220, 0, (int) (timePer * 22), 16);
	}

	private void renderSilkwormTimer(PoseStack guiGraphics) {
		float timePer = this.menu.getData().get(4) / (float) this.menu.getData().get(5);
		this.blit(guiGraphics, this.leftPos + 73, this.topPos + 52, 220, 16, (int) (timePer * 22), 5);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (ExNihiloAuto.HAS_PATCHOULI) {
			if (mouseX > this.leftPos + 181 && mouseX < this.leftPos + 181 + 18 && mouseY > this.topPos + 69 && mouseY < this.topPos + 69 + 16) {
				if (button != 0)
					return super.mouseClicked(mouseX, mouseY, button);
				PatchouliAPI.get().openBookEntry(new ResourceLocation(ExNihiloAuto.MODID, "docs"), new ResourceLocation(ExNihiloAuto.MODID, "autosilker"), 0);
				return true;
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
}
