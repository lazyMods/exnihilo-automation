package net.marcosantos.exnihiloauto.compat.jei.utils;

import net.minecraft.world.item.crafting.Ingredient;

public class TwoToOneRecipe {

	public final Ingredient primaryInput, secondaryInput, output;

	public TwoToOneRecipe(Ingredient primaryInput, Ingredient secondaryInput, Ingredient output) {
		this.primaryInput = primaryInput;
		this.secondaryInput = secondaryInput;
		this.output = output;
	}

	@Override
	public String toString() {
		return "TwoToOneRecipe{" +
				"primaryInput=" + primaryInput.getItems()[0].toString() +
				", secondaryInput=" + secondaryInput.getItems()[0].toString() +
				", output=" + output.getItems()[0] +
				'}';
	}
}
