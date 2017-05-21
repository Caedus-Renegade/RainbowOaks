package net.crazysnailboy.mods.rainboaks.block;

import java.util.Random;
import net.crazysnailboy.mods.rainboaks.common.config.ModConfiguration;
import net.crazysnailboy.mods.rainboaks.world.gen.feature.WorldGenLargeRainbowTree;
import net.crazysnailboy.mods.rainboaks.world.gen.feature.WorldGenSmallRainbowTree;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockRainbowSapling extends BlockBush implements IGrowable
{

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
	protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

	public BlockRainbowSapling()
	{
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
		this.setCreativeTab(CreativeTabs.DECORATIONS);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return SAPLING_AABB;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (!world.isRemote)
		{
			this.checkAndDropBlock(world, pos, state);
			if (world.getLightFromNeighbors(pos.up()) >= 9 && random.nextInt(7) == 0)
			{
				this.grow(world, pos, state, random);
			}
		}
	}

	public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (((Integer)state.getValue(STAGE)).intValue() == 0)
		{
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		}
		else
		{
			this.generateTree(worldIn, pos, state, rand);
		}
	}

	public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
		WorldGenerator worldgenerator = (WorldGenerator)(rand.nextInt(ModConfiguration.largeTreeChance) == 0 ? new WorldGenLargeRainbowTree(true) : new WorldGenSmallRainbowTree(true));
		int i = 0;
		int j = 0;
		boolean largeTree = false;

		IBlockState air = Blocks.AIR.getDefaultState();

		if (largeTree)
		{
			worldIn.setBlockState(pos.add(i, 0, j), air, 4);
			worldIn.setBlockState(pos.add(i + 1, 0, j), air, 4);
			worldIn.setBlockState(pos.add(i, 0, j + 1), air, 4);
			worldIn.setBlockState(pos.add(i + 1, 0, j + 1), air, 4);
		}
		else
		{
			worldIn.setBlockState(pos, air, 4);
		}

		if (!worldgenerator.generate(worldIn, rand, pos.add(i, 0, j)))
		{
			if (largeTree)
			{
				worldIn.setBlockState(pos.add(i, 0, j), state, 4);
				worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
				worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
				worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
			}
			else
			{
				worldIn.setBlockState(pos, state, 4);
			}
		}

	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return (double)worldIn.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		this.grow(worldIn, pos, state, rand);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | ((Integer)state.getValue(STAGE)).intValue() << 3;
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { STAGE });
	}

}
