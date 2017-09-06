package me.skelletonx.br.GladiadorReloaded.manager;

import java.util.ArrayList;
import java.util.List;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.outlook.devleeo.LsMito.api.LsMito;

import me.skelletonx.br.GladiadorReloaded.Gladiador;
import me.skelletonx.br.GladiadorReloaded.VariaveisGlobais;

public class GladiadorManager {

    private Gladiador hg = Gladiador.getGladiador();
    private final FileConfiguration config = hg.getConfig();
    private TeleportesManager tm = new TeleportesManager();
    private VariaveisGlobais vg = hg.vg;
    public int id1, id2;
    public Clan vencedor;
    public List<Player> gladiadores = new ArrayList<>();

    public Player mito;

    public void iniciarAnuncios(){
        vg.isAberto = true;
        vg.isOcorrendo = true;
        vg.resetVariaveisSemIS();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        id1 = scheduler.scheduleSyncRepeatingTask(hg, new Runnable() {
            @Override
            public void run() {
                if(vg.quantMensagens >= 0){
                    for (String s : config.getStringList("Mensagens_Player.Anuncio_Aberto")) {
                        hg.getServer().broadcastMessage(s.replace("&", "§").replace("<preco>", String.valueOf(vg.precoParaParticipar)).replace("<limite>", String.valueOf(vg.limiteDeMembros)).replace("<players>", String.valueOf(vg.todosParticipantes.size())).replace("<clans>", String.valueOf(vg.clans.keySet().size())));
                    }
                    vg.quantMensagens -= 1;
                }else{
                    if(vg.clans.keySet().size() >= 1){ //2
                        for(String tags : vg.clans.keySet()){
                            while(vg.clans.get(tags) > 30){
                                Clan c = hg.core.getClanManager().getClan(tags);
                                List<ClanPlayer> p = c.getOnlineMembers();
                                int valor = (int) (Math.random() * vg.clans.get(tags) + 1); 
                                ClanPlayer a = p.get(valor);
                                Player remove = hg.getServer().getPlayer(a.getName());
                                vg.todosParticipantes.remove(remove);
                                remove.teleport(tm.getTeleportSaida());
                                remove.sendMessage(config.getString("Mensagens_Player.kickado").replace("&", "§"));
                                removePlayerInClanList(tags);
                            }
                        }
                        iniciarAnuncios2();
                    }else{
                        sendMessageList4(config.getStringList("Mensagens_Player.Anuncio_Cancelado"), "Sem clans suficientes!");
                        for(Player p : vg.todosParticipantes){
                            p.teleport(tm.getTeleportSaida());
                        }
                        vg.resetVariaveis();
                    }
                    hg.getServer().getScheduler().cancelTask(id1);
                }
            }
        }, 0, config.getInt("Gladiador.Anuncio_Tempo_Entre_As_Mensagens") * 20L);
    }
    private void Gladiadores()
    {
    	//Converte a chance de (0~100) pra (0~1), 10 = 0.1, 5 = 0.05
    	//config.getInt("Gladiador_Tag.ChanceSorteio")
    	gladiadores.clear();
    	double chance = 30 / 100;
    	for(ClanPlayer player : vencedor.getOnlineMembers())
    	{
    		String name = player.getName();
    		System.out.println(name);
    		
    		//Se o nome for legível e a chance for maior que o número gerado
    		if(name != null && Math.random() >= chance)
    		{
<<<<<<< HEAD
    			gladiadores.add(hg.getServer().getPlayer(name));
    		}
            for(Player pa : gladiadores){
            	config.set("Gladiador_Tag.Jogador_Com_A_Tag_Atual", pa.getName());
            	System.out.println(pa.getName());
            	hg.saveConfig();
                }
    	}
    }
    private void Mito() {
    	
    	double chance = 40 / 100;
    	for(ClanPlayer player : vencedor.getOnlineMembers())
    	{
    		String PlayerSortudo = player.getName();
    		if(PlayerSortudo != null & Math.random() >= chance) {
    			LsMito.setMito(PlayerSortudo);
                config.set("Mito_Tag.Jogador_Com_A_Tag_Atual", PlayerSortudo);
                hg.saveConfig();
                mito = player.toPlayer();
=======
    			String glad = config.getString("Gladiador_Tag.Tag1");
    			
    			//Se for o primeiro gladiador, pode ser qualquer um, se não verifica o escolhido é igual ao primeiro
    			//Caso seja, ignore
    			if(primeiro || !name.equals(glad))
    				return player;
>>>>>>> 4569959bec1568f4761e50d345300afa0ea186b9
    		}
    	}
    }
    public void iniciarAnuncios2(){
        vg.isAberto = false;
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        id2 = scheduler.scheduleSyncRepeatingTask(hg, new Runnable() {
<<<<<<< HEAD
=======
        	
        	public void gladfinal() {
			gladiadores.get(0).getName();
                sendMessageList3(config.getStringList("Mensagens_Player.Anuncio_Fim"), vencedor, gladiadores, mito);
                for(ClanPlayer cp : vencedor.getOnlineMembers()){
                    hg.economy.depositPlayer(cp.getName(), vg.premioParaCada);
                }
                for(ClanPlayer cp : vencedor.getLeaders()){
                    hg.economy.depositPlayer(cp.getName(), vg.premioParaLider);
                }
                for(Player p : vg.todosParticipantes){
                    p.teleport(tm.getTeleportSaida());
                    System.out.println("[Gladiador] Todos Participantes foram para TeleportSaida!");
                }
                vg.resetVariaveis();
                hg.getServer().getScheduler().cancelTask(id2);

        	}
>>>>>>> 4569959bec1568f4761e50d345300afa0ea186b9
			@Override
            public void run() {
                if(vg.isOcorrendo){
                    
                    //Deixa o mundo sempre de noite
                    if(vg.isNoite){
                        hg.getServer().getWorld(config.getString("Localizacoes.Spawn_Entrada.World")).setTime(17000);
                    }
                    
                    //Deixa o mundo sem chuva
                    if(vg.isChuvendo){
                        hg.getServer().getWorld(config.getString("Localizacoes.Spawn_Entrada.World")).setWeatherDuration(0);
                    }
                    
                    //Verifica se tem apenas um clan no gladiador
                    if(vg.clans.keySet().size() == 1){
                        
                        //Pega o clan vencedor
                        for(String venc : vg.clans.keySet()){
                            vencedor = hg.core.getClanManager().getClan(venc);
                            vg.isOcorrendo = false;
                            hg.getConfig().set("Gladiador.Ultimo_Clan_Vencedor", venc);
                            hg.saveConfig();
                        }
                        
                        //Definir Gladiador
                        Gladiadores();
                        
                        //Definir Mito
                        Mito();
  
                    }else if(vg.clans.keySet().isEmpty()){
                        cancelarEvento("Evento sem vencedores");
                    }else{
                        sendMessageList2(config.getStringList("Mensagens_Player.Anuncio_Iniciado"));
                        for(String s : vg.clans.keySet()){
                        	Clan c = hg.core.getClanManager().getClan(s);
                        	c.setFriendlyFire(false);
                        }
                    }
                    
                }else{
                	gladiadores.get(0).getName();
                	System.out.println("Gladiadores: " + gladiadores);
                    sendMessageList3(config.getStringList("Mensagens_Player.Anuncio_Fim"), vencedor, gladiadores, mito);
                    for(ClanPlayer cp : vencedor.getOnlineMembers()){
                        hg.economy.depositPlayer(cp.getName(), vg.premioParaCada);
                    }
                    for(ClanPlayer cp : vencedor.getLeaders()){
                        hg.economy.depositPlayer(cp.getName(), vg.premioParaLider);
                    }
                    for(Player p : vg.todosParticipantes){
                        p.teleport(tm.getTeleportSaida());
                    }
                    vg.resetVariaveis();
                    hg.getServer().getScheduler().cancelTask(id2);
                }
            }
        }, 0, config.getInt("Gladiador.Anuncio_Tempo_Entre_As_Mensagens") * 20L);
    }
    
