/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fruchuxs.wavefrontobjloader;

import de.fruchuxs.wavefrontobjloader.data.ModelData;
import de.fruchuxs.wavefrontobjloader.data.Material;
import java.io.BufferedReader;
import java.io.File;
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
 *
 * @author FloH
 */
public class MaterialLoader {

    private static MaterialLoader instance = null;
    private ModelData modelData;

    private MaterialLoader() {

    }

    private static MaterialLoader getInstance() {
        if (instance == null) {
            instance = new MaterialLoader();
        }

        return instance;
    }

    public static List<Material> materialFactory(final Path pMaterialPath) throws FileNotFoundException {
        if (!pMaterialPath.toFile().exists()) {
            throw new FileNotFoundException(pMaterialPath.toString());
        }
        return getInstance().parseMtlFile(pMaterialPath);
    }

    public static List<Material> materialFactory(final String pMaterialPath) throws FileNotFoundException {
        return materialFactory(FileSystems.getDefault().getPath(pMaterialPath));
    }

    private List<Material> parseMtlFile(Path pMaterialPath) {
        List<Material> result = new ArrayList<>();
        Material currentMtl = null;

        try (BufferedReader fileReader = Files.newBufferedReader(pMaterialPath, StandardCharsets.UTF_8)) {
            String line, command;
            List<String> values;

            while ((line = fileReader.readLine()) != null) {
                values = ParseUtils.seperateValuesAtWhiteSpaces(line);
                command = values.get(0);
                values = values.subList(1, values.size());

                switch (command.toLowerCase()) {
                    case "newmtl": {
                        if (currentMtl != null) {
                            result.add(currentMtl);
                        }

                        currentMtl = new Material();
                        currentMtl.setName(ParseUtils.concatStringByList(values));
                        break;
                    }
                    case "ns": {
                        currentMtl.setNs(ParseUtils.parseAsFloat(
                                ParseUtils.concatStringByList(values)
                        ));
                        break;
                    }
                    case "ka": {
                        currentMtl.setKa(ParseUtils.parseAsFloat(values));
                        break;
                    }
                    case "kd": {
                        currentMtl.setKd(ParseUtils.parseAsFloat(values));
                        break;
                    }
                    case "ks": {
                        currentMtl.setKs(ParseUtils.parseAsFloat(values));
                        break;
                    }
                    case "ni": {
                        break;
                    }
                    case "d": {
                        break;
                    }
                    case "illum": {
                        break;
                    }
                    case "map_kd": {
                        currentMtl.setMap_Kd(
                                getTextureFile(ParseUtils.concatStringByList(values), pMaterialPath)
                        );
                        break;
                    }
                    case "map_ka": {
                        currentMtl.setMap_Ka(
                                getTextureFile(ParseUtils.concatStringByList(values), pMaterialPath)
                        );
                        break;
                    }
                    case "map_ks": {
                        currentMtl.setMap_Ks(
                                getTextureFile(ParseUtils.concatStringByList(values), pMaterialPath)
                        );
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MaterialLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (currentMtl != null) {
            result.add(currentMtl);
        }

        return result;
    }

    private File getTextureFile(String pFileName, Path pMaterialPath) {
        File textureFile = new File(pFileName);

        if (!textureFile.exists()) {
            textureFile = pMaterialPath.getParent().resolve(pFileName).toFile();
        }

        return textureFile;
    }
}
