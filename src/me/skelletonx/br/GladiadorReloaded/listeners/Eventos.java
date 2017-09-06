package me.skelletonx.br.GladiadorReloaded.listeners;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import me.skelletonx.br.GladiadorReloaded.Gladiador;
import me.skelletonx.br.GladiadorReloaded.VariaveisGlobais;
import me.skelletonx.br.GladiadorReloaded.manager.TeamManager;
import me.skelletonx.br.GladiadorReloaded.manager.TeleportesManager;
import net.sacredlabyrinth.phaed.simpleclans.Clan;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Eventos implements Listener{
    
    private Gladiador hg = Gladiador.getGladiador();
    private final FileConfiguration config = hg.getConfig();
    private TeleportesManager tm = new TeleportesManager();
    private VariaveisGlobais vg = hg.vg;
    private TeamManager teamManager = new TeamManager();
    
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        if(e.getPlayer().hasPermission("Gladiador.staff")){
            if(vg.isOcorrendo){
                e.getPlayer().sendMessage("§4[Gladiador] §cO evento gladiador esta ocorrendo no momento!");
            }
        }
        if(config.getBoolean("Clan_Tag.Ativar_Tab_Tag")){
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
    }
    
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){
        if(vg.todosParticipantes.contains(e.getPlayer())){
            vg.todosParticipantes.remove(e.getPlayer());
            String tag = hg.core.getClanManager().getClanByPlayerName(e.getPlayer().getName()).getColorTag();
            vg.clans.put(tag, vg.clans.get(tag) - 1);
            if(vg.clans.get(tag) == 0){
                vg.clans.remove(tag);
            }
            e.getPlayer().teleport(tm.getTeleportSaida());
            if(config.getBoolean("Gladiador.Ativar_Mensagens_De_DC")){
                hg.getServer().broadcastMessage(config.getString("Mensagens_Player.DC").replace("&", "§").replace("<player>", e.getPlayer().getName()));
            }
        }
    }
    
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e){
        if(e.getEntity().getKiller() instanceof Player){
        	if(e.getEntity().getPlayer() instanceof Player){
	            Player matou = e.getEntity().getKiller();
	            Player morreu = e.getEntity().getPlayer();
	            if(morreu.getName().equalsIgnoreCase(config.getString("Mito_Tag.Jogador_Com_A_Tag_Atual"))){
	                config.set("Mito_Tag.Jogador_Com_A_Tag_Atual", matou.getName());
	                hg.saveConfig();
	            }
	            
	            if(vg.isOcorrendo){
		            if(vg.todosParticipantes.contains(e.getEntity().getPlayer())){
		                vg.todosParticipantes.remove(e.getEntity().getPlayer());
		                String tag = hg.core.getClanManager().getClanByPlayerName(e.getEntity().getPlayer().getName()).getColorTag();
		                vg.clans.put(tag, vg.clans.get(tag) - 1);
		                if(vg.clans.get(tag) == 0){
		                    vg.clans.remove(tag);
		                }
		                e.getEntity().getPlayer().teleport(tm.getTeleportSaida());
		                if(config.getBoolean("Gladiador.Ativar_Mensagens_De_Morte")){
		                    hg.getServer().broadcastMessage(config.getString("Mensagens_Player.Morte").replace("&", "§").replace("<player>", e.getEntity().getPlayer().getName()).replace("<killer>", e.getEntity().getKiller().getName()));
		                }
		            }
	            }
	        }
        }else{
        	if(vg.isOcorrendo){
	        	if(vg.todosParticipantes.contains(e.getEntity().getPlayer())){
	        		String tag = hg.core.getClanManager().getClanByPlayerName(e.getEntity().getPlayer().getName()).getColorTag();
	                if(vg.clans.get(tag) == 0){
	                    vg.clans.remove(tag);
	                }
	                e.getEntity().getPlayer().teleport(tm.getTeleportSaida());
	            }
        	}
        }
    }
    
    @EventHandler
    public void onChatMessageEvent(ChatMessageEvent e){
        if(vg.isMitoEnable){
            if(e.getTags().contains("mito")){
                if(e.getSender().getName().equalsIgnoreCase(config.getString("Mito_Tag.Jogador_Com_A_Tag_Atual"))){
                    e.setTagValue("mito", config.getString("Mito_Tag.Tag").replace("&", "§"));
                }
            }
        }
        if(vg.isGladiadorEnable){
            if(e.getTags().contains("gladiador")){
                for(String s : config.getStringList("Gladiador_Tag.Jogador_Com_A_Tag_Atual")){
                    if(e.getSender().getName().equalsIgnoreCase(s)){
                        e.setTagValue("gladiador", config.getString("Gladiador_Tag.Tag").replace("&", "§"));
                    }
                }
            }
        }
    }
    
    @EventHandler
    private void onComand(PlayerCommandPreprocessEvent e){
        String command = e.getMessage().toLowerCase();
        if(vg.todosParticipantes.contains(e.getPlayer())){
            for(String bloqueados : config.getStringList("Comandos_Bloqueados")){
                if(command.startsWith(bloqueados)){
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(config.getString("Mensagens_Player.Comando_Bloqueado"));
                }
            }
        }
    }
    
}