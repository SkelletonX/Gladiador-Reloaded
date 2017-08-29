package me.skelletonx.br.GladiadorReloaded.commands;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.skelletonx.br.GladiadorReloaded.Gladiador;
import me.skelletonx.br.GladiadorReloaded.VariaveisGlobais;
import me.skelletonx.br.GladiadorReloaded.manager.GladiadorManager;
import me.skelletonx.br.GladiadorReloaded.manager.TeleportesManager;

public class ComandosAdmin implements CommandExecutor{
    
    private Gladiador hg = Gladiador.getGladiador();
    private final FileConfiguration config = hg.getConfig();
    private TeleportesManager tm = new TeleportesManager();
    private VariaveisGlobais vg = hg.vg;
    private GladiadorManager gm = new GladiadorManager();

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        Player p = null;
        if(cs instanceof Player){
            p = (Player)cs;
        }
        if(cmd.getName().equalsIgnoreCase("gladiadoradmin")){
            if(cs.hasPermission("hgladiador.staff")){
                if(args.length == 0){
                    cs.sendMessage("§4[Gladiador] Comandos");
                    cs.sendMessage("§4/gladadm iniciar §c- Inicia o gladiador");
                    cs.sendMessage("§4/gladadm cancelar §c- Cancela o gladiador");
                    cs.sendMessage("§4/gladadm kickplayer <player> §c- Kika um jogador do gladiador");
                    cs.sendMessage("§4/gladadm kickclan <clantag> §c- Kika um clan do gladiador");
                    cs.sendMessage("§4/gladadm set entrada/saida/camarote §c- Seta as localizacoes");
                    cs.sendMessage("§4/gladadm reload §c- Da reload na config do plugin");
                }else if(args.length == 1 && (args[0].equalsIgnoreCase("iniciar"))){
                    System.out.println(String.valueOf(vg.isOcorrendo));
                    if(!vg.isOcorrendo){
                        gm.iniciarAnuncios();
                    }else{
                        cs.sendMessage("§4[Gladiador] §cJa existe um gladiador ocorrendo no momento");
                    }
                }else if(args.length == 2 && (args[0].equalsIgnoreCase("set")) && (args[1].equalsIgnoreCase("entrada"))){
                    tm.setLocationEntrada(p);
                    cs.sendMessage("§4[Gladiador] §cEntrada setada com sucesso");
                }else if(args.length == 2 && (args[0].equalsIgnoreCase("set")) && (args[1].equalsIgnoreCase("saida"))){
                    tm.setLocationSaida(p);
                    cs.sendMessage("§4[Gladiador] §cSaida setada com sucesso");
                }else if(args.length == 2 && (args[0].equalsIgnoreCase("set")) && (args[1].equalsIgnoreCase("camarote"))){
                    tm.setLocationCamarote(p);
                    cs.sendMessage("§4[Gladiador] §cCamarote setado com sucesso");
                }else if(args.length == 1 && (args[0].equalsIgnoreCase("cancelar"))){
                    if(vg.isOcorrendo){
                        gm.cancelarEvento("Evento cancelado por um staffer");
                    }else{
                        cs.sendMessage("§4[Gladiador] §cNao existe nenhum gladiador ocorrendo no momento");
                    }
                }else if(args.length == 2 && (args[0].equalsIgnoreCase("kickplayer"))){
                    if(vg.isOcorrendo){
                        Player kickado = hg.getServer().getPlayer(args[1].toString());
                        if(kickado == null){
                            cs.sendMessage("§4[Gladiador] §cPlayer nao encontrado");
                        }else{
                            if(vg.todosParticipantes.contains(kickado)){
                                vg.todosParticipantes.remove(kickado);
                                kickado.teleport(tm.getTeleportSaida());
                                kickado.sendMessage(config.getString("Mensagens_Player.kickado").replace("&", "§"));
                                cs.sendMessage("§4[Gladiador] §cPlayer kickado com sucesso");
                            }else{
                                cs.sendMessage("§4[Gladiador] §cEsse jogador nao esta no gladiador");
                            }
                        }
                    }else{
                        cs.sendMessage("§4[Gladiador] §cNao existe nenhum gladiador ocorrendo no momento");
                    }
                }else if(args.length == 2 && (args[0].equalsIgnoreCase("kickclan"))){
                    if(vg.isOcorrendo){
                        Clan kickado = hg.core.getClanManager().getClan(args[0].toString());
                        if(kickado == null){
                            cs.sendMessage("§4[Gladiador] §cClan nao encontrado");
                        }else{
                            for(String s : vg.clans.keySet()){
                                if(s.contains(kickado.getColorTag())){
                                    vg.clans.remove(kickado.getColorTag());
                                    for(ClanPlayer cp : kickado.getOnlineMembers()){
                                        Player cpp = hg.getServer().getPlayer(cp.getName());
                                        if(vg.todosParticipantes.contains(cpp)){
                                            cpp.teleport(tm.getTeleportSaida());
                                            cpp.sendMessage(config.getString("Mensagens_Player.Clan_kickado").replace("&", "§"));
                                            vg.todosParticipantes.remove(cpp);
                                        }
                                    }
                                    cs.sendMessage("§4[Gladiador] §cClan kickado com sucesso");
                                }else{
                                    cs.sendMessage("§4[Gladiador] §cEsse clan nao esta no gladiador");
                                }
                            }
                        }
                    }else{
                        cs.sendMessage("§4[Gladiador] §cNao existe nenhum gladiador ocorrendo no momento");
                    }
                }else if(args.length == 1 && (args[0].equalsIgnoreCase("reload"))){
                    hg.reloadConfig();
                    cs.sendMessage("§4[Gladiador] §cReload realizado com sucesso!");
                }
            }else{
                cs.sendMessage("§4[Gladiador] §cVoce nao tem permissao para fazer isso");
            }
        }
        
        return true;
    }
    
}
