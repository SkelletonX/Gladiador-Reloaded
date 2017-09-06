package me.skelletonx.br.GladiadorReloaded.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.skelletonx.br.GladiadorReloaded.Gladiador;
import me.skelletonx.br.GladiadorReloaded.VariaveisGlobais;
import me.skelletonx.br.GladiadorReloaded.manager.TeleportesManager;

public class ComandosPlayer implements CommandExecutor{

    private Gladiador hg = Gladiador.getGladiador();
    private final FileConfiguration config = hg.getConfig();
    private TeleportesManager tm = new TeleportesManager();
    private VariaveisGlobais vg = hg.vg;
    
	@Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        Player p;
        if(cs instanceof Player){
            p = (Player)cs;
            
            if(cmd.getName().equalsIgnoreCase("gladiador")){
                if(args.length == 0){
                    sendMessageList(p, config.getStringList("Mensagens_Player.Default"));
                //Comando Entrar
                }else if(args.length == 1 && (args[0].equalsIgnoreCase("entrar"))){
                    if(p.hasPermission("Gladiador.entrar")){
                        if(vg.isAberto){
                            if(vg.isOcorrendo){
                                if(config.getBoolean("Gladiador.Necessario_Ter_Clan_Para_Participar")){
                                    if(hg.core.getClanManager().getClanByPlayerName(p.getName()) == null){
                                        p.sendMessage(config.getString("Mensagens_Player.Necessario_Clan").replace("&", "§"));
                                        return true;
                                    }
                                }
                                if(hg.economy.getBalance(p.getName()) >= vg.precoParaParticipar){
                                    if(!vg.todosParticipantes.contains(p)){
                                        p.teleport(tm.getTeleportEntrada());
                                        hg.economy.withdrawPlayer(p.getName(), vg.precoParaParticipar);
                                        vg.todosParticipantes.add(p);
                                        addClanInList(p);
                                        p.sendMessage(config.getString("Mensagens_Player.Entrou").replace("&", "§"));
                                        return true;
                                    }else{
                                        p.sendMessage(config.getString("Mensagens_Player.Ja_Esta").replace("&", "§"));
                                        return true;
                                    }
                                }else{
                                    p.sendMessage(config.getString("Mensagens_Player.Sem_Money").replace("&", "§"));
                                    return true;
                                }
                            }else{
                                p.sendMessage(config.getString("Mensagens_Player.Ocorrendo").replace("&", "§"));
                                return true;
                            }
                        }else{
                            p.sendMessage(config.getString("Mensagens_Player.Fechado").replace("&", "§"));
                            return true;
                        }
                    }else{
                        p.sendMessage("§cSem Permissao");
                    }
                //Comando Sair
                }else if(args.length == 1 && (args[0].equalsIgnoreCase("sair"))){
                    if(p.hasPermission("Gladiador.sair")){
                        if(vg.isAberto){
                            if(vg.todosParticipantes.contains(p)){
                                p.teleport(tm.getTeleportSaida());
                                vg.todosParticipantes.remove(p);
                                vg.clans.put(hg.core.getClanManager().getClanByPlayerName(p.getName()).getColorTag(), vg.clans.get(hg.core.getClanManager().getClanByPlayerName(p.getName()).getColorTag()) - 1);
                                String tag = hg.core.getClanManager().getClanByPlayerName(p.getName()).getColorTag();
                                vg.clans.put(tag, vg.clans.get(tag) - 1);
        		                if(vg.clans.get(tag) == 0){
        		                    vg.clans.remove(tag);
        		                }
                                p.sendMessage(config.getString("Mensagens_Player.Saiu").replace("&", "§"));
                                return true;
                            }else{
                                p.sendMessage(config.getString("Mensagens_Player.Nao_Esta").replace("&", "§"));
                                return true;
                            }
                        }else{
                            p.sendMessage(config.getString("Mensagens_Player.Fechado").replace("&", "§"));
                            return true;
                        }
                    }else{
                        p.sendMessage("§cSem Permissao");
                    }
            //Comando Camarote
            }else if(args.length == 1 && (args[0].equalsIgnoreCase("camarote"))){
                if(p.hasPermission("Gladiador.camarote")){
                    if(vg.isOcorrendo){
                        p.teleport(tm.getTeleportCamarote());
                        p.sendMessage(config.getString("Mensagens_Player.Camarote").replace("&", "§"));
                        return true;
                    }else{
                        p.sendMessage(config.getString("Mensagens_Player.Fechado").replace("&", "§"));
                        return true;
                        }
                    }else{
                        p.sendMessage("§cSem Permissao");
                    }
                }else if(args.length == 1 && (args[0].equalsIgnoreCase("tags"))){
                    if(p.hasPermission("Gladiador.tags")){
                        if(vg.isMitoEnable){
                            if(vg.isGladiadorEnable){
                                sendMessageListTags(p, config.getStringList("Mensagens_Player.Tags"), true, true);
                                return true;
                            }else{
                                sendMessageListTags(p, config.getStringList("Mensagens_Player.Tags"), false, true);
                                return true;
                            }
                        }else{
                            sendMessageListTags(p, config.getStringList("Mensagens_Player.Tags"), true, false);
                            return true;
                        }
                }else{
                    p.sendMessage("§cSem Permissao");
                }
            }else if(args.length == 1 && (args[0].equalsIgnoreCase("status"))){
                if(p.hasPermission("Gladiador.status")){
                    if(vg.isOcorrendo){
                        sendMessageListStatus(p, config.getStringList("Mensagens_Player.Status"));
                    }else{
                        p.sendMessage(config.getString("Mensagens_Player.Fechado").replace("&", "§"));
                        return true;
                    }
                }else{
                    sendMessageList(p, config.getStringList("Mensagens_Player.Default"));
                }
                }else{
                    p.sendMessage("§cSem Permissao");
                }
            }
        }
        return true;
    }

    private void addClanInList(Player p){
    	if(hg.core.getClanManager().getClanPlayer(p.getName()) != null){
	        String tag = hg.core.getClanManager().getClanByPlayerName(p.getName()).getColorTag();
	        if(!vg.clans.containsKey(tag)){
	            vg.clans.put(tag, 1);
	        }else{
	            vg.clans.put(tag, vg.clans.get(tag) + 1);
	        }
    	}
    }
    private void sendMessageList(Player p, List<String> mensagens){
        for (String s : mensagens) {
            p.sendMessage(s.replace("&", "§"));
        }
    }
    
    private void sendMessageListStatus(Player p, List<String> mensagens){
        StringBuilder builder = new StringBuilder(); 
        for(String clans : vg.clans.keySet()){
            if(builder.toString().isEmpty()){
                builder.append(clans);
            }else{
                builder.append("§0, " + clans);
            }
        }
        for (String s : mensagens) {
            p.sendMessage(s.replace("&", "§").replace("<players>", String.valueOf(vg.todosParticipantes.size())).replace("<clans>", builder.toString()));
        }
    }
    
    private void sendMessageListTags(Player p, List<String> mensagens, boolean glad, boolean mitoboo){
        if(glad){
            if(mitoboo){
                StringBuilder gladiadores = new StringBuilder();
                for(String s : config.getStringList("Gladiador_Tag.Jogador_Com_A_Tag_Atual")){
                    if(!gladiadores.toString().contains(s)){
                        gladiadores.append(s);
                    }
                }

                StringBuilder mito = new StringBuilder();
                for(String s : config.getStringList("Mito_Tag.Jogador_Com_A_Tag_Atual")){
                    if(!mito.toString().contains(s)){
                        mito.append(s);
                    }
                }
                for (String s : mensagens) {
                    p.sendMessage(s.replace("&", "§").replace("<gladiadores>", gladiadores.toString()).replace("<mito>", mito.toString()));
                }
            }else{
                StringBuilder gladiadores = new StringBuilder();
                for(String s : config.getStringList("Gladiador_Tag.Jogador_Com_A_Tag_Atual")){
                    if(!gladiadores.toString().contains(s)){
                        gladiadores.append(s);
                    }
                }
                for (String s : mensagens) {
                    p.sendMessage(s.replace("&", "§").replace("<gladiadores>", gladiadores.toString()));
                }
            }
        }else{
            StringBuilder mito = new StringBuilder();
            for(String s : config.getStringList("Mito_Tag.Jogador_Com_A_Tag_Atual")){
                if(!mito.toString().contains(s)){
                    mito.append(s);
                }
            }
            for (String s : mensagens) {
                p.sendMessage(s.replace("&", "§").replace("<mito>", mito.toString()));
            }
        }
    }
    
}