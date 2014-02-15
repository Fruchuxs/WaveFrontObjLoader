/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.fruchuxs.wavefrontobjloader.data;

import java.util.List;
import java.util.Map;
import javax.media.opengl.GL2;

/**
 *
 * @author FloH
 */
public class ImportedModel {
    private Integer listIndex;
    private List<FaceGroup> facesGroup;
    private List<Face> listOfAllFaces;
    private boolean listCreated;
    private Map<String, Float> extremPoints;
    
    public ImportedModel(List<FaceGroup> pFacesGroup, List<Face> allFaces, Map<String, Float> pExtremPoints, Integer pListIndex) {
        facesGroup = pFacesGroup;
        listIndex = pListIndex;
        listCreated = false;
        extremPoints = pExtremPoints;
        listOfAllFaces = allFaces;
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
    
    /**
     * Get an extrem Point!
     * left, right, top, bottom, near, far
     * @param pPoint
     * @return 
     */
    public Float getExtremPoint(String pPoint) {
        return extremPoints.get(pPoint);
    }

    public List<Face> getFacesList() {
        return listOfAllFaces;
    }
    
    public Integer getName() {
        return listIndex;
    }
}
