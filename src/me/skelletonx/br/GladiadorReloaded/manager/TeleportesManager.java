package me.skelletonx.br.GladiadorReloaded.manager;

import java.util.AbstractMap;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.skelletonx.br.GladiadorReloaded.Gladiador;

public class TeleportesManager {

    private static Gladiador hg = Gladiador.getGladiador();
    private static final FileConfiguration config = hg.getConfig();
    public static HashMap<Locations, Location> cache = new HashMap<>();
    
    public static Location getTeleport(Locations l){
    	return cache.get(l);
    }
    
    public static void setLocationEntrada(Player p){
        config.set("Localizacoes.Spawn_Entrada.World", p.getLocation().getWorld().getName());
        config.set("Localizacoes.Spawn_Entrada.X", p.getLocation().getX());
        config.set("Localizacoes.Spawn_Entrada.Y", p.getLocation().getY());
        config.set("Localizacoes.Spawn_Entrada.Z", p.getLocation().getZ());
        hg.saveConfig();
        Locations.updateCache(cache, Locations.ENTRADA, new Location(hg.getServer().getWorld(config.getString("Localizacoes.Spawn_Entrada.World")), config.getDouble("Localizacoes.Spawn_Entrada.X"), config.getDouble("Localizacoes.Spawn_Entrada.Y"), config.getDouble("Localizacoes.Spawn_Entrada.Z")));
    }
    
    public static void setLocationSaida(Player p){
        config.set("Localizacoes.Spawn_Saida.World", p.getLocation().getWorld().getName());
        config.set("Localizacoes.Spawn_Saida.X", p.getLocation().getX());
        config.set("Localizacoes.Spawn_Saida.Y", p.getLocation().getY());
        config.set("Localizacoes.Spawn_Saida.Z", p.getLocation().getZ());
        hg.saveConfig();
        Locations.updateCache(cache, Locations.SAIDA, new Location(hg.getServer().getWorld(config.getString("Localizacoes.Spawn_Saida.World")), config.getDouble("Localizacoes.Spawn_Saida.X"), config.getDouble("Localizacoes.Spawn_Saida.Y"), config.getDouble("Localizacoes.Spawn_Saida.Z")));
    }
    
    public static void setLocationCamarote(Player p){
        config.set("Localizacoes.Spawn_Camarote.World", p.getLocation().getWorld().getName());
        config.set("Localizacoes.Spawn_Camarote.X", p.getLocation().getX());
        config.set("Localizacoes.Spawn_Camarote.Y", p.getLocation().getY());
        config.set("Localizacoes.Spawn_Camarote.Z", p.getLocation().getZ());
        hg.saveConfig();
        Locations.updateCache(cache, Locations.CAMAROTE, new Location(hg.getServer().getWorld(config.getString("Localizacoes.Spawn_Camarote.World")), config.getDouble("Localizacoes.Spawn_Camarote.X"), config.getDouble("Localizacoes.Spawn_Camarote.Y"), config.getDouble("Localizacoes.Spawn_Camarote.Z")));
    }
    
    public enum Locations{
    	ENTRADA,
    	SAIDA,
    	CAMAROTE;
    	
    	public static void updateCache(AbstractMap<Locations, Location> map, Locations l, Location loc){
    		if(map.containsKey(l))
    			map.replace(l, loc);
    		else
    			map.put(l, loc);
    	}
    }
}
