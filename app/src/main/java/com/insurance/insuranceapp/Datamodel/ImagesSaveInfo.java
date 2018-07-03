package com.insurance.insuranceapp.Datamodel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "ImagesSaveInfo")
public class ImagesSaveInfo extends Model {
    @Column(name = "imageID")
    private String imageID;
    @Column(name = "imagesPath")
    private String imagesPath;
    @Column(name = "blockName")
    private String blockName;
    @Column(name = "cliamNumber")
    private String cliamNumber;


    public String getCliamNumber() {
        return cliamNumber;
    }

    public void setCliamNumber(String cliamNumber) {
        this.cliamNumber = cliamNumber;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public static List<ImagesSaveInfo> getimages(){

        return new Select(new String[]{"Id,imagesPath"})
                .from(ImagesSaveInfo.class)
                .execute();

    }
    public static void getdeleteimages( ){

        new Delete().from(ImagesSaveInfo.class).execute();
    }
}
