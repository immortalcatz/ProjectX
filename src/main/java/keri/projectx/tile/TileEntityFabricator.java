package keri.projectx.tile;

import codechicken.lib.inventory.InventoryRange;
import codechicken.lib.inventory.InventoryUtils;
import codechicken.lib.util.ItemUtils;
import com.google.common.collect.Lists;
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

import java.util.List;

public class TileEntityFabricator extends TileEntityInventoryBase implements ITickable, IInventoryAction {

    private boolean needsUpdate = false;
    private IRecipe currentRecipe;

    public TileEntityFabricator() {
        super(23);
    }

    @Override
    public void update() {
        if(!world.isRemote){
            if(this.needsUpdate){
                this.loadRecipe();
                this.needsUpdate = false;
            }

            if(this.currentRecipe != null){
                if(this.areIngredientsValid() && this.canCraft()){
                    this.craft();
                }
            }
        }
    }

    @Override
    public void onInventoryAction(EntityPlayer player, int index, int count, ItemStack stack, InventoryAction action) {
        if(action == InventoryAction.SET_SLOT_CONTENT){
            if(index < 9){
                this.needsUpdate = true;
            }
        }
    }

    private void craft(){
        List<ItemStack> ingredients = Lists.newArrayList();

        for(int i = 0; i < 9; i++){
            if(!this.getStackInSlot(i).isEmpty()){
                ItemStack stack = this.getStackInSlot(i).copy();
                stack.setCount(1);
                ingredients.add(stack);
            }
        }

        for(ItemStack ingredient : ingredients){
            for(int i = 0; i < 9; i++){
                ItemStack stack = this.getStackInSlot(i + 10).copy();

                if(ItemUtils.areStacksSameType(stack, ingredient)){
                    this.decrStackSize(i + 10, 1);
                }
            }
        }

        InventoryUtils.insertItem(new InventoryRange(this, 10, 19), this.currentRecipe.getRecipeOutput().copy(), false);
    }

    private boolean areIngredientsValid(){
        List<ItemStack> ingredients = Lists.newArrayList();
        int validIngredients = 0;

        for(int i = 0; i < 9; i++){
            if(!this.getStackInSlot(i).isEmpty()){
                ItemStack stack = this.getStackInSlot(i).copy();
                stack.setCount(1);
                ingredients.add(stack);
            }
        }

        for(ItemStack ingredient : ingredients){
            for(int i = 0; i < 9; i++){
                ItemStack stack = this.getStackInSlot(i + 10).copy();

                if(ItemUtils.areStacksSameType(stack, ingredient)){
                    validIngredients++;
                    break;
                }
            }
        }

        return validIngredients == ingredients.size();
    }

    private boolean canCraft(){
        int freeSpace = 0;

        for(int i = 0; i < 9; i++){
            ItemStack stack = this.getStackInSlot(i + 10).copy();

            if(stack.isEmpty()){
                freeSpace++;
            }
            else{
                if(ItemUtils.areStacksSameType(stack, this.currentRecipe.getRecipeOutput().copy())){
                    if(stack.getCount() < stack.getMaxStackSize()){
                        freeSpace++;
                    }
                }
            }
        }

        return freeSpace > 0;
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

    private InventoryCrafting getCraftMatrix(){
        InventoryCrafting matrix = new InventoryCrafting(new ContainerDummy(), 3, 3);
        ItemStack[] stacks = new ItemStack[9];
        int li = 0;

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                int index = y + 3 * x;
                stacks[li] = this.getStackInSlot(index).copy();
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
