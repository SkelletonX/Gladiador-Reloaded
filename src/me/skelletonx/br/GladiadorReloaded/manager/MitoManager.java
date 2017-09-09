package me.skelletonx.br.GladiadorReloaded.manager;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;


import me.skelletonx.br.GladiadorReloaded.Gladiador;

public class MitoManager {
	
	public static Gladiador hg = Gladiador.getGladiador();
    private static final FileConfiguration config = hg.getConfig();
	
	public static void SetMito(Player pMito) {
		Location loc = pMito.getLocation();
		config.set("Mito_Tag.Jogador_Com_A_Tag_Atual", pMito.getName());
		hg.saveConfig();
		Bukkit.broadcastMessage("§5[MITO] §c "+ pMito.getName() +" é o novo MITO do PVP!");
		loc.getWorld().strikeLightningEffect(pMito.getLocation().add(2, 0, 0));
		loc.getWorld().strikeLightningEffect(pMito.getLocation().add(-2, 0, 0));
		loc.getWorld().strikeLightningEffect(pMito.getLocation().add(0, 0, 2));
		loc.getWorld().strikeLightningEffect(pMito.getLocation().add(0, 0, -2));
		loc.getWorld().strikeLightningEffect(pMito.getLocation().add(-2, 0, 2));
		loc.getWorld().strikeLightningEffect(pMito.getLocation().add(2, 0, -2));
		HashSet<Bat> spawnedBats = new HashSet<>();
		for (int i = 0; i < 5; i++)
		{
			for(Player players : hg.getServer().getOnlinePlayers()) {
			Bat bat = pMito.getWorld().spawn(loc.add(players.getLocation()), Bat.class);
			bat.setCustomNameVisible(true);
			bat.setCustomName("§5[Mito] §cParabens " + pMito.getName());
			spawnedBats.add(bat);
			players.playSound(players.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1, 1);
			}
		}
	}
}
