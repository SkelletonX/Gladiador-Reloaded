package me.skelletonx.br.GladiadorReloaded;

import java.io.File;
import java.util.Calendar;
import net.milkbowl.vault.economy.Economy;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;

import me.skelletonx.br.GladiadorReloaded.commands.ComandosAdmin;
import me.skelletonx.br.GladiadorReloaded.commands.ComandosPlayer;
import me.skelletonx.br.GladiadorReloaded.listeners.Eventos;
import me.skelletonx.br.GladiadorReloaded.manager.TeleportesManager;
import me.skelletonx.br.GladiadorReloaded.manager.TeleportesManager.Locations;

public class Gladiador extends JavaPlugin{

    public Scoreboard scoreboard = null;
    public SimpleClans core;
    public static VariaveisGlobais vg = new VariaveisGlobais();
    public Economy economy = null;
    
    @Override
    public void onEnable() {
        setupEconomy();
        
        if(!hookSimpleClans()){
            System.out.println("[Gladiador] Plugin Desabilitado - SimpleClans Nao Encontrado");
            getPluginLoader().disablePlugin(this);
        }else{
            System.out.println("[Gladiador] Hook Com SimpleClans Ativado");
        }
        
        if (!new File(getDataFolder(), "config.yml").exists()){
            saveDefaultConfig();
            System.out.println("[Gladiador] Config.yml Criada Com Sucesso");
        }
        
        getServer().getPluginManager().registerEvents(new Eventos(), this);
        System.out.println("[Gladiador] Eventos Registrados Com Sucesso");
        
        if(getConfig().getBoolean("Clan_Tag.Ativar_Tab_Tag")){
            TabTag tt = new TabTag();
            tt.iniciarTabTagAndCustomNameTag();
            if(getConfig().getBoolean("Clan_Tag.Ativar_Custom_Name_Tag")){
                System.out.println("[Gladiador] Tag Do Clan No Custom Name Ativado");
            }
            System.out.println("[Gladiador] Tag Do Clan No TAB Ativado");
        }

        getCommand("gladiador").setExecutor(new ComandosPlayer());
        getCommand("gladiadoradmin").setExecutor(new ComandosAdmin());
        
        vg.isOcorrendo = false;
        vg.isAberto = false;
        vg.todosParticipantes.clear();
        vg.clans.clear();
        vg.quantMensagens = Gladiador.getGladiador().getConfig().getInt("Gladiador.Anuncio_Quantidade");
        vg.precoParaParticipar = Gladiador.getGladiador().getConfig().getInt("Gladiador.Preco_Para_Entrar");
        vg.premioParaCada = Gladiador.getGladiador().getConfig().getInt("Gladiador_Premio.Money_Para_Cada_Integrante");
        vg.premioParaLider = Gladiador.getGladiador().getConfig().getInt("Gladiador_Premio.Money_Para_Cada_Lider");
        vg.limiteDeMembros = Gladiador.getGladiador().getConfig().getInt("Gladiador.Limite_De_Membros");
        vg.quantGladiadores = Gladiador.getGladiador().getConfig().getInt("Gladiador_Tag.Quantidade");
        vg.quantGladiadoresAdicionados = 0;
        
        if(getServer().getWorld(getConfig().getString("Localizacoes.Spawn_Entrada.World")) != null)
        	Locations.updateCache(TeleportesManager.cache, Locations.ENTRADA, new Location(getServer().getWorld(getConfig().getString("Localizacoes.Spawn_Entrada.World")), getConfig().getDouble("Localizacoes.Spawn_Entrada.X"), getConfig().getDouble("Localizacoes.Spawn_Entrada.Y"), getConfig().getDouble("Localizacoes.Spawn_Entrada.Z")));
        if(getServer().getWorld(getConfig().getString("Localizacoes.Spawn_Saida.World")) != null)
        	Locations.updateCache(TeleportesManager.cache, Locations.SAIDA, new Location(getServer().getWorld(getConfig().getString("Localizacoes.Spawn_Saida.World")), getConfig().getDouble("Localizacoes.Spawn_Saida.X"), getConfig().getDouble("Localizacoes.Spawn_Saida.Y"), getConfig().getDouble("Localizacoes.Spawn_Saida.Z")));
        if(getServer().getWorld(getConfig().getString("Localizacoes.Spawn_Camarote.World")) != null)
        	Locations.updateCache(TeleportesManager.cache, Locations.CAMAROTE, new Location(getServer().getWorld(getConfig().getString("Localizacoes.Spawn_Camarote.World")), getConfig().getDouble("Localizacoes.Spawn_Camarote.X"), getConfig().getDouble("Localizacoes.Spawn_Camarote.Y"), getConfig().getDouble("Localizacoes.Spawn_Camarote.Z")));
        
        scoreboard = getServer().getScoreboardManager().getMainScoreboard();
        
         if(getConfig().getBoolean("Automacao.Ativar")){
             iniciarSchedulerDeAutomacao();
         }
        System.out.println("[Gladiador] Plugin Habilitado - Versao (V " + getDescription().getVersion() + ")");
    }

    @Override
    public void onDisable() {
        System.out.println("[Gladiador] Plugin Desabilitado - Versao (V " + getDescription().getVersion() + ")");
    }
    
    public static Gladiador getGladiador(){
        return (Gladiador) Bukkit.getServer().getPluginManager().getPlugin("GladiadorReloaded");
    }
    
    private boolean hookSimpleClans(){
        try {
            for (Plugin plugin : getServer().getPluginManager().getPlugins()) {
                if (plugin instanceof SimpleClans) {
                    this.core = (SimpleClans) plugin;
                    return true;
                }
            }
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    private boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
    
    public Clan getClanByPlayer(Player p){
        return core.getClanManager().getClanByPlayerName(p.getName());
    }
    
    public void iniciarSchedulerDeAutomacao(){
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    int diaConf = getConfig().getInt("Automacao.Dia");
                    int horaConf = getConfig().getInt("Automacao.Hora");
                    int minutoConf = getConfig().getInt("Automacao.Minuto");
                    if(diaConf == getDay()){
                        if(horaConf == getHour()){
                            if(minutoConf == getMinute()){
                                getServer().dispatchCommand(Bukkit.getConsoleSender(), "gladadm iniciar");
                            }
                        }
                    }
                }
            }, 60, 1 * 20L);
    }
    
    public int getDay(){
       return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }
    
    public int getHour(){
       return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }
    
    public int getMinute(){
       return Calendar.getInstance().get(Calendar.MINUTE);
    }
}