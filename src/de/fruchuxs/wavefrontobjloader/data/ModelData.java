package de.fruchuxs.wavefrontobjloader.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hier werden die geparsten Daten gesammelt
 */
public class ModelData {
    /**
     * Liste mit den verticles
     */
    private List<Float[]> vertexCoordList;
    
    /**
     * Liste mit den Normalen
     */
    private List<Float[]> normalCoordList;
    
    /**
     * Liste mit den Texture Koordinaten
     */
    private List<Float[]> textureCoordList;

    /**
     * Liste mit den Nummern der vertex/normal/textureCoord
     * Daten
     */
    private List<FaceData> facesList; 
    
    /**
     * Materialliste
     */
    private Map<String, Material> mtlList;
    
    /**
     * Extrempunkte
     */
    private Map<String, Float> extremPoints;
    
    public ModelData() {
        vertexCoordList = new ArrayList<>();
        normalCoordList = new ArrayList<>();
        textureCoordList = new ArrayList<>();
        facesList = new ArrayList<>();
        mtlList = new HashMap<>();
        extremPoints = new HashMap<>();
        
        extremPoints.put("top", 0f);
        extremPoints.put("bottom", 0f);
        extremPoints.put("right", 0f);
        extremPoints.put("left", 0f);
        extremPoints.put("near", 0f);
        extremPoints.put("far", 0f);
    }

    public Map<String, Float> getExtremPoints() {
        return extremPoints;
    }

    public void setExtremPoints(Map<String, Float> extremPoints) {
        this.extremPoints = extremPoints;
    }

    public boolean addFaceData(FaceData e) {
        return facesList.add(e);
    }

    public boolean addMaterial(Material e) {
        return mtlList.put(e.getName(), e) != null; 
    }

    public boolean addNormalCoords(Float[] e) {
        return normalCoordList.add(e);
    }

    public boolean addTextureCoords(Float[] e) {
        return textureCoordList.add(e);
    }
    
    public boolean addVertexCoords(Float[] e) {
        interpretPoints(e);
        return vertexCoordList.add(e);
    }
    
    private void interpretPoints(Float[] e) {
        if(e[0] > extremPoints.get("right")) {
            extremPoints.put("right", e[0]);
        } else {
            extremPoints.put("left", e[0]);
        }
        
        if(e[1] > extremPoints.get("top")) {
            extremPoints.put("top", e[0]);
        } else {
            extremPoints.put("bottom", e[0]);
        }
        
        if(e[2] > extremPoints.get("near")) {
            extremPoints.put("near", e[0]);
        } else {
            extremPoints.put("far", e[0]);
        }
    }
    
    public Float[] getVertexCoordsAt(Integer pPos) {
        return vertexCoordList.get(pPos);
    }
    
    public Float[] getNormalCoordsAt(Integer pPos) {
        return normalCoordList.get(pPos);
    }
    
    public Float[] getTextureCoordsAt(Integer pPos) {
        return textureCoordList.get(pPos);
    }
    
    public FaceData getFaceDataAt(Integer pPos) {
        return facesList.get(pPos);
    }
    
    public Material getMaterial(String pMtlName) {
        return mtlList.get(pMtlName);
    }
    
    public Integer getVertexCount() {
        return vertexCoordList.size();
    }
    
    public Integer getNormalCount() {
        return normalCoordList.size();
    }
    
    public Integer getTextureCoordsCount() {
        return textureCoordList.size();
    }
    
    public Integer getFacesCount() {
        return facesList.size();
    }
}
