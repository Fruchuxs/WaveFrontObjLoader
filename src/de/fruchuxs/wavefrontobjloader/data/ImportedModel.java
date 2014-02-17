package de.fruchuxs.wavefrontobjloader.data;

import java.util.List;
import java.util.Map;
import javax.media.opengl.GL2;

/**
 * Das finale Imported Model,
 * bestehend mit einer listIndex ID => kompilierte Liste aus OpenGL, teilweise wohl deprecated
 * Den facesGroup und einer Liste mit allen Flaechen und den entsprechenden Extrempunkten
 */
public class ImportedModel {
    /**
     * OpenGL Compiled List ID - wird vom ModelLoader vergeben
     */
    private Integer listIndex;
    
    /**
     * Gruppe mit Flaechen und deren verwendetes Material
     */
    private List<FaceGroup> facesGroup;
    
    /**
     * Liste mit allen Flaechen
     */
    private List<Face> listOfAllFaces;
    
    /**
     * Pruefvariable ob die compiled Liste erstellt wurde
     */
    private boolean listCreated;
    
    /**
     * Die Extrempunkte
     */
    private Map<String, Float> extremPoints;
    
    public ImportedModel(List<FaceGroup> pFacesGroup, List<Face> allFaces, Map<String, Float> pExtremPoints, Integer pListIndex) {
        facesGroup = pFacesGroup;
        listIndex = pListIndex;
        listCreated = false;
        extremPoints = pExtremPoints;
        listOfAllFaces = allFaces;
    }
    
    /**
     * Zeichnet das Model
     * 
     * @param gl Momentaner OpenGL Kontext 
     */
    public void draw(GL2 gl) {
        if(!listCreated) {
            createList(gl);
            listCreated = true;
        }
        
        gl.glPushMatrix();
        gl.glCallList(listIndex);
        gl.glPopMatrix();
    }
    
    /**
     * Erzeugt die Compiled List
     * @param gl Momentaner OpenGL Kontext
     */
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

    /**
     * Gibt die Liste der Flaechen zurueck
     * 
     * @return Liste der Flaechen 
     */
    public List<Face> getFacesList() {
        return listOfAllFaces;
    }
    
    /**
     * Gibt den Listindex als Namen zurueck
     * 
     * @return listIndex Name 
     */
    public Integer getName() {
        return listIndex;
    }
}
