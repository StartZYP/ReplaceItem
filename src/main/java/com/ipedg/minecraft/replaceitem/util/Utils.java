package com.ipedg.minecraft.replaceitem.util;

import com.comphenix.protocol.utility.StreamSerializer;
import com.ipedg.minecraft.replaceitem.Entity.MapItemEntity;
import com.ipedg.minecraft.replaceitem.main;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static ItemStack getItemStack(String data)
    {
        StreamSerializer ss=new StreamSerializer();
        try
        {
            return ss.deserializeItemStack(data);
        }catch(Exception e){e.printStackTrace();}
        return null;
    }
    public static String toData(ItemStack item)
    {
        StreamSerializer ss=new StreamSerializer();
        try
        {
            return ss.serializeItemStack(item);
        }catch(Exception e){e.printStackTrace();}
        return null;
    }



    public static void CreateItem(String ItemName,String Data){
        File file = new File(main.plugin.getDataFolder() + File.separator + ItemName+".fuck");
        if (!file.exists()){
            try{
                file.createNewFile();
                FileWriter fileWritter = new FileWriter(file,true);
                fileWritter.write(Data);
                fileWritter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }




    public static String ReadFile(String ItemPath){
        File file = new File(ItemPath);
        try{
            FileInputStream fis=new FileInputStream(file);
            byte[] b=new byte[fis.available()];//新建一个字节数组
            fis.read(b);//将文件中的内容读取到字节数组中
            fis.close();
            return new String(b);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public static void InitItem(Set<File> files){
        main.itemmap.clear();
        for (File f :files){
            String keyvalue = ReadFile(f.getAbsolutePath());
            String[] startzyps = keyvalue.split("startzyp");
            main.itemmap.put(f.getName(),new MapItemEntity(startzyps[0],startzyps[1]));
        }
    }

    public static Set<File> getFiles(){
        File file = main.plugin.getDataFolder();
        Set<File> tmpfile = new HashSet<>();
        File[] files = file.listFiles();
        for (File filepath:files){
            if(!file.isDirectory()&&filepath.getAbsolutePath().toLowerCase().endsWith(".fuck")){
                tmpfile.add(filepath);
            }
        }
        return tmpfile;
    }
}
