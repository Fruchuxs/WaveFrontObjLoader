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

/**
 *
 * @author FloH
 */
public class ModelData {
    private List<Float[]> vertexCoordList;
    private List<Float[]> normalCoordList;
    private List<Float[]> textureCoordList;
    
    /**
     * Liste mit den Nummern der vertex/normal/textureCoord
     * Daten
     */
    private List<FaceData> facesList; 
    private Map<String, Material> mtlList;
    
    public ModelData() {
        vertexCoordList = new ArrayList<>();
        normalCoordList = new ArrayList<>();
        textureCoordList = new ArrayList<>();
        facesList = new ArrayList<>();
        mtlList = new HashMap<>();
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
        return vertexCoordList.add(e);
    }
    
    public Float[] getVertexCoordsAt(Integer pPos) {
        return vertexCoordList.get(pPos);
    }
    
    public Float[] getNormalCoordsAt(Integer pPos) {
        return vertexCoordList.get(pPos);
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
