package com.bitsmantra.inout.globals;


import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by paradox on 11/29/15.
 */
public class Global {

    public static PolygonRegion createPolygon(float[] color, float[] points, short[] indices){
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(color[0], color[1], color[2], color[3]);
        pix.fill();
        Texture textureSolid = new Texture(pix);
        PolygonRegion polygonRegion = new PolygonRegion(
                                                    new TextureRegion(textureSolid),
                                                    points,
                                                    indices
                                                );
        return polygonRegion;
    }
}
