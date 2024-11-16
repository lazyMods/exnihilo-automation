package net.marcosantos.exnihiloauto.compat;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import net.marcosantos.exnihiloauto.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.neoforge.common.ModConfigSpec;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

public class MachineProperties implements ICustomComponent {

	public final transient List<String> text = Lists.newArrayList();
	public transient int x;
	public transient int y;

	@Override
	public void build(int componentX, int componentY, int pageNumber) {
		this.x = componentX;
		this.y = componentY;
	}

	@Override
	public void render(GuiGraphics guiGraphics, IComponentRenderContext iComponentRenderContext, float v, int i, int i1) {
		int index = 0;
		for (String s : this.text) {
			var parse = TextBuilder.parse(s);
			var fontRenderer = Minecraft.getInstance().font;
			for (FormattedCharSequence iReorderingProcessor : fontRenderer.split(Component.literal(parse), 145)) {
				guiGraphics.drawString(fontRenderer, iReorderingProcessor, x, y + index * (fontRenderer.lineHeight + 3), 0);
				index++;
			}
		}
	}

	@Override
	public void onVariablesAvailable(UnaryOperator<IVariable> unaryOperator, HolderLookup.Provider provider) {
		String machineType = unaryOperator.apply(IVariable.wrap("#machine_type#", provider)).asString();
		Map<String, ModConfigSpec.ConfigValue> machineConfigs = Configs.machineConfigs.get(machineType);

		this.text.add(TextBuilder.create().style(true, '6').text("Machine Properties:").resetStyle().build());
		this.text.add(TextBuilder.create().text("").build());
		this.text.add(TextBuilder.create().list().style(true, '4').text("Speed:").resetStyle().build());
		this.text.add(TextBuilder.create().text("   ").text(machineConfigs.get("speed").get()).text(" ticks").build());
		this.text.add(TextBuilder.create().list().style(false, '4').text("Energy Capacity: ").resetStyle().build());
		this.text.add(TextBuilder.create().text("   ").text(machineConfigs.get("energy_capacity").get()).build());
	}

	private static class TextBuilder {

		private static final List<String> builder = new ArrayList<>();

		public static TextBuilder create() {
			builder.clear();
			return new TextBuilder();
		}

		public TextBuilder list() {
			builder.add("$li");
			return this;
		}

		public TextBuilder resetStyle() {
			builder.add("$r");
			return this;
		}

		public TextBuilder style(boolean bold, char color) {
			builder.add("$s(" + bold + ";" + color + ")");
			return this;
		}

		public TextBuilder text(Object text) {
			builder.add("$(" + text + ")");
			return this;
		}

		public String build() {
			var stringBuilder = new StringBuilder();
			builder.forEach(s -> stringBuilder.append(s).append(","));
			return stringBuilder.toString();
		}

		public static String parse(String s) {
			var types = s.split(",");
			var builder = new StringBuilder();
			int startIndex = 0;
			if (types[0].equals("$li")) {
				builder.append("\u25E6 ");
				startIndex++;
			}
			for (int i = startIndex; i < types.length; i++) {
				if (types[i].startsWith("$s")) {
					var styleVars = types[i].substring(2, types[i].length() - 1).split(";");
					boolean bold = Boolean.parseBoolean(styleVars[0]);
					var color = styleVars[1];
					if (bold)
						builder.append("\u00a7").append("l");
					builder.append("\u00a7").append(color);
				} else if (types[i].equals("$r")) {
					builder.append("\u00a7").append("r");
				} else if (types[i].startsWith("$(")) {
					builder.append(types[i], 2, types[i].length() - 1);
				}
			}
			return builder.toString();
		}
	}
}
