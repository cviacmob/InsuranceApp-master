package com.insurance.materialfilepicker.filter;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;

/**
 * Created by Balaji on 1/13/2018.
 */

public class HiddenFilter implements FileFilter, Serializable {

    @Override
    public boolean accept(File f) {
        return !f.isHidden();
    }
}
