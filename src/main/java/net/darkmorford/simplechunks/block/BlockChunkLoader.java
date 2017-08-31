package net.darkmorford.simplechunks.block;

import net.darkmorford.simplechunks.tileentity.TileEntityChunkLoader;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockChunkLoader extends Block implements ITileEntityProvider
{
	public BlockChunkLoader()
	{
		super(Material.ROCK);
		setUnlocalizedName("chunkloader");
		setRegistryName("chunkloader");
		setCreativeTab(CreativeTabs.MISC);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityChunkLoader();
	}
}
