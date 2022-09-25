package joshuaepstein.template_mod.init;

import joshuaepstein.template_mod.Main;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;

public class ModRecipes {
//	public static final IRecipeType<TemplateRecipe> RECIPE = new IRecipeType<TemplateRecipe>() {
//		public String toString(){
//			return Main.id("template_recipe").toString();
//		}
//	};

	public static void registerRecipes(RegistryEvent.Register<RecipeSerializer<?>> event) {
//		Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(RECIPE.toString()), RECIPE);
//		event.getRegistry().register(TemplateRecipe.SERIALIZER);

	}

	public static class Serializer {
		public static void registerRecipes(RegistryEvent.Register<RecipeSerializer<?>> event){
//			Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(RECIPE.toString()), RECIPE);
//			event.getRegistry().register(TemplateRecipe.SERIALIZER);
		}

		private static <T extends Recipe<?>> SimpleRecipeSerializer<T> register(RegistryEvent.Register<RecipeSerializer<?>> event, String name, SimpleRecipeSerializer<T> serializer) {
			serializer.setRegistryName(Main.id(name));
			event.getRegistry().register(serializer);
			return serializer;
		}
	}

}
