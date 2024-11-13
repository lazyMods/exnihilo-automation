package lazy.exnihiloauto.compat.jei.category;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lazy.exnihiloauto.Ref;
import lazy.exnihiloauto.setup.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.ITooltipCallback;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import novamachina.exnihilosequentia.api.ExNihiloRegistries;
import novamachina.exnihilosequentia.api.compat.jei.JEISieveRecipe;
import novamachina.exnihilosequentia.api.crafting.sieve.MeshWithChance;
import novamachina.exnihilosequentia.api.crafting.sieve.SieveRecipe;
import novamachina.exnihilosequentia.common.item.mesh.MeshItem;
import novamachina.exnihilosequentia.common.utility.StringUtils;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;


/*
Code based on the exnihilo dry sieve recipes.
 */
public class AutoSieveCategory implements IRecipeCategory<JEISieveRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Ref.MOD_ID, "auto_sieve");
    private static final ResourceLocation texture = new ResourceLocation("exnihilosequentia", "textures/gui/jei_mid.png");

    private final IDrawable background;
    private final IDrawable icon;

    public AutoSieveCategory(IGuiHelper helper) {
        background = helper.createDrawable(texture, 0, 0, 166, 58);
        icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.AUTO_HAMMER.get()));
    }

    @Override
    @Nonnull
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    @Nonnull
    public Class<? extends JEISieveRecipe> getRecipeClass() {
        return JEISieveRecipe.class;
    }

    @Override
    @Nonnull
    public String getTitle() {
        return new TranslationTextComponent("block.exnihiloauto.auto_sieve").getString();
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    @Nonnull
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(JEISieveRecipe recipe, IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.getInputs());
        ingredients.setOutputs(VanillaTypes.ITEM, recipe.getResults());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, JEISieveRecipe recipe, @Nonnull IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 10, 38);
        recipeLayout.getItemStacks().set(0, recipe.getMesh());
        recipeLayout.getItemStacks().init(1, true, 10, 2);
        recipeLayout.getItemStacks().set(1, recipe.getSieveables());
        IFocus<?> focus = recipeLayout.getFocus();
        int slotIndex = 2;

        for (int i = 0; i < recipe.getResults().size(); ++i) {
            int slotX = 38 + i % 7 * 18;
            int slotY = 2 + i / 7 * 18;
            ItemStack outputStack = (ItemStack) recipe.getResults().get(i);
            recipeLayout.getItemStacks().init(slotIndex + i, false, slotX, slotY);
            recipeLayout.getItemStacks().set(slotIndex + i, outputStack);
            /*if (focus != null) {
                ItemStack focusStack = (ItemStack)focus.getValue();
                if (focus.getMode() == IFocus.Mode.OUTPUT && !focusStack.isEmpty() && focusStack.getItem() == outputStack.getItem() && focusStack.getDamageValue() == outputStack.getDamageValue()) {
                    recipeLayout.getItemStacks().setBackground(slotIndex + i, this.slotHighlight);
                }
            }*/
        }

        recipeLayout.getItemStacks().addTooltipCallback(new ITooltipCallback<ItemStack>() {
            @OnlyIn(Dist.CLIENT)
            public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<ITextComponent> tooltip) {
                if (!input) {
                    Multiset<String> condensedTooltips = HashMultiset.create();
                    List<SieveRecipe> drops = ExNihiloRegistries.SIEVE_REGISTRY.getDrops(((ItemStack) ((List) recipe.getInputs().get(1)).get(0)).getItem(), ((MeshItem) ((ItemStack) ((List) recipe.getInputs().get(0)).get(0)).getItem()).getMesh(), false);
                    Iterator var7 = drops.iterator();

                    label34:
                    while (true) {
                        SieveRecipe entry;
                        ItemStack drop;
                        do {
                            if (!var7.hasNext()) {
                                tooltip.add(new TranslationTextComponent("jei.sieve.dropChance"));
                                var7 = condensedTooltips.elementSet().iterator();

                                while (var7.hasNext()) {
                                    String line = (String) var7.next();
                                    tooltip.add(new StringTextComponent(" * " + condensedTooltips.count(line) + "x " + line));
                                }
                                break label34;
                            }

                            entry = (SieveRecipe) var7.next();
                            drop = entry.getDrop();
                        } while (!drop.sameItem(ingredient));

                        Iterator var10 = entry.getRolls().iterator();

                        while (var10.hasNext()) {
                            MeshWithChance meshWithChance = (MeshWithChance) var10.next();
                            condensedTooltips.add(StringUtils.formatPercent(meshWithChance.getChance()));
                        }
                    }
                }

            }
        });
    }
}
