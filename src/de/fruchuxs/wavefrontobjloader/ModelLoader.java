/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fruchuxs.wavefrontobjloader;

import de.fruchuxs.wavefrontobjloader.data.Face;
import de.fruchuxs.wavefrontobjloader.data.ModelData;
import de.fruchuxs.wavefrontobjloader.data.FaceData;
import de.fruchuxs.wavefrontobjloader.data.Model;
import de.fruchuxs.wavefrontobjloader.data.FaceGroup;
import de.fruchuxs.wavefrontobjloader.data.Material;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bietet Factory Methoden an, um ein Model aus einer Wavefront Obj Datei zu
 * erstellen und gibt ein Model zurueck.
 *
 * Der ModelLoader parst dabei erst einmal die ganze Datei und verpackt alles in
 * ein ModelData Objekt.
 *
 * Anschliesend werden Face-Objekte generiert, auf Basis des ModelData (welches
 * Intern eine Liste von FaceData-Objekten enthaelt, in diesen FaceData-Objekten
 * sind die "f 1/2/3 5/17/9 ..) Werte als Integer-Arrays gespeichert) Objektes.
 *
 * Hier werden die f Anweisungen interpretiert und den Faces entsprechend die vn
 * (vertex normals coords), vt (vertex texture coords) und v (vertexes) aus dem
 * ModelData zugewiesen auf Basis der FaceData.
 *
 * Die Face-Objekte werden zusammen mit Ihren Material in ein FaceGroup Objekt
 * zusammengepackt. TODO: Austauschen gegen HashMap?
 *
 * @author Fruchuxs
 */
public class ModelLoader {

    private static ModelLoader instance = null;
    private static Integer modelCounter = 0;

    private ModelLoader() {

    }

    private static ModelLoader getInstance() {
        if (instance == null) {
            instance = new ModelLoader();
        }

        return instance;
    }

    /**
     * Pfad zur Datei, schmeisst eine FileNotFoundException wenn die Datei nicht
     * existiert.
     *
     * @param pModelToLoad Pfad zum Model welches geladen werden soll
     * @return Ein konkretes Model-Objekt
     * @throws FileNotFoundException Wenn die Datei nicht gefunden wurde.
     */
    public static Model modelFactory(final Path pModelToLoad) throws FileNotFoundException {
        if (!pModelToLoad.toFile().exists()) {
            throw new FileNotFoundException(pModelToLoad.toString());
        }

        return getInstance().createModel(pModelToLoad);
    }

    /**
     * Pfad zur Datei, schmeisst eine FileNotFoundException wenn die Datei nicht
     * existiert.
     *
     * @param pModelPath Pfad zur Datei.
     * @return Ein konkretes Model-Objekt
     * @throws FileNotFoundException
     */
    public static Model modelFactory(final String pModelPath) throws FileNotFoundException {
        return modelFactory(FileSystems.getDefault().getPath(pModelPath));
    }

    private Model createModel(Path pModelPath) {
        ModelData data = parseObjectFile(pModelPath);
        List<FaceGroup> faces = new ArrayList<>();
        FaceGroup fg = null;

        for (int i = 0; i < data.getFacesCount(); i++) {
            // Generiere die Faces und packe sie zusammen mit einer Textur
            // in FaceGroups
            String mtlName = data.getFaceDataAt(i).getMtlName();
            if (fg == null) {
                fg = new FaceGroup(data.getMaterial(mtlName));
            }

            fg.addFace(createFace(data, i));

            if (!fg.getFacesMaterial().getName().equals(mtlName)) {
                faces.add(fg);
                fg = null;
            }
        }

        if (fg != null) {
            faces.add(fg);
        }

        return new Model(faces, ++ModelLoader.modelCounter);
    }

    private Face createFace(ModelData pData, Integer currentIndex) {
        FaceData fd = pData.getFaceDataAt(currentIndex);
        Face faceToAdd = new Face();

        for (int j = 0; j < fd.getFaceDataCount(); j++) {

            faceToAdd.addNormalCoords(pData.getNormalCoordsAt(fd.getNormalNumberAt(j) - 1));
            faceToAdd.addTextureCoords(pData.getTextureCoordsAt(fd.getTextureCoordNumberAt(j) - 1));
            faceToAdd.addVertexCoords(pData.getVertexCoordsAt(fd.getVertexNumberAt(j) - 1));
        }

        return faceToAdd;
    }

    private ModelData parseObjectFile(Path pModelPath) {
        ModelData data = null;

        try (BufferedReader fileReader = Files.newBufferedReader(pModelPath, StandardCharsets.UTF_8)) {
            String line, command, usedMtl = "";
            List<String> values;
            data = new ModelData();

            /**
             * Todo: use command pattern? :/
             */
            while ((line = fileReader.readLine()) != null) {
                values = ParseUtils.seperateValuesAtWhiteSpaces(line);
                command = values.get(0);
                values = values.subList(1, values.size());

                switch (command.toLowerCase()) {
                    case "v": {
                        data.addVertexCoords(ParseUtils.parseAsFloat(values));
                        break;
                    }
                    case "vt": {
                        data.addTextureCoords(ParseUtils.parseAsFloat(values));
                        break;
                    }
                    case "vn": {
                        data.addNormalCoords(ParseUtils.parseAsFloat(values));
                        break;
                    }
                    case "f": {
                        data.addFaceData(parseFace(values, usedMtl));
                        break;
                    }
                    case "mtllib": {
                        List<Material> materials = MaterialLoader.materialFactory(
                                pModelPath.getParent().resolve(values.get(0))
                        );
                        
                        for (Material m : materials) {
                            data.addMaterial(m);
                        }
                        break;
                    }
                    case "usemtl": {
                        //concat all values
                        usedMtl = ParseUtils.concatStringByList(values);
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ModelLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }

    private FaceData parseFace(List<String> values, String pUsedMtl) {
        FaceData faceData = new FaceData();
        List<String> faces_seperated;

        for (String faceValue : values) {
            // v/vt/vn
            Integer[] facesValuesToAdd = new Integer[3];

            // Zerteile bei Slash
            faces_seperated = ParseUtils.seperateValues(faceValue, "/");

            // Packe in Integer Array!
            for (int i = 0; i < faces_seperated.size(); i++) {
                if (!faces_seperated.get(i).isEmpty()) {
                    facesValuesToAdd[i] = ParseUtils.parseAsInteger(faces_seperated.get(i));
                } else {
                    facesValuesToAdd[i] = null;
                }
            }

            faceData.addDataArray(facesValuesToAdd);
        }

        // set usedmtl name
        if (!pUsedMtl.isEmpty()) {
            faceData.setMtlName(pUsedMtl);
        }

        return faceData;
    }
}
