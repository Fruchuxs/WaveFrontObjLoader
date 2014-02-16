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
public class FaceGroup {
    private List<Face> faces;
    private Material facesMaterial;
    
    public FaceGroup() {
        faces = new ArrayList<>();
        facesMaterial = null;
    }
    
    public FaceGroup(Material pMaterial) {
        this();
        facesMaterial = pMaterial;
    }
    
    public void draw(GL2 gl) {
        if(facesMaterial != null) {
            facesMaterial.loadTextures();
            facesMaterial.enableTextures(gl);
        }
        
        drawMaterial(gl);
        for(Face i : faces) {
            i.draw(gl);
        }
        if(facesMaterial != null) {
            facesMaterial.disableTextures(gl);
        }
    }
    
    private void drawMaterial(GL2 gl) {
        /*gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glEnable(GL2.GL_TEXTURE_2D);*/
        Float[] ks = facesMaterial.getKs();
        Float[] ka = facesMaterial.getKa();
        Float[] kd = facesMaterial.getKd();
        
        if(ks != null) {
            gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, getAsPrim(ks), 0);
            
            if(facesMaterial.getNs() != null) {
                gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, new float[] { facesMaterial.getNs() }, 0);
            }
        }
        
        if(ka != null) {
            gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, getAsPrim(ka), 0);
        }
        
        if(kd != null) {
            gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, getAsPrim(kd), 0);
        }
    }
    
    private float[] getAsPrim(Float[] x) {
        float[] result = new float[x.length];
        
        for(int i = 0; i < x.length; i++) {
            if(x[i] == null) {
                x[i] = 0f;
            }
            
            result[i] = x[i];
        }
        
        return result;
    }
    
    public boolean addFace(Face pToAdd) {
        return faces.add(pToAdd);
    }

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }

    public Material getFacesMaterial() {
        return facesMaterial;
    }

    public void setFacesMaterial(Material facesMaterial) {
        this.facesMaterial = facesMaterial;
    }
}
