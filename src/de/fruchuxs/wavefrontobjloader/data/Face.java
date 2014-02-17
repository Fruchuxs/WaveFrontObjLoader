package de.fruchuxs.wavefrontobjloader.data;

import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL2;

/**
 *
 * Stellt eine Flaeche dar mit Punkten (verticles), normalen (normals) und 
 * Texturcoordinaten.
 */
public class Face {
    private List<Float[]> vertexCoordList;
    private List<Float[]> normalCoordList;
    private List<Float[]> textureCoordList;
    
    /**
     * Erstellt eine leere Fleache
     */
    public Face() {
        vertexCoordList = new ArrayList<>();
        normalCoordList = new ArrayList<>();
        textureCoordList = new ArrayList<>();
    }
    
    /**
     * Fuegt ein Array mit Normalen Koordinaten hinzu (delegate Method fuer arraylist)
     * 
     * @param e Koordinaten die hinzugefuegt werden
     * @return true wenn erfolgreich hinzugefuegt, andernfalls false
     */
    public boolean addNormalCoords(Float[] e) {
        return normalCoordList.add(e);
    }

    /**
     * Fuegt ein Array mit Texture Koordinaten hinzu (delegate Method fuer arraylist)
     * 
     * @param e Koordinaten die hinzugefuegt werden
     * @return true wenn erfolgreich hinzugefuegt, andernfalls false
     */
    public boolean addTextureCoords(Float[] e) {
        return textureCoordList.add(e);
    }
    
    /**
     * Fuegt ein Array mit Vertex Koordinaten hinzu (delegate Method fuer arraylist)
     * 
     * @param e Koordinaten die hinzugefuegt werden
     * @return true wenn erfolgreich hinzugefuegt, andernfalls false
     */
    public boolean addVertexCoords(Float[] e) {
        return vertexCoordList.add(e);
    }
    
    /**
     * Zeichnet die Flaeche
     * 
     * @param gl Momentan OpenGL Kontext
     */
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

    /**
     * Gibt die Vertex Koordinaten-Liste zurueck
     * 
     * @return Vertex Liste
     */
    public List<Float[]> getVertexCoordList() {
        return vertexCoordList;
    }

    /**
     * Setzt eine Vertex Koordinatenliste
     * 
     * @param vertexCoordList Liste die gesetzt werden soll
     */
    public void setVertexCoordList(List<Float[]> vertexCoordList) {
        this.vertexCoordList = vertexCoordList;
    }
    
    /**
     * Gibt die Normalenliste zurueck
     * 
     * @return Liste der Normalen
     */
    public List<Float[]> getNormalCoordList() {
        return normalCoordList;
    }

    /**
     * Setzt die normalen Koordinatenliste
     * 
     * @param normalCoordList Liste mit den Normalen 
     */
    public void setNormalCoordList(List<Float[]> normalCoordList) {
        this.normalCoordList = normalCoordList;
    }

    /**
     * Gibt die Liste der Texture Koordinaten
     * 
     * @return Liste der Texture Koordinaten 
     */
    public List<Float[]> getTextureCoordList() {
        return textureCoordList;
    }

    /**
     * Setzt die Liste mit den Texture Koordinaten
     * 
     * @param textureCoordList Setz die Liste mit den Texturekoordinaten
     */
    public void setTextureCoordList(List<Float[]> textureCoordList) {
        this.textureCoordList = textureCoordList;
    }
}