    public void cancelarEvento(String motivo){
        sendMessageList4(config.getStringList("Mensagens_Player.Anuncio_Cancelado"), motivo);
        hg.getServer().getScheduler().cancelTask(id1);
        hg.getServer().getScheduler().cancelTask(id2);
        for(Player p : vg.todosParticipantes){
            p.teleport(tm.getTeleportSaida());
        }
        vg.resetVariaveis();
    }
    
    private void sendMessageList2(List<String> mensagens){
    	StringBuilder builder = new StringBuilder(); 
        for(String clans : vg.clans.keySet()){
        	Clan clann = hg.core.getClanManager().getClan(clans);
            if(builder.toString().isEmpty()){
                builder.append(clann.getColorTag());
            }else{
                builder.append("§0, " + clann.getColorTag());
            }
        }
        for (String s : mensagens) {
        	hg.getServer().broadcastMessage(s.replace("&", "§").replace("<preco>", String.valueOf(vg.precoParaParticipar)).replace("<limite>", String.valueOf(vg.limiteDeMembros)).replace("<players>", String.valueOf(vg.todosParticipantes.size())).replace("<clans>", builder.toString()));
        }
        /*StringBuilder builder = new StringBuilder(); 
        for (String s : mensagens) {
            for(String clans : vg.clans.keySet()){
                if(builder.toString().isEmpty()){
                    builder.append(clans);
                }else{
                    builder.append("�0, " + clans);
                }
            }
             hg.getServer().broadcastMessage(s.replace("&", "�").replace("<preco>", String.valueOf(vg.precoParaParticipar)).replace("<limite>", String.valueOf(vg.limiteDeMembros)).replace("<players>", String.valueOf(vg.todosParticipantes.size())).replace("<clans>", builder.toString()));
        }*/
    }
    
