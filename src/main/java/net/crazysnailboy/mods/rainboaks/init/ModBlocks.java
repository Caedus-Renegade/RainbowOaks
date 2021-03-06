package net.crazysnailboy.mods.rainboaks.init;

import net.crazysnailboy.mods.rainboaks.block.BlockRainbowLeaves;
import net.crazysnailboy.mods.rainboaks.block.BlockRainbowLog;
import net.crazysnailboy.mods.rainboaks.block.BlockRainbowSapling;
import net.crazysnailboy.mods.rainboaks.item.ItemRainbowLeaves;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks
{

	public static final Block LOG = new BlockRainbowLog().setRegistryName("rainboak_log").setUnlocalizedName("log.rainboak");
	public static final BlockLeaves LEAVES = (BlockLeaves)(new BlockRainbowLeaves().setRegistryName("rainboak_leaves").setUnlocalizedName("leaves.rainboak"));
	public static final Block SAPLING = new BlockRainbowSapling().setRegistryName("rainboak_sapling").setUnlocalizedName("sapling.rainboak");

	public static void registerBlocks()
	{
		registerBlock(LOG);
		registerBlock(LEAVES, new ItemRainbowLeaves(LEAVES));
		registerBlock(SAPLING);

		Blocks.FIRE.setFireInfo(LOG, 5, 5);
		Blocks.FIRE.setFireInfo(LEAVES, 30, 60);
	}

	public static void registerInventoryModels()
	{
		registerInventoryModel(LOG);
		registerInventoryModel(LEAVES);
		registerInventoryModel(SAPLING);
	}

	public static void registerOreDictionaryEntries()
	{
		OreDictionary.registerOre("logWood", new ItemStack(LOG, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("treeSapling", new ItemStack(SAPLING, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("treeLeaves", new ItemStack(LEAVES, 1, OreDictionary.WILDCARD_VALUE));
	}


	private static void registerBlock(Block block)
	{
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	private static void registerBlock(Block block, ItemBlock item)
	{
		GameRegistry.register(block);
		GameRegistry.register(item.setRegistryName(block.getRegistryName()));
	}

	private static void registerInventoryModel(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}


}
