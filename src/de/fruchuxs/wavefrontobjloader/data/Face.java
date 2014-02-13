/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.fruchuxs.wavefrontobjloader.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.media.opengl.GL2;

/**
 *
 * Stellt eine Flaeche dar mit Punkten (verticles), normalen (normals) und 
 * Texturcoordinaten.
 * @author Fruchuxs
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
        Float[] normalCoords, texCoords, vertexCoords;
        
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
                normalCoords = normalCoordList.get(i);
                texCoords = textureCoordList.get(i);
                vertexCoords = vertexCoordList.get(i);
                
                if(normalCoords != null) {
                    gl.glNormal3f(normalCoords[0], normalCoords[1], normalCoords[2]);
                }
                
                if(texCoords != null) {
                    gl.glTexCoord2d(texCoords[0], texCoords[1]);
                }
                
                if(vertexCoords != null) {
                    gl.glVertex3f(vertexCoords[0], vertexCoords[1], vertexCoords[2]);
                }
            }
        gl.glEnd();
    }
}