    private void sendMessageList3(List<String> mensagens, Clan vencedor, List<Player> gladiadores, Player mito){
        for (String s : mensagens) {
            for(Player p : gladiadores){
            	if(mito != null){
            		if(gladiadores != null){
<<<<<<< HEAD
            			hg.getServer().broadcastMessage(s.replace("&", "�").replace("<vencedor>", vencedor.getColorTag()).replace("<gladiadores>", p.getName()).replace("<mito>", mito.getName()));
            		}else{
            			hg.getServer().broadcastMessage(s.replace("&", "�").replace("<vencedor>", vencedor.getColorTag()).replace("<mito>", mito.getName()));
=======
            			hg.getServer().broadcastMessage(s.replace("&", "§").replace("<vencedor>", vencedor.getColorTag()).replace("<gladiadores>", p.getName()).replace("<mito>", mito));
            		}else{
            			hg.getServer().broadcastMessage(s.replace("&", "§").replace("<vencedor>", vencedor.getColorTag()).replace("<mito>", mito));
>>>>>>> 4569959bec1568f4761e50d345300afa0ea186b9
            		}
            	}else{
            		hg.getServer().broadcastMessage(s.replace("&", "§").replace("<vencedor>", vencedor.getColorTag()).replace("<gladiadores>", p.getName()));
            	}
            }
        }
    }
    
    private void sendMessageList4(List<String> mensagens, String motivo){
        for (String s : mensagens) {
            hg.getServer().broadcastMessage(s.replace("&", "§").replace("<motivo>", motivo));
        }
    }

    public void addPlayerInClanList(String clan){
        if(vg.clans.containsKey(clan)){
            vg.clans.put(clan, vg.clans.get(clan) + 1);
        }else{
            vg.clans.put(clan, 1);
        }
    }
    
    public void removePlayerInClanList(String clan){
        if(vg.clans.containsKey(clan)){
            if(vg.clans.get(clan) >= 2){
                vg.clans.put(clan, vg.clans.get(clan) - 1);
            }else{
                vg.clans.remove(clan);
            }
        }
    }
    public void addClanInClanList(String clan){
        if(!vg.clans.containsKey(clan)){
            vg.clans.put(clan, 1);
        }
    }
    
    public void removeClanInClanList(String clan){
        if(!vg.clans.containsKey(clan)){
            vg.clans.remove(clan);
        }
    }
    
}