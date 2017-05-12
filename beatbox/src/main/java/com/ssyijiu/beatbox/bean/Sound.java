package com.ssyijiu.beatbox.bean;

/**
 * Created by ssyijiu on 2017/5/12.
 * Github: ssyijiu
 * E-mail: lxmyijiu@163.com
 */

public class Sound {
    private String assetPath;
    private String name;


    public Sound(String assetPath) {
        this.assetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        name = filename.replace(".wav", "");
    }


    public String getAssetPath() {
        return assetPath;
    }


    public String getName() {
        return name;
    }
}
