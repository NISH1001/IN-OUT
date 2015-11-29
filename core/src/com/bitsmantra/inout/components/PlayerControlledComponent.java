package com.bitsmantra.inout.components;

import com.badlogic.ashley.core.*;

/**
 * Created by paradox on 11/28/15.
 */
public class PlayerControlledComponent implements Component{
    public boolean controllable = true;

    public boolean get(){
        return  this.controllable;
    }
}
