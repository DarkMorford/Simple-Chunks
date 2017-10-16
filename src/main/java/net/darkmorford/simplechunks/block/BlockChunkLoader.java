package net.darkmorford.simplechunks.block;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.darkmorford.simplechunks.SimpleChunks;
import net.darkmorford.simplechunks.compat.TOPInfoProvider;
import net.darkmorford.simplechunks.tileentity.TileEntityChunkLoader;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockChunkLoader extends Block implements ITileEntityProvider, TOPInfoProvider
{
	public BlockChunkLoader()
	{
		super(Material.ROCK);
		setUnlocalizedName("chunkloader");
		setRegistryName("chunkloader");
		setCreativeTab(CreativeTabs.MISC);

		setHardness(5.0F);
		setResistance(20.0F);
		setHarvestLevel("pickaxe", 2);
	}

	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityChunkLoader();
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (worldIn.isRemote)
		{
			return;
		}

		if (!(placer instanceof EntityPlayer))
		{
			SimpleChunks.logger.warn("Chunk loader placed by " + placer);
			return;
		}

		EntityPlayer player = (EntityPlayer)placer;

		ForgeChunkManager.Ticket tkt = ForgeChunkManager.requestPlayerTicket(SimpleChunks.instance, player.getName(), worldIn, ForgeChunkManager.Type.NORMAL);
		if (tkt != null)
		{
			SimpleChunks.logger.info("Received ticket from Forge Chunk Manager");
			tkt.getModData().setTag("position", NBTUtil.createPosTag(pos));

			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof TileEntityChunkLoader)
			{
				TileEntityChunkLoader loader = (TileEntityChunkLoader) te;
				loader.setChunkTicket(tkt);
				loader.forceChunks();
			}
		}
		else
		{
			SimpleChunks.logger.error("Unable to get ticket from Forge Chunk Manager!");
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		SimpleChunks.logger.info("Releasing chunkloading ticket");

		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityChunkLoader)
		{
			TileEntityChunkLoader loader = (TileEntityChunkLoader) te;

			loader.clearTicketChunks();
			ForgeChunkManager.Ticket tkt = loader.getChunkTicket();
			ForgeChunkManager.releaseTicket(tkt);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData hitData)
	{
		TileEntity te = world.getTileEntity(hitData.getPos());
		if (te instanceof TileEntityChunkLoader)
		{
			TileEntityChunkLoader loader = (TileEntityChunkLoader) te;
			ForgeChunkManager.Ticket tkt = loader.getChunkTicket();

			if (tkt != null)
			{
				String owner;
				if (tkt.isPlayerTicket())
				{
					owner = tkt.getPlayerName();
				}
				else
				{
					owner = tkt.getModId();
				}

				probeInfo.horizontal().text(TextFormatting.GREEN + "Owner: " + owner);
			}
			else
			{
				probeInfo.horizontal().text(TextFormatting.RED + "No chunk ticket found!");
			}
		}
	}
}
