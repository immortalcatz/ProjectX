/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not destribute or redistribute this software without the
 * explicit permission of the developer!
 */

package keri.projectx.multiblock;

import com.google.common.collect.Maps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.Vec3i;

import java.util.Map;

public class MultiblockPattern {

    private Map<Vec3i, IBlockState> pattern;

    private MultiblockPattern(Map<Vec3i, IBlockState> pattern){
        this.pattern = pattern;
    }

    public static class Builder {

        private Map<Character, IBlockState> partDefinetions;
        private Map<Vec3i, Character> partLocations;
        private int y = 0;

        private Builder(){
            this.partDefinetions = Maps.newHashMap();
            this.partLocations = Maps.newHashMap();
        }

        public static Builder newBuilder(){
            return new Builder();
        }

        public void addLayer(String... params){
            for(int z = 0; z < params.length; z++){
                String row = params[z];

                for(int x = 0; x < row.length(); x++){
                    Character part = row.charAt(x);

                    if(!this.partDefinetions.containsKey(part)){
                        throw new MultiblockPatternException(String.format("Part \'%s\' is undefined!", part));
                    }

                    Vec3i position = new Vec3i(x, this.y, z);
                    this.partLocations.put(position, part);
                }
            }

            this.y++;
        }

        public void addPartDefinition(char part, IBlockState state){
            if(state == null){
                throw new MultiblockPatternException(String.format("Definition for part \'%s\' is null!", part));
            }

            if(this.partDefinetions.containsKey(part)){
                throw new MultiblockPatternException(String.format("Definition for part \'%s\' was registered twice!", part));
            }

            this.partDefinetions.put(part, state);
        }

        public MultiblockPattern build(){
            Map<Vec3i, IBlockState> pattern = Maps.newHashMap();

            for(Map.Entry<Vec3i, Character> partLocation : this.partLocations.entrySet()){
                if(!this.partDefinetions.containsKey(partLocation.getValue())){
                    throw new MultiblockPatternException("There was an error compiling the multiblock pattern!");
                }

                IBlockState partDefinition = this.partDefinetions.get(partLocation.getValue());
                pattern.put(partLocation.getKey(), partDefinition);
            }

            return new MultiblockPattern(pattern);
        }

    }

    private static class MultiblockPatternException extends RuntimeException {

        public MultiblockPatternException(String message) {
            super(message);
        }

    }

}
