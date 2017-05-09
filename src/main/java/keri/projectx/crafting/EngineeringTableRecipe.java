package keri.projectx.crafting;

import codechicken.lib.util.ItemUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import keri.projectx.tile.TileEntityEngineeringTable;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

public class EngineeringTableRecipe {

    private ItemStack output;
    private Object[] args;

    public EngineeringTableRecipe(ItemStack output, Object... args){
        this.output = output;
        this.args = args;
    }

    private ItemStack[] parse(Object[] args){
        List<ItemStack> stackList = Lists.newArrayList();
        List<String> rows = Lists.newArrayList();
        Map<Character, ItemStack> ingredients = Maps.newHashMap();

        for(int i = 0; i < args.length; i++){
            Object currentArgument = args[i];

            if(currentArgument instanceof String){
                rows.add((String)currentArgument);
            }
            else if(currentArgument instanceof Character){
                if(args.length >= i + 1){
                    Object nextArgument = args[i + 1];

                    if(nextArgument instanceof ItemStack){
                        ingredients.put((char)currentArgument, (ItemStack)nextArgument);
                    }
                }
            }
        }

        if(rows.size() > 3){
            throw new IllegalArgumentException(String.format("The Engineering Table only has 3 rows, not %d you dummy -_-", rows.size()));
        }

        int slotIndex = 0;

        for(int i = 0; i < rows.size(); i++){
            String row = rows.get(i);

            for(int j = 0; j < row.length(); j++){
                char currentCharacter = row.charAt(j);

                if(ingredients.containsKey(currentCharacter)){
                    if(currentCharacter == ' '){
                        stackList.add(slotIndex, null);
                    }
                    else{
                        stackList.add(slotIndex, ingredients.get(currentCharacter));
                    }
                }
                else{
                    throw new IllegalArgumentException(String.format("Recipe parsing failed because someone forgot to buy the stack for %s", Character.toString(currentCharacter)));
                }

                slotIndex++;
            }
        }

        return stackList.toArray(new ItemStack[stackList.size()]);
    }

    public boolean isValid(TileEntityEngineeringTable tile){
        ItemStack[] stacks = this.parse(this.args);
        int validItems = 0;

        for(int i = 0; i < stacks.length; i++){
            if(stacks[i] == null){
                if(tile.getStackInSlot(i) == null){
                    validItems++;
                }
                else{
                    return false;
                }
            }
            else{
                if(ItemUtils.areStacksSameType(tile.getStackInSlot(i), stacks[i])){
                    if(stacks[i].getTagCompound() != null){
                        if(tile.getStackInSlot(i).getTagCompound() != null){
                            if(ItemStack.areItemStackTagsEqual(tile.getStackInSlot(i), stacks[i])){
                                validItems++;
                            }
                        }
                        else{
                            return false;
                        }
                    }
                    else{
                        validItems++;
                    }
                }
            }
        }

        return validItems == 7;
    }

}
