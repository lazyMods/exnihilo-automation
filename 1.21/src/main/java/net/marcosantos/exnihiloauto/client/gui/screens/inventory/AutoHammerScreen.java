package net.marcosantos.exnihiloauto.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.marcosantos.exnihiloauto.ExNihiloAuto;
import net.marcosantos.exnihiloauto.world.inventory.AutoHammerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import vazkii.patchouli.api.PatchouliAPI;

public class AutoHammerScreen extends AbstractContainerScreen<AutoHammerMenu> {

	public static final ResourceLocation BACK = ResourceLocation.fromNamespaceAndPath(ExNihiloAuto.MODID, "textures/gui/auto_hammer.png");

	public AutoHammerScreen(AutoHammerMenu screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		this.imageWidth = 204;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		if (this.hoveredSlot != null) {
			if (!this.hoveredSlot.hasItem()) {
				if (this.hoveredSlot.index == 1) {
					guiGraphics.renderTooltip(this.font, Component.translatable("hammer.input"), mouseX, mouseY);
				} else if (this.hoveredSlot.index == 0) {
					guiGraphics.renderTooltip(this.font, Component.translatable("hammer.hammer"), mouseX, mouseY);
				}
			} else {
				this.renderTooltip(guiGraphics, mouseX, mouseY);
			}
		}
		if (mouseX > this.leftPos + 7 && mouseX < this.leftPos + 7 + 18 && mouseY > this.topPos + 15 && mouseY < this.topPos + 15 + 54) {
			guiGraphics.renderTooltip(this.font, Component.translatable("tiles.energy").append(Component.literal(this.menu.getData().get(0) + "/" + this.menu.getData().get(1))), mouseX, mouseY);
		}
		if (ExNihiloAuto.HAS_PATCHOULI) {
			if (mouseX > this.leftPos + 181 && mouseX < this.leftPos + 181 + 18 && mouseY > this.topPos + 69 && mouseY < this.topPos + 69 + 16) {
				guiGraphics.renderTooltip(this.font, Component.translatable("tiles.openbookentry"), mouseX, mouseY);
			}
		}
	}

	@Override
	public void renderBg(@Nonnull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		GlStateManager._clearColor(1, 1, 1, 1);
		guiGraphics.blit(BACK, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		this.renderEnergyBar(guiGraphics);
		this.renderHammerTimer(guiGraphics);
		if (ExNihiloAuto.HAS_PATCHOULI) {
			guiGraphics.blit(BACK, this.leftPos + 177, this.topPos + 64, 204, 52, 28, 26);
		}
	}

	private void renderEnergyBar(GuiGraphics guiGraphics) {
		int startY = this.topPos + 16;
		float scaled = 52.f * ((float) menu.getData().get(0) / menu.getData().get(1));
		guiGraphics.blit(BACK, this.leftPos + 8, startY + (52 - (int) scaled), 204, 0, 16, (int) scaled);
	}

	private void renderHammerTimer(GuiGraphics guiGraphics) {
		float timePer = this.menu.getData().get(2) / (float) this.menu.getData().get(3);
		guiGraphics.blit(BACK, this.leftPos + 73, this.topPos + 33, 220, 0, (int) (timePer * 22), 16);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (ExNihiloAuto.HAS_PATCHOULI) {
			if (mouseX > this.leftPos + 181 && mouseX < this.leftPos + 181 + 18 && mouseY > this.topPos + 69 && mouseY < this.topPos + 69 + 16) {
				if (button != 0)
					return super.mouseClicked(mouseX, mouseY, button);
				PatchouliAPI.get().openBookEntry(
						ResourceLocation.fromNamespaceAndPath(ExNihiloAuto.MODID, "docs"),
						ResourceLocation.fromNamespaceAndPath(ExNihiloAuto.MODID, "autohammer"),
						0);
				return true;
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
}
