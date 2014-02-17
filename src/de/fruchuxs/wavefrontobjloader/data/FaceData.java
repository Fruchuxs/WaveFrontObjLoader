package de.fruchuxs.wavefrontobjloader.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Datenhaltungsklasse fuer Flaechen Daten
 */
public class FaceData {
    /**
     * Liste der Daten fuer die spaetere Flaeche
     * 0 => vertex Nummer
     * 1 => texture Coordinate Nummer, kann null sein
     * 2 => normalen Nummer, kann null sein
     */
    private List<Integer[]> faceData;
    private String mtlName;
    
    public FaceData() {
        faceData = new ArrayList<>();
    }
    
    public void addDataArray(Integer[] pData) {
        if(pData != null && pData[0] != null) {
            faceData.add(pData);
        }
    }
    
    public Integer[] getDataAt(Integer pPos) {  
        return faceData.get(pPos);
    }
    
    public Integer getVertexNumberAt(Integer pPos) {
        return getDataAt(pPos)[0];
    }
    
    public Integer getTextureCoordNumberAt(Integer pPos) {
        return getDataAt(pPos)[1];
    }
    
    public Integer getNormalNumberAt(Integer pPos) {
        return getDataAt(pPos)[2];
    }
    
    public boolean hasMtl() {
        return mtlName != null;
    }
    
    public String getMtlName() {
        return mtlName;
    }
    
    public void setMtlName(String pMtlName) {
        mtlName = pMtlName;
    }
    
    public Integer getFaceDataCount() {
        return faceData.size();
    }
}
