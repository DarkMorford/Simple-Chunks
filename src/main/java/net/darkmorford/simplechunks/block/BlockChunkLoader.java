package net.darkmorford.simplechunks.block;

import net.darkmorford.simplechunks.SimpleChunks;
import net.darkmorford.simplechunks.tileentity.TileEntityChunkLoader;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import org.apache.logging.log4j.Level;

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
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityChunkLoader();
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (!(placer instanceof EntityPlayer))
		{
			return;
		}

		EntityPlayer player = (EntityPlayer)placer;

		ForgeChunkManager.Ticket tkt = ForgeChunkManager.requestPlayerTicket(SimpleChunks.instance, player.getName(), worldIn, ForgeChunkManager.Type.NORMAL);
		if (tkt != null)
		{
			SimpleChunks.logger.log(Level.INFO, "Received ticket from Forge Chunk Manager");
			tkt.getModData().setLong("position", pos.toLong());
			TileEntityChunkLoader loader = (TileEntityChunkLoader)worldIn.getTileEntity(pos);
			loader.setChunkTicket(tkt);
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		SimpleChunks.logger.log(Level.INFO, "Releasing chunkloading ticket");
		TileEntityChunkLoader loader = (TileEntityChunkLoader)worldIn.getTileEntity(pos);

		ForgeChunkManager.Ticket tkt = loader.getChunkTicket();
		ForgeChunkManager.releaseTicket(tkt);

		super.breakBlock(worldIn, pos, state);
	}
}
