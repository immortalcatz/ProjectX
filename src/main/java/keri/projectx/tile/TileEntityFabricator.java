package keri.projectx.tile;

import keri.ninetaillib.lib.tile.IInventoryAction;
import keri.ninetaillib.lib.tile.InventoryAction;
import keri.ninetaillib.lib.tile.TileEntityInventoryBase;
import keri.projectx.container.ContainerDummy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ITickable;

public class TileEntityFabricator extends TileEntityInventoryBase implements ITickable, IInventoryAction {

    private boolean needsUpdate = false;
    private IRecipe currentRecipe;

    public TileEntityFabricator() {
        super(19);
    }

    @Override
    public void update() {
        if(!world.isRemote){
            if(this.needsUpdate){
                this.loadRecipe();
                this.needsUpdate = false;
            }
        }
    }

    private void loadRecipe(){
        CraftingManager craftingManager = CraftingManager.getInstance();
        InventoryCrafting craftingMatrix = this.getCraftMatrix();
        this.currentRecipe = null;

        for(IRecipe recipe : craftingManager.getRecipeList()){
            if(recipe.matches(craftingMatrix, this.world)){
                this.currentRecipe = recipe;
                break;
            }
        }

        if(this.currentRecipe != null){
            this.setInventorySlotContents(9, this.currentRecipe.getCraftingResult(craftingMatrix).copy());
        }
        else{
            this.removeStackFromSlot(9);
        }
    }

    @Override
    public void onInventoryAction(EntityPlayer player, int index, int count, ItemStack stack, InventoryAction action) {
        this.needsUpdate = true;
    }

    private InventoryCrafting getCraftMatrix(){
        InventoryCrafting matrix = new InventoryCrafting(new ContainerDummy(), 3, 3);
        ItemStack[] stacks = new ItemStack[9];
        int li = 0;

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                int index = y + 3 * x;
                stacks[li] = this.getStackInSlot(index);
                li++;
            }
        }

        for(int i = 0; i < 9; i++){
            ItemStack stack = stacks[i];
            matrix.setInventorySlotContents(i, stack);
        }

        return matrix;
    }

}
