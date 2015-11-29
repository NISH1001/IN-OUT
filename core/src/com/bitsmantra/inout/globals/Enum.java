package com.bitsmantra.inout.globals;

/**
 * Created by paradox on 11/29/15.
 */
public class Enum {

    public enum SHAPE{
        CIRCLE(0),
        RECTANGLE(1);

        private int shape;

        SHAPE(int shape){
            this.shape = shape;
        }

        public int getShape(){
            return this.shape;
        }
    }
}
