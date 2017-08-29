package me.skelletonx.br.GladiadorReloaded;

import net.sacredlabyrinth.phaed.simpleclans.Clan;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.skelletonx.br.GladiadorReloaded.manager.TeamManager;

public class TabTag {
    
    private Gladiador hg = Gladiador.getGladiador();
    private final FileConfiguration config = hg.getConfig();
    private TeamManager teamManager = new TeamManager();
    
    public void iniciarTabTagAndCustomNameTag(){
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(hg, new Runnable() {
            @Override
            public void run() {
                for(Player p : hg.getServer().getOnlinePlayers()){
                    Clan cp = hg.core.getClanManager().getClanByPlayerName(p.getName());
                    if(cp == null){
                        String sa = config.getString("Clan_Tag.Formato_Sem_Clan").replace("&", "§");
                        teamManager.criar(p.getName());
                        teamManager.addPrefixo(p.getName(), sa);
                    }else{
                        String sa = config.getString("Clan_Tag.Formato_Com_Clan").replace("{clantag}", cp.getColorTag());
                        teamManager.criar(p.getName());
                        teamManager.addPrefixo(p.getName(), sa);
                    }
                }
            }
        }, 0L, config.getInt("Clan_Tag.Tempo_Para_Atualizar") * 20L);
    }
}
