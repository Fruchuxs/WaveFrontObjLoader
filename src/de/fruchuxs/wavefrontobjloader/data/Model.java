/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.fruchuxs.wavefrontobjloader.data;

import java.util.List;
import javax.media.opengl.GL2;

/**
 *
 * @author FloH
 */
public class Model {
    private Integer listIndex;
    private List<FaceGroup> facesGroup;
    private boolean listCreated;
    
    public Model(List<FaceGroup> pFacesGroup, Integer pListIndex) {
        facesGroup = pFacesGroup;
        listIndex = pListIndex;
        listCreated = false;
    }
    
    public void draw(GL2 gl) {
        if(!listCreated) {
            createList(gl);
            listCreated = true;
        }
        
        gl.glPushMatrix();
        gl.glCallList(listIndex);
        gl.glPopMatrix();
    }
    
    private void createList(GL2 gl) {
        gl.glNewList(listIndex, GL2.GL_COMPILE);
            for(FaceGroup i : facesGroup) {
                i.draw(gl);
            }
        gl.glEndList();
        
        for(FaceGroup i : facesGroup) {
                i.draw(gl);
            }
    }
}