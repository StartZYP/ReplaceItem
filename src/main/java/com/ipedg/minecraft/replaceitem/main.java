package com.ipedg.minecraft.replaceitem;


import com.ipedg.minecraft.replaceitem.Entity.MapItemEntity;
import com.ipedg.minecraft.replaceitem.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class main extends JavaPlugin implements Listener {
    public static Map<String, MapItemEntity> itemmap = new HashMap<>();
    public static String cmdmsg;
    public static Plugin plugin;
    public static MapItemEntity tmpitem =new MapItemEntity();

    @Override
    public void onEnable() {
        plugin = this;
        File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
        }
        saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
        super.onEnable();
    }


    private void ConfigReloads(){
        reloadConfig();
        cmdmsg = getConfig().getString("cmdmsg");
        Set<File> files = Utils.getFiles();
        Utils.InitItem(files);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            return false;
        }
        Player p = (Player) sender;
        if (args.length==0){
            sender.sendMessage(cmdmsg);
        }else if (args.length==1){
            if (args[0].equals("reload")){
                ConfigReloads();
                sender.sendMessage("重载成功");
            }else if (args[0].equalsIgnoreCase("list")){
                Set<String> strings = itemmap.keySet();
                for (String keys:strings){
                    p.sendMessage(keys);
                }
            }
        }else if (args.length==2){
            if (args[0].equalsIgnoreCase("get")){
                if (itemmap.containsKey(args[1])){
                    MapItemEntity mapItemEntity = itemmap.get(args[0]);
                    String keyItem = mapItemEntity.getKeyItem();
                    String valueItem = mapItemEntity.getValueItem();
                    ItemStack itemStack = Utils.getItemStack(keyItem);
                    ItemStack itemStack1 = Utils.getItemStack(valueItem);
                    p.getInventory().addItem(itemStack);
                    p.getInventory().addItem(itemStack1);
                }else {
                    sender.sendMessage("抱歉没有这个");
                }
            }

        }else if (args.length==3){
            if (args[0].equals("add")&&args[1].equals("key")){
                ItemStack itemInHand = p.getItemInHand();
                if (itemInHand==null){
                    p.sendMessage("不能空手");
                    return false;
                }
                if (itemInHand.getType()== Material.AIR){
                    p.sendMessage("不能空手");
                    return false;
                }
                if (itemInHand.getType()== Material.AIR){
                    return false;
                }
                if (itemInHand.getAmount()!=1){
                    p.sendMessage("数量必须为1");
                    return false;
                }
                tmpitem.setKeyItem(Utils.toData(itemInHand));
                p.sendMessage("配置key成功");
            }else if (args[0].equals("add")&&args[1].equals("value")){
                ItemStack itemInHand = p.getItemInHand();
                if (itemInHand==null){
                    p.sendMessage("不能空手");
                    return false;
                }
                if (itemInHand.getType()== Material.AIR){
                    p.sendMessage("不能空手");
                    return false;
                }
                if (itemInHand.getAmount()!=1){
                    p.sendMessage("数量必须为1");
                    return false;
                }
                if (tmpitem.getKeyItem().equals("")){
                    p.sendMessage("您还没设置key");
                    return false;
                }
                tmpitem.setValueItem(Utils.toData(itemInHand));
                itemmap.put(args[2],tmpitem);
                Utils.CreateItem(args[2],tmpitem.getKeyItem()+"startzyp"+tmpitem.getValueItem());
                tmpitem = new MapItemEntity();
                p.sendMessage("配置value成功-已存储为"+args[2]);
            }
        }
        return super.onCommand(sender, command, label, args);
    }

    private MapItemEntity CheackItem(String ItemData){
        Set<String> strings = itemmap.keySet();
        for (String a:strings){
            MapItemEntity mapItemEntity = itemmap.get(a);
            if (mapItemEntity.getKeyItem().equals(ItemData)){
                return mapItemEntity;
            }
        }
        return null;
    }

    @EventHandler
    public void PlayerClickInventory(InventoryClickEvent event){
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem!=null&&currentItem.getType()!= Material.AIR){
            int amount = currentItem.getAmount();
            currentItem.setAmount(1);
            String data = Utils.toData(currentItem);
            MapItemEntity mapItemEntity = CheackItem(data);
            if (mapItemEntity!=null){
                event.setCancelled(true);
                String keyItem = mapItemEntity.getKeyItem();
                if (data.equals(keyItem)){
                    ItemStack itemStack = Utils.getItemStack(mapItemEntity.getValueItem());
                    itemStack.setAmount(amount);
                    event.setCurrentItem(itemStack);
                }
            }
        }
    }

    @EventHandler
    public void PlayerPickItem(PlayerPickupItemEvent event){
        ItemStack itemStack = event.getItem().getItemStack();
        if (itemStack!=null&&itemStack.getType()!= Material.AIR){
            int amount = itemStack.getAmount();
            itemStack.setAmount(1);
            String data = Utils.toData(itemStack);
            MapItemEntity mapItemEntity = CheackItem(data);
            if (mapItemEntity!=null){
                event.setCancelled(true);
                String keyItem = mapItemEntity.getKeyItem();
                if (data.equals(keyItem)){
                    ItemStack itemStack1 = Utils.getItemStack(mapItemEntity.getValueItem());
                    itemStack1.setAmount(amount);
                    event.getItem().setItemStack(itemStack1);
                }
            }
        }
    }

//    @EventHandler
//    public void PlayerChangeItem(InventoryOpenEvent event){
//        Inventory inventory = event.getInventory();
//        int size = inventory.getSize()-1;
//        for (int a=0;a<=size;a++){
//
//        }
//
//    }
}
