/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.fruchuxs.wavefrontobjloader.data;

import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL2;

/**
 *
 * @author FloH
 */
public class Face {
    private List<Float[]> vertexCoordList;
    private List<Float[]> normalCoordList;
    private List<Float[]> textureCoordList;
    
    public Face() {
        vertexCoordList = new ArrayList<>();
        normalCoordList = new ArrayList<>();
        textureCoordList = new ArrayList<>();
    }
    
    public boolean addNormalCoords(Float[] e) {
        return normalCoordList.add(e);
    }

    public boolean addTextureCoords(Float[] e) {
        return textureCoordList.add(e);
    }
    
    public boolean addVertexCoords(Float[] e) {
        return vertexCoordList.add(e);
    }
    
    public void draw(GL2 gl) {
        int mode;
        
        switch(vertexCoordList.size()) {
            case 3: {
                mode = GL2.GL_TRIANGLES;
                break;
            }
            case 4: {
                mode = GL2.GL_QUADS;
                break;
            }
            default: {
                mode = GL2.GL_POLYGON;
            }
        }
        
        gl.glBegin(mode);
            for(int i = 0; i < vertexCoordList.size(); i++) {
                gl.glNormal3f(normalCoordList.get(i)[0], normalCoordList.get(i)[1], normalCoordList.get(i)[2]);
                gl.glTexCoord2d(textureCoordList.get(i)[0], textureCoordList.get(i)[1]);
                gl.glVertex3f(vertexCoordList.get(i)[0], vertexCoordList.get(i)[1], vertexCoordList.get(i)[2]);
            }
        gl.glEnd();
    }
}
