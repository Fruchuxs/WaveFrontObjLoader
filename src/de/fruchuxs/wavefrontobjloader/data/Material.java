package de.fruchuxs.wavefrontobjloader.data;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL2;
import javax.media.opengl.GLException;

/**
 * TODO: maps in Arrays zusammenfassen ..
 * Repraesentiert ein Material
 */
public class Material {
    private String name;
    
    /**
     * Ambiente Textur
     */
    private File map_Ka;
    
    /**
     * Diffuse Textur
     */
    private File map_Kd;
    
    /**
     * Spekulare Textur
     */
    private File map_Ks;
    
    /**
     * Alpha Textur
     */
    private File map_d;
    
    /**
     * Transparenz
     */
    private Float d = 1f;
    
    /**
     * Ambiente Farbe
     */
    private Float[] ka;
    
    /**
     * Diffuse Farbe
     */
    private Float[] kd;
    
    /**
     * Spekulare Farbe
     */
    private Float[] ks;
    
    private Float ns;
    
    private Texture tex_ka;
    private Texture tex_kd;
    private Texture tex_ks;
    
    public void loadTextures() {
        try {
            if(map_Ka != null) {
                tex_ka = TextureIO.newTexture(map_Ka, true);
            }
            if(map_Kd != null) {
                tex_kd = TextureIO.newTexture(map_Kd, true);
            }
            if(map_Ks != null) {
                tex_ks = TextureIO.newTexture(map_Ks, true);
            }
        } catch (IOException | GLException ex) {
            Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enableTextures(GL2 gl) {
        if(tex_ka != null) {
            tex_ka.enable(gl);
            tex_ka.bind(gl);
        }
        
        if(tex_kd != null) {
            tex_kd.enable(gl);
            tex_kd.bind(gl);
        }
        
        if(tex_ks != null) {
            tex_ks.enable(gl);
            tex_ks.bind(gl);
        }
    }
    
    public void disableTextures(GL2 gl) {
        if(tex_ka != null) {
            tex_ka.disable(gl);
        }
        
        if(tex_kd != null) {
            tex_kd.disable(gl);
        }
        
        if(tex_ks != null) {
            tex_ks.disable(gl);
        }
    }
    
    public boolean isSpecular() {
        return ks != null;
    }
    
    public boolean isAmbient() {
        return ka != null;
    }
    
    public boolean isDiffuse() {
        return kd != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getMap_Ka() {
        return map_Ka;
    }

    public void setMap_Ka(File map_Ka) {
        this.map_Ka = map_Ka;
    }

    public File getMap_Kd() {
        return map_Kd;
    }

    public void setMap_Kd(File map_Kd) {
        this.map_Kd = map_Kd;
    }

    public File getMap_Ks() {
        return map_Ks;
    }

    public void setMap_Ks(File map_Ks) {
        this.map_Ks = map_Ks;
    }

    public File getMap_d() {
        return map_d;
    }

    public void setMap_d(File map_d) {
        this.map_d = map_d;
    }

    public Float getD() {
        return d;
    }

    public void setD(Float d) {
        this.d = d;
    }

    public Float[] getKa() {
        return ka;
    }

    public void setKa(Float[] ka) {
        this.ka = ka;
    }

    public Float[] getKd() {
        return kd;
    }

    public void setKd(Float[] kd) {
        this.kd = kd;
    }

    public Float[] getKs() {
        return ks;
    }

    public void setKs(Float[] ks) {
        this.ks = ks;
    }

    public Float getNs() {
        return ns;
    }

    public void setNs(Float ns) {
        this.ns = ns;
    }
}
