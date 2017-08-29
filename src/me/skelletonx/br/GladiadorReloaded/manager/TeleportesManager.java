package me.skelletonx.br.GladiadorReloaded.manager;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.skelletonx.br.GladiadorReloaded.Gladiador;

public class TeleportesManager {

    private Gladiador hg = Gladiador.getGladiador();
    private final FileConfiguration config = hg.getConfig();
    
    public Location getTeleportEntrada(){
       return new Location(hg.getServer().getWorld(config.getString("Localizacoes.Spawn_Entrada.World")), config.getDouble("Localizacoes.Spawn_Entrada.X"), config.getDouble("Localizacoes.Spawn_Entrada.Y"), config.getDouble("Localizacoes.Spawn_Entrada.Z"));
    }
    
    public Location getTeleportSaida(){
       return new Location(hg.getServer().getWorld(config.getString("Localizacoes.Spawn_Saida.World")), config.getDouble("Localizacoes.Spawn_Saida.X"), config.getDouble("Localizacoes.Spawn_Saida.Y"), config.getDouble("Localizacoes.Spawn_Saida.Z"));
    }
    
    public Location getTeleportCamarote(){
       return new Location(hg.getServer().getWorld(config.getString("Localizacoes.Spawn_Camarote.World")), config.getDouble("Localizacoes.Spawn_Camarote.X"), config.getDouble("Localizacoes.Spawn_Camarote.Y"), config.getDouble("Localizacoes.Spawn_Camarote.Z"));
    }
    
    public void setLocationEntrada(Player p){
        config.set("Localizacoes.Spawn_Entrada.World", p.getLocation().getWorld().getName());
        config.set("Localizacoes.Spawn_Entrada.X", p.getLocation().getX());
        config.set("Localizacoes.Spawn_Entrada.Y", p.getLocation().getY());
        config.set("Localizacoes.Spawn_Entrada.Z", p.getLocation().getZ());
        hg.saveConfig();
    }
    
    public void setLocationSaida(Player p){
        config.set("Localizacoes.Spawn_Saida.World", p.getLocation().getWorld().getName());
        config.set("Localizacoes.Spawn_Saida.X", p.getLocation().getX());
        config.set("Localizacoes.Spawn_Saida.Y", p.getLocation().getY());
        config.set("Localizacoes.Spawn_Saida.Z", p.getLocation().getZ());
        hg.saveConfig();
    }
    
    public void setLocationCamarote(Player p){
        config.set("Localizacoes.Spawn_Camarote.World", p.getLocation().getWorld().getName());
        config.set("Localizacoes.Spawn_Camarote.X", p.getLocation().getX());
        config.set("Localizacoes.Spawn_Camarote.Y", p.getLocation().getY());
        config.set("Localizacoes.Spawn_Camarote.Z", p.getLocation().getZ());
        hg.saveConfig();
    }
}
